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
import xxfin.oracle.apps.ap.iou.server.iouVOImpl;

/**
 * Controller for ...
 */
public class iouSuperUserCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
    /*********************************************AM initialization************************************************************/  
    iouAMImpl    am    = (iouAMImpl)pageContext.getApplicationModule(webBean);
    /*********************************************AM initialization************************************************************/  

     System.out.println("Inside processRequest");

    /*********************************************VO initialization************************************************************/ 
    iouVOImpl   iouVO  = (iouVOImpl)am.findViewObject("iouVO");

    /*********************************************VO initialization************************************************************/ 



     am.executeQuery(iouVO,null,null);
    
  }

  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
    String EventName = "";
    String per_id = "";
    String Status ="";
    //   String division="";
    String whereclause ="1=1";
    String Division = "";
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


      if("Search".equals(EventName))
      {
        System.out.println("Inside Search");
        whereclause ="1=1";
        
        per_id     = pageContext.getParameter("Eid");
        Status     = pageContext.getParameter("Status");
        Division   = pageContext.getParameter("division");
        
        System.out.println("Inside Search per_id = "+per_id);
        
        if(!per_id.equals(null) && !per_id.equals(""))
        {
          whereclause = whereclause + "and PERSON_ID = "+per_id;
        } 
        if(!Status.equals(null) && !Status.equals(""))
        {
          whereclause = whereclause + "and STATUS ='"+Status+"'";
        }
        if(!Division.equals(null) && !Division.equals(""))
        {
          whereclause = whereclause + "and DIVISION = '"+Division+"'";
        }
        
        
        /*if(whereclause.equals("1=1"))
        {
         whereclause = "1=1";
        }
        else
        {
         whereclause = whereclause.substring(8);
        }
        */
      
       am.executeQuery(iouVO,whereclause,null);
      
      }


      if("Clear".equals(EventName))
      {
        System.out.println("Inside Clear");
    //        pageContext.forwardImmediatelyToCurrentPage(null, false, "N");
        pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/iou/webui/iouSuperUserPG",
                                   null,
                                   (byte)0,
                                   null,
                                   null,
                                   false,
                                   "N", 
                                   (byte)99);
      
      }
      


    
    
    if("InfoBT".equals(EventName))
    {
      System.out.println("Inside InfoBT");
    
      pageContext.setForwardURL("OA.jsp?page=/xxfin/oracle/apps/ap/iou/webui/iouInfoPG&Div="+Division,
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
