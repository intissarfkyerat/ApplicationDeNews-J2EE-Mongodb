package com.eclipse.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
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

@WebServlet("/FriendRecommendationService")
public class FriendRecommendationService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Ajoute la méthode getLikeDislikeEntry 
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
    
        

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("PMongo");
            DBCollection newsColl = db.getCollection("news");
            DBCollection personColl = db.getCollection("person");

            DBCursor usersCursor = personColl.find();
            DBCursor newsCursor = newsColl.find();

            // Obtenez la liste de tous les utilisateurs et de toutes les news
            List<String> users = new ArrayList<>();
            List<String> news = new ArrayList<>();

            while (usersCursor.hasNext()) {
                DBObject user = usersCursor.next();
                String uname = (String) user.get("uname");
                users.add(uname);
            }

            while (newsCursor.hasNext()) {
                DBObject newsObj = newsCursor.next();
                String newsId = ((ObjectId) newsObj.get("_id")).toString();
                news.add(newsId);
            }

            // Créqtion d'une structure de données pour stocker les likes et dislikes sous forme de tableau à deux dimensions
            int numRows = users.size();
            int numColumns = news.size();
            int[][] likesDislikesTable = new int[numRows][numColumns];

            // Parcourez les données de  base de données pour remplir le tableau
            for (int i = 0; i < numRows; i++) {
                String user = users.get(i);
                for (int j = 0; j < numColumns; j++) {
                    String newsId = news.get(j);
                    int entry = getLikeDislikeEntry(user, newsId);
                    likesDislikesTable[i][j] = entry;
                }
            }
            
            
            
            
         // Calculez la similarité entre l'utilisateur actuel et tous les autres utilisateurs
            String currentUser = (String) request.getSession().getAttribute("uname"); // Remplacez par l'utilisateur actuel
            Map<String, Double> similarityScores = new HashMap<>();

            for (String user : users) {
                if (!user.equals(currentUser)) {
                    double similarity = calculateCosineSimilarity(likesDislikesTable, currentUser, user);
                    similarityScores.put(user, similarity);
                }
            }

   
         // Triez les utilisateurs par similarité décroissante
            List<String> recommendedFriends = new ArrayList<>();
            similarityScores.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .forEach(entry -> recommendedFriends.add(entry.getKey()));
            
            Map<String, Double> actualRatings = new HashMap<>();

            // Parcourez les utilisateurs et les news pour obtenir les évaluations réelles
            for (int i = 0; i < numRows; i++) {
                String user = users.get(i);
                for (int j = 0; j < numColumns; j++) {
                    String newsId = news.get(j);
                    int entry = likesDislikesTable[i][j];

                    // Ajoutez l'évaluation réelle à la Map si l'utilisateur a aimé ou détesté la news
                    if (entry == 1 || entry == -1) {
                        // Générez une clé unique pour chaque paire utilisateur-news
                        String ratingKey = user + "_" + newsId;
                        // Ajoutez l'évaluation réelle dans la Map actualRatings
                        actualRatings.put(ratingKey, (double) entry);
                    }
                }
            }

            
           
            request.setAttribute("likesDislikesTable", likesDislikesTable);
            request.setAttribute("users", users);
            request.setAttribute("news", news);
            request.getRequestDispatcher("hoom.jsp").forward(request, response);
            request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
            mongo.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    private double calculateCosineSimilarity(int[][] likesDislikesTable, String user1, String user2) {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("PMongo");
            DBCollection personColl = db.getCollection("person");

            // Obtenez les utilisateurs à partir de la collection person
            DBCursor usersCursor = personColl.find();

            ObjectId user1ObjectId = null;
            ObjectId user2ObjectId = null;

            // Recherchez les ObjectId des utilisateurs dans la liste des utilisateurs
            while (usersCursor.hasNext()) {
                DBObject userObj = usersCursor.next();
                String uname = (String) userObj.get("uname");
                ObjectId userId = (ObjectId) userObj.get("_id");

                if (uname.equals(user1)) {
                    user1ObjectId = userId;
                } else if (uname.equals(user2)) {
                    user2ObjectId = userId;
                }
            }

            if (user1ObjectId == null || user2ObjectId == null) {
                // Utilisateurs introuvables, retournez une valeur par défaut
                return 0.0;
            }

            int numColumns = likesDislikesTable[0].length;

            int[] user1Vector = likesDislikesTable[user1ObjectId.toString().hashCode() % likesDislikesTable.length];
            int[] user2Vector = likesDislikesTable[user2ObjectId.toString().hashCode() % likesDislikesTable.length];

            // Calculez le produit scalaire
            double dotProduct = 0;
            for (int i = 0; i < numColumns; i++) {
                dotProduct += user1Vector[i] * user2Vector[i];
            }

            // Calculez les normes des vecteurs
            double normUser1 = 0;
            double normUser2 = 0;

            for (int i = 0; i < numColumns; i++) {
                normUser1 += Math.pow(user1Vector[i], 2);
                normUser2 += Math.pow(user2Vector[i], 2);
            }

            normUser1 = Math.sqrt(normUser1);
            normUser2 = Math.sqrt(normUser2);

            // Calculez la similarité cosinus
            if (normUser1 != 0 && normUser2 != 0) {
                return dotProduct / (normUser1 * normUser2);
            } else {
                // L'un des vecteurs a une norme nulle, retournez 0.0 pour éviter la division par zéro
                return 0.0;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérez les exceptions de manière appropriée
            return 0.0;
        }
    }
    
    private double calculateMAE(Map<String, Double> similarityScores, Map<String, Double> actualRatings) {
        double totalError = 0;
        int count = 0;

        for (String userNewsKey : similarityScores.keySet()) {
            Double similarity = similarityScores.get(userNewsKey);
            Double actualRating = actualRatings.get(userNewsKey);

            // Vérifiez si la similarité et l'évaluation réelle ne sont pas null
            if (similarity != null && actualRating != null) {
                totalError += Math.abs(actualRating - similarity);
                count++;
            } else {
                
            }
        }

        // Vérifiez si count est non nul pour éviter la division par zéro
        return (count != 0) ? totalError / count : 0;
    }

    private double calculateRMSE(Map<String, Double> similarityScores, Map<String, Double> actualRatings) {
        double squaredErrorSum = 0;
        int count = 0;

        for (String userNewsKey : similarityScores.keySet()) {
            Double similarity = similarityScores.get(userNewsKey);
            Double actualRating = actualRatings.get(userNewsKey);
             
               if (similarity != null && actualRating != null) {
            squaredErrorSum += Math.pow(actualRating - similarity, 2);
            count++;
            } else {
                
            }
        }

        return Math.sqrt(squaredErrorSum / count);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}