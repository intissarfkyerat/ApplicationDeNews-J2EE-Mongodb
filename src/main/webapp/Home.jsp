<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>News</title>
<link rel="icon" href="images/icone.png" type="image/x-icon">
  <link rel="stylesheet" href="style.css">
</head>
<body>
<section>
   <div class="circle"></div>
   <header>
     <a href="#" ><img src="images/icone.png" class="logo"></a>
     <ul>
       <li><a href="">Home</a></li>
       <c:url value="/Login" var="l1"/>
       <li><a href="${ l1 }">Log In</a></li>
       <c:url value="/Signup" var="l2"/>
       <li><a href="${ l2 }">Sign Up</a></li>
       </ul> 
       <img  src="images/sombre.png" class="sombre" id="icon">
   </header>
   <div class="content">
      <div class="textBox">
         <h2>Site<span> News !!</span></h2>
         <p>Welcome to my news website, your reliable and up-to-date source for all essential information. I am dedicated to providing accurate, relevant and interesting news in various fields. Whether you are interested in national, international, economic, political, technological, sports or cultural news, you will find complete and balanced coverage here.</p>
         <a href="#"> Learn More</a>
      </div>
      <div class="imgBox">
       <img src="images/n5.jpg" class="starbucks">
      </div>
   </div>
   <ul class="thumb">
      <li><img src="images/icone.png" onclick="imgSlider('images/n5.jpg');changeCircleColor('#ff0000')"></li>
      <li><img src="images/n2.png" onclick="imgSlider('images/n6.jpg');changeCircleColor('#33FFE6')"></li>
      <li><img src="images/n4.png" onclick="imgSlider('images/n7.jpg');changeCircleColor('#FA9F8C')"></li>
   </ul>
</section>
<script type="text/javascript">
   function imgSlider(anything){
	   document.querySelector('.starbucks').src=anything;
   }
   
   function changeCircleColor(color){
	   const circle = document.querySelector('.circle');
	   circle.style.background = color;
   }
</script>
<script>
    var icon = document.getElementById("icon");
    icon.onclick = function(){
    	document.body.classList.toggle("dark-theme");
    	if(document.body.classList.contains("dark-theme")){
    		icon.src = "images/sun.png";
    	}else{
    		icon.src = "images/sombre.png";
    	}
    }
</script>
</body>
</html>