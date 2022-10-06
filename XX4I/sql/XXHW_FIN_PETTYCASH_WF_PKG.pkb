SET PAGESIZE 0 FEEDBACK OFF DEFINE OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500

CREATE OR REPLACE package body APPS.xxhw_fin_pettycash_wf_pkg
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
PROCEDURE start_process (p_ptycsh_hdr_id   IN            NUMBER,
                         p_wf_item_key     in varchar2,
                         p_error_code         OUT NOCOPY NUMBER,
                         p_error_msg          OUT NOCOPY VARCHAR2)
IS
    lc_item_type            VARCHAR2 (8) := 'XXHWPTCS';                           
    lc_item_key             VARCHAR2 (240);
    lc_requested_by_uname   VARCHAR2 (240);
    
    lc_request_number      VARCHAR2 (500);
    lc_supplier_name        VARCHAR2 (500);
    lc_sme_po_number        VARCHAR2(500);
    lc_location_name        VARCHAR2(500);

    CURSOR c_get_item_owner (p_ln_ptycsh_hdr_id IN NUMBER)
    IS
        SELECT requested_by, request_number
          FROM xxfin_ap_pettycash_hdr_v
         WHERE ptycsh_hdr_id = p_ln_ptycsh_hdr_id;

    CURSOR c_get_req_by (p_ln_ptycsh_hdr_id IN NUMBER)
    IS
        SELECT requested_by, request_number, VENDOR_NAME, VENDOR_SITE_NAME, LOCATION_NAME
          FROM xxfin_ap_pettycash_hdr_v
         WHERE ptycsh_hdr_id = p_ln_ptycsh_hdr_id;
BEGIN
    p_error_code := 0;
    p_error_msg := 'COMPLETE';
    lc_item_key := p_wf_item_key;    

    -- launch workflow
    wf_engine.createprocess (lc_item_type, lc_item_key, 'PETTY_CASH_APPRVL');
--         xx_debug('Getting values  ---------------------- '||p_ptycsh_hdr_id);

    FOR i IN c_get_req_by (p_ptycsh_hdr_id)
    LOOP
        lc_requested_by_uname := i.requested_by;
        lc_request_number := i.request_number;
        lc_supplier_name := i.VENDOR_NAME;
        lc_sme_po_number := i.VENDOR_SITE_NAME;
        lc_location_name := i.LOCATION_NAME;
--        xx_debug('Getting values '||lc_requested_by_uname||lc_request_number||lc_supplier_name);
    END LOOP;

    wf_engine.setitemattrtext (
        lc_item_type,
        lc_item_key,
        'OAF_RN',
           'JSP:/OA_HTML/OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshWFRN&'|| 'PTYCSHHDRID='|| p_ptycsh_hdr_id);
        wf_engine.setitemattrtext (
        lc_item_type,
        lc_item_key,
        'OAF_RN_READONLY',
           'JSP:/OA_HTML/OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshWFRN&'|| 'PTYCSHHDRID='|| p_ptycsh_hdr_id||'&MODE='||'READDONLY');       
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               '#FROM_ROLE',
                               lc_requested_by_uname);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'REQUESTED_BY',
                               lc_requested_by_uname);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'REQUESTED_FOR',
                               lc_requested_by_uname);


    --setting the header id primary key
--    xx_debug('Setting primary key =========================== Before'||p_ptycsh_hdr_id);
    wf_engine.setitemattrnumber (lc_item_type,
                                 lc_item_key,
                                 'PTYCSH_HDR_ID',
                                 p_ptycsh_hdr_id);
--    xx_debug('Setting primary key =========================== After'||p_ptycsh_hdr_id);    
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'WF_ITEM_KEY',
                               lc_item_key);
                               
    wf_engine.setitemowner (lc_item_type, lc_item_key, lc_requested_by_uname);

    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'REQUEST_NUMBER',
                               lc_request_number);

    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'SUPPLIER_NAME',
                               lc_supplier_name);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'LOCATION_NAME',
                               lc_location_name);
                                                              
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'SME_PO_NUMBER',
                               lc_sme_po_number);

    wf_engine.startprocess (lc_item_type, lc_item_key);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               '#FROM_ROLE',
                               lc_requested_by_uname);

    UPDATE xxfin_ap_pettycash_hdr_t
       SET status = 'Approval in Progress', wf_status = 'Approval in Progress'
     WHERE PTYCSH_HDR_ID = p_ptycsh_hdr_id;

    COMMIT;
EXCEPTION
    WHEN OTHERS
    THEN
        p_error_code := SQLCODE;
        p_error_msg := SQLERRM (SQLCODE);
END start_process;


 PROCEDURE config_utilities (p_itemtype    IN            VARCHAR2,
                            p_itemkey     IN            VARCHAR2,
                            p_actid       IN            NUMBER,
                            p_funcmode    IN            VARCHAR2,
                            x_resultout      OUT NOCOPY VARCHAR2)
IS
    lc_error_msg   VARCHAR2 (2000);
    ln_req_id      NUMBER;
    ln_iteration   NUMBER;
    
    
    cursor c_get_petty_cash_rev is
        select rownum rn, appr.user_name, appr.user_role
          from (
                select name user_name, display_name user_role
                  from wf_roles
                 where display_name = 'Petty Cash Reviewer'
                ) appr;
                 
    cursor c_get_hoap is
        select rownum rn, appr.user_name, appr.user_role
          from (
                select name user_name, display_name user_role
                  from wf_roles
                 where display_name = 'Head of Accounts Payable'
                ) appr;
                
    LC_USER_NAME varchar2(500);
    LC_USER_ROLE varchar2(500);
    
    cursor c_rec_det(ln_request_id in number) is select nvl(LOCATION_NAME, DIVISION) DIV_LOC from XXFIN_AP_PETTYCASH_HDR_V where 
    PTYCSH_HDR_ID = ln_request_id;
    
BEGIN

--xx_debug('Inside Config utilites');
    ln_req_id :=
        wf_engine.getitemattrnumber (p_itemtype, p_itemkey, 'PTYCSH_HDR_ID');


    if (p_funcmode = 'RUN')
    then
        ln_iteration :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'WF_ITERATION');

        if (ln_iteration is null)
        then
            ln_iteration := 1;
            wf_engine.setitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'WF_ITERATION',
                                         ln_iteration);
        else
            ln_iteration := ln_iteration + 1;
            wf_engine.setitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'WF_ITERATION',
                                         ln_iteration);
        end if;
--xx_debug('Inside Config utilites : Before For loop c_get_petty_cash_rev');        
    for i in c_get_petty_cash_rev
        loop
