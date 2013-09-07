package edu.gatech.architourists.database;

import java.util.ArrayList;

import edu.gatech.architourists.R;

public class PlacaEspanyaTour {

	/**
	 * Get image id
	 * 
	 * @return
	 */
	public static int getImage() {
		return R.drawable.pl_espanya_tour;
	}

	/**
	 * Get a list of site names on the route
	 * 
	 * @return
	 */
	public static ArrayList<String> getSiteList() {
		ArrayList<String> siteList = new ArrayList<String>();
		siteList.add("Residencia Onix");
		siteList.add("Plaça d'Espanya");
		siteList.add("Les Arenes");
		siteList.add("Parc de Joan Miró");
		siteList.add("Parc de la Espanya Industrial");
		siteList.add("Torres Venecianas");
		siteList.add("Barcelona Pavillion");
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
						+ "c. Get off at Espanya and exit the station.");
		routeInfo
				.add("a. Head north toward Av. Paral·lel\n"
						+ "b. Exit the roundabout onto Gran Via de les Corts Catalanes\n");
		routeInfo.add("a. Head northeast on Gran Via de les Corts Catalanes\n"
				+ "b. Turn left toward Carrer de la Diputació\n"
				+ "c. Turn left onto Carrer de la Diputació\n"
				+ "d. Turn right , Take the stairs");
		routeInfo
				.add("a. Head northwest toward Carrer d'Aragó\n"
						+ "b. Turn left onto Carrer d'Aragó\n"
						+ "c. Continue onto Carrer de Sant Nicolau\n"
						+ "d. Turn right onto Carrer del Rector Triadó\n"
						+ "e. Turn left onto Carrer d'Ermengarda\n"
						+ "f. Turn right onto Plaça d'Antoni Pérez i Moya\n"
						+ "g. Continue onto Carrer de Muntadas\n"
						+ "h. Turn right onto Carrer de Watt\n"
						+ "i. Carrer de Watt turns slightly left and becomes Carrer de l'Autonomia\n"
						+ "j. Turn right onto Carrer de Premià\n");
		routeInfo
				.add("a. Head south toward Carrer de Premià\n"
						+ "b. Slight left onto Carrer de Premià\n"
						+ "c. Turn left onto Carrer de Sants/N-340\n"
						+ "d. At the roundabout, take the 3rd exit onto Av. Reina Maria Cristina");
		routeInfo
				.add("a. Head southeast on Av. Reina Maria Cristina toward Plaça de l'Univers\n"
						+ "b. Turn right onto Plaça de Carles Buïgas\n"
						+ "c. Plaça de Carles Buïgas turns slightly left and becomes Av. de Francesc Ferrer i Guàrdia/Av. Frances Ferrer i Guardia\n"
						+ "d. Slight left onto Plaça de Carles Buïgas");
		routeInfo.add("Enjoy your trip!");
		return routeInfo;
	}

	public static String getWalkingTime() {
		return "Approx. 41 mins / 3.3km";

	}

	public static String getMetroTime() {
		return "18 mins from Onix to Placa Espanya";
	}

	public static ArrayList<String> getSiteTime() {
		ArrayList<String> siteTime = new ArrayList<String>();
		siteTime.add("0");
		siteTime.add("18");
		siteTime.add("1");
		siteTime.add("5");
		siteTime.add("13");
		siteTime.add("13");
		siteTime.add("7");
		return siteTime;
	}
	
	public static String getIntro(){
		return "Because of the newfound knowledged that Barcelona was going to host another International Exhibition, Placa Espanya was created. Upon exiting the metro station, one notices the most prominent features of this massive square, the monument that stands in the center of a massive roundabout. Also included within the Plaza is the old bull fighting ring Shopping Center Las Arenas. This walking tour then takes you to Parc de Joan Miro a few minutes away from the center of this major intersection. Upon return to the main Plaza  one stand two towers that, due to their stylistic allusions to Italian architecture, were dubbed the Venetian Towers (Torres Venecianas). As one walks in between these two massive structures and approaches MNAC, one can see all of the buildings designed for the International Exhibition of 1929. One of the most favorite works of this, directly next to MNAC, is Mies van Der Rohe’s Barcelona Pavilion.";
	}
}
