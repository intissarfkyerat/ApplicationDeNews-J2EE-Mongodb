<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
     <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title>
<link rel="icon" href="images/icone.png" type="image/x-icon">
  <link rel="stylesheet" href="style1.css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
 
 <style>
        .w3-bar {
            position: relative;
        }

        .w3-dropdown-hover:hover .w3-dropdown-content {
            display: block;
        }

        .w3-button {
            background-color: transparent;
            border: none;
            cursor: pointer;
            padding: 5px;
        }

        .w3-dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        .w3-dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .w3-dropdown-content a:hover {
            background-color: #f1f1f1;
        }

.profile-card{
    width: fit-content;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 10px;
}

.profile-card .profile-pic{
    flex: 0 0 auto;
    padding: 0;
    background: none;
    width: 40px;
    height: 40px;
    margin-right: 10px;
}

.profile-card:first-child .profile-pic{
    width: 70px;
    height: 70px;
}

.profile-card .profile-pic img{
    border: none;
}

.profile-card .username{
    font-weight: 500;
    font-size: 14px;
    color: #000;
}

.sub-text{
    color:  #e63939;
    font-size:12px;
    font-weight: 500;
    margin-top: 5px;
}

.action-btn{
    opacity: 1;
    font-weight: 700;
    font-size: 12px;
}


.comment-wrapper{
    width: 100%;
    height: 50px;
    border-radius: 1px solid #e63939;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.comment-wrapper .icon{
    height: 30px;
}

.comment-box{
    width: 80%;
    height: 100%;
    border: none;
    outline: none;
    font-size: 14px;
}

.comment-btn,
.action-btn{
    width: 70px;
    height: 100%;
    background: none;
    border: none;
    outline: none;
    text-transform: capitalize;
    font-size: 16px;
    color:  #e63939;
    opacity: 0.5;
}

.reaction-wrapper{
    width: 100%;
    height: 50px;
    display: flex;
    margin-top: -20px;
    align-items: center;
}

.reaction-wrapper .icon{
    height: 25px;
    margin: 0;
    margin-right: 20px;
}

.reaction-wrapper .icon.save{
    margin-left: auto;
}



.comment-count {
    font-weight: bold;
    margin-right: 5px;
}

/* Styles pour le texte "comment" */
.comment-text {
    color: #888;
}

/* Styles pour la section des commentaires */
.comments-section {
    margin-top: 10px;
}

/* Styles pour chaque commentaire */
.comment {
    border: 1px solid #ccc;
    padding: 10px;
    margin: 10px 0;
    background-color: #f7f7f7;
}

/* Styles pour l'auteur du commentaire */
.comment-author {
    font-weight: bold;
}

/* Styles pour le contenu du commentaire */
.comment-content {
    margin-top: 5px;
}

/* Styles pour la date du commentaire */
.comment-date {
    font-size: 0.8em;
    color: #888;
    margin-top: 5px;
}
 
.hidden {
            display: none;
        }    
        
        
        
.like-button.clicked {
    color: red;
} 
       
       .links-container {
      float: left; /* Alignement à gauche */
      margin-left: 20px; /* Marge à gauche pour séparer les liens du bord gauche de la page */
    }
       .fas {
      margin-right: 10px; /* Marge à droite pour séparer l'icône du texte */
    }
       
    </style>
    
  <script>
function calculateTimePassed(dateString) {
    var dateAjout = new Date(dateString);
    var now = new Date();
    var timeDifference = now - dateAjout; // Différence en millisecondes

    var seconds = Math.floor(timeDifference / 1000);
    var minutes = Math.floor(seconds / 60);
    var hours = Math.floor(minutes / 60);
    var days = Math.floor(hours / 24);
    var weeks = Math.floor(days / 7);
    var months = Math.floor(days / 30); // Estimation approximative pour les mois

    var timePassed = "";
    if (months > 0) {
        timePassed += months + (months === 1 ? " month" : " months");
    } else if (weeks > 0) {
        timePassed += weeks + (weeks === 1 ? " week" : " weeks");
    } else if (days > 0) {
        timePassed += days + (days === 1 ? " day" : " days");
    } else if (hours > 0) {
        timePassed += hours + (hours === 1 ? " hour" : " hours");
    } else if (minutes > 0) {
        timePassed += minutes + (minutes === 1 ? " minute" : " minutes");
    } else {
        timePassed += seconds + (seconds === 1 ? " second" : " seconds");
    }
    
    return timePassed + " ago";
}

