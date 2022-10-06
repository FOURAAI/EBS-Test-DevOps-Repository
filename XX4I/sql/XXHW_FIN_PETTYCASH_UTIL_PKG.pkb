SET PAGESIZE 0 FEEDBACK OFF DEFINE OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500

CREATE OR REPLACE package body APPS.xxhw_fin_pettycash_util_pkg
is
	-- +===================================================================+
	-- +                      Haya Water, Muscat, OMAN                     +
	-- +===================================================================+
	-- |Author: 4iapps                                                     |
	-- |Initial Build Date: 01-Mar-2020                                    |
	-- |Source File Name: xxhw_scm_sme_cntrct_wf_pkg.pls                   |
	-- |                                                                   |
	-- |Object Name:                                                       |
	-- |Description: <Description or Purpose>                              |
	-- |                                                                   |
	-- |Dependencies:  No                                                  |
	-- |                                                                   |
	-- |Usage:  This pacakge is used in Finance Petty Cash process      |
	-- |                                                                   |
	-- |Parameters : <Required Parameters>                                 |
	-- |             <Optional Parameters>                                 |
	-- |            <Return Codes if any>                                  |
	-- |                                                                   |
	-- |                                                                   |
	-- |Modification History:                                              |
	-- |===============                                                    |
	-- |Version     Date          Author   Remarks                         |
	-- |========= ============= ========= =============================    |
	-- |0.1        01-Mar-2020   4iapps     Initial draft version           |
	-- +===================================================================+

	/*============================================================================
	Procedure     : start_process 
	Called by     : This procedure will call from OAF custom page of
						 SME Contract Workflow
	Description   : This procedure is being used to launch the workflow for
						 SME Contract Workflow
	=============================================================================*/
function PTYCSH_IS_FUND_AVAIL (
p_ptycsh_dtl_id in number  
    )
    return varchar2 
    IS
    p_amount             NUMBER;
    p_date               DATE;
    p_module             VARCHAR2(20);
    PRAGMA AUTONOMOUS_TRANSACTION;
    lx_budget                     NUMBER;
    lx_encumbrance                NUMBER;
    lx_actual                     NUMBER;
    lx_funds_available            NUMBER;
    lx_req_encumbrance_amount     NUMBER;
    lx_po_encumbrance_amount      NUMBER;
    lx_other_encumbrance_amount   NUMBER;
    l_conversion_rate             NUMBER;
    l_encumbrance_type_id         NUMBER;
    l_closing_status              VARCHAR2 (2);
    l_set_of_books_id             NUMBER;
    l_period_name                 VARCHAR2 (30);
    l_period_set_name             VARCHAR2 (50);
    l_period_type                 VARCHAR2 (30);
    l_period_num                  VARCHAR2 (30);
    l_quarter_num                 VARCHAR2 (30);
    l_period_year                 VARCHAR2 (30);
    l_currency_code               VARCHAR2 (10);
    l_budget_version_id           NUMBER;
    l_code_combination_id         NUMBER;
    lx_funds_available_usd        NUMBER;
    ln_fund_exclusion             NUMBER;
    p_end_date                    DATE;
    expense_exception             EXCEPTION;
    l_rei_curr_code               VARCHAR2 (10);
    ln_period_year                gl_periods.period_year%TYPE;
    lc_period_name                gl_periods.period_year%TYPE;

    x_msg_status                  VARCHAR2 (1000) := NULL;
    x_msg_data                    VARCHAR2 (100);
    p_ledger_id                   NUMBER := 2024;       --Haya Ledger
    p_rei_curr_code               VARCHAR2 (10) := 'OMR';
    l_req_encumbrance_id          NUMBER;
    l_po_encumbrance_id           NUMBER;
    l_oblig_revresal_amount       NUMBER := 0;
    l_oblig_revresal_enucm_id     NUMBER := NULL;
    lx_funds_available_temp       NUMBER := 0;

