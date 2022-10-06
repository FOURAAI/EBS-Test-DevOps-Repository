/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import com.sun.java.util.collections.HashMap;

import java.util.Hashtable;

import java.io.Serializable;

import java.sql.CallableStatement;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;
import java.sql.Types;



import oracle.apps.fnd.framework.server.OADBTransaction;


import oracle.apps.fnd.framework.webui.OADecimalValidater;
import oracle.apps.fnd.framework.webui.beans.layout.OAPageLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.apps.fnd.framework.webui.beans.table.OATableBean;

import oracle.cabo.style.CSSStyle;
import oracle.cabo.ui.validate.Formatter;

import oracle.jbo.Row;

import xxscm.oracle.apps.po.UtilCO;

/**
 * Controller for ...
 */
public class PtCshCreateCO extends OAControllerImpl
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
    String PDispMsg;
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      UtilCO util;
            util = new UtilCO();
            pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID")+" MODE "+pageContext.getParameter("MODE"), 4);
            System.out.println("Inside XXSCMCntrctDetCO : Getting  Param -----------------------> CONTRACTNUMBER"+pageContext.getParameter("CONTRACTNUMBER"));
            PDiv = pageContext.getParameter("DIV");
            PLoc = pageContext.getParameter("LOC");
            PPtyCshHdrId = pageContext.getParameter("PTYCSHHDRID");
            PMode = pageContext.getParameter("MODE");
            PDispMsg = pageContext.getParameter("DISPMSG");
          if(PDiv == null)
          {
              PDiv = "OPS";
          }
          if(PMode == null)
          {
              PMode = "ADD";
          }
          if(PMode == "ADD")
          {
              PPtyCshHdrId = "";
          }
//            pCntrctSmeID =pageContext.getParameter("CNTRCTSMEID");
//            PContractNumber = pageContext.getParameter("CONTRACTNUMBER");
//            ProwReference = pageContext.getParameter("ProwReference");
            OAApplicationModule am=null;
            am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
            OAViewObject ptHdrVO= null;
            ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
            OAViewObject ptDtlVO= null;
            ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
            OAViewObject ptRecptVO= null;
            ptRecptVO=(OAViewObject)am.findViewObject("PtCshRecptVO1");
            OAViewObject expTypeVO= null;
            expTypeVO=(OAViewObject)am.findViewObject("ExpenseTypeVO1");
            OAViewObject supVO= null;
            supVO=(OAViewObject)am.findViewObject("SuppVO1");
            
   
            

            /**=======================================================================
            *            ADD FUNCTIONALITY FROM SME DETAIL PAGE
            * ====================================================================**/ 
            if(PMode.equals("ADD")&&PPtyCshHdrId==null)
            {
                 pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Before Creating Row", 4);
                Serializable[] sn1={"PtCshHdrVO1"};
                am.invokeMethod("createRecord", sn1); 
                OAViewObject ptHdrVO3= null;
                ptHdrVO3=(OAViewObject)am.findViewObject("PtCshHdrVO3");
                if(PLoc!=null)
                {
                     ptHdrVO3.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and LOCATION_NAME = '"+PLoc+"' and PTYCSH_HDR_ID = (select max(PTYCSH_HDR_ID) from xxfin_ap_pettycash_hdr_v where  DIVISION = '"+PDiv+"' and LOCATION_NAME = '"+PLoc+"') " );    
                }
                else
                {
                     ptHdrVO3.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and PTYCSH_HDR_ID = (select max(PTYCSH_HDR_ID) from xxfin_ap_pettycash_hdr_v where   DIVISION = '"+PDiv+"' and LOCATION_NAME is null)");  
                }
                pageContext.writeDiagnostics(this, "Inside Params with Values SAVE getting Query ptHdrVO2----------------------->"+ptHdrVO3.getQuery(),4);  
                ptHdrVO3.executeQuery(); 
                ptHdrVO3.first();
                pageContext.writeDiagnostics(this, "Inside Params with Values SAVE getting Query ptHdrVO2----------------------->"+ptHdrVO3.getFetchedRowCount(),4);  
                if(ptHdrVO3.getRowCount()>0)
                {
                    pageContext.writeDiagnostics(this, "Inside PtCshCreateCO SAVE ptHdrVO2.getRowCount()>0----------------------->"+ptHdrVO3.getCurrentRow().getAttribute("ClosingBalDesc"), 4);
                    ptHdrVO.getCurrentRow().setAttribute("OpeningBal", ptHdrVO3.getCurrentRow().getAttribute("ClosingBalDesc"));
                    OAMessageTextInputBean openingBal = (OAMessageTextInputBean)webBean.findChildRecursive("OpeningBal");
                    openingBal.setReadOnly(Boolean.TRUE);
                }
                
                ptHdrVO.getCurrentRow().setAttribute("RequestDateDesc", am.getOADBTransaction().getCurrentUserDate());    
                ptHdrVO.getCurrentRow().setAttribute("LocationName", PLoc);    
                ptHdrVO.getCurrentRow().setAttribute("Division", PDiv);    
                ptHdrVO.getCurrentRow().setAttribute("Status", "Draft");    
                ptHdrVO.getCurrentRow().setAttribute("Governate", "HAYA Muscat Op.Unit");    
                ptHdrVO.getCurrentRow().setAttribute("DisableAprStatus", Boolean.TRUE);    
                initializePgLoad(pageContext, PDiv, PLoc, am, ptHdrVO, expTypeVO, supVO);
                setIOUNumberAmt(am, pageContext, ptHdrVO);
                
            }
            else if((PPtyCshHdrId!=null||PPtyCshHdrId!="")&&PMode.equals("UPD"))
            {
                ptHdrVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);
                ptHdrVO.executeQuery();
                ptHdrVO.first();

                if(PLoc==null||PLoc=="")
                {
                    pageContext.writeDiagnostics(this, "Inside  Inside If OPS with PLoc==null ", 4);
                    ptHdrVO.getCurrentRow().setAttribute("DispModeLoc", Boolean.TRUE);
                    if(ptHdrVO.getCurrentRow().getAttribute("Division").equals("OPS"))
                    {
                        ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.TRUE);
                        
                    }
                    else
                    {
                        ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);
                    }
                }
                else
                {
                    pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS insdie else Condition  ========TESTING", 4);
                    ptHdrVO.getCurrentRow().setAttribute("DispModeLoc", Boolean.FALSE);
                    ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);                    
                }
                System.out.println("Executing the Query "+ptHdrVO.getQuery());
                pageContext.writeDiagnostics(this, "Executing the Query"+ptHdrVO.getQuery(), 4);
                System.out.println("Getting the RowCount After Execution "+ptHdrVO.getRowCount());
                pageContext.writeDiagnostics(this, "Executing the Query After Execution"+ptHdrVO.getRowCount(), 4);
                pageContext.writeDiagnostics(this, "Inside IF Condition Checking with the Values of the Fetched Row Count "+ptHdrVO.getFetchedRowCount(), 4);          
                System.out.println("Inside IF Condition Checking with the Values of the Fetched Row Count "+ptHdrVO.getFetchedRowCount());   
                
                ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);      
                ptDtlVO.setOrderByClause(" creation_date desc "); 
                pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
                ptDtlVO.executeQuery();
                ptRecptVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);      
                ptRecptVO.setOrderByClause(" creation_date desc "); 
                pageContext.writeDiagnostics(this, "Getting ptRecptVO.getQuery() "+ptRecptVO.getQuery(), 4);
                ptRecptVO.executeQuery();
                
                if(ptHdrVO.getCurrentRow().getAttribute("Status").equals("Approved")||ptHdrVO.getCurrentRow().getAttribute("Status").equals("Approval in Progress"))
                {
                    pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Inside ReadOnly Mode and Disabling the Buttons as status in Approved ", 4);
                    ptHdrVO.getCurrentRow().setAttribute("DisableAprStatus", Boolean.FALSE);    
                    ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE); 
                    util.setPageReadOnly(pageContext,webBean);
                }
                else
                {
                    ptHdrVO.getCurrentRow().setAttribute("DisableAprStatus", Boolean.TRUE);  
                    setIOUNumberAmt(am, pageContext, ptHdrVO);
                    ptHdrVO.getCurrentRow().setAttribute("RequestDateDesc", am.getOADBTransaction().getCurrentUserDate());    
                }

                
                
