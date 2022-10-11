SET PAGESIZE 0 FEEDBACK OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500

-- =========================================================================
-- $header$
--
-- name             : xxhw_fin_pettycash_t.sql
--
-- description      : Table creation scripts for Haya Water Finance Petty Cash
--
--
-- who                                            remarks                        date
-- --------------                  --------------------------------------- ------------------
-- gowtham raam.j -4iapps                     0.1 - dev version               26-FEB-2020
-- =========================================================================

/*table -1     :     Petty Cash Header Table */


CREATE TABLE XXFIN_AP_PETTYCASH_HDR_T
(
  PTYCSH_HDR_ID       NUMBER,
  REQUEST_NUMBER      VARCHAR2(500 BYTE),
  GOVERNATE           VARCHAR2(500 BYTE),
  VENDOR_NAME         VARCHAR2(500 BYTE),
  VENDOR_SITE_NAME    VARCHAR2(500 BYTE),
  REQUEST_DATE        DATE,
  OPENING_BAL         NUMBER,
  RECEIPTS            NUMBER,
  CLAIM_AMOUNT        NUMBER,
  STAFF_IOU_AMOUNT    NUMBER,
  CLOSING_BALANCE     NUMBER,
  CASH_IN_HAND        NUMBER,
  LOCATION_NAME       VARCHAR2(240 BYTE),
  DIVISION            VARCHAR2(240 BYTE),
  WF_STATUS           VARCHAR2(50 BYTE),
  WF_ITEM_KEY         VARCHAR2(240 BYTE),
  WF_ITEM_TYPE        VARCHAR2(240 BYTE),
  PENDING_WITH        VARCHAR2(240 BYTE),
  REQUESTED_BY        VARCHAR2(240 BYTE),
  REQUESTED_FOR       VARCHAR2(240 BYTE),
  FLOW_ITERATION      NUMBER,
  ATTRIBUTE_CATEGORY  VARCHAR2(240 BYTE),
  ATTRIBUTE1          VARCHAR2(240 BYTE),
  ATTRIBUTE2          VARCHAR2(240 BYTE),
  ATTRIBUTE3          VARCHAR2(240 BYTE),
  ATTRIBUTE4          VARCHAR2(240 BYTE),
  ATTRIBUTE5          VARCHAR2(240 BYTE),
  ATTRIBUTE6          VARCHAR2(240 BYTE),
  ATTRIBUTE7          VARCHAR2(240 BYTE),
  ATTRIBUTE8          VARCHAR2(240 BYTE),
  ATTRIBUTE9          VARCHAR2(240 BYTE),
  ATTRIBUTE10         VARCHAR2(240 BYTE),
  CREATED_BY          NUMBER                    NOT NULL,
  CREATION_DATE       DATE                      NOT NULL,
  LAST_UPDATED_BY     NUMBER                    NOT NULL,
  LAST_UPDATE_DATE    DATE                      NOT NULL,
  LAST_UPDATE_LOGIN   NUMBER,
  STATUS              VARCHAR2(240 BYTE),
  DIV_PROCESSED       VARCHAR2(10 BYTE),
  INVOICE_NUMBER      VARCHAR2(240 BYTE),
  PAID_STATUS         VARCHAR2(240 BYTE),
  STAFF_IOU_NUMBER    NUMBER,
  ERROR_MSG           VARCHAR2(2000 BYTE),
  ERROR_FLAG          VARCHAR2(240 BYTE),
  BUDGET_APPROVAL     VARCHAR2(240 BYTE)
);


ALTER TABLE xxfin_ap_pettycash_hdr_t
    ADD CONSTRAINT xxfin_ap_pettycash_hdr_t_pk PRIMARY KEY (ptycsh_hdr_id)
            USING INDEX;

---------------------------------------------------------------------------------------------------------------------------------------

/* table -2     :       Petty Cash Detail Table */


