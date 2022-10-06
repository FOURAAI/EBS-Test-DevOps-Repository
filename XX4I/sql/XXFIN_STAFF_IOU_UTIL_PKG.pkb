SET PAGESIZE 0 FEEDBACK OFF DEFINE OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500

CREATE OR REPLACE package body APPS.XXFIN_STAFF_IOU_UTIL_PKG as
  /* $header: XXFIN_STAFF_IOU_UTIL_PKG 29-OCT-2017 $ */
/******************************************************************************
   pl/sql package             :  XXFIN_STAFF_IOU_UTIL_PKG
   file                       :  XXFIN_STAFF_IOU_UTIL_PKG.pkb
   author                     :  Saravana Gowtham
   date                       :  22-APR-2020
   purpose                    :  Staff IOU Process utility package
-- -----------------------------------------------------------------------------
-- modification:
-- dd-mon-yyyy - <<abc>> - explanation
-- -----------------------------------------------------------------------------
*******************************************************************************/

procedure cancel_iou(P_iou_id in varchar2) as


begin

delete from xxfin_staff_iou where IOU_ID = P_iou_id;

commit;

end cancel_iou;

procedure get_diff_amount (P_iou_id in varchar2 , P_bill_amt in varchar2, X_AMT_OUT OUT VARCHAR2) is

l_given_amount  number;

l_diff_amount   number;

l_bill_amount   number  := to_number(P_bill_amt);

begin


select to_number(AMOUNT_OMR)
into   l_given_amount 
from   xxfin_staff_iou 
where  IOU_ID = P_iou_id;

if l_given_amount > 0 then

l_diff_amount  :=  l_given_amount - l_bill_amount;

end if;


X_AMT_OUT := to_char(l_diff_amount) ;

--return X_AMT_OUT;
exception when others then
X_AMT_OUT := '0';

end get_diff_amount;

procedure close_iou(P_iou_id in varchar2) as



begin


update xxfin_staff_iou set status = 'Closed', 
LAST_UPDATE_DATE  = sysdate, 
LAST_UPDATED_BY   = fnd_profile.value('USER_ID'),
LAST_UPDATE_LOGIN = fnd_profile.value('LOGIN_ID')
where IOU_ID = P_iou_id;

commit;

end close_iou;

procedure validate_division(p_division in varchar2,p_amount in varchar2 ,X_FLAG OUT VARCHAR2) aS

l_flag varchar2(10);
out_flag varchar2(10);
l_amount number := to_number(p_amount);

begin

select EXCEED_200 into l_flag
from XXFIN_STAFF_IOU_DIVISION_V
where LOOKUP_CODE = p_division;


 if l_flag ='N' and (l_amount < 200 OR l_amount =200) then
 
    out_flag  := 'Y';
 
 elsif l_flag ='Y'  then

    out_flag  := 'Y';
 
 else    out_flag  := 'N';
  
  end if;

X_FLAG := out_flag;

end validate_division;

procedure bill_attach_count (p_iou_id in varchar2, X_FLAG OUT VARCHAR2) as


l_count number;

begin

select count(*) into l_count
from fnd_attached_documents fad   ,fnd_documents fd  
     ,fnd_lobs fl,
     xxfin_staff_iou iou

where 
      fad.document_id = fd.document_id  
and   fd.media_id     = fl.file_id 
and   to_char(iou.IOU_ID) = fad.pk1_value
and   fad.ENTITY_NAME = 'XXFIN_IOU_BILLS'
and   iou.IOU_ID  = p_iou_id;


if l_count =0 then 
X_FLAG :='N';

elsif l_count >0 then 
X_FLAG :='Y';
end if;


exception when others then

X_FLAG :='0';

end bill_attach_count;

procedure SEND_FYI (ERRBUF out varchar2, RETCODE  out varchar2, p_iou_id in varchar2) as

g_chr_itm_typ VARCHAR2(25) := 'XXFN_IOU';
p_chr_itemkey VARCHAR2(50); 

  cursor cur_det is
  select a.*
  from xxfin_staff_iou_v a
  where a.IOU_ID  = p_iou_id
  and   a.DIVISION <> 'OPS-IOU';

begin
  
  DBMS_OUTPUT.PUT_LINE('Inside SEND_FYI');
  
  for c in cur_det
  loop
    
