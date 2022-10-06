/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import com.sun.java.util.collections.HashMap;

import java.io.Serializable;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OADecimalValidater;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.cabo.ui.validate.Formatter;

import xxscm.oracle.apps.po.UtilCO;

/**
 * Controller for ...
 */
public class PtCshRLCreateCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   
   String PDiv;
   String PLoc;
   String PMode;                             
   String PPtyCshHdrId;   
   String PPtycshRecptId;
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
      pageContext.writeDiagnostics(this, "Inside PtCshRLCreateCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID")+"  PTYCSHRECPTID "+pageContext.getParameter("PTYCSHRECPTID"), 4);
      System.out.println("Inside PtCshRLCreateCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID"));
      PDiv = pageContext.getParameter("DIV");
      PLoc = pageContext.getParameter("LOC");
      PPtyCshHdrId = pageContext.getParameter("PTYCSHHDRID");
      PPtycshRecptId = pageContext.getParameter("PTYCSHRECPTID");
      PMode = pageContext.getParameter("MODE");
      if(PDiv == null)
      {
        PDiv = "OPS";
      }     
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptRecptVO= null;
      ptRecptVO=(OAViewObject)am.findViewObject("PtCshRecptVO1");
      UtilCO util;
            util = new UtilCO();


      /**=======================================================================
      *            ADD FUNCTIONALITY FROM SME DETAIL PAGE
      * ====================================================================**/ 
       if(PMode.equals("ADD")&&PPtyCshHdrId!=null)
        {
           pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Before Creating Row", 4);
          Serializable[] sn1={"PtCshRecptVO1"};
          am.invokeMethod("createRecord", sn1); 
            ptRecptVO.getCurrentRow().setAttribute("PtycshHdrId", PPtyCshHdrId); 
            ptRecptVO.getCurrentRow().setAttribute("RCurrency", "OMR");
//          ptRecptVO.getCurrentRow().setAttribute("ExchangeRateDate", am.getOADBTransaction().getCurrentUserDate()); 
//            pageContext.writeDiagnostics(this, "Inside PMode.equals(\"ADD\") Inside If OPS", 4);
//            expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
//            pageContext.writeDiagnostics(this, "Inside PMode.equals(\"ADD\") Inside If OPS expTypeVO.getQuery() "+expTypeVO.getQuery(), 4);
            
        }
       
            else if(PMode.equals("UPD")&&PPtycshRecptId!=null)
            {
                ptRecptVO.setWhereClause(" PTYCSH_RECPT_ID = "+PPtycshRecptId);      
                pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptRecptVO.getQuery(), 4);
                ptRecptVO.executeQuery();
            }
      else if(PMode.equals("FARO"))
      {
          ptRecptVO.setWhereClause(" PTYCSH_RECPT_ID = "+PPtycshRecptId);      
          pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptRecptVO.getQuery(), 4);
          ptRecptVO.executeQuery();
          util.setPageReadOnly(pageContext,webBean);
      }
            
      

        