CREATE TABLE XXFIN_AP_PETTYCASH_DTL_T
(
  PTYCSH_DTL_ID          NUMBER                 NOT NULL,
  PTYCSH_HDR_ID          NUMBER,
  EXPENSE_TYPE           VARCHAR2(240 BYTE),
  DEPARTMENT             VARCHAR2(240 BYTE),
  ACCOUNT_NUM            VARCHAR2(240 BYTE),
  ACCOUNT_DESCRIPTION    VARCHAR2(2000 BYTE),
  CURRENCY               VARCHAR2(240 BYTE),
  AMOUNT                 NUMBER,
  EXCHANGE_RATE_TYPE     VARCHAR2(240 BYTE),
  EXCHANGE_RATE_DATE     DATE,
  EXCHANGE_RATE          VARCHAR2(240 BYTE),
  BRIEF_NARRATION        VARCHAR2(240 BYTE),
  BUDGET_RESULTS         VARCHAR2(240 BYTE),
  ATTACH_FILE            BLOB,
  FILE_DESC              VARCHAR2(2000 BYTE),
  ATTRIBUTE_CATEGORY     VARCHAR2(240 BYTE),
  ATTRIBUTE1             VARCHAR2(240 BYTE),
  ATTRIBUTE2             VARCHAR2(240 BYTE),
  ATTRIBUTE3             VARCHAR2(240 BYTE),
  ATTRIBUTE4             VARCHAR2(240 BYTE),
  ATTRIBUTE5             VARCHAR2(240 BYTE),
  ATTRIBUTE6             VARCHAR2(240 BYTE),
  ATTRIBUTE7             VARCHAR2(240 BYTE),
  ATTRIBUTE8             VARCHAR2(240 BYTE),
  ATTRIBUTE9             VARCHAR2(240 BYTE),
  ATTRIBUTE10            VARCHAR2(240 BYTE),
  CREATED_BY             NUMBER                 NOT NULL,
  CREATION_DATE          DATE                   NOT NULL,
  LAST_UPDATED_BY        NUMBER                 NOT NULL,
  LAST_UPDATE_DATE       DATE                   NOT NULL,
  LAST_UPDATE_LOGIN      NUMBER,
  CC_ID                  NUMBER(20),
  CONCATENATED_SEGMENTS  VARCHAR2(240 BYTE),
  DIV_PROCESSED          VARCHAR2(10 BYTE),
  OLD_PTYCSH_HDR_ID      NUMBER,
  OLD_PTYCSH_DTL_ID      NUMBER,
  ELIGIBLE_SEL_FLAG      VARCHAR2(10 BYTE),
  DTL_NUM                NUMBER,
  DTL_NUM_ACT            VARCHAR2(240 BYTE),
  ATTACH_RO_UPD          VARCHAR2(240 BYTE)
);

ALTER TABLE xxfin_ap_pettycash_dtl_t
    ADD CONSTRAINT xxfin_ap_pettycash_dtl_pk PRIMARY KEY (ptycsh_dtl_id)
            USING INDEX;


---------------------------------------------------------------------------------------------------------------------------------------

/* table -3     :       Petty Cash Receipt Detail Table */

CREATE TABLE XXFIN_AP_PETTYCASH_RECPT_T
(
  PTYCSH_RECPT_ID          NUMBER               NOT NULL,
  PTYCSH_HDR_ID            NUMBER,
  R_MOP_RECEIVED           VARCHAR2(240 BYTE),
  R_REFERENCE_NUMBER       VARCHAR2(240 BYTE),
  R_CURRENCY               VARCHAR2(240 BYTE),
  R_AMOUNT                 NUMBER,
  R_PAYMENT_RECEIVED_DATE  DATE,
  R_BRIEF_NARRATION        VARCHAR2(2000 BYTE),
  R_ATTACH_FILE            BLOB,
  R_FILE_DESC              VARCHAR2(2000 BYTE),
  ATTRIBUTE_CATEGORY       VARCHAR2(240 BYTE),
  ATTRIBUTE1               VARCHAR2(240 BYTE),
  ATTRIBUTE2               VARCHAR2(240 BYTE),
  ATTRIBUTE3               VARCHAR2(240 BYTE),
  ATTRIBUTE4               VARCHAR2(240 BYTE),
  ATTRIBUTE5               VARCHAR2(240 BYTE),
  ATTRIBUTE6               VARCHAR2(240 BYTE),
  ATTRIBUTE7               VARCHAR2(240 BYTE),
  ATTRIBUTE8               VARCHAR2(240 BYTE),
  ATTRIBUTE9               VARCHAR2(240 BYTE),
  ATTRIBUTE10              VARCHAR2(240 BYTE),
  CREATED_BY               NUMBER               NOT NULL,
  CREATION_DATE            DATE                 NOT NULL,
  LAST_UPDATED_BY          NUMBER               NOT NULL,
  LAST_UPDATE_DATE         DATE                 NOT NULL,
  LAST_UPDATE_LOGIN        NUMBER
);

ALTER TABLE xxfin_ap_pettycash_recpt_t
    ADD CONSTRAINT xxfin_ap_pettycash_recpt_pk PRIMARY KEY (ptycsh_recpt_id)
            USING INDEX;
			