</script>
  
   
</head>
<body  class="bg-gray">
      <a href="data" download="data.csv" class="links-container"><i class="fas fa-download"></i></a>
      <a href="RecommandationMahout" class="links-container"><i class="fas fa-bell"></i></a>
   <main>
        <div class="container">
            <div class="content d-flex flex-d-column flex-d-m-row flex-wrap-wrap">
                <div class="left w-100">
                    <div class="box box-1 bg-white">
                        <div class="user-profile">
                            <div class="user-top bg-red">
                                <div class="user-img">
                                    <img src="images/personRo.png" alt="user" class="b-rd-50">
                                </div>
                            </div>
                            <div class="user-bottom bg-white t-c">
                                <h3 class="t-capitalize"><%= session.getAttribute("uname") %></h3>
                            </div>
                           
                            <ul class="d-flex flex-d-column align-items-center t-c">
                                <li class="w-100">
                                    <h4 class="t-capitalize c-mediumGray fs-20">following</h4>
                                    <span class="c-black fs-20"><%= ((List<String>) request.getAttribute("friendsList")).size() %></span>
                                </li>
                                <li class="w-100">
                                    <h4 class="t-capitalize c-mediumGray fs-20">followers</h4>
                                    <span class="c-black fs-20"><%= ((List<String>) request.getAttribute("followersList")).size() %></span>
                                </li>
                                <li class="w-100">
                                    <a href="Home.jsp" class="c-red t-capitalize fs-14">disconnect</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="box box-3 bg-white">
                        <div class="tags">
                            <ul class="d-flex align-items-center justify-content-center flex-wrap-wrap ">
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray fs-14">
                                        help center
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray">
                                        about
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray">
                                        privacy policy
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray">
                                        community guidelines
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray">
                                        cookies policy
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray">
                                        career
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray">
                                        language
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="t-capitalize c-lightGray">
                                        copyright
                                    </a>
                                </li>
                            </ul>
                            <div class="tags-bottom t-c">
                                <p class="t-capitalize">&copy; copyright 2023</p>
                            </div>
                        </div>
                    </div>
                 </div>
                <div class="middle w-100">
                    <div class="box box-4 bg-white">
                       <div class="post d-flex align-items-center justify-content-space-between flex-wrap-wrap">
                            <img src="images/ajoutRouge.png" alt="user">
                           <div class="post-btn d-flex align-items-center">
                             <form  method="post" action="AjouteNews">
                                <input type="text" required placeholder="Title" name="titre"><br><br>
                                <input type="text" required placeholder="URL" name="url"><br><br>
                                <c:if test="${not empty error}">
                                <p>Error : ${error}</p>
                           </c:if>
                                <input type="submit" value="Save">
                             </form>
                           </div>
                       </div>
                      </div>
                       <c:forEach var="newsItem" items="${requestScope.news}">
    <div class="box box-5 bg-white">
        <div class="post-section">
            <div class="post-topbar d-flex align-items-center justify-content-space-between">
                <div class="user-info d-flex align-items-center">
                    <img src="images/personRo.png" alt="user">
                    <div class="info">
                        <h3 class="t-capitalize">${newsItem.auteur}</h3>
                        <span class="c-lightGray">
                            <i class="fas fa-clock"></i>
                          <script>
                                    document.write(calculateTimePassed('${newsItem.dateAjout}'));
                                </script>
                        </span>
                    </div>
                </div>
                   <div class="w3-container">
  <div class="w3-bar w3-light-grey">
    <div class="w3-dropdown-hover">
      <button class="w3-button"> <i class="fas fa-ellipsis-v c-lightGray pointer fs-16"></i></button>
      <div class="w3-dropdown-content w3-bar-block w3-card-4">
        <a href="SupprimerNewsServlet?newsId=${newsItem._id}&uname=${newsItem.auteur}" class="w3-bar-item w3-button">Remove news</a>
      </div>
    </div>
  </div>
