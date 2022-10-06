/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import com.sun.java.util.collections.ArrayList;
import com.sun.java.util.collections.HashMap;
import com.sun.java.util.collections.List;

import java.io.Serializable;

import java.sql.CallableStatement;

import java.util.Enumeration;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADecimalValidater;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.cabo.ui.validate.Formatter;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import xxfin.oracle.apps.ap.pettycash.server.PtCshDtlVOImpl;
import xxfin.oracle.apps.ap.pettycash.server.PtCshDtlVORowImpl;

/**
 * Controller for ...
 */
public class PtCshCLAddRCO extends OAControllerImpl
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
    
       
       
      pageContext.writeDiagnostics(this, "Inside PtCshSearchCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID"), 4);
      System.out.println("Inside PtCshSearchCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID"));
      PDiv = pageContext.getParameter("DIV");
      PLoc = pageContext.getParameter("LOC");
      PPtyCshHdrId = pageContext.getParameter("PTYCSHHDRID");
      PDispMsg = pageContext.getParameter("DISPMSG");
      PMode = pageContext.getParameter("MODE");
      if(PDiv == null)
      {
        PDiv = "OPS";
      }   
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptDtlVO= null;
      ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
  
       Formatter formatter =         new OADecimalValidater("#,##0.000;#,##0.000","#,##0.000;#,##0.000");
       OAMessageStyledTextBean sbean4 = (OAMessageStyledTextBean)webBean.findIndexedChildRecursive("ClaimLineAmount"); 
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
      OAViewObject ptDtlVO= null;
      ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
      
      
      OAViewObject ptHdrVO2= null;
      ptHdrVO2=(OAViewObject)am.findViewObject("PtCshHdrVO2");
      OAViewObject ptDtlVO2= null;
      ptDtlVO2=(OAViewObject)am.findViewObject("PtCshDtlVO2");

    
    
    
      /**=======================================================================
          *           SEARH WHEN BUTTON IS CLICKED
          * ====================================================================**/ 
          if (pageContext.getParameter(EVENT_PARAM).equals("SEARCH"))
          {
              pageContext.writeDiagnostics(this, "Inside Search Logic ----------------------->",4);
              System.out.println("Inside Search Logic ----------------------->");                   
              Enumeration x=null;
              x=pageContext.getParameterNames();
              if(x!=null)        
              {        
                  System.out.println("Elements in Enum"+x);
                  while(x.hasMoreElements())
                  {
                  String aParamName = (String)x.nextElement();
                  System.out.println("Obatining the Child Names     : "+aParamName);
                  }
                  System.out.println("VO Gets Queried");        
              } 
               /**=======================================================================
               *           LISTING OUT THE ELEMENTS IN THE PAGE
               * ====================================================================**/ 
               System.out.println("pageContext.getParameter(\"Governate1\")"+pageContext.getParameter("Governate1"));
               System.out.println("pageContext.getParameter(\"RequestNumber\")"+pageContext.getParameter("RequestNumber"));
               System.out.println("pageContext.getParameter(\"FromDate\")"+pageContext.getParameter("FromDate"));
               System.out.println("pageContext.getParameter(\"ToDate\")"+pageContext.getParameter("ToDate"));         
                
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"Governate1\")"+pageContext.getParameter("Governate1") , 4);
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"RequestNumber\")"+pageContext.getParameter("RequestNumber"), 4);
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"FromDate\")"+pageContext.getParameter("FromDate"), 4);
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"ToDate\")"+pageContext.getParameter("ToDate"), 4);         
               
               List whClauseParams = new ArrayList();
               if (pageContext.getParameter("Governate1") != null && pageContext.getParameter("Governate1") != "") { 
                   try
                     {
                        whClauseParams.add(" and QRSLT.GOVERNATE like '%"+pageContext.getParameter("Governate1")+ "%'");
                     }
                     catch(Exception e){
                     System.out.println(e);
                     }
               }
               if (pageContext.getParameter("RequestNumber") != null && pageContext.getParameter("RequestNumber")!="") { 
                   try
                     {
                        whClauseParams.add(" and QRSLT.REQUEST_NUMBER like '%"+pageContext.getParameter("RequestNumber")+ "%'");
                     }
                     catch(Exception e){
                     System.out.println(e);
                     }
               }
               if (pageContext.getParameter("FromDate")!= null && pageContext.getParameter("FromDate")!="") { 
                   try
                     {
                        whClauseParams.add(" and trunc(QRSLT.REQUEST_DATE) >= to_date('"+pageContext.getParameter("FromDate")+ "', 'DD-Mon-YYYY')");
                     }
                     catch(Exception e){
                     System.out.println(e);
                     }
               }
               if (pageContext.getParameter("ToDate") != null &&pageContext.getParameter("ToDate")!="") { 
                   try
                     {
                        whClauseParams.add(" and trunc(QRSLT.REQUEST_DATE) <= to_date('"+pageContext.getParameter("ToDate")+ "', 'DD-Mon-YYYY')");
                     }
                     catch(Exception e){
                     System.out.println(e);
                     }
               }
               
                System.out.println("To check VO is not null"+ptHdrVO);
                if(ptHdrVO!=null)
                {        
                     String sqlparam=" ";
                     if(whClauseParams.isEmpty())
                    {
                          System.out.println("No Params for Search");
                          ptHdrVO.reset();
                          ptHdrVO.setWhereClauseParams(null); // Always reset
                          ptHdrVO.setWhereClause("1=1 and LOCATION_NAME is not null and status = 'Approved' and nvl(DIV_PROCESSED, 'N') = 'N' ");    
                         pageContext.writeDiagnostics(this, "Inside Params Empty SAVE - Reset 2 and getting Query ----------------------->"+ptHdrVO.getQuery(),4);
                          ptHdrVO.executeQuery();    
                         ptHdrVO.first();
                         pageContext.writeDiagnostics(this, "Inside Params with Values ptHdrVO.getRowCount() getting Query ----------------------->"+ptHdrVO.getRowCount(),4);                      
                         
                          
                     }
                     else
                     {
                          for (int i = 0; i < whClauseParams.size(); i++) 
                          {
                             String k=whClauseParams.get(i)+"";
                             System.out.println(whClauseParams.get(i));
                             sqlparam=sqlparam.concat(k);
                             System.out.println("The param is "+sqlparam);                                    
                          }                    
                          if((sqlparam!=null)&&(sqlparam!=""))
                          {
                              sqlparam=sqlparam.substring(5);
                              sqlparam.concat(" 1=1 and LOCATION_NAME is not null and status = 'Approved' and nvl(DIV_PROCESSED, 'N') = 'N' ");    
                              System.out.println("Modified param"+sqlparam);
                              ptHdrVO.reset();
                              ptHdrVO.setWhereClause(sqlparam);                            
                              pageContext.writeDiagnostics(this, "Inside Params with Values "+sqlparam+"SAVE getting Query ----------------------->"+ptHdrVO.getQuery(),4);                      
                              ptHdrVO.executeQuery();
                              ptHdrVO.first();
                              pageContext.writeDiagnostics(this, "Inside Params with Values ptHdrVO.getRowCount() getting Query ----------------------->"+ptHdrVO.getRowCount(),4);                      
                             
                          }                    
                      }
                 }
           }    
      if(pageContext.getParameter(EVENT_PARAM).equals("CANCEL"))
                    /**=======================================================================
                           *            NAVIGATE TO BACK PAGE OR CANCEL TRANSACTION
                           * ====================================================================**/ 
                             if(pageContext.getParameter(EVENT_PARAM).equals("CANCEL"))
                             {
                                 OADBTransaction txn = am.getOADBTransaction();
                                 txn.rollback();
                                 pageContext.writeDiagnostics(this, "Inside CANCEL Logic ----------------------->",4);
                                HashMap hmClAddCncl=new HashMap();  //Tried to send parameter through SetFowardURL
                                 hmClAddCncl.put("PTYCSHHDRID", ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId"));
                                 hmClAddCncl.put("DIV", PDiv);
                                 hmClAddCncl.put("LOC", PLoc);
                                 hmClAddCncl.put("MODE", "UPD");
                          
                                 pageContext.setForwardURL("/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
                                 null,
                                 //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                                 OAWebBeanConstants.KEEP_MENU_CONTEXT,
                                 null,
                                 hmClAddCncl, //HashMap                      
                                 false,
                                 OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                                 OAWebBeanConstants.IGNORE_MESSAGES); 
                             }
      /**=======================================================================
              *            SAVE RECORDS WHICH WILL COMMIT THE RECORDS
              * ====================================================================**/ 
                if(pageContext.getParameter(EVENT_PARAM).equals("SAVE"))
                {
                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ----------------------->",4);
                    
      //                    String validateAllo = validateAllocation(am, pageContext);
      //                    if( validateAllo.equals("Y"))
      //                      {
      //                          pageContext.writeDiagnostics(this, "Inside validateAllo is Y----------------------->", 4);
      //                      }
      //                    else
      //                           {
      //                               OAException confirmMessage = new OAException(validateAllo , OAException.ERROR);
      //                               pageContext.writeDiagnostics(this, "Inside Validation Logic Before throwing Exception ----------------------->", 4);
      //                               pageContext.putDialogMessage(confirmMessage);
      //                           }
      //                    validateFields(pageContext, am, smeDetVO, cntrctAttchVO);
      //                    smeDetVO.getCurrentRow().setAttribute("WfStatus", "Saved");
                          Row row[] = ptHdrVO.getAllRowsInRange();
                           pageContext.writeDiagnostics(this, "Proceed ADDTOSHIPMENT       --------"+row.length, 4);
                             
                            for (int i=0;i<row.length;i++)
                              {
                              OARow rowi = (OARow)row[i];
                                if (rowi.getAttribute("SelectFlag")!= null && rowi.getAttribute("SelectFlag").equals("Y"))  
       //                          if (rowi.getSelectFlag()!= null && rowi.getSelToDel().equals("Y"))
                                {
                                    pageContext.writeDiagnostics(this , "rowi.getAttribute(\"PtycshHdrId\")"+rowi.getAttribute("PtycshHdrId"), 4);
                                  pageContext.writeDiagnostics(this, "Getting Rows       ------PtycshHdrId--"+rowi.getAttribute("PtycshHdrId")+"---RequestNumber--"+rowi.getAttribute("RequestNumber")+"---RequestDate--"+rowi.getAttribute("RequestDate"), 4);
//                                      Serializable[] sn1={"PtCshDtlVO2"};
//                                      am.invokeMethod("createRecord", sn1); 
                                    ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+rowi.getAttribute("PtycshHdrId"));      
                                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
                                    ptDtlVO.executeQuery();
                                    int n = ptDtlVO.getFetchedRowCount();
                                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getRowCount()() "+ptDtlVO.getRowCount(), 4);   
                                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getFetchedRowCount() "+ptDtlVO.getFetchedRowCount(), 4);               
                                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getAllRowsInRange().length "+ptDtlVO.getAllRowsInRange().length, 4); 
                                    
                                    int fetchRowCount = ptDtlVO.getFetchedRowCount();
                                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.fetchRowCount "+fetchRowCount, 4);      
                                    OARow rowdtl = null;
                                    if (fetchRowCount  > 0) {
                                        pageContext.writeDiagnostics(this, "Getting ptDtlVO.fetchRowCount >0  ", 4); 
                                        RowSetIterator iter = ptDtlVO.createRowSetIterator("ptDtlRowSetIter");
                                            iter.setRangeStart(0);
                                            iter.setRangeSize(fetchRowCount);
                                            for (int j = 0; j < fetchRowCount; j++) {
                                            pageContext.writeDiagnostics(this, "Inside For Loop   j ========="+j, 4); 
                                                rowdtl = (OARow)iter.getRowAtRangeIndex(j);
                                            Serializable[] sn1={"PtCshDtlVO2"};
                                            am.invokeMethod("createRecord", sn1);
                                            pageContext.writeDiagnostics(this, "Inside For Loop    ptDtlVO.getCurrentRow().getAttribute(\"\")"+rowdtl.getAttribute("PtycshDtlId"), 4);      
                                            pageContext.writeDiagnostics(this, "Before Seting and Getting Values   PPtyCshHdrId : "+PPtyCshHdrId, 4);
                                          //  pageContext.writeDiagnostics(this, "Getting Rows       ------rowdtl.getAttribute.getAttribute(\"PtycshDtlId\")--"+ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId")+"Getting Rows       ------rowdtl.getAttribute(\"ConcatenatedSegments\")--"+ptDtlVO.getCurrentRow().getAttribute("ConcatenatedSegments")+"ptDtlVO2.getCurrentRow().getAttribute(\"PtycshDtlId\")"+ptDtlVO2.getCurrentRow().getAttribute("PtycshDtlId"), 4);    
                                          
                                            ptDtlVO2.getCurrentRow().setAttribute("PtycshHdrId", PPtyCshHdrId);
                                            pageContext.writeDiagnostics(this, "Setting PtycshHdrId", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("ExpenseType", rowdtl.getAttribute("ExpenseType"));
                                            pageContext.writeDiagnostics(this, "Setting ExpenseType", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("DtlNumAct", rowdtl.getAttribute("DtlNumAct"));
                                            pageContext.writeDiagnostics(this, "Setting DtlNumAct", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("Department",rowdtl.getAttribute("Department"));
                                            pageContext.writeDiagnostics(this, "Setting Department", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("AccountNum", rowdtl.getAttribute("AccountNum"));
                                            pageContext.writeDiagnostics(this, "Setting AccountNum", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("AccountDescription",rowdtl.getAttribute("AccountDescription"));                   
                                            pageContext.writeDiagnostics(this, "Setting AccountDescription", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("Currency", rowdtl.getAttribute("Currency"));                                 
                                            pageContext.writeDiagnostics(this, "Setting Currency", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("Amount", rowdtl.getAttribute("Amount"));                                     
                                            pageContext.writeDiagnostics(this, "Setting Amount", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("ExchangeRateType", rowdtl.getAttribute("ExchangeRateType"));                 
                                            pageContext.writeDiagnostics(this, "Setting ExchangeRateType", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("ExchangeRateDate", rowdtl.getAttribute("ExchangeRateDate"));                 
                                            pageContext.writeDiagnostics(this, "Setting ExchangeRateDate", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("ExchangeRate", rowdtl.getAttribute("ExchangeRate"));                         
                                            pageContext.writeDiagnostics(this, "Setting ExchangeRate", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("BriefNarration", rowdtl.getAttribute("BriefNarration"));                     
                                            pageContext.writeDiagnostics(this, "Setting BriefNarration", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("AttachFile", rowdtl.getAttribute("AttachFile"));                             
                                            pageContext.writeDiagnostics(this, "Setting AttachFile", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("FileDesc", rowdtl.getAttribute("FileDesc"));                                 
                                            pageContext.writeDiagnostics(this, "Setting FileDesc", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("CcId", rowdtl.getAttribute("CcId"));                                         
                                            pageContext.writeDiagnostics(this, "Setting CcId", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("ConcatenatedSegments", rowdtl.getAttribute("ConcatenatedSegments"));                                       
                                            pageContext.writeDiagnostics(this, "Setting ConcatenatedSegments", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("OldPtycshHdrId", rowdtl.getAttribute("PtycshHdrId"));                                       
                                            pageContext.writeDiagnostics(this, "Setting OldPtycshHdrId", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("OldPtycshDtlId", rowdtl.getAttribute("PtycshDtlId"));                                       
                                            pageContext.writeDiagnostics(this, "Setting OldPtycshDtlId", 4);
                                            ptDtlVO2.getCurrentRow().setAttribute("DivProcessed", "Y");  
                                            pageContext.writeDiagnostics(this, "Setting DivProcessed  Line", 4);
                                            pageContext.writeDiagnostics(this, "Before Invoking PLSQL----------==========TEST========---------->", 4);    
                                             CallableStatement callableStatement =
                                             am.getOADBTransaction().createCallableStatement("declare begin xxhw_fin_pettycash_util_pkg.create_attachment_copy (  "+rowdtl.getAttribute("PtycshDtlId")+", "+ptDtlVO2.getCurrentRow().getAttribute("PtycshDtlId")+" ); end;",OADBTransaction.DEFAULT); 
                                             //Refer to the Syntax where the String is a procedure 
                                             //Create a JDBC CallableStatement with the PL/SQL block containing the stored procedure invocation
                                             //Bind any variables.
                                             String valuetobePassed2=null;
                                             String  outParamValue1=null;
                                             String  outParamValue2 =null;
                                             String  outParamValue3 =null;
                                             try
                                             {
//                                                 callableStatement.registerOutParameter(1, Types.VARCHAR); //First Bind Varible should give us out Parameter
//                                                 callableStatement.registerOutParameter(2, Types.VARCHAR); //First Bind Varible should give us out Parameter            
                                                 callableStatement.execute(); // Execute the statement.
                                                 System.out.println("After Executing the PLSQL for Workflow"+"declare begin xxhw_fin_pettycash_util_pkg.create_attachment_copy (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+","+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"); end;");
                                                 pageContext.writeDiagnostics(this, "After Executing the PLSQL for Workflow"+"declare begin xxhw_fin_pettycash_util_pkg.create_attachment_copy (  "+ptHdrVO.getCurrentRow().getAttribute("PtycshHdrId")+","+ptHdrVO.getCurrentRow().getAttribute("WfItemKey")+"); end;", 4);
                                                 outParamValue1 = (callableStatement.getString(1)).toString(); //Retrieve the values of any OUT parameters.
                                                 outParamValue2 = (callableStatement.getString(2)).toString(); //Retrieve the values of any OUT parameters.            
                                             }
                                             catch (Exception sqle)  //Be cautious that execute() throws an SQL Exception
                                             {
                                                 System.out.println("Exception occured"+sqle);
                                                 pageContext.writeDiagnostics(this, "INside Exception Wile Invoking Attachement Copy Procedure "+sqle , 4);
                                                 System.out.println("INside Exception "+sqle);
                                             }
                                            
                                            
                                            
                                         
                                        }
                                        iter.closeRowSetIterator();
                                    }
                                    rowi.setAttribute("DivProcessed", "Y");
                                    am.invokeMethod("apply");
                                     OAException confirmMessage = new OAException("The Transactions has been saved Successfully", OAException.CONFIRMATION);
                                     pageContext.writeDiagnostics(this, "Inside SAVE Logic Before throwing Exception ----------------------->", 4);
                                     pageContext.putDialogMessage(confirmMessage);
                                    
                                    
                                    
                                    
//                                   // lOOPING USING WHILE MTHOD
//                                        ptDtlVO.first();
//                                       for (int j=0; j<n;j++)
//                                               { 
//                                     
//                                                 Serializable[] sn1={"PtCshDtlVO2"};
//                                                 am.invokeMethod("createRecord", sn1); 
//                                                 
//                                              pageContext.writeDiagnostics(this, "Getting Rows       ------rowdtl.getAttribute.getAttribute(\"PtycshDtlId\")--"+ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId")+"Getting Rows       ------rowdtl.getAttribute(\"ConcatenatedSegments\")--"+ptDtlVO.getCurrentRow().getAttribute("ConcatenatedSegments")+"ptDtlVO2.getCurrentRow().getAttribute(\"PtycshDtlId\")"+ptDtlVO2.getCurrentRow().getAttribute("PtycshDtlId"), 4);    
//                                              
//                                              ptDtlVO2.getCurrentRow().setAttribute("PtycshHdrId", PPtyCshHdrId);
//                                              pageContext.writeDiagnostics(this, "Setting PtycshHdrId", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("ExpenseType", ptDtlVO.getCurrentRow().getAttribute("ExpenseType"));
//                                              pageContext.writeDiagnostics(this, "Setting ExpenseType", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("Department",ptDtlVO.getCurrentRow().getAttribute("Department"));
//                                              pageContext.writeDiagnostics(this, "Setting Department", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("AccountNum", ptDtlVO.getCurrentRow().getAttribute("AccountNum"));
//                                              pageContext.writeDiagnostics(this, "Setting AccountNum", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("AccountDescription",ptDtlVO.getCurrentRow().getAttribute("AccountDescription"));                   
//                                              pageContext.writeDiagnostics(this, "Setting AccountDescription", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("Currency", ptDtlVO.getCurrentRow().getAttribute("Currency"));                                 
//                                              pageContext.writeDiagnostics(this, "Setting Currency", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("Amount", ptDtlVO.getCurrentRow().getAttribute("Amount"));                                     
//                                              pageContext.writeDiagnostics(this, "Setting Amount", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("ExchangeRateType", ptDtlVO.getCurrentRow().getAttribute("ExchangeRateType"));                 
//                                              pageContext.writeDiagnostics(this, "Setting ExchangeRateType", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("ExchangeRateDate", ptDtlVO.getCurrentRow().getAttribute("ExchangeRateDate"));                 
//                                              pageContext.writeDiagnostics(this, "Setting ExchangeRateDate", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("ExchangeRate", ptDtlVO.getCurrentRow().getAttribute("ExchangeRate"));                         
//                                              pageContext.writeDiagnostics(this, "Setting ExchangeRate", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("BriefNarration", ptDtlVO.getCurrentRow().getAttribute("BriefNarration"));                     
//                                              pageContext.writeDiagnostics(this, "Setting BriefNarration", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("AttachFile", ptDtlVO.getCurrentRow().getAttribute("AttachFile"));                             
//                                              pageContext.writeDiagnostics(this, "Setting AttachFile", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("FileDesc", ptDtlVO.getCurrentRow().getAttribute("FileDesc"));                                 
//                                              pageContext.writeDiagnostics(this, "Setting FileDesc", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("CcId", ptDtlVO.getCurrentRow().getAttribute("CcId"));                                         
//                                              pageContext.writeDiagnostics(this, "Setting CcId", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("ConcatenatedSegments", ptDtlVO.getCurrentRow().getAttribute("ConcatenatedSegments"));                                       
//                                              pageContext.writeDiagnostics(this, "Setting ConcatenatedSegments", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("OldPtycshHdrId", ptDtlVO.getCurrentRow().getAttribute("PtycshHdrId"));                                       
//                                              pageContext.writeDiagnostics(this, "Setting OldPtycshHdrId", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("OldPtycshDtlId", ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId"));                                       
//                                              pageContext.writeDiagnostics(this, "Setting OldPtycshDtlId", 4);
//                                              ptDtlVO2.getCurrentRow().setAttribute("DivProcessed", "Y");  
//                                              pageContext.writeDiagnostics(this, "Setting DivProcessed  Line", 4);
//                                                  am.invokeMethod("apply");
//                                                  ptDtlVO.next();
//                                              //          dvo.getCurrentRow().setAttribute("GrvHdrId", hdrId);
//                                              //          dvo.getCurrentRow().setAttribute("GrvDesc", lvo.getCurrentRow().getAttribute("Meaning"));
//                                              //          System.out.println("Attributes ??????> "+lvo.getCurrentRow().getAttribute(0)+lvo.getCurrentRow().getAttribute(1)+lvo.getCurrentRow().getAttribute(2));
//                                             
//                                              }
                                      
//                                    Row rowdtlcnt[] = ptDtlVO.getAllRowsInRange();
                                    
//                                  pageContext.writeDiagnostics(this, "Proceed to Add PtyCsh Detail       --------"+rowdtlcnt.length, 4);
//                                    for (int j=0;j<rowdtlcnt.length;j++)
//                                      {
//                                      
//                                          Serializable[] sn1={"PtCshDtlVO2"};
//                                          am.invokeMethod("createRecord", sn1); 
//                                          
//                                      OARow rowdtl = (OARow)rowdtlcnt[j];
//                                          pageContext.writeDiagnostics(this, "Getting Rows       ------rowdtl.getAttribute.getAttribute(\"PtycshDtlId\")--"+rowdtl.getAttribute("PtycshDtlId")+"Getting Rows       ------rowdtl.getAttribute(\"ConcatenatedSegments\")--"+rowdtl.getAttribute("ConcatenatedSegments")+"ptDtlVO2.getCurrentRow().getAttribute(\"PtycshDtlId\")"+ptDtlVO2.getCurrentRow().getAttribute("PtycshDtlId"), 4);    
//                                    
//                                          ptDtlVO2.getCurrentRow().setAttribute("PtycshHdrId", PPtyCshHdrId);
//                                    pageContext.writeDiagnostics(this, "Setting PtycshHdrId", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("ExpenseType", rowdtl.getAttribute("ExpenseType"));
//                                    pageContext.writeDiagnostics(this, "Setting ExpenseType", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("Department",rowdtl.getAttribute("Department"));
//                                    pageContext.writeDiagnostics(this, "Setting Department", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("AccountNum", rowdtl.getAttribute("AccountNum"));
//                                    pageContext.writeDiagnostics(this, "Setting AccountNum", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("AccountDescription",rowdtl.getAttribute("AccountDescription"));                   
//                                    pageContext.writeDiagnostics(this, "Setting AccountDescription", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("Currency", rowdtl.getAttribute("Currency"));                                 
//                                    pageContext.writeDiagnostics(this, "Setting Currency", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("Amount", rowdtl.getAttribute("Amount"));                                     
//                                    pageContext.writeDiagnostics(this, "Setting Amount", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("ExchangeRateType", rowdtl.getAttribute("ExchangeRateType"));                 
//                                    pageContext.writeDiagnostics(this, "Setting ExchangeRateType", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("ExchangeRateDate", rowdtl.getAttribute("ExchangeRateDate"));                 
//                                    pageContext.writeDiagnostics(this, "Setting ExchangeRateDate", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("ExchangeRate", rowdtl.getAttribute("ExchangeRate"));                         
//                                    pageContext.writeDiagnostics(this, "Setting ExchangeRate", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("BriefNarration", rowdtl.getAttribute("BriefNarration"));                     
//                                    pageContext.writeDiagnostics(this, "Setting BriefNarration", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("AttachFile", rowdtl.getAttribute("AttachFile"));                             
//                                    pageContext.writeDiagnostics(this, "Setting AttachFile", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("FileDesc", rowdtl.getAttribute("FileDesc"));                                 
//                                    pageContext.writeDiagnostics(this, "Setting FileDesc", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("CcId", rowdtl.getAttribute("CcId"));                                         
//                                    pageContext.writeDiagnostics(this, "Setting CcId", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("ConcatenatedSegments", rowdtl.getAttribute("ConcatenatedSegments"));                                       
//                                    pageContext.writeDiagnostics(this, "Setting ConcatenatedSegments", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("OldPtycshHdrId", rowdtl.getAttribute("PtycshHdrId"));                                       
//                                    pageContext.writeDiagnostics(this, "Setting OldPtycshHdrId", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("OldPtycshDtlId", rowdtl.getAttribute("PtycshDtlId"));                                       
//                                    pageContext.writeDiagnostics(this, "Setting OldPtycshDtlId", 4);
//                                          ptDtlVO2.getCurrentRow().setAttribute("DivProcessed", "Y");  
//                                    pageContext.writeDiagnostics(this, "Setting DivProcessed  Line", 4);
//                                      }
                                    
//                                    rowi.setAttribute("DivProcessed", "Y");
//                                    pageContext.writeDiagnostics(this, "Setting DivProcessed", 4);
                                     //       soVO.getCurrentRow().setAttribute("PoHeaderId", rowi.getAttribute("PoHeaderId"));
             //                              soVO.getCurrentRow().setAttribute("PoLineId", rowi.getAttribute("PoLineId"));
                                
//                                             
                                    
                                  
                            
                                    
       //                              OADBTransaction txn=(OADBTransaction)am.getOADBTransaction();
                                    
       //                              Number seqNoSno1=txn.getSequenceValue("XXATC_SHIPMT_DTL_ID_S");
       //                              rowi.setAttribute("ShipmtDtlId", seqNoSno1);
       //                              rowi.setAttribute("ShipmtHdrId", pageContext.getParameter("SHIPMTHDRID"));
          //                            oracle.jbo.domain.Number vFileId = (oracle.jbo.domain.Number)rowi.getFileId();
       //                            rowi.remove();   // Here you can call updates if reqd
       //                            System.out.println("Selected To Delete File Id "+vFileId);
                                }
                              }
      
      
      
      
      
//                    am.invokeMethod("apply");  
//                    
                    HashMap hmClAddSave=new HashMap();  //Tried to send parameter through SetFowardURL
                    hmClAddSave.put("DISPMSG", "Records has been saved Successfully"); 
                    hmClAddSave.put("PTYCSHHDRID", PPtyCshHdrId);
                    hmClAddSave.put("DIV", PDiv);
                    hmClAddSave.put("LOC", PLoc);
                    hmClAddSave.put("MODE", "UPD");
               
               
                    pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
                    null,
                    //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                     OAWebBeanConstants.KEEP_MENU_CONTEXT,
                    null,
                    hmClAddSave, //HashMap                      
                    false,
                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                    OAWebBeanConstants.IGNORE_MESSAGES); 
                }
   
  }
    public String validateAllocation(  OAApplicationModule am,OAPageContext pageContext)
           {
               OAViewObject ptHdrVO= null;
               String retVal = null;
               ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
               Row row[] = ptHdrVO.getAllRowsInRange();
               pageContext.writeDiagnostics(this, "Proceed validateAllocation       --------"+row.length, 4);
                 int counter=0;
                for (int i=0;i<row.length;i++)
                  {
                  OARow rowi = (OARow)row[i];
                    if (rowi.getAttribute("SelectFlag")!= null && rowi.getAttribute("SelectFlag").equals("Y"))  
               //                          if (rowi.getSelectFlag()!= null && rowi.getSelToDel().equals("Y"))
                    {
                    
                            pageContext.writeDiagnostics(this, "Proceed validateAllocation    Inside rowi.getAttribute(\"SelectFlag\")!= null && rowi.getAttribute(\"SelectFlag\").equals(\"Y\")   --------"+row.length, 4);
                       
                        }
                        else
                        {
                            counter = counter+1;
                            retVal = "Sales Order Number and Allocation Quantity has to be Entered";
                        }
                        
                        
                   
                   
                  }
                 pageContext.writeDiagnostics(this, "Value of Counter is  "+counter,4);
                  if(counter>0)
                  { 
                   return retVal;
                  }
                  else
                  return "Y";
                  
                
         
           }


}