---------------------------------------------------------------------------------------------------------------------------------------

/* table -4     :       Petty Cash Workflow  Table */

CREATE TABLE XXFIN_AP_PETTYCASH_WF_T
(
  PTYCSH_WF_HIST_ID   NUMBER                    NOT NULL,
  PTYCSH_DTL_ID       NUMBER,
  PTYCSH_HDR_ID       NUMBER,
  WF_ITEM_KEY         VARCHAR2(240 BYTE),
  ITERATION           NUMBER,
  RESPONSE            VARCHAR2(240 BYTE),
  APPROVER_COMMENTS   VARCHAR2(2000 BYTE),
  APPROVER_ID         NUMBER,
  APPROVER_USER_NAME  VARCHAR2(240 BYTE),
  RESPONSE_DATE       DATE,
  SUBMISSION_DATE     DATE,
  REMARKS             VARCHAR2(4000 BYTE),
  ATTRIBUTE_CATEGORY  VARCHAR2(240 BYTE),
  ATTRIBUTE1          VARCHAR2(240 BYTE),
  ATTRIBUTE2          VARCHAR2(240 BYTE),
  ATTRIBUTE3          VARCHAR2(240 BYTE),
  ATTRIBUTE4          VARCHAR2(240 BYTE),
  ATTRIBUTE5          VARCHAR2(240 BYTE),
  ATTRIBUTE6          VARCHAR2(240 BYTE),
  ATTRIBUTE7          VARCHAR2(240 BYTE),
  ATTRIBUTE8          VARCHAR2(240 BYTE),
  ATTRIBUTE9          VARCHAR2(240 BYTE),
  ATTRIBUTE10         VARCHAR2(240 BYTE),
  CREATED_BY          NUMBER                    NOT NULL,
  CREATION_DATE       DATE                      NOT NULL,
  LAST_UPDATED_BY     NUMBER                    NOT NULL,
  LAST_UPDATE_DATE    DATE                      NOT NULL,
  LAST_UPDATE_LOGIN   NUMBER
);

ALTER TABLE xxfin_ap_pettycash_wf_t
    ADD CONSTRAINT xxfin_ap_pettycash_wf_pk PRIMARY KEY (ptycsh_wf_hist_id)
            USING INDEX;


---------------------------------------------------------------------------------------------------------------------------------------

/* table - 5    :       Petty Cash Sequence  Table */


CREATE TABLE XXFIN_AP_PETTYCASH_SEQ_T
(
  PTYCSH_SEQ_ID             NUMBER,
  DIVISION_NAME             VARCHAR2(240 BYTE),
  SEQ_PREFIX                VARCHAR2(240 BYTE),
  STARTING_NUMBER           NUMBER,
  DATE_START                DATE,
  DATE_END                  DATE,
  CURRENT_RUNNING_SEQUENCE  NUMBER,
  ATTRIBUTE_CATEGORY        VARCHAR2(240 BYTE),
  ATTRIBUTE1                VARCHAR2(240 BYTE),
  ATTRIBUTE2                VARCHAR2(240 BYTE),
  ATTRIBUTE3                VARCHAR2(240 BYTE),
  ATTRIBUTE4                VARCHAR2(240 BYTE),
  ATTRIBUTE5                VARCHAR2(240 BYTE),
  ATTRIBUTE6                VARCHAR2(240 BYTE),
  ATTRIBUTE7                VARCHAR2(240 BYTE),
  ATTRIBUTE8                VARCHAR2(240 BYTE),
  ATTRIBUTE9                VARCHAR2(240 BYTE),
  ATTRIBUTE10               VARCHAR2(240 BYTE),
  CREATED_BY                NUMBER              NOT NULL,
  CREATION_DATE             DATE                NOT NULL,
  LAST_UPDATED_BY           NUMBER              NOT NULL,
  LAST_UPDATE_DATE          DATE                NOT NULL,
  LAST_UPDATE_LOGIN         NUMBER
);



---------------------------------------------------------------------------------------------------------------------------------------

/* table - 6    :       IOU  Table */


