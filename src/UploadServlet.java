package net.codejava.upload;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * @author Nguyen, Truong Thanh
 * @author 1009851 <p>
 * This servlet is implemented on java SE v. 8u60 64bit <p>
 * Function: Generate histogram from an Image. Use CMY color model. <p>
 * The code use getRGB function to get color of one pixel, then calculate number of pixel in 256 level for each color cyan, magenta and yellow then draw the Histogram panel  <p>
 * File supported: image file with extension *.jpg or *.jpeg <p>
 * User Manual: first start by running the upload.jsp file. Or type "http://localhost:8085/UploadServietApp1/UploadServlet" in browser<p>
 * If run from war file in serve type "http://192.172.0.241:8080/HistogramProject/Upload.jsp" in the browser<p>
 * Chose file in your computer then Click on Upload <p>
 * Wait for the file to be processed <p>
 * After the file finishes being processed, it is shown in web page and available for downloading <p>
 * A link will appear, click on it to download the file <p>
 * Servlet implementation class UploadServlet 
 */

public class UploadServlet extends HttpServlet {
	  
    private static final long serialVersionUID = 1L;
 
    public static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    private String ti;
    private String teo;
    /**
	 * This method handle the upload part and process image file <p>
	 * First the file is uploaded and saved to the project folder <p>
	 * The parameters of Histogram is also uploaded and retrive from form field in upload.jsp <p>
	 * File is then open, generate 3 matrix of CMY form Histogram class   <p>
	 * Three matrix which generated form Histogram class drawed by DrawHistogram class  <p>
	 * File will be processed and saved as new file in the project folder <p>
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
	 * @see Histogram.java & DrawHistogram.java
	 */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }
         
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
         
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
         
        // constructs the directory path to store upload file
        String uploadPath = getServletContext().getRealPath("")
            + File.separator + UPLOAD_DIRECTORY;
         //creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        /** get value form Histogram class
         * draw 3 chart of Cyan, magenta and Yellow color
         * store file at Image upload.jpg and HS1 for Cyan Histogram, HS 2 for Mangenta Histogram and HS3 for Yellow Histogram*/
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(new ServletRequestContext(request));
            Iterator iter = formItems.iterator();
             
            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    
                    String fileName = "ImageUpload.jpg";
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    
                    String fileName2 = "HS.jpg";
                    String filePath2 = uploadPath + File.separator + fileName2;
                    String fileName3 = "HS1.jpg";
                    String filePath3 = uploadPath + File.separator + fileName3;
                    String fileName4 = "HS2.jpg";
                    String filePath4 = uploadPath + File.separator + fileName4;
                    item.write(storeFile);
                    File imageFile = new File(filePath);
                    File imageFile2 = new File(filePath2);
                    File imageFile3 = new File(filePath3);
                    File imageFile4 = new File(filePath4);
                    Histogram his = new Histogram(imageFile);
                    /** get value form Histogram class
                     * i,j,k is array form 1 to 256 
                     * use value form 3 arrays to draw histogram in 
                     * DrawHistogram class*/
                    int[] i= new int[256];
                	int[] j= new int[256];
                	int[] k= new int[256];
                	i=his.GetHis1();
                	j=his.GetHis2();
                	k=his.GetHis3();
                	DrawHistogram cp = new DrawHistogram(i,"Cyan",0);
                	cp.getImage(imageFile2);
                	cp.saveImage();
                	DrawHistogram cp1 = new DrawHistogram(j,"Magenta",1);
                	cp1.getImage(imageFile3);
                	cp1.saveImage();
                	DrawHistogram cp2 = new DrawHistogram(k,"Yellow",2);
                	cp2.getImage(imageFile4);
                	cp2.saveImage();
                    System.out.println("Histogram success");   
                }
            }
           
        } catch (Exception ex) {
        	System.out.println("Upload error");
        	System.exit(1);
        }
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
    
    /**
	 * This method handles the download part of the servlet <p>
	 * This method is called by uploaded.jsp <p>
	 * this function creates the path to the file and get the file type of the file
	 * set up header of the downloaded file then let it be downloadable
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        
        String filePath = getServletContext().getRealPath("")
                   + File.separator + UPLOAD_DIRECTORY+ File.separator + "HS.jpg ";
        String filePath1 = getServletContext().getRealPath("")
                + File.separator + UPLOAD_DIRECTORY+ File.separator + "HS1.jpg ";
        File downloadFile = new File(filePath);
        File downloadFile1 = new File(filePath1);
        FileInputStream in = new FileInputStream(downloadFile);
        
        FileInputStream in1 = new FileInputStream(downloadFile1);
        // obtains ServletContext
           ServletContext context = getServletContext();
            
           // gets MIME type of the file
           String mimeType = context.getMimeType(filePath);
           if (mimeType == null) {        
               // set to binary type if MIME mapping not found
               mimeType = "application/octet-stream";
           }
           System.out.println("MIME type: " + mimeType);
            
           // modifies response
           response.setContentType(mimeType);
          
           response.setContentLength((int) downloadFile.length());
           //force download
           
           if (ti != null){
           String headerKey = "Content-Disposition";
           String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
           response.setHeader(headerKey, headerValue);
           }
           else if(ti == null){
        	   String headerKey = "Content-Disposition";
               String headerValue = String.format("attachment; filename=\"%s\"", downloadFile1.getName());
               response.setHeader(headerKey, headerValue);   	
           }
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
           System.out.println("File downloaded at client successfully");
       }
}