//                if(smeDetExpVO!=null)
//                {
//                System.out.println("Inside PR is not null and Querying the Export VO Query");
//                pageContext.writeDiagnostics(this, "Inside PR is not null and Querying the Export VO Query"+cntrctMasVO.getFetchedRowCount(), 4);    
//                smeDetExpVO.setWhereClause(" contract_number = "+PContractNumber);         
//                smeDetExpVO.executeQuery();
//                }
            
            
            }
         else if((PPtyCshHdrId!=null||PPtyCshHdrId!="")&&PMode.equals("FARO"))
         {
             pageContext.writeDiagnostics(this, "Inside FUll Access Read Only Page ", 4);
             ptHdrVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);
             ptHdrVO.executeQuery();
             ptHdrVO.first();

         
            pageContext.writeDiagnostics(this, "Inside  Inside If OPS with PLoc==null ", 4);
            ptHdrVO.getCurrentRow().setAttribute("DispModeLoc", Boolean.TRUE);
            
            ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);
              
             ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);      
             ptDtlVO.executeQuery();
             pageContext.writeDiagnostics(this, "Getting ptRecptVO.getQuery() "+ptRecptVO.getQuery(), 4);
             ptRecptVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId); 
             ptRecptVO.executeQuery();
             
                 pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Inside ReadOnly Mode and Disabling the Buttons as status in Approved ", 4);
                 ptHdrVO.getCurrentRow().setAttribute("DisableAprStatus", Boolean.FALSE);    
                 ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE); 
                 //setIOUNumberAmt(am, pageContext, ptHdrVO);
                 util.setPageReadOnly(pageContext,webBean);
             
             
             
             //                if(smeDetExpVO!=null)
             //                {
             //                System.out.println("Inside PR is not null and Querying the Export VO Query");
             //                pageContext.writeDiagnostics(this, "Inside PR is not null and Querying the Export VO Query"+cntrctMasVO.getFetchedRowCount(), 4);
             //                smeDetExpVO.setWhereClause(" contract_number = "+PContractNumber);
             //                smeDetExpVO.executeQuery();
             //                }
             
         
         }
          /**=======================================================================
          *            ADD FUNCTIONALITY FROM SME DETAIL PAGE
          * ====================================================================**/ 
          if(PPtyCshHdrId!=null&&PDispMsg!=null)
          {
              OAException confirmMessage = new OAException(PDispMsg, OAException.CONFIRMATION);     
              System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
              pageContext.writeDiagnostics(this, "Getting Value of "+PDispMsg, 4);
              pageContext.putDialogMessage(confirmMessage);   
          }
           
      
         
           
      OAViewObject ptBudVO= null;
      ptBudVO=(OAViewObject)am.findViewObject("BudgetVO1");
      OAViewObject ptBudCntVO= null;
      ptBudCntVO=(OAViewObject)am.findViewObject("BudgetCntVO1");
      pageContext.writeDiagnostics(this, "Before  "+PDispMsg, 4);
      
      ptBudCntVO.setWhereClause(" PTYCSH_HDR_ID = "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId"));
      pageContext.writeDiagnostics(this, "Getting Value of Petty Cash Budget Count ================> "+ptBudCntVO.getQuery(), 4);
      ptBudCntVO.executeQuery();
      ptBudCntVO.first();
      if(ptBudCntVO.getRowCount()>0)
      {
              pageContext.writeDiagnostics(this, "Getting Value of Petty Cash Budget Count ================>ptBudCntVO.getCurrentRow().getAttribute(\"CntRec\" : "+ptBudCntVO.getCurrentRow().getAttribute("CntRec")+"ptBudCntVO.getCurrentRow().getAttribute(\"BudgetResultsDescCnt\") : "+ptBudCntVO.getCurrentRow().getAttribute("BudgetResultsDescCnt"), 4);
              
              if(Integer.parseInt(ptBudCntVO.getCurrentRow().getAttribute("CntRec").toString())==0)
              {
                  pageContext.writeDiagnostics(this, "Need to remove the Records in the VO", 4);
                  ptBudVO.setWhereClause(" value not in ('Budget Check', 'Budget Mail', 'Submit for Approval') ");
                  pageContext.writeDiagnostics(this, "Getting Value of Petty Cash Budget Count ================>"+ptBudVO.getQuery(), 4);
                  ptBudVO.executeQuery();
              }
              else
              {
              
                  if(PLoc==null||PLoc=="")
                  {
                      pageContext.writeDiagnostics(this, "Records Exist=========================== ", 4);
                      if(Integer.parseInt(ptBudCntVO.getCurrentRow().getAttribute("BudgetResultsDescCnt").toString())==0)
                      {
                          pageContext.writeDiagnostics(this, "Records Exist=========================== and Budgets has been passed", 4);
                          ptBudVO.setWhereClause(" value <> 'Budget Mail' ");
                          pageContext.writeDiagnostics(this, "Getting Value of Petty Cash Budget Count ================>"+ptBudVO.getQuery(), 4);
                          ptBudVO.executeQuery();
                      }
                      else
                      {
                          pageContext.writeDiagnostics(this, "Records Exist=========================== and Budgets has been Failed/Invalid Combination", 4);
                          ptBudVO.setWhereClause(" value <> 'Submit for Approval' ");
                      }
                  }
                  else
                  {
                      pageContext.writeDiagnostics(this, "Records Exist=========================== Inside Location Site Exists"+PLoc, 4);
                      ptBudVO.setWhereClause(" value not in ('Budget Check', 'Budget Mail') ");
                  }
              }
              
      }   
      else
      {
          pageContext.writeDiagnostics(this, "Need to remove the Records in the VO as in else condition ptBudCntVO.getRowCount()", 4);
          ptBudVO.setWhereClause(" value not in ('Budget Check', 'Budget Mail', 'Submit for Approval') ");
          pageContext.writeDiagnostics(this, "Getting Value of Petty Cash Budget Count ================>"+ptBudVO.getQuery(), 4);
          ptBudVO.executeQuery();
      
      }
      
//         OAMessageFileUploadBean file = (OAMessageFileUploadBean)webBean.findChildRecursive("AttachFile");
//         OADataBoundValueViewObject displayNameBoundValue = new OADataBoundValueViewObject(file, "FileDesc");
//         file.setAttributeValue(OAWebBeanConstants.DOWNLOAD_FILE_NAME, displayNameBoundValue);
//         
//        OAMessageFileUploadBean rfile = (OAMessageFileUploadBean)webBean.findChildRecursive("RAttachFile");
//        OADataBoundValueViewObject rdisplayNameBoundValue = new OADataBoundValueViewObject(rfile, "RFileDesc");
//        rfile.setAttributeValue(OAWebBeanConstants.DOWNLOAD_FILE_NAME, rdisplayNameBoundValue);
//       pageContext.writeDiagnostics(this, "Inside PtCshCreateCO :PR  --TESTING  Before Entereing pageContext.getSessionValue(\"pFromPFR\")"+pageContext.getSessionValue("pFromPFR"), 4);      
        
        pageContext.writeDiagnostics(this, "Value of the Decimal Formatter  1", 4);
        OAPageLayoutBean pageBean = (OAPageLayoutBean)pageContext.getPageLayoutBean();
        OATableBean tableBean = (OATableBean)webBean.findIndexedChildRecursive("claimLinesTblRN");
        OATableBean rtableBean = (OATableBean)webBean.findIndexedChildRecursive("receiptLinesTblRN");
        pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 2"+pageBean, 4);
        Formatter formatter =         new OADecimalValidater("#,##0.000;#,##0.000","#,##0.000;#,##0.000");
        pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 3"+formatter, 4);
        OAMessageTextInputBean sbean2 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("OpeningBal");
        pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 4"+sbean2, 4);
        sbean2.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);   
        pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 5 After Setting", 4);
        OAMessageTextInputBean sbean3 = (OAMessageTextInputBean)tableBean.findIndexedChildRecursive("Amount111"); 
        OAMessageStyledTextBean sbean4 = (OAMessageStyledTextBean)webBean.findIndexedChildRecursive("ClosingBalance113"); 
        OAMessageTextInputBean sbean5 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("CashInHand113"); 
        OAMessageTextInputBean sbean6 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("Receipts114");      
        OAMessageTextInputBean sbean7 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("ClaimAmount114");
        OAMessageTextInputBean sbean8 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("StaffIouAmount114");
        OAMessageTextInputBean rsbean3 = (OAMessageTextInputBean)rtableBean.findIndexedChildRecursive("RAmount11");
        sbean3.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
        sbean4.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
        sbean5.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
        sbean6.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
        sbean7.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
      CSSStyle csNum = new CSSStyle();
      csNum.setProperty("text-align", "right");
      sbean2.setInlineStyle(csNum);
      sbean3.setInlineStyle(csNum);
      sbean4.setInlineStyle(csNum);
      sbean5.setInlineStyle(csNum);
      sbean6.setInlineStyle(csNum);
      sbean7.setInlineStyle(csNum);
    
      
        
          if(sbean8!=null)
          {
          sbean8.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
          }
        rsbean3.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
      
 
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
      pageContext.writeDiagnostics(this, "Inside PtCshCreateCO :PFR  Getting  Param -----------------------> MODE"+pageContext.getParameter("MODE")+" Getting  Param -----------------------> CNTRCTID "+pageContext.getParameter("CNTRCTID")+" Getting  Param -----------------------> CNTRCTSMEID "+pageContext.getParameter("CNTRCTSMEID")+"Getting Session Value pageContext.getSessionValue(\"pFromPFR\")"+pageContext.getSessionValue("pFromPFR"), 4);
      System.out.println("Inside XXSCMCntrctDetCO : Getting  Param -----------------------> CONTRACTNUMBER"+pageContext.getParameter("CONTRACTNUMBER"));
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptDtlVO= null;
      ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
      OAViewObject ptRecptVO= null;
      ptRecptVO=(OAViewObject)am.findViewObject("PtCshRecptVO1");
    pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : PFR --pageContext.getParameter(\"Action\")!=null"+pageContext.getParameter("Action"), 4);
      
    if(pageContext.getParameter("Action")!=null) 
        {
            pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : PFR --pageContext.getParameter(\"Action\")!=null", 4);
            pageContext.writeDiagnostics(this, "Inside PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> MODE", 4);      
            OAPageButtonBarBean pgButton = (OAPageButtonBarBean)webBean.findChildRecursive("PGBtnBrRN");
            //                OAMessageChoiceBean messageChoice = (OAMessageChoiceBean)webBean.findChildRecursive("MainRN");
            pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING Inside SUBMIT -----------------------> pgButton"+pgButton, 4); 
            OAMessageChoiceBean mchoice=(OAMessageChoiceBean)pgButton.findIndexedChild("Action");
            pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> MODE"+mchoice, 4);      
            
            pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> MODE"+mchoice.getValue(pageContext), 4);    
            pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> ptHdrVO.getCurrentRow().getAttribute(\"Attribute10\") "+ptHdrVO.getCurrentRow().getAttribute("Attribute10"), 4);   
            //
        }
             pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : PFR --pageContext.getParameter(\"Submit\")!=null"+pageContext.getParameter("Submit"), 4);
        
             if(pageContext.getParameter("Submit")!=null) 
                 {
                     pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : PFR --pageContext.getParameter(\"Submit\")!=null", 4);
                     pageContext.writeDiagnostics(this, "Inside PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> MODE", 4);      
                     OAPageButtonBarBean pgButton = (OAPageButtonBarBean)webBean.findChildRecursive("PGBtnBrRN");
                     //                OAMessageChoiceBean messageChoice = (OAMessageChoiceBean)webBean.findChildRecursive("MainRN");
                     pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING Inside SUBMIT -----------------------> pgButton"+pgButton, 4); 
                     OAMessageChoiceBean mchoice=(OAMessageChoiceBean)pgButton.findIndexedChild("Action");
                     pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> MODE"+mchoice, 4);      
                     
                     pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> MODE"+mchoice.getValue(pageContext), 4);    
                     pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT -----------------------> ptHdrVO.getCurrentRow().getAttribute(\"Attribute10\") "+ptHdrVO.getCurrentRow().getAttribute("Attribute10"), 4);   
                     String username = pageContext.getUserName();
                     ptHdrVO.getCurrentRow().setAttribute("RequestedBy", username) ;
                     ptHdrVO.getCurrentRow().setAttribute("RequestedFor", username) ;
                     ptHdrVO.getCurrentRow().setAttribute("RequestDate", am.getOADBTransaction().getCurrentUserDate());    
                     am.invokeMethod("apply"); 
                     //
                     if(mchoice.getValue(pageContext).equals("Submit for Approval"))
                     {
                     
                         String validateAllo = validateAllocation(am, pageContext);
                         if( validateAllo.equals("Y"))
                           {
                           setIOUNumberAmt(am, pageContext, ptHdrVO);
                            pageContext.writeDiagnostics(this, "Inside validateAllo is Y- and Thro == Proceeding Further---------------------->", 4);
                            oracle.jbo.domain.Number flowIterationNum = (oracle.jbo.domain.Number)ptHdrVO.getCurrentRow().getAttribute("FlowIteration");          
                            
                            pageContext.writeDiagnostics(this, "Getting flowIteration   "+flowIterationNum, 4);
                            pageContext.writeDiagnostics(this, "Getting flowIteration & Status  "+ptHdrVO.getCurrentRow().getAttribute("Status"), 4);
                            
                            if(ptHdrVO.getCurrentRow().getAttribute("Status").equals("Rejected"))
                            {
                                pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected  "+flowIterationNum, 4);
                                if(flowIterationNum==null)
                                {
                                
                                  pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside Flow Iteration is Null "+flowIterationNum, 4);
                                  ptHdrVO.getCurrentRow().setAttribute("FlowIteration", 0);
                                  pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside setting Flow Iteration to 0 "+flowIterationNum, 4);
                                   Integer flowIteration = 0;
                                  pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside Flow Iteration is Null "+flowIteration, 4);
                                  ptHdrVO.getCurrentRow().setAttribute("WfItemKey", (ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")).toString()+" - "+flowIteration);
                                  pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside Flow Iteration is Null  and setting ptHdrVO.getCurrentRow().getAttribute(\"WfItemKey\") "+ptHdrVO.getCurrentRow().getAttribute("WfItemKey"), 4);
                                }
                                else
                                {
                                  pageContext.writeDiagnostics(this, "Inside SUBMIT Logic Getting Flow Iteration----------------------->"+ptHdrVO.getCurrentRow().getAttribute("FlowIteration"),4);      
                                  Integer flowIteration = Integer.parseInt(flowIterationNum.toString());
                                  pageContext.writeDiagnostics(this, "Inside SUBMIT Logic Getting Flow Iteration----------------------->"+flowIteration+"",4);
                                  flowIteration=flowIteration+1;
                                  pageContext.writeDiagnostics(this, "Inside SUBMIT Logic Getting Flow Iteration--------UPDATED VALUE and Setting--------------->"+flowIteration+"",4);
                                  ptHdrVO.getCurrentRow().setAttribute("FlowIteration", flowIteration);
                                  ptHdrVO.getCurrentRow().setAttribute("WfItemKey", (ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")).toString()+" - "+flowIteration);
                                  ptHdrVO.getCurrentRow().setAttribute("RequestDate", am.getOADBTransaction().getCurrentUserDate());    
                                }
                            }
                            else
                            {
                                ptHdrVO.getCurrentRow().setAttribute("WfItemKey", ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId"));
                            }
                            am.invokeMethod("apply");  
                            
                            pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT ---Inside Submit for Approval-------------------->"+mchoice.getValue(pageContext), 4);    
                            CallableStatement callableStatement =
                            am.getOADBTransaction().createCallableStatement("declare begin XXHW_FIN_PETTYCASH_WF_PKG.START_PROCESS (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+", '"+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"' , :1, :2 ); end;",OADBTransaction.DEFAULT); 
                            //Refer to the Syntax where the String is a procedure 
                            //Create a JDBC CallableStatement with the PL/SQL block containing the stored procedure invocation
                            //Bind any variables.
                            String valuetobePassed2=null;
                            String  outParamValue1=null;
                            String  outParamValue2 =null;
                            String  outParamValue3 =null;
                            try
                            {
                            callableStatement.registerOutParameter(1, Types.VARCHAR); //First Bind Varible should give us out Parameter
                            callableStatement.registerOutParameter(2, Types.VARCHAR); //First Bind Varible should give us out Parameter            
                            callableStatement.execute(); // Execute the statement.
                            System.out.println("After Executing the PLSQL for Workflow"+"declare begin XXHW_FIN_PETTYCASH_WF_PKG.START_PROCESS (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+",'"+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"', :1, :2 ); end;");
                            pageContext.writeDiagnostics(this, "After Executing the PLSQL for Workflow"+"declare begin XXHW_FIN_PETTYCASH_WF_PKG.START_PROCESS (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+", '"+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"' , :1, :2 ); end;" , 4);
                            outParamValue1 = (callableStatement.getString(1)).toString(); //Retrieve the values of any OUT parameters.
                            outParamValue2 = (callableStatement.getString(2)).toString(); //Retrieve the values of any OUT parameters.            
                            }
                            catch (Exception sqle)  //Be cautious that execute() throws an SQL Exception
                            {
                            System.out.println("Exception occured"+sqle);
                            pageContext.writeDiagnostics(this, "INside Exception Wile Invoking Workflow "+sqle , 4);
                            System.out.println("INside Exception "+sqle);
                            }
                            pageContext.writeDiagnostics(this, "Workflow is @ Package XXHW_FIN_PETTYCASH_WF_PKG Initiated"+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+outParamValue1+outParamValue2, 4);
                            am.invokeMethod("apply");  
                            //                                         HashMap hm=new HashMap();  //Tried to send parameter through SetFowardURL
                            //                                         hm.put("DISPMSG", "Records for Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" has submited for Approval"); 
                            //                         OAException confirmMessage = new OAException("Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" has been submitted for Approval", OAException.CONFIRMATION);     
                            //                         System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
                            //                         pageContext.writeDiagnostics(this, " Inside PtCshCreateCO :PFR  Inside SUBMIT  Getting Value of "+confirmMessage, 4);
                            //                         pageContext.putDialogMessage(confirmMessage);
                            
                            HashMap hmsfa=new HashMap();  //Tried to send parameter through SetFowardURL
                            hmsfa.clear();
                            //                hm.put("MODE", "ADD");
                            //    hm.put("PTYCSHHDRID", "");
                            hmsfa.put("DIV", PDiv);
                            hmsfa.put("LOC", PLoc);
                            hmsfa.put("DISPMSG", "Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" has been submitted for Approval");
                            
                            pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshSearchPG",
                            null,
                            //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                            OAWebBeanConstants.KEEP_MENU_CONTEXT,
                            null,
                            hmsfa, //HashMap
                            false,
                            OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                            OAWebBeanConstants.IGNORE_MESSAGES);
                            
                         
                         }
                         else
                              {
                                  OAException confirmMessage = new OAException(validateAllo , OAException.ERROR);
                                  pageContext.writeDiagnostics(this, "Inside Validation Logic Before throwing Exception ----------------------->", 4);
                                  pageContext.putDialogMessage(confirmMessage);
                              }
                     }
                     else if(mchoice.getValue(pageContext).equals("Budget Check"))
                     {
                         pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT --Inside --Budget Check-------------------> "+mchoice.getValue(pageContext), 4);    
                         ptDtlVO.reset();
                         ptDtlVO.setWhereClauseParams(null); // Always reset
                         ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);      
                         pageContext.writeDiagnostics(this, "Inside Budget Check Executing and getting ptDtlVO query "+ptDtlVO.getQuery(), 4);
                         ptDtlVO.executeQuery();
                         
                         
                     }
                     else if(mchoice.getValue(pageContext).equals("Budget Mail"))
                     {
                         pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PR  --TESTING  Inside SUBMIT ---Inside Budget Mail--------------------> "+mchoice.getValue(pageContext), 4);    
                         oracle.jbo.domain.Number flowIterationNum = (oracle.jbo.domain.Number)ptHdrVO.getCurrentRow().getAttribute("FlowIteration");          
                         if(flowIterationNum==null)
                         {
                         
                              pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside Flow Iteration is Null "+flowIterationNum, 4);
                              ptHdrVO.getCurrentRow().setAttribute("FlowIteration", 0);
                              pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside setting Flow Iteration to 0 "+flowIterationNum, 4);
                               Integer flowIteration = 0;
                              pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside Flow Iteration is Null "+flowIteration, 4);
                              ptHdrVO.getCurrentRow().setAttribute("WfItemKey", (ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")).toString()+" - "+flowIteration);
                              pageContext.writeDiagnostics(this, "Getting flowIteration Inside Rejected Inside Flow Iteration is Null  and setting ptHdrVO.getCurrentRow().getAttribute(\"WfItemKey\") "+ptHdrVO.getCurrentRow().getAttribute("WfItemKey"), 4);
                         }
                         else
                         {
                              pageContext.writeDiagnostics(this, "Inside SUBMIT Logic Getting Flow Iteration----------------------->"+ptHdrVO.getCurrentRow().getAttribute("FlowIteration"),4);      
                              Integer flowIteration = Integer.parseInt(flowIterationNum.toString());
                              pageContext.writeDiagnostics(this, "Inside SUBMIT Logic Getting Flow Iteration----------------------->"+flowIteration+"",4);
                              flowIteration=flowIteration+1;
                              pageContext.writeDiagnostics(this, "Inside SUBMIT Logic Getting Flow Iteration--------UPDATED VALUE and Setting--------------->"+flowIteration+"",4);
                              ptHdrVO.getCurrentRow().setAttribute("FlowIteration", flowIteration);
                              ptHdrVO.getCurrentRow().setAttribute("WfItemKey", (ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")).toString()+" - "+flowIteration);
//                              ptHdrVO.getCurrentRow().setAttribute("RequestDate", am.getOADBTransaction().getCurrentUserDate());    
                         }
                         CallableStatement callableStatement =
                         am.getOADBTransaction().createCallableStatement("declare begin XXHW_FIN_PETTYCASH_WF_PKG.budget_fyi_start_process (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+", '"+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"' , :1, :2 ); end;",OADBTransaction.DEFAULT); 
                         //Refer to the Syntax where the String is a procedure 
                         //Create a JDBC CallableStatement with the PL/SQL block containing the stored procedure invocation
                         //Bind any variables.
                         String valuetobePassed2=null;
                         String  outParamValue1=null;
                         String  outParamValue2 =null;
                         String  outParamValue3 =null;
                         try
                         {
                             callableStatement.registerOutParameter(1, Types.VARCHAR); //First Bind Varible should give us out Parameter
                             callableStatement.registerOutParameter(2, Types.VARCHAR); //First Bind Varible should give us out Parameter            
                             callableStatement.execute(); // Execute the statement.
                             System.out.println("After Executing the PLSQL for Workflow"+"declare begin XXHW_FIN_PETTYCASH_WF_PKG.budget_fyi_start_process (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+",'"+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"' , :1, :2 ); end;");
                             pageContext.writeDiagnostics(this, "After Executing the PLSQL for Workflow"+"declare begin XXHW_FIN_PETTYCASH_WF_PKG.budget_fyi_start_process (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+", '"+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"'  , :1, :2 ); end;" , 4);
                             outParamValue1 = (callableStatement.getString(1)).toString(); //Retrieve the values of any OUT parameters.
                             outParamValue2 = (callableStatement.getString(2)).toString(); //Retrieve the values of any OUT parameters.            
                         }
                         catch (Exception sqle)  //Be cautious that execute() throws an SQL Exception
                         {
                             System.out.println("Exception occured"+sqle);
                             pageContext.writeDiagnostics(this, "INside Exception Wile Invoking Workflow "+sqle , 4);
                             System.out.println("INside Exception "+sqle);
                         }
                         pageContext.writeDiagnostics(this, "Workflow is @ Package XXHW_FIN_PETTYCASH_WF_PKG Initiated"+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+outParamValue1+outParamValue2, 4);
                         ptHdrVO.getCurrentRow().setAttribute("BudgetApproval", "In Progress");
                         am.invokeMethod("apply");  
                         
                         OAException confirmMessage = new OAException("Budget Notification has been triggered for Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" ", OAException.CONFIRMATION);     
                         System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
                         pageContext.writeDiagnostics(this, " Inside PtCshCreateCO :PFR  Inside SUBMIT  Getting Value of "+confirmMessage, 4);
                         pageContext.putDialogMessage(confirmMessage);
                         
                     }
                   
                      
                     
                 }
      
      
      String pageEvent;
        if(pageContext.getParameter(EVENT_PARAM)!=null)
        {
            pageEvent = pageContext.getParameter(EVENT_PARAM);
//            if(pageContext.getParameter(EVENT_PARAM).equals("ADDCLAIMLINES"))
//            {
//              Serializable[] sn1={"PtCshDtlVO1"};
//              am.invokeMethod("createRecord", sn1); 
//              ptDtlVO.getCurrentRow().setAttribute("ExchangeRateDate", am.getOADBTransaction().getCurrentUserDate());
//            }
//            if(pageContext.getParameter(EVENT_PARAM).equals("ADDRECEIPTLINES"))
//            {
//              Serializable[] sn1={"PtCshRecptVO1"};
//              am.invokeMethod("createRecord", sn1);               
//            }
            if(pageContext.getParameter(EVENT_PARAM).equals("SAVE"))
            {
                am.invokeMethod("apply");                   
                HashMap hmSaveBtn=new HashMap();  //Tried to send parameter through SetFowardURL            // Changes done by 4iapps on 7th June 2020 for Page Refreshing while save
                hmSaveBtn.put("DISPMSG", "Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" saved Successfully"); 
                hmSaveBtn.put("PTYCSHHDRID", PPtyCshHdrId);
                hmSaveBtn.put("DIV", PDiv);
                hmSaveBtn.put("LOC", PLoc);
                if(PMode.equals("FARO"))
                {
                    hmSaveBtn.put("MODE", PMode);
                }
                else
                {
                    hmSaveBtn.put("MODE", "UPD");
                }  
                
                
                pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
                null,
                //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                 OAWebBeanConstants.KEEP_MENU_CONTEXT,
                null,
                hmSaveBtn, //HashMap                      
                false,
                OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                OAWebBeanConstants.IGNORE_MESSAGES); 
                
            
            }
            if(pageContext.getParameter(EVENT_PARAM).equals("SAVECLOSE"))
            {
                if(ptHdrVO.getCurrentRow().getAttribute("Status").equals("Approved")||ptHdrVO.getCurrentRow().getAttribute("Status").equals("Approval in Progress"))
                {
                    HashMap hmsc=new HashMap();  //Tried to send parameter through SetFowardURL
                     hmsc.clear();
                    //                hm.put("MODE", "ADD");
                    //    hm.put("PTYCSHHDRID", "");
                    hmsc.put("DIV", PDiv);
                    hmsc.put("LOC", PLoc);
//                    hmsc.put("DISPMSG", "Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" saved Successfully"); 
                    
                    pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshSearchPG",
                    null,
                    //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                    OAWebBeanConstants.KEEP_MENU_CONTEXT,
                    null,
                    hmsc, //HashMap
                    false,
                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                    OAWebBeanConstants.IGNORE_MESSAGES);
                
                }
                else
                {
                    am.invokeMethod("apply");     
                    HashMap hmsc=new HashMap();  //Tried to send parameter through SetFowardURL
                     hmsc.clear();
    //                hm.put("MODE", "ADD");
                //    hm.put("PTYCSHHDRID", "");
                    hmsc.put("DIV", PDiv);
                    hmsc.put("LOC", PLoc);
                    hmsc.put("DISPMSG", "Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" saved Successfully"); 
                    
                    pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshSearchPG",
                    null,
                    //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                    OAWebBeanConstants.KEEP_MENU_CONTEXT,
                    null,
                    hmsc, //HashMap
                    false,
                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                    OAWebBeanConstants.IGNORE_MESSAGES);
                }
                
            }
//            if(pageContext.getParameter(EVENT_PARAM).equals("SUBMIT"))
//            {
//                pageContext.writeDiagnostics(this, "Inside PtCshCreateCO :PFR  Inside SUBMIT -----------------------> MODE", 4);      
//                OAPageButtonBarBean pgButton = (OAPageButtonBarBean)webBean.findChildRecursive("PGBtnBrRN");
////                OAMessageChoiceBean messageChoice = (OAMessageChoiceBean)webBean.findChildRecursive("MainRN");
//                pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PFR  Inside SUBMIT -----------------------> pgButton"+pgButton, 4); 
//                OAMessageChoiceBean mchoice=(OAMessageChoiceBean)pgButton.findIndexedChild("Action");
//                pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PFR  Inside SUBMIT -----------------------> MODE"+mchoice, 4);      
//            
//                pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PFR  Inside SUBMIT -----------------------> MODE"+mchoice.getValue(pageContext), 4);    
//                pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PFR  Inside SUBMIT -----------------------> ptHdrVO.getCurrentRow().getAttribute(\"Attribute10\") "+ptHdrVO.getCurrentRow().getAttribute("Attribute10"), 4);   
////                 String mChoiceVal2=mchoice.getValue(pageContext).toString();
////                 
////                String val1=pageContext.getParameter("Action");
//                pageContext.writeDiagnostics(this, "Inside " +"PtCshCreateCO :PFR  Inside SUBMIT -----------------------> MODE pageContext.getParameter(\"Action\")  "+pageContext.getParameter("Action"), 4);      
//
//                String username = pageContext.getUserName();
//                ptHdrVO.getCurrentRow().setAttribute("RequestedBy", username) ;
//                ptHdrVO.getCurrentRow().setAttribute("RequestedFor", username) ;
//                ptHdrVO.getCurrentRow().setAttribute("WfStatus", "InProgress") ;              
//                ptHdrVO.getCurrentRow().setAttribute("Status", "InProgress") ;   
//                am.invokeMethod("apply"); 
//                
//                OAException confirmMessage = new OAException("Claim Number "+ptHdrVO.getCurrentRow().getAttribute("RequestNumber")+" has been submitted Successfully", OAException.CONFIRMATION);     
//                System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
//                pageContext.writeDiagnostics(this, " Inside PtCshCreateCO :PFR  Inside SUBMIT  Getting Value of "+PDispMsg, 4);
////                pageContext.putDialogMessage(confirmMessage);  
//                
//            }
            if(pageContext.getParameter(EVENT_PARAM).equals("EXCHRATEDATE"))
            {
                pageContext.writeDiagnostics(this, "Inside  EXCHRATEDATE ==============> "  , 4);
            }
            /**=======================================================================
                   *            DELETE RECORDS
                   * ====================================================================**/ 
                   if(pageContext.getParameter(EVENT_PARAM).equals("DELETECLAIMLINES"))
                     {
                         pageContext.writeDiagnostics(this, "Inside DELETEDETAIL Logic ----------------------->",4);
                           String rowReference = pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE);    //Obtaining the Row Reference of the current Row
                           OARow row = (OARow)am.findRowByRef(rowReference);
                         pageContext.writeDiagnostics(this, " row.getAttribute(\"CntrctSmeId\")"+ row.getAttribute("PtycshDtlId"), 4);
                               String docNo = " ";
                               String PtycshDtlId = (row.getAttribute("PtycshDtlId")).toString();                       
                              if ((PtycshDtlId!=null))
                              {
                                  // This performs the actual delete.
                                  pageContext.writeDiagnostics(this, "Getting the Values CntrctSmeIdPrim for DTL Deletion CntrctSmeIdPrim: "+" PtycshDtlId  "+PtycshDtlId, 4);
                                  row.remove();
                                  am.invokeMethod("apply");  
                              }
                     }
                     
            /**=======================================================================
                   *            DELETE RECORDS
                   * ====================================================================**/ 
                   if(pageContext.getParameter(EVENT_PARAM).equals("DELETERECLINES"))
                     {
                         pageContext.writeDiagnostics(this, "Inside DELETEDETAIL Logic ----------------------->",4);
                           String rowReference = pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE);    //Obtaining the Row Reference of the current Row
                           OARow row = (OARow)am.findRowByRef(rowReference);
                         pageContext.writeDiagnostics(this, " row.getAttribute(\"CntrctSmeId\")"+ row.getAttribute("PtycshRecptId"), 4);
                               String docNo = " ";
                               String PtycshRecptId = (row.getAttribute("PtycshRecptId")).toString();                       
                              if ((PtycshRecptId!=null))
                              {
                                  // This performs the actual delete.
                                  pageContext.writeDiagnostics(this, "Getting the Values CntrctSmeIdPrim for DTL Deletion CntrctSmeIdPrim: "+" PtycshRecptId  "+PtycshRecptId, 4);
                                  row.remove();
                                  am.invokeMethod("apply");  
                              }
                     }
                     
                     /**=======================================================================
                      *            ADD NEW SME RECORDS
                      * ====================================================================**/ 
                      else if  ("ADDCLAIMLINES".equals(pageContext.getParameter("event")))
                      {
                          am.invokeMethod("apply");  
                          pageContext.writeDiagnostics(this, "Inside addRows Logic The source is :\"+pageContext.getParameter(\"source\")"+pageContext.getParameter("source"),4);
                          System.out.println("The source is :"+pageContext.getParameter("source"));
                      //            Serializable[] sn1={"XXSCMSMEDetailsVO1"};
                      //            am.invokeMethod("createRecord", sn1);
                      //            smeDetVO.getCurrentRow().setAttribute("CntrctId", cntrctMasVO.getCurrentRow().getAttribute("CntrctId"));
                      //            pageContext.writeDiagnostics(this, "smeDetVO.getCurrentRow().setAttribute(\"CntrctId\""+smeDetVO.getCurrentRow().getAttribute("CntrctId"), 4);
                      //            String username = pageContext.getUserName();
                      //            smeDetVO.getCurrentRow().setAttribute("RequestedBy", username) ;
                      //            smeDetVO.getCurrentRow().setAttribute("RequestedFor", username) ;

                            HashMap hm=new HashMap();  //Tried to send parameter through SetFowardURL
                            hm.put("MODE", "ADD");
                            hm.put("PTYCSHHDRID", ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId"));
                            hm.put("DIV", PDiv);
                            hm.put("LOC", PLoc);
                    
                          
                           pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCLCreatePG",
                           null,
                           //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                           OAWebBeanConstants.KEEP_MENU_CONTEXT,
                           null,
                           hm, //HashMap                      
                           false,
                           OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                           OAWebBeanConstants.IGNORE_MESSAGES); 

                      }
            /**=======================================================================
             *            ADD NEW SME RECORDS
             * ====================================================================**/ 
             else if  ("ADDAPPRDLINES".equals(pageContext.getParameter("event")))
             {
                 am.invokeMethod("apply");  
                 pageContext.writeDiagnostics(this, "Inside addRows Logic The source is :\"+pageContext.getParameter(\"source\")"+pageContext.getParameter("source"),4);
                 System.out.println("The source is :"+pageContext.getParameter("source"));
             //            Serializable[] sn1={"XXSCMSMEDetailsVO1"};
             //            am.invokeMethod("createRecord", sn1);
             //            smeDetVO.getCurrentRow().setAttribute("CntrctId", cntrctMasVO.getCurrentRow().getAttribute("CntrctId"));
             //            pageContext.writeDiagnostics(this, "smeDetVO.getCurrentRow().setAttribute(\"CntrctId\""+smeDetVO.getCurrentRow().getAttribute("CntrctId"), 4);
             //            String username = pageContext.getUserName();
             //            smeDetVO.getCurrentRow().setAttribute("RequestedBy", username) ;
             //            smeDetVO.getCurrentRow().setAttribute("RequestedFor", username) ;

                   HashMap hmSiteAdd=new HashMap();  //Tried to send parameter through SetFowardURL
                   hmSiteAdd.put("MODE", "ADD");
                   hmSiteAdd.put("PTYCSHHDRID", ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId"));
                   hmSiteAdd.put("DIV", PDiv);
                   hmSiteAdd.put("LOC", PLoc);
            
                 
//                  pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCLAddPG",
                 pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCLAddRPG",
                  null,
                  //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                  OAWebBeanConstants.KEEP_MENU_CONTEXT,
                  null,
                  hmSiteAdd, //HashMap                      
                  false,
                  OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                  OAWebBeanConstants.IGNORE_MESSAGES); 

             }
            /**=======================================================================
             *            UPDATE CLAIM LINES RECORDS
             * ====================================================================**/ 
             else if  ("UPDATECLAIMLINES".equals(pageContext.getParameter("event")))
             {
                 am.invokeMethod("apply");  
                 pageContext.writeDiagnostics(this, "Inside addRows Logic The source is :\"+pageContext.getParameter(\"source\")"+pageContext.getParameter("source"),4);
                 System.out.println("The source is :"+pageContext.getParameter("source"));
                  String lPtycshDtlId = null;
                           pageContext.writeDiagnostics(this, "Inside UPDATESME Logic ----------------------->"+pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE),4);
                           System.out.println("Inside UPDATESME Logic ----------------------->"+pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE));
                           String rowReference = pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE);
                           if(rowReference!=null)
                           {
                              pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference ----------------------->",4);
                              OARow row = (OARow)am.findRowByRef(rowReference);
                              pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference ----------------------->"+row,4);
                              if(row!=null)
                              {
                                 lPtycshDtlId = (row.getAttribute("PtycshDtlId")).toString();
                                  pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference and getting PtycshDtlId----------------------->"+lPtycshDtlId,4);
                                  System.out.println("Inside UPDATEHDR Logic and Obtaining Row Reference and getting CntrctSmeID----------------------->"+lPtycshDtlId);
                              }
                           }

                   HashMap hmClLines=new HashMap();  //Tried to send parameter through SetFowardURL
                    if(PMode.equals("FARO"))
                    {
                        hmClLines.put("MODE", PMode);
                    }
                    else
                    {
                        hmClLines.put("MODE", "UPD");
                    }                 
                   hmClLines.put("PTYCSHDTLID", lPtycshDtlId);
                   hmClLines.put("DIV", PDiv);
                   hmClLines.put("LOC", PLoc);
            
                 
                  pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCLCreatePG",
                  null,
                  //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                  OAWebBeanConstants.KEEP_MENU_CONTEXT,
                  null,
                  hmClLines, //HashMap                      
                  false,
                  OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                  OAWebBeanConstants.IGNORE_MESSAGES); 

             }
             
            /**=======================================================================
             *            UPDATE RECEIPT LINES RECORDS
             * ====================================================================**/ 
             else if  ("UPDATERECLINES".equals(pageContext.getParameter("event")))
             {
                 am.invokeMethod("apply");  
                 pageContext.writeDiagnostics(this, "Inside addRows Logic The source is :\"+pageContext.getParameter(\"source\")"+pageContext.getParameter("source"),4);
                 System.out.println("The source is :"+pageContext.getParameter("source"));
                  String lPtycshRecptId = null;
                           pageContext.writeDiagnostics(this, "Inside UPDATESME Logic ----------------------->"+pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE),4);
                           System.out.println("Inside UPDATESME Logic ----------------------->"+pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE));
                           String rowReference = pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE);
                           if(rowReference!=null)
                           {
                              pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference ----------------------->",4);
                              OARow row = (OARow)am.findRowByRef(rowReference);
                              pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference ----------------------->"+row,4);
                              if(row!=null)
                              {
                                 lPtycshRecptId = (row.getAttribute("PtycshRecptId")).toString();
                                  pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference and getting PtycshDtlId----------------------->"+lPtycshRecptId,4);
                                  System.out.println("Inside UPDATEHDR Logic and Obtaining Row Reference and getting CntrctSmeID----------------------->"+lPtycshRecptId);
                              }
                           }

                   HashMap hmCRlLines=new HashMap();  //Tried to send parameter through SetFowardURL                
                 if(PMode.equals("FARO"))
                 {
                     hmCRlLines.put("MODE", PMode);
                 }
                 else
                 {
                     hmCRlLines.put("MODE", "UPD");
                 }
                   hmCRlLines.put("PTYCSHRECPTID", lPtycshRecptId);
                   hmCRlLines.put("DIV", PDiv);
                   hmCRlLines.put("LOC", PLoc);
            
                 
                  pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshRLCreatePG",
                  null,
                  //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                  OAWebBeanConstants.KEEP_MENU_CONTEXT,
                  null,
                  hmCRlLines, //HashMap                      
                  false,
                  OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                  OAWebBeanConstants.IGNORE_MESSAGES); 

             }


            /**=======================================================================
             *            ADD NEW SME RECORDS
             * ====================================================================**/ 
             else if  ("ADDRECEIPTLINES".equals(pageContext.getParameter("event")))
             {
                 am.invokeMethod("apply");  
                 pageContext.writeDiagnostics(this, "Inside addRows Logic The source is :\"+pageContext.getParameter(\"source\")"+pageContext.getParameter("source"),4);
                 System.out.println("The source is :"+pageContext.getParameter("source"));
                   HashMap hm=new HashMap();  //Tried to send parameter through SetFowardURL
                   hm.put("MODE", "ADD");
                   hm.put("PTYCSHHDRID", ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId"));
                   hm.put("DIV", PDiv);
                   hm.put("LOC", PLoc);
            
                 
                  pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshRLCreatePG",
                  null,
                  //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                  OAWebBeanConstants.KEEP_MENU_CONTEXT,
                  null,
                  hm, //HashMap                      
                  false,
                  OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                  OAWebBeanConstants.IGNORE_MESSAGES); 

             }
        }
}

    private void initializePgLoad(OAPageContext pageContext, String PDiv, String PLoc, 
                                  OAApplicationModule am, OAViewObject ptHdrVO,
                                  OAViewObject expTypeVO, OAViewObject supVO) {
        pageContext.writeDiagnostics(this, "Inside initializePgLoad "+PLoc, 4);
        String lcDivLoc =null;
        if(PLoc==null||PLoc=="")
        {
            pageContext.writeDiagnostics(this, "Inside initializePgLoad PLoc is null"+PLoc, 4);
            lcDivLoc = PDiv;
        }
        else
        {
            pageContext.writeDiagnostics(this, "Inside initializePgLoad PLoc is not null"+PLoc, 4);
            lcDivLoc = PLoc;
        }
             
        pageContext.writeDiagnostics(this, " +++++++++++++++++Going to set Sequence Number++++++++++++++++++  ", 4);
        OAViewObject ptSeqVO= null;
        ptSeqVO=(OAViewObject)am.findViewObject("PtCshSeqVO1");
        OAViewObject iouAmtVO= null;
        iouAmtVO=(OAViewObject)am.findViewObject("IOUAmountVO1");
        ptSeqVO.setWhereClause(" sysdate between date_start and date_end and seq_prefix = '"+lcDivLoc+"-PC' ");
        pageContext.writeDiagnostics(this, "Executing the Seq Query "+ptSeqVO.getQuery(), 4);
        ptSeqVO.executeQuery();
        ptSeqVO.first();
        if(ptSeqVO.getRowCount()>0)
        {
            pageContext.writeDiagnostics(this, "Setting Sequence Number "+ptSeqVO.getCurrentRow().getAttribute("CurRunSequence"), 4);
            ptHdrVO.getCurrentRow().setAttribute("RequestNumber", ptSeqVO.getCurrentRow().getAttribute("CurRunSequence"));
        }
        pageContext.writeDiagnostics(this, " +++++++++++++++++Sequence Number Execution Completed++++++++++++++++++  ", 4);
             
             
             if(PLoc==null||PLoc=="")
             {
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS with PLoc==null ", 4);
                 ptHdrVO.getCurrentRow().setAttribute("DispModeLoc", Boolean.TRUE);
             }
             else
             {
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside  else Condition LOC Initialize ===========TESTING", 4);
                 ptHdrVO.getCurrentRow().setAttribute("DispModeLoc", Boolean.FALSE);
                 ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);
             }
             if(PDiv.equals("OPS"))
             {
                 if(PLoc==null||PLoc=="") 
                 {
                     pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS", 4);
    //                 expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
    //                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS expTypeVO.getQuery() "+expTypeVO.getQuery(), 4);
                     supVO.setWhereClause(" lookup_code = 'OPERATIONS' ");
                     pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute Supplier VO  "+supVO.getQuery(), 4);
                     supVO.executeQuery();
                     supVO.first();
                    
                     ptHdrVO.getCurrentRow().setAttribute("VendorName", supVO.getCurrentRow().getAttribute("Meaning"));
                     ptHdrVO.getCurrentRow().setAttribute("VendorSiteName", supVO.getCurrentRow().getAttribute("Tag"));
                     pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS Setting VendorName   "+ptHdrVO.getCurrentRow().getAttribute("VendorName")+" VendorSiteName "+ptHdrVO.getCurrentRow().getAttribute("VendorSiteName"), 4);
                 }
                 if(PLoc==null||PLoc=="")
                 {
                 ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.TRUE);
                 }

             }
             else if(PDiv.equals("FIN"))
             {
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS", 4);
                 //                 expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
                 //                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS expTypeVO.getQuery() "+expTypeVO.getQuery(), 4);
                 supVO.setWhereClause(" lookup_code = 'FINANCE' ");
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS going to Execute Supplier VO  "+supVO.getQuery(), 4);
                 supVO.executeQuery();
                 supVO.first();
                 
                 ptHdrVO.getCurrentRow().setAttribute("VendorName", supVO.getCurrentRow().getAttribute("Meaning"));
                 ptHdrVO.getCurrentRow().setAttribute("VendorSiteName", supVO.getCurrentRow().getAttribute("Tag"));
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS Setting VendorName   "+ptHdrVO.getCurrentRow().getAttribute("VendorName")+" VendorSiteName "+ptHdrVO.getCurrentRow().getAttribute("VendorSiteName"), 4);
                 
                 ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);
                 

             }
             else if(PDiv.equals("ADM"))
             {
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS", 4);
                 //                 expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
                 //                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS expTypeVO.getQuery() "+expTypeVO.getQuery(), 4);
                 supVO.setWhereClause(" lookup_code = 'HR & ADMIN' ");
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS going to Execute Supplier VO  "+supVO.getQuery(), 4);
                 supVO.executeQuery();
                 supVO.first();
                 
                 ptHdrVO.getCurrentRow().setAttribute("VendorName", supVO.getCurrentRow().getAttribute("Meaning"));
                 ptHdrVO.getCurrentRow().setAttribute("VendorSiteName", supVO.getCurrentRow().getAttribute("Tag"));
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS Setting VendorName   "+ptHdrVO.getCurrentRow().getAttribute("VendorName")+" VendorSiteName "+ptHdrVO.getCurrentRow().getAttribute("VendorSiteName"), 4);
                 
                 ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);
             }
             else if(PDiv.equals("LOG"))
             {
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS", 4);
                 //                 expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
                 //                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS expTypeVO.getQuery() "+expTypeVO.getQuery(), 4);
                 supVO.setWhereClause(" lookup_code = 'LOGISTICS' ");
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS going to Execute Supplier VO  "+supVO.getQuery(), 4);
                 supVO.executeQuery();
                 supVO.first();
                 
                 ptHdrVO.getCurrentRow().setAttribute("VendorName", supVO.getCurrentRow().getAttribute("Meaning"));
                 ptHdrVO.getCurrentRow().setAttribute("VendorSiteName", supVO.getCurrentRow().getAttribute("Tag"));
                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS Setting VendorName   "+ptHdrVO.getCurrentRow().getAttribute("VendorName")+" VendorSiteName "+ptHdrVO.getCurrentRow().getAttribute("VendorSiteName"), 4);
                 
                 ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);
                 
            }
             
    }
    
    public String validateAllocation(  OAApplicationModule am,OAPageContext pageContext)
           {
               OAViewObject ptHdrVO= null;
               String retVal = null;
                int counter=0;
               ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
               if((ptHdrVO.getCurrentRow().getAttribute("CashInHand")==null)||(ptHdrVO.getCurrentRow().getAttribute("CashInHand")==""))
               {
                   counter = counter+1;
                   retVal=" Cash In Hand should not be Empty : Cash in Hand should be Equal to Closing Balance";
               }
               else
               {
                   int cashInHand = Integer.parseInt(ptHdrVO.getCurrentRow().getAttribute("CashInHand").toString());
                   int closingBalDesc = Integer.parseInt(ptHdrVO.getCurrentRow().getAttribute("ClosingBalDesc").toString());
                   pageContext.writeDiagnostics(this, "Validation is Passed for cashInHand : "+cashInHand+" closingBalDesc : "+closingBalDesc, 4);
                   if(cashInHand <= 0){
                       counter = counter+1;
                       retVal="Closing Balance should be always positive. Please check claim details to Submit further for Approval ";                   
                   }
                   else
                   {
                       if(cashInHand==closingBalDesc)
                       {
                           pageContext.writeDiagnostics(this, "Validation is Passed for cashInHand  and closingBalDesc ", 4);
                       }
                       else
                       {
                           counter = counter+1;
                           retVal="Cash in Hand should be Equal to Closing Balance ";
                       }
                   }
               }
//               Row row[] = ptHdrVO.getAllRowsInRange();
//               pageContext.writeDiagnostics(this, "Proceed validateAllocation       --------"+row.length, 4);

//                for (int i=0;i<row.length;i++)
//                  {
//                  OARow rowi = (OARow)row[i];
//                    if (rowi.getAttribute("SelectFlag")!= null && rowi.getAttribute("SelectFlag").equals("Y"))  
//               //                          if (rowi.getSelectFlag()!= null && rowi.getSelToDel().equals("Y"))
//                    {
//                    
//                            pageContext.writeDiagnostics(this, "Proceed validateAllocation    Inside rowi.getAttribute(\"SelectFlag\")!= null && rowi.getAttribute(\"SelectFlag\").equals(\"Y\")   --------"+row.length, 4);
//                       
//                        }
//                        else
//                        {
//                            counter = counter+1;
//                            retVal = "Sales Order Number and Allocation Quantity has to be Entered";
//                        }
//                        
//                        
//                   
//                   
//                  }
                 pageContext.writeDiagnostics(this, "Value of Counter is  "+counter,4);
                  if(counter>0)
                  { 
                   return retVal;
                  }
                  else
                  return "Y";
           }

    private void setIOUNumberAmt(OAApplicationModule am, 
                                 OAPageContext pageContext, OAViewObject ptHdrVO) {
        OAViewObject iouAmtVO= null;
        iouAmtVO=(OAViewObject)am.findViewObject("IOUAmountVO1");
        if(PDiv.equals("OPS"))
        {
            if(PLoc==null||PLoc=="")
            {
                pageContext.writeDiagnostics(this, "Inside initializePgLoad IOU Inside If OPS", 4);
                iouAmtVO.setNamedWhereClauseParam("p_div", ptHdrVO.getCurrentRow().getAttribute("DivisionName"));          //Admin //Sample
                //                 iouAmtVO.setNamedWhereClauseParam("p_div", "Operations");
                //                 iouAmtVO.setWhereClause(" trunc(IOU_GIVEN_DATE)  < to_date('"+am.getOADBTransaction().getCurrentUserDate()+"', 'DD-Mon-YYYY') and division = '"+PDiv+"'");
                pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO  "+iouAmtVO.getQuery(), 4);
                iouAmtVO.executeQuery();
                iouAmtVO.first();
                pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO and amount is "+iouAmtVO.getCurrentRow().getAttribute("IouAmount"), 4);
                ptHdrVO.getCurrentRow().setAttribute("StaffIouAmount", iouAmtVO.getCurrentRow().getAttribute("IouAmount"));
                ptHdrVO.getCurrentRow().setAttribute("StaffIouNumber", iouAmtVO.getCurrentRow().getAttribute("IouCount"));
            }
            else
            {
                pageContext.writeDiagnostics(this, "Inside As location Exists and IOU Will not be Considered", 4);
            }
            
         

        }
        else if(PDiv.equals("FIN"))
        {
            pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS", 4);
            iouAmtVO.setNamedWhereClauseParam("p_div", "Finance");
            //                 iouAmtVO.setWhereClause(" trunc(IOU_GIVEN_DATE)  < to_date('"+am.getOADBTransaction().getCurrentUserDate()+"', 'DD-Mon-YYYY') and division = '"+PDiv+"'");
            pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO  "+iouAmtVO.getQuery(), 4);
            iouAmtVO.executeQuery();
            iouAmtVO.first();
            pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO and amount is "+iouAmtVO.getCurrentRow().getAttribute("IouAmount"), 4);
            ptHdrVO.getCurrentRow().setAttribute("StaffIouAmount", iouAmtVO.getCurrentRow().getAttribute("IouAmount"));
            ptHdrVO.getCurrentRow().setAttribute("StaffIouNumber", iouAmtVO.getCurrentRow().getAttribute("IouCount"));
        }
        else if(PDiv.equals("ADM"))
        {
            pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS", 4);
            //                 expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
            //                 pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS expTypeVO.getQuery() "+expTypeVO.getQuery(), 4);
             iouAmtVO.setNamedWhereClauseParam("p_div", ptHdrVO.getCurrentRow().getAttribute("DivisionName"));
            //                 iouAmtVO.setWhereClause(" trunc(IOU_GIVEN_DATE)  < to_date('"+am.getOADBTransaction().getCurrentUserDate()+"', 'DD-Mon-YYYY') and division = '"+PDiv+"'");
            pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO  "+iouAmtVO.getQuery(), 4);
            iouAmtVO.executeQuery();
            iouAmtVO.first();
            pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO and amount is "+iouAmtVO.getCurrentRow().getAttribute("IouAmount"), 4);
            ptHdrVO.getCurrentRow().setAttribute("StaffIouAmount", iouAmtVO.getCurrentRow().getAttribute("IouAmount"));
            ptHdrVO.getCurrentRow().setAttribute("StaffIouNumber", iouAmtVO.getCurrentRow().getAttribute("IouCount"));
        }
        else if(PDiv.equals("LOG"))
        {
            pageContext.writeDiagnostics(this, "Inside initializePgLoad Inside If OPS", 4);
              iouAmtVO.setNamedWhereClauseParam("p_div", ptHdrVO.getCurrentRow().getAttribute("DivisionName"));
            //                 iouAmtVO.setWhereClause(" trunc(IOU_GIVEN_DATE)  < to_date('"+am.getOADBTransaction().getCurrentUserDate()+"', 'DD-Mon-YYYY') and division = '"+PDiv+"'");
            pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO  "+iouAmtVO.getQuery(), 4);
            iouAmtVO.executeQuery();
            iouAmtVO.first();
            pageContext.writeDiagnostics(this, "Inside initializePgDispModeLocLoad Inside If OPS going to Execute IOU VO and amount is "+iouAmtVO.getCurrentRow().getAttribute("IouAmount"), 4);
            ptHdrVO.getCurrentRow().setAttribute("StaffIouAmount", iouAmtVO.getCurrentRow().getAttribute("IouAmount"));
            ptHdrVO.getCurrentRow().setAttribute("StaffIouNumber", iouAmtVO.getCurrentRow().getAttribute("IouCount"));
        }

                                 
    }
}