--    update xxfin_staff_iou set IOU_NUM = c.SEQ where IOU_ID = c.IOU_ID;
    
    p_chr_itemkey       := c.IOU_NUM||'-'||to_char(sysdate, 'DD/MM-MI:SS');          /* Changes done by 4iapps to avoid Duplicate Item Key */
    
    DBMS_OUTPUT.PUT_LINE('Inside SEND_FYI p_chr_itemkey ='||p_chr_itemkey);
    
    WF_ENGINE.Threshold := -1;
    WF_ENGINE.CREATEPROCESS(g_chr_itm_typ, p_chr_itemkey, 'XXFIN_STAFF_IOU');
    
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'EMP_USER_NAME',c.USER_NAME);
    
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'EMP_NAME',c.FULL_NAME);
        
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'IOU_NUM',to_char(c.IOU_NUM));
    
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'DIVISION',c.DIVISION_name);
  
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'GIVEN_DATE',to_char(c.IOU_GIVEN_DATE) );
    
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'AMOUNT', c.AMOUNT_OMR);
    
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'REMARKS', c.REMARKS);
    
    WF_ENGINE.SetItemAttrText(g_chr_itm_typ, p_chr_itemkey,'FOCAL_NAME', c.FOCAL_NAME);
    
    WF_ENGINE.SETITEMATTRTEXT ( g_chr_itm_typ, p_chr_itemkey, 'WF_REGION', 
                          'JSP:/OA_HTML/' || 'OA.jsp?page=/xxfin/oracle/apps/ap/iou/webui/iouWFRN' || '&' || 
                          'Piouid=' || TO_NUMBER (c.iou_id) ) ;
    
    wf_engine.SetItemUserKey (g_chr_itm_typ,p_chr_itemkey,c.IOU_NUM||'-'||to_char(sysdate, 'DD/MM-MI:SS'));   /* Changes done by 4iapps to avoid Duplicate Item Key */
    
    wf_engine.SetItemOwner(g_chr_itm_typ,p_chr_itemkey,c.USER_NAME);
    
    WF_ENGINE.STARTPROCESS(g_chr_itm_typ, p_chr_itemkey);
    
      COMMIT;
  DBMS_OUTPUT.PUT_LINE('Inside SEND_FYI after start');
  
  END LOOP;
  
  
  exception when others then
  DBMS_OUTPUT.PUT_LINE('Inside SEND_FYI Exception SQLERRM ='||SQLERRM);
  DBMS_OUTPUT.PUT_LINE('Inside SEND_FYI Exception SQLCODE ='||SQLCODE);

  WF_CORE.CONTEXT('XXFIN_STAFF_IOU_UTIL_PKG','SEND_FYI',g_chr_itm_typ,p_chr_itemkey);
  
end SEND_FYI;


procedure generate_iou_num (p_iou_id in varchar2) as


l_seq_num   varchar2(100);


cursor c_seq (p_division_code in varchar2) is
select CUR_RUN_SEQUENCE
from   XXFIN_AP_PETTYCASH_SEQ_V 
where SEQ_PREFIX = p_division_code
and trunc(sysdate) between DATE_START and DATE_END;

cursor c_iou is
  select division
  from   xxfin.xxfin_staff_iou
  where iou_id = p_iou_id 
  and iou_num is null;

begin

  DBMS_OUTPUT.PUT_LINE('Inside generate_iou_num');
  
  for c in c_iou loop
  
   DBMS_OUTPUT.PUT_LINE('Inside generate_iou_num 2');
  
  if c.division is not null then
    open   c_seq(c.division);
    fetch  c_seq into l_seq_num;
    close  c_seq;
  DBMS_OUTPUT.PUT_LINE('Inside generate_iou_num 3 = '||l_seq_num);
  end if;
  
  if l_seq_num is not null then
  
    update xxfin_staff_iou set iou_num =l_seq_num,
    LAST_UPDATE_DATE  = sysdate, 
    LAST_UPDATED_BY   = fnd_profile.value('USER_ID'),
    LAST_UPDATE_LOGIN = fnd_profile.value('LOGIN_ID')
    where iou_id = p_iou_id and iou_num is null;

    
    update xxfin_ap_pettycash_seq_t set CURRENT_RUNNING_SEQUENCE = CURRENT_RUNNING_SEQUENCE+1 ,
    LAST_UPDATE_DATE  = sysdate, 
    LAST_UPDATED_BY   = fnd_profile.value('USER_ID'),
    LAST_UPDATE_LOGIN = fnd_profile.value('LOGIN_ID')
    where SEQ_PREFIX = c.division;
    
    
  end if;
   
  
  commit;
  
  end loop;
  
end generate_iou_num;

FUNCTION get_email (p_per_id             IN     NUMBER)
  return varchar2 IS
  
  p_email varchar2(100);
  
   BEGIN
      SELECT   email_address
        INTO   p_email
        FROM   per_all_people_f 
       WHERE   person_id = p_per_id
         AND   SYSDATE BETWEEN effective_start_date AND effective_end_date 
         and   current_employee_flag = 'Y'
         AND   ROWNUM = 1;
               
      return   p_email;
               
   EXCEPTION
      WHEN OTHERS
      THEN
        return NULL;
  END get_email;



