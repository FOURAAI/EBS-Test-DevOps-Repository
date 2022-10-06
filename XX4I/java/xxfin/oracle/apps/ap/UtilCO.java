package xxscm.oracle.apps.po;


import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.server.OAViewObjectImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAProcessingPage;

import com.sun.java.util.collections.HashMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.fnd.framework.webui.OADialogPage;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OADescriptiveFlexBean;
import oracle.apps.fnd.framework.webui.beans.OAKeyFlexBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.OAWebBeanAttachment;
import oracle.apps.fnd.framework.webui.beans.OAWebBeanContainer;
import oracle.apps.fnd.framework.webui.beans.OAWebBeanDataAttribute;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageLovInputBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;
import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;

import oracle.apps.fnd.framework.webui.beans.table.OAColumnBean;
import oracle.apps.fnd.framework.webui.beans.table.OASortableHeaderBean;

import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.UINode;
import oracle.cabo.ui.beans.form.FormElementBean;
import oracle.cabo.ui.data.DataObject;

import oracle.jbo.XMLInterface;

import oracle.xml.parser.v2.XMLNode;
import java.text.ParseException;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

 import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

public class UtilCO {

    /**
     * Method to execute View Object
     * @param vo View Object which will be executed
     */
    public void executeVO(OAViewObjectImpl vo, String whereClause, 
                          Object[] whereClauseParams) {

        vo.setWhereClause(null);
        vo.setWhereClause(whereClause);
        if (notNull(whereClauseParams)) {
            for (int i = 0; i < whereClauseParams.length; i++) {
                vo.setWhereClauseParam(i, whereClauseParams[i]);
            }
        }
        vo.executeQuery();
    }

