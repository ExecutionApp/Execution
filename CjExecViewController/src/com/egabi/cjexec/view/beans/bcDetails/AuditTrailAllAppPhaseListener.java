package com.egabi.cjexec.view.beans.bcDetails;


import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import oracle.adf.model.RegionContext;
import oracle.adf.model.RegionController;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AuditTrailAllAppPhaseListener implements RegionController{
    public AuditTrailAllAppPhaseListener() {
        super();
    }
     
    public boolean refreshRegion(RegionContext regionContext)   // you need to override refresh region method.
    {
        int refreshFlag= regionContext.getRefreshFlag();
        FacesContext fctx = FacesContext.getCurrentInstance();
        //check internal request parameter
        Map requestMap = fctx.getExternalContext().getRequestMap();
        PhaseId currentPhase=(PhaseId)requestMap.get("oracle.adfinternal.view.faces.lifecycle.CURRENT_PHASE_ID");   
        if(currentPhase.getOrdinal()==PhaseId.RENDER_RESPONSE.getOrdinal())   // write custom logic of correct lifecycle phase.
        {
            Object showPrintableBehavior =
            requestMap.get("oracle.adfinternal.view.faces.el.PrintablePage");
            if (showPrintableBehavior != null)
            {
                if (Boolean.TRUE == showPrintableBehavior) 
                {
                ExtendedRenderKitService erks = null;
                erks =
                Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
                //invoke JavaScript from the server
                erks.addScript(fctx, "window.print();");
                erks.addScript(fctx, "window.close();");
                     
                }
           }
                regionContext.getRegionBinding().refresh(refreshFlag);
        }
    return false;
    }
      
    public boolean validateRegion(RegionContext regionContext) 
    {
        regionContext.getRegionBinding().validate();
        return false;
    }
      
    public boolean isRegionViewable(RegionContext regionContext) 
    {
     return regionContext.getRegionBinding().isViewable();
    }
      
        public String getName() 
        {
        return null;
        }
 }