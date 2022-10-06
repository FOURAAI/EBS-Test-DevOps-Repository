SET PAGESIZE 0 FEEDBACK OFF DEFINE OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500

CREATE OR REPLACE package APPS.xxhw_fin_pettycash_wf_pkg

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
procedure start_process (p_ptycsh_hdr_id     in            number,
                            p_wf_item_key     in varchar2
                              ,p_error_code    out nocopy number
                              ,p_error_msg     out nocopy varchar2
                              );

procedure config_utilities (
                           p_itemtype       in            varchar2
                          ,p_itemkey        in            varchar2
                          ,p_actid          in            number
                          ,p_funcmode       in            varchar2
                          ,x_resultout         out nocopy varchar2
                          );

procedure get_approvers (
                           p_itemtype       in            varchar2
                          ,p_itemkey        in            varchar2
                          ,p_actid          in            number
                          ,p_funcmode       in            varchar2
                          ,x_resultout         out nocopy varchar2
                          );
                          
procedure insert_history (
                           p_itemtype       in            varchar2
                          ,p_itemkey        in            varchar2
                          ,p_actid          in            number
                          ,p_funcmode       in            varchar2
                          ,x_resultout         out nocopy varchar2
                          );
procedure insert_oaf_history (
                           p_itemtype       in            varchar2
                          ,p_itemkey        in            varchar2
                          ,p_actid          in            number
                          ,p_funcmode       in            varchar2
                          ,x_resultout         out nocopy varchar2
                          );                          
  PROCEDURE REASON_NOT_NULL_VALIDATE (
                                        P_ITEMTYPE IN VARCHAR2,
                                        P_ITEMKEY IN VARCHAR2,
                                        P_ACTID IN NUMBER,
                                        P_FUNCMODE IN VARCHAR2,
                                        X_RESULTOUT OUT NOCOPY VARCHAR2
                                    );                          
                          
procedure update_history (
                           p_itemtype       in            varchar2
                          ,p_itemkey        in            varchar2
                          ,p_actid          in            number
                          ,p_funcmode       in            varchar2
                          ,x_resultout         out nocopy varchar2
                          );      
procedure update_oaf_history (
                           p_itemtype       in            varchar2
                          ,p_itemkey        in            varchar2
                          ,p_actid          in            number
                          ,p_funcmode       in            varchar2
                          ,x_resultout         out nocopy varchar2
                          );                                                  
 procedure create_adhoc_role(
                               p_itemtype       in            varchar2
                              ,p_itemkey        in            varchar2
                              ,p_actid          in            number
                              ,p_funcmode       in            varchar2
                              ,x_resultout         out nocopy varchar2
                           );
                           
procedure budget_fyi_start_process (p_ptycsh_hdr_id     in            number,
                                  p_wf_item_key     in varchar2
                              ,p_error_code    out nocopy number
                              ,p_error_msg     out nocopy varchar2
                              );

                         
procedure budget_update(
                               p_itemtype       in            varchar2
                              ,p_itemkey        in            varchar2
                              ,p_actid          in            number
                              ,p_funcmode       in            varchar2
                              ,x_resultout         out nocopy varchar2
                           );                                   
     
                              
                              
procedure execute_concurrent(
                               p_itemtype       in            varchar2
                              ,p_itemkey        in            varchar2
                              ,p_actid          in            number
                              ,p_funcmode       in            varchar2
                              ,x_resultout         out nocopy varchar2
                           );                                   
                                                    
                                    
end xxhw_fin_pettycash_wf_pkg;
/

EXIT;
EOF