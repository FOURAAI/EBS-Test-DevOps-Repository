package xxfin.oracle.apps.ap.pettycash.lov.server;

import oracle.apps.fnd.framework.server.OAViewObjectImpl;

import oracle.jbo.domain.Date;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class IOUAmountVOImpl extends OAViewObjectImpl {
    /**This is the default constructor (do not remove)
     */
    public IOUAmountVOImpl() {
    }


    /**Gets the bind variable value for p_div
     */
    public String getp_div() {
        return (String)getNamedWhereClauseParam("p_div");
    }

    /**Sets <code>value</code> for bind variable p_div
     */
    public void setp_div(String value) {
        setNamedWhereClauseParam("p_div", value);
    }
}