package gr.dim.stas.view.beans;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.adapter.dataformat.csv.CSVParser;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class UploadBean {
    public UploadBean() {
    }

    public void parse(String[] lineValues){
        BindingContainer bdCont=BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operation = bdCont.getOperationBinding("createRow");
       operation.getParamsMap().put("lineValues", lineValues);
       operation.execute();


    }
    public void fileUploadValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(" uploading file ");
        UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
        

        try {
           CSVParser csvParser = new CSVParser(file.getInputStream());
            
            //skip the first line since it holds the header
            csvParser.nextLine();
            
            while(csvParser.nextLine()){
                String[] lineValues = csvParser.getLineValues();
                this.parse(lineValues);
            }
                       
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
