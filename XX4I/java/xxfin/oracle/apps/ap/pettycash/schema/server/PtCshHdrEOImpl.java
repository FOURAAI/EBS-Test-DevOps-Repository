package xxfin.oracle.apps.ap.pettycash.schema.server;

import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.server.OAEntityDefImpl;
import oracle.apps.fnd.framework.server.OAEntityImpl;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PtCshHdrEOImpl extends OAEntityImpl {
    public static final int PTYCSHHDRID = 0;
    public static final int REQUESTNUMBER = 1;
    public static final int GOVERNATE = 2;
    public static final int VENDORNAME = 3;
    public static final int VENDORSITENAME = 4;
    public static final int REQUESTDATE = 5;
    public static final int OPENINGBAL = 6;
    public static final int RECEIPTS = 7;
    public static final int CLAIMAMOUNT = 8;
    public static final int STAFFIOUAMOUNT = 9;
    public static final int CLOSINGBALANCE = 10;
    public static final int CASHINHAND = 11;
    public static final int LOCATIONNAME = 12;
    public static final int DIVISION = 13;
    public static final int WFSTATUS = 14;
    public static final int WFITEMKEY = 15;
    public static final int WFITEMTYPE = 16;
    public static final int PENDINGWITH = 17;
    public static final int REQUESTEDBY = 18;
    public static final int REQUESTEDFOR = 19;
    public static final int FLOWITERATION = 20;
    public static final int ATTRIBUTECATEGORY = 21;
    public static final int ATTRIBUTE1 = 22;
    public static final int ATTRIBUTE2 = 23;
    public static final int ATTRIBUTE3 = 24;
    public static final int ATTRIBUTE4 = 25;
    public static final int ATTRIBUTE5 = 26;
    public static final int ATTRIBUTE6 = 27;
    public static final int ATTRIBUTE7 = 28;
    public static final int ATTRIBUTE8 = 29;
    public static final int ATTRIBUTE9 = 30;
    public static final int ATTRIBUTE10 = 31;
    public static final int CREATEDBY = 32;
    public static final int CREATIONDATE = 33;
    public static final int LASTUPDATEDBY = 34;
    public static final int LASTUPDATEDATE = 35;
    public static final int LASTUPDATELOGIN = 36;
    public static final int STATUS = 37;
    public static final int DIVPROCESSED = 38;
    public static final int INVOICENUMBER = 39;
    public static final int PAIDSTATUS = 40;
    public static final int STAFFIOUNUMBER = 41;
    public static final int ERRORMSG = 42;
    public static final int ERRORFLAG = 43;
    public static final int BUDGETAPPROVAL = 44;


    private static OAEntityDefImpl mDefinitionObject;

    /**This is the default constructor (do not remove)
     */
    public PtCshHdrEOImpl() {
    }


    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = 
                    (OAEntityDefImpl)EntityDefImpl.findDefObject("xxfin.oracle.apps.ap.pettycash.schema.server.PtCshHdrEO");
        }
        return mDefinitionObject;
    }

    /**Add attribute defaulting logic in this method.
     */
    public void create(AttributeList attributeList) {
           super.create(attributeList);
           OADBTransaction transaction = getOADBTransaction();
           Number ptCashHdrId = transaction.getSequenceValue("XXFIN_AP_PETTYCASH_HDR_S");
           System.out.println("Inside Create in EO value of  param "+ptCashHdrId);
           setPtycshHdrId(ptCashHdrId);
           setRequestNumber(ptCashHdrId.toString());
           setWfItemKey(ptCashHdrId.toString());
           setWfItemType("XXHWSME"); 
     //      setSmeYesNo("Yes");
     //      setStatus("Incomplete");
         }

    /**Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**Add Entity validation code in this method.
     */
    protected void validateEntity() {
        super.validateEntity();
    }

    /**Gets the attribute value for PtycshHdrId, using the alias name PtycshHdrId
     */
    public Number getPtycshHdrId() {
        return (Number)getAttributeInternal(PTYCSHHDRID);
    }

    /**Sets <code>value</code> as the attribute value for PtycshHdrId
     */
    public void setPtycshHdrId(Number value) {
        setAttributeInternal(PTYCSHHDRID, value);
    }

    /**Gets the attribute value for RequestNumber, using the alias name RequestNumber
     */
    public String getRequestNumber() {
        return (String)getAttributeInternal(REQUESTNUMBER);
    }

    /**Sets <code>value</code> as the attribute value for RequestNumber
     */
    public void setRequestNumber(String value) {
        setAttributeInternal(REQUESTNUMBER, value);
    }

    /**Gets the attribute value for Governate, using the alias name Governate
     */
    public String getGovernate() {
        return (String)getAttributeInternal(GOVERNATE);
    }

    /**Sets <code>value</code> as the attribute value for Governate
     */
    public void setGovernate(String value) {
        setAttributeInternal(GOVERNATE, value);
    }

    /**Gets the attribute value for VendorName, using the alias name VendorName
     */
    public String getVendorName() {
        return (String)getAttributeInternal(VENDORNAME);
    }

    /**Sets <code>value</code> as the attribute value for VendorName
     */
    public void setVendorName(String value) {
        setAttributeInternal(VENDORNAME, value);
    }

    /**Gets the attribute value for VendorSiteName, using the alias name VendorSiteName
     */
    public String getVendorSiteName() {
        return (String)getAttributeInternal(VENDORSITENAME);
    }

    /**Sets <code>value</code> as the attribute value for VendorSiteName
     */
    public void setVendorSiteName(String value) {
        setAttributeInternal(VENDORSITENAME, value);
    }

    /**Gets the attribute value for RequestDate, using the alias name RequestDate
     */
    public Date getRequestDate() {
        return (Date)getAttributeInternal(REQUESTDATE);
    }

    /**Sets <code>value</code> as the attribute value for RequestDate
     */
    public void setRequestDate(Date value) {
        setAttributeInternal(REQUESTDATE, value);
    }

    /**Gets the attribute value for OpeningBal, using the alias name OpeningBal
     */
    public Number getOpeningBal() {
        return (Number)getAttributeInternal(OPENINGBAL);
    }

    /**Sets <code>value</code> as the attribute value for OpeningBal
     */
    public void setOpeningBal(Number value) {
        setAttributeInternal(OPENINGBAL, value);
    }

    /**Gets the attribute value for Receipts, using the alias name Receipts
     */
    public Number getReceipts() {
        return (Number)getAttributeInternal(RECEIPTS);
    }

    /**Sets <code>value</code> as the attribute value for Receipts
     */
    public void setReceipts(Number value) {
        setAttributeInternal(RECEIPTS, value);
    }

    /**Gets the attribute value for ClaimAmount, using the alias name ClaimAmount
     */
    public Number getClaimAmount() {
        return (Number)getAttributeInternal(CLAIMAMOUNT);
    }

    /**Sets <code>value</code> as the attribute value for ClaimAmount
     */
    public void setClaimAmount(Number value) {
        setAttributeInternal(CLAIMAMOUNT, value);
    }

    /**Gets the attribute value for StaffIouAmount, using the alias name StaffIouAmount
     */
    public Number getStaffIouAmount() {
        return (Number)getAttributeInternal(STAFFIOUAMOUNT);
    }

    /**Sets <code>value</code> as the attribute value for StaffIouAmount
     */
    public void setStaffIouAmount(Number value) {
        setAttributeInternal(STAFFIOUAMOUNT, value);
    }

    /**Gets the attribute value for ClosingBalance, using the alias name ClosingBalance
     */
    public Number getClosingBalance() {
        return (Number)getAttributeInternal(CLOSINGBALANCE);
    }

    /**Sets <code>value</code> as the attribute value for ClosingBalance
     */
    public void setClosingBalance(Number value) {
        setAttributeInternal(CLOSINGBALANCE, value);
    }

    /**Gets the attribute value for CashInHand, using the alias name CashInHand
     */
    public Number getCashInHand() {
        return (Number)getAttributeInternal(CASHINHAND);
    }

    /**Sets <code>value</code> as the attribute value for CashInHand
     */
    public void setCashInHand(Number value) {
        setAttributeInternal(CASHINHAND, value);
    }

    /**Gets the attribute value for LocationName, using the alias name LocationName
     */
    public String getLocationName() {
        return (String)getAttributeInternal(LOCATIONNAME);
    }

    /**Sets <code>value</code> as the attribute value for LocationName
     */
    public void setLocationName(String value) {
        setAttributeInternal(LOCATIONNAME, value);
    }

    /**Gets the attribute value for Division, using the alias name Division
     */
    public String getDivision() {
        return (String)getAttributeInternal(DIVISION);
    }

    /**Sets <code>value</code> as the attribute value for Division
     */
    public void setDivision(String value) {
        setAttributeInternal(DIVISION, value);
    }

    /**Gets the attribute value for WfStatus, using the alias name WfStatus
     */
    public String getWfStatus() {
        return (String)getAttributeInternal(WFSTATUS);
    }

    /**Sets <code>value</code> as the attribute value for WfStatus
     */
    public void setWfStatus(String value) {
        setAttributeInternal(WFSTATUS, value);
    }

    /**Gets the attribute value for WfItemKey, using the alias name WfItemKey
     */
    public String getWfItemKey() {
        return (String)getAttributeInternal(WFITEMKEY);
    }

    /**Sets <code>value</code> as the attribute value for WfItemKey
     */
    public void setWfItemKey(String value) {
        setAttributeInternal(WFITEMKEY, value);
    }

    /**Gets the attribute value for WfItemType, using the alias name WfItemType
     */
    public String getWfItemType() {
        return (String)getAttributeInternal(WFITEMTYPE);
    }

    /**Sets <code>value</code> as the attribute value for WfItemType
     */
    public void setWfItemType(String value) {
        setAttributeInternal(WFITEMTYPE, value);
    }

    /**Gets the attribute value for PendingWith, using the alias name PendingWith
     */
    public String getPendingWith() {
        return (String)getAttributeInternal(PENDINGWITH);
    }

    /**Sets <code>value</code> as the attribute value for PendingWith
     */
    public void setPendingWith(String value) {
        setAttributeInternal(PENDINGWITH, value);
    }

    /**Gets the attribute value for RequestedBy, using the alias name RequestedBy
     */
    public String getRequestedBy() {
        return (String)getAttributeInternal(REQUESTEDBY);
    }

    /**Sets <code>value</code> as the attribute value for RequestedBy
     */
    public void setRequestedBy(String value) {
        setAttributeInternal(REQUESTEDBY, value);
    }

    /**Gets the attribute value for RequestedFor, using the alias name RequestedFor
     */
    public String getRequestedFor() {
        return (String)getAttributeInternal(REQUESTEDFOR);
    }

    /**Sets <code>value</code> as the attribute value for RequestedFor
     */
    public void setRequestedFor(String value) {
        setAttributeInternal(REQUESTEDFOR, value);
    }

    /**Gets the attribute value for FlowIteration, using the alias name FlowIteration
     */
    public Number getFlowIteration() {
        return (Number)getAttributeInternal(FLOWITERATION);
    }

    /**Sets <code>value</code> as the attribute value for FlowIteration
     */
    public void setFlowIteration(Number value) {
        setAttributeInternal(FLOWITERATION, value);
    }

    /**Gets the attribute value for AttributeCategory, using the alias name AttributeCategory
     */
    public String getAttributeCategory() {
        return (String)getAttributeInternal(ATTRIBUTECATEGORY);
    }

    /**Sets <code>value</code> as the attribute value for AttributeCategory
     */
    public void setAttributeCategory(String value) {
        setAttributeInternal(ATTRIBUTECATEGORY, value);
    }

    /**Gets the attribute value for Attribute1, using the alias name Attribute1
     */
    public String getAttribute1() {
        return (String)getAttributeInternal(ATTRIBUTE1);
    }

    /**Sets <code>value</code> as the attribute value for Attribute1
     */
    public void setAttribute1(String value) {
        setAttributeInternal(ATTRIBUTE1, value);
    }

    /**Gets the attribute value for Attribute2, using the alias name Attribute2
     */
    public String getAttribute2() {
        return (String)getAttributeInternal(ATTRIBUTE2);
    }

    /**Sets <code>value</code> as the attribute value for Attribute2
     */
    public void setAttribute2(String value) {
        setAttributeInternal(ATTRIBUTE2, value);
    }

    /**Gets the attribute value for Attribute3, using the alias name Attribute3
     */
    public String getAttribute3() {
        return (String)getAttributeInternal(ATTRIBUTE3);
    }

    /**Sets <code>value</code> as the attribute value for Attribute3
     */
    public void setAttribute3(String value) {
        setAttributeInternal(ATTRIBUTE3, value);
    }

    /**Gets the attribute value for Attribute4, using the alias name Attribute4
     */
    public String getAttribute4() {
        return (String)getAttributeInternal(ATTRIBUTE4);
    }

    /**Sets <code>value</code> as the attribute value for Attribute4
     */
    public void setAttribute4(String value) {
        setAttributeInternal(ATTRIBUTE4, value);
    }

    /**Gets the attribute value for Attribute5, using the alias name Attribute5
     */
    public String getAttribute5() {
        return (String)getAttributeInternal(ATTRIBUTE5);
    }

    /**Sets <code>value</code> as the attribute value for Attribute5
     */
    public void setAttribute5(String value) {
        setAttributeInternal(ATTRIBUTE5, value);
    }

    /**Gets the attribute value for Attribute6, using the alias name Attribute6
     */
    public String getAttribute6() {
        return (String)getAttributeInternal(ATTRIBUTE6);
    }

    /**Sets <code>value</code> as the attribute value for Attribute6
     */
    public void setAttribute6(String value) {
        setAttributeInternal(ATTRIBUTE6, value);
    }

    /**Gets the attribute value for Attribute7, using the alias name Attribute7
     */
    public String getAttribute7() {
        return (String)getAttributeInternal(ATTRIBUTE7);
    }

    /**Sets <code>value</code> as the attribute value for Attribute7
     */
    public void setAttribute7(String value) {
        setAttributeInternal(ATTRIBUTE7, value);
    }

    /**Gets the attribute value for Attribute8, using the alias name Attribute8
     */
    public String getAttribute8() {
        return (String)getAttributeInternal(ATTRIBUTE8);
    }

    /**Sets <code>value</code> as the attribute value for Attribute8
     */
    public void setAttribute8(String value) {
        setAttributeInternal(ATTRIBUTE8, value);
    }

    /**Gets the attribute value for Attribute9, using the alias name Attribute9
     */
    public String getAttribute9() {
        return (String)getAttributeInternal(ATTRIBUTE9);
    }

    /**Sets <code>value</code> as the attribute value for Attribute9
     */
    public void setAttribute9(String value) {
        setAttributeInternal(ATTRIBUTE9, value);
    }

    /**Gets the attribute value for Attribute10, using the alias name Attribute10
     */
    public String getAttribute10() {
        return (String)getAttributeInternal(ATTRIBUTE10);
    }

    /**Sets <code>value</code> as the attribute value for Attribute10
     */
    public void setAttribute10(String value) {
        setAttributeInternal(ATTRIBUTE10, value);
    }

    /**Gets the attribute value for CreatedBy, using the alias name CreatedBy
     */
    public Number getCreatedBy() {
        return (Number)getAttributeInternal(CREATEDBY);
    }

    /**Sets <code>value</code> as the attribute value for CreatedBy
     */
    public void setCreatedBy(Number value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**Gets the attribute value for CreationDate, using the alias name CreationDate
     */
    public Date getCreationDate() {
        return (Date)getAttributeInternal(CREATIONDATE);
    }

    /**Sets <code>value</code> as the attribute value for CreationDate
     */
    public void setCreationDate(Date value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**Gets the attribute value for LastUpdatedBy, using the alias name LastUpdatedBy
     */
    public Number getLastUpdatedBy() {
        return (Number)getAttributeInternal(LASTUPDATEDBY);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdatedBy
     */
    public void setLastUpdatedBy(Number value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**Gets the attribute value for LastUpdateDate, using the alias name LastUpdateDate
     */
    public Date getLastUpdateDate() {
        return (Date)getAttributeInternal(LASTUPDATEDATE);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdateDate
     */
    public void setLastUpdateDate(Date value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**Gets the attribute value for LastUpdateLogin, using the alias name LastUpdateLogin
     */
    public Number getLastUpdateLogin() {
        return (Number)getAttributeInternal(LASTUPDATELOGIN);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdateLogin
     */
    public void setLastUpdateLogin(Number value) {
        setAttributeInternal(LASTUPDATELOGIN, value);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case PTYCSHHDRID:
            return getPtycshHdrId();
        case REQUESTNUMBER:
            return getRequestNumber();
        case GOVERNATE:
            return getGovernate();
        case VENDORNAME:
            return getVendorName();
        case VENDORSITENAME:
            return getVendorSiteName();
        case REQUESTDATE:
            return getRequestDate();
        case OPENINGBAL:
            return getOpeningBal();
        case RECEIPTS:
            return getReceipts();
        case CLAIMAMOUNT:
            return getClaimAmount();
        case STAFFIOUAMOUNT:
            return getStaffIouAmount();
        case CLOSINGBALANCE:
            return getClosingBalance();
        case CASHINHAND:
            return getCashInHand();
        case LOCATIONNAME:
            return getLocationName();
        case DIVISION:
            return getDivision();
        case WFSTATUS:
            return getWfStatus();
        case WFITEMKEY:
            return getWfItemKey();
        case WFITEMTYPE:
            return getWfItemType();
        case PENDINGWITH:
            return getPendingWith();
        case REQUESTEDBY:
            return getRequestedBy();
        case REQUESTEDFOR:
            return getRequestedFor();
        case FLOWITERATION:
            return getFlowIteration();
        case ATTRIBUTECATEGORY:
            return getAttributeCategory();
        case ATTRIBUTE1:
            return getAttribute1();
        case ATTRIBUTE2:
            return getAttribute2();
        case ATTRIBUTE3:
            return getAttribute3();
        case ATTRIBUTE4:
            return getAttribute4();
        case ATTRIBUTE5:
            return getAttribute5();
        case ATTRIBUTE6:
            return getAttribute6();
        case ATTRIBUTE7:
            return getAttribute7();
        case ATTRIBUTE8:
            return getAttribute8();
        case ATTRIBUTE9:
            return getAttribute9();
        case ATTRIBUTE10:
            return getAttribute10();
        case CREATEDBY:
            return getCreatedBy();
        case CREATIONDATE:
            return getCreationDate();
        case LASTUPDATEDBY:
            return getLastUpdatedBy();
        case LASTUPDATEDATE:
            return getLastUpdateDate();
        case LASTUPDATELOGIN:
            return getLastUpdateLogin();
        case STATUS:
            return getStatus();
        case DIVPROCESSED:
            return getDivProcessed();
        case INVOICENUMBER:
            return getInvoiceNumber();
        case PAIDSTATUS:
            return getPaidStatus();
        case STAFFIOUNUMBER:
            return getStaffIouNumber();
        case ERRORMSG:
            return getErrorMsg();
        case ERRORFLAG:
            return getErrorFlag();
        case BUDGETAPPROVAL:
            return getBudgetApproval();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case PTYCSHHDRID:
            setPtycshHdrId((Number)value);
            return;
        case REQUESTNUMBER:
            setRequestNumber((String)value);
            return;
        case GOVERNATE:
            setGovernate((String)value);
            return;
        case VENDORNAME:
            setVendorName((String)value);
            return;
        case VENDORSITENAME:
            setVendorSiteName((String)value);
            return;
        case REQUESTDATE:
            setRequestDate((Date)value);
            return;
        case OPENINGBAL:
            setOpeningBal((Number)value);
            return;
        case RECEIPTS:
            setReceipts((Number)value);
            return;
        case CLAIMAMOUNT:
            setClaimAmount((Number)value);
            return;
        case STAFFIOUAMOUNT:
            setStaffIouAmount((Number)value);
            return;
        case CLOSINGBALANCE:
            setClosingBalance((Number)value);
            return;
        case CASHINHAND:
            setCashInHand((Number)value);
            return;
        case LOCATIONNAME:
            setLocationName((String)value);
            return;
        case DIVISION:
            setDivision((String)value);
            return;
        case WFSTATUS:
            setWfStatus((String)value);
            return;
        case WFITEMKEY:
            setWfItemKey((String)value);
            return;
        case WFITEMTYPE:
            setWfItemType((String)value);
            return;
        case PENDINGWITH:
            setPendingWith((String)value);
            return;
        case REQUESTEDBY:
            setRequestedBy((String)value);
            return;
        case REQUESTEDFOR:
            setRequestedFor((String)value);
            return;
        case FLOWITERATION:
            setFlowIteration((Number)value);
            return;
        case ATTRIBUTECATEGORY:
            setAttributeCategory((String)value);
            return;
        case ATTRIBUTE1:
            setAttribute1((String)value);
            return;
        case ATTRIBUTE2:
            setAttribute2((String)value);
            return;
        case ATTRIBUTE3:
            setAttribute3((String)value);
            return;
        case ATTRIBUTE4:
            setAttribute4((String)value);
            return;
        case ATTRIBUTE5:
            setAttribute5((String)value);
            return;
        case ATTRIBUTE6:
            setAttribute6((String)value);
            return;
        case ATTRIBUTE7:
            setAttribute7((String)value);
            return;
        case ATTRIBUTE8:
            setAttribute8((String)value);
            return;
        case ATTRIBUTE9:
            setAttribute9((String)value);
            return;
        case ATTRIBUTE10:
            setAttribute10((String)value);
            return;
        case CREATEDBY:
            setCreatedBy((Number)value);
            return;
        case CREATIONDATE:
            setCreationDate((Date)value);
            return;
        case LASTUPDATEDBY:
            setLastUpdatedBy((Number)value);
            return;
        case LASTUPDATEDATE:
            setLastUpdateDate((Date)value);
            return;
        case LASTUPDATELOGIN:
            setLastUpdateLogin((Number)value);
            return;
        case STATUS:
            setStatus((String)value);
            return;
        case DIVPROCESSED:
            setDivProcessed((String)value);
            return;
        case INVOICENUMBER:
            setInvoiceNumber((String)value);
            return;
        case PAIDSTATUS:
            setPaidStatus((String)value);
            return;
        case STAFFIOUNUMBER:
            setStaffIouNumber((Number)value);
            return;
        case ERRORMSG:
            setErrorMsg((String)value);
            return;
        case ERRORFLAG:
            setErrorFlag((String)value);
            return;
        case BUDGETAPPROVAL:
            setBudgetApproval((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for Status, using the alias name Status
     */
    public String getStatus() {
        return (String)getAttributeInternal(STATUS);
    }

    /**Sets <code>value</code> as the attribute value for Status
     */
    public void setStatus(String value) {
        setAttributeInternal(STATUS, value);
    }

    /**Gets the attribute value for DivProcessed, using the alias name DivProcessed
     */
    public String getDivProcessed() {
        return (String)getAttributeInternal(DIVPROCESSED);
    }

    /**Sets <code>value</code> as the attribute value for DivProcessed
     */
    public void setDivProcessed(String value) {
        setAttributeInternal(DIVPROCESSED, value);
    }

    /**Gets the attribute value for InvoiceNumber, using the alias name InvoiceNumber
     */
    public String getInvoiceNumber() {
        return (String)getAttributeInternal(INVOICENUMBER);
    }

    /**Sets <code>value</code> as the attribute value for InvoiceNumber
     */
    public void setInvoiceNumber(String value) {
        setAttributeInternal(INVOICENUMBER, value);
    }

    /**Gets the attribute value for PaidStatus, using the alias name PaidStatus
     */
    public String getPaidStatus() {
        return (String)getAttributeInternal(PAIDSTATUS);
    }

    /**Sets <code>value</code> as the attribute value for PaidStatus
     */
    public void setPaidStatus(String value) {
        setAttributeInternal(PAIDSTATUS, value);
    }

    /**Gets the attribute value for StaffIouNumber, using the alias name StaffIouNumber
     */
    public Number getStaffIouNumber() {
        return (Number)getAttributeInternal(STAFFIOUNUMBER);
    }

    /**Sets <code>value</code> as the attribute value for StaffIouNumber
     */
    public void setStaffIouNumber(Number value) {
        setAttributeInternal(STAFFIOUNUMBER, value);
    }

    /**Gets the attribute value for ErrorMsg, using the alias name ErrorMsg
     */
    public String getErrorMsg() {
        return (String)getAttributeInternal(ERRORMSG);
    }

    /**Sets <code>value</code> as the attribute value for ErrorMsg
     */
    public void setErrorMsg(String value) {
        setAttributeInternal(ERRORMSG, value);
    }

    /**Gets the attribute value for ErrorFlag, using the alias name ErrorFlag
     */
    public String getErrorFlag() {
        return (String)getAttributeInternal(ERRORFLAG);
    }

    /**Sets <code>value</code> as the attribute value for ErrorFlag
     */
    public void setErrorFlag(String value) {
        setAttributeInternal(ERRORFLAG, value);
    }

    /**Gets the attribute value for BudgetApproval, using the alias name BudgetApproval
     */
    public String getBudgetApproval() {
        return (String)getAttributeInternal(BUDGETAPPROVAL);
    }

    /**Sets <code>value</code> as the attribute value for BudgetApproval
     */
    public void setBudgetApproval(String value) {
        setAttributeInternal(BUDGETAPPROVAL, value);
    }

    /**Creates a Key object based on given key constituents
     */
    public static Key createPrimaryKey(Number ptycshHdrId) {
        return new Key(new Object[]{ptycshHdrId});
    }
}