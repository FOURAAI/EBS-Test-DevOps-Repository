SET PAGESIZE 0 FEEDBACK OFF DEFINE OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500
-- =========================================================================
-- $header$
--
-- name      : xxhw_fin_pettycash_v.sql
--
-- description     : view creation scripts for Haya Water Finance Petty Cash
--
--
-- who                                            remarks                        date
-- --------------                  --------------------------------------- ------------------
-- gowtham raam.j -4iapps                     0.1 - dev version               26-FEB-2020
-- =========================================================================

/*      view - 1      :     Petty Cash Header View  */

CREATE OR REPLACE FORCE VIEW APPS.XXFIN_AP_PETTYCASH_HDR_V
(
    ROW_ID,
    PTYCSH_HDR_ID,
    REQUEST_NUMBER,
    REQUEST_NUM_DESC,
    GOVERNATE,
    SEGMENT1_GOV_DESC,
    VENDOR_NAME,
    VENDOR_SITE_NAME,
    REQUEST_DATE,
    REQUEST_DATE_DESC,
    OPENING_BAL,
    RECEIPTS,
    RECEIPT_AMOUNT,
    RECEIPT_LINE_CNT,
    CLAIM_AMOUNT,
    CLAIM__LINE_AMOUNT,
    CLAIM_LINE_CNT,
    STAFF_IOU_AMOUNT,
    STAFF_IOU_NUMBER,
    CLOSING_BALANCE,
    CLOSING_BAL_DESC,
    CASH_IN_HAND,
    LOCATION_NAME,
    DIVISION,
    DIVISION_NAME,
    DIV_PROCESSED,
    INVOICE_NUMBER,
    PAID_STATUS,
    PAID_STATUS_DESC,
    UPD_IMG,
    BUDGET_APPROVAL,
    ERROR_MSG,
    ERROR_FLAG,
    STATUS,
    WF_STATUS,
    WF_ITEM_KEY,
    WF_ITEM_TYPE,
    PENDING_WITH,
    REQUESTED_BY,
    REQUESTED_FOR,
    FLOW_ITERATION,
    ATTRIBUTE_CATEGORY,
    ATTRIBUTE1,
    ATTRIBUTE2,
    ATTRIBUTE3,
    ATTRIBUTE4,
    ATTRIBUTE5,
    ATTRIBUTE6,
    ATTRIBUTE7,
    ATTRIBUTE8,
    ATTRIBUTE9,
    ATTRIBUTE10,
    CREATED_BY,
    CREATION_DATE,
    LAST_UPDATED_BY,
    LAST_UPDATE_DATE,
    LAST_UPDATE_LOGIN
)
BEQUEATH DEFINER
AS
    SELECT ROWID
               row_id,
           xapht.ptycsh_hdr_id,
           xapht.request_number,
           CASE
               WHEN xapht.status = 'Draft' THEN 'RequestNumberTxt'
               WHEN xapht.status = 'InProgress' THEN 'RequestNumberTxt'
               WHEN xapht.status = 'Rejected' THEN 'RequestNumberTxt'
               ELSE 'RequestNumberLink'
           END
               Request_Num_Desc,
           xapht.governate,
           (SELECT description
              FROM fnd_lookup_values flv
             WHERE     lookup_type = 'XXFIN_PC_GOVERNARATE'
                   AND language = 'US'
                   AND xapht.governate = flv.lookup_code)
               segment1_gov_desc,
           xapht.vendor_name,
           xapht.vendor_site_name,
           xapht.request_date,
           NVL (xapht.request_date, SYSDATE)
               request_date_desc,
           xapht.opening_bal,
           xapht.receipts,
           NVL ((SELECT SUM (R_AMOUNT)
                   FROM XXFIN_AP_PETTYCASH_RECPT_V xaprt
                  WHERE xapht.ptycsh_hdr_id = xaprt.ptycsh_hdr_id),
                0)
               receipt_amount,
           NVL ((SELECT COUNT (*)
                   FROM XXFIN_AP_PETTYCASH_RECPT_V xaprt
                  WHERE xapht.ptycsh_hdr_id = xaprt.ptycsh_hdr_id),
                0)
               receipt_lines_CNT,
           xapht.claim_amount,
           NVL ((SELECT SUM (AMOUNT_IN_OMR)
                   FROM XXFIN_AP_PETTYCASH_DTL_V xapdt
                  WHERE xapht.ptycsh_hdr_id = xapdt.ptycsh_hdr_id),
                0)
               claim__line_amount,
           NVL ((SELECT COUNT (*)
                   FROM XXFIN_AP_PETTYCASH_DTL_V xapdt
                  WHERE xapht.ptycsh_hdr_id = xapdt.ptycsh_hdr_id),
                0)
               claim_line_CNT,
           xapht.staff_iou_amount,
           xapht.staff_iou_number,
           xapht.closing_balance,
             (  NVL (xapht.opening_bal, 0)
              + NVL ((SELECT SUM (R_AMOUNT)
                        FROM XXFIN_AP_PETTYCASH_RECPT_V xaprt
                       WHERE xapht.ptycsh_hdr_id = xaprt.ptycsh_hdr_id),
                     0))
           - (NVL ((SELECT SUM (AMOUNT_IN_OMR)
                      FROM XXFIN_AP_PETTYCASH_DTL_V xapdt
                     WHERE xapht.ptycsh_hdr_id = xapdt.ptycsh_hdr_id),
                   0))
           - NVL (xapht.staff_iou_amount, 0)
               closing_bal_desc,
           xapht.cash_in_hand,
           xapht.location_name,
           xapht.division,
           (SELECT description
              FROM fnd_lookup_values flv
             WHERE     lookup_type = 'XXFIN_PC_CLAIM_NO_SEQ'
                   AND xapht.division = flv.lookup_code
                   AND flv.language = 'US')
               division_name,
           xapht.DIV_PROCESSED,
           xapht.invoice_number,
           xapht.paid_status,
           CASE
               WHEN (SELECT NVL (COUNT (*), 0)
                       FROM AP_invoice_payments_all
                      WHERE invoice_id =
                            (SELECT invoice_id
                               FROM ap_invoices_all
                              WHERE invoice_num = xapht.invoice_number)) =
                    0
               THEN
                   CASE
                       WHEN xapht.invoice_number IS NOT NULL
                       THEN
                           'In Progress'
                       ELSE
                           ''
                   END
               ELSE
                   'Paid'
           END
               paid_status_desc,
           CASE
               WHEN xapht.status = 'Draft' THEN 'UpdateImage'
               WHEN xapht.status = 'InProgress' THEN 'UpdateImage'
               WHEN xapht.status = 'Rejected' THEN 'UpdateImage'
               ELSE 'DisableImage'
           END
               Upd_Img,
           xapht.BUDGET_APPROVAL,
           xapht.ERROR_MSG,
           xapht.ERROR_FLAG,
           xapht.status,
           xapht.wf_status,
           xapht.wf_item_key,
           xapht.wf_item_type,
           xapht.pending_with,
           xapht.requested_by,
           xapht.requested_for,
           xapht.flow_iteration,
           xapht.attribute_category,
           xapht.attribute1,
           xapht.attribute2,
           xapht.attribute3,
           xapht.attribute4,
           xapht.attribute5,
           xapht.attribute6,
           xapht.attribute7,
           xapht.attribute8,
           xapht.attribute9,
           xapht.attribute10,
           xapht.created_by,
           xapht.creation_date,
           xapht.last_updated_by,
           xapht.last_update_date,
           xapht.last_update_login
      FROM xxfin_ap_pettycash_hdr_t xapht;




