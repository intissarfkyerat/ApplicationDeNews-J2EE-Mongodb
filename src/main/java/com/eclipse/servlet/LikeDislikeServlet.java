package com.eclipse.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/LikeDislikeServlet")
public class LikeDislikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String uname = (String) request.getSession().getAttribute("uname");
	        String newsId = request.getParameter("newsId");
	        String action = request.getParameter("action"); // Assuming this is either "like" or "dislike"

	        try {
	            MongoClient mongo = new MongoClient("localhost", 27017);
	            DB db = mongo.getDB("PMongo");
	            DBCollection coll = db.getCollection("news");
	            DBCollection coll1 = db.getCollection("person");
	            BasicDBObject query2 = new BasicDBObject("uname", uname);
			    DBObject user = coll1.findOne(query2);
	            BasicDBObject updateQuery = new BasicDBObject("_id", new ObjectId(newsId));
	            DBObject news = coll.findOne(updateQuery);

	            if (news != null && (action.equals("like") || action.equals("dislike"))) {
	                Set<String> likedBy = new HashSet<>((List<String>) news.get("jaime"));
	                Set<String> dislikedBy = new HashSet<>((List<String>) news.get("jedeteste"));

	                if (action.equals("like")) {
	                    if (!likedBy.contains(uname)) {
	                        likedBy.add(uname);
	                        dislikedBy.remove(uname);
	                    }
	                } else if (action.equals("dislike")) {
	                    if (!dislikedBy.contains(uname)) {
	                        dislikedBy.add(uname);
	                        likedBy.remove(uname);
	                    }
	                }

	                BasicDBObject updateCommand = new BasicDBObject("$set", new BasicDBObject("jaime", new ArrayList<>(likedBy))
	                        .append("jedeteste", new ArrayList<>(dislikedBy)));

	                coll.update(updateQuery, updateCommand);
	            }

	            
	            
	            DBCursor c = coll.find().sort(new BasicDBObject("dateAjout", -1));
	            BasicDBObject query = new BasicDBObject("uname", new BasicDBObject("$ne", uname));
	            DBCursor c1 = coll1.find(query);
	            
	            if (user != null) {
    		    	BasicDBList followingList = (BasicDBList) user.get("following");
    		    	BasicDBList followersList = (BasicDBList) user.get("followers");
                    List<String> unameFollowingList = new ArrayList<>();
                    List<String> unameFollowersList = new ArrayList<>();
                    if (followingList != null) {
                        for (Object obj : followingList) {
                            if (obj instanceof BasicDBObject) {
                                BasicDBObject followingObject = (BasicDBObject) obj;
                                String unameFollowing = followingObject.getString("unameFollowing");
                                unameFollowingList.add(unameFollowing);
                            }
                        }
                    }
                    if (followersList != null) {
                        for (Object obj : followersList) {
                            if (obj instanceof BasicDBObject) {
                                BasicDBObject followersObject = (BasicDBObject) obj;
                                String unameFollowers = followersObject.getString("unameFollowers");
                                unameFollowersList.add(unameFollowers);
                            }
                        }
                    }
    				request.setAttribute("person", c1);
    				request.setAttribute("news", c);
    				request.setAttribute("friendsList", unameFollowingList);
    				request.setAttribute("followersList", unameFollowersList);
    		        request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
    		      
    		    } else {
    		    	request.setAttribute("news", c);
    	            request.setAttribute("person", c1);
    	            this.getServletContext().getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
    	            
    		    }
                mongo.close();
	            
	            

	        } catch (Exception e) {
	            throw new ServletException(e);
	        }
	}
}