cursor c_get_dtl(ln_ptycsh_dtl_id in number)
is 
select * from XXFIN_AP_PETTYCASH_DTL_V where PTYCSH_DTL_ID =  ln_ptycsh_dtl_id;



    CURSOR get_period_year IS
        SELECT period_year
          FROM gl_periods
         WHERE p_date BETWEEN start_date AND end_date;

    CURSOR get_last_period IS
          SELECT gp.period_type,
                 gsob.period_set_name,
                 gp.period_name,
                 gp.period_num,
                 gp.quarter_num,
                 gp.period_year,
                 gp.start_date,
                 gp.end_date,
                 gp.year_start_date
            FROM gl_sets_of_books gsob, gl_periods gp
           WHERE     gsob.period_set_name = gp.period_set_name
                 AND gp.period_year = ln_period_year
                 AND gsob.set_of_books_id = p_ledger_id
                 AND adjustment_period_flag != 'Y'
        ORDER BY period_num;

    lcu_get_last_period           get_last_period%ROWTYPE;
BEGIN

for i in c_get_dtl(p_ptycsh_dtl_id) loop
  l_code_combination_id    := i.CC_ID;
    p_amount      := i.AMOUNT_IN_OMR;
    p_date        := sysdate;
end loop;

if(l_code_combination_id is not null)
then 


    DBMS_OUTPUT.put_line ('p_amount is :' || p_amount);

    DBMS_OUTPUT.put_line ('Code combination id is ' || l_code_combination_id);
    DBMS_OUTPUT.put_line ('Set of books id is :' || p_ledger_id);

    BEGIN
        SELECT 1
          INTO ln_fund_exclusion
          FROM gl_budget_assignments
         WHERE ledger_id = p_ledger_id                     --l_set_of_books_id
                                       AND code_combination_id = l_code_combination_id --AND amount_type = 'YTD'
                                            --AND funds_check_level_code = 'B'
        ;
    EXCEPTION
        WHEN NO_DATA_FOUND
        THEN
            ln_fund_exclusion := 0;
        WHEN OTHERS
        THEN
            ln_fund_exclusion := 0;
    END;

    DBMS_OUTPUT.put_line ('Fund Exclusion:' || ln_fund_exclusion);

--    IF NVL (ln_fund_exclusion, 0) = 1
--    THEN
        BEGIN
            SELECT period_name,
                   period_set_name,
                   period_type,
                   period_num,
                   quarter_num,
                   period_year
              INTO l_period_name,
                   l_period_set_name,
                   l_period_type,
                   l_period_num,
                   l_quarter_num,
                   l_period_year
              FROM gl_periods
             WHERE period_name = TO_CHAR (p_date, 'MON-RR');
        EXCEPTION
            WHEN OTHERS
            THEN
                x_msg_status := x_msg_status || 'ERR:Period';
        --RAISE expense_exception;
        END;
  DBMS_OUTPUT.put_line ('l_period_name:' || l_period_name ||' l_period_set_name '||l_period_set_name);
        BEGIN
            SELECT currency_code
              INTO l_currency_code
              FROM gl_sets_of_books
             WHERE set_of_books_id = NVL (p_ledger_id, 2024);
        EXCEPTION
            WHEN OTHERS
            THEN
                l_currency_code := 'OMR';
        END;
  DBMS_OUTPUT.put_line ('l_currency_code:' || l_currency_code );
        BEGIN
            SELECT budget_version_id
              INTO l_budget_version_id
              FROM gl_budgets_with_dates_v
             WHERE     set_of_books_id = NVL (p_ledger_id, 2024)
                   AND status != 'R'
                   AND status = 'C'
                   -- Status 'C' denotes the Current Active Budget
                   AND p_date BETWEEN start_date AND end_date;
        EXCEPTION
            WHEN OTHERS
            THEN
                l_budget_version_id := 1000;                 --Approved Budget
        END;