---------------------------------------------------------------------------------------------------------------------------------------

/* View -2     :        Petty Cash Detail Table */


CREATE OR REPLACE FORCE VIEW APPS.XXFIN_AP_PETTYCASH_DTL_V
(
    ROW_ID,
    PTYCSH_DTL_ID,
    PTYCSH_HDR_ID,
    REQUEST_NUMBER,
    DTL_NUM,
    DTL_NUM_DESC,
    DTL_NUM_ACT,
    SEGMENT1_GOV_DESC,
    EXPENSE_TYPE,
    EXPENSE_TYPE_DESC,
    DEPARTMENT,
    DEPARTMENT_DESC,
    ACCOUNT_NUM,
    ACCOUNT_DESCRIPTION,
    CURRENCY,
    AMOUNT,
    EXCHANGE_RATE_TYPE,
    EXCHANGE_RATE_DATE,
    EXCHANGE_RATE,
    AMOUNT_IN_OMR,
    BRIEF_NARRATION,
    BUDGET_RESULTS,
    CC_ID,
    CONCATENATED_SEGMENTS,
    UPD_IMG,
    DEL_IMG,
    ATTACH_RO_UPD,
    ATTACH_FILE,
    FILE_DESC,
    DIV_PROCESSED,
    OLD_PTYCSH_HDR_ID,
    OLD_PTYCSH_DTL_ID,
    ELIGIBLE_SEL_FLAG,
    ATTRIBUTE_CATEGORY,
    ATTRIBUTE1,
    ATTRIBUTE2,
    ATTRIBUTE3,
    ATTRIBUTE4,
    ATTRIBUTE5,
    ATTRIBUTE6,
    ATTRIBUTE7,
    ATTRIBUTE8,
    ATTRIBUTE9,
    ATTRIBUTE10,
    CREATED_BY_USERNAME,
    UPDATED_BY_USERNAME,
    CREATED_BY,
    CREATION_DATE,
    LAST_UPDATED_BY,
    LAST_UPDATE_DATE,
    LAST_UPDATE_LOGIN
)
BEQUEATH DEFINER
AS
    SELECT xapdt.ROWID
               row_id,
           xapdt.ptycsh_dtl_id,
           xapdt.ptycsh_hdr_id,
           xapht.request_number,
           xapdt.DTL_NUM,
           xapdt.DTL_NUM || '-' || NVL (location_name, division)
               DTL_NUM_DESC,
           xapdt.dtl_num_act,
           --xaphv.SEGMENT1_GOV_DESC,
           (SELECT description
              FROM fnd_lookup_values flv
             WHERE     lookup_type = 'XXFIN_PC_GOVERNARATE'
                   AND language = 'US'
                   AND xapht.governate = flv.lookup_code)
               segment1_gov_desc,
           xapdt.expense_type,
           CASE
               WHEN xapht.DIVISION = 'OPS'
               THEN
                   (SELECT meaning
                      FROM fnd_lookup_values fl_exp
                     WHERE     language = 'US'
                           AND lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST'
                           AND xapdt.expense_type = fl_exp.lookup_code)
               WHEN xapht.DIVISION = 'FIN'
               THEN
                   (SELECT meaning
                      FROM fnd_lookup_values fl_exp
                     WHERE     language = 'US'
                           AND lookup_type = 'XXFIN_PC_FINANCE_EXP_LIST'
                           AND xapdt.expense_type = fl_exp.lookup_code)
               WHEN xapht.DIVISION = 'ADM'
               THEN
                   (SELECT meaning
                      FROM fnd_lookup_values fl_exp
                     WHERE     language = 'US'
                           AND lookup_type = 'XXFIN_PC_HR_ADMIN_EXP_LIST'
                           AND xapdt.expense_type = fl_exp.lookup_code)
               WHEN xapht.DIVISION = 'LOG'
               THEN
                   (SELECT meaning
                      FROM fnd_lookup_values fl_exp
                     WHERE     language = 'US'
                           AND lookup_type = 'XXFIN_PC_LOGISTICS_EXP_LIST'
                           AND xapdt.expense_type = fl_exp.lookup_code)
           END
               expense_type_desc,
           xapdt.department,
           (SELECT fv.DESCRIPTION
              FROM FND_FLEX_VALUES_VL fv, fnd_flex_value_sets fvs
             WHERE     fvs.flex_value_set_id = fv.flex_value_set_id
                   AND fvs.flex_value_set_name = 'HAYA_COST_CENTER_VS'
                   AND fv.FLEX_VALUE = xapdt.department)
               department_desc,
           xapdt.account_num,
           xapdt.account_description,
           xapdt.currency,
           xapdt.amount,
           xapdt.exchange_rate_type,
           xapdt.exchange_rate_date,
           xapdt.exchange_rate,
           NVL (xapdt.exchange_rate * xapdt.amount, xapdt.amount)
               amount_in_omr,
           xapdt.brief_narration,
           xapdt.budget_results,
           xapdt.cc_id,
           xapdt.concatenated_segments,
           CASE
               WHEN xapht.status = 'Draft' THEN 'UpdateImage'
               WHEN xapht.status = 'InProgress' THEN 'UpdateImage'
               WHEN xapht.status = 'Rejected' THEN 'UpdateImage'
               ELSE 'DisableImage'
           END
               Upd_Img,
           CASE
               WHEN xapht.status = 'Draft' THEN 'DeleteImage'
               WHEN xapht.status = 'InProgress' THEN 'DeleteImage'
               WHEN xapht.status = 'Rejected' THEN 'DeleteImage'
               ELSE 'DisDeleteImage'
           END
               Del_Img,
           NVL (xapdt.attach_ro_upd, 'ROAttach')
               attach_ro_upd,
           xapdt.attach_file,
           xapdt.file_desc,
           xapdt.DIV_PROCESSED,
           xapdt.OLD_PTYCSH_HDR_ID,
           xapdt.OLD_PTYCSH_DTL_ID,
           xapdt.ELIGIBLE_SEL_FLAG,
           xapdt.attribute_category,
           xapdt.attribute1,
           xapdt.attribute2,
           xapdt.attribute3,
           xapdt.attribute4,
           xapdt.attribute5,
           xapdt.attribute6,
           xapdt.attribute7,
           xapdt.attribute8,
           xapdt.attribute9,
           xapdt.attribute10,
           fu_cBy.USER_NAME
               CREATED_BY_USERNAME,
           fu_upBy.USER_NAME
               UPDATED_BY_USERNAME,
           xapdt.created_by,
           xapdt.creation_date,
           xapdt.last_updated_by,
           xapdt.last_update_date,
           xapdt.last_update_login
      FROM apps.xxfin_ap_pettycash_dtl_t  xapdt,
           apps.xxfin_ap_pettycash_hdr_t  xapht,
           fnd_user                       fu_cBy,
           fnd_user                       fu_upBy
     WHERE     xapdt.ptycsh_hdr_id = xapht.ptycsh_hdr_id
           AND fu_cBy.user_id = xapdt.created_by
           AND fu_upBy.user_id = xapdt.last_updated_by;
