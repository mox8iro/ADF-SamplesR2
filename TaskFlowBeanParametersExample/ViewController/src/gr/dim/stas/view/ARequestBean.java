package gr.dim.stas.view;

import java.sql.SQLException;

import javax.faces.event.ValueChangeEvent;
import oracle.jbo.domain.Number;

import oracle.adf.view.rich.context.AdfFacesContext;

/**
 * This is a bean that will be used with a Request Scope.
 * The name of the class is purely illustrative and should not be confused with anything else.
 */
public class ARequestBean {
    
   private TemplateBean myTemplateBean;
    
    public ARequestBean() {
        super();
    }
    
    
    public void salaryVCL(ValueChangeEvent valueChangeEvent) {
        
        
        TemplateBean bean = getMyTemplateBean();
               
               boolean isDisabled=bean.getMenu1B().isDisabled();
                
               //get the new value of the salary
               Object newValueObj=valueChangeEvent.getNewValue();
               
               if(newValueObj!=null){
                   
                   Number newValueN=null;

                   try {
                       newValueN = new Number(newValueObj);
                       
                       if(newValueN.compareTo(new Number(100))<=0){
                           System.out.println("enabling..");
                           //disable it
                           bean.getMenu1B().setDisabled(false);
                       }
                       else{
                           System.out.println("disabling..");
                           //enable it
                           bean.getMenu1B().setDisabled(true);
                       }
                       
                       //refresh the component:
                       AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                       adfFacesContext.getCurrentInstance().addPartialTarget(bean.getMenu1B());
                      
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }

        
    }


    public void setMyTemplateBean(TemplateBean myTemplateBean) {
        this.myTemplateBean = myTemplateBean;
    }

    public TemplateBean getMyTemplateBean() {
        return myTemplateBean;
    }
}
