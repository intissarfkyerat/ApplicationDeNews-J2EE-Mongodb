<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Table of Similarity</title>
<link rel="icon" href="images/icone.png" type="image/x-icon">
  <link rel="stylesheet" href="style1.css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
 <style>
        /* Ajoutez du CSS pour centrer le titre */
        h1 {
            text-align: center; /* Centre le texte horizontalement */
        }
    </style>
</head>
<body>
    <h1>News Likes and Dislikes</h1>
    
    <table border="1">
        <tr>
            <th>User</th>
            <c:forEach items="${news}" var="newsId">
                <th>${newsId}</th>
            </c:forEach>
        </tr>
        
        <c:forEach items="${users}" var="user" varStatus="loop">
            <tr>
                <td>${user}</td>
                <c:forEach items="${likesDislikesTable[loop.index]}" var="entry">
                    <td>${entry}</td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
    
</body>
</html>