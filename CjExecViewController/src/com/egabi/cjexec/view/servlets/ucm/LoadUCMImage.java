package com.egabi.cjexec.view.servlets.ucm;

import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.FilesModel;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadUCMImage extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try {
        String dID = request.getParameter("did"); 
            System.out.println("-------------------------------");
        System.out.println("dID "+dID);
            if (dID.length()>0){
           FilesModel filesModel =  CjUtils.DownloadContent(dID,"");
            System.out.println("filesModel  "+filesModel);
            response.setContentType("image/jpg");
//        InputStream content = filesModel.getFileContent();
            System.out.println("content  "+filesModel.getFileContent());
//        request.getSession().setAttribute("partyImage", content);
        BufferedImage bi = ImageIO.read(filesModel.getFileContent());
            System.out.println("bi  "+bi);
            OutputStream os = response.getOutputStream();
            System.out.println("os  "+os);
            ImageIO.write(bi, "jpg", os);
            }
        }
        catch (Exception e )
        {e.printStackTrace();}
    }
}
