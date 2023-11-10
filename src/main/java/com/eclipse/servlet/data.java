package com.eclipse.servlet;

import java.io.PrintWriter;
import java.io.IOException;
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
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;



@WebServlet("/data")
public class data extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	
	private void generateCSV(List<Object[]> data, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=data.csv");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", "text/csv");

        PrintWriter writer = response.getWriter();
        
        try (FileWriter fileWriter = new FileWriter("D:\\INTISSAR\\S2 GLCC\\BDNOSQL\\SiteWeb\\SiteWebNews\\SiteNewsRecommandation\\src\\main\\webapp\\WEB-INF\\classes\\data.csv")) {
        for (Object[] row : data) {
            long userId = (long) row[0];
            long newsId = (long) row[1];
            int rating = (int) row[2];
            writer.println(userId + "," + newsId + "," + rating);
            fileWriter.write(userId + "," + newsId + "," + rating + System.lineSeparator());
        }
        }
        writer.close();
    }
	
	
	private int getLikeDislikeEntry(String user, String newsId) {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("PMongo");
            DBCollection newsColl = db.getCollection("news");
           
            BasicDBObject query = new BasicDBObject("_id", new ObjectId(newsId));
            DBObject news = newsColl.findOne(query);

            if (news != null) {
                BasicDBList likedByList = (BasicDBList) news.get("jaime");
                BasicDBList dislikedByList = (BasicDBList) news.get("jedeteste");

                if (likedByList != null && likedByList.contains(user)) {
                    // L'utilisateur a aimé la news, retournez 1
                    return 1;
                } else if (dislikedByList != null && dislikedByList.contains(user)) {
                    // L'utilisateur a détesté la news, retournez -1
                    return -1;
                }
            }
            mongo.close();
        } catch (Exception e) {
            e.printStackTrace(); // Gérez les exceptions de manière appropriée
        }

        // Par défaut, retournez 0 si l'information n'est pas trouvée
        return 0;
    }
	
	private static Map<String, Long> userMapping = new HashMap<>();
	private static long userIdCounter = 1;
	public static long getUserID(String uname) {
		if (!userMapping.containsKey(uname)) {
            userMapping.put(uname, userIdCounter);
            userIdCounter++;
        }

        long userId = userMapping.get(uname);
		return userId;
    }


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		   try {
	            MongoClient mongo = new MongoClient("localhost", 27017);
	            DB db = mongo.getDB("PMongo");
	            DBCollection newsColl = db.getCollection("news");
	            DBCollection personColl = db.getCollection("person");

	            DBCursor usersCursor = personColl.find();
	            DBCursor newsCursor = newsColl.find();

	            // Créer une liste dynamique pour stocker les données
	            List<Object[]> data = new ArrayList<>();
             
	            
	            
	            Map<String, Long> newsMapping = new HashMap<>();
	            long newsIdCounter = 1;
	            
	            // Remplir le tableau de données avec les évaluations
	            while (usersCursor.hasNext()) {
	                DBObject user = usersCursor.next();
	                String userName = (String) user.get("uname");
	                newsCursor = newsColl.find(); 	              	                

	                long userId = getUserID(userName);
	                request.getSession().setAttribute("userID", userId);
	                
	                while (newsCursor.hasNext()) {
	                    DBObject newsObj = newsCursor.next();
	                    String newsId = ((ObjectId) newsObj.get("_id")).toString();
	                    int rating = getLikeDislikeEntry(userName, newsId);

	                    if (!newsMapping.containsKey(newsId)) {
		                    newsMapping.put(newsId, newsIdCounter);
		                    newsIdCounter++;
		                }

		                long newsIdN = newsMapping.get(newsId);
	                    
	                    // Ajouter l'entrée au tableau de données si l'utilisateur a une interaction avec cette news
	                    if (rating != 0) {
	                        Object[] row = new Object[]{userId, newsIdN, rating};
	                        data.add(row);
	                    }
	                }
	            }
	            
	            mongo.close();
	            

	            
	         
	            
	            generateCSV(data, response);
	           
	        } catch (Exception e) {
	            throw new ServletException(e);
	        }
		   
		 
		   
		
		   
 		  
		   
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
