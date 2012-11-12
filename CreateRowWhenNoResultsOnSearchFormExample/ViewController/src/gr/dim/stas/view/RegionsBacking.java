package gr.dim.stas.view;

import javax.faces.event.ActionEvent;

public class RegionsBacking {
    public RegionsBacking() {
    }

    public void executeActionListener(ActionEvent actionEvent) {
        //invoke the Execute
        ADFUtils.getDCBindingContainer().getOperationBinding("Execute").execute();
        ADFUtils.getDCBindingContainer().getOperationBinding("createInsert").execute();
        // Add event code here...
    }
}
