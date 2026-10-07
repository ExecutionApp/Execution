package com.egabi.cjexec.view.beans.arrestWarrants;

import oracle.jbo.ViewCriteria;
import oracle.jbo.server.ViewObjectImpl;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.FilesModel;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import javax.faces.context.FacesContext;

public class PreviewArrestWarrants {
    public PreviewArrestWarrants() {
    }

    public String search() {
        
            ViewObjectImpl AssetsDescr = (ViewObjectImpl) ADFUtil.findIterator("PreviewArrestWarrantsROVO1Iterator").getViewObject();
            ViewCriteria criteria = AssetsDescr.getViewCriteria("PreviewArrestWarrantsROVOCriteria");
            
            
            BigDecimal contCodeBind = new BigDecimal(0);
            
        if (JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")!= null)
        contCodeBind =   (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
        String police = ""+JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}");
           
        System.out.println("NeyabKolyaa : "+JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
        System.out.println("Police : "+police);
        
           
           
                if (police!= null && police.length()>0&&!police.isEmpty())
             contCodeBind =   (BigDecimal) JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}");
        
            AssetsDescr.applyViewCriteria(criteria);
            AssetsDescr.setNamedWhereClauseParam("pEntityId", contCodeBind); 

            AssetsDescr.executeQuery();
            System.err.println("SEARCH" + contCodeBind);
         
        return null;
    }
    
    
    
    public void downloadArrestWarrantsFile(FacesContext context, OutputStream out) throws IOException  {
        downloadGeneric(ADFUtil.evaluateEL("#{row.Did}").toString(),out);
    }

    private void downloadGeneric(String did, OutputStream out)throws IOException{
        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(did, "");
            fis = content.getFileContent();

            int read;
            byte[] bytes = new byte[1024];
            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
    
}
