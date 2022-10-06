/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.iou.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import xxfin.oracle.apps.ap.iou.server.iouAMImpl;
import xxfin.oracle.apps.ap.iou.server.iouHistVOImpl;
import xxfin.oracle.apps.ap.iou.server.iouVOImpl;

/**
 * Controller for ...
 */
public class iouUpdateCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   String iou_id ="";
  String Division = "";
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
    /*********************************************AM initialization************************************************************/  
    iouAMImpl    am    = (iouAMImpl)pageContext.getApplicationModule(webBean);
    /*********************************************AM initialization************************************************************/  

     System.out.println("Inside processRequest");

    /*********************************************VO initialization************************************************************/ 
    iouVOImpl   iouVO  = (iouVOImpl)am.findViewObject("iouVO");
    iouHistVOImpl histVO = (iouHistVOImpl)am.findViewObject("iouHistVO");

    /*********************************************VO initialization************************************************************/ 
       if(pageContext.getParameter("Piouid")!=null && pageContext.getParameter("Piouid") !="")
       {
        iou_id = pageContext.getParameter("Piouid").trim();
        System.out.println("Inside iou_id = "+iou_id);
        pageContext.writeDiagnostics(this,"Inside iou_id = "+iou_id,4); 
       }
      if(pageContext.getParameter("Div")!=null && pageContext.getParameter("Div") !="")
      {
       Division = pageContext.getParameter("Div").trim();
       System.out.println("Inside Division = "+Division);
       pageContext.writeDiagnostics(this,"Inside Division = "+Division,4); 
      }
    //     Division ="Admin";


     am.executeQuery(iouVO,"IOU_ID = '"+iou_id+"'",null);
     
     am.executeQuery(histVO,"IOU_ID = '"+iou_id+"'",null);
    
  }

  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   String diff_amt = "";
   String bill_amt = "";
   String EventName = null;
   String bill_flag ="";
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processFormRequest(pageContext, webBean);
    /*********************************************AM initialization************************************************************/  
    iouAMImpl    am    = (iouAMImpl)pageContext.getApplicationModule(webBean);
    /*********************************************AM initialization************************************************************/  

    /*********************************************VO initialization************************************************************/ 
    iouVOImpl   iouVO  = (iouVOImpl)am.findViewObject("iouVO");

    /*********************************************VO initialization************************************************************/ 
     /*********************************************EVENT Capturing ************************************************************/ 
      if(pageContext.getParameter(EVENT_PARAM) != null)
       {
        EventName = pageContext.getParameter(EVENT_PARAM).toString();
        pageContext.writeDiagnostics(this, "EventName-->" + EventName, 4);
        System.out.println("EventName--->" + EventName);
       }
     /*********************************************EVENT Capturing ************************************************************/
   
    
     if("Save".equals(EventName))
     {
     
      System.out.println("Inside Save");
      am.getOADBTransaction().commit();
      
       bill_flag = am.get_bill_flag(pageContext,webBean,am,iou_id);
      
      if(bill_flag.equals("Y")){
      
      throw new OAException("IOU Saved Successfully.",OAException.CONFIRMATION);
      }
      else
      {
        throw new OAException("Kindly attach bills and supporting documents to save.",OAException.ERROR);
      }
      
     }
     
      if("SaveClose".equals(EventName))
      {
       System.out.println("Inside SaveClose");
       am.getOADBTransaction().commit();
       
        bill_flag = am.get_bill_flag(pageContext,webBean,am,iou_id);
        
        if(bill_flag.equals("Y"))
        {
           am.Close_IOU(pageContext,webBean,am,iou_id);
           pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/iou/webui/iouSearchPG&Div="+Division,
                                               null,
                                               (byte)0,
                                               null,
                                               null,
                                               false,
                                               "N", 
                                               (byte)99);   
        }
        else
        {
          throw new OAException("Kindly attach bills and supporting documents to close the IOU.",OAException.ERROR);
        }                                   
                                           
      }
      
      if("BackBT".equals(EventName))
      {     
       System.out.println("Inside BackBT");
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
      
    
    
    if("Difference".equals(EventName))
    {     
    
     System.out.println("Inside Difference");
     
     try
     { bill_amt = pageContext.getParameter("BillAmount").toString();}
     catch (Exception e){bill_amt ="0";}
     
     if( !bill_amt.equals("0") &&  !bill_amt.equals(""))
     {
     diff_amt = am.get_diff(pageContext,webBean,am,iou_id,bill_amt);
     
     OAMessageStyledTextBean bill_amt_b = (OAMessageStyledTextBean)webBean.findChildRecursive("DiffAmount");
     bill_amt_b.setValue(pageContext,diff_amt);
     
     }
     
    }
     
    
  }

}