CREATE TABLE XXFIN_STAFF_IOU
(
  IOU_ID               NUMBER,
  IOU_NUM              VARCHAR2(100 BYTE),
  PERSON_ID            NUMBER,
  DIVISION_ID          NUMBER,
  IOU_GIVEN_DATE       DATE,
  CURRENCY             VARCHAR2(10 BYTE),
  AMOUNT               VARCHAR2(20 BYTE),
  EXCHANGE_RATE        VARCHAR2(20 BYTE),
  EXCHANGE_RATE_DATE   DATE,
  EXCHANGE_RATE_TYPE   VARCHAR2(100 BYTE),
  AMOUNT_OMR           VARCHAR2(20 BYTE),
  REMARKS              VARCHAR2(3000 BYTE),
  IOU_CLOSED_DATE      DATE,
  REASON_FOR_CLOSING   VARCHAR2(200 BYTE),
  BILL_AMOUNT          NUMBER,
  BILL_ATTACHMENT      NUMBER,
  DIFF_AMOUNT          NUMBER,
  DIFF_AMOUNT_SETTLED  VARCHAR2(20 BYTE),
  ATTACHMENT           VARCHAR2(1 BYTE),
  ATTRIBUTE_CATEGORY   VARCHAR2(240 BYTE),
  ATTRIBUTE1           VARCHAR2(240 BYTE),
  ATTRIBUTE2           VARCHAR2(240 BYTE),
  ATTRIBUTE3           VARCHAR2(240 BYTE),
  ATTRIBUTE4           VARCHAR2(240 BYTE),
  ATTRIBUTE5           VARCHAR2(240 BYTE),
  ATTRIBUTE6           VARCHAR2(240 BYTE),
  ATTRIBUTE7           VARCHAR2(240 BYTE),
  ATTRIBUTE8           VARCHAR2(240 BYTE),
  ATTRIBUTE9           VARCHAR2(240 BYTE),
  ATTRIBUTE10          VARCHAR2(240 BYTE),
  ATTRIBUTE11          VARCHAR2(240 BYTE),
  ATTRIBUTE12          VARCHAR2(240 BYTE),
  ATTRIBUTE13          VARCHAR2(240 BYTE),
  ATTRIBUTE14          VARCHAR2(240 BYTE),
  ATTRIBUTE15          VARCHAR2(240 BYTE),
  ATTRIBUTE16          VARCHAR2(240 BYTE),
  ATTRIBUTE17          VARCHAR2(240 BYTE),
  ATTRIBUTE18          VARCHAR2(240 BYTE),
  ATTRIBUTE19          VARCHAR2(240 BYTE),
  ATTRIBUTE20          VARCHAR2(240 BYTE),
  CREATION_DATE        DATE,
  CREATED_BY           NUMBER,
  LAST_UPDATE_DATE     DATE,
  LAST_UPDATED_BY      NUMBER,
  LAST_UPDATE_LOGIN    NUMBER,
  STATUS               VARCHAR2(200 BYTE),
  DIVISION             VARCHAR2(100 BYTE),
  CLOSING_REMARKS      VARCHAR2(3000 BYTE),
  CREATOR_PERSON_ID    NUMBER
);

-------------------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE xxfin_ap_pettycash_hdr_s START WITH 1 NOCYCLE NOCACHE;

CREATE SEQUENCE xxfin_ap_pettycash_dtl_s START WITH 1 NOCYCLE NOCACHE;

CREATE SEQUENCE xxfin_ap_pettycash_recpt_s START WITH 1 NOCYCLE NOCACHE;

CREATE SEQUENCE xxfin_ap_pettycash_wf_s START WITH 1 NOCYCLE NOCACHE;

CREATE SEQUENCE xxfin_ap_pettycash_seq_s START WITH 1 NOCYCLE NOCACHE;

CREATE SEQUENCE XXFIN_STAFF_IOU_ID START WITH 1 NOCYCLE NOCACHE;



 -------------------------------------------------------------------------------------------------------------------------------------

    --grant for tables
GRANT ALL ON xxfin_ap_pettycash_hdr_t TO apps;
GRANT ALL ON xxfin_ap_pettycash_dtl_t TO apps;
GRANT ALL ON xxfin_ap_pettycash_recpt_t TO apps;
GRANT ALL ON xxfin_ap_pettycash_wf_t TO apps;
GRANT ALL ON xxfin_ap_pettycash_seq_t TO apps;
GRANT ALL ON XXFIN_STAFF_IOU TO apps;


     --grant for sequences
GRANT ALL ON xxfin_ap_pettycash_hdr_s TO apps;
GRANT ALL ON xxfin_ap_pettycash_dtl_s TO apps;
GRANT ALL ON xxfin_ap_pettycash_recpt_s TO apps;
GRANT ALL ON xxfin_ap_pettycash_wf_s TO apps;
GRANT ALL ON xxfin_ap_pettycash_seq_s TO apps;
GRANT ALL ON XXFIN_STAFF_IOU_ID TO apps;


-------------------------------------------------------------------------------------------------------------------------------------
EXIT;
eof
