/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import com.sun.java.util.collections.ArrayList;
import com.sun.java.util.collections.HashMap;
import com.sun.java.util.collections.List;

import java.io.Serializable;

import java.util.Enumeration;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.webui.OADecimalValidater;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.cabo.ui.validate.Formatter;

/**
 * Controller for ...
 */
public class PtCshSearchCO extends OAControllerImpl
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
//   String PPtyCshHdrId; 
    String PDispMsg;
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      pageContext.writeDiagnostics(this, "Inside PtCshSearchCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID"), 4);
      System.out.println("Inside PtCshSearchCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID"));
      PDiv = pageContext.getParameter("DIV");
      PLoc = pageContext.getParameter("LOC");
//      PPtyCshHdrId = pageContext.getParameter("PTYCSHHDRID");
      PDispMsg = pageContext.getParameter("DISPMSG");
      PMode = pageContext.getParameter("MODE");
      if(PMode == null)
      {
          PMode = "    ";
      }        
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptDtlVO= null;
      ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
      OAViewObject ptRecptVO= null;
      ptRecptVO=(OAViewObject)am.findViewObject("PtCshRecptVO1");
      OAViewObject ptTempVO= null;
      ptTempVO=(OAViewObject)am.findViewObject("PtCshTempVO1");
      if(PMode.equals("FARO"))
      {
          pageContext.writeDiagnostics(this, "Inside ReadOnly Mode  ", 4);
          Serializable[] sn1={"PtCshTempVO1"};
          am.invokeMethod("createRecord", sn1);      
          ptTempVO.getCurrentRow().setAttribute("DisSuppFields", Boolean.TRUE);
          ptTempVO.getCurrentRow().setAttribute("DisCreateButton", Boolean.FALSE);
      }
      else
      {
          pageContext.writeDiagnostics(this, "Inside ReadOnly Mode Else Condition ", 4);
          if(PDiv == null)
          {
            PDiv = "OPS";
          } 
          /**=======================================================================
          *            ADD FUNCTIONALITY FROM SME DETAIL PAGE
          * ====================================================================**/ 
          if(PDispMsg!=null)
          {
              OAException confirmMessage = new OAException(PDispMsg, OAException.CONFIRMATION);     
              System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
              pageContext.writeDiagnostics(this, "Getting Value of "+PDispMsg, 4);
              pageContext.putDialogMessage(confirmMessage);   
          }
          OAViewObject cLaimVO= null;
          cLaimVO=(OAViewObject)am.findViewObject("ClaimNumVO1");
          OAViewObject getVendorVO= null;
          getVendorVO=(OAViewObject)am.findViewObject("getVendorVO1");
          cLaimVO.setWhereClause(" DIVISION = '"+PDiv+"' and LOCATION_NAME  is null ");
          
          if(PLoc!=null)
          {
              cLaimVO.setWhereClause("  LOCATION_NAME = '"+PLoc+"'");
          }
          
          getVendorVO.setWhereClause(" DIVISION = '"+PDiv+"' and location_name is null");      
          if(PLoc!=null)
          {
              getVendorVO.setWhereClause("  LOCATION_NAME = '"+PLoc+"'");
          }
          
          Serializable[] sn1={"PtCshTempVO1"};
          am.invokeMethod("createRecord", sn1); 
          
          

            if(PLoc!=null)
            {
               ptHdrVO.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and LOCATION_NAME = '"+PLoc+"'");    
                ptTempVO.getCurrentRow().setAttribute("DisSuppFields", Boolean.FALSE);
                ptTempVO.getCurrentRow().setAttribute("DisCreateButton", Boolean.TRUE);
            }
            else
            {
               ptHdrVO.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and LOCATION_NAME is null ");  
                ptTempVO.getCurrentRow().setAttribute("DisSuppFields", Boolean.TRUE);
                ptTempVO.getCurrentRow().setAttribute("DisCreateButton", Boolean.TRUE);
            }
          ptHdrVO.setOrderByClause(" creation_date desc ");
          pageContext.writeDiagnostics(this, "Executing Header VO "+ptHdrVO.getQuery(), 4);
          ptHdrVO.executeQuery();
          ptHdrVO.first();
      
      }
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
      pageContext.writeDiagnostics(this, "Inside PtCshSearchCO We have the Source parameter ----------------------->"+SOURCE_PARAM,4);
          pageContext.writeDiagnostics(this, "Inside PtCshSearchCO We have the EVENT parameter ----------------------->"+EVENT_PARAM,4);
          System.out.println("We have the Source parameter ----------------------->"+SOURCE_PARAM);
          System.out.println("We have the EVENT parameter ----------------------->"+EVENT_PARAM);  
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptHdrVO2= null;
      ptHdrVO2=(OAViewObject)am.findViewObject("PtCshHdrVO2");
    
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
               System.out.println("pageContext.getParameter(\"VendorName\")"+pageContext.getParameter("VendorName"));   
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"Governate1\")"+pageContext.getParameter("Governate1") , 4);
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"RequestNumber\")"+pageContext.getParameter("RequestNumber"), 4);
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"FromDate\")"+pageContext.getParameter("FromDate"), 4);
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"ToDate\")"+pageContext.getParameter("ToDate"), 4);         
               pageContext.writeDiagnostics(this, "pageContext.getParameter(\"VendorName\")"+pageContext.getParameter("VendorName"), 4);   
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
               if (pageContext.getParameter("VendorName") != null &&pageContext.getParameter("VendorName")!="") { 
                   try
                     {
                        whClauseParams.add(" and QRSLT.VENDOR_NAME like '%"+pageContext.getParameter("VendorName")+ "%'");
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
                           
                             if(PMode.equals("FARO"))
                             {
                                 pageContext.writeDiagnostics(this, "Inside ReadOnly Access ----------------------->",4);
                             }
                             else
                             {
                                 if(PLoc!=null)
                                 {
                                      ptHdrVO.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and LOCATION_NAME = '"+PLoc+"'");    
                                 }
                                 else
                                 {
                                      ptHdrVO.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and LOCATION_NAME is null ");  
                                 }
                             }
                         ptHdrVO.setOrderByClause(" creation_date desc ");
                          ptHdrVO.executeQuery();    
                          pageContext.writeDiagnostics(this, "Inside Params Empty SAVE - Reset 2 and getting Query ----------------------->"+ptHdrVO.getQuery(),4);
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
                              pageContext.writeDiagnostics(this, "Inside sqlparam is not null  ----------------------->",4);
                              sqlparam=sqlparam.substring(5);
                                  if(PMode.equals("FARO"))
                                  {
                                      pageContext.writeDiagnostics(this, "Inside ReadOnly Access ----------------------->",4);
                                  }
                                  else
                                  {
                                      if(PLoc!=null)
                                      {
                                          pageContext.writeDiagnostics(this, "Inside PLoc!=null Condition  is not null  ----------------------->",4);
                                          ptHdrVO.setWhereClause(" and  DIVISION = '"+PDiv+"' and LOCATION_NAME = '"+PLoc+"'");  
                                          sqlparam=sqlparam.concat(" and  DIVISION = '"+PDiv+"' and LOCATION_NAME = '"+PLoc+"'");
                                          
                                      }
                                      else
                                      {
                                          pageContext.writeDiagnostics(this, "Inside Else PLoc!=null Condition  is not null  ----------------------->",4);
                                           ptHdrVO.setWhereClause(" and  DIVISION = '"+PDiv+"' and LOCATION_NAME is null ");  
                                          sqlparam=sqlparam.concat("  and  DIVISION = '"+PDiv+"' and LOCATION_NAME is null ");
                                      }
                                  }
                              //sqlparam.concat(" and vendor_id = nvl("+pageContext.getParameter("POVENDORID")+", vendor_id)");
                              System.out.println("Modified param"+sqlparam);
                              ptHdrVO.reset();
                              ptHdrVO.setWhereClause(sqlparam);                            
                              ptHdrVO.setOrderByClause(" creation_date desc ");
                              ptHdrVO.executeQuery();
                              pageContext.writeDiagnostics(this, "Inside Params with Values "+sqlparam+"SAVE getting Query ----------------------->"+ptHdrVO.getQuery(),4);                      
                          }                    
                      }
                 }
           }    
      /**=======================================================================
           *            NAVIGATE TO SAME PAGE TO CANCEL/CLEAR TRANSACTION
           * ====================================================================**/ 
          if (pageContext.getParameter(EVENT_PARAM).equals("CLEAR"))
          {              
                   OADBTransaction txn = am.getOADBTransaction();
                   txn.rollback();
               HashMap hmclear=new HashMap();
               hmclear.put("DIV", PDiv);
               hmclear.put("LOC", PLoc);
               
                   pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshSearchPG",
                   null,
                   //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                    OAWebBeanConstants.KEEP_MENU_CONTEXT,
                   null,
                   hmclear, //HashMap
                   
                   false,
                   OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                   OAWebBeanConstants.IGNORE_MESSAGES); 
           }
           
         /**=======================================================================
           *            NAVIGATE TO CONTRACT DETAIL PAGE TO UPDATE TRANSACTION
           * ====================================================================**/ 
           if(pageContext.getParameter(EVENT_PARAM).equals("UPDATE"))
          {
              pageContext.writeDiagnostics(this, "Inside UPDATE Logic ----------------------->"+pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE),4);
              System.out.println("Inside UPDATEHDR Logic ----------------------->"+pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE));
              String rowReference = pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE);
              HashMap hm=new HashMap();  //Tried to send parameter through SetFowardURL
              if(rowReference!=null)
              {
                  pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference ----------------------->",4);
                  OARow row = (OARow)am.findRowByRef(rowReference);
                  if(row!=null)
                  {
                      System.out.println("\"Inside UPDATEHDR Logic and Obtaining Row Reference with Getting Update Header Row+++ PtycshHdrId ----------------------->"+row.getAttribute("PtycshHdrId"));
                      pageContext.writeDiagnostics(this, "Inside UPDATEHDR Logic and Obtaining Row Reference with Getting Update Header Row PtycshHdrId----------------------->"+row.getAttribute("PtycshHdrId"),4);
             
                      hm.put("PTYCSHHDRID", row.getAttribute("PtycshHdrId"));
                      hm.put("DIV", PDiv);
                      hm.put("LOC", PLoc);
                      if(PMode.equals("FARO"))
                      {
                          hm.put("MODE", PMode);
                      }
                      else
                      {
                      hm.put("MODE", "UPD");
                      }
                  }
              }         
             pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
             null,
             //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
              OAWebBeanConstants.KEEP_MENU_CONTEXT,
             null,
             hm, //HashMap
             true,
             OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
             OAWebBeanConstants.IGNORE_MESSAGES);
          }
      /**=======================================================================
        *            NAVIGATE TO CREATE PAGE
        * ====================================================================**/ 
        if(pageContext.getParameter(EVENT_PARAM).equals("CREATE"))
       {
               if(PLoc!=null)
               {
                    ptHdrVO2.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and LOCATION_NAME = '"+PLoc+"' and status in ('InProgress', 'Draft')" );    
               }
               else
               {
                    ptHdrVO2.setWhereClause("1=1 and  DIVISION = '"+PDiv+"' and LOCATION_NAME is null and status in ('InProgress', 'Draft')");  
               }
               pageContext.writeDiagnostics(this, "Inside Params with Values SAVE getting Query ptHdrVO2----------------------->"+ptHdrVO2.getQuery(),4);  
               ptHdrVO2.executeQuery();
               pageContext.writeDiagnostics(this, "Inside Params with Values SAVE getting Query ptHdrVO2----------------------->"+ptHdrVO2.getQuery(),4);  
               pageContext.writeDiagnostics(this, "Inside Params with Values SAVE getting Query ptHdrVO2----------------------->"+ptHdrVO2.getFetchedRowCount(),4);  
               if(ptHdrVO2.getRowCount()>0)
               {
               
                   OAException confirmMessage = new OAException("Cannot Create as Draft Records Exists", OAException.ERROR);
                   pageContext.writeDiagnostics(this, "Inside SAVE Logic Before throwing Exception ----------------------->", 4);
                   pageContext.putDialogMessage(confirmMessage);
               }
               else
               {
                   pageContext.writeDiagnostics(this, "Inside CREATE Logic and Valid and so Allowing ----------------------->",4);
                   HashMap hm=new HashMap();  //Tried to send parameter through SetFowardURL
                    hm.clear();

                   hm.put("DIV", PDiv);
                   hm.put("LOC", PLoc);
                   hm.put("MODE", "ADD");
                   //           hm.put("PTYCSHHDRID", "");

                   
                   pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
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

}
