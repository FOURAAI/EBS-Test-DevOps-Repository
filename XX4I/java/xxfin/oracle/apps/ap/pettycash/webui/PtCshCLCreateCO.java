/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.webui;

import com.sun.java.util.collections.HashMap;

import java.io.Serializable;

import java.sql.Types;

import java.util.Hashtable;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OADecimalValidater;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.apps.fnd.wf.WFContext;

import oracle.cabo.ui.validate.Formatter;

import oracle.jdbc.OracleCallableStatement;

import xxscm.oracle.apps.po.UtilCO;




/**
 * Controller for ...
 */

public class PtCshCLCreateCO extends OAControllerImpl
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
    String PPtycshDtlId;   
    String exRateCon = " 1=1 ";
    int AttachmentCnt = -1;
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
      pageContext.writeDiagnostics(this, "Inside PtCshCLCreateCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID")+" For Update PTYCSHDTLID : "+pageContext.getParameter("PTYCSHDTLID"), 4);
      System.out.println("Inside PtCshCLCreateCO : Getting  Param -----------------------> DIV"+pageContext.getParameter("DIV")+" Getting  Param -----------------------> LOC "+pageContext.getParameter("LOC")+" Getting  Param -----------------------> MODE "+pageContext.getParameter("MODE")+" Getting  Param -----------------------> PTYCSHHDRID "+pageContext.getParameter("PTYCSHHDRID"));
      PDiv = pageContext.getParameter("DIV");
      PLoc = pageContext.getParameter("LOC");
      PPtyCshHdrId = pageContext.getParameter("PTYCSHHDRID");
      PPtycshDtlId = pageContext.getParameter("PTYCSHDTLID");
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
      
      
      OAViewObject expTypeVO= null;
      expTypeVO=(OAViewObject)am.findViewObject("ExpenseTypeVO1");
      
      OAViewObject expTypeVO2= null;                                    // For 
      expTypeVO2=(OAViewObject)am.findViewObject("ExpenseTypeVO2");
      
      UtilCO util;
            util = new UtilCO();
      

      /**=======================================================================
      *            ADD FUNCTIONALITY FROM SME DETAIL PAGE
      * ====================================================================**/ 
       if(PMode.equals("ADD")&&PPtyCshHdrId!=null)
        {
           pageContext.writeDiagnostics(this, "Inside PtCshCreateCO : Before Creating Row", 4);
          Serializable[] sn1={"PtCshDtlVO1"};
          am.invokeMethod("createRecord", sn1); 
          ptDtlVO.getCurrentRow().setAttribute("ExchangeRateDate", am.getOADBTransaction().getCurrentUserDate()); 
          ptDtlVO.getCurrentRow().setAttribute("PtycshHdrId", PPtyCshHdrId); 
            ptDtlVO.getCurrentRow().setAttribute("PtycshHdrId", PPtyCshHdrId); 
            ptDtlVO.getCurrentRow().setAttribute("Currency", "OMR");
            ptDtlVO.getCurrentRow().setAttribute("ExchangeRate", "1");
            ptDtlVO.getCurrentRow().setAttribute("ReadOnly", Boolean.TRUE);
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
            try 
            {
            OAViewObject getSeqVO= null;
            getSeqVO=(OAViewObject)am.findViewObject("getLineSeqVO1");
            pageContext.writeDiagnostics(this, "Inside Create Mode getting getSeqVO"+getSeqVO, 4);
            getSeqVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);
            pageContext.writeDiagnostics(this, "Inside Create Mode getting getSeqVO"+getSeqVO.getQuery(), 4);
            getSeqVO.executeQuery();
            getSeqVO.first();
            pageContext.writeDiagnostics(this, "Inside Create Mode getting getSeqVO"+getSeqVO.getRowCount()+" Getting Curr Sequence "+getSeqVO.getCurrentRow().getAttribute("CurrSeq") , 4);
            ptDtlVO.getCurrentRow().setAttribute("DtlNum", getSeqVO.getCurrentRow().getAttribute("CurrSeq"));
            ptDtlVO.getCurrentRow().setAttribute("DtlNumAct", getSeqVO.getCurrentRow().getAttribute("CurrSeq")+"-"+lcDivLoc);    
                
                
            }
            catch(Exception e)
            {
                pageContext.writeDiagnostics(this, "Inside Exception in getting Sequence", 4);
                ptDtlVO.getCurrentRow().setAttribute("DtlNum", 1);
                ptDtlVO.getCurrentRow().setAttribute("DtlNumAct", 1+"-"+lcDivLoc);    
            }
            
            
            pageContext.writeDiagnostics(this, "Inside PMode.equals(\"ADD\") Inside If OPS", 4);
                if(PDiv.equals("OPS"))
                {
                    expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
                    if(PLoc==null||PLoc=="")
                    {
                        expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_COST_CENTE' ");
                    }
                    else
                    {
                        if(PLoc.equals("ANSB")) {
                            
                            expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'ANSB' ");
                        }
                        if(PLoc.equals("SEB")) {
                            
                            expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'SEB' ");
                        }
                        if(PLoc.equals("BSR")) {
                            
                            expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'BSR' ");
                        }
                        if(PLoc.equals("GMTR")) {
                            
                            expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'GMTR' ");
                        }
                    }
                } 
                else if(PDiv.equals("FIN"))
                {
                    expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_FINANCE_EXP_LIST' ");
                    expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_FINANCE_COST_CENTER' ");
                }
                else if(PDiv.equals("ADM"))
                {
                    expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_HR_ADMIN_EXP_LIST' ");
                    expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_HR_ADMIN_COST_CENTER' ");
                }
                else if(PDiv.equals("LOG"))
                {
                    expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_LOGISTICS_EXP_LIST' ");
                    expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_LOGISTIC_COST_CENTER' ");
                }
                
            pageContext.writeDiagnostics(this, "Inside PMode.equals(\"ADD\") Inside If OPS expTypeVO.getQuery() "+expTypeVO.getQuery(), 4);
            
        }
      else if(PMode.equals("UPD")&&PPtycshDtlId!=null)
      {
          ptDtlVO.setWhereClause(" PTYCSH_DTL_ID = "+PPtycshDtlId);      
          pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
          ptDtlVO.executeQuery();
              if(PDiv.equals("OPS"))
              {
                  expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_EXP_LIST' ");
                  if(PLoc==null||PLoc=="")
                  {
                      expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_OPERATIONS_COST_CENTE' ");
                  }
                  else
                  {
                      if(PLoc.equals("ANSB")) {
                          
                          expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'ANSB' ");
                      }
                      if(PLoc.equals("SEB")) {
                          
                          expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'SEB' ");
                      }
                      if(PLoc.equals("BSR")) {
                          
                          expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'BSR' ");
                      }
                      if(PLoc.equals("GMTR")) {
                          
                          expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_SITES_COST_CENTER' and tag = 'GMTR' ");
                      }
                  }
              } 
              else if(PDiv.equals("FIN"))
              {
                  expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_FINANCE_EXP_LIST' ");
                  expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_FINANCE_COST_CENTER' ");
              }
              else if(PDiv.equals("ADM"))
              {
                  expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_HR_ADMIN_EXP_LIST' ");
                  expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_HR_ADMIN_COST_CENTER' ");
              }
              else if(PDiv.equals("LOG"))
              {
                  expTypeVO.setWhereClause(" lookup_type = 'XXFIN_PC_LOGISTICS_EXP_LIST' ");
                  expTypeVO2.setWhereClause(" lookup_type = 'XXFIN_PC_LOGISTIC_COST_CENTER' ");
              }
      }
      else if(PMode.equals("FARO"))
      {
          ptDtlVO.setWhereClause(" PTYCSH_DTL_ID = "+PPtycshDtlId);      
          pageContext.writeDiagnostics(this, "Getting ptDtlVO.getQuery() "+ptDtlVO.getQuery(), 4);
          ptDtlVO.executeQuery();
          util.setPageReadOnly(pageContext,webBean);
      }
        

