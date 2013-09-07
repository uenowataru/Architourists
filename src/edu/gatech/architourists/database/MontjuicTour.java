package edu.gatech.architourists.database;

import java.util.ArrayList;

import edu.gatech.architourists.R;

public class MontjuicTour {

	/**
	 * Get image id
	 * 
	 * @return
	 */
	public static int getImage() {
		return R.drawable.montjuic_tour;
	}

	/**
	 * Get a list of site names on the route
	 * 
	 * @return
	 */
	public static ArrayList<String> getSiteList() {
		ArrayList<String> siteList = new ArrayList<String>();
		siteList.add("Residencia Onix");
		siteList.add("Museu Nacional d'Art de Catalunya (MNAC)");
		siteList.add("Fundación Joan Miró");
		siteList.add("Montjuic Castle");
		siteList.add("Montjuic Communications Tower");
		siteList.add("Cementiri de Montjuic");
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
				.add("a. Walk to Arc de Triomf Station from Ressidencia Onix\n"
						+ "b. Take L1 (red) train towards Hospital de Bellvitge and get off at Espanya\n"
						+ "c. Get off and walk southeast  between the Venetian towers along Av Reina Marina Cristina towards the palace on the hill\n"
						+ "d. Ascend the stairs at the bottom of the hill till you reach the palace, this is the Museu national d’Art de Catalunya(MNAC)\n");
		routeInfo.add("a. Turn right toward Av. dels Montanyans\n"
				+ "b. Turn left onto Av. dels Montanyans\n"
				+ "c. Continue straight onto Passeig de Santa Madrona\n"
				+ "d. Turn left onto Carrer de l'Estadi\n"
				+ "e. Slight left onto Carrer Neptú");
		routeInfo
				.add("a. Head northeast on Av. Miramar toward Carrer dels Tarongers\n"
						+ "b. Slight right onto Carrer dels Tarongers\n"
						+ "c. Slight right to stay on Carrer dels Tarongers\n"
						+ "d. Turn left to stay on Carrer dels Tarongers\n"
						+ "e. Turn right toward Carrer del Castell\n"
						+ "Turn left onto Carrer del Castell");
		routeInfo
				.add("a. Head northwest on Carrer del Castell toward Passeig del Migdia\n"
						+ "b. Continue onto Passeig del Migdia\n"
						+ "c. Turn right onto Carrer Can Valero\n"
						+ "d. Turn left onto Carrer Dr. Font Quer\n"
						+ "e. Continue onto Passeig Olímpic\n"
						+ "f. Turn right onto Carrer Pierre de Coubertin");
		routeInfo.add("a. Head southeast on Carrer Pierre de Coubertin\n"
				+ "b. Turn right onto Carrer del Foc\n"
				+ "c. Turn left onto Carrer dels Ferrocarrils Catalans\n"
				+ "d. Continue straight onto Carrer Mare de Déu de Port\n"
				+ "e. Turn left at Carrer dels Motors\n"
				+ "f. Turn right onto Carrer Mare de Déu de Port\n"
				+ "g. Turn left toward Carrer del Cementiri\n"
				+ "h. Continue straight onto Carrer del Cementiri");
		routeInfo.add("Enjoy your trip!");
		return routeInfo;
	}

	public static String getWalkingTime() {
		return "Approx. 1.5 hours/ 6.7km";

	}

	public static String getMetroTime() {
		return "18 mins from Onix to Placa Espanya";
	}

	public static ArrayList<String> getSiteTime() {
		ArrayList<String> siteTime = new ArrayList<String>();
		siteTime.add("0");
		siteTime.add("18");
		siteTime.add("10");
		siteTime.add("22");
		siteTime.add("24");
		siteTime.add("28");
		return siteTime;
	}
	
	public static String getIntro(){
		return "The montjuic tour takes you through placa espanya to all the significant sites on one of the most prominent hills in Barcelona. The first site on the tour is the Montjuic Palace which houses the Museu Nacional d’Art de Catalunya (MNAC) since 1934. It can be seen easily as soon as you get off at Espanya station as it is perfectly framed by the Torres Venecianas. The walk along the road leading up to the castle is beautiful since it has fountains running along both its sides. Before ascending the hill to the palace you will come across the Magic Fountain of Montjuic which has a beautiful light and sound show from 9pm-11:30pm which attracts huge crowds. A ticket to get into the palace and see the exhibitions is 12 Euro if you are interested in art. The next is the Fundación Joan Miró which is a museum of modern art honoring the Catalan painter Joan Miro.The next site is the Montjuic castle and is situated at the top of the hill, it is a fortress built in the 17th century and is open to the public free of charge until 9 pm in the Summer. It offers beautiful 360 views of Barcelona from the roof and an amazing place to watch the sunset over the mountains of Barcelona. After that you should head on to the Olympic Park that was built when Barcelona Hosted the Summer OLympics of 1992. Here you can see the Montjuic Communications Tower built by the world famous Architect Santiago Calatrava. The last site on the tour is the Cementiri de Montjuïc which is Barcelona’s main cemetery and offers great views of gothic and classic architecture.";
	}
}
