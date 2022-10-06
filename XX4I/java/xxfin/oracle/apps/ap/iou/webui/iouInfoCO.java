/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.iou.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import xxfin.oracle.apps.ap.iou.server.iouAMImpl;
import xxfin.oracle.apps.ap.iou.server.iouHistVOImpl;
import xxfin.oracle.apps.ap.iou.server.iouVOImpl;

/**
 * Controller for ...
 */
public class iouInfoCO extends OAControllerImpl
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
   String Division="";
   String from  ="";
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
      
      if(pageContext.getParameter("from")!=null && pageContext.getParameter("from") !="")
      {
       from = pageContext.getParameter("from").trim();
       System.out.println("Inside from = "+from);
       pageContext.writeDiagnostics(this,"Inside from = "+from,4); 
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
   String EventName = null;
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
    
    
    
     if("BackBT".equals(EventName))
     {     
      System.out.println("Inside BackBT");
     //       am.getOADBTransaction().rollback();
     
      if(from.equals("SU"))
       {
         pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/iou/webui/iouSuperUserPG",
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
    
  }

}