--            XX_DEBUG('Inside Config utilites : Before For loop c_get_petty_cash_rev'||LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole  Getting the Number'||i.rn);
            if (ln_iteration = i.rn)
            then
               LC_USER_NAME := i.USER_NAME;
               LC_USER_ROLE := i.user_role;
--               XX_DEBUG(LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole');
                wf_engine.setitemattrtext (p_itemtype,
                                           p_itemkey,
                                           'OAF_APPROVER_UNAME',
                                           LC_USER_NAME);
                                
            end if;
    end loop;

    for i in c_get_hoap
        loop
--            XX_DEBUG('Inside Config utilites : Before For loop c_get_petty_cash_rev'||LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole  Getting the Number'||i.rn);
            if (ln_iteration = i.rn)
            then
               LC_USER_NAME := i.USER_NAME;
               LC_USER_ROLE := i.user_role;
--               XX_DEBUG(LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole');
                wf_engine.setitemattrtext (p_itemtype,
                                           p_itemkey,
                                           'OAF_FINAL_APPR_UNAME',
                                           LC_USER_NAME);
                                
            end if;
    end loop;
        
--    XX_DEBUG('Inside Config utilites : Before For loop c_rec_det'||ln_req_id);
    for i in c_rec_det(ln_req_id) loop  
--    XX_DEBUG('Inside Config utilites : Inside Loop Update Sequence i.DIV_LOC '||i.DIV_LOC);  
    update XXFIN_AP_PETTYCASH_SEQ_T set  CURRENT_RUNNING_SEQUENCE = CURRENT_RUNNING_SEQUENCE+1 where SEQ_PREFIX = i.DIV_LOC||'-PC' and sysdate between DATE_START and DATE_END;   
    
    end loop;
        
        
    end if;
exception
    when others
    then
        lc_error_msg := sqlerrm (sqlcode);
--        xx_debug ('Inside Exception in config_utilities' || lc_error_msg);
        x_resultout :=
               'ERROR: HAS OCCURED IN CONFIG PROCESS'
            || dbms_utility.format_error_stack;
end;

 
procedure get_approvers (p_itemtype    in            varchar2,
                         p_itemkey     in            varchar2,
                         p_actid       in            number,
                         p_funcmode    in            varchar2,
                         x_resultout      out nocopy varchar2)
is
    ln_req_id      number;
    lc_error_msg   varchar2 (2000);
    LC_USER_NAME   varchar2 (200);
    lc_location_name varchar2(200);

    LC_USER_ROLE   varchar2 (200);
        LN_USER_NAME         VARCHAR2 (200);
		lc_item_order_number    varchar2(20);
		L_COMPLETEYNO        VARCHAR2 (100);
		LN_ITERATION         NUMBER;
		lc_approver_order_number varchar2(20);
    
        L_NEXT_APPROVERS     AME_UTIL.APPROVERSTABLE2;  
        L_NEXT_APPROVER      AME_UTIL.APPROVERRECORD2;
        L_INDEX              AME_UTIL.IDLIST;
        L_IDS                AME_UTIL.STRINGLIST;
        L_CLASS              AME_UTIL.STRINGLIST;
        L_SOURCE             AME_UTIL.LONGSTRINGLIST;
		LCU_NEXT_APPROVER    AME_UTIL.APPROVERRECORD;
		lC_productionIndexesOut  AME_UTIL.IDLIST;
		LC_variableNamesOut  AME_UTIL.STRINGLIST;
		LC_variableValuesOut  AME_UTIL.STRINGLIST;
    
--    cursor c_get_apprs is
--        select rownum rn, appr.user_name, appr.user_role
--          from (
--                select name user_name, display_name user_role
--                  from wf_roles
--                 where display_name = 'Senior Project Engineer'
--                union
--                select name, display_name
--                  from wf_roles
--                 where display_name = 'VMS Team') appr;
begin
    if (p_funcmode = 'RUN')
    then
--    xx_debug('Inside  get_approvers  ');    
        ln_req_id :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_HDR_ID');
                              
        lc_location_name :=    wf_engine.getitemattrtext (p_itemtype,
                                         p_itemkey,
                                         'LOCATION_NAME');
--        xx_debug('Inside  get_approvers lc_location_name '||lc_location_name);                                  
        ln_iteration :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'WF_ITERATION');
--        XX_DEBUG('Getting UserRole  Getting the Iteration NO'||ln_iteration);

--        wf_engine.setitemattrtext (p_itemtype,
--                                   p_itemkey,
--                                   'WF_AME_TRANS_TYPE',
--                                   'XXHWSME_AME_WF_SERIAL');
--        for i in c_get_apprs
--        loop
--            XX_DEBUG(LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole  Getting the Number'||i.rn);
--            if (ln_iteration = i.rn)
--            then
--               LC_USER_NAME := i.USER_NAME;
--               LC_USER_ROLE := i.user_role;
--               XX_DEBUG(LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole');
--                wf_engine.setitemattrtext (p_itemtype,
--                                           p_itemkey,
--                                           'NEXT_APPROVER_USER_NAME',
--                                           LC_USER_NAME);
--                              
--
--                wf_engine.setitemattrtext (p_itemtype,
--                                           p_itemkey,
--                                           'WF_NOTE',
--                                           '');
--                update xxfin_ap_pettycash_hdr_t  set   PENDING_WITH	= LC_USER_ROLE where PTYCSH_HDR_ID = ln_req_id;
--                                
--            end if;
--        end loop;
--        ln_iteration := ln_iteration + 1;
--        wf_engine.setitemattrnumber (p_itemtype,
--                                     p_itemkey,
--                                     'WF_ITERATION',
--                                     ln_iteration);
--
--        if (LC_USER_NAME is not null)
--        then
--            x_resultout := wf_engine.eng_completed || ':' || 'Y';
--        else
--            x_resultout := wf_engine.eng_completed || ':' || 'N';
--        end if;
--    else
--        x_resultout := wf_engine.eng_completed || ':' || 'N';
--    end if;
            AME_API2.getNextApprovers2 (
                APPLICATIONIDIN                => 50100,            --select * from fnd_application_tl order by application_id desc
                TRANSACTIONTYPEIN              => 'XXHW_FIN_PETTYCASH',
                TRANSACTIONIDIN                => p_itemkey,
                FLAGAPPROVERSASNOTIFIEDIN      => AME_UTIL.BOOLEANFALSE,
                APPROVALPROCESSCOMPLETEYNOUT   => L_COMPLETEYNO,
                NEXTAPPROVERSOUT               => L_NEXT_APPROVERS,
                itemIndexesOut  => L_INDEX,
                itemClassesOut => L_CLASS,
                itemIdsOut  =>L_IDS,
                itemSourcesOut => L_SOURCE,
                productionIndexesOut => lC_productionIndexesOut,
                              variableNamesOut  => LC_variableNamesOut,
                              variableValuesOut => LC_variableValuesOut);
    
            IF L_NEXT_APPROVERS.COUNT > 0
            THEN
                FOR I IN 1 .. L_NEXT_APPROVERS.COUNT
                LOOP
--                    xx_debug('Inside  L_NEXT_APPROVERS.COUNT > 0'||LN_REQ_ID);
                    LN_USER_NAME := L_NEXT_APPROVERS (I).NAME;
                    lc_item_order_number := L_NEXT_APPROVERS (I).item_order_number;
                    lc_approver_order_number := L_NEXT_APPROVERS (I).approver_order_number;
 
--                    xx_debug('Inside  L_NEXT_APPROVERS.COUNT > 0  L_NEXT_APPROVERS ============================== '||I||' LN_USER_NAME'||LN_USER_NAME||'  lc_item_order_number '||lc_item_order_number||' lc_approver_order_number : '||lc_approver_order_number);
                    WF_ENGINE.SETITEMATTRTEXT (P_ITEMTYPE,
                                               P_ITEMKEY,
                                               'NEXT_APPROVER_USER_NAME',
                                               LN_USER_NAME);
                    update xxfin_ap_pettycash_hdr_t set   PENDING_WITH = LN_USER_NAME, STATUS = 'Approval in Progress' where ptycsh_hdr_id = LN_REQ_ID;                      
--       xx_debug('Inside  L_NEXT_APPROVERS.COUNT > 0 LN_USER_NAME'||LN_USER_NAME);                
                                

                                               
 
 
                    WF_ENGINE.SETITEMATTRTEXT (P_ITEMTYPE,
                                               P_ITEMKEY,
                                               'WF_AME_TRANS_TYPE',
                                               'XXHW_FIN_PETTYCASH');
--                                                xx_debug('Inside  L_NEXT_APPROVERS.COUNT > 0 WF_AME_TRANS_TYPE');
                    LN_ITERATION :=
                        WF_ENGINE.GETITEMATTRNUMBER (P_ITEMTYPE,
                                                     P_ITEMKEY,
                                                     'WF_ITERATION');
--                                                     xx_debug('Inside  L_NEXT_APPROVERS.COUNT > 0 WF_ITERATION'||LN_ITERATION);



                END LOOP;
--                        xx_debug('Inside  L_NEXT_APPROVERS.COUNT > 0 End Loops');
--                            xx_debug('Inside  WF_ENGINE.ENG_COMPLETED || Y');
                X_RESULTOUT := WF_ENGINE.ENG_COMPLETED || ':' || 'Y';
            ELSE
            if(lc_location_name is null)
            then
--              xx_debug('Inside  WF_ENGINE.ENG_COMPLETED || N');
                X_RESULTOUT := WF_ENGINE.ENG_COMPLETED || ':' || 'N';
            else
--                xx_debug('Inside  WF_ENGINE.ENG_COMPLETED || CLOSE');
                X_RESULTOUT := WF_ENGINE.ENG_COMPLETED || ':' || 'CLOSE';
            end if;
            END IF;
    end if;

exception
    when others
    then
        lc_error_msg := sqlerrm (sqlcode);
--        xx_debug ('Inside Exception in get_approvers' || lc_error_msg);
end;



procedure insert_history (p_itemtype    in            varchar2,
                          p_itemkey     in            varchar2,
                          p_actid       in            number,
                          p_funcmode    in            varchar2,
                          x_resultout      out nocopy varchar2)
is
    lc_error_msg           varchar2 (2000);
    ln_ptycsh_wf_hist_id   number := xxfin_ap_pettycash_wf_s.nextval ();
    ln_PTYCSH_HDR_ID       number;
    lc_wf_item_key         varchar2 (240);
    lc_appr_user_name      varchar2 (240);
    ln_user_id             number;
begin
    if (p_funcmode = 'RUN')
    then
--        xx_debug('Inside  Insert History');
        ln_PTYCSH_HDR_ID :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_HDR_ID');
        lc_wf_item_key :=
            wf_engine.getitemattrtext (p_itemtype, p_itemkey, 'WF_ITEM_KEY');
        lc_appr_user_name :=
            wf_engine.getitemattrtext (p_itemtype,
                                       p_itemkey,
                                       'NEXT_APPROVER_USER_NAME');


        if (lc_appr_user_name is not null)
        then
            wf_engine.setitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_WF_HIST_ID',
                                         ln_ptycsh_wf_hist_id);
        end if;
        
--        xx_debug('Before Insertion of Insert History');

        WF_ENGINE.SETITEMATTRTEXT (P_ITEMTYPE, P_ITEMKEY, 'WF_NOTE', '');

        insert into xxfin_ap_pettycash_wf_t (ptycsh_wf_hist_id,
                                          ptycsh_hdr_id,
                                          ptycsh_dtl_id,
                                          wf_item_key,
                                          response,
                                          approver_comments,
                                          approver_id,
                                          approver_user_name,
                                          response_date,
                                          submission_date,
                                          iteration,
                                          remarks,
                                          created_by,
                                          creation_date,
                                          last_updated_by,
                                          last_update_date,
                                          last_update_login)
             values (ln_ptycsh_wf_hist_id,                 --ptycsh_wf_hist_id
                     ln_PTYCSH_HDR_ID,                         --ptycsh_hdr_id
                     null,                                         --ptycsh_dtl_id
                     lc_wf_item_key,                             --WF_ITEM_KEY
                     null,                                          --RESPONSE
                     null,                                 --APPROVER_COMMENTS
                     ln_user_id,                                 --APPROVER_ID
                     lc_appr_user_name,                   --APPROVER_USER_NAME
                     null,                                     --RESPONSE_DATE
                     sysdate + 1 / (24 * 60 * 60),           --SUBMISSION_DATE
                     null,                                         --ITERATION
                     null,                                           --REMARKS
                     0,                                           --CREATED_BY
                     sysdate,                                  --CREATION_DATE
                     0,                                      --LAST_UPDATED_BY
                     sysdate,                              -- LAST_UPDATE_DATE
                     0                                     --LAST_UPDATE_LOGIN
                      );
                      
--                      xx_debug('After Insertion of Insert History');
    end if;
exception
    when others
    then
    
        lc_error_msg := sqlerrm (sqlcode);
--                xx_debug (
--               'Inside Exception in Insert_history'
--            || lc_error_msg
--            || '|| DBMS_UTILITY.FORMAT_ERROR_STACK'
--            || DBMS_UTILITY.format_error_stack);
end;


procedure insert_oaf_history (p_itemtype    in            varchar2,
                          p_itemkey     in            varchar2,
                          p_actid       in            number,
                          p_funcmode    in            varchar2,
                          x_resultout      out nocopy varchar2)
is
    lc_error_msg           varchar2 (2000);
    ln_ptycsh_wf_hist_id   number := xxfin_ap_pettycash_wf_s.nextval ();
    ln_PTYCSH_HDR_ID       number;
    lc_wf_item_key         varchar2 (240);
    lc_appr_user_name      varchar2 (240);
    ln_user_id             number;
    lc_get_resp             varchar2(240);
    lc_OAF_APPROVER_UNAME_ACTED varchar2(240);
begin
    if (p_funcmode = 'RUN')
    then
--        xx_debug('Inside  Insert History');
        ln_PTYCSH_HDR_ID :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_HDR_ID');
        lc_wf_item_key :=
            wf_engine.getitemattrtext (p_itemtype, p_itemkey, 'WF_ITEM_KEY');
            
        lc_OAF_APPROVER_UNAME_ACTED :=
            wf_engine.getitemattrtext (p_itemtype,
                               p_itemkey,
                               'OAF_APPROVER_UNAME_ACTED');   
            
        if(lc_OAF_APPROVER_UNAME_ACTED is null)
        then
--        xx_debug('Inside Insert_OAF_History Logic to insert uname lc_appr_user_name'||lc_appr_user_name);
            lc_appr_user_name :=
            wf_engine.getitemattrtext (p_itemtype,
                               p_itemkey,
                               'OAF_APPROVER_UNAME');
        else
            lc_appr_user_name :=
            wf_engine.getitemattrtext (p_itemtype,
                               p_itemkey,
                               'OAF_FINAL_APPR_UNAME');
        end if;                                


        if (lc_appr_user_name is not null)
        then
            wf_engine.setitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_WF_HIST_ID',
                                         ln_ptycsh_wf_hist_id);
        end if;
        
        
        
--        xx_debug('Before Insertion of Insert History');
        
        WF_ENGINE.SETITEMATTRTEXT (P_ITEMTYPE, P_ITEMKEY, 'WF_NOTE', '');

        insert into xxfin_ap_pettycash_wf_t (ptycsh_wf_hist_id,
                                          ptycsh_hdr_id,
                                          ptycsh_dtl_id,
                                          wf_item_key,
                                          response,
                                          approver_comments,
                                          approver_id,
                                          approver_user_name,
                                          response_date,
                                          submission_date,
                                          iteration,
                                          remarks,
                                          created_by,
                                          creation_date,
                                          last_updated_by,
                                          last_update_date,
                                          last_update_login)
             values (ln_ptycsh_wf_hist_id,                 --ptycsh_wf_hist_id
                     ln_PTYCSH_HDR_ID,                         --ptycsh_hdr_id
                     null,                                         --ptycsh_dtl_id
                     lc_wf_item_key,                             --WF_ITEM_KEY
                     null,                                          --RESPONSE
                     null,                                 --APPROVER_COMMENTS
                     ln_user_id,                                 --APPROVER_ID
                     lc_appr_user_name,                   --APPROVER_USER_NAME
                     null,                                     --RESPONSE_DATE
                     sysdate + 1 / (24 * 60 * 60),           --SUBMISSION_DATE
                     null,                                         --ITERATION
                     null,                                           --REMARKS
                     0,                                           --CREATED_BY
                     sysdate,                                  --CREATION_DATE
                     0,                                      --LAST_UPDATED_BY
                     sysdate,                              -- LAST_UPDATE_DATE
                     0                                     --LAST_UPDATE_LOGIN
                      );
            
            wf_engine.setitemattrtext (p_itemtype,
                               p_itemkey,
                               'OAF_APPROVER_UNAME_ACTED',
                               'YES');   

--                      xx_debug('After Insertion of Insert History');
    end if;
exception
    when others
    then
    
        lc_error_msg := sqlerrm (sqlcode);
--                xx_debug (
--               'Inside Exception in Insert_history'
--            || lc_error_msg
--            || '|| DBMS_UTILITY.FORMAT_ERROR_STACK'
--            || DBMS_UTILITY.format_error_stack);
end;


     
 procedure reason_not_null_validate (p_itemtype    in            varchar2,
                                    p_itemkey     in            varchar2,
                                    p_actid       in            number,
                                    p_funcmode    in            varchar2,
                                    x_resultout      out nocopy varchar2)
is
    lc_error_msg      varchar2 (2000);
    lc_get_comments   varchar2 (2400);
    l_nid             number;
    v_result          varchar2 (100);
    v_role_name       varchar2 (500);
    lc_get_resp       varchar2 (2000);
    p_ptycsh_hdr_id   number;
begin
--    if (p_funcmode = 'RESPOND')
--    then
--        begin
--            select usr.user_name
--              into v_role_name
--              from fnd_user usr
--             where usr.user_id = fnd_profile.value ('USER_ID');
--
--          
--        exception
--            when no_data_found
--            then
--                v_role_name := 'SYSADMIN';
--            when others
--            then
--                v_role_name := 'SYSADMIN';
--        end;
--
--        wf_engine.setitemattrtext (itemtype   => p_itemtype,
--                                   itemkey    => p_itemkey,
--                                   aname      => '#FROM_ROLE',
--                                   avalue     => v_role_name);
--        wf_engine.setitemattrtext (itemtype   => p_itemtype,
--                                   itemkey    => p_itemkey,
--                                   aname      => 'NEXT_APPROVER_USERNAME',
--                                   avalue     => v_role_name);
--        wf_engine.setitemattrnumber (
--            itemtype   => p_itemtype,
--            itemkey    => p_itemkey,
--            aname      => 'NEXT_APPROVER_USER_ID',
--            avalue     => fnd_profile.value ('USER_ID'));
--    end if;

--    if (p_funcmode = 'RUN')
--    then
--        begin
--            select usr.user_name
--              into v_role_name
--              from fnd_user usr
--             where usr.user_id = fnd_profile.value ('USER_ID');
--
--         
--        exception
--            when no_data_found
--            then
--                v_role_name := 'SYSADMIN';
--            when others
--            then
--                v_role_name := 'SYSADMIN';
--        end;
--
--        wf_engine.setitemattrtext (itemtype   => p_itemtype,
--                                   itemkey    => p_itemkey,
--                                   aname      => '#FROM_ROLE',
--                                   avalue     => v_role_name);
--    end if;

if ( p_funcmode = 'ANSWER' ) then
--         xx_debug('Inside More Information ANSWER to be Tested Check==================> Before Setting ');
          p_ptycsh_hdr_id :=   wf_engine.getitemattrnumber (p_itemtype,
                                 p_itemkey,
                                 'PTYCSH_HDR_ID'
                                 );
--          wf_engine.setitemattrtext (
--        p_itemtype,
--        p_itemkey,
--        'OAF_RN_READONLY',
--          'JSP:/OA_HTML/OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshWFRN&'|| 'PTYCSHHDRID='|| p_ptycsh_hdr_id);
        update XXFIN_AP_PETTYCASH_DTL_t set ATTACH_RO_UPD = 'ROAttach' where ptycsh_hdr_id = p_ptycsh_hdr_id;

--          xx_debug('Inside More Information  ANSWER to be Tested Check==================> After Setting ');  
       X_RESULTOUT := 'COMPLETE';
--        X_RESULTOUT := WF_ENGINE.ENG_COMPLETED || ':' || 'Y';
       return;
     end if;


if ( p_funcmode = 'QUESTION' ) then
--         xx_debug('Inside More Information QUESTION to be Tested Check==================> Before Setting ');
          p_ptycsh_hdr_id :=   wf_engine.getitemattrnumber (p_itemtype,
                                 p_itemkey,
                                 'PTYCSH_HDR_ID'
                                 );
--          wf_engine.setitemattrtext (
--        p_itemtype,
--        p_itemkey,
--        'OAF_RN_READONLY',
--          'JSP:/OA_HTML/OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshWFRN&'|| 'PTYCSHHDRID='|| p_ptycsh_hdr_id);
        update XXFIN_AP_PETTYCASH_DTL_t set ATTACH_RO_UPD = 'UpdAttach' where ptycsh_hdr_id = p_ptycsh_hdr_id;

--          xx_debug('Inside More Information QUESTION to be Tested Check==================> After Setting ');  
       X_RESULTOUT := 'COMPLETE';
--        X_RESULTOUT := WF_ENGINE.ENG_COMPLETED || ':' || 'Y';
       return;
     end if;
     
--     if ( p_funcmode = 'RUN' ) then
--         xx_debug('Inside More Information RESPOND to be Tested Check==================> Before Setting ');
--          p_ptycsh_hdr_id :=   wf_engine.getitemattrnumber (p_itemtype,
--                                 p_itemkey,
--                                 'PTYCSH_HDR_ID'
--                                 );
----          wf_engine.setitemattrtext (
----        p_itemtype,
----        p_itemkey,
----        'OAF_RN_READONLY',
----          'JSP:/OA_HTML/OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshWFRN&'|| 'PTYCSHHDRID='|| p_ptycsh_hdr_id);
--        update XXFIN_AP_PETTYCASH_DTL_t set ATTACH_RO_UPD = 'ROAttach' where ptycsh_hdr_id = p_ptycsh_hdr_id;
--
--          xx_debug('Inside More Information  RESPOND to be Tested Check==================> After Setting ');  
--       X_RESULTOUT := 'COMPLETE';
----        X_RESULTOUT := WF_ENGINE.ENG_COMPLETED || ':' || 'Y';
--       return;
--     end if;

exception
    when others
    then
        lc_error_msg := sqlerrm (sqlcode);
end;

    
    
PROCEDURE update_history (p_itemtype    IN            VARCHAR2,
                          p_itemkey     IN            VARCHAR2,
                          p_actid       IN            NUMBER,
                          p_funcmode    IN            VARCHAR2,
                          x_resultout      OUT NOCOPY VARCHAR2)
IS
    lc_error_msg           VARCHAR2 (2000);
    ln_ptycsh_wf_hist_id   NUMBER;
    ln_PTYCSH_HDR_ID       NUMBER;
    lc_wf_item_key         VARCHAR2 (240);
    lc_get_resp            VARCHAR2 (240);
    lc_appr_user_name      VARCHAR2 (240);
    lc_get_ame_trx_type    VARCHAR2 (240);
    nvl_username           VARCHAR2 (240);
    lc_get_comments        VARCHAR2 (2000);
BEGIN
    SELECT user_name
      INTO nvl_username
      FROM fnd_user
     WHERE user_id = fnd_global.user_id;

    IF (p_funcmode = 'RUN')
    THEN
        ln_PTYCSH_HDR_ID :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_HDR_ID');

        ln_ptycsh_wf_hist_id :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_WF_HIST_ID');
        lc_wf_item_key :=
            wf_engine.getitemattrtext (p_itemtype, p_itemkey, 'WF_ITEM_KEY');
        

--        if(wf_engine.getactivityattrtext (p_itemtype,
--                                           p_itemkey,
--                                           p_actid,
--                                           'PTY_CSH_REVIEWER_ACT') is not null)
--        then
--        lc_appr_user_name :=
--            wf_engine.getitemattrtext (p_itemtype,
--                                       p_itemkey,
--                                       'OAF_APPROVER_UNAME');
--
--        elsif(wf_engine.getactivityattrtext (p_itemtype,
--                                           p_itemkey,
--                                           p_actid,
--                                           'PTY_CSH_ACTED_HEAD_AP') is not null)
--        then
--         
--        lc_appr_user_name :=
--            wf_engine.getitemattrtext (p_itemtype,
--                                       p_itemkey,
--                                       'OAF_FINAL_APPR_UNAME');
--        else
        lc_appr_user_name :=
            wf_engine.getitemattrtext (p_itemtype,
                                       p_itemkey,
                                       'NEXT_APPROVER_USER_NAME');
        
--         end if;

        lc_get_resp :=
            wf_engine.getactivityattrtext (p_itemtype,
                                           p_itemkey,
                                           p_actid,
                                           'GET_RESP');
--        xx_debug ('Setting #FROM_ROLE and about to do ' || lc_appr_user_name||' Getting lc_get_resp'||lc_get_resp);
        wf_engine.setitemattrtext (p_itemtype,
                                   p_itemkey,
                                   '#FROM_ROLE',
                                   lc_appr_user_name);



        lc_get_comments :=
            wf_engine.getitemattrtext (p_itemtype, p_itemkey, 'WF_NOTE');



        lc_get_ame_trx_type :=
            wf_engine.getitemattrtext (p_itemtype,
                                       p_itemkey,
                                       'WF_AME_TRANS_TYPE');


--        xx_debug ('Before Entering and Updating in WF History Table');

        if( lc_get_resp not in ('CLOSE', 'NO'))
        then
        UPDATE xxfin_ap_pettycash_wf_t
           SET response = lc_get_resp,
               approver_comments = lc_get_comments,
               created_by = fnd_global.user_id,
               last_updated_by = fnd_global.user_id,
               last_update_date = SYSDATE,
               response_date = SYSDATE
         WHERE ptycsh_wf_hist_id = ln_ptycsh_wf_hist_id;
--         xx_debug(' Checking the Values that are getting updated : UPDATE xxfin_ap_pettycash_wf_t
--            response = '||lc_get_resp||',
--              approver_comments = '||lc_get_comments||',
--               created_by = '||fnd_global.user_id||',
--               last_updated_by = '||fnd_global.user_id||',
--               last_update_date = '||SYSDATE||',
--               response_date = '||SYSDATE||'
--         where ptycsh_wf_hist_id = '||ln_ptycsh_wf_hist_id);
--         
         end if;


--        IF (lc_get_resp = 'APPROVED')
--        THEN
--            xx_debug ('Inside Approved Condition in UPDATE_HISTROY');
--
--            IF (lc_appr_user_name IS NOT NULL)
--            THEN
--                NULL;
--            END IF;
--        ELSE
--            IF (lc_get_resp = 'NO')
--            THEN
--                UPDATE xxfin_ap_pettycash_hdr_t
--                   SET wf_status = 'Approved',
--                       status = 'Approved',
--                       pending_with = NULL
--                 WHERE PTYCSH_HDR_ID = ln_PTYCSH_HDR_ID;
--                 
--                 create_adhoc_role(
--                               p_itemtype 
--                              ,p_itemkey
--                              ,p_actid  
--                              ,p_funcmode  
--                              ,x_resultout 
--                           );
--            ELSE
--                IF (lc_appr_user_name IS NOT NULL)
--                THEN
--                    UPDATE xxfin_ap_pettycash_hdr_t
--                   SET wf_status = 'Rejected', status = 'Rejected'
--                 WHERE PTYCSH_HDR_ID = ln_PTYCSH_HDR_ID;
--                --
--                END IF;
--
--                BEGIN
--                    create_adhoc_role(
--                               p_itemtype 
--                              ,p_itemkey
--                              ,p_actid  
--                              ,p_funcmode  
--                              ,x_resultout 
--                           );
--                END;
--
--                
--            --
--            END IF;
--
--            wf_engine.setitemattrtext (p_itemtype,
--                                       p_itemkey,
--                                       'WF_NOTE',
--                                       '');
--            x_resultout := wf_engine.eng_completed;
--        END IF;
     IF (LC_GET_RESP = 'APPROVED')
            THEN
--             xx_debug('Inside Approved Condition in UPDATE_HISTROY');
                IF (LC_APPR_USER_NAME IS NOT NULL)
                THEN

                    AME_API2.UPDATEAPPROVALSTATUS2 (
                        APPLICATIONIDIN     => 50100,
                        TRANSACTIONIDIN     => p_itemkey, --?GENERALLY ITEMKEY
                        APPROVALSTATUSIN    => AME_UTIL.APPROVEDSTATUS, --?APPROVED_STATUS_OR_REJECTED_STATUS
                        APPROVERNAMEIN      => LC_APPR_USER_NAME,
                        TRANSACTIONTYPEIN   => LC_GET_AME_TRX_TYPE);

                END IF;
            ELSE     IF (LC_GET_RESP = 'CLOSE')
            then
            update xxfin_ap_pettycash_hdr_t set WF_STATUS = 'Approved' , STATUS = 'Approved', PENDING_WITH = null where ptycsh_hdr_id = LN_ptycsh_hdr_id;  
--            ELSE     IF (LC_GET_RESP = 'NO')
--            then
--            update xxfin_ap_pettycash_hdr_t set WF_STATUS = 'Approved' , STATUS = 'Approved', PENDING_WITH = null where ptycsh_hdr_id = LN_ptycsh_hdr_id;
            ELSE
--                WF_ENGINE.SETITEMATTRTEXT (ITEMTYPE   => P_ITEMTYPE,
--                                           ITEMKEY    => P_ITEMKEY,
--                                           ANAME      => 'REJECT_COMMENT',
--                                           AVALUE     => LC_GET_COMMENTS);

--                xx_debug('Inside ELSE Condition in UPDATE_HISTROY Before AME_API2.UPDATEAPPROVALSTATUS2');
                                                

                IF (LC_APPR_USER_NAME IS NOT NULL)
                THEN
--                 xx_debug('Inside ELSE Condition in UPDATE_HISTROY Inside AME_API2.UPDATEAPPROVALSTATUS2');
                    AME_API2.UPDATEAPPROVALSTATUS2 (
                        APPLICATIONIDIN     => 50100,
                        TRANSACTIONIDIN     => p_itemkey, --?GENERALLY ITEMKEY
                        APPROVALSTATUSIN    => AME_UTIL.REJECTSTATUS, --?APPROVED_STATUS_OR_REJECTED_STATUS
                        APPROVERNAMEIN      => LC_APPR_USER_NAME,
                        TRANSACTIONTYPEIN   => LC_GET_AME_TRX_TYPE);
                --
                END IF;
--                xx_debug('Inside ELSE Condition in UPDATE_HISTROY After AME_API2.UPDATEAPPROVALSTATUS2');

                BEGIN
--                xx_debug('Inside ELSE Condition in UPDATE_HISTROY Inside  AME_API2.CLEARALLAPPROVALS');
--                    AME_API2.CLEARALLAPPROVALS (50100,
--                                                'LC_GET_AME_TRX_TYPE',
--                                                Lc_WF_ITEM_KEY);
                   AME_API2.CLEARALLAPPROVALS (50100,
                                                LC_GET_AME_TRX_TYPE,
                                                p_itemkey);                                                
                                                
                END;
--                xx_debug('Inside ELSE Condition in UPDATE_HISTROY After  AME_API2.CLEARALLAPPROVALS  LN_ptycsh_hdr_id'||LN_ptycsh_hdr_id);
               update xxfin_ap_pettycash_hdr_t set WF_STATUS = 'Rejected', STATUS = 'Rejected'  where ptycsh_hdr_id = LN_ptycsh_hdr_id;
               
--               xx_debug('Inside ELSE Condition in UPDATE_HISTROY Before create_adhoc_role');

                create_adhoc_role(
                                               p_itemtype 
                                              ,p_itemkey
                                              ,p_actid  
                                              ,p_funcmode  
                                              ,x_resultout 
                                           );
--                 xx_debug('Inside ELSE Condition in UPDATE_HISTROY After  create_adhoc_role');
                
                   
            --
            END IF;
       WF_ENGINE.SETITEMATTRTEXT (P_ITEMTYPE, P_ITEMKEY, 'WF_NOTE', '');
            X_RESULTOUT := WF_ENGINE.ENG_COMPLETED;
        END IF;
 



    END IF;
EXCEPTION
    WHEN OTHERS
    THEN
        lc_error_msg := SQLERRM (SQLCODE);
--        xx_debug (
--               'Inside Exception in update_history'
--            || lc_error_msg
--            || '|| DBMS_UTILITY.FORMAT_ERROR_STACK'
--            || DBMS_UTILITY.format_error_stack);
END;
        
    
PROCEDURE update_oaf_history (p_itemtype    IN            VARCHAR2,
                          p_itemkey     IN            VARCHAR2,
                          p_actid       IN            NUMBER,
                          p_funcmode    IN            VARCHAR2,
                          x_resultout      OUT NOCOPY VARCHAR2)
IS
    lc_error_msg           VARCHAR2 (2000);
    ln_ptycsh_wf_hist_id   NUMBER;
    ln_PTYCSH_HDR_ID       NUMBER;
    lc_wf_item_key         VARCHAR2 (240);
    lc_get_resp            VARCHAR2 (240);
    lc_appr_user_name      VARCHAR2 (240);
    lc_get_ame_trx_type    VARCHAR2 (240);
    nvl_username           VARCHAR2 (240);
    lc_get_comments        VARCHAR2 (2000);
    lc_final_approval      varchar2(200);
BEGIN
    SELECT user_name
      INTO nvl_username
      FROM fnd_user
     WHERE user_id = fnd_global.user_id;

    IF (p_funcmode = 'RUN')
    THEN
        ln_PTYCSH_HDR_ID :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_HDR_ID');

        ln_ptycsh_wf_hist_id :=
            wf_engine.getitemattrnumber (p_itemtype,
                                         p_itemkey,
                                         'PTYCSH_WF_HIST_ID');
        lc_wf_item_key :=
            wf_engine.getitemattrtext (p_itemtype, p_itemkey, 'WF_ITEM_KEY');
        

        if(wf_engine.getactivityattrtext (p_itemtype,
                                           p_itemkey,
                                           p_actid,
                                           'PTY_CSH_REVIEWER_ACT') is not null)
        then
--        xx_debug('Inside update_oaf_history if PTY_CSH_REVIEWER_ACT');        
        lc_appr_user_name :=
            wf_engine.getitemattrtext (p_itemtype,
                                       p_itemkey,
                                       'OAF_APPROVER_UNAME');

        elsif(wf_engine.getactivityattrtext (p_itemtype,
                                           p_itemkey,
                                           p_actid,
                                           'PTY_CSH_ACTED_HEAD_AP') is not null)
        then
--        xx_debug('Inside update_oaf_history if PTY_CSH_ACTED_HEAD_AP'); 
        lc_appr_user_name :=
            wf_engine.getitemattrtext (p_itemtype,
                                       p_itemkey,
                                       'OAF_FINAL_APPR_UNAME');
        else
--        xx_debug('Inside update_oaf_history else Condition NEXT_APPROVER_USER_NAME '); 
        lc_appr_user_name :=
            wf_engine.getitemattrtext (p_itemtype,
                                       p_itemkey,
                                       'NEXT_APPROVER_USER_NAME');
        
         end if;
         
        lc_get_resp :=
            wf_engine.getactivityattrtext (p_itemtype,
                                           p_itemkey,
                                           p_actid,
                                           'GET_RESP');
                                           
         lc_final_approval :=
            wf_engine.getactivityattrtext (p_itemtype,
                                           p_itemkey,
                                           p_actid,
                                           'IS_FINAL_APPR');                                   
                                           
--        xx_debug ('Setting #FROM_ROLE and about to do ' || lc_appr_user_name||' Getting lc_get_resp'||lc_get_resp);
        wf_engine.setitemattrtext (p_itemtype,
                                   p_itemkey,
                                   '#FROM_ROLE',
                                   lc_appr_user_name);



        lc_get_comments :=
            wf_engine.getitemattrtext (p_itemtype, p_itemkey, 'WF_NOTE');



        lc_get_ame_trx_type :=
            wf_engine.getitemattrtext (p_itemtype,
                                       p_itemkey,
                                       'WF_AME_TRANS_TYPE');


--        xx_debug ('Before Entering and Updating in WF History Table');

        if( lc_get_resp not in ('CLOSE', 'NO'))
        then
        UPDATE xxfin_ap_pettycash_wf_t
           SET response = lc_get_resp,
               approver_comments = lc_get_comments,
               created_by = fnd_global.user_id,
               last_updated_by = fnd_global.user_id,
               last_update_date = SYSDATE,
               response_date = SYSDATE
         WHERE ptycsh_wf_hist_id = ln_ptycsh_wf_hist_id;
--         xx_debug(' Checking the Values that are getting updated : UPDATE xxfin_ap_pettycash_wf_t
--            response = '||lc_get_resp||',
--              approver_comments = '||lc_get_comments||',
--               created_by = '||fnd_global.user_id||',
--               last_updated_by = '||fnd_global.user_id||',
--               last_update_date = '||SYSDATE||',
--               response_date = '||SYSDATE||'
--         where ptycsh_wf_hist_id = '||ln_ptycsh_wf_hist_id);
         
         end if;


--        IF (lc_get_resp = 'APPROVED')
--        THEN
--            xx_debug ('Inside Approved Condition in UPDATE_HISTROY');
--
--            IF (lc_appr_user_name IS NOT NULL)
--            THEN
--                NULL;
--            END IF;
--        ELSE
--            IF (lc_get_resp = 'NO')
--            THEN
--                UPDATE xxfin_ap_pettycash_hdr_t
--                   SET wf_status = 'Approved',
--                       status = 'Approved',
--                       pending_with = NULL
--                 WHERE PTYCSH_HDR_ID = ln_PTYCSH_HDR_ID;
--                 
--                 create_adhoc_role(
--                               p_itemtype 
--                              ,p_itemkey
--                              ,p_actid  
--                              ,p_funcmode  
--                              ,x_resultout 
--                           );
--            ELSE
--                IF (lc_appr_user_name IS NOT NULL)
--                THEN
--                    UPDATE xxfin_ap_pettycash_hdr_t
--                   SET wf_status = 'Rejected', status = 'Rejected'
--                 WHERE PTYCSH_HDR_ID = ln_PTYCSH_HDR_ID;
--                --
--                END IF;
--
--                BEGIN
--                    create_adhoc_role(
--                               p_itemtype 
--                              ,p_itemkey
--                              ,p_actid  
--                              ,p_funcmode  
--                              ,x_resultout 
--                           );
--                END;
--
--                
--            --
--            END IF;
--
--            wf_engine.setitemattrtext (p_itemtype,
--                                       p_itemkey,
--                                       'WF_NOTE',
--                                       '');
--            x_resultout := wf_engine.eng_completed;
--        END IF;
     IF (LC_GET_RESP = 'APPROVED')
            THEN
--             xx_debug('Inside Approved Condition in UPDATE_OAF_HISTROY  lc_final_approval'||lc_final_approval);
--                IF (LC_APPR_USER_NAME IS NOT NULL)
--                THEN
--
--                    AME_API2.UPDATEAPPROVALSTATUS2 (
--                        APPLICATIONIDIN     => 50100,
--                        TRANSACTIONIDIN     => p_itemkey, --?GENERALLY ITEMKEY
--                        APPROVALSTATUSIN    => AME_UTIL.APPROVEDSTATUS, --?APPROVED_STATUS_OR_REJECTED_STATUS
--                        APPROVERNAMEIN      => LC_APPR_USER_NAME,
--                        TRANSACTIONTYPEIN   => LC_GET_AME_TRX_TYPE);
--
--                END IF;

            if(lc_final_approval is not null)
            then 
            update xxfin_ap_pettycash_hdr_t set WF_STATUS = 'Approved' , STATUS = 'Approved', PENDING_WITH = null where ptycsh_hdr_id = LN_ptycsh_hdr_id;
            end if;

            ELSE     IF (LC_GET_RESP = 'CLOSE')
            then
            update xxfin_ap_pettycash_hdr_t set WF_STATUS = 'Approved' , STATUS = 'Approved', PENDING_WITH = null where ptycsh_hdr_id = LN_ptycsh_hdr_id;  
--            ELSE     IF (LC_GET_RESP = 'NO')
--            then
--            update xxfin_ap_pettycash_hdr_t set WF_STATUS = 'Approved' , STATUS = 'Approved', PENDING_WITH = null where ptycsh_hdr_id = LN_ptycsh_hdr_id;
            ELSE
--                WF_ENGINE.SETITEMATTRTEXT (ITEMTYPE   => P_ITEMTYPE,
--                                           ITEMKEY    => P_ITEMKEY,
--                                           ANAME      => 'REJECT_COMMENT',
--                                           AVALUE     => LC_GET_COMMENTS);

--                xx_debug('Inside ELSE Condition in UPDATE_OAF_HISTROY ==');
                                                

--                IF (LC_APPR_USER_NAME IS NOT NULL)
--                THEN
--                 xx_debug('Inside ELSE Condition in UPDATE_HISTROY Inside AME_API2.UPDATEAPPROVALSTATUS2');
--                    AME_API2.UPDATEAPPROVALSTATUS2 (
--                        APPLICATIONIDIN     => 50100,
--                        TRANSACTIONIDIN     => p_itemkey, --?GENERALLY ITEMKEY
--                        APPROVALSTATUSIN    => AME_UTIL.REJECTSTATUS, --?APPROVED_STATUS_OR_REJECTED_STATUS
--                        APPROVERNAMEIN      => LC_APPR_USER_NAME,
--                        TRANSACTIONTYPEIN   => LC_GET_AME_TRX_TYPE);
--                --
--                END IF;
--                xx_debug('Inside ELSE Condition in UPDATE_HISTROY After AME_API2.UPDATEAPPROVALSTATUS2');

--                BEGIN
--                xx_debug('Inside ELSE Condition in UPDATE_HISTROY Inside  AME_API2.CLEARALLAPPROVALS');
----                    AME_API2.CLEARALLAPPROVALS (50100,
----                                                'LC_GET_AME_TRX_TYPE',
----                                                Lc_WF_ITEM_KEY);
--                   AME_API2.CLEARALLAPPROVALS (50100,
--                                                LC_GET_AME_TRX_TYPE,
--                                                p_itemkey);                                                
--                                                
--                END;
--                xx_debug('Inside ELSE Condition in UPDATE_HISTROY After  AME_API2.CLEARALLAPPROVALS  LN_ptycsh_hdr_id'||LN_ptycsh_hdr_id);
               update xxfin_ap_pettycash_hdr_t set WF_STATUS = 'Rejected', STATUS = 'Rejected'  where ptycsh_hdr_id = LN_ptycsh_hdr_id;
               
--               xx_debug('Inside ELSE Condition in UPDATE_HISTROY Before create_adhoc_role');

                create_adhoc_role(
                                               p_itemtype 
                                              ,p_itemkey
                                              ,p_actid  
                                              ,p_funcmode  
                                              ,x_resultout 
                                           );
--                 xx_debug('Inside ELSE Condition in UPDATE_HISTROY After  create_adhoc_role');
                
                   
            --
            END IF;
       WF_ENGINE.SETITEMATTRTEXT (P_ITEMTYPE, P_ITEMKEY, 'WF_NOTE', ' ');
            X_RESULTOUT := WF_ENGINE.ENG_COMPLETED;
        END IF;
    END IF;
EXCEPTION
    WHEN OTHERS
    THEN
        lc_error_msg := SQLERRM (SQLCODE);
--        xx_debug (
--               'Inside Exception in update_history'
--            || lc_error_msg
--            || '|| DBMS_UTILITY.FORMAT_ERROR_STACK'
--            || DBMS_UTILITY.format_error_stack);
END;
        




procedure create_adhoc_role(
                               p_itemtype       in            varchar2
                              ,p_itemkey        in            varchar2
                              ,p_actid          in            number
                              ,p_funcmode       in            varchar2
                              ,x_resultout         out nocopy varchar2
                           )
                           
is
 
cursor c(pc_itemkey in varchar) is  
select distinct RESPONDED_USER_NAME from xxfin_ap_pettycash_wf_v xapwv where xapwv.WF_ITEM_KEY = pc_itemkey;
lc_error_msg    VARCHAR2 (2000);
lv_PTYCSH_HDR_ID number;
lv_role varchar2(100) := 'PYTCSH'||p_itemkey;
lv_role_desc varchar2(100) := 'PYTCSH_REJECT_USERS'||p_itemkey;
BEGIN
--xx_debug('Inside create_adhoc_role and Entering ');

    lv_PTYCSH_HDR_ID:=wf_engine.getitemattrnumber(
                                                            p_itemtype, 
                                                            p_itemkey,
                                                            'PTYCSH_HDR_ID'
                                                       );
--xx_debug('Inside create_adhoc_role and Entering  lv_PTYCSH_HDR_ID'||lv_PTYCSH_HDR_ID);                                                       
                                                       
       wf_directory.CreateAdHocRole (lv_role,
                                  lv_role_desc,
                                  NULL,
                                  NULL,
                                  lv_role_desc,
                                  'MAILHTML',
                                  NULL,          --USER NAME SHOULD BE IN CAPS
                                  NULL,
                                  NULL,
                                  'ACTIVE',
                                  NULL);
                           

--    xx_debug('Before going inside ADHOC Role'||lv_role||' lv_role_desc '||lv_role_desc);
    FOR i IN c (p_itemkey)
    LOOP
--    xx_debug('Getting the Adhoc User Roles inside CREATE_ADHOC_ROLE'||lv_role||' i.RESPONDED_USER_NAME '||i.RESPONDED_USER_NAME);
        WF_DIRECTORY.AddUsersToAdHocRole (lv_role, i.RESPONDED_USER_NAME);   
    END LOOP;
--    xx_debug('After going inside ADHOC Role'||lv_role||' lv_role_desc '||lv_role_desc);
    
    wf_engine.setitemattrtext (p_itemtype,
                                   p_itemkey,
                                   'FINAL_NOTIF_REC',
                                   lv_role
                                   );   
                                           
    exception
                when others
                then
         lc_error_msg := sqlerrm (sqlcode);
--        xx_debug ('Inside Exception in Create_ADHOC_ROLE' || lc_error_msg          || '|| DBMS_UTILITY.FORMAT_ERROR_STACK'
--            || DBMS_UTILITY.format_error_stack);
                    
    End;    
    

procedure budget_update(
                               p_itemtype       in            varchar2
                              ,p_itemkey        in            varchar2
                              ,p_actid          in            number
                              ,p_funcmode       in            varchar2
                              ,x_resultout         out nocopy varchar2
                           )
                           
is
 
cursor c(pc_itemkey in varchar) is  
select distinct RESPONDED_USER_NAME from xxfin_ap_pettycash_wf_v xapwv where xapwv.WF_ITEM_KEY = pc_itemkey;
lc_error_msg    VARCHAR2 (2000);
lv_PTYCSH_HDR_ID number;
lv_role varchar2(100) := 'PYTCSH'||p_itemkey;
lv_role_desc varchar2(100) := 'PYTCSH_REJECT_USERS'||p_itemkey;
lc_get_resp varchar2(240);
BEGIN
--xx_debug('Inside BUDGET_UPDATE and Entering ');

    lv_PTYCSH_HDR_ID:=wf_engine.getitemattrnumber(
                                                            p_itemtype, 
                                                            p_itemkey,
                                                            'PTYCSH_HDR_ID'
                                                       );
                                                       
                                                       
    lc_get_resp :=
            wf_engine.getactivityattrtext (p_itemtype,
                                           p_itemkey,
                                           p_actid,
                                           'GET_RESP');
--xx_debug('Inside BUDGET_UPDATE and Getting Response  '||lc_get_resp );
                                            
update xxfin_ap_pettycash_hdr_t set BUDGET_APPROVAL = lc_get_resp where PTYCSH_HDR_ID = lv_PTYCSH_HDR_ID;
                                           
    exception
                when others
                then
         lc_error_msg := sqlerrm (sqlcode);
--        xx_debug ('Inside Exception in Create_ADHOC_ROLE' || lc_error_msg          || '|| DBMS_UTILITY.FORMAT_ERROR_STACK'
--            || DBMS_UTILITY.format_error_stack);
                    
    End budget_update;    






    PROCEDURE budget_fyi_start_process (p_ptycsh_hdr_id   IN            NUMBER,
                         p_wf_item_key     in varchar2,
                         p_error_code         OUT NOCOPY NUMBER,
                         p_error_msg          OUT NOCOPY VARCHAR2)
IS
    lc_item_type            VARCHAR2 (8) := 'XXHWPTCS';                           
    lc_item_key             VARCHAR2 (240);
    lc_requested_by_uname   VARCHAR2 (240);
    
    lc_request_number      VARCHAR2 (500);
    lc_supplier_name        VARCHAR2 (500);
    lc_sme_po_number        VARCHAR2(500);
    lc_location_name        VARCHAR2(500);

    CURSOR c_get_item_owner (p_ln_ptycsh_hdr_id IN NUMBER)
    IS
        SELECT requested_by, request_number
          FROM xxfin_ap_pettycash_hdr_v
         WHERE ptycsh_hdr_id = p_ln_ptycsh_hdr_id;

    CURSOR c_get_req_by (p_ln_ptycsh_hdr_id IN NUMBER)
    IS
        SELECT requested_by, request_number, VENDOR_NAME, VENDOR_SITE_NAME, LOCATION_NAME
          FROM xxfin_ap_pettycash_hdr_v
         WHERE ptycsh_hdr_id = p_ln_ptycsh_hdr_id;
         
      cursor c_get_receiver is
        select rownum rn, appr.user_name, appr.user_role
          from (
                select name user_name, display_name user_role
                  from wf_roles
                 where display_name = 'Budget Team'
                ) appr;
                
    LC_USER_NAME varchar2(500);
    LC_USER_ROLE varchar2(500);         
BEGIN
    p_error_code := 0;
    p_error_msg := 'COMPLETE';
    lc_item_key := p_wf_item_key;    

    -- launch workflow
    wf_engine.createprocess (lc_item_type, lc_item_key, 'PETTY_CASH_BUDGET_NOTIF');
--         xx_debug('Getting values  ---------------------- '||p_ptycsh_hdr_id);

    FOR i IN c_get_req_by (p_ptycsh_hdr_id)
    LOOP
        lc_requested_by_uname := i.requested_by;
        lc_request_number := i.request_number;
        lc_supplier_name := i.VENDOR_NAME;
        lc_sme_po_number := i.VENDOR_SITE_NAME;
        lc_location_name := i.LOCATION_NAME;
--        xx_debug('Getting values '||lc_requested_by_uname||lc_request_number||lc_supplier_name);
    END LOOP;
    
    
    for i in c_get_receiver
        loop
--            XX_DEBUG('Inside budget_fyi_start_process : Before For loop c_get_petty_cash_rev'||LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole  Getting the Number'||i.rn);
--            if (ln_iteration = i.rn)
--            then
               LC_USER_NAME := i.USER_NAME;
               LC_USER_ROLE := i.user_role;
--               XX_DEBUG(LC_USER_NAME||'Getting UserName '||LC_USER_ROLE||'Getting UserRole');
                wf_engine.setitemattrtext (lc_item_type,
                                           lc_item_key,
                                           'BUDGET_TEAM',
                                           LC_USER_NAME);
                                
--            end if;
    end loop;                               


    wf_engine.setitemattrtext (
        lc_item_type,
        lc_item_key,
        'OAF_RN',
           'JSP:/OA_HTML/OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshWFRN&'|| 'PTYCSHHDRID='|| p_ptycsh_hdr_id);
        wf_engine.setitemattrtext (
        lc_item_type,
        lc_item_key,
        'OAF_RN_READONLY',
           'JSP:/OA_HTML/OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshWFRN&'|| 'PTYCSHHDRID='|| p_ptycsh_hdr_id||'&MODE='||'READDONLY');       
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               '#FROM_ROLE',
                               lc_requested_by_uname);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'REQUESTED_BY',
                               lc_requested_by_uname);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'REQUESTED_FOR',
                               lc_requested_by_uname);


    --setting the header id primary key
--    xx_debug('Setting primary key =========================== Before'||p_ptycsh_hdr_id);
    wf_engine.setitemattrnumber (lc_item_type,
                                 lc_item_key,
                                 'PTYCSH_HDR_ID',
                                 p_ptycsh_hdr_id);
--    xx_debug('Setting primary key =========================== After'||p_ptycsh_hdr_id);    
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'WF_ITEM_KEY',
                               lc_item_key);
                               
    wf_engine.setitemowner (lc_item_type, lc_item_key, lc_requested_by_uname);

    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'REQUEST_NUMBER',
                               lc_request_number);

    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'SUPPLIER_NAME',
                               lc_supplier_name);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'LOCATION_NAME',
                               lc_location_name);
                                                              
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               'SME_PO_NUMBER',
                               lc_sme_po_number);

    wf_engine.startprocess (lc_item_type, lc_item_key);
    wf_engine.setitemattrtext (lc_item_type,
                               lc_item_key,
                               '#FROM_ROLE',
                               lc_requested_by_uname);
                               
                               
    
   
    COMMIT;
