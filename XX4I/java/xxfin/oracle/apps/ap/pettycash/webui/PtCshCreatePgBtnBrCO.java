/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;

/**
 * Controller for ...
 */
public class PtCshCreatePgBtnBrCO extends OAControllerImpl
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
      pageContext.writeDiagnostics(this, "Inside PtCshCreatePgBtnBrCO :PFR  Getting  Param -", 4);
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
      pageContext.writeDiagnostics(this, "Inside PtCshCreatePgBtnBrCO :PFR  Getting  Param -----------------------> MODE"+pageContext.getParameter("MODE")+" Getting  Param -----------------------> CNTRCTID "+pageContext.getParameter("CNTRCTID")+" Getting  Param -----------------------> CNTRCTSMEID "+pageContext.getParameter("CNTRCTSMEID"), 4);
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptDtlVO= null;
      ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
      OAViewObject ptRecptVO= null;
      ptRecptVO=(OAViewObject)am.findViewObject("PtCshRecptVO1");
      if(pageContext.getParameter(EVENT_PARAM)!=null)
      {
          if(pageContext.getParameter(EVENT_PARAM).equals("SUBMIT"))
          {
              pageContext.writeDiagnostics(this, "Inside PtCshCreatePgBtnBrCO :PFR  Inside SUBMIT -----------------------> MODE", 4);      
              OAPageButtonBarBean pgButton = (OAPageButtonBarBean)webBean.findChildRecursive("PGBtnBrRN");
          //                OAMessageChoiceBean messageChoice = (OAMessageChoiceBean)webBean.findChildRecursive("MainRN");
              pageContext.writeDiagnostics(this, "Inside " +"PtCshCreatePgBtnBrCO :PFR  Inside SUBMIT -----------------------> pgButton"+pgButton, 4); 
              OAMessageChoiceBean mchoice=(OAMessageChoiceBean)pgButton.findIndexedChild("Action");
              pageContext.writeDiagnostics(this, "Inside " +"PtCshCreatePgBtnBrCO :PFR  Inside SUBMIT -----------------------> MODE"+mchoice, 4);      
          
              pageContext.writeDiagnostics(this, "Inside " +"PtCshCreatePgBtnBrCO :PFR  Inside SUBMIT -----------------------> MODE"+mchoice.getValue(pageContext), 4);    
              pageContext.writeDiagnostics(this, "Inside " +"PtCshCreatePgBtnBrCO :PFR  Inside SUBMIT -----------------------> ptHdrVO.getCurrentRow().getAttribute(\"Attribute10\") "+ptHdrVO.getCurrentRow().getAttribute("Attribute10"), 4);   
          //                 String mChoiceVal2=mchoice.getValue(pageContext).toString();
          //
          //                String val1=pageContext.getParameter("Action");
              pageContext.writeDiagnostics(this, "Inside " +"PtCshCreatePgBtnBrCO :PFR  Inside SUBMIT -----------------------> MODE pageContext.getParameter(\"Action\")  "+pageContext.getParameter("Action"), 4);      

              String username = pageContext.getUserName();
              ptHdrVO.getCurrentRow().setAttribute("RequestedBy", username) ;
              ptHdrVO.getCurrentRow().setAttribute("RequestedFor", username) ;
              ptHdrVO.getCurrentRow().setAttribute("WfStatus", "InProgress") ;              
              ptHdrVO.getCurrentRow().setAttribute("Status", "InProgress") ;   
              am.invokeMethod("apply"); 
//              
//              OAException confirmMessage = new OAException("Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" has been submitted Successfully", OAException.CONFIRMATION);     
//              System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
              
          //                pageContext.putDialogMessage(confirmMessage);
              
          }
          
      
      
      }
      
  }

}
