/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxfin.oracle.apps.ap.pettycash.popup.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

/**
 * Controller for ...
 */
public class PtyCshWFPopupCO extends OAControllerImpl
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
      String PPtyCshHdrId; 
      PPtyCshHdrId = pageContext.getParameter("param1");
      OAApplicationModule am=null;
      am=(OAApplicationModule)pageContext.getApplicationModule(webBean);
      OAViewObject ptHdrVO= null;
      ptHdrVO=(OAViewObject)am.findViewObject("PtCshWfHistVO1");
      
      ptHdrVO.setWhereClause(" ptycsh_hdr_id = "+PPtyCshHdrId);
      pageContext.writeDiagnostics(this, "Inside PtCshWFCO : Inside ReadOnly Mode and Disabling the Buttons"+ptHdrVO.getQuery(), 4);
      ptHdrVO.setOrderByClause(" submission_date asc ");
      ptHdrVO.executeQuery();      
      ptHdrVO.first();
      
    
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
  }

}