---------------------------------------------------------------------------------------------------------------------------------------

/* View - 3    :      Petty Cash Receipt Detail Table */

CREATE OR REPLACE FORCE VIEW APPS.XXFIN_AP_PETTYCASH_RECPT_V
(
    ROW_ID,
    PTYCSH_RECPT_ID,
    PTYCSH_HDR_ID,
    REQUEST_NUMBER,
    R_MOP_RECEIVED,
    R_MOP_RECEIVED_DESC,
    R_REFERENCE_NUMBER,
    R_CURRENCY,
    R_AMOUNT,
    R_PAYMENT_RECEIVED_DATE,
    R_BRIEF_NARRATION,
    R_UPD_IMG,
    R_DEL_IMG,
    R_ATTACH_FILE,
    R_FILE_DESC,
    ATTRIBUTE_CATEGORY,
    ATTRIBUTE1,
    ATTRIBUTE2,
    ATTRIBUTE3,
    ATTRIBUTE4,
    ATTRIBUTE5,
    ATTRIBUTE6,
    ATTRIBUTE7,
    ATTRIBUTE8,
    ATTRIBUTE9,
    ATTRIBUTE10,
    CREATED_BY,
    CREATION_DATE,
    LAST_UPDATED_BY,
    LAST_UPDATE_DATE,
    LAST_UPDATE_LOGIN
)
BEQUEATH DEFINER
AS
    SELECT xaprt.ROWID
               row_id,
           xaprt.ptycsh_recpt_id,
           xaprt.ptycsh_hdr_id,
           xapht.request_number,
           xaprt.r_mop_received,
           flv.meaning
               r_mop_received_desc,
           xaprt.r_reference_number,
           xaprt.r_currency,
           xaprt.r_amount,
           xaprt.r_payment_received_date,
           xaprt.r_brief_narration,
           CASE
               WHEN xapht.status = 'Draft' THEN 'RUpdateImage'
               WHEN xapht.status = 'InProgress' THEN 'RUpdateImage'
               ELSE 'RDisableImage'
           END
               R_Upd_Img,
           CASE
               WHEN xapht.status = 'Draft' THEN 'RDeleteImage'
               WHEN xapht.status = 'InProgress' THEN 'RDeleteImage'
               ELSE 'RDisDeleteImage'
           END
               R_Del_Img,
           xaprt.r_attach_file,
           xaprt.r_file_desc,
           xaprt.attribute_category,
           xaprt.attribute1,
           xaprt.attribute2,
           xaprt.attribute3,
           xaprt.attribute4,
           xaprt.attribute5,
           xaprt.attribute6,
           xaprt.attribute7,
           xaprt.attribute8,
           xaprt.attribute9,
           xaprt.attribute10,
           xaprt.created_by,
           xaprt.creation_date,
           xaprt.last_updated_by,
           xaprt.last_update_date,
           xaprt.last_update_login
      FROM apps.xxfin_ap_pettycash_recpt_t  xaprt,
           apps.xxfin_ap_pettycash_hdr_t    xapht,
           fnd_lookup_values                flv
     WHERE     xaprt.ptycsh_hdr_id = xapht.ptycsh_hdr_id
           AND flv.lookup_type = 'XXFIN_PC_PAYMENT_MODE'
           AND flv.language = 'US'
           AND xaprt.r_mop_received = flv.lookup_code(+);



      
