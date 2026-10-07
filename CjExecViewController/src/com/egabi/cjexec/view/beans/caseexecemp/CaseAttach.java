package com.egabi.cjexec.view.beans.caseexecemp;

import com.egabi.cjexec.view.utils.ADFUtil;

import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.FilesModel;
import com.egabi.cjexec.view.utils.JSFUtils;
import oracle.jbo.domain.Number;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;

public class CaseAttach {
    public CaseAttach() {
    }

    public void download(FacesContext context, OutputStream out) throws IOException {
        
        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(ADFUtil.evaluateEL("#{row.Did}").toString(), "");
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

    public void executeCriminalFiles() {
        // Add event code here...
    }
}
