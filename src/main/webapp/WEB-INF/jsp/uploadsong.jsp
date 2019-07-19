<%-- 
    Document   : uploadsong
    Created on : Jun 23, 2019, 4:34:28 PM
    Author     : Los_e
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles.css" rel="stylesheet"/>

        <title>Upload song</title>
    </head>
    <body>
        <nav>
            <a href="/song">Home</a>
            <a href="/song/uploadsong">Upload song</a>
        </nav>
        <h1 id="title">MP3 deposit</h1>

        <form  method="Post" action="/song/songcontroller/douploadsong" enctype="multipart/form-data">
            <input class="inputfile" id ="upload" type="file" name="myfile" accept=".mp3">
            <label for="upload">Choose MP3 file to upload</label>
            <input class="btn-1" type="submit">
        </form>

        <script src="js/app.js"></script>
    </body>
</html>