    /**
     * Method to check if an object is null or not
     * @param obj -- object which has to be checked
     * @return  -- boolean true if not null, false if null
     */
    public boolean notNull(Object obj) {
        if (obj != null && !"".equals(obj)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used to write log information if logging is enabled.
     * @param am            -- The calling controller's Application module
     * @param logLevel      -- Log level
     * @param logMessage    -- Log message to write.
     */
    public void writeLog(OAApplicationModule am, int logLevel, 
                         String logMessage) {
        System.out.println(logMessage);
        if (am.getOADBTransaction().isLoggingEnabled(logLevel)) {
            am.getOADBTransaction().writeDiagnostics(this, logMessage, 
                                                     logLevel);
        }
    }

    /**
     * @param pageContext   -- the current page's  webBean
     * @param controller    -- the controller page's  controller for performing
     *                          validations
     * @param conciseMsg    -- Concise message to display in the page
     * @param detailedMsg   -- Detailed message to display in the page
     * @param processName   -- Process name to display in the page
     */
    public void callProcessingPage(OAPageContext pageContext, 
                                   String controller, String conciseMsg, 
                                   String detailedMsg, String processName) {
        OAProcessingPage page = new OAProcessingPage(controller);
        page.setConciseMessage(conciseMsg);
        page.setDetailedMessage(detailedMsg);
        page.setProcessName(processName);
        page.setRetainAMValue(true);
        pageContext.forwardToProcessingPage(page);
    }

    public static void callPage(OAPageContext pageContext, String pageDir, 
                                String CallPage, HashMap hmpmsg, 
                                boolean AMtf) {
        System.out.println("invoked DynHrReqCallPG");
        pageContext.setForwardURL(pageDir + CallPage, null, 
                                  OAWebBeanConstants.KEEP_MENU_CONTEXT, null, 
                                  hmpmsg, AMtf, "Y", 
                                  OAWebBeanConstants.IGNORE_MESSAGES);

    }

    public void confirmationDialog(OAPageContext pageContext, String descMsg, 
                                   String instrMsg, Hashtable formParams, 
                                   String btnPrefix) {

        OAException descMesg = new OAException(descMsg);
        OAException instrMesg = new OAException(instrMsg);
        OADialogPage dialogPage = 
            new OADialogPage(OAException.WARNING, descMesg, instrMesg, "", "");
        dialogPage.setFormParameters(formParams);
        dialogPage.setOkButtonToPost(true);
        dialogPage.setNoButtonToPost(true);
        dialogPage.setPostToCallingPage(true);
        dialogPage.setOkButtonItemName(btnPrefix + "Yes");
        dialogPage.setNoButtonItemName(btnPrefix + "No");
        pageContext.redirectToDialogPage(dialogPage);
    }


    public static void setPageReadOnly(OAPageContext pageContext, 
                                       OAWebBean webBean) {
        setViewOnlyRecursiveInternal(pageContext, webBean, false, 0);
    }

    private static void setViewOnlyRecursiveInternal(OAPageContext pageContext, 
                                                     OAWebBean webBean, 
                                                     boolean flag, int i) {
        if (webBean == null) {
            return;
        }
        if (webBean instanceof FormElementBean) {
            if (webBean instanceof OAWebBeanDataAttribute) {
                ((OAWebBeanDataAttribute)webBean).setRequired("no");
            }
            FormElementBean formelementbean = (FormElementBean)webBean;
            formelementbean.setReadOnly(true);
            
            if (!flag) {
                webBean.setStyleClass("OraDataText");
            }
            if (webBean instanceof OAMessageTextInputBean) {
                ((OAMessageTextInputBean)webBean).setTip(null);
            } else if (webBean instanceof OAMessageLovInputBean) {
                ((OAMessageLovInputBean)webBean).setTip(null);
            }
        } 
      if (webBean instanceof OADescriptiveFlexBean) {
            OADescriptiveFlexBean oadescriptiveflexbean = 
                (OADescriptiveFlexBean)webBean;
            oadescriptiveflexbean.setRequired("no");
            oadescriptiveflexbean.setReadOnly(true, true, true);
            if (!flag) {
                oadescriptiveflexbean.setStyleClass("OraDataText");
            }
        }
        
//        else if (webBean instanceof OADescriptiveFlexBean) {
//            OADescriptiveFlexBean oadescriptiveflexbean = 
//                (OADescriptiveFlexBean)webBean;
//            oadescriptiveflexbean.setRequired("no");
//            oadescriptiveflexbean.setReadOnly(true, true, true);
//            if (!flag) {
//                oadescriptiveflexbean.setStyleClass("OraDataText");
//            }
//        } 
        else if (webBean instanceof OAKeyFlexBean) {
            OAKeyFlexBean oakeyflexbean = (OAKeyFlexBean)webBean;
            oakeyflexbean.setRequired("no");
            oakeyflexbean.setReadOnly(true);
            if (!flag) {
                oakeyflexbean.setStyleClass("OraDataText");
            }
        } else if (webBean instanceof OAWebBeanAttachment) {
            OAWebBeanAttachment webBeanattachment = 
                (OAWebBeanAttachment)webBean;
            Dictionary adictionary[] = webBeanattachment.getEntityMappings();
            if (adictionary != null) {
                for (int k = 0; k < adictionary.length; k++) {
                    adictionary[k].put("insertAllowed", Boolean.FALSE);
                    adictionary[k].put("deleteAllowed", Boolean.FALSE);
                    adictionary[k].put("updateAllowed", Boolean.FALSE);
                }

            }
        } else if (webBean instanceof OAAdvancedTableBean) {
            OAAdvancedTableBean oaadvancedtablebean = 
                (OAAdvancedTableBean)webBean;
            UINode uinode = oaadvancedtablebean.getTableActions();
            if (uinode != null && (uinode instanceof OAWebBean)) {
                ((OAWebBean)uinode).setRendered(false);
            }
            UINode uinode1 = oaadvancedtablebean.getFooter();
            if (uinode1 != null && (uinode1 instanceof OAWebBean)) {
                ((OAWebBean)uinode1).setRendered(false);
            }
        } else if (webBean instanceof OASortableHeaderBean) {
            OASortableHeaderBean oasortableheaderbean = 
                (OASortableHeaderBean)webBean;
            oasortableheaderbean.setRequired("no");
        } else if ((webBean instanceof OAWebBeanContainer) && 
                   !(webBean instanceof OAPageButtonBarBean)) {
            boolean flag1 = false;
            if (flag || (webBean instanceof OAColumnBean)) {
                flag1 = true;
            }
            int j = webBean.getIndexedChildCount();
            for (int l = 0; l < j; l++) {
                UINode uinode2 = webBean.getIndexedChild(l);
                if (uinode2 instanceof OAWebBean) {
                    setViewOnlyRecursiveInternal(pageContext, 
                                                 (OAWebBean)uinode2, flag1, 
                                                 i + 1);
                }
            }

            Enumeration enumeration = webBean.getChildNames();
            if (enumeration != null) {
                do {
                    if (!enumeration.hasMoreElements()) {
                        break;
                    }
                    String s1 = (String)enumeration.nextElement();
                    UINode uinode3 = webBean.getNamedChild(s1);
                    if (uinode3 instanceof OAWebBean) {
                        setViewOnlyRecursiveInternal(pageContext, 
                                                     (OAWebBean)uinode3, flag1, 
                                                     i + 1);
                    }
                } while (true);
            }
        }
    }


    public void downloadReport(OAPageContext pageContext, OAWebBean webBean, 
                               String applShortCode, String templateShortCode, 
                               XMLNode xmlNode) {

        OAApplicationModule am = pageContext.getApplicationModule(webBean);
        writeLog(am, 6, "Print Button Invoked");
        DataObject sessionDictionary = 
            (DataObject)pageContext.getNamedDataObject("_SessionParameters");
        HttpServletResponse response = 
            (HttpServletResponse)sessionDictionary.selectValue(null, 
                                                               "HttpServletResponse");
        OADBTransactionImpl txn = (OADBTransactionImpl)am.getOADBTransaction();
        writeLog(am, 6, "Before Try");
        try {
            writeLog(am, 6, "Inside Try");
            ServletOutputStream os = response.getOutputStream();
            String contentDisposition = "attachment;filename=PrintPage.pdf";
            response.setHeader("Content-Disposition", contentDisposition);
            response.setContentType("application/pdf");
            writeLog(am, 6, "Response Default Set...");
            OAViewObject vo = (OAViewObject)am.findViewObject("HeaderEVO");
            vo.executeQuery();
            //XMLNode xmlNode = generateXML(vo);
            writeLog(am, 6, "AM Invoked...");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            xmlNode.print(outputStream);
            ByteArrayInputStream inputStream = 
                new ByteArrayInputStream(outputStream.toByteArray());
            ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
            writeLog(am, 6, 
                     "****************************************** XML Output Start ******************************************");
            writeLog(am, 6, "" + outputStream);
            writeLog(am, 6, 
                     "****************************************** XML Output End ******************************************");
            writeLog(am, 6, "Language : " + txn.getUserLocale().getLanguage());
            writeLog(am, 6, "Counttry: " + txn.getUserLocale().getCountry());
            TemplateHelper.processTemplate(txn.getAppsContext(), applShortCode, 
                                           templateShortCode, 
                                           txn.getUserLocale().getLanguage(), 
                                           txn.getUserLocale().getCountry(), 
                                           inputStream, 
                                           TemplateHelper.OUTPUT_TYPE_PDF, 
                                           null, pdfFile);
            byte[] b = pdfFile.toByteArray();
            response.setContentLength(b.length);
            os.write(b, 0, b.length);
            os.flush();
            os.close();
            pdfFile.flush();
            pdfFile.close();
        } catch (XDOException xe) {
            throw new OAException("XDOException:" + xe.getMessage());

        } catch (Exception e) {
            response.setContentType("text/html");
            throw new OAException(e.getMessage(), OAException.ERROR);

        }
        pageContext.setDocumentRendered(true);

    }

    public XMLNode generateXML(OAViewObject vo) {
        try {
            System.out.println("Inside AM Method..");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XMLNode xmlNode = 
                (XMLNode)vo.writeXML(4, XMLInterface.XML_OPT_ALL_ROWS);
            System.out.println("XML Node is : " + xmlNode.getText());
            ((XMLNode)vo.writeXML(4, 0L)).print(outputStream);

            return xmlNode;
        } catch (Exception e) {

            System.out.println("Exception: " + e.toString());
            throw new OAException(e.getMessage());
        }
    }
    
    public static Date stringToDate(String stringToConvert,
                                        String dateFormat) {
            SimpleDateFormat df = null;
            df = new SimpleDateFormat(dateFormat);
            Date dateVal = null;
            try {
                java.sql.Date sqlDate =
                    new java.sql.Date(df.parse(stringToConvert).getTime());
                System.out.println("sql date : " + sqlDate);
                            //dateVal = new oracle.jbo.domain.Date(sqlDate);
                System.out.println("Date value is : " + dateVal);
                    java.sql.Timestamp timeStampDate = new Timestamp(sqlDate.getTime());
                    System.out.println("2.Date value in Timestamp is : " + timeStampDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateVal;
        }
        
    public static oracle.jbo.domain.Date stringToDomainDate(String stringToConvert, String dateFormat) {
            DateFormat df = null;
            df = new SimpleDateFormat(dateFormat);
            oracle.jbo.domain.Date dateVal = null;
            try {
                java.sql.Date sqlDate =
                    new java.sql.Date(df.parse(stringToConvert).getTime());
                dateVal = new oracle.jbo.domain.Date(sqlDate);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new OAException("Error in StrintoDate Method : " +
                                      e.toString());
            }
            return dateVal;
        }
    public static String stringDate(String dateVal,String dateFormat,String toFormat) {
        String convertedDate=null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        SimpleDateFormat toformat = new SimpleDateFormat(toFormat);
        try {
            convertedDate=toformat.format(format.parse(dateVal));
        } catch (ParseException e) {
            // TODO
             e.printStackTrace();
             throw new OAException("Error in StrintoDate Method : " +
                                   e.toString());
        }
        return convertedDate;
    }
    
    public static Double stringDouble(String val) {
        Double d=Double.parseDouble(val);
        return d;
    }
}