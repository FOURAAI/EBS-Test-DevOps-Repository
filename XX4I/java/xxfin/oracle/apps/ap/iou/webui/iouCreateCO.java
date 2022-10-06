/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.iou.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.server.OAViewObjectImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageDateFieldBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.server.OAAttachmentsAMImpl;

import xxfin.oracle.apps.ap.iou.server.iouAMImpl;
import xxfin.oracle.apps.ap.iou.server.iouExchangeVOImpl;
import xxfin.oracle.apps.ap.iou.server.iouVOImpl;
import oracle.jbo.domain.Number;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import java.text.ParseException;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OADecimalValidater;
import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;

import oracle.cabo.ui.validate.Formatter;

/**
 * Controller for ...
 */
public class iouCreateCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   String Division = "";
   String iouid =null;
   int AttachmentCnt= -1;
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    /*********************************************AM initialization************************************************************/  
     iouAMImpl    am    = (iouAMImpl)pageContext.getApplicationModule(webBean);
    /*********************************************AM initialization************************************************************/  


    /*********************************************VO initialization************************************************************/ 
     iouVOImpl   iouVO  = (iouVOImpl)am.findViewObject("iouVO");
    /*********************************************VO initialization************************************************************/ 

     if(pageContext.getParameter("Div")!=null && pageContext.getParameter("Div") !="")
     {
      Division = pageContext.getParameter("Div").trim();
      System.out.println("Inside Division = "+Division);
      pageContext.writeDiagnostics(this,"Inside Division = "+Division,4); 
     }
      pageContext.writeDiagnostics(this,"Inside pageContext.getSessionValue(\"iouid\") = "+pageContext.getSessionValue("iouid"),4); 
      if(pageContext.getSessionValue("iouid")!=null && pageContext.getSessionValue("iouid") !="")
      {
       iouid = (String)pageContext.getSessionValue("iouid");
       System.out.println("Inside iouid = "+iouid);
       pageContext.writeDiagnostics(this,"Inside iouid = "+iouid,4); 
      }
//    Division ="Logistics";
    
    System.out.println("Inside Create page");
    if (iouVO.getCurrentRow() == null && iouid == null)
    {
     iouid = am.create_row(Division,pageContext,am);
    }
      
