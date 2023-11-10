<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Recommender System</title>
<link rel="icon" href="images/icone.png" type="image/x-icon">
  
</head>
<body>
<h1>Recommendations for user <%= session.getAttribute("uname") %>   <%= request.getAttribute("userID") %></h1>
    
    <c:forEach var="recommendation" items="${recommendations}">
        <p>ItemID: ${recommendation.itemID}, Score: ${recommendation.value}</p>
    </c:forEach> 
</body>
</html>