---------------------------------------------------------------------------------------------------------------------------------------

/* View -4     :       Sub Contract SME History View */      
      


CREATE OR REPLACE FORCE VIEW APPS.XXFIN_AP_PETTYCASH_WF_V
(
    ROW_ID,
    PTYCSH_WF_HIST_ID,
    PTYCSH_DTL_ID,
    PTYCSH_HDR_ID,
    WF_ITEM_KEY,
    ITERATION,
    RESPONSE,
    APPROVER_COMMENTS,
    APPROVER_ID,
    APPROVER_USER_NAME,
    RESPONSE_DATE,
    SUBMISSION_DATE,
    RESPONDER_ROLE,
    RESPONDER_NAME,
    EMPLOYEE_NUMBER,
    REMARKS,
    ATTRIBUTE_CATEGORY,
    ATTRIBUTE1,
    ATTRIBUTE2,
    ATTRIBUTE3,
    ATTRIBUTE4,
    ATTRIBUTE5,
    ATTRIBUTE6,
    ATTRIBUTE7,
    ATTRIBUTE8,
    ATTRIBUTE9,
    ATTRIBUTE10,
    RESPONDED_USER_NAME,
    CREATED_BY,
    CREATION_DATE,
    LAST_UPDATED_BY,
    LAST_UPDATE_DATE,
    LAST_UPDATE_LOGIN
)
BEQUEATH DEFINER
AS
    SELECT xapwt.ROWID                               row_id,
           xapwt.ptycsh_wf_hist_id,
           xapwt.ptycsh_dtl_id,
           xapwt.ptycsh_hdr_id,
           xapwt.wf_item_key,
           xapwt.iteration,
           xapwt.response,
           xapwt.approver_comments,
           xapwt.approver_id,
           xapwt.approver_user_name,
           xapwt.response_date,
           xapwt.submission_date,
           NVL (papf.full_name, wr.display_name)     Responder_role,
           papf_resp.full_name                       Responder_name,
           papf_resp.employee_number                 employee_number,
           xapwt.remarks,
           xapwt.attribute_category,
           xapwt.attribute1,
           xapwt.attribute2,
           xapwt.attribute3,
           xapwt.attribute4,
           xapwt.attribute5,
           xapwt.attribute6,
           xapwt.attribute7,
           xapwt.attribute8,
           xapwt.attribute9,
           xapwt.attribute10,
           fu.user_name                              responded_user_name,
           xapwt.created_by,
           xapwt.creation_date,
           xapwt.last_updated_by,
           xapwt.last_update_date,
           xapwt.last_update_login
      FROM xxfin_ap_pettycash_wf_t  xapwt,
           (SELECT *
              FROM per_all_people_f papf
             WHERE SYSDATE BETWEEN papf.effective_start_date
                               AND papf.effective_end_date) papf,
           fnd_user                 fu,
           fnd_user                 fu_papf,
           --           fnd_user                 fu_resp,
            (SELECT *
               FROM per_all_people_f papf
              WHERE SYSDATE BETWEEN papf.effective_start_date
                                AND papf.effective_end_date) papf_resp,
           wf_roles                 wr
     WHERE     1 = 1
           AND xapwt.LAST_UPDATED_BY = fu.user_id
           AND xapwt.approver_user_name = fu_papf.user_name(+)
           AND xapwt.approver_user_name = wr.name(+)
           AND fu_papf.employee_id = papf.person_id(+)
           AND fu.employee_id = papf_resp.person_id(+);



