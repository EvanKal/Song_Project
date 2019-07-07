<%-- 
    Document   : viewallsongs
    Created on : Jun 25, 2019, 10:48:58 PM
    Author     : Los_e
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles.css" rel="stylesheet"/>
        <title>All Songs</title>
    </head>
    <body>
        <nav>
            <a href="/song">Home</a>
            <a href="/song/uploadsong">Upload song</a>
        </nav>
        <h1 id="title">MP3 deposit</h1>

        <c:choose>

            <c:when test = "${songlist.size() == 0}">
                <p>No results. Currently no songs</p>
            </c:when>

            <c:when test = "${songlist.size() > 0}">
                <table>
                    <thead>
                        <tr>
                            <td >Title</td>
                            <td >Artist</td>
                            <td >Album</td>
                            <td >Year</td>
                            <td >Artwork</td>
                            <td >Lyrics</td>
                            <td >Download</td>
                            <td >Delete</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${songlist}" var="song">
                            <tr>
                                <td >${song.title}</td>
                                <td >${song.artist}</td>
                                <td >${song.album}</td>
                                <td >${song.yearofrelease}</td>

                                <c:choose>
                                    <c:when test="${song.image != null}">
                                        <c:url value="/songcontroller/image" var = "imageURL">
                                            <c:param name="songid" value="${song.id}" />
                                        </c:url>
                                        <td ><img src="${imageURL}" alt="artwork"/></td>
                                        </c:when>    
                                        <c:otherwise>
                                        <td >No artwork</td>
                                    </c:otherwise>
                                </c:choose>

                                <c:url value="/songcontroller/getlyrics" var = "lyricsURL">
                                    <c:param name="artist" value="${song.artist}" />
                                    <c:param name="title" value="${song.title}" />
                                </c:url>
                                <td ><a href="${lyricsURL}" >Lyrics</a></td>


                                <c:choose>
                                    <c:when test="${song.file != null}">
                                        <c:url value="/songcontroller/download" var = "myURL">
                                            <c:param name="songid" value="${song.id}" />
                                        </c:url>
                                        <td ><a href="${myURL}">Link</a></td>

                                    </c:when>    
                                    <c:otherwise>
                                        <td >No file</td>
                                    </c:otherwise>
                                </c:choose>

                                <c:url value="/songcontroller/delete" var = "delURL">
                                    <c:param name="songid" value="${song.id}" />
                                </c:url>
                                <td ><a href="${delURL}">Delete</a></td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
        </c:choose>



        <c:if test="${param.success ne null}">
            <div id="overlay">
                <a id="close" href="#overlay">&times;</a>

                <c:choose>
                    <c:when test = "${success eq false && lyrics eq ''}">
                        <p id="lyrics" >No results. Unable to retrieve lyrics</p>
                    </c:when>
                    <c:when test = "${success eq true}">
                        <p id="lyrics" style="white-space: pre-line">${lyrics}</p>
                    </c:when>
                </c:choose>
            </div>
        </c:if>
        <script src="js/app.js"></script>
    </body>
</html>