FUNCTION Get_Business_Days
(
    v_Begin_Date DATE,
    v_End_Date DATE   
)
    RETURN NUMBER
AS
    v_DaysInbetween NUMBER := 0;
    v_BusDaysInbetween NUMBER := 0;
         
BEGIN
    WITH days
    AS
    (
        SELECT
            v_Begin_Date + seq AS day_date,
            to_char(v_Begin_Date + seq , 'D') day_of_week
        FROM
        (
            SELECT ROWNUM-1 seq
            FROM   ( SELECT 1
                     FROM   dual
                     --number of rows should be exactly the number of days between begin and end dates
                     CONNECT BY LEVEL <= (v_End_Date - v_Begin_Date) + 1
                   )
        )
        ORDER BY 1
    )
    SELECT
        v_End_Date - v_Begin_Date AS days_inbetween,
        count(1) business_days_inbetween
    INTO
        v_DaysInbetween,
        v_BusDaysInbetween        
    FROM
        days
    WHERE
        ---------------------------------------------------
        --...then exclude Fri and Sat
        ---------------------------------------------------
        days.day_of_week NOT IN (6,7)
        ---------------------------------------------------
        --...then exclude week-day holidays
        ---------------------------------------------------
        /*
        AND NOT EXISTS
            (
                SELECT holiday
                FROM holidays_table ht
                WHERE ht.holidaydate = days.day_date
                --Optionally include Region!
            )*/;        
 
    DBMS_OUTPUT.PUT_LINE('Days inbetween = ' || TO_CHAR(v_DaysInbetween));
    DBMS_OUTPUT.PUT_LINE('Days inbetween = ' || TO_CHAR(v_BusDaysInbetween));
     
    RETURN v_BusDaysInbetween;        
END Get_Business_Days;

PROCEDURE xxfin_interface_inv ( P_PTYCSH_HDR_ID             IN       NUMBER,
                          p_err_code                  out      VARCHAR2,
                          p_err_msg                   out      VARCHAR2) AS

--P_PO_HEADER_ID  NUMBER  :=250144;
l_vendor_id     NUMBER  ;
l_ven_site_id   NUMBER  ;
p_group_id      NUMBER  ;
l_amount        NUMBER  ;

l_inv_seq       NUMBER;
l_inv_line_seq  NUMBER;
l_org_id         NUMBER;
l_term_name      VARCHAR2(30);
l_term_id        NUMBER;
l_payment_type   VARCHAR2(30);
l_currency       VARCHAR2(30);
l_line_no        NUMBER;
l_pay_group      VARCHAR2(30);
l_header_error     VARCHAR2(1);
l_line_error       VARCHAR2(1);
l_invoice_num      VARCHAR2(50);
l_po_num           VARCHAR2(50);
l_hdr_err          VARCHAR2(10000);
l_line_err         VARCHAR2(10000);
l_invoice_type     VARCHAR2(50);
l_cur_period_avail_cnt NUMBER;
l_gl_date           DATE;
l_add_month         NUMBER;
l_open_period_cnt   NUMBER;
l_periodv_avbl      NUMBER;

CURSOR cur_sup_det(i_sup_id NUMBER)
IS
SELECT payment_method_lookup_code, payment_currency_code , pay_group_lookup_code
FROM ap_suppliers
WHERE vendor_id = i_sup_id;

CURSOR cur_org(p_ven_site_id NUMBER)
IS
  SELECT ss.org_id
  FROM ap_supplier_sites_all ss,
  hr_operating_units ou
  WHERE ss.org_id  = ou.organization_id
  AND ss.vendor_site_id = p_ven_site_id;

CURSOR cur_term
IS
  SELECT NAME,term_id
  FROM ap_terms
  WHERE NAME='Immediate';

CURSOR cur_header IS
select hro.ORGANIZATION_ID,aps.VENDOR_ID,apsa.vendor_site_id,a.*
from xxfin_ap_pettycash_hdr_v a , hr_operating_units hro , ap_suppliers aps , ap_supplier_sites_all apsa
where 1=1
and a.GOVERNATE = hro.Name
and a.VENDOR_NAME  = aps.VENDOR_NAME
and a.VENDOR_SITE_NAME = apsa.VENDOR_SITE_CODE
and hro.ORGANIZATION_ID = apsa.org_id
and a.status = 'Approved'
and a.PTYCSH_HDR_ID = P_PTYCSH_HDR_ID;


CURSOR cur_line IS
select  'ITEM' line_type_lookup_code, a.*
from  xxfin_ap_pettycash_dtl_v  a
where a.PTYCSH_HDR_ID = P_PTYCSH_HDR_ID;


   

CURSOR cur_inv_seq (l_inv_seq NUMBER)
is
   SELECT reject_lookup_code
     FROM ap.ap_interface_rejections
    WHERE parent_id = l_inv_seq;

