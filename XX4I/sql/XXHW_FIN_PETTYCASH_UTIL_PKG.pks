SET PAGESIZE 0 FEEDBACK OFF DEFINE OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500

CREATE OR REPLACE package APPS.xxhw_fin_pettycash_util_pkg

is
 	-- +===================================================================+
	-- +                      Haya Water, Muscat, OMAN                     +
	-- +===================================================================+
	-- |Author: 4iapps                                                      |
	-- |Initial Build Date: 01-Mar-2020                                     |
	-- |Source File Name: xxhw_fin_pettycash_wf_pkg.pls                 |
	-- |                                                                   |
	-- |Object Name:                                                       |
	-- |Description: <Description or Purpose>                              |
	-- |                                                                   |
	-- |Dependencies:  No                                                  |
	-- |                                                                   |
	-- |Usage:  This pacakge is used in Custom SME Contract Workflow      |
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
	-- |0.1        13-Apr-2020   4iapps     Initial draft version           |
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
    return varchar2;
    
procedure create_attachment_copy
(p_old_pk1_value in varchar2, p_new_pk1_value  in varchar2);    

procedure bill_attach_count (p_iou_id in varchar2, X_FLAG OUT VARCHAR2);

function get_div_loc_conc_restric(p_resp_name in varchar2) 
return varchar2;

function get_eligible_amt(p_div in varchar2) 
return number;

FUNCTION spell_number_omr (p_number IN NUMBER)
   RETURN VARCHAR2;
                                    
end xxhw_fin_pettycash_util_pkg;
/
 
EXIT;
EOF