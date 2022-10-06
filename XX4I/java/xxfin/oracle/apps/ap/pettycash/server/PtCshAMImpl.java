package xxfin.oracle.apps.ap.pettycash.server;

import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.jbo.Row;

import xxfin.oracle.apps.ap.pettycash.lov.server.ClaimNumVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.ConversionTypeVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.CurrencyVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.DeptVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.ExpenseTypeVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.GovernateVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.IOUAmountVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.PaymentModeVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.SeqClaimPrefixVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.SuppVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.getLineSeqVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.getVendorVOImpl;
import xxfin.oracle.apps.ap.pettycash.lov.server.glDailyRatesVOImpl;
import xxfin.oracle.apps.ap.pettycash.poplist.server.BudgetCntVOImpl;
import xxfin.oracle.apps.ap.pettycash.poplist.server.BudgetVOImpl;

// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PtCshAMImpl extends OAApplicationModuleImpl {
    /**This is the default constructor (do not remove)
     */
    public PtCshAMImpl() {
    }

    /**Sample main for debugging Business Components code using the tester.
     */
    public static void main(String[] args) {
        launchTester("xxfin.oracle.apps.ap.pettycash.server", /* package name */
      "PtCshAMLocal" /* Configuration Name */);
    }
    /**=======================================================================
        *            CREATE RECORD FOR THE VO WHICH HAS BEEN PASSED IN PARAM
        * ====================================================================**/
        public void createRecord(String voname)
           {
                System.out.print((new StringBuilder()).append("Going to Create a Row in the VO : ").append(voname).toString());
                writeDiagnostics(this, "Going to Create a Row in the VO : "+voname, 4);
                OAViewObject vo = (OAViewObject)findViewObject(voname);
                // We need to do this on a VO that has not been queried before we insert
                // our first row.  We don't want to do it for subsequent inserts.             
                 if (vo.getFetchedRowCount() == 0) 
                 {
                   vo.setMaxFetchSize(0);
                     System.out.println("Checking Inside vo.setMaxFetchSize");
                     writeDiagnostics(this, "Checking Inside vo.setMaxFetchSize ", 4);
                 }  
                 Row row = vo.createRow();           
                 if(row!=null) 
                 {                
                     vo.insertRow(row);
                     row.setNewRowState(Row.STATUS_INITIALIZED);            
                 }
                 System.out.println("New Row has been created in VO :"+voname);
               writeDiagnostics(this, "New Row has been created in VO "+voname, 4);
           }
        /**=======================================================================
        *            COMMIT CHANGES
        * ====================================================================**/
        public void apply()
        {
             getTransaction().commit();
             System.out.println("Calling Commit Functionality ");
             writeDiagnostics(this, "Calling Commit Functionality ", 4);
        }
        /**=======================================================================
        *            UNDO - ROLLBACK  CHANGES
        * ====================================================================**/
        public void undoChanges()
        {
             getOADBTransaction().rollback();
             System.out.println("Calling RollBack Functionality ");
             writeDiagnostics(this, "Calling RollBack Functionality ", 4);
        }


    /**Container's getter for PtCshHdrVO1
     */
    public PtCshHdrVOImpl getPtCshHdrVO1() {
        return (PtCshHdrVOImpl)findViewObject("PtCshHdrVO1");
    }

    /**Container's getter for PtCshRecptVO1
     */
    public PtCshRecptVOImpl getPtCshRecptVO1() {
        return (PtCshRecptVOImpl)findViewObject("PtCshRecptVO1");
    }

    /**Container's getter for DeptVO1
     */
    public DeptVOImpl getDeptVO1() {
        return (DeptVOImpl)findViewObject("DeptVO1");
    }

    /**Container's getter for BudgetVO1
     */
    public BudgetVOImpl getBudgetVO1() {
        return (BudgetVOImpl)findViewObject("BudgetVO1");
    }

    /**Container's getter for CurrencyVO1
     */
    public CurrencyVOImpl getCurrencyVO1() {
        return (CurrencyVOImpl)findViewObject("CurrencyVO1");
    }

    /**Container's getter for ConversionTypeVO1
     */
    public ConversionTypeVOImpl getConversionTypeVO1() {
        return (ConversionTypeVOImpl)findViewObject("ConversionTypeVO1");
    }

    /**Container's getter for GovernateVO1
     */
    public GovernateVOImpl getGovernateVO1() {
        return (GovernateVOImpl)findViewObject("GovernateVO1");
    }

    /**Container's getter for ExpenseTypeVO1
     */
    public ExpenseTypeVOImpl getExpenseTypeVO1() {
        return (ExpenseTypeVOImpl)findViewObject("ExpenseTypeVO1");
    }

    /**Container's getter for PaymentModeVO1
     */
    public PaymentModeVOImpl getPaymentModeVO1() {
        return (PaymentModeVOImpl)findViewObject("PaymentModeVO1");
    }

    /**Container's getter for CurrencyVO2
     */
    public CurrencyVOImpl getCurrencyVO2() {
        return (CurrencyVOImpl)findViewObject("CurrencyVO2");
    }

    /**Container's getter for SuppVO1
     */
    public SuppVOImpl getSuppVO1() {
        return (SuppVOImpl)findViewObject("SuppVO1");
    }

    /**Container's getter for glDailyRatesVO1
     */
    public glDailyRatesVOImpl getglDailyRatesVO1() {
        return (glDailyRatesVOImpl)findViewObject("glDailyRatesVO1");
    }

    /**Container's getter for ClaimNumVO1
     */
    public ClaimNumVOImpl getClaimNumVO1() {
        return (ClaimNumVOImpl)findViewObject("ClaimNumVO1");
    }

    /**Container's getter for getVendorVO1
     */
    public getVendorVOImpl getgetVendorVO1() {
        return (getVendorVOImpl)findViewObject("getVendorVO1");
    }


    /**Container's getter for CodeCombinationVO1
     */
    public CodeCombinationVOImpl getCodeCombinationVO1() {
        return (CodeCombinationVOImpl)findViewObject("CodeCombinationVO1");
    }

    /**Container's getter for PtCshWfHistVO1
     */
    public PtCshWfHistVOImpl getPtCshWfHistVO1() {
        return (PtCshWfHistVOImpl)findViewObject("PtCshWfHistVO1");
    }


    /**Container's getter for SeqClaimPrefixVO1
     */
    public SeqClaimPrefixVOImpl getSeqClaimPrefixVO1() {
        return (SeqClaimPrefixVOImpl)findViewObject("SeqClaimPrefixVO1");
    }

    /**Container's getter for PtCshSeqVO1
     */
    public PtCshSeqVOImpl getPtCshSeqVO1() {
        return (PtCshSeqVOImpl)findViewObject("PtCshSeqVO1");
    }


    /**Container's getter for PtCshHdrVO2
     */
    public PtCshHdrVOImpl getPtCshHdrVO2() {
        return (PtCshHdrVOImpl)findViewObject("PtCshHdrVO2");
    }

    /**Container's getter for PtCshDtlVO1
     */
    public PtCshDtlVOImpl getPtCshDtlVO1() {
        return (PtCshDtlVOImpl)findViewObject("PtCshDtlVO1");
    }

    /**Container's getter for PtCshDtlVO2
     */
    public PtCshDtlVOImpl getPtCshDtlVO2() {
        return (PtCshDtlVOImpl)findViewObject("PtCshDtlVO2");
    }

    /**Container's getter for IOUAmountVO1
     */
    public IOUAmountVOImpl getIOUAmountVO1() {
        return (IOUAmountVOImpl)findViewObject("IOUAmountVO1");
    }

    /**Container's getter for PtCshTempVO1
     */
    public PtCshTempVOImpl getPtCshTempVO1() {
        return (PtCshTempVOImpl)findViewObject("PtCshTempVO1");
    }

    /**Container's getter for BudgetCntVO1
     */
    public BudgetCntVOImpl getBudgetCntVO1() {
        return (BudgetCntVOImpl)findViewObject("BudgetCntVO1");
    }

    /**Container's getter for PtCshHdrVO3
     */
    public PtCshHdrVOImpl getPtCshHdrVO3() {
        return (PtCshHdrVOImpl)findViewObject("PtCshHdrVO3");
    }

    /**Container's getter for PtCshDtlVO3
     */
    public PtCshDtlVOImpl getPtCshDtlVO3() {
        return (PtCshDtlVOImpl)findViewObject("PtCshDtlVO3");
    }

    /**Container's getter for getLineSeqVO1
     */
    public getLineSeqVOImpl getgetLineSeqVO1() {
        return (getLineSeqVOImpl)findViewObject("getLineSeqVO1");
    }

    /**Container's getter for ExpenseTypeVO2
     */
    public ExpenseTypeVOImpl getExpenseTypeVO2() {
        return (ExpenseTypeVOImpl)findViewObject("ExpenseTypeVO2");
    }
}