CURSOR cur_inv_line_seq (l_inv_line_seq NUMBER)
is
   SELECT reject_lookup_code
     FROM ap.ap_interface_rejections
    WHERE parent_id = l_inv_line_seq;

BEGIN
--   fnd_client_info.set_org_context(81);--initializing org_id
--   fnd_global.Apps_initialize(user_id => 18113, resp_id => 50264,resp_appl_id => 200);
             fnd_global.apps_initialize(user_id => fnd_global.user_id, resp_id => fnd_global.resp_id,resp_appl_id => fnd_global.resp_appl_id);

   p_err_code     := '0';
   p_err_msg      := 'SUCCESS';

--    OPEN   cur_po_det;
--    fetch  cur_po_det INTO l_vendor_id,l_ven_site_id,l_amount,l_po_num;
--    CLOSE  cur_po_det;
    
    for c in cur_header loop

   fnd_client_info.set_org_context(c.ORGANIZATION_ID);--initializing org_id

    OPEN cur_term;
    fetch cur_term INTO l_term_name, l_term_id;
    CLOSE cur_term;

    OPEN  cur_sup_det(c.vendor_id);
    fetch cur_sup_det INTO l_payment_type, l_currency , l_pay_group;
    CLOSE cur_sup_det;

--    OPEN cur_org(l_ven_site_id);
--    fetch cur_org INTO l_org_id;
--    CLOSE cur_org;

BEGIN
    l_inv_seq:=ap_invoices_interface_s.nextval;
    p_group_id :=l_inv_seq;
--    l_invoice_num  := 'TRS-'||l_po_num;


   l_invoice_type := 'STANDARD';

  dbms_output.put_line('L_INV_SEQ = '||l_inv_seq);
--  fnd_file.Put_line (fnd_file.log, 'L_INV_SEQ = '||L_INV_SEQ);

  l_header_error :='N';

/*
    BEGIN
    select count(*) into l_cur_period_avail_cnt from GL_PERIOD_STATUSES where period_name = to_char(c.REQUEST_DATE,'MON-YYYY') and application_id in (200,101) and set_of_books_id=2024 and closing_status  in ('O','F');

    IF l_cur_period_avail_cnt = 2 THEN

    l_gl_date := c.REQUEST_DATE;

    ELSE

       l_add_month := 0;
       LOOP
         l_add_month := l_add_month +1;

         select count(*) into l_open_period_cnt from GL_PERIOD_STATUSES where period_name = to_char(add_months(c.REQUEST_DATE,l_add_month),'MON-YYYY') and application_id in (200,101) and set_of_books_id=2024 and closing_status  in ('O','F');
         select count(*) into l_periodv_avbl from GL_PERIOD_STATUSES where period_name = to_char(add_months(c.REQUEST_DATE,l_add_month),'MON-YYYY') and application_id in (200,101) and set_of_books_id=2024;

         IF l_open_period_cnt = 2 THEN

              l_gl_date := to_date('01-'||to_char(add_months(c.REQUEST_DATE,l_add_month),'MON-YYYY'));
         EXIT;

         ELSE

             IF l_periodv_avbl = 0 THEN
             l_header_error :='Y';

             p_err_msg := 'Could not able to find period status as open or future period in GL and AP';

             EXIT;
             END IF;

         END IF;



       END LOOP;

    END IF;

    EXCEPTION WHEN OTHERS THEN NULL;
    l_header_error :='Y';
    END;

*/

IF   l_header_error='N' THEN