DBMS_OUTPUT.put_line ('l_budget_version_id is ' || l_budget_version_id);
 

        OPEN get_period_year;

        FETCH get_period_year INTO ln_period_year;

        CLOSE get_period_year;

        BEGIN
            SELECT closing_status
              INTO l_closing_status
              FROM gl_period_statuses
             WHERE     period_name = l_period_name
                   AND application_id = 101
                   AND set_of_books_id = NVL (p_ledger_id, 1001);
        EXCEPTION
            WHEN OTHERS
            THEN
                x_msg_status := x_msg_status || 'ERR:Closing status';
        END;

        -- DBMS_OUTPUT.put_line('Last Period Name ' ||lcu_get_last_period.period_name);

        DBMS_OUTPUT.put_line ('Period Closing status ' || l_closing_status);


        /* l_period_type     := lcu_get_last_period.period_type;
        l_period_set_name := lcu_get_last_period.period_set_name;
        l_period_name     := lcu_get_last_period.period_name;
        l_period_num      := lcu_get_last_period.period_num;
        l_quarter_num     := lcu_get_last_period.quarter_num;
        l_period_year     := lcu_get_last_period.period_year;*/

        /* Budget Validation ends here */
        gl_funds_available_pkg.calc_funds (
            x_amount_type                => 'YTDE',
            x_code_combination_id        => l_code_combination_id,
            x_account_type               => 'A',
            x_template_id                => NULL,
            x_ledger_id                  => p_ledger_id,
            x_currency_code              => l_currency_code,
            x_po_install_flag            => 'Y',
            x_accounted_period_type      => l_period_type,
            x_period_set_name            => l_period_set_name,
            x_period_name                => l_period_name,
            x_period_num                 => l_period_num,
            x_quarter_num                => l_quarter_num,
            x_period_year                => l_period_year,
            x_closing_status             => l_closing_status,
            x_budget_version_id          => l_budget_version_id,
            x_encumbrance_type_id        => NVL (-1,  --l_encumbrance_type_id,
                                                     -1),                ---1,
            x_req_encumbrance_id         => NULL, --1000,--l_req_encumbrance_id       ,
            x_po_encumbrance_id          => NULL, --1001,--l_po_encumbrance_id,
            x_budget                     => lx_budget,
            x_encumbrance                => lx_encumbrance,
            x_actual                     => lx_actual,
            x_funds_available            => lx_funds_available_temp,
            x_req_encumbrance_amount     => lx_req_encumbrance_amount,
            x_po_encumbrance_amount      => lx_po_encumbrance_amount,
            x_other_encumbrance_amount   => lx_other_encumbrance_amount);

        DBMS_OUTPUT.put_line (
               'Amount Available (lx_funds_available_temp):'
            || lx_funds_available_temp);


        ------------------------

        DBMS_OUTPUT.put_line ('Budget amount is ' || lx_budget);

        DBMS_OUTPUT.put_line ('Encumbrance amount is ' || lx_encumbrance);

        DBMS_OUTPUT.put_line ('Actual amount is ' || lx_actual);

        DBMS_OUTPUT.put_line ('From Currency is ' || l_currency_code);

        DBMS_OUTPUT.put_line ('To Currency is ' || l_rei_curr_code);

        IF (NVL (lx_funds_available_temp, 0) < NVL (p_amount, 0))
        THEN
            
        x_msg_data := 'Over Spent';
        ELSE
        x_msg_data := 'Successful';
            
        END IF;
--    END IF;

--    px_result := x_msg_status;
--    px_fund_available := NVL (lx_funds_available_temp, 0);

else
   x_msg_data := 'Invalid Code Combination';
end if;

 return x_msg_data;
EXCEPTION
    WHEN OTHERS
    THEN
        x_msg_data := 'Insufficient Funds';
        DBMS_OUTPUT.put_line ('Error:' || SQLERRM);
        x_msg_status := 'insufficient fund3';
--        px_result := x_msg_status;
 return x_msg_data;        
END PTYCSH_IS_FUND_AVAIL;


  
  procedure create_attachment_copy
(p_old_pk1_value in varchar2, p_new_pk1_value  in varchar2)
is
l_rowid ROWID;
l_attached_document_id NUMBER;
l_document_id NUMBER;
--l_media_id NUMBER := 6185853;


l_seq_num NUMBER;

l_entity_name   VARCHAR2(100) ;
l_category_name VARCHAR2(100) ;

cursor cur_det is
 select fl.file_id,fl.file_name, fl.file_content_type, SYSDATE,
        NULL, fl.program_name, fl.program_tag, fl.file_data,
        fl.LANGUAGE, fl.oracle_charset, fl.file_format  
--       ,(fad.entity_name || '_' || fad.document_id ||'_' || fl.file_name) file_name_new,   -- Changed for File name
       ,(fl.file_name) file_name_new,
       (SELECT MAX (file_id) + 1 FROM fnd_lobs) New_file_id,
       
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
 and   fad.pk1_value= p_old_pk1_value;


begin

