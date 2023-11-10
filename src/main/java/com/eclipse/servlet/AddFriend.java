package com.eclipse.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


@WebServlet("/AddFriend")
public class AddFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String frienduname = request.getParameter("friendUname");
		String uname = (String) request.getSession().getAttribute("uname");
	    try {
	        MongoClient mongo = new MongoClient("localhost", 27017);
	        DB db = mongo.getDB("PMongo");
	        DBCollection coll = db.getCollection("news");
	        DBCollection coll1 = db.getCollection("person");
	        BasicDBObject newFollowing = new BasicDBObject("unameFollowing", frienduname);
	        BasicDBObject newFollowers = new BasicDBObject("unameFollowers", uname);
	        BasicDBObject updateQuery = new BasicDBObject("uname", uname);
	        BasicDBObject updateQuery2 = new BasicDBObject("uname", frienduname);
            BasicDBObject updateCommand = new BasicDBObject("$addToSet", new BasicDBObject("following", newFollowing));
            BasicDBObject updateCommand2 = new BasicDBObject("$addToSet", new BasicDBObject("followers", newFollowers));
            coll1.update(updateQuery, updateCommand);
            coll1.update(updateQuery2, updateCommand2);
            BasicDBObject query1 = new BasicDBObject("uname", uname);
		    DBObject user = coll1.findOne(query1);
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
		    }else {
	          
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