---------------------------------------------------------------------------------------------------------------------------------------

/* View - 5     :      Petty Cash Sequence View */      


CREATE OR REPLACE FORCE VIEW APPS.XXFIN_AP_PETTYCASH_SEQ_V
(
    ROW_ID,
    PTYCSH_SEQ_ID,
    DIVISION_NAME,
    SEQ_PREFIX,
    STARTING_NUMBER,
    DATE_START,
    DATE_END,
    CURRENT_RUNNING_SEQUENCE,
    CUR_RUN_SEQUENCE,
    ATTRIBUTE_CATEGORY,
    ATTRIBUTE1,
    ATTRIBUTE2,
    ATTRIBUTE3,
    ATTRIBUTE4,
    ATTRIBUTE5,
    ATTRIBUTE6,
    ATTRIBUTE7,
    ATTRIBUTE8,
    ATTRIBUTE9,
    ATTRIBUTE10,
    CREATED_BY,
    CREATION_DATE,
    LAST_UPDATED_BY,
    LAST_UPDATE_DATE,
    LAST_UPDATE_LOGIN
)
BEQUEATH DEFINER
AS
    SELECT ROWID
               row_id,
           ptycsh_seq_id,
           division_name,
           seq_prefix,
           starting_number,
           date_start,
           date_end,
           current_running_sequence,
           seq_prefix || '-' || current_running_sequence
               cur_run_sequence,
           attribute_category,
           attribute1,
           attribute2,
           attribute3,
           attribute4,
           attribute5,
           attribute6,
           attribute7,
           attribute8,
           attribute9,
           attribute10,
           created_by,
           creation_date,
           last_updated_by,
           last_update_date,
           last_update_login
      FROM xxfin_ap_pettycash_seq_t
     WHERE 1 = 1;