EXCEPTION
    WHEN OTHERS
    THEN
        p_error_code := SQLCODE;
        p_error_msg := SQLERRM (SQLCODE);
END budget_fyi_start_process;





procedure execute_concurrent(
                               p_itemtype       in            varchar2
                              ,p_itemkey        in            varchar2
                              ,p_actid          in            number
                              ,p_funcmode       in            varchar2
                              ,x_resultout         out nocopy varchar2
                           )
                           
is
 
cursor c(pc_itemkey in varchar) is  
select distinct RESPONDED_USER_NAME from xxfin_ap_pettycash_wf_v xapwv where xapwv.WF_ITEM_KEY = pc_itemkey;
lc_error_msg    VARCHAR2 (2000);
lv_PTYCSH_HDR_ID number;
--lv_role varchar2(100) := 'PYTCSH'||p_itemkey;
--lv_role_desc varchar2(100) := 'PYTCSH_REJECT_USERS'||p_itemkey;

   l_responsibility_id     NUMBER;
   l_resp_application_id   NUMBER;
   l_security_group_id     NUMBER;
   l_user_id               NUMBER;
   l_request_id            NUMBER;
   
BEGIN
--xx_debug('Inside execute_concurrent and Entering ');

    lv_PTYCSH_HDR_ID:=wf_engine.getitemattrnumber(
                                                            p_itemtype, 
                                                            p_itemkey,
                                                            'PTYCSH_HDR_ID'
                                                       );
