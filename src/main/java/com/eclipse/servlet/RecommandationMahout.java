package com.eclipse.servlet;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;



@WebServlet("/RecommandationMahout")
public class RecommandationMahout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		  		  
		
		// Charger le modèle de données à partir du fichier de sortie de Mahout
		
		DataModel model = new FileDataModel(new File("D:\\INTISSAR\\S2 GLCC\\BDNOSQL\\SiteWeb\\SiteWebNews\\SiteNewsRecommandation\\src\\main\\webapp\\WEB-INF\\classes\\data.csv"));
        int neighborhoodSize = 3; 
        UserNeighborhood neighborhood;
        UserSimilarity similarity;
        ItemSimilarity itemsimilarity;
        String uname = (String) request.getSession().getAttribute("uname");
		try {
			similarity = new PearsonCorrelationSimilarity(model);
			itemsimilarity = new PearsonCorrelationSimilarity(model);
			neighborhood = new NearestNUserNeighborhood(neighborhoodSize, similarity, model);
			
			// Créer un recommandeur basé sur les utilisateurs
	        GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);              			
            
	     // Créer un recommandeur basé sur les items
	        GenericItemBasedRecommender recommender2 = new GenericItemBasedRecommender(model, itemsimilarity);
	

	        long userID = data.getUserID(uname);
	        // Obtenir 5 recommandations pour l'utilisateur spécifié
	        List<RecommendedItem> recommendations = recommender.recommend(userID, 6);

	        request.setAttribute("userID", userID);
			request.setAttribute("recommendations", recommendations);
			request.getRequestDispatcher("/WEB-INF/page1.jsp").forward(request, response);
		} catch (TasteException e) {
			
			e.printStackTrace();
		}

        
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	        
	    
	       
	}

}
