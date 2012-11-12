package gr.dim.stas.backing;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.el.ELContext;

import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class IndexBackingBean {
    private RichPanelBox rightPanelBox;
    private RichPanelBox leftPanelBox;

    public IndexBackingBean() {
    }
    
   /**
     *
     * @return
     */
   public ELContext getELContext(){
        FacesContext facesCtx = FacesContext.getCurrentInstance();  
        ELContext elContext = facesCtx.getELContext();  
       return elContext;  
    }
    
    
    
    /**
     * Finds the DepartmentId of the landing Employees table.
     * @param dropEvent
     * @return
     */
    public  Row getLandingDepartmentRow(DropEvent dropEvent){
        
        RichTable landTable=(RichTable)dropEvent.getDropComponent();
        // need to get the new DepartmentId.
        // we cannot just find an employee row here because there might be not employees in this table.
        // so we need to find the Departments Iterator of this employees table.
        // this is a generic approach and the reason is that I did not want to put hardcoded stuff..
         
        CollectionModel theCollectionModel=(CollectionModel)landTable.getValue(); 
        JUCtrlHierBinding tb = (JUCtrlHierBinding)theCollectionModel.getWrappedData();
        DCIteratorBinding dc = tb.getDCIteratorBinding();
        //ok, now we have the DC Iterator binding.
        ViewObject object = dc.getViewObject();
        RowSetIterator[] iterator = object.getMasterRowSetIterators();
        
        for(int i=0; i<iterator.length; i++){
            RowSetIterator rsi=iterator[i];
            System.out.println("name of the master iterator:  "+rsi.getName());
            //get the current row. It is the new Department row
            Row currentRow = rsi.getCurrentRow();
            
            return currentRow;

        }        
        
        return null;
    }
    
    /**
     * Helper method that updates the DepId of the current Employee.
     * @param operName
     * @param newDepId
     */
    public void updateEmps(String operName, Integer newDepId){
        OperationBinding operation = ADFUtils.getDCBindingContainer().getOperationBinding(operName);
        Map map = operation.getParamsMap();
        map.put("depId",newDepId);
        operation.execute();

    }
    
/**
     * This is the dropListener for right employees.
     * @param dropEvent
     * @return
     */
    public DnDAction emps2DropListener(DropEvent dropEvent) {
        // Add event code here...
        System.out.println("just droping to employees Right");
        
        // get the landing Department row
        Row newDepRow= getLandingDepartmentRow(dropEvent);
        
        //get the drag source 
        RichTable table = (RichTable) dropEvent.getDragComponent();
        //determine the rows that are dragged over
        Transferable t = dropEvent.getTransferable();
         //when looking for data, use the same discriminator as defined 
        //on the drag source
        DataFlavor<RowKeySet> df =
             DataFlavor.getDataFlavor(RowKeySet.class, "rowmove");
         RowKeySet rks = t.getData(df);
        Iterator iter = rks.iterator();
        
        
        while (iter.hasNext()) {
          //get next selected row key
           List key = (List)iter.next();
          //make row current so we can access it            
           table.setRowKey(key);
        //the table model represents its row by the ADF binding class,
        //which is JUCtrlHierNodeBinding
           JUCtrlHierNodeBinding rowBinding = 
                              (JUCtrlHierNodeBinding) table.getRowData();
            Row row = (Row) rowBinding.getRow();
            Object oldDepId = row.getAttribute("DepartmentId");
            System.out.println("this Employee has id "+ row.getAttribute("EmployeeId") +"department Id:  "+ row.getAttribute("DepartmentId"));
     
            
            //compare DepIds
              Object newDepId = newDepRow.getAttribute("DepartmentId");
            if(newDepId!=null && !newDepId.equals(oldDepId)){
                
                //update the id
                //using the view object.
                Integer depInt=Integer.parseInt(newDepId.toString());
                
//                     updateEmps("updateDepartmentIdRight", depInt);
                row.setAttribute("DepartmentId", depInt);
                
            }
            else{
                //raise message here that there is no point in droping to the same or no department.
                return DnDAction.NONE;
            }
            
        }
      
        ADFUtils.getDCBindingContainer().getOperationBinding("executeEmpsRight").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getRightPanelBox());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getLeftPanelBox());
        
        return  DnDAction.MOVE;
    }

/**
     * This is the drop listener for left employees.
     * @param dropEvent
     * @return
     */
    public DnDAction emps1DropListener(DropEvent dropEvent) {
        // Add event code here...
        
        System.out.println("just droping to employees Left");
        
        //get the drag source 
        RichTable table = (RichTable) dropEvent.getDragComponent();
        //determine the rows that are dragged over
        Transferable t = dropEvent.getTransferable();
         //when looking for data, use the same discriminator as defined 
        //on the drag source
        DataFlavor<RowKeySet> df =
             DataFlavor.getDataFlavor(RowKeySet.class, "rowmove");
         RowKeySet rks = t.getData(df);
        Iterator iter = rks.iterator();
        //get the landing department
        Row newDepRow= getLandingDepartmentRow(dropEvent);
        
        while (iter.hasNext()) {
          //get next selected row key
           List key = (List)iter.next();
          //make row current so we can access it            
           table.setRowKey(key);
        //the table model represents its row by the ADF binding class,
        //which is JUCtrlHierNodeBinding
           JUCtrlHierNodeBinding rowBinding = 
                              (JUCtrlHierNodeBinding) table.getRowData();
           Row row = (Row) rowBinding.getRow();
            Object oldDepId = row.getAttribute("DepartmentId");

            System.out.println("this Employee has id "+ row.getAttribute("EmployeeId") +"department Id:  "+ oldDepId);
          

          //compare DepIds
            Object newDepId = newDepRow.getAttribute("DepartmentId");

            if(newDepId!=null && !newDepId.equals(oldDepId)){
                
                // update the id
                // using View Object.
               Integer depInt=Integer.parseInt(newDepId.toString());
            
//                    updateEmps("updateDepartmentIdLeft", depInt);
            
                row.setAttribute("DepartmentId", depInt);
                
                //refresh department.
               
                
            }
            else{
                //raise message here that there is no point in droping to the same or no department.
                return DnDAction.NONE;
            }
        
        }
        //update left employees.
       
        
        ADFUtils.getDCBindingContainer().getOperationBinding("executeEmpsLeft").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getRightPanelBox());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getLeftPanelBox());
        return DnDAction.MOVE;
    }

    public void setRightPanelBox(RichPanelBox rightPanelBox) {
        this.rightPanelBox = rightPanelBox;
    }

    public RichPanelBox getRightPanelBox() {
        return rightPanelBox;
    }

    public void setLeftPanelBox(RichPanelBox leftPanelBox) {
        this.leftPanelBox = leftPanelBox;
    }

    public RichPanelBox getLeftPanelBox() {
        return leftPanelBox;
    }
}
