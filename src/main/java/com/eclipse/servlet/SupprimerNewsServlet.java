package com.eclipse.servlet;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
@WebServlet("/SupprimerNewsServlet")
public class SupprimerNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = (String) request.getSession().getAttribute("uname");
		String newsIdStr = request.getParameter("newsId");
		HttpSession session = request.getSession();
	    String authenticatedUser = (String) session.getAttribute("uname");
	    try {
	        MongoClient mongo = new MongoClient("localhost", 27017);
	        DB db = mongo.getDB("PMongo");
	        DBCollection coll = db.getCollection("news");
	        DBCollection coll1 = db.getCollection("person");
	        if (newsIdStr != null && !newsIdStr.isEmpty()) {
	            ObjectId objectId = new ObjectId(newsIdStr);
	            BasicDBObject query3 = new BasicDBObject("_id", objectId);
	            query3.append("auteur", authenticatedUser);
	            coll.remove(query3);
	            
	        } 
	       
	        BasicDBObject query = new BasicDBObject("uname", uname);
		    DBObject user = coll1.findOne(query);
	        BasicDBObject query1 = new BasicDBObject("uname", new BasicDBObject("$ne", uname));   
		    DBCursor c = coll1.find(query1);
		    DBCursor c1 = coll.find().sort(new BasicDBObject("dateAjout", -1));
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
				request.setAttribute("person", c);
				request.setAttribute("news", c1);
				request.setAttribute("friendsList", unameFollowingList);
				request.setAttribute("followersList", unameFollowersList);
		        request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
		    } else {
		    	request.setAttribute("news", c1);
	            request.setAttribute("person", c);
	            this.getServletContext().getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
		    }
            mongo.close();
		    
	    } catch (Exception e) {
	        throw new ServletException(e);
	    }

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
