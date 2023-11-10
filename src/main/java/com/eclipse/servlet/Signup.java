package com.eclipse.servlet;

import java.io.IOException;
import com.mongodb.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom=request.getParameter("nom");
		String prenom=request.getParameter("prenom");
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		try {
			MongoClient mongo = new MongoClient("localhost" ,27017);
			DB db = mongo.getDB("PMongo"); 
		    DBCollection coll=db.getCollection("person");
		    DBCollection coll1=db.getCollection("news");
		    DBObject existingName = coll.findOne(new BasicDBObject("uname", uname));
		    BasicDBObject query = new BasicDBObject("uname", new BasicDBObject("$ne", uname));
		    DBCursor c = coll.find(query);
		    DBCursor c1 = coll1.find().sort(new BasicDBObject("dateAjout", -1));
            if (existingName != null) {
                request.setAttribute("error", "User name existe");
                this.getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
    		    } 
            else {
            BasicDBObject d1=new BasicDBObject("nom",nom).append("prenom", prenom).append("uname", uname).append("pwd", pwd).append("following", new BasicDBList()).append("followers", new BasicDBList());
		    coll.insert(d1);	    
		    HttpSession session = request.getSession();		    
		    request.setAttribute("person", c);
		    request.setAttribute("news", c1);
		    session.setAttribute("uname", uname);
			this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    	 }  
            mongo.close();
		}catch(Exception e) {
			throw e;
		}
	}

}