//      OAMessageFileUploadBean file = (OAMessageFileUploadBean)webBean.findChildRecursive("AttachFile");
//      OADataBoundValueViewObject displayNameBoundValue = new OADataBoundValueViewObject(file, "FileDesc");
//      file.setAttributeValue(OAWebBeanConstants.DOWNLOAD_FILE_NAME, displayNameBoundValue);
      
            OAMessageFileUploadBean rfile = (OAMessageFileUploadBean)webBean.findChildRecursive("RAttachFile");
            OADataBoundValueViewObject rdisplayNameBoundValue = new OADataBoundValueViewObject(rfile, "RFileDesc");
            rfile.setAttributeValue(OAWebBeanConstants.DOWNLOAD_FILE_NAME, rdisplayNameBoundValue);
            
      Formatter formatter =         new OADecimalValidater("#,##0.000;#,##0.000","#,##0.000;#,##0.000");
      OAMessageTextInputBean sbean4 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("RAmount"); 
      sbean4.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
      
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
    
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptRecptVO= null;
      ptRecptVO=(OAViewObject)am.findViewObject("PtCshRecptVO1");
      if(pageContext.getParameter(EVENT_PARAM).equals("CANCEL"))
                    /**=======================================================================
                           *            NAVIGATE TO BACK PAGE OR CANCEL TRANSACTION
                           * ====================================================================**/ 
                             if(pageContext.getParameter(EVENT_PARAM).equals("CANCEL"))
                             {
                                 OADBTransaction txn = am.getOADBTransaction();
                                 txn.rollback();
                                 pageContext.writeDiagnostics(this, "Inside SAVE Logic ----------------------->",4);
                                HashMap hmRlCan=new HashMap();  //Tried to send parameter through SetFowardURL
                                 hmRlCan.put("PTYCSHHDRID", PPtyCshHdrId);
                                 hmRlCan.put("DIV", PDiv);
                                 hmRlCan.put("LOC", PLoc);
                                 if(PMode.equals("FARO"))
                                 {
                                     hmRlCan.put("MODE", PMode);
                                 }
                                 else
                                 {
                                     hmRlCan.put("MODE", "UPD");
                                 } 
//                                 hm.put("CLAIMNUMBER", ptRecptVO.getCurrentRow().getAttribute("RequestNumber"));
                                 pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
                                 null,
                                 //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                                 OAWebBeanConstants.KEEP_MENU_CONTEXT,
                                 null,
                                 hmRlCan, //HashMap                      
                                 false,
                                 OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                                 OAWebBeanConstants.IGNORE_MESSAGES); 
                             }
      /**=======================================================================
              *            SAVE RECORDS WHICH WILL COMMIT THE RECORDS
              * ====================================================================**/ 
                if(pageContext.getParameter(EVENT_PARAM).equals("SAVE"))
                {
      //                    validateFields(pageContext, am, smeDetVO, cntrctAttchVO);
      //                    smeDetVO.getCurrentRow().setAttribute("WfStatus", "Saved");
                validateFields(pageContext, am, ptRecptVO);    
                    am.invokeMethod("apply");  
                    HashMap hmRlSave=new HashMap();  //Tried to send parameter through SetFowardURL
                    hmRlSave.put("DISPMSG", "Records for "+ptRecptVO.getCurrentRow().getAttribute("PtycshRecptId")+" saved Successfully"); 
                    hmRlSave.put("PTYCSHHDRID", PPtyCshHdrId);
                    hmRlSave.put("DIV", PDiv);
                    hmRlSave.put("LOC", PLoc);
                    if(PMode.equals("FARO"))
                    {
                        hmRlSave.put("MODE", PMode);
                    }
                    else
                    {
                        hmRlSave.put("MODE", "UPD");
                    } 
//                    hm.put("CLAIMNUMBER", ptRecptVO.getCurrentRow().getAttribute("RequestNumber"));
                    pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
                    null,
                    //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                     OAWebBeanConstants.KEEP_MENU_CONTEXT,
                    null,
                    hmRlSave, //HashMap                      
                    false,
                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                    OAWebBeanConstants.IGNORE_MESSAGES); 
                }
      
  }
    public void validateFields(OAPageContext pageContext, OAApplicationModule am, OAViewObject ptRecptVO)
       {

           if(ptRecptVO.getCurrentRow().getAttribute("RMopReceived")==null||ptRecptVO.getCurrentRow().getAttribute("RAmount")==null||ptRecptVO.getCurrentRow().getAttribute("RPaymentReceivedDate")==null||ptRecptVO.getCurrentRow().getAttribute("RBriefNarration")==null||ptRecptVO.getCurrentRow().getAttribute("RAttachFile")==null)
           {
             String fieldNums = ""; 
               if(ptRecptVO.getCurrentRow().getAttribute("RMopReceived")==null)
               {
                   fieldNums = " MOP Received ";            
               }
               if(ptRecptVO.getCurrentRow().getAttribute("RAmount")==null)
               {
                   fieldNums = fieldNums+" Amount ";                   
               }
              if(ptRecptVO.getCurrentRow().getAttribute("RPaymentReceivedDate")==null)
               {
                   fieldNums = fieldNums+" Payment Received Date ";            
               }            
               if(ptRecptVO.getCurrentRow().getAttribute("RBriefNarration")==null)
               {                
                   fieldNums = fieldNums+" Breif Narration ";          
               }                      
               if(ptRecptVO.getCurrentRow().getAttribute("RAttachFile")==null)
               {
                   fieldNums = fieldNums+" Attachment ";            
               }
               
               throw new OAException("Fields should be Entered :"+fieldNums,OAException.ERROR); 
           }
       }

}