</div>
            </div>
            <div class="epic-sec">
                <div class="job-desc">
                    <h3 class="t-capitalize fs-16">${newsItem.titre}</h3>
                    <a href="${newsItem.url}" target="_blank">
                    <iframe width="600" height="400" src="${newsItem.url}"></iframe>
                     Visit the link
                    </a>
                    <div class="job-status-bar">
                    <div class="button-container">
                        <ul>
                            <li>
                           <form action="LikeDislikeServlet" method="post">
                           <input type="hidden" name="action" value="like">
                           <input type="hidden" name="newsId" value="${newsItem._id}">
                           <button class="dislike-button like-button" type="submit">
                                <i class="fas fa-thumbs-up"></i>
                           </button>
                            like <span>${newsItem.jaime.size()}</span>
                           </form>
                           </li>
                           <li>
                           <form action="LikeDislikeServlet" method="post">
                           <input type="hidden" name="action" value="dislike">
                           <input type="hidden" name="newsId" value="${newsItem._id}">
                           <button class="dislike-button like-button" type="submit">
                                 <i class="fas fa-thumbs-down"></i>
                           </button>
                             dislike <span>${newsItem.jedeteste.size()}</span>
                          </form>
                          </li>                            
                            <li>                            
                            <button class="show-comments-button" id="showCommentsButton_${newsItem._id}" type="button">
                            <i class="fas fa-comment-alt"></i>
                        </button>
                        Show <span id="commentCount_${newsItem._id}"> ${newsItem.commentaires.size()}</span> comment
                            <div class="comments-section hidden" id="commentsSection_${newsItem._id}">
                            <ul class="comment-list">
                              <c:forEach var="comment" items="${newsItem.commentaires}">
                                  <li class="comment">
                                    <div class="comment-author">${comment.username}</div>
                                    <div class="comment-content">${comment.comment}</div>
                                    <div class="comment-date"> <script>
                                       document.write(calculateTimePassed('${comment.date}'));</script>
                                    </div>
                                  </li>
                             </c:forEach>
                        </ul>
                        </div>
                       <script>
                            document.addEventListener("DOMContentLoaded", function() {
                                const showCommentsButton = document.getElementById("showCommentsButton_${newsItem._id}");
                                const commentsSection = document.getElementById("commentsSection_${newsItem._id}");
                                let commentsVisible = false;

                                showCommentsButton.addEventListener("click", function() {
                                    if (!commentsVisible) {
                                        commentsSection.classList.remove("hidden");
                                    } else {
                                        commentsSection.classList.add("hidden");
                                    }
                                    commentsVisible = !commentsVisible;
                                });
                            });                                                       
                            document.addEventListener("DOMContentLoaded", function() {
                                const likeButtons = document.querySelectorAll(".like-button");
                                
                                likeButtons.forEach(button => {
                                    button.addEventListener("click", function() {
                                        this.classList.add("clicked");
                                    });
                                });
                            });
                        </script>           
                                    <form action="AddComment" method="post">
                                     <div class="comment-wrapper">                                             
                                                <input type="hidden" name="newsId" value="${newsItem._id}">
                                                <input type="text" class="comment-box" placeholder="Add a comment" name="comment">
                                                <button class="comment-btn">Post</button>
                                     </div>
                                     </form>
                            </li>
                           
                        </ul> </div>
                    </div>
                </div>
            </div>
        </div>   
</div>
     </c:forEach>
     </div>
    <div class=" box box-3 bg-white ">
                        <div class="sg-title d-flex align-items-center ">
                            <h3 class="t-capitalize">Suggestions</h3>
                        </div>
                        <div class="sg-list">
                        <c:forEach var="p" items="${ requestScope.person }">
                         <c:if test="${!friendsList.contains(p.uname)}">               
                           <div class="profile-card">
                            <div>
                              <div class="username">${ p.uname }</div>
                            </div>
                            <form action="AddFriend" method="post">
                              <input type="hidden" name="friendUname" value="${ p.uname }">
                              <button class="action-btn">follow</button>     
                            </form>
                           </div>
                            </c:if>
                        </c:forEach>                           
                        </div>                  
                    </div>                          
              </div>
              </div>  
                                             
    </main> 
   
</body>
</html>