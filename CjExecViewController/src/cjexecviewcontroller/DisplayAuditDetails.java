package cjexecviewcontroller;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.FilesModel;

import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;

public class DisplayAuditDetails {
    public DisplayAuditDetails() {
    }

    public void downloadOldValueMethod(FacesContext facesContext, OutputStream out) {
        String did = ADFUtil.evaluateEL("#{row.OldValue}").toString();
        download(did, out);
    }

    public void downloadNewValueMethod(FacesContext facesContext, OutputStream out) {
        String did = ADFUtil.evaluateEL("#{row.NewValue}").toString();
        download(did, out);
    }

    private void download(String did, OutputStream out) {

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
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String searchAction() {
        if (ADFUtil.findIterator("AuditSimpleMasterRoIterator").getEstimatedRowCount() == 0) {
            JSFUtils.addFacesInformationMessage("\u0627\u0644\u0642\u0636\u064A\u0629 \u0642\u062F \u062A\u0643\u0648\u0646 \u063A\u064A\u0631 \u0645\u062F\u0631\u062C\u0629 \u0628\u0627\u0644\u0646\u0638\u0627\u0645 \u0623\u0648 \u0644\u0627\u062A\u0648\u062C\u062F \u062A\u0639\u062F\u064A\u0644\u0627\u062A");
        }
        return null;
    }
}
