<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload</title>
</head>
<body>
    <center>
        <form method="post" action="UploadServlet" enctype="multipart/form-data">
            Select file to upload: <input type="file" name="uploadFile" />
            <br/><br/>
            <input type="submit" value="Upload" />
        </form>
        
        <form method="get" action="UploadServlet" enctype="multipart/form-data">
            
          	<%-- <jsp:useBean id="ti" class="net.codejava.upload.UploadServlet">
          	<jsp:setProperty name="ti" property="ti" value="ti" />		
                	
             </jsp:useBean> --%>
            <!-- <input type="submit" value="Download" /> --> <a href="UploadServlet">Download HS</a> 
            <br></br>
            <!-- <input type="submit" value="Download" /> --> <a href="UploadServlet">Download HS1</a>
            <br></br>
            <!-- <input type="submit" value="Download" /> --> <a href="UploadServlet">Download HS2</a>
        </form>
        
        
    </center>
    <br>
    <br>
    <img src="upload//ImageUpload.jpg" height="700" width="700">
    <img src="upload/HS.jpg">
    <img src="upload/HS1.jpg">
    <img src="upload/HS2.jpg">
</body>
</html>