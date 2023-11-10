<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log In</title>
<link rel="icon" href="images/icone.png" type="image/x-icon">
  <link rel="stylesheet" href="style2.css">
</head>
<body>
<section>
   <div class="circle"></div>
   <header>
     <a href="#" ><img src="images/icone.png" class="logo"></a>
       <img  src="images/sombre.png" class="sombre" id="icon">
   </header>
   <div class="content">
      <div class="textBox">
            <h2>Log In</h2>
            <form  method="post" action="Login">
               <div class="field">
                  <span class="fa fa-user"></span>
                  <input type="text" required placeholder="UserName" name="uname">
               </div>
               <div class="field space">
                  <span class="fa fa-lock"></span>
                  <input type="password" class="pass-key" required placeholder="Password" name="pwd">
                  <span class="show">SHOW</span>
               </div>
               
               <div class="field space">
                  <input type="submit" value="Log In">
               </div>
               <div class="field space">
               If Don't have account ? Sign Up
               </div>
            </form>
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
 <script>
         const pass_field = document.querySelector('.pass-key');
         const showBtn = document.querySelector('.show');
         showBtn.addEventListener('click', function(){
          if(pass_field.type === "password"){
            pass_field.type = "text";
            showBtn.textContent = "HIDE";
            showBtn.style.color = "#ff0000";
          }else{
            pass_field.type = "password";
            showBtn.textContent = "SHOW";
            showBtn.style.color = "#ff0000";
          }
         });
      </script>
</body>
</html>