--xx_debug('Inside execute_concurrent and Entering  lv_PTYCSH_HDR_ID'||lv_PTYCSH_HDR_ID);                                                       

SELECT user_id, responsibility_id, responsibility_application_id,
          security_group_id
     INTO l_user_id, l_responsibility_id, l_resp_application_id,
          l_security_group_id
     FROM fnd_user_resp_groups
    WHERE user_id = (SELECT user_id
                       FROM fnd_user
                      WHERE user_name = 'SYSADMIN')
      AND responsibility_id =
             (SELECT responsibility_id
                FROM fnd_responsibility_vl
               WHERE responsibility_name = 'Haya Payables Super User MG');


   --
   --To set environment context.
   --
   apps.fnd_global.apps_initialize (l_user_id,
                                    l_responsibility_id,
                                    l_resp_application_id
                                   );
   --
   --Submitting Concurrent Request
   --
   l_request_id :=
      fnd_request.submit_request (application      => 'XXFIN',         -- Application Short Name
                                  program          => 'XXFIN_PETTY_CASH_INVOICE',   -- Program Short Name
                                  description      => 'Haya Finance Petty Cash Invoice Program', -- Any Meaningful Description
                                  start_time       => SYSDATE,          -- Start Time
                                  sub_request      => FALSE,            -- Subrequest Default False
                                  argument1        => lv_PTYCSH_HDR_ID      -- Parameters Starting
                                 );
   --
   COMMIT;

   --
--   IF l_request_id = 0
--   THEN
----      xx_debug('Concurrent request failed to submit');
--   ELSE
----      xx_debug ('Successfully Submitted the Concurrent Request: '||l_request_id);
--   END IF;
--   --



    exception
                when others
                then
         lc_error_msg := sqlerrm (sqlcode);
--        xx_debug ('Inside Exception in execute_concurrent' || lc_error_msg          || '|| DBMS_UTILITY.FORMAT_ERROR_STACK'
--            || DBMS_UTILITY.format_error_stack);
                    
    End execute_concurrent;    
    
    
end xxhw_fin_pettycash_wf_pkg;
/
EXIT;
EOF