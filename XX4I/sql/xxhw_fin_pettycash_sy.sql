/* Formatted on 2/26/2020 10:55:09 AM (QP5 v5.326) */
SET PAGESIZE 0 FEEDBACK OFF VERIFY OFF HEADING OFF ECHO OFF LONG 999999 LONGCHUNKSIZE 999999 LINESIZE 500
-- =========================================================================
-- $header$
--
-- name             : xxhw_fin_pettycash_sy.sql
--
-- description      : Synonym creation scripts for Haya Water Finance Petty Cash
--
--
-- who                                            remarks                        date
-- --------------                  --------------------------------------- ------------------
-- gowtham raam.j -4iapps                     0.1 - dev version               26-FEB-2020
-- =========================================================================

/*     Synonyms     :     Tables  */

CREATE SYNONYM apps.xxfin_ap_pettycash_hdr_t FOR xxfin.xxfin_ap_pettycash_hdr_t;
CREATE SYNONYM apps.xxfin_ap_pettycash_dtl_t FOR xxfin.xxfin_ap_pettycash_dtl_t;
CREATE SYNONYM apps.xxfin_ap_pettycash_recpt_t FOR xxfin.xxfin_ap_pettycash_recpt_t;
CREATE SYNONYM apps.xxfin_ap_pettycash_wf_t FOR xxfin.xxfin_ap_pettycash_wf_t;
CREATE SYNONYM apps.xxfin_ap_pettycash_seq_t FOR xxfin.xxfin_ap_pettycash_seq_t;
CREATE SYNONYM apps.XXFIN_STAFF_IOU  FOR xxfin.XXFIN_STAFF_IOU ;

 /*     Synonyms     :    Sequences*/

CREATE SYNONYM apps.xxfin_ap_pettycash_hdr_s FOR xxfin.xxfin_ap_pettycash_hdr_s;
CREATE SYNONYM apps.xxfin_ap_pettycash_dtl_s FOR xxfin.xxfin_ap_pettycash_dtl_s;
CREATE SYNONYM apps.xxfin_ap_pettycash_recpt_s FOR xxfin.xxfin_ap_pettycash_recpt_s;
CREATE SYNONYM apps.xxfin_ap_pettycash_wf_s FOR xxfin.xxfin_ap_pettycash_wf_s;
CREATE SYNONYM apps.xxfin_ap_pettycash_seq_s FOR xxfin.xxfin_ap_pettycash_seq_s;
CREATE SYNONYM apps.XXFIN_STAFF_IOU_ID  FOR xxfin.XXFIN_STAFF_IOU_ID ;


EXIT;
EOF