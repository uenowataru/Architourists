package edu.gatech.architourists.database;

import java.util.ArrayList;

import edu.gatech.architourists.R;

public class GaudiTour {

	/**
	 * Get image id
	 * 
	 * @return
	 */
	public static int getImage() {
		return R.drawable.gaudi_tour;
	}

	/**
	 * Get a list of site names on the route
	 * 
	 * @return
	 */
	public static ArrayList<String> getSiteList() {
		ArrayList<String> siteList = new ArrayList<String>();
		siteList.add("Residencia Onix");
		siteList.add("Palau G¨¹ell");
		siteList.add("Casa Batllo");
		siteList.add("Casa Mila");
		siteList.add("Basilica de La Sagrada Familia");
		siteList.add("Parc G¨¹ell");
		return siteList;

	}

	/**
	 * Get a list of directions for each site
	 * 
	 * @return
	 */
	public static ArrayList<String> getRouteInfo() {
		ArrayList<String> routeInfo = new ArrayList<String>();
		routeInfo
				.add("a. From Onix walk towards Arc de Triomf station\n"
						+ "b. Take L1 (red) train towards Hospital de Bellvitge and get off at Catalunya\n"
						+ "c. Get off and switch to L3 (green) towards Zona Universitaria and get off at Liceu\n"
						+ "d. Walk down La Rambla towards the ocean (downhill) until Carrer Nou de La Rambla\n"
						+ "e. Turn right on Carrer Nou de La Rambla. Palau Guell will be on the right.");
		routeInfo
				.add("a. From Palau G¨¹ell walk towards Liceu stop on La Rambla\n"
						+ "b. Take L3 (green) train towards Trinitat Nova until Passeig de Gracia\n"
						+ "c. Get out and Casa Batllo will be right outside the metro stop");
		routeInfo
				.add("a. From Casa Battlo walk towards Diagonal on Passeig de Gracia\n"
						+ "b. Casa Mila will be on the right side of the street.");
		routeInfo
				.add("a. From Casa Mila walk up Passeig De Gracia towards the Diagonal metro stop\n"
						+ "b. Take L5 (blue) train from Diagonal towards Vall d¡¯Hebron\n"
						+ "c. Get off at Sagrada Familia stop");
		routeInfo
				.add("a. From La Sagrada Familia take the L5 (blue) train towards Cornella Centre until Diagonal\n"
						+ "b. Change to L3 (green) at Diagonal and take the train towards Trinitat Nova\n"
						+ "c. Get off at Vallcarca stop\n"
						+ "d. Walk down Avenida de Vallcarca until Carrer de les Medes\n"
						+ "e. Make a left on Carrer de les Medes\n"
						+ "f. Follow signs to get to Parc Guell");
		routeInfo.add("Enjoy your trip!");
		return routeInfo;
	}

	public static String getWalkingTime() {
		return "Approx. 2 hours";

	}

	public static String getMetroTime() {
		return "Approx. 79 min";
	}

	public static ArrayList<String> getSiteTime() {
		ArrayList<String> siteTime = new ArrayList<String>();
		siteTime.add("0");
		siteTime.add("15");
		siteTime.add("11");
		siteTime.add("7");
		siteTime.add("14");
		siteTime.add("32");
		return siteTime;
	}

	public static String getIntro() {
		return "The following tour takes you through the all sites which were designed by the Catalan Architect Antoni Gaudi. The first destination will be Palau G¨¹ell, a.k.a G¨¹ell Palace, which is a mansion built by Gaudi for the Catalan industrial industrial tycoon Eusebi G¨¹ell. You can enter the mansion on horse drawn carriages which is a wonderful experience. The tour continues to the next stop Casa Batllo, which is also a mansion standing tall on Passeig de Gracia, one of the most expensive and fashionable avenue in the Barcelona. Here, you can get the real view of Gaudi¡¯s art with the facade having not even a single straight line. From here, the tour goes to Casa Mila that, like previous two buildings, was designed as a mansion for rich American couple on the streets of Passeig de Gracia. It actually consists of two buildings which are structured around two courtyards that provide light to the nine levels: basement, ground floor, mezzanine, main (or noble) floor, four upper floors, and an attic. From here, you will go to Gaudi¡¯s ultimate creation, but ironically, is still under construction. La Sagrada Familia is a building that gets the cover page of any Barcelona tourist guide. Started in 1882, it was a church but later became a minor basilica. It is arguably the best architectural site in whole Barcelona. Going up the tower is also a venture worth experiencing. The last destination, Park G¨¹ell is one of the largest architectural work in the whole Europe. It also gives a perfect end to the tour where you can go and rest in the calm and serene environment. The buildings flanking the entrance, though very original and remarkable with fantastically shaped roofs with unusual pinnacles, fit in well with the use of the park as pleasure gardens and seem relatively inconspicuous in the landscape when one considers the flamboyance of other buildings designed by Gaud¨ª.";
	}
}