//      Formatter formatter =         new OADecimalValidater("#,##0.000;#,##0.000","#,##0.000;#,##0.000");
//      OAMessageStyledTextBean sbean4 = (OAMessageStyledTextBean)webBean.findIndexedChildRecursive("AmountOmr"); 
//      sbean4.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
  }

  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   String EventName   = "";
   String GivenDate   = "";
   String Currency    = "";
   String ExRateType  = "";
   String ExRateDate  = "";
   String ExchangeRate = "";
   String AmountinOMR  = "";
   String Amount ="";
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processFormRequest(pageContext, webBean);
    /*********************************************AM initialization************************************************************/  
     iouAMImpl    am    = (iouAMImpl)pageContext.getApplicationModule(webBean);
    /*********************************************AM initialization************************************************************/  


    /*********************************************VO initialization************************************************************/ 
     iouVOImpl   iouVO      = (iouVOImpl)am.findViewObject("iouVO");
     iouExchangeVOImpl ExVO = (iouExchangeVOImpl)am.findViewObject("iouExchangeVO");
    /*********************************************VO initialization************************************************************/ 

     /*********************************************EVENT Capturing ************************************************************/ 
      if(pageContext.getParameter(EVENT_PARAM) != null)
       {
        EventName = pageContext.getParameter(EVENT_PARAM).toString();
        pageContext.writeDiagnostics(this, "EventName-->" + EventName, 4);
        System.out.println("EventName--->" + EventName);
      
     /*********************************************EVENT Capturing ************************************************************/



      if("Cancel".equals(EventName))
      {     
       System.out.println("Inside Cancel");
       am.Cancel_IOU(pageContext,webBean,am,iouid);
       pageContext.removeSessionValue("iouid");
//       am.getOADBTransaction().rollback();
       pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/iou/webui/iouSearchPG&Div="+Division,
                                           null,
                                           (byte)0,
                                           null,
                                           null,
                                           false,
                                           "N", 
                                           (byte)99);
      
      
      }
      
      if("Amount".equals(EventName))
      {
        System.out.println("Inside Amount");
        
        Exchange_method(am,iouVO,ExVO,pageContext,webBean);       
        
      }
    if (pageContext.isLovEvent()) 
     {
          String lovInputSourceId = pageContext.getLovInputSourceId();
          System.out.println("lovInputSourceId = "+lovInputSourceId);
          
  
              if("CurrencyLOV".equals(lovInputSourceId)) 
              {
                System.out.println("Inside CurrencyLOV"); 
                Currency  = iouVO.getCurrentRow().getAttribute("Currency").toString();
                
                if (Currency.equals("OMR"))
                {
                  iouVO.getCurrentRow().setAttribute("ReadOnlyT",Boolean.TRUE);
                }
                else
                {
                  iouVO.getCurrentRow().setAttribute("ReadOnlyT",Boolean.FALSE);
                }
//                Exchange_method(am,iouVO,ExVO,pageContext,webBean);
              }
              
             if("ExRateType".equals(lovInputSourceId)) 
             {
             
               Exchange_method(am,iouVO,ExVO,pageContext,webBean);
              
             }
              
      
      }
      
      if("Submit".equals(EventName))
      {
      
       System.out.println("Inside Submit");
        SetAttachmentCnt( pageContext, webBean);  

        if (AttachmentCnt == -1)
        {
        throw new OAException("Please attach staff signed copy of money received acknowledgement.",OAException.ERROR);
        }
       else
       {
         am.getOADBTransaction().commit();
         am.generate_num(pageContext,webBean,am,iouid);
         am.call_workflow(pageContext,webBean,am,iouid);
         am.getOADBTransaction().commit();
         pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/iou/webui/iouSearchPG&Div="+Division,
                                             null,
                                             (byte)0,
                                             null,
                                             null,
                                             false,
                                             "N", 
                                             (byte)99);   
       }                                   
      }
      
      
    if("Save".equals(EventName))
    {
    
     System.out.println("Inside Save");
     Exchange_method(am,iouVO,ExVO,pageContext,webBean);
//     validateAttachment(am);
     SetAttachmentCnt( pageContext, webBean);  

     if (AttachmentCnt == -1) 
     {
      throw new OAException("Please attach staff signed copy of money received acknowledgement.",OAException.ERROR);
     }
     else
     {
       am.getOADBTransaction().commit();
       am.generate_num(pageContext,webBean,am,iouid);
       throw new OAException("IOU Saved Successfully.Kindly submit to initiate the Transaction.",OAException.CONFIRMATION);
     }
     
    }

    }
  }
  
  public void Exchange_method(iouAMImpl am, iouVOImpl iouVO,OAViewObject ExVO, OAPageContext pageContext, OAWebBean webBean)
  {
      String Flag = "";
      String ExRDate ="";
      String ExRType ="";
      try{GivenDate = pageContext.getParameter("IOUGivenDate").toString();}
      catch (Exception e){GivenDate = null;}
      

      try{
      ExRDate = iouVO.getCurrentRow().getAttribute("ExchangeRateDate").toString();
//          ExRDate = pageContext.getParameter("ExRateDate").toString();
          }
      catch(Exception e){ExRDate =null;}
      
      try{ExRType = iouVO.getCurrentRow().getAttribute("ExchangeRateType").toString();}
      catch(Exception e){ExRType =null;}
      
      try{Currency  = pageContext.getParameter("CurrencyLOV").toString();}
      catch (Exception e){Currency =null;}
      
      try{Amount  = pageContext.getParameter("Amount").toString();}
      catch (Exception e){Amount =null;}
      
      System.out.println("GivenDate = "+GivenDate);
      System.out.println("Currency = "+Currency);
      System.out.println("ExRDate = "+ExRDate);
      System.out.println("ExRType = "+ExRType);
      
      if (ExRDate !=null && Currency !=null && Amount!=null && ExRType !=null && !ExRDate.equals("") && !Currency.equals("") && !Amount.equals("") && !ExRType.equals(""))
      {     
          ExVO.setWhereClause(null);
          ExVO.setWhereClauseParams(null);
          
          ExVO.setWhereClauseParam(0,ExRDate);
          ExVO.setWhereClauseParam(1,Currency);
          ExVO.setWhereClauseParam(2,Amount);
          ExVO.setWhereClauseParam(3,ExRType);
          ExVO.executeQuery();
          
          ExVO.first();
          
//          ExRateType   = ExVO.getCurrentRow().getAttribute("ConversionType").toString();
          ExchangeRate = ExVO.getCurrentRow().getAttribute("ConversionRate").toString();
          AmountinOMR  = ExVO.getCurrentRow().getAttribute("Amt").toString();
          
          Flag = am.get_flag(pageContext,webBean,am,Division,AmountinOMR);
          
//          int OMRAmount = Integer.parseInt(AmountinOMR);
        if (Flag.equals("Y"))
          {
//            OAMessageStyledTextBean ExRateTypeB = (OAMessageStyledTextBean)webBean.findChildRecursive("ExRateType");
//            ExRateTypeB.setValue(pageContext,ExRateType);
            
            OAMessageStyledTextBean ExchangeRateB = (OAMessageStyledTextBean)webBean.findChildRecursive("ExchangeRate");
            ExchangeRateB.setValue(pageContext,ExchangeRate);
            
            OAMessageStyledTextBean AmountOmrB = (OAMessageStyledTextBean)webBean.findChildRecursive("AmountOmr");
            AmountOmrB.setValue(pageContext,AmountinOMR);
         /*             
            //defining format of date
            SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
            Date sqlDate =null;
          
          try
          {
            sqlDate = new Date(f.parse(GivenDate).getTime());
          } catch (ParseException e)
          {
            System.out.println(e);
          }       
          iouVO.getCurrentRow().setAttribute("ExchangeRateDate",sqlDate);
          */
        }
        else
        {
          throw new OAException("IOU Amount exceeded 200 OMR.Kindly reduce and try again.",OAException.ERROR);
        }
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