INSERT INTO ap_invoices_interface (         invoice_id,
                                            invoice_num,
                                            vendor_id,
                                            vendor_site_id,
                                            invoice_amount,
                                            invoice_currency_code,
                                            invoice_date,
                                            description,
                                            pay_group_lookup_code,
                                            SOURCE,
                                            gl_date,
                                            payment_method_code,
                                            org_id,
                                            invoice_type_lookup_code,
                                            group_id,
                                            terms_id,
                                            terms_name
                                        )
            VALUES                      (   l_inv_seq,
                                            c.REQUEST_NUMBER,
                                            c.vendor_id,
                                            c.VENDOR_SITE_ID,
                                            c.CLAIM__LINE_AMOUNT,
                                            l_currency,
                                            c.REQUEST_DATE,
                                            c.vendor_name,--||IREQUEST_ID,
                                            l_pay_group,
                                            'MANUAL INVOICE ENTRY',
                                            c.REQUEST_DATE,
                                            'XXHW_CHK_PM',--decode(l_payment_type,'Cash','CASH','Check','CHECK','WIRE'),
                                            c.ORGANIZATION_ID,
                                            l_invoice_type,--'STANDARD',
                                            p_group_id,
                                            l_term_id,
                                            l_term_name
                                        );
 END IF;

   exception
      WHEN others THEN
         p_err_code := sqlcode;
         p_err_msg  := substr (sqlerrm, 1, 2000);
         l_header_error :='Y';
        dbms_output.put_line ('Error while inserting Invoice Header Record = '||sqlerrm);
        fnd_file.put_line (fnd_file.LOG,'Error while inserting Invoice Header Record = '||sqlerrm);

  END;



  IF l_header_error = 'N' THEN

  BEGIN

  FOR c IN cur_line
  loop
  l_line_no:=l_line_no+1;
  l_inv_line_seq:= ap_invoice_lines_interface_s.nextval;

  l_line_error  :='N';

  INSERT INTO ap_invoice_lines_interface (
                                           invoice_id,
                                           invoice_line_id,
                                           line_number,
                                           line_type_lookup_code,
                                           amount,
                                           dist_code_combination_id,
                                           description
                                          )
       VALUES                            (
                                          l_inv_seq,
                                          l_inv_line_seq,
                                          l_line_no,
                                          c.line_type_lookup_code,--'ITEM',
                                          c.AMOUNT_IN_OMR,
                                          c.CC_ID,
                                          substr(c.BRIEF_NARRATION,0,239)
                                          );
      END loop;
   COMMIT;


   exception
      WHEN others THEN
         p_err_code := sqlcode;
         p_err_msg  := substr (sqlerrm, 1, 2000);
         l_line_error  :='Y';
        dbms_output.put_line ('Error while inserting Invoice Lines Record = '||sqlerrm);
        fnd_file.put_line (fnd_file.LOG,'Error while inserting Invoice Lines Record = '||sqlerrm);
    END;
  END IF;

    IF(l_header_error='N' AND l_line_error='N') THEN

      xxfin_create_inv_api(p_group_id,c.ORGANIZATION_ID,p_err_code,p_err_msg);



    FOR i IN cur_inv_seq(l_inv_seq) loop

    IF i.reject_lookup_code IS NOT NULL THEN
    p_err_msg := p_err_msg||', '||i.reject_lookup_code;

    END IF;

    END loop;


    FOR i IN cur_inv_line_seq(l_inv_line_seq) loop

    IF i.reject_lookup_code IS NOT NULL THEN
    p_err_msg := p_err_msg||', '||i.reject_lookup_code;

    END IF;

    END loop;



      IF p_err_code ='0' AND p_err_msg ='SUCCESS' AND nvl(l_header_error,'N') = 'N' AND nvl(l_line_error,'N') = 'N'  THEN

       create_attachment(P_PTYCSH_HDR_ID,'T',c.ORGANIZATION_ID, p_err_msg,p_err_code);

      UPDATE xxfin_ap_pettycash_hdr_t SET INVOICE_NUMBER = c.REQUEST_NUMBER,
                                error_flag = 'Y',error_msg ='Success',
                                LAST_UPDATE_DATE  = sysdate, 
                                LAST_UPDATED_BY   = fnd_profile.value('USER_ID'),
                                LAST_UPDATE_LOGIN = fnd_profile.value('LOGIN_ID')
      WHERE PTYCSH_HDR_ID = P_PTYCSH_HDR_ID;
      COMMIT;

     fnd_file.put_line (fnd_file.LOG,'Invoice Number generated = '||c.REQUEST_NUMBER);

--     XXTS_TRAVEL_SERVICE_WF_PKG.CALL_SUPPLIER_FYI_PROC (p_hdr_rec_id,   p_err_msg ); -- Initiatng supplier FYI notification workflow process

      ELSE
--      p_err_msg  :=p_err_msg||'-'||l_hdr_err||'-'||l_line_err;

      UPDATE xxfin_ap_pettycash_hdr_t SET error_flag = 'N',error_msg = substr(p_err_msg,1,2000), ATTRIBUTE7 = 'Error in Invoice Creation',
      LAST_UPDATE_DATE  = sysdate, 
      LAST_UPDATED_BY   = fnd_profile.value('USER_ID'),
      LAST_UPDATE_LOGIN = fnd_profile.value('LOGIN_ID')
      
      WHERE PTYCSH_HDR_ID = P_PTYCSH_HDR_ID;
      COMMIT;


      END IF;
    END IF;




end loop;


  exception
      WHEN others THEN
         p_err_code := sqlcode;
         p_err_msg  := p_err_msg ||'-'||substr (sqlerrm, 1, 2000);
        dbms_output.put_line ('Exception in CREATE_INV = '||sqlerrm);
      UPDATE xxfin_ap_pettycash_hdr_t SET error_flag = 'N',error_msg = 'Error at Invoice  = '||p_err_msg,
      LAST_UPDATE_DATE  = sysdate, 
      LAST_UPDATED_BY   = fnd_profile.value('USER_ID'),
      LAST_UPDATE_LOGIN = fnd_profile.value('LOGIN_ID')
      WHERE PTYCSH_HDR_ID = P_PTYCSH_HDR_ID;
      COMMIT;