//      OAMessageFileUploadBean file = (OAMessageFileUploadBean)webBean.findChildRecursive("AttachFile");
//      OADataBoundValueViewObject displayNameBoundValue = new OADataBoundValueViewObject(file, "FileDesc");
//      file.setAttributeValue(OAWebBeanConstants.DOWNLOAD_FILE_NAME, displayNameBoundValue);
      
//      OAMessageFileUploadBean rfile = (OAMessageFileUploadBean)webBean.findChildRecursive("RAttachFile");
//      OADataBoundValueViewObject rdisplayNameBoundValue = new OADataBoundValueViewObject(rfile, "RFileDesc");
//      rfile.setAttributeValue(OAWebBeanConstants.DOWNLOAD_FILE_NAME, rdisplayNameBoundValue);
          
     Formatter formatter =         new OADecimalValidater("#,##0.000;#,##0.000","#,##0.000;#,##0.000");
     OAMessageTextInputBean sbean4 = (OAMessageTextInputBean)webBean.findIndexedChildRecursive("Amount"); 
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
      OAViewObject expTypeVO= null;
      expTypeVO=(OAViewObject)am.findViewObject("ExpenseTypeVO1");
      OAViewObject glRatesVO= null;
      glRatesVO=(OAViewObject)am.findViewObject("glDailyRatesVO1");

      

          pageContext.writeDiagnostics(this, "Before Entering LOV Event PFR" , 4);
      if (pageContext.isLovEvent()) {
          pageContext.writeDiagnostics(this, "Inside ============> LOV Event" , 4);
          String lovInputSourceId = pageContext.getLovInputSourceId();
          if ("ExchangeRateDate".equals(lovInputSourceId)) 
          {
              pageContext.writeDiagnostics(this, "Inside ExchangeRateDate============> LOV Event ptDtlVO.getCurrentRow().getAttribute(\"ExchangeRateDate\")"+ptDtlVO.getCurrentRow().getAttribute("ExchangeRateDate"), 4); 
          
          }
          
          if ("Currency".equals(lovInputSourceId)) 
          {
              pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event" , 4);
              pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event ptDtlVO.getCurrentRow().getAttribute(\"Currency\")"+ptDtlVO.getCurrentRow().getAttribute("Currency"), 4); 
         //     exRateCon = exRateCon.concat(" and FROM_CURRENCY ="+ptDtlVO.getCurrentRow().getAttribute("Currency"));
//              pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event ptDtlVO.getCurrentRow().  exRateCon   "+exRateCon, 4);     
             if(ptDtlVO.getCurrentRow().getAttribute("Currency").equals("OMR"))
             {
                 pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event Inside If Condition"+exRateCon, 4);
             }
             else
             {
                 ptDtlVO.getCurrentRow().setAttribute("ReadOnly", Boolean.FALSE);
                 pageContext.writeDiagnostics(this, "Inside Currency============> LOV Event Inside If Condition"+ptDtlVO.getCurrentRow().getAttribute("ReadOnly"), 4);
             }          
          }
          if ("ExchangeRateType".equals(lovInputSourceId)) 
          {
              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event"+ptDtlVO.getCurrentRow().getAttribute("ExchangeRateType"), 4);
       //       pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event ptDtlVO.getCurrentRow().getAttribute(\"Currency\")"+ptDtlVO.getCurrentRow().getAttribute("Currency"), 4); 
           //   exRateCon = exRateCon.concat(" and CONVERSION_TYPE ="+ptDtlVO.getCurrentRow().getAttribute("ExchangeRateType"));
           //   pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event ptDtlVO.getCurrentRow().  exRateCon   "+exRateCon, 4);  
//              if(ptDtlVO.getCurrentRow().getAttribute("Currency").equals("OMR"))
//              {
//                  pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside If Condition"+exRateCon, 4);
//              }
//              else
//              {
                  exRateCon = "";
//                  OAMessageTextInputBean otib = (OAMessageTextInputBean)webBean.findChildRecursive("ExchangeRateDate")
                  
                  exRateCon = exRateCon.concat(" 1=1 and FROM_CURRENCY ='"+ptDtlVO.getCurrentRow().getAttribute("Currency")+"'"+" and CONVERSION_TYPE = '"+ptDtlVO.getCurrentRow().getAttribute("ExchangeRateType")+"'"+" and trunc(CONVERSION_DATE) = to_date('"+pageContext.getParameter("ExchangeRateDate")+ "', 'DD-Mon-YYYY')");
                  pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside else Condition"+exRateCon, 4);
                  glRatesVO.setWhereClause(exRateCon);
                  pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> glRatesVO   "+glRatesVO.getQuery(), 4); 
                  glRatesVO.executeQuery();
                 glRatesVO.first();
              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> glRatesVO.getRowCount()    : "+glRatesVO.getRowCount(), 4);
                 if(glRatesVO.getRowCount()>0)
                 {
              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside else Condition"+glRatesVO.getCurrentRow().getAttribute("ConversionRate"), 4);
//              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> LOV Event Inside else Condition"+exRateCon, 4);
                 
                  
              ptDtlVO.getCurrentRow().setAttribute("ExchangeRate", glRatesVO.getCurrentRow().getAttribute("ConversionRate"));
              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> After setting the Value"+glRatesVO.getCurrentRow().getAttribute("ConversionRate"), 4);              
//              int exchangeRate = Integer.parseInt(glRatesVO.getCurrentRow().getAttribute("ExchangeRate").toString());
//              int amount = Integer.parseInt(glRatesVO.getCurrentRow().getAttribute("Amount").toString());
//              pageContext.writeDiagnostics(this, "Inside ExchangeRateType============> exchangeRate   "+exchangeRate+"  amount "+amount, 4); 
//              ptDtlVO.getCurrentRow().setAttribute("AmountInOmr", glRatesVO.getCurrentRow().getAttribute("ExchangeRate"));
//                  
//              }
          }
                       
          }
          
      }
      
      
      if(pageContext.getParameter(EVENT_PARAM).equals("CANCEL"))
                    /**=======================================================================
                           *            NAVIGATE TO BACK PAGE OR CANCEL TRANSACTION
                           * ====================================================================**/ 
                             if(pageContext.getParameter(EVENT_PARAM).equals("CANCEL"))
                             {
                                 pageContext.writeDiagnostics(this, "Inside CANCEL Logic ----------------------->",4);
                                HashMap hmclCan=new HashMap();  //Tried to send parameter through SetFowardURL
                                 hmclCan.put("PTYCSHHDRID", PPtyCshHdrId);
                                 hmclCan.put("DIV", PDiv);
                                 hmclCan.put("LOC", PLoc);
                                 if(PMode.equals("FARO"))
                                 {
                                     hmclCan.put("MODE", PMode);
                                 }
                                 else
                                 {
                                     hmclCan.put("MODE", "UPD");
                                 }  
                          
                                 pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
                                 null,
                                 //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                                 OAWebBeanConstants.KEEP_MENU_CONTEXT,
                                 null,
                                 hmclCan, //HashMap                      
                                 false,
                                 OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                                 OAWebBeanConstants.IGNORE_MESSAGES); 
                             }
      /**=======================================================================
              *            SAVE RECORDS WHICH WILL COMMIT THE RECORDS
              * ====================================================================**/ 
                if(pageContext.getParameter(EVENT_PARAM).equals("SAVE"))
                {
                    am.invokeMethod("apply"); 
                    pageContext.writeDiagnostics(this, "Inside SAVE Logic ----------------------->",4);
//                    validateFields(pageContext, am, smeDetVO, cntrctAttchVO);          
//                    smeDetVO.getCurrentRow().setAttribute("WfStatus", "Saved");

                     SetAttachmentCnt( pageContext, webBean);  
                     if(PMode.equals("UPD"))
                     {
                         String flag = "";
                               String callStr = "{ call xxhw_fin_pettycash_util_pkg.bill_attach_count (:1,:2) }";
                               try {
                                   OracleCallableStatement callable = (OracleCallableStatement)am.getOADBTransaction().createCallableStatement(callStr,1);
                                   callable.setString(1,ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId").toString());
                                   callable.registerOutParameter(2,Types.VARCHAR);
                                   pageContext.writeDiagnostics(this, "Inside SAVE Before Callable Executable---->" ,4);
                                   callable.execute();
                                   flag = callable.getString(2);
                                   pageContext.writeDiagnostics(this, "Inside SAVE After Callable Executable---->"+flag,4);
                                   callable.close();
                                   if(flag.equals("Y"))
                                   {
                                       AttachmentCnt=AttachmentCnt+1;
                                   }
                                    
                               }
                               catch (Exception e) {
                                   throw OAException.wrapperException(e);
                               }

                     
                     }
                     if (AttachmentCnt == -1) 
                     {
                         throw new OAException("Attachment has to be attached",OAException.ERROR);
                     }

                    else
                    {
                        validateFields(pageContext, am, ptDtlVO);    
                         String ptyCshDtlID = ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId").toString();
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
                            }
                            else
                            {
                            pageContext.writeDiagnostics(this, "Inside SAVE Logic ccVO!=null  Row Count is not greater than Zero",4);
                            }
                        }
                        am.invokeMethod("apply");                      
                        HashMap hmClSave=new HashMap();  //Tried to send parameter through SetFowardURL
                        hmClSave.put("DISPMSG", "Records for "+ptDtlVO.getCurrentRow().getAttribute("PtycshDtlId")+" saved Successfully"); 
                        hmClSave.put("PTYCSHHDRID", PPtyCshHdrId);
                        hmClSave.put("DIV", PDiv);
                        hmClSave.put("LOC", PLoc);
                        if(PMode.equals("FARO"))
                        {
                            hmClSave.put("MODE", PMode);
                        }
                        else
                        {
                            hmClSave.put("MODE", "UPD");
                        }  
                   
                   
                        pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/pettycash/webui/PtCshCreatePG",
                        null,
                        //OAWebBeanConstants.KEEP_NO_DISPLAY_MENU_CONTEXT,
                         OAWebBeanConstants.KEEP_MENU_CONTEXT,
                        null,
                        hmClSave, //HashMap                      
                        false,
                        OAWebBeanConstants.ADD_BREAD_CRUMB_YES,
                        OAWebBeanConstants.IGNORE_MESSAGES); 
                    }
                }
    
  }
  
    public void validateFields(OAPageContext pageContext, OAApplicationModule am, OAViewObject ptDtlVO)
       {
       
            String ValMsg = "";
           pageContext.writeDiagnostics(this, "Inside validateFields ", 4);
           if(ptDtlVO.getCurrentRow().getAttribute("ExpenseType")==null||ptDtlVO.getCurrentRow().getAttribute("Department")==null||ptDtlVO.getCurrentRow().getAttribute("Currency")==null||ptDtlVO.getCurrentRow().getAttribute("Amount")==null||ptDtlVO.getCurrentRow().getAttribute("BriefNarration")==null||Float.parseFloat(ptDtlVO.getCurrentRow().getAttribute("Amount").toString())>=201)      //||ptDtlVO.getCurrentRow().getAttribute("AttachFile")==null)
           {
             String fieldNums = ""; 
               if(ptDtlVO.getCurrentRow().getAttribute("ExpenseType")==null)
               {
                   fieldNums = " Expense Type ";            
               }
               if(ptDtlVO.getCurrentRow().getAttribute("Department")==null)
               {
                   fieldNums = fieldNums+" Department ";                   
               }
              if(ptDtlVO.getCurrentRow().getAttribute("Currency")==null)
               {
                   fieldNums = fieldNums+" Currency ";            
               }            
               if(ptDtlVO.getCurrentRow().getAttribute("Amount")==null)
               {                
                   fieldNums = fieldNums+" Amount ";          
               }  
               pageContext.writeDiagnostics(this, "Inside validateFields Before Condition =  "+ptDtlVO.getCurrentRow().getAttribute("Amount"), 4);
               if(Float.parseFloat(ptDtlVO.getCurrentRow().getAttribute("Amount").toString())>=201)
               {                
                   pageContext.writeDiagnostics(this, "Inside AMount is greater than or Equal 200 ",4);
                   ValMsg = ValMsg+" Amount should be less than 200 : ";          
                   fieldNums = fieldNums+" Amount ";   
               }     
               if(ptDtlVO.getCurrentRow().getAttribute("BriefNarration")==null)
               {
                   fieldNums = fieldNums+" BriefNarration ";            
               }
//               if(ptDtlVO.getCurrentRow().getAttribute("AttachFile")==null)
//               {
//                   fieldNums = fieldNums+" AttachFile ";            
//               }
               throw new OAException("Errors: "+ValMsg+" Fields should be Entered :"+fieldNums,OAException.ERROR); 
           }
       }
    public void SetAttachmentCnt(OAPageContext pageContext,OAWebBean oawebbean){  

                pageContext.writeDiagnostics(this,"SET ATTACHMENT COUNT METHOD START",1);

                 pageContext.writeDiagnostics(this,"SET ATTACHMENT COUNT METHOD START",4);

               OAApplicationModule am = pageContext.getApplicationModule(oawebbean); 

                String amName = "";

                String objectivesAMName = "OAAttachmentsAM"; 

                String objectiveVOName = "FndAttachedDocumentsDomExtensionVO";

              //   String objectivesAMName = "InvoiceRequestAM"; 

              //   String objectiveVOName = "AttachmentExistsVO";

                String nestedAMArray[] = am.getApplicationModuleNames();   

                pageContext.writeDiagnostics(this,"Root AM=>"+am.getName() + " Child AMs=>"+ nestedAMArray.length,1);  

                OAApplicationModule  currentAM  = null;  

                OAViewObject vo = null;    

                currentAM =  am;   

                    for(int i = 0; i < nestedAMArray.length; i++){

                        amName = nestedAMArray[i]; 

                        currentAM  = (OAApplicationModule)am.findApplicationModule(amName);

                        if(!(amName.indexOf(objectivesAMName)==-1)){

                            String[] viewNames =  currentAM.getViewObjectNames();    

                             for (int j =0 ;j<viewNames.length ;j++ ){

                                  if(viewNames[j].indexOf(objectiveVOName) > 0){

                                     vo = (OAViewObject)currentAM.findViewObject(viewNames[j]);

                                     if(vo!=null){

                                        if(vo.getFetchedRowCount()>0){

                                            vo.first();

                                            while(vo.getCurrentRow()!=null){

                                                if(vo.getCurrentRow().getAttribute("DatatypeName")!=null){

                                                    String DatatypeName = vo.getCurrentRow().getAttribute("DatatypeName").toString();

                                                pageContext.writeDiagnostics(this,"DatatypeName--->"+DatatypeName,1);

                                                pageContext.writeDiagnostics(this,"DatatypeName--->"+DatatypeName,4);

                                                if(DatatypeName!=null){

                                                    if(DatatypeName.equalsIgnoreCase("File")){

                                                        AttachmentCnt = AttachmentCnt+1;                                            

                                                    }

                                                }

                                            }

                                            vo.next();

                                        }

                                    }

            //                             AttachmentCnt = vo.getFetchedRowCount();

                                     pageContext.writeDiagnostics(this,"AttachmentCnt--->"+AttachmentCnt,1);

                                     pageContext.writeDiagnostics(this,"AttachmentCnt--->"+AttachmentCnt,4);
                                    

                                 } 

                              }  

                          }

                    }      

                 }      

                 pageContext.writeDiagnostics(this,"SET ATTACHMENT COUNT METHOD END",1);

                 pageContext.writeDiagnostics(this,"SET ATTACHMENT COUNT METHOD END",4);

             }

}