---------------------------------------------------------------------------------------------------------------------------------------

/* View -     :      IOU Views */      

CREATE OR REPLACE FORCE VIEW APPS.XXFIN_IOU_EMP_V
(
    PERSON_ID,
    EMPLOYEE_NUMBER,
    FULL_NAME,
    EMAIL_ADDRESS
)
BEQUEATH DEFINER
AS
      SELECT person_id,
             employee_number,
             full_name,
             email_address
        FROM per_all_people_f
       WHERE     TRUNC (SYSDATE) BETWEEN effective_start_date
                                     AND effective_end_date
             AND current_employee_flag = 'Y'
    ORDER BY 1;

CREATE OR REPLACE FORCE VIEW APPS.XXFIN_IOU_REC_HIST_V
(
    IOU_ID,
    CREATED_BY,
    CREATION_DATE,
    UPDATED_BY,
    LAST_UPDATE_DATE
)
BEQUEATH DEFINER
AS
    SELECT iou.IOU_ID,
           papf.full_name      created_by,
           iou.CREATION_DATE,
           papf1.full_name     updated_by,
           iou.LAST_UPDATE_DATE
      FROM xxfin_staff_iou   iou,
           fnd_user          fu,
           fnd_user          fu1,
           per_all_people_f  papf,
           per_all_people_f  papf1
     WHERE     iou.created_by = fu.user_id
           AND iou.LAST_UPDATED_BY = fu1.user_id
           AND papf.person_id = fu.employee_id
           AND papf1.person_id = fu1.employee_id
           AND TRUNC (SYSDATE) BETWEEN papf.effective_start_date
                                   AND papf.effective_end_date
           AND TRUNC (SYSDATE) BETWEEN papf1.effective_start_date
                                   AND papf1.effective_end_date;

