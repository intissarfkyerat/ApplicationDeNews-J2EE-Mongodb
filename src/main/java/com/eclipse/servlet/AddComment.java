package com.eclipse.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String uname = (String) request.getSession().getAttribute("uname");
	    String comment = request.getParameter("comment");
	    String newsId = request.getParameter("newsId");
	    try {
	        MongoClient mongo = new MongoClient("localhost", 27017);
	        DB db = mongo.getDB("PMongo");
	        DBCollection coll = db.getCollection("news");
	        DBCollection coll1 = db.getCollection("person");
	        BasicDBObject query2 = new BasicDBObject("uname", uname);
		    DBObject user = coll1.findOne(query2);
	        if (ObjectId.isValid(newsId)) {
	            BasicDBObject newComment = new BasicDBObject("comment", comment)
	                    .append("username", uname)
	                    .append("date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	            BasicDBObject updateQuery = new BasicDBObject("_id", new ObjectId(newsId));
	            BasicDBObject updateCommand = new BasicDBObject("$push", new BasicDBObject("commentaires", newComment));
	            coll.update(updateQuery, updateCommand);	            	         	            
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
    		    	request.setAttribute("news", c1);
    	            request.setAttribute("person", c);
    	            this.getServletContext().getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
    		    }
                mongo.close();
	        } else {
	            DBCursor c = coll.find().sort(new BasicDBObject("dateAjout", -1));
	            BasicDBObject query = new BasicDBObject("uname", new BasicDBObject("$ne", uname));
	            DBCursor c1 = coll1.find(query);
	            request.setAttribute("news", c);
	            request.setAttribute("person", c1);
	            this.getServletContext().getRequestDispatcher("/WEB-INF/success.jsp?error=1").forward(request, response);
	        }
	    } catch (Exception e) {
	    	throw new ServletException(e);
	    }
	}

}
