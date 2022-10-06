/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import java.io.Serializable;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.jbo.Row;

/**
 * Controller for ...
 */
public class PtCshSeqCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);

        pageContext.writeDiagnostics(this, "Inside PtCshSeqCO :PFR  Getting  Param -----------------------> MODE"+pageContext.getParameter("MODE")+" Getting  Param -----------------------> CNTRCTID "+pageContext.getParameter("CNTRCTID")+" Getting  Param -----------------------> CNTRCTSMEID "+pageContext.getParameter("CNTRCTSMEID")+"Getting Session Value pageContext.getSessionValue(\"pFromPFR\")"+pageContext.getSessionValue("pFromPFR"), 4);
        System.out.println("Inside PtCshSeqCO : Getting  Param -----------------------> CONTRACTNUMBER"+pageContext.getParameter("CONTRACTNUMBER"));
        OAApplicationModule am=null;
        am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
        OAViewObject ptSeqVO= null;
        ptSeqVO=(OAViewObject)am.findViewObject("PtCshSeqVO1");
        pageContext.writeDiagnostics(this, "Inside PtCshSeqCO :PR  Getting  ptSeqVO -----------------------> ptSeqVO"+ptSeqVO, 4);
        
        ptSeqVO.reset();
        pageContext.writeDiagnostics(this, "Inside PtCshSeqCO :PR  Getting  ptSeqVO -----------------------> ptSeqVO"+ptSeqVO, 4);
        ptSeqVO.setWhereClause(" 1=1 ");
        pageContext.writeDiagnostics(this, "Inside PtCshSeqCO :PR  Getting  ptSeqVO -----------------------> ptSeqVO"+ptSeqVO.getQuery(), 4);
        ptSeqVO.setOrderByClause("PTYCSH_SEQ_ID desc");
        ptSeqVO.executeQuery();
        ptSeqVO.first();
        pageContext.writeDiagnostics(this, "Inside PtCshSeqCO :PR  Getting  ptSeqVO -----------------------> ptSeqVO"+ptSeqVO.getRowCount(), 4);
      
  }


  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processFormRequest(pageContext, webBean);
  
    pageContext.writeDiagnostics(this, "Inside PtCshSeqCO :PFR  Getting  Param -----------------------> MODE"+pageContext.getParameter("MODE")+" Getting  Param -----------------------> CNTRCTID "+pageContext.getParameter("CNTRCTID")+" Getting  Param -----------------------> CNTRCTSMEID "+pageContext.getParameter("CNTRCTSMEID")+"Getting Session Value pageContext.getSessionValue(\"pFromPFR\")"+pageContext.getSessionValue("pFromPFR"), 4);
    System.out.println("Inside PtCshSeqCO : Getting  Param -----------------------> CONTRACTNUMBER"+pageContext.getParameter("CONTRACTNUMBER"));
    OAApplicationModule am=null;
    am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
    OAViewObject ptSeqVO= null;
    ptSeqVO=(OAViewObject)am.findViewObject("PtCshSeqVO1");
    /**=======================================================================
    *            SAVING SEQUENCE 
    * ====================================================================**/ 
    if(pageContext.getParameter("Save")!=null) 
    {
        pageContext.writeDiagnostics(this, "Inside SAVE Logic ----------------------->",4);
        System.out.println("Inside SAVE Logic ----------------------->"+ptSeqVO.getRowCount());
        int rowcount1 = ptSeqVO.getRowCount();
        if( rowcount1 > 0)
        {
        
          Row row[] = ptSeqVO.getAllRowsInRange();
          int getSumOfAllQty=0;
          for (int i=0;  i<row.length; i++)
          {
                 OARow rowi = (OARow)row[i];
                 pageContext.writeDiagnostics(this, "Getting Current row of ptSeqVO"+i+"StartingNumber ---> "+rowi.getAttribute("StartingNumber")+"rowi.getAttribute(\"CurrentRunningSequence\")"+rowi.getAttribute("CurrentRunningSequence"), 4);
                 getSumOfAllQty = Integer.parseInt((rowi.getAttribute("StartingNumber")).toString());
                 pageContext.writeDiagnostics(this, "Getting Current row of getSumOfAllQty Value to Check the Values "+getSumOfAllQty, 4);
                 if(getSumOfAllQty!=0)
                 {
                     if( Integer.parseInt((rowi.getAttribute("CurrentRunningSequence").toString())) == 0)
                    {    
                        pageContext.writeDiagnostics(this, "Inside CurrentRunningSequence = 0 and setting the Value", 4);
                        rowi.setAttribute("CurrentRunningSequence", getSumOfAllQty);
                    }
                }
                    
          }
        }
        pageContext.writeDiagnostics(this, "Outer - Getting Current row of getSumOfAllQty Value to Check the Values ", 4);
        am.invokeMethod("apply"); 
        OAException confirmMessage = new OAException("Sequences has been saved Successfully", OAException.CONFIRMATION);     
        System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
        pageContext.writeDiagnostics(this, " Inside PtCshCreateCO :PFR  Inside SUBMIT  Getting Value of "+confirmMessage, 4);
        pageContext.putDialogMessage(confirmMessage);
    }
    if(pageContext.getParameter("Refresh")!=null) 
    {
        OADBTransaction txn = am.getOADBTransaction();
        txn.rollback();
        OAException confirmMessage = new OAException("Transaction has been reverted Successfully", OAException.CONFIRMATION);     
        System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
        pageContext.writeDiagnostics(this, " Inside PtCshCreateCO :PFR  Inside SUBMIT  Getting Value of "+confirmMessage, 4);
        pageContext.putDialogMessage(confirmMessage);    
        
    }
    if(pageContext.getParameter(EVENT_PARAM)!=null)
    {
        if(pageContext.getParameter(EVENT_PARAM).equals("ADDROWS"))
        {
        
              //  pageEvent = pageContext.getParameter(EVENT_PARAM);
            pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Before Creating Row PtCshSeqVO1 ", 4);
            Serializable[] sn1={"PtCshSeqVO1"};
            am.invokeMethod("createRecord", sn1);
            ptSeqVO.getCurrentRow().setAttribute("CurrentRunningSequence", "0");
        }
    }
}
}