for c in cur_det loop

  INSERT INTO fnd_lobs
  (file_id, file_name, file_content_type, upload_date,
  expiration_date, program_name, program_tag, file_data,
  LANGUAGE, oracle_charset, file_format
  )
  (SELECT c.New_file_id, file_name, file_content_type, SYSDATE,
          NULL, program_name, program_tag, file_data,
          LANGUAGE, oracle_charset, file_format
          
          from fnd_lobs where file_id = c.file_id
  );


SELECT apps.fnd_documents_s.NEXTVAL,
       apps.fnd_attached_documents_s.NEXTVAL
INTO l_document_id,
     l_attached_document_id
FROM DUAL;



SELECT NVL (MAX (seq_num), 0) + 10
INTO   l_seq_num
FROM   fnd_attached_documents
WHERE  pk1_value = p_new_pk1_value AND entity_name = 'XXFIN_PTYCSH_LINE';


fnd_documents_pkg.insert_row
(x_rowid => l_rowid,
x_document_id => l_document_id,
x_creation_date => SYSDATE,
x_created_by => c.created_by,-- fnd_profile.value('USER_ID')
x_last_update_date => SYSDATE,
x_last_updated_by => c.last_updated_by,-- fnd_profile.value('USER_ID')
x_last_update_login => c.last_update_login,
x_datatype_id => c.DATATYPE_ID, -- FILE
X_security_id => c.SECURITY_ID,
x_publish_flag => c.PUBLISH_FLAG,
x_category_id => c.CATEGORY_ID,
x_security_type => c.SECURITY_TYPE,
x_usage_type => c.USAGE_TYPE,
x_language => 'US',
x_description => c.file_name_new,--l_description,
x_file_name => c.file_name_new,
x_media_id => c.New_file_id
);
commit;

fnd_documents_pkg.insert_tl_row
(x_document_id => l_document_id,
x_creation_date => SYSDATE,
x_created_by => c.created_by,--fnd_profile.VALUE('USER_ID'),
x_last_update_date => SYSDATE,
x_last_updated_by => c.last_updated_by,--fnd_profile.VALUE('USER_ID'),
x_last_update_login => c.last_update_login,
x_language => 'US',
x_description =>  c.file_name--l_description
);
COMMIT;


  
  fnd_attached_documents_pkg.insert_row
  (x_rowid => l_rowid,
  x_attached_document_id => l_attached_document_id,
  x_document_id => l_document_id,
  x_creation_date => SYSDATE,
  x_created_by => c.created_by,--fnd_profile.VALUE('USER_ID'),
  x_last_update_date => SYSDATE,
  x_last_updated_by => c.last_updated_by,--fnd_profile.VALUE('USER_ID'),
  x_last_update_login => c.last_update_login,
  x_seq_num => l_seq_num,
  x_entity_name => 'XXFIN_PTYCSH_LINE',
  x_column1 => NULL,
  x_pk1_value => p_new_pk1_value,
  x_pk2_value => NULL,
  x_pk3_value => NULL,
  x_pk4_value => NULL,
  x_pk5_value => NULL,
  x_automatically_added_flag => c.automatically_added_flag,
  x_datatype_id => c.datatype_id,
  x_category_id => c.category_id,
  x_security_type => c.SECURITY_TYPE,
  X_security_id => c.security_id,
  x_publish_flag => 'Y',
  x_language => 'US',
  x_description => c.file_name,--l_description,
  x_file_name => c.file_name,
  x_media_id => c.New_file_id
  );


commit;
  DBMS_OUTPUT.put_line ('MEDIA ID CREATED IS ' || c.New_file_id);



  end loop;
  
  
--  exception when others then
--         retcode := sqlcode;
--         errbuff := substr (sqlerrm, 1, 2000);
  
  
  end ;
    
  
procedure bill_attach_count (p_iou_id in varchar2, X_FLAG OUT VARCHAR2) as


l_count number;

begin

select count(*) into l_count
from fnd_attached_documents fad   ,fnd_documents fd  
     ,fnd_lobs fl,
     xxfin_ap_pettycash_dtl_v pty

where 
      fad.document_id = fd.document_id  
and   fd.media_id     = fl.file_id 
and   pty.PTYCSH_DTL_ID = fad.pk1_value
and   fad.ENTITY_NAME = 'XXFIN_PTYCSH_LINE'
and   pty.PTYCSH_DTL_ID	= p_iou_id;