--     fnd_file.Put_line (fnd_file.log,'Exception in INTERFACE_INV = '||SQLERRM);
     fnd_file.put_line (fnd_file.LOG,'p_err_code in INTERFACE_INV = '||p_err_code);
     fnd_file.put_line (fnd_file.LOG,'p_err_msg in INTERFACE_INV = '||p_err_msg);

END xxfin_interface_inv;

PROCEDURE xxfin_create_inv_api( p_group_id                  IN       NUMBER,
                                p_org_id                    IN       NUMBER,
                                p_err_code                  out      VARCHAR2,
                                p_err_msg                   out      VARCHAR2) AS



--    P_GROUP_ID            VARCHAR2(240) := '718793';
    l_sub_conc_request_id  NUMBER := 0;
    l_rphase               VARCHAR2(240);
    l_rstatus              VARCHAR2(240);
    l_dphase               VARCHAR2(240) := 'X';
    l_dstatus              VARCHAR2(240);
    l_msg_data             VARCHAR2(2000);
    l_complete constant VARCHAR2 (10) := 'COMPLETE';
    l_pos                  VARCHAR2(240);
    l_wait_status          boolean;
    l_request_status       boolean;

 BEGIN

  BEGIN
   p_err_code     := '0';
   p_err_msg      := 'SUCCESS';

  mo_global.set_policy_context('S',p_org_id);
--  fnd_global.Apps_initialize(user_id => 18113, resp_id => 50264,resp_appl_id => 200);
--      fnd_global.Apps_initialize(user_id => FND_GLOBAL.USER_ID, resp_id => 50354,resp_appl_id => 800);
    fnd_global.apps_initialize(user_id => fnd_global.user_id, resp_id => fnd_global.resp_id,resp_appl_id => fnd_global.resp_appl_id);

 l_sub_conc_request_id := fnd_request.submit_request( application   => 'SQLAP',
                                                      PROGRAM       => 'APXIIMPT',
                                                      start_time    => SYSDATE,
                                                      sub_request   => FALSE,
                                                      argument1     => p_org_id,
                                                      argument2     => 'MANUAL INVOICE ENTRY'
                                                     ,argument3     => p_group_id
                                                     ,argument4     => NULL
                                                     ,argument5     => NULL
                                                     ,argument6     => NULL
                                                     ,argument7     => NULL
                                                     ,argument8     => 'Y');


      COMMIT;
  exception
      WHEN others THEN
         p_err_code := sqlcode;
         p_err_msg := substr (sqlerrm, 1, 2000);

  END;

     dbms_output.put_line('l_sub_conc_request_id = '||l_sub_conc_request_id);
     fnd_file.put_line (fnd_file.LOG,'Invoice l_sub_conc_request_id = '||l_sub_conc_request_id);

    IF l_sub_conc_request_id > 0 THEN
      loop
          --
          --To make process execution to wait for 1st program to complete
          --
          l_request_status := fnd_concurrent.wait_for_request (
                                                                request_id => l_sub_conc_request_id,
                                                                INTERVAL   => 2,
                                                                max_wait   => 60,--in seconds
                                                                phase      => l_rphase,
                                                                status     => l_rstatus,
                                                                dev_phase  => l_dphase,
                                                                dev_status => l_dstatus,
                                                                message    => l_msg_data);

          exit WHEN upper (l_dphase) = 'COMPLETE' OR upper (l_dstatus) IN ( 'CANCELLED', 'ERROR', 'TERMINATED' );
      END loop;
    END IF;

 dbms_output.put_line('l_dphase  = '||l_dphase);
 dbms_output.put_line('l_dstatus = '||l_dstatus);

    IF ( l_sub_conc_request_id > 0 ) THEN

      IF upper (l_dphase) = 'COMPLETE' AND upper (l_dstatus) = 'ERROR' THEN
         p_err_code := sqlcode;
         p_err_msg  := sqlerrm;

         dbms_output.put_line('************The Invoice Import completed in error in If. Oracle request id: '
                              ||l_sub_conc_request_id
                              ||' '
                              ||sqlerrm);
        fnd_file.put_line (fnd_file.LOG,'************The Invoice Import completed in error in If. Oracle request id: '
                              ||l_sub_conc_request_id
                              ||' '
                              ||sqlerrm);


    elsif upper (l_dphase) = 'COMPLETE'  AND upper (l_dstatus) = 'NORMAL' THEN


         dbms_output.put_line('************The Invoice Import completed in Success in Else if. Oracle request id: '
                              ||l_sub_conc_request_id
                              ||' '
                              ||sqlerrm);
         fnd_file.put_line (fnd_file.LOG, '************The Invoice Import completed in Success in Else if. Oracle request id: '
                              ||l_sub_conc_request_id
                              ||' '
                              ||sqlerrm);
    END IF;

    END IF;

  exception
      WHEN others THEN
         p_err_code := sqlcode;
         p_err_msg  := substr (sqlerrm, 1, 2000);
        dbms_output.put_line ('Exception in CREATE_INV_API = '||sqlerrm);
     fnd_file.put_line (fnd_file.LOG,'p_err_code in CREATE_INV_API = '||p_err_code);
     fnd_file.put_line (fnd_file.LOG,'p_err_msg in CREATE_INV_API = '||p_err_msg);

