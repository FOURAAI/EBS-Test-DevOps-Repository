// 
// Decompiled by Procyon v0.5.36
// 
package xxscm.oracle.apps.po;

import oracle.apps.fnd.framework.webui.OADialogPage;
import com.sun.java.util.collections.HashMap;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.apps.fnd.framework.server.common.OAApplicationModule;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import com.sun.java.util.collections.List;
import oracle.jbo.AttributeDef;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import oracle.apps.fnd.framework.server.OAViewRowImpl;
import javax.servlet.http.HttpServletResponse;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonClass
{
    public static String EMAIL_PATTERN;
    public static String ALPHANUM_PATTERN;
    public static String ID_PATTERN;
    public static String PHONE_PATTERN;
    public static String ALPHA_ALL;
    public static String ALPHA_UC;
    public static String ALPHA_LC;
    public static String NUMBER_ONLY;
    public static final long ONE_HOUR = 3600000L;
    
    public static void main(final String[] args) {
        final CommonClass commonClass = new CommonClass();
    }
    
    public boolean validatePattern(final String Value, final String ValuePattern) {
        final Pattern pattern = Pattern.compile(ValuePattern);
        final Matcher matcher = pattern.matcher(Value);
        return matcher.matches();
    }
    
    public boolean isGtSysdate(final Date Value) {
        final Date sd = new Date();
        return Value.after(sd);
    }
    
    public boolean isLtSysdate(final Date Value) {
        final Date sd = new Date();
        return Value.before(sd);
    }
    
    public int compareDate(final Date dt1, final Date dt2) {
        return dt1.compareTo(dt2);
    }
    
    public long daysBetween(final Date d1, final Date d2) {
        System.out.println("daysBetween: d2 - " + d2.getTime());
        System.out.println("daysBetween: d1 - " + d1.getTime());
        final long days = (d2.getTime() - d1.getTime() + 3600000L) / 86400000L;
        System.out.println("daysBetween: days - " + days);
        return days;
    }
    
    public boolean isNegative(final String Value) {
        final int intValue = Integer.parseInt(Value);
        return intValue < 0;
    }
    
    public boolean isPositive(final String Value) {
        final int intValue = Integer.parseInt(Value);
        return intValue > 0;
    }
    
    public boolean isZero(final String Value) {
        final int intValue = Integer.parseInt(Value);
        return intValue == 0;
    }
    
    public <T> T nvl(final T a, final T b) {
        return (a == null) ? b : a;
    }
    
    public void export2Excel(final OAPageContext pageContext, final String viewObj, String fileName, final String maxSize, final String[] attrList, final String[] attrHdr) {
        final OAViewObject eVO = (OAViewObject)pageContext.getRootApplicationModule().findViewObject(viewObj);
        System.out.println("@Common Class - export2Excel");
        if (eVO == null) {
            throw new OAException("Could not find View Object Instance " + viewObj + " in root AM...");
        }
        if (eVO.getRowCount() == 0) {
            throw new OAException("There is no data to export...");
        }
        if (fileName == null || "".equals(fileName)) {
            fileName = "Export";
        }
        final HttpServletResponse response = (HttpServletResponse)pageContext.getRenderingContext().getServletResponse();
        response.setContentType("application/text");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");
        ServletOutputStream sOs = null;
        try {
            sOs = response.getOutputStream();
            int j = 0;
            int k = 0;
            boolean bb = true;
            System.out.println("@Common Class - export2Excel - inside try block");
            if (maxSize == null || "".equals(maxSize)) {
                k = Integer.parseInt(pageContext.getProfile("VO_MAX_FETCH_SIZE"));
                bb = false;
            }
            else if ("MAX".equals(maxSize)) {
                bb = true;
            }
            else {
                k = Integer.parseInt(maxSize);
                bb = false;
            }
            System.out.println("@Common Class - export2Excel - Exporting Header");
            final StringBuffer hdrBuf = new StringBuffer();
            if (attrHdr != null) {
                for (int i = 0; i < attrHdr.length; ++i) {
                    hdrBuf.append("\"" + attrHdr[i] + "\"");
                    hdrBuf.append(",");
                }
            }
            final String header_row = hdrBuf.toString();
            sOs.println(header_row);
            final AttributeDef[] a = eVO.getAttributeDefs();
            System.out.println("@Common Class - export2Excel - Exporting Data");
            int l = 0;
            for (OAViewRowImpl row = (OAViewRowImpl)eVO.first(); row != null; row = (OAViewRowImpl)eVO.next()) {
                ++j;
                final StringBuffer b = new StringBuffer();
                for (int u = 0; u < attrList.length; ++u) {
                    boolean appendYes = false;
                    for (l = 0; l < eVO.getAttributeCount(); ++l) {
                        if (a[l].getName().equals(attrList[u])) {
                            appendYes = true;
                            break;
                        }
                    }
                    if (appendYes) {
                        final Object o = row.getAttribute(l);
                        if (o != null) {
                            if (o.getClass().equals(Class.forName("oracle.jbo.domain.Date"))) {
                                final oracle.jbo.domain.Date dt = (oracle.jbo.domain.Date)o;
                                final java.sql.Date ts = dt.dateValue();
                                final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                final String convertedDateString = displayDateFormat.format(ts);
                                b.append("\"" + convertedDateString + "\"");
                            }
                            else {
                                b.append("\"" + o.toString() + "\"");
                            }
                        }
                        else {
                            b.append("\"\"");
                        }
                        b.append(",");
                    }
                }
                final String final_row = b.toString();
                sOs.println(final_row);
                if (!bb && j == k) {
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new OAException("Unexpected Exception occured.Exception Details :" + e.toString());
        }
        finally {
            pageContext.setDocumentRendered(false);
            try {
                sOs.flush();
                sOs.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                throw new OAException("Unexpected Exception occured.Exception Details :" + ex.toString());
            }
        }
        pageContext.setDocumentRendered(false);
        try {
            sOs.flush();
            sOs.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
            throw new OAException("Unexpected Exception occured.Exception Details :" + e2.toString());
        }
    }
    
    public void ExecuteQuery(final OAViewObject VO, final String WhereClause, final List param) {
        VO.setWhereClause((String)null);
        VO.setWhereClause(WhereClause);
        VO.setWhereClauseParams((Object[])null);
        for (int i = 0; i < param.size(); ++i) {
            System.out.println("Params-->" + i + "--->" + param.get(i));
            VO.setWhereClauseParam(i, param.get(i));
        }
        VO.executeQuery();
        VO.first();
    }
    
    public void saveRequest(final OAPageContext pageContext, final OAWebBean webBean, final OAApplicationModule AM, final String RequestId) {
        final String callStr = "{ call XXHLP_COMMON_PKG.SAVE_REQUEST(:1) }";
        try {
            final OracleCallableStatement callable = (OracleCallableStatement)AM.getOADBTransaction().createCallableStatement(callStr, 1);
            callable.setInt(1, Integer.parseInt(RequestId));
            callable.execute();
            callable.close();
        }
        catch (SQLException sqle) {
            throw OAException.wrapperException((Exception)sqle);
        }
        catch (OAException e) {
            throw OAException.wrapperException((Exception)e);
        }
    }
    
    public void cancelRequest(final OAPageContext pageContext, final OAWebBean webBean, final OAApplicationModule AM, final String RequestId) {
        final String callStr = "{ call XXHLP_COMMON_PKG.CANCEL_REQUEST(:1) }";
        try {
            final OracleCallableStatement callable = (OracleCallableStatement)AM.getOADBTransaction().createCallableStatement(callStr, 1);
            callable.setInt(1, Integer.parseInt(RequestId));
            callable.execute();
            callable.close();
        }
        catch (SQLException sqle) {
            throw OAException.wrapperException((Exception)sqle);
        }
        catch (OAException e) {
            throw OAException.wrapperException((Exception)e);
        }
    }
    
    public void StartWorkflow(final OAPageContext pageContext, final OAWebBean webBean, final OAApplicationModule AM, final String RequestId) {
        final String callStr = "{ call XXHLP_WF_PKG.START_MAIN_PROCESS(:1,:2,:3) }";
        String retcode = null;
        String errbuf = null;
        try {
            final OracleCallableStatement callable = (OracleCallableStatement)AM.getOADBTransaction().createCallableStatement(callStr, 1);
            callable.setInt(1, Integer.parseInt(RequestId));
            callable.registerOutParameter(2, 12);
            callable.registerOutParameter(3, 12);
            callable.execute();
            retcode = callable.getString(2);
            errbuf = callable.getString(3);
            callable.close();
        }
        catch (SQLException sqle) {
            throw OAException.wrapperException((Exception)sqle);
        }
        catch (OAException e) {
            throw OAException.wrapperException((Exception)e);
        }
    }
    
    public void ContinueWorkflow(final OAPageContext pageContext, final OAWebBean webBean, final OAApplicationModule AM, final String RequestId, final String Response, final String HrProcId) {
        final String callStr = "{ call XXHLP_WF_PKG.OAF_CALLING_PROC(:1,:2,:3,:4,:5) }";
        String retcode = null;
        String errbuf = null;
        try {
            final OracleCallableStatement callable = (OracleCallableStatement)AM.getOADBTransaction().createCallableStatement(callStr, 1);
            callable.setInt(1, Integer.parseInt(RequestId));
            callable.setString(2, Response);
            callable.setString(3, HrProcId);
            callable.registerOutParameter(4, 12);
            callable.registerOutParameter(5, 12);
            callable.execute();
            retcode = callable.getString(4);
            errbuf = callable.getString(5);
            callable.close();
        }
        catch (SQLException sqle) {
            throw OAException.wrapperException((Exception)sqle);
        }
        catch (OAException e) {
            throw OAException.wrapperException((Exception)e);
        }
    }
    
    public void NavigateToEmpSearchPG(final OAPageContext pageContext, final OAWebBean webBean) {
        pageContext.setForwardURL("OA.jsp?page=/xxhlp/oracle/apps/xxhlp/common/srcreq/webui/hrreqPG&Role=EMP", (String)null, (byte)0, (String)null, (HashMap)null, false, "Y", (byte)99);
    }
    
    public void NavigateToMgrSearchPG(final OAPageContext pageContext, final OAWebBean webBean) {
        pageContext.setForwardURL("OA.jsp?page=/xxhlp/oracle/apps/xxhlp/common/srcreq/webui/hrreqPG&Role=MANAGER", (String)null, (byte)0, (String)null, (HashMap)null, false, "Y", (byte)99);
    }
    
    public void CommitTrx(final OAApplicationModule AM) {
        AM.getOADBTransaction().commit();
    }
    
    public void RollbackTrx(final OAApplicationModule AM) {
        AM.getOADBTransaction().rollback();
    }
    
    public void DialogPage(final OAPageContext pageContext, final OAException Exception, final String OkButonName) {
        final OADialogPage dialogpage = new OADialogPage((byte)1, Exception, (OAException)null, "", "");
        dialogpage.setOkButtonLabel("Yes");
        dialogpage.setNoButtonLabel("No");
        dialogpage.setOkButtonItemName(OkButonName);
        dialogpage.setOkButtonToPost(true);
        dialogpage.setNoButtonToPost(true);
        dialogpage.setPostToCallingPage(true);
        pageContext.redirectToDialogPage(dialogpage);
    }
    
    static {
        CommonClass.EMAIL_PATTERN = "^[A-Za-z0-9-_\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CommonClass.ALPHANUM_PATTERN = "^([A-Z0-9])*$";
        CommonClass.ID_PATTERN = "^([A-Za-z0-9-_.])*$";
        CommonClass.PHONE_PATTERN = "^([0-9+-., \t])*$";
        CommonClass.ALPHA_ALL = "^([A-Za-z \t])*$";
        CommonClass.ALPHA_UC = "^([A-Z])*$";
        CommonClass.ALPHA_LC = "^([a-z])*$";
        CommonClass.NUMBER_ONLY = "^([0-9])*$";
    }
}
