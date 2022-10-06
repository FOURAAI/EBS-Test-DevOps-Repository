package xxfin.oracle.apps.ap.pettycash.schema.server;

import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.server.OAEntityDefImpl;
import oracle.apps.fnd.framework.server.OAEntityImpl;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PtCshDtlEOImpl extends OAEntityImpl {
    public static final int PTYCSHDTLID = 0;
    public static final int PTYCSHHDRID = 1;
    public static final int EXPENSETYPE = 2;
    public static final int DEPARTMENT = 3;
    public static final int ACCOUNTNUM = 4;
    public static final int ACCOUNTDESCRIPTION = 5;
    public static final int CURRENCY = 6;
    public static final int AMOUNT = 7;
    public static final int EXCHANGERATETYPE = 8;
    public static final int EXCHANGERATEDATE = 9;
    public static final int EXCHANGERATE = 10;
    public static final int BRIEFNARRATION = 11;
    public static final int BUDGETRESULTS = 12;
    public static final int ATTACHFILE = 13;
    public static final int FILEDESC = 14;
    public static final int ATTRIBUTECATEGORY = 15;
    public static final int ATTRIBUTE1 = 16;
    public static final int ATTRIBUTE2 = 17;
    public static final int ATTRIBUTE3 = 18;
    public static final int ATTRIBUTE4 = 19;
    public static final int ATTRIBUTE5 = 20;
    public static final int ATTRIBUTE6 = 21;
    public static final int ATTRIBUTE7 = 22;
    public static final int ATTRIBUTE8 = 23;
    public static final int ATTRIBUTE9 = 24;
    public static final int ATTRIBUTE10 = 25;
    public static final int CREATEDBY = 26;
    public static final int CREATIONDATE = 27;
    public static final int LASTUPDATEDBY = 28;
    public static final int LASTUPDATEDATE = 29;
    public static final int LASTUPDATELOGIN = 30;
    public static final int CCID = 31;
    public static final int CONCATENATEDSEGMENTS = 32;
    public static final int DIVPROCESSED = 33;
    public static final int OLDPTYCSHHDRID = 34;
    public static final int OLDPTYCSHDTLID = 35;
    public static final int ELIGIBLESELFLAG = 36;
    public static final int DTLNUM = 37;
    public static final int DTLNUMACT = 38;


    private static OAEntityDefImpl mDefinitionObject;

    /**This is the default constructor (do not remove)
     */
    public PtCshDtlEOImpl() {
    }


    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = 
                    (OAEntityDefImpl)EntityDefImpl.findDefObject("xxfin.oracle.apps.ap.pettycash.schema.server.PtCshDtlEO");
        }
        return mDefinitionObject;
    }

    /**Add attribute defaulting logic in this method.
     */
    public void create(AttributeList attributeList) {
           super.create(attributeList);
           OADBTransaction transaction = getOADBTransaction();
           Number ptCashDtlId = transaction.getSequenceValue("XXFIN_AP_PETTYCASH_DTL_S");
           System.out.println("Inside Create in EO value of  param "+ptCashDtlId);
           setPtycshDtlId(ptCashDtlId);
         }

    /**Gets the attribute value for PtycshDtlId, using the alias name PtycshDtlId
     */
    public Number getPtycshDtlId() {
        return (Number)getAttributeInternal(PTYCSHDTLID);
    }

    /**Sets <code>value</code> as the attribute value for PtycshDtlId
     */
    public void setPtycshDtlId(Number value) {
        setAttributeInternal(PTYCSHDTLID, value);
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

    /**Gets the attribute value for ExpenseType, using the alias name ExpenseType
     */
    public String getExpenseType() {
        return (String)getAttributeInternal(EXPENSETYPE);
    }

    /**Sets <code>value</code> as the attribute value for ExpenseType
     */
    public void setExpenseType(String value) {
        setAttributeInternal(EXPENSETYPE, value);
    }

    /**Gets the attribute value for Department, using the alias name Department
     */
    public String getDepartment() {
        return (String)getAttributeInternal(DEPARTMENT);
    }

    /**Sets <code>value</code> as the attribute value for Department
     */
    public void setDepartment(String value) {
        setAttributeInternal(DEPARTMENT, value);
    }

    /**Gets the attribute value for AccountNum, using the alias name AccountNum
     */
    public String getAccountNum() {
        return (String)getAttributeInternal(ACCOUNTNUM);
    }

    /**Sets <code>value</code> as the attribute value for AccountNum
     */
    public void setAccountNum(String value) {
        setAttributeInternal(ACCOUNTNUM, value);
    }

    /**Gets the attribute value for AccountDescription, using the alias name AccountDescription
     */
    public String getAccountDescription() {
        return (String)getAttributeInternal(ACCOUNTDESCRIPTION);
    }

    /**Sets <code>value</code> as the attribute value for AccountDescription
     */
    public void setAccountDescription(String value) {
        setAttributeInternal(ACCOUNTDESCRIPTION, value);
    }

    /**Gets the attribute value for Currency, using the alias name Currency
     */
    public String getCurrency() {
        return (String)getAttributeInternal(CURRENCY);
    }

    /**Sets <code>value</code> as the attribute value for Currency
     */
    public void setCurrency(String value) {
        setAttributeInternal(CURRENCY, value);
    }

    /**Gets the attribute value for Amount, using the alias name Amount
     */
    public Number getAmount() {
        return (Number)getAttributeInternal(AMOUNT);
    }

    /**Sets <code>value</code> as the attribute value for Amount
     */
    public void setAmount(Number value) {
        setAttributeInternal(AMOUNT, value);
    }

    /**Gets the attribute value for ExchangeRateType, using the alias name ExchangeRateType
     */
    public String getExchangeRateType() {
        return (String)getAttributeInternal(EXCHANGERATETYPE);
    }

    /**Sets <code>value</code> as the attribute value for ExchangeRateType
     */
    public void setExchangeRateType(String value) {
        setAttributeInternal(EXCHANGERATETYPE, value);
    }

    /**Gets the attribute value for ExchangeRateDate, using the alias name ExchangeRateDate
     */
    public Date getExchangeRateDate() {
        return (Date)getAttributeInternal(EXCHANGERATEDATE);
    }

    /**Sets <code>value</code> as the attribute value for ExchangeRateDate
     */
    public void setExchangeRateDate(Date value) {
        setAttributeInternal(EXCHANGERATEDATE, value);
    }

    /**Gets the attribute value for ExchangeRate, using the alias name ExchangeRate
     */
    public String getExchangeRate() {
        return (String)getAttributeInternal(EXCHANGERATE);
    }

    /**Sets <code>value</code> as the attribute value for ExchangeRate
     */
    public void setExchangeRate(String value) {
        setAttributeInternal(EXCHANGERATE, value);
    }

    /**Gets the attribute value for BriefNarration, using the alias name BriefNarration
     */
    public String getBriefNarration() {
        return (String)getAttributeInternal(BRIEFNARRATION);
    }

    /**Sets <code>value</code> as the attribute value for BriefNarration
     */
    public void setBriefNarration(String value) {
        setAttributeInternal(BRIEFNARRATION, value);
    }

    /**Gets the attribute value for BudgetResults, using the alias name BudgetResults
     */
    public String getBudgetResults() {
        return (String)getAttributeInternal(BUDGETRESULTS);
    }

    /**Sets <code>value</code> as the attribute value for BudgetResults
     */
    public void setBudgetResults(String value) {
        setAttributeInternal(BUDGETRESULTS, value);
    }


    /**Gets the attribute value for AttachFile, using the alias name AttachFile
     */
    public BlobDomain getAttachFile() {
        return (BlobDomain)getAttributeInternal(ATTACHFILE);
    }

    /**Sets <code>value</code> as the attribute value for AttachFile
     */
    public void setAttachFile(BlobDomain value) {
        setAttributeInternal(ATTACHFILE, value);
    }

    /**Gets the attribute value for FileDesc, using the alias name FileDesc
     */
    public String getFileDesc() {
        return (String)getAttributeInternal(FILEDESC);
    }

    /**Sets <code>value</code> as the attribute value for FileDesc
     */
    public void setFileDesc(String value) {
        setAttributeInternal(FILEDESC, value);
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
        case PTYCSHDTLID:
            return getPtycshDtlId();
        case PTYCSHHDRID:
            return getPtycshHdrId();
        case EXPENSETYPE:
            return getExpenseType();
        case DEPARTMENT:
            return getDepartment();
        case ACCOUNTNUM:
            return getAccountNum();
        case ACCOUNTDESCRIPTION:
            return getAccountDescription();
        case CURRENCY:
            return getCurrency();
        case AMOUNT:
            return getAmount();
        case EXCHANGERATETYPE:
            return getExchangeRateType();
        case EXCHANGERATEDATE:
            return getExchangeRateDate();
        case EXCHANGERATE:
            return getExchangeRate();
        case BRIEFNARRATION:
            return getBriefNarration();
        case BUDGETRESULTS:
            return getBudgetResults();
        case ATTACHFILE:
            return getAttachFile();
        case FILEDESC:
            return getFileDesc();
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
        case CCID:
            return getCcId();
        case CONCATENATEDSEGMENTS:
            return getConcatenatedSegments();
        case DIVPROCESSED:
            return getDivProcessed();
        case OLDPTYCSHHDRID:
            return getOldPtycshHdrId();
        case OLDPTYCSHDTLID:
            return getOldPtycshDtlId();
        case ELIGIBLESELFLAG:
            return getEligibleSelFlag();
        case DTLNUM:
            return getDtlNum();
        case DTLNUMACT:
            return getDtlNumAct();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case PTYCSHDTLID:
            setPtycshDtlId((Number)value);
            return;
        case PTYCSHHDRID:
            setPtycshHdrId((Number)value);
            return;
        case EXPENSETYPE:
            setExpenseType((String)value);
            return;
        case DEPARTMENT:
            setDepartment((String)value);
            return;
        case ACCOUNTNUM:
            setAccountNum((String)value);
            return;
        case ACCOUNTDESCRIPTION:
            setAccountDescription((String)value);
            return;
        case CURRENCY:
            setCurrency((String)value);
            return;
        case AMOUNT:
            setAmount((Number)value);
            return;
        case EXCHANGERATETYPE:
            setExchangeRateType((String)value);
            return;
        case EXCHANGERATEDATE:
            setExchangeRateDate((Date)value);
            return;
        case EXCHANGERATE:
            setExchangeRate((String)value);
            return;
        case BRIEFNARRATION:
            setBriefNarration((String)value);
            return;
        case BUDGETRESULTS:
            setBudgetResults((String)value);
            return;
        case ATTACHFILE:
            setAttachFile((BlobDomain)value);
            return;
        case FILEDESC:
            setFileDesc((String)value);
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
        case CCID:
            setCcId((Number)value);
            return;
        case CONCATENATEDSEGMENTS:
            setConcatenatedSegments((String)value);
            return;
        case DIVPROCESSED:
            setDivProcessed((String)value);
            return;
        case OLDPTYCSHHDRID:
            setOldPtycshHdrId((Number)value);
            return;
        case OLDPTYCSHDTLID:
            setOldPtycshDtlId((Number)value);
            return;
        case ELIGIBLESELFLAG:
            setEligibleSelFlag((String)value);
            return;
        case DTLNUM:
            setDtlNum((String)value);
            return;
        case DTLNUMACT:
            setDtlNumAct((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for CcId, using the alias name CcId
     */
    public Number getCcId() {
        return (Number)getAttributeInternal(CCID);
    }

    /**Sets <code>value</code> as the attribute value for CcId
     */
    public void setCcId(Number value) {
        setAttributeInternal(CCID, value);
    }

    /**Gets the attribute value for ConcatenatedSegments, using the alias name ConcatenatedSegments
     */
    public String getConcatenatedSegments() {
        return (String)getAttributeInternal(CONCATENATEDSEGMENTS);
    }

    /**Sets <code>value</code> as the attribute value for ConcatenatedSegments
     */
    public void setConcatenatedSegments(String value) {
        setAttributeInternal(CONCATENATEDSEGMENTS, value);
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

    /**Gets the attribute value for OldPtycshHdrId, using the alias name OldPtycshHdrId
     */
    public Number getOldPtycshHdrId() {
        return (Number)getAttributeInternal(OLDPTYCSHHDRID);
    }

    /**Sets <code>value</code> as the attribute value for OldPtycshHdrId
     */
    public void setOldPtycshHdrId(Number value) {
        setAttributeInternal(OLDPTYCSHHDRID, value);
    }

    /**Gets the attribute value for OldPtycshDtlId, using the alias name OldPtycshDtlId
     */
    public Number getOldPtycshDtlId() {
        return (Number)getAttributeInternal(OLDPTYCSHDTLID);
    }

    /**Sets <code>value</code> as the attribute value for OldPtycshDtlId
     */
    public void setOldPtycshDtlId(Number value) {
        setAttributeInternal(OLDPTYCSHDTLID, value);
    }

    /**Gets the attribute value for EligibleSelFlag, using the alias name EligibleSelFlag
     */
    public String getEligibleSelFlag() {
        return (String)getAttributeInternal(ELIGIBLESELFLAG);
    }

    /**Sets <code>value</code> as the attribute value for EligibleSelFlag
     */
    public void setEligibleSelFlag(String value) {
        setAttributeInternal(ELIGIBLESELFLAG, value);
    }

    /**Gets the attribute value for DtlNum, using the alias name DtlNum
     */
    public String getDtlNum() {
        return (String)getAttributeInternal(DTLNUM);
    }

    /**Sets <code>value</code> as the attribute value for DtlNum
     */
    public void setDtlNum(String value) {
        setAttributeInternal(DTLNUM, value);
    }

    /**Gets the attribute value for DtlNumAct, using the alias name DtlNumAct
     */
    public String getDtlNumAct() {
        return (String)getAttributeInternal(DTLNUMACT);
    }

    /**Sets <code>value</code> as the attribute value for DtlNumAct
     */
    public void setDtlNumAct(String value) {
        setAttributeInternal(DTLNUMACT, value);
    }

    /**Creates a Key object based on given key constituents
     */
    public static Key createPrimaryKey(Number ptycshDtlId) {
        return new Key(new Object[]{ptycshDtlId});
    }
}