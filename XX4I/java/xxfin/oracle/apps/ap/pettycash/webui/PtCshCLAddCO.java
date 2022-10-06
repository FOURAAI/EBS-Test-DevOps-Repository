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

import java.util.Enumeration;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.apps.fnd.framework.OARow;

/**
 * Controller for ...
 */
public class PtCshCLAddCO extends OAControllerImpl
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
      OAViewObject ptRecptVO= null;
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
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO2");
      OAViewObject ptDtlVO2= null;
      ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO2");

    
    
    
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
                          ptHdrVO.setWhereClause("1=1 and LOCATION_NAME is not null and status = 'Approved' ");    
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
                              sqlparam=sqlparam.substring(5);
                              sqlparam.concat(" 1=1 and LOCATION_NAME is not null and status = 'Approved' ");    
                              System.out.println("Modified param"+sqlparam);
                              ptHdrVO.reset();
                              ptHdrVO.setWhereClause(sqlparam);                            
                              ptHdrVO.executeQuery();
                              pageContext.writeDiagnostics(this, "Inside Params with Values "+sqlparam+"SAVE getting Query ----------------------->"+ptHdrVO.getQuery(),4);                      
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
                                      Serializable[] sn1={"PtCshDtlVO2"};
                                      am.invokeMethod("createRecord", sn1); 
                                    ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+rowi.getAttribute("PtycshHdrId"));      
                                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
                                    ptDtlVO.executeQuery();
                                    ptDtlVO.first();
                                    pageContext.writeDiagnostics(this, "Getting Rows       ------ptDtlVO.getCurrentRow().getAttribute(\"PtycshDtlId\")--"+ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId")+"Getting Rows       ------ptDtlVO.getCurrentRow().getAttribute(\"ConcatenatedSegments\")--"+ptDtlVO.getCurrentRow().getAttribute("ConcatenatedSegments"), 4);
                                      
                                      
                                      
                                    
//                                    ptDtlVO2.getCurrentRow().setAttribute("ShipmtHdrId", pageContext.getParameter("SHIPMTHDRID"));
//                                    ptDtlVO2.getCurrentRow().setAttribute("PoHeaderId", rowi.getAttribute("PoHeaderId"));
//                                    ptDtlVO2.getCurrentRow().setAttribute("PoLineId", rowi.getAttribute("PoLineId"));
//                                    ptDtlVO2.getCurrentRow().setAttribute("SalesOrderNumber", rowi.getAttribute("SalesOrderNumber"));
//                                    ptDtlVO2.getCurrentRow().setAttribute("AllocationQty", rowi.getAttribute("AllocationQty"));
//                                    
//                                    ptDtlVO2.getCurrentRow().setAttribute("ShipmtDtlId", dtlVO.getCurrentRow().getAttribute("ShipmtDtlId"));
//                                    ptDtlVO2.getCurrentRow().setAttribute("ShipmtHdrId", pageContext.getParameter("SHIPMTHDRID"));
//       //                              soVO.getCurrentRow().setAttribute("PoHeaderId", rowi.getAttribute("PoHeaderId"));
//       //                              soVO.getCurrentRow().setAttribute("PoLineId", rowi.getAttribute("PoLineId"));
//                                    ptDtlVO2.getCurrentRow().setAttribute("SalesOrderNumber", rowi.getAttribute("SalesOrderNumber"));
//                                    ptDtlVO2.getCurrentRow().setAttribute("AllocationQty", rowi.getAttribute("AllocationQty"));
//                                        am.invokeMethod("apply");
//                                        OAException confirmMessage = new OAException("The Transactions has been saved Successfully", OAException.CONFIRMATION);
//                                        System.out.println("Inside SAVE Logic Before throwing Exception ----------------------->");
//                                        pageContext.putDialogMessage(confirmMessage);
                                    
                                  
                            
                                    
       //                              OADBTransaction txn=(OADBTransaction)am.getOADBTransaction();
                                    
       //                              Number seqNoSno1=txn.getSequenceValue("XXATC_SHIPMT_DTL_ID_S");
       //                              rowi.setAttribute("ShipmtDtlId", seqNoSno1);
       //                              rowi.setAttribute("ShipmtHdrId", pageContext.getParameter("SHIPMTHDRID"));
          //                            oracle.jbo.domain.Number vFileId = (oracle.jbo.domain.Number)rowi.getFileId();
       //                            rowi.remove();   // Here you can call updates if reqd
       //                            System.out.println("Selected To Delete File Id "+vFileId);
                                }
                              }
      
      
      
      
      
                    am.invokeMethod("apply");  
                    
                    HashMap hmClAddSave=new HashMap();  //Tried to send parameter through SetFowardURL
                    hmClAddSave.put("DISPMSG", "Records for "+ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId")+" saved Successfully"); 
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
           
    public void validateFields(OAPageContext pageContext, OAApplicationModule am, OAViewObject smeDetVO, OAViewObject cntrctAttchVO)
       {

           if(smeDetVO.getCurrentRow().getAttribute("SmeName")==null||smeDetVO.getCurrentRow().getAttribute("SmeYesNo")==null||smeDetVO.getCurrentRow().getAttribute("ItemDescription")==null||(smeDetVO.getCurrentRow().getAttribute("ReasonForForeign")==null&&(smeDetVO.getCurrentRow().getAttribute("LocationOfVendor")).equals("Foreign"))||smeDetVO.getCurrentRow().getAttribute("PoNumber")==null||smeDetVO.getCurrentRow().getAttribute("PoDate")==null||smeDetVO.getCurrentRow().getAttribute("PoValue")==null||cntrctAttchVO.getRowCount()==0)
           {
             String fieldNums = ""; 
               if(smeDetVO.getCurrentRow().getAttribute("SmeName")==null)
               {
                   fieldNums = " Vendor Name ";            
               }
               if(smeDetVO.getCurrentRow().getAttribute("SmeYesNo")==null)
               {
                   fieldNums = fieldNums+" SME Yes NO ";                   
               }
              if(smeDetVO.getCurrentRow().getAttribute("ItemDescription")==null)
               {
                   fieldNums = fieldNums+" Item Description ";            
               }            
               if(smeDetVO.getCurrentRow().getAttribute("ReasonForForeign")==null&&(smeDetVO.getCurrentRow().getAttribute("LocationOfVendor")).equals("Foreign"))
               {                
                   fieldNums = fieldNums+" Reason For Foreign ";          
               }                      
               if(smeDetVO.getCurrentRow().getAttribute("PoNumber")==null)
               {
                   fieldNums = fieldNums+" PO Number ";            
               }
               if(smeDetVO.getCurrentRow().getAttribute("PoDate")==null)
               {
                   fieldNums = fieldNums+" PO Date ";            
               }
               if(smeDetVO.getCurrentRow().getAttribute("PoValue")==null)
               {
                   fieldNums = fieldNums+" PO Value ";            
               }
               if(cntrctAttchVO.getRowCount()==0);
               {
                   fieldNums = fieldNums+" Attachment ";
               }
               throw new OAException("Fields should be Entered :"+fieldNums,OAException.ERROR); 
           }
       }

}