CREATE OR REPLACE FORCE VIEW APPS.XXFIN_IOU_STATUS_V
(
    STATUS
)
BEQUEATH DEFINER
AS
    SELECT 'Open' status FROM DUAL
    UNION ALL
    SELECT 'Closed' status FROM DUAL;


CREATE OR REPLACE FORCE VIEW APPS.XXFIN_STAFF_IOU_CURR_V
(
    CURRENCY_CODE
)
BEQUEATH DEFINER
AS
    SELECT DISTINCT currency_code
      FROM fnd_currencies_tl;


CREATE OR REPLACE FORCE VIEW APPS.XXFIN_STAFF_IOU_DIVISION_V
(
    LOOKUP_CODE,
    MEANING,
    DESCRIPTION,
    ENABLED_FLAG,
    EXCEED_200
)
BEQUEATH DEFINER
AS
    SELECT LOOKUP_CODE,
           MEANING,
           DESCRIPTION,
           ENABLED_FLAG,
           tag     exceed_200
      FROM APPLSYS.fnd_lookup_values
     WHERE     LOOKUP_TYPE = 'XXFIN_PC_CLAIM_NO_SEQ'
           AND language = 'US'
           AND ENABLED_FLAG = 'Y'
           AND LOOKUP_CODE LIKE '%IOU';


CREATE OR REPLACE FORCE VIEW APPS.XXFIN_STAFF_IOU_REASON_V
(
    LOOKUP_CODE,
    MEANING,
    DESCRIPTION,
    ENABLED_FLAG
)
BEQUEATH DEFINER
AS
    SELECT LOOKUP_CODE,
           MEANING,
           DESCRIPTION,
           ENABLED_FLAG
      FROM APPLSYS.fnd_lookup_values
     WHERE     LOOKUP_TYPE = 'XXFIN_STAFF_IOU_REASON'
           AND language = 'US'
           AND ENABLED_FLAG = 'Y';