END XXFIN_create_inv_api;

PROCEDURE invoice_process   ( errbuff                     out  nocopy  VARCHAR2,
                              retcode                     out  nocopy  VARCHAR2,
                              p_req_num                   IN       VARCHAR2) AS


l_po_header_id    NUMBER;

CURSOR c1 IS
SELECT * FROM xxfin_ap_pettycash_hdr_v
WHERE request_number = p_req_num
AND    status = 'Approved';


BEGIN

fnd_file.put_line (fnd_file.LOG,'Inside INVOICE P2P= '||p_req_num);
dbms_output.put_line ('Inside INVOICE P2P= '||p_req_num);

    FOR c IN c1
    loop

        dbms_output.put_line ('Inside loop= '||c.PTYCSH_HDR_ID);
--         update_conc_id ('XXTS_INVOICE_P2P', p_req_num);
          XXFIN_STAFF_IOU_UTIL_PKG.xxfin_interface_inv(
            P_PTYCSH_HDR_ID => c.PTYCSH_HDR_ID,
            p_err_code     => retcode,
            p_err_msg      => errbuff
          );

    END loop;

  exception
      WHEN others THEN
         retcode := sqlcode;
         errbuff := substr (sqlerrm, 1, 2000);

END  invoice_process;

procedure create_attachment(P_PTYCSH_HDR_ID in varchar2 , p_invoice_num in varchar2,
                            P_ORG_ID        in NUMBER,
                            errbuff                     out  nocopy  VARCHAR2,
                            retcode                     out  nocopy  VARCHAR2) as

l_rowid ROWID;
l_attached_document_id NUMBER;
l_document_id          NUMBER;
l_new_file_id          NUMBER;
l_seq_num              NUMBER;

l_entity_name   VARCHAR2(100) ;
l_category_name VARCHAR2(100) ;

cursor cur_head is
select h.PTYCSH_HDR_ID, h.REQUEST_NUMBER , l.PTYCSH_DTL_ID , apa.invoice_id
from xxfin_ap_pettycash_hdr_v h , xxfin_ap_pettycash_dtl_v l , ap_invoices_all apa
where h.PTYCSH_HDR_ID = l.PTYCSH_HDR_ID
and   h.request_number = apa.invoice_num
and   apa.org_id       = P_ORG_ID
and   h.PTYCSH_HDR_ID  = P_PTYCSH_HDR_ID;


cursor cur_det(P_PTYCSH_DTL_ID in number) is
 select fl.file_id,fl.file_name, fl.file_content_type, SYSDATE,
        NULL, fl.program_name, fl.program_tag, fl.file_data,
        fl.LANGUAGE, fl.oracle_charset, fl.file_format  
       ,(fl.file_name) file_name_new,
       
       fd.created_by , fd.DATATYPE_ID , fd.SECURITY_ID, 
       fd.PUBLISH_FLAG, fd.CATEGORY_ID, fd.SECURITY_TYPE, 
       fd.USAGE_TYPE, fd.last_update_login, fd.last_updated_by, fad.AUTOMATICALLY_ADDED_FLAG
       
 from fnd_attached_documents fad  
     ,fnd_documents fd  
     ,fnd_lobs fl
     ,fnd_document_datatypes fdd
 where fad.document_id = fd.document_id  
 and   fd.media_id     = fl.file_id  
 and   fd.datatype_id  = fdd.datatype_id
 and   fdd.user_name   = 'File'  
 and   fdd.language = 'US'
 and   fad.entity_name = 'XXFIN_PTYCSH_LINE' 
 and   fad.pk1_value= P_PTYCSH_DTL_ID;


begin

-- fnd_global.Apps_initialize(user_id => FND_GLOBAL.USER_ID, resp_id => 50354,resp_appl_id => 800);

for h in cur_head loop

