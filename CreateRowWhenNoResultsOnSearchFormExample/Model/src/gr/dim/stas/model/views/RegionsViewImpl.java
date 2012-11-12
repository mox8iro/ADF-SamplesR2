package gr.dim.stas.model.views;

import java.sql.ResultSet;

import oracle.jbo.AttributeList;
import oracle.jbo.Row;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Nov 13 14:00:04 CET 2011
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class RegionsViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public RegionsViewImpl() {
    }

    /**
     * executeQueryForCollection - overridden for custom java data source support.
     */
    protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
        System.out.println("printing parameters before executing...");
//        printParamValues(params);
        super.executeQueryForCollection(qc, params, noUserParams);
//        int count = getFetchedRowCount();
//        if(count==0){
//            Row createRow = createRow();
//            insertRow(createRow);
//            setCurrentRow(createRow);
//        }
//        System.out.println("executeQuery for Collection done.");
    
    }
//    public void printParamValues(Object[] params){
//        if(params!=null && params.length>=1){
//        for (Object param : params) {  
//            Object[] nameValue = (Object[])param;  
//            String name = (String)nameValue[0];  
//           System.out.println("parameter: "+name+"  =   "+nameValue[1]);
//        }  
//        }
//    }
    
    private Object getParamValue(String varName, Object[] params) {  
      
                for (Object param : params) {  
                    Object[] nameValue = (Object[])param;  
                    String name = (String)nameValue[0];  
                    if (name.equals(varName)) {  
                        return nameValue[1];  
                    }  
                }  
                
                return null;
    }  

    /**
     * hasNextForCollection - overridden for custom java data source support.
     */
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - overridden for custom java data source support.
     */
    protected ViewRowImpl createRowFromResultSet(Object qc, ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - overridden for custom java data source support.
     */
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }

    public Row createAndInitRow(AttributeList attributeList) {
        System.out.println("createAndInitRow(attributeList)");
        return super.createAndInitRow(attributeList);
    }

    protected ViewRowImpl createAndInitRowForCollection(Object object, AttributeList attributeList) {
        System.out.println("createAndInitRowForCollection(object,attributeList)");
        return super.createAndInitRowForCollection(object, attributeList);
    }

    protected void create() {
        System.out.println("create()");
        super.create();
    }
}
