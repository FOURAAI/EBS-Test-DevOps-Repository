/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Types;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADecimalValidater;
import oracle.apps.fnd.framework.webui.OANavigation;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAKeyFlexBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OAPageLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;

import oracle.apps.fnd.framework.webui.beans.table.OATableBean;
import oracle.apps.fnd.wf.WFContext;

import xxscm.oracle.apps.po.UtilCO;
import oracle.apps.fnd.wf.engine.WFNotificationAPI;

import oracle.cabo.ui.validate.Formatter;


/**
 * Controller for ...
 */
public class PtCshWFCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   String PPtyCshHdrId;    
    String PMode;                      
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID")+" MODE "+pageContext.getParameter("MODE"), 4);
      PPtyCshHdrId = pageContext.getParameter("PTYCSHHDRID");
      PMode = pageContext.getParameter("MODE");
      UtilCO util;
            util = new UtilCO();
      
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
      OAViewObject ptDtlVO= null;
      ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
      OAViewObject ptRecptVO= null;
      
      ptHdrVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);
      ptHdrVO.executeQuery();
      ptHdrVO.first();
      
      if(ptHdrVO.getCurrentRow().getAttribute("LocationName")==null)
      {
          ptHdrVO.getCurrentRow().setAttribute("DispModeLoc", Boolean.TRUE);      
          pageContext.writeDiagnostics(this, "Inside PtCshWFCO : Inside LocationName is null", 4);
      }
      else
      {
          ptHdrVO.getCurrentRow().setAttribute("DispModeLoc", Boolean.FALSE);  
          pageContext.writeDiagnostics(this, "Inside PtCshWFCO : Inside LocationName is not null", 4);
      }
      
      if(PMode!=null)
      {
      if(PMode.equals("READDONLY"))
      {
          pageContext.writeDiagnostics(this, "Inside PtCshWFCO : Inside ReadOnly Mode and Disabling the Buttons", 4);
          ptHdrVO.getCurrentRow().setAttribute("WFButtonRNEnable", Boolean.FALSE);
          ptHdrVO.getCurrentRow().setAttribute("hideWfValues", Boolean.TRUE);
         // util.setPageReadOnly(pageContext,webBean);
      }
     
      } 
      else
      {
          pageContext.writeDiagnostics(this, "Inside PtCshWFCO : Inside ReadOnly Mode and Enabling the Buttons", 4);
//          ptHdrVO.getCurrentRow().setAttribute("WFButtonRNEnable", Boolean.TRUE);
          ptHdrVO.getCurrentRow().setAttribute("WFButtonRNEnable", Boolean.FALSE);
      }
      
      if(ptHdrVO.getCurrentRow().getAttribute("LocationName")==null||ptHdrVO.getCurrentRow().getAttribute("LocationName")=="")
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
          pageContext.writeDiagnostics(this, "Inside  LocationName is not null Condition to Disable Attachment Link in Header ", 4);
          ptHdrVO.getCurrentRow().setAttribute("DisSiteLinesOps", Boolean.FALSE);
      }
      
      
      
//      OAKeyFlexBean kffIdDtl = (OAKeyFlexBean)webBean.findIndexedChildRecursive("KeyFlexItem1");
//       
//       // Set the code combination lov
//        kffIdDtl.useCodeCombinationLOV(true);
//
//        //set the structure code for the item key flex
//         kffIdDtl.setStructureCode("HAYA_ACCOUNTING_FLEXFIELD");
//
//        //Set the attribute name to the item
//        kffIdDtl.setCCIDAttributeName("CcId");
      
      
      OAKeyFlexBean kffIdDt2 = (OAKeyFlexBean)webBean.findIndexedChildRecursive("KeyFlexItem2");
       
       // Set the code combination lov
        kffIdDt2.useCodeCombinationLOV(true);

        //set the structure code for the item key flex
         kffIdDt2.setStructureCode("HAYA_ACCOUNTING_FLEXFIELD");

        //Set the attribute name to the item
        kffIdDt2.setCCIDAttributeName("CcId");
      
      
      ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);         
      pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
      ptDtlVO.executeQuery();
      
//      OAKeyFlexBean kffId = (OAKeyFlexBean)webBean.findIndexedChildRecursive("KeyFlexItem");
//       
//       // Set the code combination lov
//        kffId.useCodeCombinationLOV(true);
//
//        //set the structure code for the item key flex
//         kffId.setStructureCode("HAYA_ACCOUNTING_FLEXFIELD");
//
//        //Set the attribute name to the item
//        kffId.setCCIDAttributeName("CodeCombinationId");
//        
//      OAViewObject gccCodeVO= null;  
//      gccCodeVO=(OAViewObject)am.findViewObject("CodeCombinationVO1");
//      
//        
//      if(!gccCodeVO.isPreparedForExecution())
//                          {
//                              gccCodeVO.executeQuery();
//                          } 

       //Execute the Query
