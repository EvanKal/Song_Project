<%-- 
    Document   : lyrics
    Created on : Jun 27, 2019, 12:25:26 AM
    Author     : Los_e
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lyrics</title>
    </head>
    <body>
        <c:choose>

            <c:when test = "${lyricsnode == null}">
                <p>No results. Unable to retrieve lyrics</p>
            </c:when>
            <c:otherwise>
                <p style="white-space: pre-line">${lyricsnode}</p>
            </c:otherwise>
                </c:choose>
        </body>
    </html>
