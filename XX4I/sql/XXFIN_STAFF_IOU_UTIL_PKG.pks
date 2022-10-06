
SET PAGESIZE 0 FEEDBACK OFF DEFINE OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500

CREATE OR REPLACE package APPS.XXFIN_STAFF_IOU_UTIL_PKG as
  /* $header: XXFIN_STAFF_IOU_UTIL_PKG 29-OCT-2017 $ */
/******************************************************************************
   pl/sql package             :  XXFIN_STAFF_IOU_UTIL_PKG
   file                       :  XXFIN_STAFF_IOU_UTIL_PKG.pkh
   author                     :  Saravana Gowtham
   date                       :  22-APR-2020
   purpose                    :  Staff IOU Process utility package
-- -----------------------------------------------------------------------------
-- modification:
-- dd-mon-yyyy - <<abc>> - explanation
-- -----------------------------------------------------------------------------
*******************************************************************************/

procedure cancel_iou(P_iou_id in varchar2);

procedure get_diff_amount (P_iou_id in varchar2 , P_bill_amt in varchar2, X_AMT_OUT OUT VARCHAR2);

procedure close_iou(P_iou_id in varchar2);

procedure validate_division(p_division in varchar2,p_amount in varchar2, X_FLAG OUT VARCHAR2);

procedure bill_attach_count (p_iou_id in varchar2, X_FLAG OUT VARCHAR2);

procedure SEND_FYI (ERRBUF out varchar2, RETCODE  out varchar2, p_iou_id in varchar2);

procedure generate_iou_num (p_iou_id in varchar2);

FUNCTION get_email (p_per_id             IN     NUMBER)
  return varchar2;

FUNCTION Get_Business_Days
(v_Begin_Date DATE,v_End_Date DATE) RETURN NUMBER;

PROCEDURE xxfin_interface_inv ( P_PTYCSH_HDR_ID             IN       NUMBER,
                          p_err_code                  out      VARCHAR2,
                          p_err_msg                   out      VARCHAR2);
                          
PROCEDURE xxfin_create_inv_api( p_group_id                  IN       NUMBER,
                                p_org_id                    IN       NUMBER,
                                p_err_code                  out      VARCHAR2,
                                p_err_msg                   out      VARCHAR2);

PROCEDURE invoice_process   ( errbuff                     out  nocopy  VARCHAR2,
                              retcode                     out  nocopy  VARCHAR2,
                              p_req_num                   IN       VARCHAR2);

procedure create_attachment(P_PTYCSH_HDR_ID in varchar2 , p_invoice_num in varchar2,
                            P_ORG_ID        in NUMBER,
                            errbuff                     out  nocopy  VARCHAR2,
                            retcode                     out  nocopy  VARCHAR2) ;


end XXFIN_STAFF_IOU_UTIL_PKG;
/
 
EXIT;
EOF