CREATE OR REPLACE FORCE VIEW APPS.XXFIN_STAFF_IOU_V
(
    IOU_ID,
    IOU_NUM,
    PERSON_ID,
    USER_NAME,
    EMPLOYEE_NUMBER,
    FULL_NAME,
    DIVISION_ID,
    DIVISION,
    DIVISION_NAME,
    IOU_GIVEN_DATE,
    CURRENCY,
    AMOUNT,
    EXCHANGE_RATE,
    EXCHANGE_RATE_DATE,
    EXCHANGE_RATE_TYPE,
    AMOUNT_OMR,
    REMARKS,
    STATUS,
    IOU_CLOSED_DATE,
    REASON_FOR_CLOSING,
    BILL_AMOUNT,
    BILL_ATTACHMENT,
    DIFF_AMOUNT,
    DIFF_AMOUNT_SETTLED,
    ATTACHMENT,
    ATTRIBUTE_CATEGORY,
    ATTRIBUTE1,
    ATTRIBUTE2,
    ATTRIBUTE3,
    ATTRIBUTE4,
    ATTRIBUTE5,
    ATTRIBUTE6,
    ATTRIBUTE7,
    ATTRIBUTE8,
    ATTRIBUTE9,
    ATTRIBUTE10,
    ATTRIBUTE11,
    ATTRIBUTE12,
    ATTRIBUTE13,
    ATTRIBUTE14,
    ATTRIBUTE15,
    ATTRIBUTE16,
    ATTRIBUTE17,
    ATTRIBUTE18,
    ATTRIBUTE19,
    ATTRIBUTE20,
    CREATION_DATE,
    CREATED_BY,
    LAST_UPDATE_DATE,
    LAST_UPDATED_BY,
    LAST_UPDATE_LOGIN,
    UPDATE_ICON,
    FOCAL_USER_NAME,
    FOCAL_NAME,
    FOCAL_EMAIL,
    EMP_EMAIL,
    SUPERVISOR_ID
)
BEQUEATH DEFINER
AS
      SELECT iou.IOU_ID,
             iou.IOU_NUM,
             iou.PERSON_ID,
             fu.user_name,
             papf.EMPLOYEE_NUMBER,
             papf.full_name,
             iou.DIVISION_ID,
             iou.DIVISION,
             div.DESCRIPTION
                 DIVISION_NAME,
             IOU_GIVEN_DATE,
             iou.CURRENCY,
             iou.AMOUNT,
             iou.EXCHANGE_RATE,
             EXCHANGE_RATE_DATE,
             iou.EXCHANGE_RATE_TYPE,
             iou.AMOUNT_OMR,
             REMARKS,
             iou.STATUS,
             iou.IOU_CLOSED_DATE,
             iou.REASON_FOR_CLOSING,
             iou.BILL_AMOUNT,
             iou.BILL_ATTACHMENT,
             iou.DIFF_AMOUNT,
             iou.DIFF_AMOUNT_SETTLED,
             iou.ATTACHMENT,
             iou.ATTRIBUTE_CATEGORY,
             iou.ATTRIBUTE1,
             iou.ATTRIBUTE2,
             iou.ATTRIBUTE3,
             iou.ATTRIBUTE4,
             iou.ATTRIBUTE5,
             iou.ATTRIBUTE6,
             iou.ATTRIBUTE7,
             iou.ATTRIBUTE8,
             iou.ATTRIBUTE9,
             iou.ATTRIBUTE10,
             iou.ATTRIBUTE11,
             iou.ATTRIBUTE12,
             iou.ATTRIBUTE13,
             iou.ATTRIBUTE14,
             iou.ATTRIBUTE15,
             iou.ATTRIBUTE16,
             iou.ATTRIBUTE17,
             iou.ATTRIBUTE18,
             iou.ATTRIBUTE19,
             iou.ATTRIBUTE20,
             iou.CREATION_DATE,
             iou.CREATED_BY,
             iou.LAST_UPDATE_DATE,
             iou.LAST_UPDATED_BY,
             iou.LAST_UPDATE_LOGIN,
             DECODE (iou.STATUS, 'Open', 'Update', 'Updatedis')
                 Update_icon,
             fu1.user_name
                 focal_user_name,
             papf1.full_name
                 focal_name,
             papf1.email_address
                 Focal_email,
             papf.email_address
                 emp_email,
             paaf.supervisor_id
        FROM xxfin_staff_iou           iou,
             per_all_people_f          papf,
             per_all_assignments_f     paaf,
             fnd_user                  fu,
             fnd_user                  fu1,
             per_all_people_f          papf1,
             XXFIN_STAFF_IOU_DIVISION_V div
       WHERE     1 = 1
             AND iou.person_id = papf.person_id
             AND papf.person_id = paaf.person_id
             AND papf.person_id = fu.employee_id
             AND iou.division = div.LOOKUP_CODE
             AND (fu.end_date IS NULL OR fu.end_date > SYSDATE)
             AND iou.created_by = fu1.user_id
             AND (fu1.end_date IS NULL OR fu1.end_date > SYSDATE)
             AND fu1.employee_id = papf1.person_id
             AND TRUNC (SYSDATE) BETWEEN papf1.effective_start_date
                                     AND papf1.effective_end_date
             AND TRUNC (SYSDATE) BETWEEN papf.effective_start_date
                                     AND papf.effective_end_date
             AND TRUNC (SYSDATE) BETWEEN paaf.effective_start_date
                                     AND paaf.effective_end_date
             AND papf.current_employee_flag = 'Y'
             AND paaf.ASSIGNMENT_TYPE = 'E'
    --and papf1.current_employee_flag = 'Y'
    ORDER BY iou.IOU_ID DESC;



 
EXIT;
EOF