if l_count =0 then 
X_FLAG :='N';

elsif l_count >0 then 
X_FLAG :='Y';
end if;


exception when others then

X_FLAG :='0';

end bill_attach_count;

function get_div_loc_conc_restric(p_resp_name in varchar2) 
return varchar2
is
lc_return_div_loc varchar2(240); 
begin

xx_debug('Getting Values of Resp for testing'||p_resp_name);
    if(p_resp_name='Haya Petty Cash HR and Admin')
    then 
    lc_return_div_loc:='ADM';
    elsif(p_resp_name='HAYA Petty Cash Al Ansab')
    then 
    lc_return_div_loc:='ANSB';
    elsif(p_resp_name='Haya Petty Cash Bousher')
    then 
    lc_return_div_loc:='BSR';
    elsif(p_resp_name='Haya Petty Cash Finance')
    then 
    lc_return_div_loc:='FIN';
    elsif(p_resp_name='Haya Petty Cash Great Mutrah')
    then 
    lc_return_div_loc:='GMTR';
    elsif(p_resp_name='Haya Petty Cash Logistics')
    then 
    lc_return_div_loc:='LOG';
    elsif(p_resp_name='Haya Petty Cash Operation')
    then 
    lc_return_div_loc:='OPS';
    elsif(p_resp_name='Haya Petty Cash Aseeb')
    then 
    lc_return_div_loc:='SEB';
    elsif(p_resp_name='Haya Petty Cash Claims Inquiry')
    then 
    lc_return_div_loc:='';
    elsif(p_resp_name='Haya Petty Cash Admin')
    then 
    lc_return_div_loc:='';
    end if;
--    
return lc_return_div_loc;
end;  


function get_eligible_amt(p_div in varchar2) 
return number
is
ln_eamt number;

begin

xx_debug('Getting Values of Resp for testing'||p_div);
if(p_div = 'Operations')
then
ln_eamt := 3000;
else
ln_eamt := 1000;
end if;
    
return ln_eamt;
end;  

 FUNCTION spell_number_omr (p_number IN NUMBER)
   RETURN VARCHAR2
AS
   TYPE myArray IS TABLE OF VARCHAR2 (255);
 
   l_str myArray
         := myArray ('',
                     'thousand',
                     'million',
                     'billion',
                     'trillion',
                     'quadrillion',
                     'quintillion',
                     'sextillion',
                     'septillion',
                     'octillion',
                     'nonillion',
                     'decillion',
                     'undecillion',
                     'duodecillion');
   l_str_dec myArray
         := myArray ('',
                     'ten-',
                     'hundred-');
 
   l_num      VARCHAR2 (50) DEFAULT TRUNC (p_number);
   l_return   VARCHAR2 (4000);
   l_decimal  NUMBER DEFAULT (p_number - l_num);
   l_len_decimal NUMBER DEFAULT LENGTH(l_decimal)-1;
BEGIN
   FOR i IN 1 .. l_str.COUNT
   LOOP
      EXIT WHEN l_num IS NULL;
 
      IF (SUBSTR (l_num, LENGTH (l_num) - 2, 3) <> 0)
      THEN
         l_return :=
            TO_CHAR (TO_DATE (SUBSTR (l_num, LENGTH (l_num) - 2, 3), 'J'),
                     'Jsp')
            || ' ' || l_str (i)
            || CASE WHEN i!=1 THEN ' ' ELSE '' END
            || l_return;
            l_return :=l_return||'Rials ';
      END IF;
 
      l_num := SUBSTR (l_num, 1, LENGTH (l_num) - 3);
   END LOOP;
   --
   IF l_decimal !=0
   THEN
      l_return := l_return
                   || 'and '
                   || xx_spell_number((l_decimal*POWER(10, l_len_decimal)))
                   || CASE
                        WHEN (l_len_decimal < 3) THEN l_str_dec(l_len_decimal)
                        ELSE l_str_dec(MOD(l_len_decimal,3)+1) || l_str(TRUNC(l_len_decimal/3)+1)
                      END  
                   || ' Baisas Only';
   END IF;
   --
   RETURN l_return;
END;


end xxhw_fin_pettycash_util_pkg;
/
 
EXIT;
EOF