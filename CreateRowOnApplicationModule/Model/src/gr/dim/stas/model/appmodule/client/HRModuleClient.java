package gr.dim.stas.model.appmodule.client;

import gr.dim.stas.model.appmodule.common.HRModule;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Nov 23 13:02:32 CET 2011
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HRModuleClient extends ApplicationModuleImpl implements HRModule {
    /**
     * This is the default constructor (do not remove).
     */
    public HRModuleClient() {
    }

    public void createNewRowOnDepts() {
        Object _ret = this.riInvokeExportedMethod(this,"createNewRowOnDepts",null,null);
        return;
    }
}
