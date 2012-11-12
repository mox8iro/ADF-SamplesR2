package gr.dim.stas.backing;

import gr.dim.stas.view.ADFUtils;
import gr.dim.stas.view.JSFUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class IndexBackingBean {
    public IndexBackingBean() {
    }

    public void treeSelectionListener(SelectionEvent selectionEvent) {
        System.out.println("Selection Listenereeeeeeeeeee");
        //invoke the listener
//      invokeEL("#{bindings.DepartmentsView.treeModel.makeCurrent}");
     invokeEdL("#{bindings.DepartmentsView.treeModel.makeCurrent}");
      System.out.println("executing Employeeeeeseneee");
      //re execute the EmpViewDetails
      ADFUtils.getDCBindingContainer().getOperationBinding("executeEmplDetailsView").execute();
      
      
    }
    public void tableMakeCurrentSelectionListener(SelectionEvent selectionEvent) {
        System.out.println("invoking for table... selection Listener");
        // Add event code here...
       invokeEdL("#{bindings.EmpDetailsViewView.collectionModel.makeCurrent}");
    }
    public void invokeEdL(String el){
        
        // FacesContext
        FacesContext facesCtx = FacesContext.getCurrentInstance(); 
        Application app = facesCtx.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesCtx.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext,
                                                                  el,
                                                                   Object.class);
        Object result = valueExp.getValue(elContext);
        
    }
              
    /**
    * Programmatic evaluation of EL.
    *
    * @param el EL to evaluate
    * @return Result of the evaluation
    */
    public static Object evaluateEL(String el) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    ValueExpression exp =
    expressionFactory.createValueExpression(elContext, el,
    Object.class);

    return exp.getValue(elContext);
    }

    /**
    * Programmatic invocation of a method that an EL evaluates to.
    * The method must not take any parameters.
    *
    * @param el EL of the method to invoke
    * @return Object that the method returns
    */
    public static Object invokeEL(String el) {
    return invokeEL(el, new Class[0], new Object[0]);
    }

    /**
    * Programmatic invocation of a method that an EL evaluates to.
    *
    * @param el EL of the method to invoke
    * @param paramTypes Array of Class defining the types of the parameters
    * @param params Array of Object defining the values of the parametrs
    * @return Object that the method returns
    */
    public static Object invokeEL(String el, Class[] paramTypes,
    Object[] params) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    MethodExpression exp =
    expressionFactory.createMethodExpression(elContext, el,
    Object.class, paramTypes);

    return exp.invoke(elContext, params);
    }

   
}
