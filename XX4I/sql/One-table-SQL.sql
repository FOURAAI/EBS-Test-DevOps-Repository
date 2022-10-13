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
            
-------------------------------------------------------------------------------------------------------------------------------------
EXIT;
eof            