//        KFFAMImpl am = (KFFAMImpl)pageContext.getApplicationModule(webBean);
//        KFFVOImpl vo = (KFFVOImpl)am.findViewObject("KFFVO1");
           
//         if(!vo.isPreparedForExecution())
//        {
//               vo.executeQuery();
//        }
//      }


         pageContext.writeDiagnostics(this, "Value of the Decimal Formatter  1", 4);
      
          pageContext.writeDiagnostics(this, "Value of the Decimal Formatter  1", 4);
          OAPageLayoutBean pageBean = (OAPageLayoutBean)pageContext.getPageLayoutBean();
          OATableBean tableBean = (OATableBean)webBean.findIndexedChildRecursive("claimLinesTblRN");
          
          pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 2"+pageBean, 4);
          Formatter formatter =         new OADecimalValidater("#,##0.000;#,##0.000","#,##0.000;#,##0.000");
          pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 3"+formatter, 4);
          OAMessageTextInputBean sbean2 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("OpeningBal");
          pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 4"+sbean2, 4);
   
          pageContext.writeDiagnostics(this, "Value of the Decimal Formatter 5 After Setting", 4);
          OAMessageTextInputBean sbean3 = (OAMessageTextInputBean)tableBean.findIndexedChildRecursive("Amount111"); 
          OAMessageStyledTextBean sbean4 = (OAMessageStyledTextBean)webBean.findIndexedChildRecursive("ClosingBalance113"); 
          OAMessageTextInputBean sbean5 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("CashInHand113"); 
          OAMessageTextInputBean sbean6 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("Receipts114");      
          OAMessageTextInputBean sbean7 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("ClaimAmount114");
          OAMessageTextInputBean sbean8 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("StaffIouAmount114");
          sbean2.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);  
          sbean3.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
          sbean4.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
          sbean5.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
          sbean6.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
          sbean7.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
          if(sbean8!=null)
          {
          sbean8.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
          }

 
 
         OANavigation wf = new OANavigation();
         WFContext ctx = wf.getWFContext(pageContext); 
         pageContext.writeDiagnostics(this, "Testing the WF Context =============================> "+ctx, 4);
        
         
         pageContext.writeDiagnostics(this, "Testing the WF Context =============================>"+WFNotificationAPI.getSubject(ctx,new BigDecimal(74)), 4);
         
         pageContext.writeDiagnostics(this, "Testing the WF Context =============================>"+WFNotificationAPI.getSubject(ctx,new BigDecimal(74)), 4);
         
      
         
      
    
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
    pageContext.writeDiagnostics(this, "Inside PtCshWFCO :PFR ",4);

    OAApplicationModule am=null;
    am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
    OAViewObject ptHdrVO= null;
    ptHdrVO=(OAViewObject)am.findViewObject("PtCshHdrVO1");
    OAViewObject ptDtlVO= null;
    ptDtlVO=(OAViewObject)am.findViewObject("PtCshDtlVO1");
    OAViewObject expTypeVO= null;
    expTypeVO=(OAViewObject)am.findViewObject("ExpenseTypeVO1");
    OAViewObject glRatesVO= null;
    glRatesVO=(OAViewObject)am.findViewObject("glDailyRatesVO1");
    
    
    if (pageContext.isLovEvent()) {
        pageContext.writeDiagnostics(this, "Inside ============> LOV Event" , 4);
        String lovInputSourceId = pageContext.getLovInputSourceId();
        if ("ExpenseType".equals(lovInputSourceId)) 
        {
            pageContext.writeDiagnostics(this, "Inside ExpenseType1============> LOV Event ptDtlVO.getCurrentRow().getAttribute(\"ExpenseType\")"+ptDtlVO.getCurrentRow().getAttribute("ExpenseType"), 4); 
            String ptyCshDtlID = ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId").toString();
            pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event" , 4);
            am.invokeMethod("apply");  
            OAViewObject ccVO= null;
            ccVO=(OAViewObject)am.findViewObject("CodeCombinationVO1");
            pageContext.writeDiagnostics(this, "Inside SAVE Logic Befor ccVO!=null------------ccVO----------->"+ccVO,4);
            if(ccVO!=null)
            {
                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null-------------------ptyCshDtlID---->"+ptyCshDtlID ,4);
                ccVO.setNamedWhereClauseParam("ptycsh_dtl_id", ptyCshDtlID);
                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----------------------->"+ccVO.getQuery(),4);
                ccVO.executeQuery();
                ccVO.first();
                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----ccVO.getFetchedRowCount(------------------->"+ccVO.getFetchedRowCount(),4);
                if(ccVO.getFetchedRowCount()>0)
                {
                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----ccVO.getCurrentRow().getAttribute(\"CodeCombinationId\")------------------->"+ccVO.getCurrentRow().getAttribute("CodeCombinationId")+" ccVO.getCurrentRow().getAttribute(\"ConcatenatedSegments\") : "+ccVO.getCurrentRow().getAttribute("ConcatenatedSegments"),4);
                    ptDtlVO.getCurrentRow().setAttribute("CcId", ccVO.getCurrentRow().getAttribute("CodeCombinationId"));    
                    ptDtlVO.getCurrentRow().setAttribute("ConcatenatedSegments", ccVO.getCurrentRow().getAttribute("ConcatenatedSegments"));  
                    
                    am.invokeMethod("apply");  
                    
                    ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);         
                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
                    ptDtlVO.executeQuery();
                }
                else
                {
                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null  Row Count is not greater than Zero",4);
                }
            }
                                
            am.invokeMethod("apply");  
        
        }
        
        
//        if ("Governate11".equals(lovInputSourceId)) 
//        {
//            pageContext.writeDiagnostics(this, "Inside ExpenseType1============> LOV Event ptDtlVO.getCurrentRow().getAttribute(\"ExpenseType\")"+ptDtlVO.getCurrentRow().getAttribute("ExpenseType"), 4); 
//            String ptyCshDtlID = ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId").toString();
//            pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event" , 4);
//            am.invokeMethod("apply");  
//            OAViewObject ccVO= null;
//            ccVO=(OAViewObject)am.findViewObject("CodeCombinationVO1");
//            pageContext.writeDiagnostics(this, "Inside SAVE Logic Befor ccVO!=null------------ccVO----------->"+ccVO,4);
//            if(ccVO!=null)
//            {
//                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null-------------------ptyCshDtlID---->"+ptyCshDtlID ,4);
//                ccVO.setNamedWhereClauseParam("ptycsh_dtl_id", ptyCshDtlID);
//                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----------------------->"+ccVO.getQuery(),4);
//                ccVO.executeQuery();
//                ccVO.first();
//                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----ccVO.getFetchedRowCount(------------------->"+ccVO.getFetchedRowCount(),4);
//                if(ccVO.getFetchedRowCount()>0)
//                {
//                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----ccVO.getCurrentRow().getAttribute(\"CodeCombinationId\")------------------->"+ccVO.getCurrentRow().getAttribute("CodeCombinationId")+" ccVO.getCurrentRow().getAttribute(\"ConcatenatedSegments\") : "+ccVO.getCurrentRow().getAttribute("ConcatenatedSegments"),4);
//                    ptDtlVO.getCurrentRow().setAttribute("CcId", ccVO.getCurrentRow().getAttribute("CodeCombinationId"));    
//                    ptDtlVO.getCurrentRow().setAttribute("ConcatenatedSegments", ccVO.getCurrentRow().getAttribute("ConcatenatedSegments"));  
//                    
//                    am.invokeMethod("apply");  
//                    
//                    ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);         
//                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
//                    ptDtlVO.executeQuery();
//                }
//                else
//                {
//                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null  Row Count is not greater than Zero",4);
//                }
//            }
//                                
//            am.invokeMethod("apply");  
//        
//        }

       
        
        if ("Department".equals(lovInputSourceId)) 
        {
            String ptyCshDtlID = ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId").toString();
            pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event" , 4);
            am.invokeMethod("apply");  
            OAViewObject ccVO= null;
            ccVO=(OAViewObject)am.findViewObject("CodeCombinationVO1");
            pageContext.writeDiagnostics(this, "Inside SAVE Logic Befor ccVO!=null------------ccVO----------->"+ccVO,4);
            if(ccVO!=null)
            {
                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null-------------------ptyCshDtlID---->"+ptyCshDtlID ,4);
                ccVO.setNamedWhereClauseParam("ptycsh_dtl_id", ptyCshDtlID);
                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----------------------->"+ccVO.getQuery(),4);
                ccVO.executeQuery();
                ccVO.first();
                pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----ccVO.getFetchedRowCount(------------------->"+ccVO.getFetchedRowCount(),4);
                if(ccVO.getFetchedRowCount()>0)
                {
                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null----ccVO.getCurrentRow().getAttribute(\"CodeCombinationId\")------------------->"+ccVO.getCurrentRow().getAttribute("CodeCombinationId")+" ccVO.getCurrentRow().getAttribute(\"ConcatenatedSegments\") : "+ccVO.getCurrentRow().getAttribute("ConcatenatedSegments"),4);
                    ptDtlVO.getCurrentRow().setAttribute("CcId", ccVO.getCurrentRow().getAttribute("CodeCombinationId"));    
                    ptDtlVO.getCurrentRow().setAttribute("ConcatenatedSegments", ccVO.getCurrentRow().getAttribute("ConcatenatedSegments"));  
                    
                    am.invokeMethod("apply");  
                    
                    ptDtlVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);         
                    pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
                    ptDtlVO.executeQuery();
                }
                else
                {
                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null  Row Count is not greater than Zero",4);
                }
            }
                                
            am.invokeMethod("apply");  
        }
//        if ("ExchangeRateType".equals(lovInputSourceId)) 
//        {
//            pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event"+ptDtlVO.getCurrentRow().getAttribute("ExchangeRateType"), 4);
//     //       pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event ptDtlVO.getCurrentRow().getAttribute(\"Currency\")"+ptDtlVO.getCurrentRow().getAttribute("Currency"), 4); 
//         //   exRateCon = exRateCon.concat(" and CONVERSION_TYPE ="+ptDtlVO.getCurrentRow().getAttribute("ExchangeRateType"));
//         //   pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event ptDtlVO.getCurrentRow().  exRateCon   "+exRateCon, 4);  
//    //              if(ptDtlVO.getCurrentRow().getAttribute("Currency").equals("OMR"))
//    //              {
//    //                  pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside If Condition"+exRateCon, 4);
//    //              }
//    //              else
//    //              {
//                exRateCon = "";
//    //                  OAMessageTextInputBean otib = (OAMessageTextInputBean)webBean.findChildRecursive("ExchangeRateDate")
//                
//                exRateCon = exRateCon.concat(" 1=1 and FROM_CURRENCY ='"+ptDtlVO.getCurrentRow().getAttribute("Currency")+"'"+" and CONVERSION_TYPE = '"+ptDtlVO.getCurrentRow().getAttribute("ExchangeRateType")+"'"+" and trunc(CONVERSION_DATE) = to_date('"+pageContext.getParameter("ExchangeRateDate")+ "', 'DD-Mon-YYYY')");
//                pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside else Condition"+exRateCon, 4);
//                glRatesVO.setWhereClause(exRateCon);
//                pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> glRatesVO   "+glRatesVO.getQuery(), 4); 
//                glRatesVO.executeQuery();
//               glRatesVO.first();
//            pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> glRatesVO.getRowCount()    : "+glRatesVO.getRowCount(), 4);
//               if(glRatesVO.getRowCount()>0)
//               {
//            pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside else Condition"+glRatesVO.getCurrentRow().getAttribute("ConversionRate"), 4);
//    //              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside else Condition"+exRateCon, 4);
//               
//                
//            ptDtlVO.getCurrentRow().setAttribute("ExchangeRate", glRatesVO.getCurrentRow().getAttribute("ConversionRate"));
//            pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> After setting the Value"+glRatesVO.getCurrentRow().getAttribute("ConversionRate"), 4);              
//    //              int exchangeRate = Integer.parseInt(glRatesVO.getCurrentRow().getAttribute("ExchangeRate").toString());
//    //              int amount = Integer.parseInt(glRatesVO.getCurrentRow().getAttribute("Amount").toString());
//    //              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> exchangeRate   "+exchangeRate+"  amount "+amount, 4);
//    //              ptDtlVO.getCurrentRow().setAttribute("AmountInOmr", glRatesVO.getCurrentRow().getAttribute("ExchangeRate"));
//    //
//    //              }
//        }
                     
//        }
    }
    
    
    pageContext.writeDiagnostics(this, "Inside PtCshWFCO : PFR --pageContext.getParameter(\"Action\")!=null"+pageContext.getParameter("Action"), 4);
    if(pageContext.getParameter("WFSave")!=null) 
        {
              pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : PFR --pageContext.getParameter(\"WFSave\")!=null"+pageContext.getUserName()+" pageContext.getUserName() "+pageContext.getUserId(), 4);
              am.invokeMethod("apply"); 
            
        }
    else if(pageContext.getParameter("WFCancel")!=null) 
        {
            pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : PFR --pageContext.getParameter(\"WFCancel\")!=null"+pageContext.getUserName()+" pageContext.getUserName() "+pageContext.getUserId(), 4);
          
        }

}
}
