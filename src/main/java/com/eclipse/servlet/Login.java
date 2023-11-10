package com.eclipse.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.BasicDBList;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		try {
		    MongoClient mongo = new MongoClient("localhost", 27017);
		    DB db = mongo.getDB("PMongo");
		    DBCollection coll = db.getCollection("person");
		    DBCollection coll1 = db.getCollection("news");
		    BasicDBObject query = new BasicDBObject("uname", uname).append("pwd", pwd);
		    DBObject user = coll.findOne(query);
		    BasicDBObject query1 = new BasicDBObject("uname", new BasicDBObject("$ne", uname));   
		    DBCursor c = coll.find(query1);
		    DBCursor c1 = coll1.find().sort(new BasicDBObject("dateAjout", -1));
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
		    	HttpSession session = request.getSession();
				session.setAttribute("uname", uname);
				request.setAttribute("person", c);
				request.setAttribute("news", c1);
				request.setAttribute("friendsList", unameFollowingList);
				request.setAttribute("followersList", unameFollowersList);
		        request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
		    } else {
		    	request.getRequestDispatcher("/WEB-INF/login.jsp?error=1").forward(request, response);
		    }
		    mongo.close();
		} catch (Exception e) {
		    throw new ServletException(e);
		}
	}

}