dbms_output.put_line ('Inside create_attachment = '||h.PTYCSH_DTL_ID);

      for c in cur_det(h.PTYCSH_DTL_ID) loop
      
      dbms_output.put_line ('Inside create_attachment c.file_id = '||c.file_id);
      
      SELECT MAX (file_id) + 1 
      into l_new_file_id 
      FROM fnd_lobs;
      
      
        INSERT INTO fnd_lobs
        (file_id, file_name, file_content_type, upload_date,
        expiration_date, program_name, program_tag, file_data,
        LANGUAGE, oracle_charset, file_format
        )
        (SELECT l_new_file_id, file_name, file_content_type, SYSDATE,
                NULL, program_name, program_tag, file_data,
                LANGUAGE, oracle_charset, file_format
                
                from fnd_lobs where file_id = c.file_id
        );
      
      
      SELECT apps.fnd_documents_s.NEXTVAL,
             apps.fnd_attached_documents_s.NEXTVAL
      INTO l_document_id,
           l_attached_document_id
      FROM DUAL;
      
      dbms_output.put_line ('Inside create_attachment l_document_id = '||l_document_id);
      dbms_output.put_line ('Inside create_attachment l_attached_document_id = '||l_attached_document_id);
      
      
      SELECT NVL (MAX (seq_num), 0) + 10
      INTO   l_seq_num
      FROM   fnd_attached_documents
      WHERE  pk1_value = h.invoice_id AND entity_name = 'AP_INVOICES';
      
      dbms_output.put_line ('Inside create_attachment l_seq_num = '||l_seq_num);
      
      fnd_documents_pkg.insert_row
      (x_rowid => l_rowid,
      x_document_id => l_document_id,
      x_creation_date => SYSDATE,
      x_created_by => fnd_profile.value('USER_ID'), --c.created_by,
      x_last_update_date => SYSDATE,
      x_last_updated_by => fnd_profile.value('USER_ID'), --c.last_updated_by
      x_last_update_login => fnd_profile.value('LOGIN_ID'),--c.last_update_login,
      x_datatype_id => c.DATATYPE_ID, -- FILE
      X_security_id => 2024,--c.SECURITY_ID,
      x_publish_flag => c.PUBLISH_FLAG,
      x_category_id => c.CATEGORY_ID,
      x_security_type => 2,--c.SECURITY_TYPE,
      x_usage_type => 'O',--c.USAGE_TYPE,
      x_language => 'US',
      x_description => c.file_name_new,--l_description,
      x_file_name => c.file_name_new,
      x_media_id => l_new_file_id
      );
      commit;
      
      fnd_documents_pkg.insert_tl_row
      (x_document_id => l_document_id,
      x_creation_date => SYSDATE,
      x_created_by => fnd_profile.VALUE('USER_ID'),
      x_last_update_date => SYSDATE,
      x_last_updated_by => fnd_profile.VALUE('USER_ID'),
      x_last_update_login => fnd_profile.value('LOGIN_ID'),--c.last_update_login,
      x_language => 'US',
      x_description =>  c.file_name--l_description
      );
      COMMIT;
      
      
        
        fnd_attached_documents_pkg.insert_row
        (x_rowid => l_rowid,
        x_attached_document_id => l_attached_document_id,
        x_document_id => l_document_id,
        x_creation_date => SYSDATE,
        x_created_by => fnd_profile.VALUE('USER_ID'),--c.created_by,
        x_last_update_date => SYSDATE,
        x_last_updated_by => fnd_profile.VALUE('USER_ID'),--c.last_updated_by,
        x_last_update_login => fnd_profile.value('LOGIN_ID'),--c.last_update_login,
        x_seq_num => l_seq_num,
        x_entity_name => 'AP_INVOICES',
        x_column1 => NULL,
        x_pk1_value => h.invoice_id,
        x_pk2_value => NULL,
        x_pk3_value => NULL,
        x_pk4_value => NULL,
        x_pk5_value => NULL,
        x_automatically_added_flag => c.automatically_added_flag,
        x_datatype_id => c.datatype_id,
        x_category_id => c.category_id,
        x_security_type => 2,--c.SECURITY_TYPE,
        X_security_id => 2024,--c.security_id,
        x_publish_flag => 'Y',
        x_language => 'US',
        x_description => c.file_name,--l_description,
        x_file_name => c.file_name,
        x_media_id => l_new_file_id
        );
      
      
      commit;
        DBMS_OUTPUT.put_line ('MEDIA ID CREATED IS ' || l_new_file_id);
      
      
      
        end loop;
  
  end loop;
  
  exception when others then
         retcode := sqlcode;
         errbuff := substr (sqlerrm, 1, 2000);
  DBMS_OUTPUT.put_line ('retcode = ' || retcode);
  DBMS_OUTPUT.put_line ('errbuff = ' || errbuff);
  
  end create_attachment;

end XXFIN_STAFF_IOU_UTIL_PKG;
/

 
EXIT;
EOF