package edu.gatech.architourists.database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.gatech.architourists.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ArchitectureDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_SITE, MySQLiteHelper.COLUMN_ARCHITECT,
			MySQLiteHelper.COLUMN_YEAR, MySQLiteHelper.COLUMN_STYLE,
			MySQLiteHelper.COLUMN_PROGRAM, MySQLiteHelper.COLUMN_LOCATION,
			MySQLiteHelper.COLUMN_LATITUDE, MySQLiteHelper.COLUMN_LONGITUDE,
			MySQLiteHelper.COLUMN_COST, MySQLiteHelper.COLUMN_OPENHOURS,
			MySQLiteHelper.COLUMN_RID, MySQLiteHelper.COLUMN_RIDI,
			MySQLiteHelper.COLUMN_TEXT };

	public ArchitectureDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Architecture createArchitecture(String site, String architect,
			String year, String style, String program, String location,
			Double latitude, Double longitude, String cost, String openhours,
			int rid, int ridi, String text) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_SITE, site);
		values.put(MySQLiteHelper.COLUMN_ARCHITECT, architect);
		values.put(MySQLiteHelper.COLUMN_YEAR, year);
		values.put(MySQLiteHelper.COLUMN_STYLE, style);
		values.put(MySQLiteHelper.COLUMN_PROGRAM, program);
		values.put(MySQLiteHelper.COLUMN_LOCATION, location);
		values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
		values.put(MySQLiteHelper.COLUMN_LONGITUDE, longitude);
		values.put(MySQLiteHelper.COLUMN_COST, cost);
		values.put(MySQLiteHelper.COLUMN_OPENHOURS, openhours);
		values.put(MySQLiteHelper.COLUMN_RID, rid);
		values.put(MySQLiteHelper.COLUMN_RIDI, ridi);
		values.put(MySQLiteHelper.COLUMN_TEXT, text);
		long insertId = database.insert(MySQLiteHelper.TABLE_ARCHITECTURE,
				null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Architecture newComment = cursorToArchitecture(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteArchitecture(Architecture architecture) {
		long id = architecture.getId();
		System.out.println("Architecture deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_ARCHITECTURE,
				MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Architecture> getAllArchitectures() {
		List<Architecture> architectures = new ArrayList<Architecture>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Architecture architecture = cursorToArchitecture(cursor);
			architectures.add(architecture);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return architectures;
	}

	/**
	 * Get architecture by site name
	 * 
	 * @param site
	 * @return
	 */
	public Architecture getArchitecture(String site) {
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				allColumns, MySQLiteHelper.COLUMN_SITE + "=?",
				new String[] { site }, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Architecture a = cursorToArchitecture(cursor);

		// Make sure to close the cursor
		cursor.close();
		return a;
	}

	/**
	 * Get architecture by its id
	 * 
	 * @param Id
	 * @return
	 */
	public Architecture getArchitecture(long Id) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				allColumns, MySQLiteHelper.COLUMN_ID + "=?",
				new String[] { String.valueOf(Id) }, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Architecture a = cursorToArchitecture(cursor);


		// Make sure to close the cursor
		cursor.close();
		return a;
	}

	/**
	 * Get a list of site names in List<String>
	 * 
	 * @return
	 */
	public List<String> getSiteList() {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				new String[] { MySQLiteHelper.COLUMN_SITE }, null, null, null,
				null, null);

		List<String> listOfSite = new ArrayList<String>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			listOfSite.add(cursor.getString(0));
			cursor.moveToNext();
		}

		// Make sure to close the cursor
		cursor.close();
		return listOfSite;
	}

	/**
	 * Get a list of key-value pairs of <architect, list of sites>
	 * 
	 * @return
	 */
	public Map<String, List<String>> getArchitectList() {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				new String[] { MySQLiteHelper.COLUMN_ARCHITECT,
						MySQLiteHelper.COLUMN_SITE }, null, null, null, null,
				null);

		// List<String> listOfArchitect = new ArrayList<String>();
		Map<String, List<String>> architectList = new LinkedHashMap<String, List<String>>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String architect = cursor.getString(0);
			String site = cursor.getString(1);
			if (architectList.containsKey(architect)) {
				architectList.get(architect).add(site);
			} else {
				List<String> sitelist = new ArrayList<String>();
				sitelist.add(site);
				architectList.put(architect, sitelist);
			}
			cursor.moveToNext();
		}

		// Make sure to close the cursor
		cursor.close();
		return architectList;
	}

	/**
	 * Get a list of key-value pairs of <style, list of sites>
	 * 
	 * @return
	 */
	public Map<String, List<String>> getStyleList() {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				new String[] { MySQLiteHelper.COLUMN_STYLE,
						MySQLiteHelper.COLUMN_SITE }, null, null, null, null,
				null);

		// List<String> listOfArchitect = new ArrayList<String>();
		Map<String, List<String>> styleList = new LinkedHashMap<String, List<String>>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String styles = cursor.getString(0);
			String site = cursor.getString(1);
			Scanner s = new Scanner(styles);
			s.useDelimiter(", ");
			while (s.hasNext()) {
				String style = s.next();

				if (styleList.containsKey(style)) {
					styleList.get(style).add(site);
				} else {
					List<String> sitelist = new ArrayList<String>();
					sitelist.add(site);
					styleList.put(style, sitelist);
				}
			}
			cursor.moveToNext();
		}

		// Make sure to close the cursor
		cursor.close();
		return styleList;
	}

	public Map<String, List<String>> getPeriodList() {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARCHITECTURE,
				new String[] { MySQLiteHelper.COLUMN_YEAR,
						MySQLiteHelper.COLUMN_SITE }, null, null, null, null,
				null);

		// List<String> listOfArchitect = new ArrayList<String>();
		Map<String, List<String>> architectList = new LinkedHashMap<String, List<String>>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String year = cursor.getString(0);
			String site = cursor.getString(1);

			int startyear = Integer.parseInt(year.substring(0, 4));
			if (startyear - (startyear / 100) * 100 >= 50) {
				year = year.substring(0, 2) + "50 ~ " + year.substring(0, 2)
						+ "99";
			} else {
				year = year.substring(0, 2) + "00 ~ " + year.substring(0, 2)
						+ "49";
			}

			if (architectList.containsKey(year)) {
				architectList.get(year).add(site);
			} else {
				List<String> sitelist = new ArrayList<String>();
				sitelist.add(site);
				architectList.put(year, sitelist);
			}

			cursor.moveToNext();
		}

		// Make sure to close the cursor
		cursor.close();
		return architectList;
	}

	private Architecture cursorToArchitecture(Cursor cursor) {
		Architecture a = new Architecture();
		a.setId(cursor.getLong(0));
		a.setSite(cursor.getString(1));
		a.setArchitect(cursor.getString(2));
		a.setYear(cursor.getString(3));
		a.setStyle(cursor.getString(4));
		a.setProgram(cursor.getString(5));
		a.setLocation(cursor.getString(6));
		a.setLatitude(cursor.getDouble(7));
		a.setLongitude(cursor.getDouble(8));
		a.setCost(cursor.getString(9));
		a.setOpenhours(cursor.getString(10));
		a.setRID(cursor.getInt(11));
		a.setRIDi(cursor.getInt(12));
		a.setText(cursor.getString(13));
		return a;
	}

	public boolean enterDataOnFirstLaunch() {
		open();
		createArchitecture(
				"Agbar Tower",
				"Jean Nouvel",
				"2004",
				"Contemporary",
				"Office Space",
				"Avinguda Diagonal, 211, 08018 Barcelona",
				41.4033,
				2.1894,
				"N/A",
				"Not open to public",
				R.drawable.agbar_tower_b,
				R.drawable.agbar_tower_i,
				"Agbar Tower, which is actually acronym for Agua (Ag) Barcelona (bar), is a recent addition to the Barcelona skyline but is not open to the general public and has no public viewing deck. It is a 38 story (four underground, thirty-four above ground), 144m / 474ft tall bullet looking building with 4400 windows. It has many other derogatory names as well \n\nThe building has a double facade, meaning two faces. These are made up of two concentric concrete oval cylinders, which do not come in contact with each other. A dome of glass and steel, which gives the tower its characteristic shape of a bullet, covers the outer cylinder, which completely encases the inner cylinder.  The inner cylinder is where the lifts stairs, and facilities are located. \n\nOne of the most characteristic elements of the building, which really brings out its awe and shape, is its nocturnal illumination. The windows of the building comprise of more than 4,500 luminous devices that can operate independently using LED technology and enables the generation of images on the outside of the tower. The system is capable of creating 16 million colors, thanks to a sophisticated system of hardware and software. It has the ability to quickly transition between colors, which can create a shocking effect. The system of the building, dubbed by its creator Yann Kersalé as diffraction, who defined it as 'a vaporous cloud of color that seeks moiré', is often used in the celebration of various events. \n\nWhat is great about the construction of this building, even before taking into consideration the lights, is the play it has on the skyline with the Sagrada Familia. The two buildings are completely different in style but they both have such an influence on the skyline of Barcelona. In different postcards and images of Barcelona, you will usually see these next to each other, even though they are quite apart. It shows a very contrasting image of the styles of architecture that have passed through the history of the city, and that is wonderful.\n\nIn the design of the Agbar Tower, Nouvel actually said he rejected the prevailing opinion in North America of what a skyscraper should look like, hence the phallic shape. It is the architect's intention to give the impression of land that is emerging in a special way out of the ground. The use of the tower by a water utility company led him to the design: a metaphor of a geyser sprouting from the deep sea.   \n\nIt is said however by critics that a flaw in the architecture is that the structure does not have a great idea on how the building meets the ground, even with that as its intended design, and that this situation itself ruins the overall design.");
		createArchitecture(
				"Arc de Triomf",
				"Josep Vilaseca y Casanovas",
				"1888",
				"Neo-Mudéjar",
				"Monument",
				"Passeig Lluis Companys, Barcelona",
				41.3908,
				2.1806,
				"Free",
				"24/7",
				R.drawable.arc_de_triomf_b,
				R.drawable.arc_de_triomf_i,
				"Origin: Built for the World Expo 1888.\n\nThe arch was designed by the noted Catalan architect Josep Vilaseca as a symbol of Catalan pride for welcoming the nations for World Expo 1888. Unlike many triumphal arches, like in Arc de Triomphe Paris, it is made of red bricks than sandstone or marble. Another striking feature of the arch is that the architecture is inspired by Muslim architecture. The style is known as “Mudéjar” which emerged during the 12th century on the Iberian Peninsula. The style was created by the Moors and Muslims who remained in the area after the Christians had recaptured and repopulated the whole Iberian Peninsula.\n\nThe arch is dominated by its several beautiful stone carvings. Above the arch itself, visitors can see carvings depicting the Barcelona coat of arms with a small crown above it. On either side of the Barcelona coat of arms, one can see the coat of arms of the other provinces in Spain. The relief on one side depicts a prize giving ceremony and is known as “Recompense”. The relief on the other side is known as “Barcelona rep les nacions”, Catalan for “Barcelona welcomes the nations”. It represents the various countries' participation in the World Expo and Barcelona thanking them for their attendance.\n\nFurther, there is a man in the middle who bears the Barcelona coat of arms on his chest. The crosses, which are red on a white background, represent the patron saint of Barcelona; Saint George. The striped sections, which are red on a yellow background, represent the Catalonian coat of arms. The legend has it that the red stripes are the blood from a mortal wound the Barcelona Count, Wilfred the Hairy, suffered during the defense of Barcelona against the Moors in the 9th century. The blood is said to have been painted on the Count’s golden shield by the French ally, Charles the Bold, as a sign of gratitude.\n\nSurrounding the arch stands twelve angels representing fame. Arc de Triomf is crowned by yet another coat of arms. The one at the top represents the arms of the Spanish monarchs. The pillars held by the lions are known as the Pillars of Hercules, which is the ancient name given to the Strait of Gibraltar. Under the lions, even though it can be hard to spot from ground, stands the words “Plus Ultra” – a motto which means “Further and beyond” in Latin.");

		createArchitecture(
				"Barcelona Cathedral",
				"Josep O. Mestres",
				"1448",
				"Gothic",
				"Church",
				"Plaça de la Seu, 08002 Barcelona",
				41.38389,
				2.17639,
				"Free \nVisit to Choir- 2.80 (general), 2.50 (groups) \nVisit to Rooftop - 3.00 (general), 2.50 (groups)",
				"Mon-Sat: 8:00-12:30, 17:15-19:30\nCLOSED: Sunday",
				R.drawable.barcelona_cathedral_b,
				R.drawable.barcelona_cathedral_i,
				"The Barcelona Cathedral is the seat of the Archbishop of Barcelona. Gaudi's La Sagrada Familia may be Barcelona's most famous landmark (and rightly so), but La Seu, which is another name for the Cathedral, still holds it own as one of the most impressive cathedrals in Spain.  The rich detail in the Cathedral is something notable of many churches of the Gothic period. It is truly remarkable how the designs of the old times were made by hand, using no technology but a couple of rulers and pencils and papers.  A blending of medieval and Renaissance styles, Barcelona's cathedral features large bell towers covered in Gothic pinnacles, high Gothic arches, a handsomely sculptured choir and many side chapels with rich altarpieces. The interior was recently cleaned. Especially notable is the Cappella de Sant Benet behind the altar, with a magnificent 15th-century interpretation of the crucifixion by Bernat Matorell.\n\nSuch rich history as well goes with this magnificent establishment. The cathedral is dedicated to Eulália of Barcelona, co-patron saint of Barcelona, a young virgin who, according to Catholic tradition, suffered martyrdom during Roman times in the city. One story says that she was exposed naked in the public square and a miraculous snowfall in mid-spring covered her nudity. The enraged Romans put her into a barrel with knives stuck into it and rolled it down a street (according to tradition, the one now called Baixada de Santa Eulàlia). The body of Saint Eulalia is entombed in the cathedral's crypt.\n\nOne of the most beautiful parts of the Cathedral, with a wonderful play of light coming through the top, is the cloister, with its vaulted galleries overlooking a lush garden filled with orange, medlar and palm trees and a mossy central pond. Underneath the well-worn slabs of its stone floor are the tombs of key members of the Barri Gòtic's ancient guilds.\n\nThe structure sits on the site of an ancient Roman basilica built in 343 A.D. The Basilica was destroyed in 985. A Roman cathedral, built between 1046 and 1058, replaced the ruined basilica. Between 1257 and 1268, a chapel, the Capella de Santa Llucia, was added.\n\nIn addition to Saints Eulàlia and Olegarius, the cathedral contains the tombs of Saint Raymond of Penyafort, Count Ramon Berenguer I and his third wife Almodis de la Marche, and bishops Berenguer de Palou II, Salvador Casañas y Pagés, an Arnau de Gurb, who is buried in the Chapel of Santa Llúcia, which he had constructed and which was the only thing left after 1268, when the entire prior structure except for the Santa Llucia Chapel was demolished to make way for the Gothic cathedral.");
		createArchitecture(
				"Barcelona Pavilion",
				"Ludwig Mies Van der Rohe",
				"1929",
				"Modernism",
				"Pavilion",
				"Av. Francesc Ferrer i Guàrdia, 7, 08038 Barcelona",
				41.3706,
				2.15,
				"General entry- 5.00 \nStudent price- 2.60",
				"10:00-20:00",
				R.drawable.barcelona_pavillion_b,
				R.drawable.barcelona_pavillion_i,
				"This pavilion was designed as the German Pavilion for the 1929 International Exposition in Barcelona.  The event was 6 months long and afterwards the pavilion was destroyed because the German government didn’t find a buyer. After many years, however, In 1980 Oriol Bohigas put the project for reconstruction in motion in 1980, designating the architects Ignasi de Solà-Morales, Cristian Cirici and Fernando Ramos to research, design and supervise the reconstruction.  It became a keypoint in Mies Van der Rohe’s career and also of the century as emblematic of the Modernist Movement.  As shocking as it may seem, the Pavilion itself had no real use.  The work of Mies is very much brought out in this pavilion with his ongoing motto through his career, “less is more.” The Pavilion is asymmetric and fluid with a continuous flow of space.  Wall transitions are made of both glass and marble. \n\nMies always tried to use the most minimal structure possible in his buildings in order to keep them standing, an idea so grand and formal and aesthetically pleasing. It gave his building feminist characteristics, making them look slim and beautiful. He always worked on a grid and he always designed everything down to where the furniture was placed.  In the pavilion we can see this with the mullions and floor. The 4 corner mullions and floor cracks line up perfectly.  The mullions also, as a way to show minimalism, are in the shape of a cross in order to take away the most of the structure possible, visually. Mies was known also for using the finest of materials in all of his structures. Here, the Pavilion uses 4 different types of marble, Roman Travertine, Green Alpine, Ancient Green, and Golden Onyx, and 4 different types of glass, transparent, milky white, translucent, and opaque black. \n\nMies wanted to give off the feel of enclosed space, without ever having the space actually enclosed. Also, Mies always tried to make a sense, at some point, of looking from a non-enclosed space, through the “enclosed” space, to another non-enclosed area.  This can be seen by the way the walls are partitioned to where they do not make enclosing corners. \n\nToday the pavilion just serves as a landmark and a site for observation in its original location next to MNAC.");

		createArchitecture(
				"Basilica de La Sagrada Familia",
				"Antoni Gaudi",
				"1882 - Unfinished",
				"Modernism, Art Nouveau",
				"Church",
				"Carrer de Mallorca, 401 08013 Barcelona",
				41.4039639,
				2.1751516,
				"General entry- 17 \nStudent discount- 13 \nOther discount- 16% with Barcelona Card",
				"Apr-Sep: 9:00-20:00\nOct-Mar: 9:00-18:00\nDec 25&26 and Jan 01&06: 9:00-14:00",
				R.drawable.sagrada_familia_b,
				R.drawable.sagrada_familia_i,
				"The Basílica i Temple Expiatori de la Sagrada Família or in English The Basilica and Expiatory Church of the Holy Family is an infamous project known for the fact that it is still under construction. Construction for this site began in 1882 but Architect Antoni Gaudi did not get involved until 1883 mixing Gothic and Art Nouveau elements to the basilica. Upon Gaudi’s death in 1926, La Sagrada Familia was only between 15 and 25 percent complete and when asked about the slow construction process Gaudi responded “my client is not in a hurry.” Construction resumed until the Spanish Civil War in 1936. The church was never designed to be a cathedral (the seat of a bishop), which is why in November of 2010 it was consecrated by Pope Benedict XVI as a minor basilica. \n\nThe church, upon completion, will have three grand façades: the Nativity facade to the East, the Passion facade to the West, and the Glory facade to the South (this one has not been completed). The Nativity facade is dedicated to the birth of Jesus and is decorated with scenes that allude to the creation of life. The Passion facade is dedicated to the Passion of Christ (Jesus’s suffering during His crucifixion) and is reduced to a barebones (very simple) style of construction. The Glory facade (not completed) is going to show the ascension of Christ to heaven and the road believers have to take to get there. This facade will also depict Hell and purgatory.");
		createArchitecture(
				"Camp Nou",
				"Francesc Mitjans, Josep Soteras, Lorenzo García-Barbón",
				"1957",
				"Contemporary",
				"Sports Arena",
				"Carrer d'Aristides Maillol, s/n, 08028 Barcelona",
				41.38087,
				2.122802,
				"General entry- 24.00 \nStudent price- 17.00",
				"Apr 2 - Oct 7: 10:00-20:00 \n Rest of year: 10:00-18:30 \n CLOSED: Jan 1 & 6, and Dec 25 & 26\n Dec 31: closes at 14:30\n Days of important matches: closes at 15:00",
				R.drawable.camp_nou_b,
				R.drawable.camp_nou_i,
				"Soccer is a sport that has a huge impact on the people of Spain. Barcelona also follows this trend. Their team, named 'FCB' i.e. futbol club de Barcelona, is not only of the most famous soccer club, but most successful as well. Camp Nou is their fortress and has a significant impact on the Catalan capital of Barcelona. Apart from being the home club of a huge club, Camp Nou is the largest stadium in Europe and 13th largest stadium in whole world. Constructed in 1957, the first match was played in the same year, and since then, has been an important center in the city. It was designed and planned by Francesc Mitjans, Josep Soteras, Lorenzo García-Barbón and follows a contemporary style of architecture. It has an oval shape that has been made to host more people at one time, which can be explained by the fact that it has a seating capacity of 99,786 people. \n\nApart from the stadium, it also has a museum and a whole tour named the FCB Experience. The tour not only gives you a chance to look at the stadium, but also provides an opportunity to visit players' locker rooms. Further, it takes you through trophy house, which has all the collection of trophies of the competitions and leagues won by the team. As they say, it makes Barça “mes que un club”- more than a club.");
		createArchitecture(
				"Casa Mila",
				"Antoni Gaudi",
				"1907",
				"Modernism, Art Nouveau",
				"Housing, Musem",
				"Carrer de Provença, 261, 08008, Barcelona",
				41.3953008,
				2.1620185,
				"General entry-16.50 \nStudent price- 14.85 \n",
				"Nov-Feb: 9:00-18:30\nMar-Oct: 9:00-20:00\nCLOSED: Dec 25&26 and Jan 1&6",
				R.drawable.casa_mila_b,
				R.drawable.casa_mila_i,
				"Casa Mila, also known as la Pedrera (meaning “The Quarry”), was completed in 1912 and was one of Gaudi’s most iconic buildings for its lack of 90-degree angles, which was signature of his designs.  It was this particularly amazing feature of his structures that made his buildings so amazing and sought out by public for their out-of-the-ordinary looks. \n\n This amazing house was commissioned by Mr. Pere Mila, whom was married to a rich widow, Mrs. Roser Segimon. They wanted to live in a spectacular house in the most cosmopolitan avenue of the Barcelona of the time: the Passeig de Gracia. Gaudí was their choice of architect, mainly because he had just built the nearby Casa Batlló building, which was considered the very latest example of the modernist architecture at the time. Mila wanted his house to be even bigger and more monumental than Casa Batlló. \n\nIt was a controversial design in its time for the bold forms of the undulating stone facade and wrought iron decoration of the balconies and windows, designed largely by Josep Maria Jujol, who also created some of the plaster ceilings. Architecturally it is considered an innovative work for its steel structure and curtain walls.  The façade itself is self-supporting.  This means that it is free of the functions of a load-bearing wall, which connects to the internal structure of each floor by means of curved iron beams surrounding the perimeter of each floor. This construction system sets large openings in the façade, giving light to the homes and free structuring of the different levels, thus the walls can be demolished without affecting the stability of the building. This allows the owners to change their minds at will and to modify the interior layout of the homes without problems. \n\nOther innovative elements were the construction of underground car parking and separate lifts and stairs for the owners and their servants.  In the time of its design, the use of cars was just becoming a norm, and thus Gaudi had wanted to design an innovative way to get the cars up to the apartments themselves with a spiral parking deck like ramp. Unfortunately, the site was not large enough and this design idea failed.\n\nOne of the most significant parts of the building is the roof, crowned with skylights or staircase exits, fans, and chimneys. All of these elements, constructed with timber coated with limestone, broken marble or glass, have become real sculptures integrated into the building.");
		createArchitecture(
				"Casa Batllo",
				"Antoni Gaudi",
				"1877",
				"Modernism, Art Nouveau",
				"Housing, Musem",
				"Passeig de Gràcia, 43, 08007 Barcelona",
				41.3917,
				2.165,
				"General entry- 20.35 \nStudent price- 16.30 \nGroup price (+20 students)- 17.30",
				"9:00-21:00 Everyday",
				R.drawable.casa_batllo_b,
				R.drawable.casa_batllo_i,
				"A Gaudí masterpiece, this house is a remodel of a previously built house. In 1904, the Batlló family bought the house and commissioned Antoni Gaudí to redesign it. It is locally dubbed Casa dels ossos (House of Bones) for its skeletal and organic quality. This house, like all of Gaudí’s work, is in the Modernisme or Art Nouveau style. The front façade is decorated with mosaic made of broken ceramic and glass pieces, a technique called trencadís, leading up to an arched roof that resembles a dragon. The cross at the top, along with the allusion to the dragon, is a representation of the story of Saint George, the patron saint of Catalonia, who slayed a dragon.\n\nIn 1954, an insurance company named Seguros Iberia bought Casa Batlló and set up offices inside following the Batlló family’s death. It wasn’t until 1993 that the current owners of the site bought the home and continued to refurbish it. It is now open to the public to tour and only one person still remains a resident.");
		createArchitecture(
				"Cementiri de Montjuic",
				"Josep M. Jujol, Josep Puig i Cadafalch and Eusebi Arnau",
				"1883",
				"Classic, Gothic",
				"Cementary, Garden, Park",
				"Carrer de la Mare de Déu de Port, 56, 08038 Barcelona",
				41.353889,
				2.155556,
				"Free",
				"08:00-18:00 Everyday",
				R.drawable.cementiri_de_montjuic_b,
				R.drawable.cementiri_de_montjuic_i,
				"The cemetery was opened as Barcelona’s main cemetery, superseding the cemetery in Poble Nou in the east. The slope of Montjuic was chosen as the site of the “new” cemetery, in order to keep away from the pressures of urban housing developments.  This was believed to help because at this time in history it was believed that burial sites should be kept away from the cities, rather than being inherent towards the city.  The hillside of the mountain is what gives the site its special character, compared to many other cemeteries that are on flat land, with winding paths and terraced areas looking seaward.  There is such a rich juxtaposition in the cemetery between the different styles of architecture and art: Classic and Gothic styles, along with Art Nouveau.\n\nA walk through this haven of peace reveals valuable funerary architecture and sculpture, in a setting comprising Mediterranean plants and trees, and magnificent sea views. It is truly a site to see.");

		createArchitecture(
				"Fundacio Antoni Tapies",
				"Lluís Domènech i Montaner",
				"1984",
				"Modernism, Contemporary",
				"Museum",
				"Carrer d'Aragó, 255, 08007 Barcelona",
				41.391667,
				2.163611,
				"General entry- 7.00 \nStudent price- 5.60 ",
				"Tue-Sun: 10:00-19:00",
				R.drawable.fundacio_antoni_tapies_b,
				R.drawable.fundacio_antoni_tapies_i,
				"Located in Carrer d’Arago (about a block and a half away from Casa Batlló) this museum is mainly dedicated to the life and works of the painter Antoni Tapies. It was created in 1984 by Tapies to promote the study and knowledge of modern and contemporary art. The building itself was design by the Modernist architect Lluis Domenech i Montaner under the former name Editorial Montaner i Simon publishing house. It opened as the Fundacio in June of 1990. The Fundació’s building is 'hemmed in' between the two side walls of the adjacent buildings. To elevate its height and underscore its new identity, Antoni Tàpies created the sculpture crowning the building entitled Núvol i cadira (Cloud and Chair, 1990). This sculpture represents a chair jutting out of a large cloud. The chair, a recurring motif in Tàpies’ works, alludes to a meditative attitude and to aesthetic contemplation.\n\nThis building’s façade combines classical stylistic influences, which are visible on the central doorway and the two symmetrical lateral volumes, and Muslim influences, as seen in the use of unpolished brick, classical Mozarabic elements and the arabesque-like geometrical composition.\n\nAmong paintings, sculptures and drawings, books, and engravings, the collection has examples of every aspect of Tàpies’ artistic output and of the different typologies, techniques and materials he uses. The collection includes a selection of drawings and portraits from the forties, a major group of the matter paintings of the fifties and sixties, a large number of object works from the sixties and early seventies, and others done with foam-rubber or the spray technique, varnishes and fireclay from the eighties, and objects and sculptures done over the nineties, in which he experimented with new materials such as metal sheets, sometimes used as pictorial supports, or bronze. Further, the museum has an auditorium, a library exhibition gallery, a terrace, an atrium and a museum shop.");
		createArchitecture(
				"Gran Teatre de Liceu",
				"Miquel Garriga i Roca and Josep Oriol Mestres",
				"1847",
				"Eclecticism",
				"Theatre, Concert Hall",
				"La Rambla, 51, 08002 Barcelona",
				41.380278,
				2.173611,
				"Depends on schedule, check www.liceubarcelona.cat",
				"Mon-Thu, Sun: until midnight \n Fri: until 2:00 \n Sat: the whole day",
				R.drawable.gran_teatre_de_liceu_b,
				R.drawable.gran_teatre_de_liceu_i,
				"Miquel Garriga i Roca (1847); Josep Oriol Mestres (1862); Ignasi de Sola-Morales (1999)\n\nAlso called as ‘Barcelona’s opera house’, it was built in 1847 and was designed by Miquel Garriaga i Roca, subsequently assisted by Josep Oriol Mestres. Selling shares funded the project, which meant that many of the boxes and seats were to be privately owned. The shareholders formed the Societat del Gran Teatre del Liceu, known as the “Societat de Propietaris” (Society of Owners). One of the important events that happened in the history of the building is the 1994 fire, which destroyed the Auditorium and stage. It was decided to rebuild and improve the emblematic building and to create a new legal framework to put it under public ownership. This time, the project was rebuilt on the basis of pre-existing Refurbishment and Enlargement Project, drawn up in 1986 by Ignasi de Solà-Morales with later input (1986) from Xavier Fabré and Lluís Dilmé. It opened again in 1999.\n\nAs for architecture of the building, it can be elaborated in 6 parts. The Vestibule, which is one of the areas saved in the 1994 fire, is of an eclectic style, based on the Renaissance, a style known in the mid nineteenth century as “Florentine” and which was the more or less free interpretation given to the neo-Classical style in the reign of Isabel II. The Main Hall, explicitly inspired by Milan's Teatro alla Scala, is laid out in the shape of a horseshoe (maximum depth and width of 33 and 27 meters), with stalls and five levels, making it one of the most outstanding auditoria of nineteenth-century European architecture. It can host around 2,292 people, making it one of the largest opera houses in Europe. \n\nSaló de Miralls, one of the areas saved in the fire, is a public meeting area. The room, with its ceiling paintings and the curious texts referring to art and music that run along the tops of the walls, is a space where the atmosphere of the old Liceu survives intact. The Foyer is a rest and public meeting area below the main hall. It is a multi-purpose space used both as a café during intervals and as a room for small-format concerts and performances, information sessions prior to each opera performance and as a venue for business and social events. \n\nThe stage is the largest area in the whole of the theatre and the core around which the entire building is organized. It is fitted with equipment of a large scope and complexity which allows a great deal of flexibility in assembling and striking productions, scene changes and alternating titles in the program. The Espai Liceu, the last one, is on the first floor of the basement, with access from La Rambla and Carrer de Sant Pau, and connected to the Foyer.\n\nThe starting point for tours of the Liceu, it is also a cultural and commercial area for promoting the theatre and lyrical music. There is a small, spherical-shaped auditorium where visitors can listen to music and watch projections of operas, a media library, a café and the commercial area related to music and the arts. It also includes the box office.");
		createArchitecture(
				"Hospital Santa Creu y Sant Pau",
				"Lluís Domènech i Montaner",
				"1930",
				"Modernism, art nouveau",
				"Hospital",
				"Carrer Sant Quintí, 89, 08026 Barcelona",
				41.412778,
				2.174444,
				"Free",
				"Museum - Mon-Sun: 10:00-14:00 (information office)\n CLOSED: Dec 25 & 26, Jan 1 & 6 ",
				R.drawable.hospital_sant_creu_y_sant_pau_b,
				R.drawable.hospital_sant_creu_y_sant_pau_i,
				"UNESCO World Heritage Site 1997\n\nCurrently called the Hospital de Sant Pau, this complex, located in the neighborhood of El Guinardo, was built between 1901 and 1930. This is designed by the same architect (Lluis Domenech i Montaner) as Palau de La Musica Catalana. It was a fully functional hospital until 2009 when it began restoration for use as a museum and cultural center. The current buildings date from the 20th century though the named Hospital de Sant Pau was founded in 1401. The architectural complex of Hospital de la Santa Creu i Sant Pau is an unmistakable landmark in the culture and heritage of Barcelona city in particular and in Catalonia in general. Its architectural and patrimonial value extends beyond its urban location and the site has won European and international acclaim. In 1997, together with the Palau de la Música Catalana (one of the world's leading concert halls), the Hospital was declared World Heritage by UNESCO for its singular architectural and artistic beauty. The hospital is one of the biggest (area) site that also holds some architectural significance. It covers around an area of 6 Eixample blocks.");
		createArchitecture(
				"La Monumental",
				"Manuel Joaquim Raspall i Mayol, Ignasi Mas i Morell, Domènec Sugrañes i Gras",
				"1915",
				"Noucentisme,Neo-Mudejar, Byzantine",
				"Bull Ring",
				"Gran Via de les Corts Catalanes, 749, 08013 Barcelona",
				41.399672,
				2.181142,
				"Bullfight Museum- 4.00 \nBullring is used for various events, and thus has different prices",
				"Mon-Fri: 11:00- 23:00\n​Sat-Sun: 10:00- 12:00",
				R.drawable.la_monumental_b,
				R.drawable.la_monumental_i,
				"Plaza Monumental de Barcelona or simply La Monumental, located at the intersection of Gran Via and Carrer Marina in the Eixample district, was the last bullfighting arena in commercial operation in Catalonia. Currently, this building holds the bullfighting museum of Barcelona in which famous bullfighting artifacts are displayed. The buildings original style was noucentista (a style that came after the Catalan Modernisme as its opposition) and was designed by Manuel Joaquim Raspall i Mayol. This structure was later expanded later by Ignasi Mas i Morell and Domenec Sugranes i Gras and was given its current facade. This new facade is strongly influenced by Mudejar and Byzantine architecture. Originally dubbed Plaza de El Sport in 1914, the expansion efforts for this ring began immediately and continued until its renaming (to its current name) in 1916. This arena has a capicity of 19,582 for bullfights or about 25,000 for concerts (part of the ring can be used for spectators).");
		createArchitecture(
				"Les Arenes",
				"August Font i Carreras",
				"1900",
				"Moorish",
				"Bullring, Mall",
				"Gran Via de les Corts Catalanes, 373-385, 08015 Barcelona",
				41.3761,
				2.14946,
				"Free \n1.00 (Access to rooftop)",
				"Mall: Mon-Sat: 10:00-22:00 \n Museum: Tue-Sun: 10:00-22:00 ",
				R.drawable.les_arenes_b,
				R.drawable.les_arenes_i,
				"Once an ancient bull fighting ring, this building built in Plaza Espanya has a capacity for 14,893 people. Currently it is called Centro Comercial de Las Arenas. The idea of building Las Arenas came from the fact that the bullring of Barceloneta, built in 1834, had become too small to the increase in population. Las Arenas opened as a bullfighting ring in June 29, 1900.\n\nDuring the Spanish Civil War, this became republican army barracks. Other sporting events such as boxing, circuses and festivals were hosted after the end of the civil war. The last bullfight was held on June 19, 1977. It currently is a 30,000 square meter commercial complex that was inaugurated on March 24, 2011.");
		createArchitecture(
				"MACBA",
				"Richard Meier",
				"1995",
				"Contemporary, Rationalist",
				"Museum",
				"Plaça dels Àngels, 1, 08001 Barcelona",
				41.383333,
				2.166944,
				"General entry- 9.00 \nStudent price- 7.00",
				"Mon, Wed-Fri: 11:00-19:30pm \n Sat: 10:00-20:00 \n Sun & Hdys: 10:00-15:00  \n CLOSED: Tue (except Hdys), Dec 25, Jan 1",
				R.drawable.macba_b,
				R.drawable.macba_i,
				"This building is designed by the renowned American architect ‘Richard Meier’ who was the architect of the ‘High Museum Art’ in Atlanta built in 1983. Known for his rationalist buildings and the use of white color, the museum also follows the same pattern and its white color also sets it apart from Barcelona’s oldest streets and buildings. This is one of the reasons it is referred to by the local media as ‘the pearl’. Situated amongst the old architecture and narrow streets just a few blocks from Gothic center of Barcelona, its architectural style has strong references to Modernism.\n\nThe MACBA Foundation manages the permanent collection, which dates from the mid-20th century onward. There are three periods of modern art represented: the first one covers the forties to the sixties; the second spans the sixties and seventies; the third period is contemporary. The collections focus on post-1945 Catalan and Spanish art, although some International artists are also represented. One of the interesting facts about the museum is its location.\n\nMeier’s initial plan didn’t coincide with the Barcelona authorities in placing the museum in the old city. But, as they agreed, the museum became much more than a tourist location. It can be shown by the fact that, the ‘podium’ that was built on the one side now serves as a prominent social gathering point and a “stage” for large outdoor art. If you visit the museum in the evening, you can see the natives from the neighborhood using it as their public space. The podium serves as one of the main skateboarding point for the kids in the Barcelona. Thus, the museum not only acts as a tourist destination as an exhibition of art, but also takes you through the social fabric of the old city, La Raval and makes it worth going there.");
		createArchitecture(
				"Fundación Joan Miró",
				"Josep Lluis Sert",
				"1975",
				"Modernism, Mediterranean",
				"Museum",
				"Parc de Montjuïc, s/n, 08038 Barcelona",
				41.368611,
				2.16,
				"General entry- 11.00 \nStudent price- 7.00",
				"Oct-Jun: Tue-Sat: 10:00-19:00 \n Jul-Sep: Tue-Sat: 10:00-20:00\n Thurs: 10:00-21:30 \n Sun & Hdys: 10:00-14:30 \nCLOSED: Mon except Holidays",
				R.drawable.miro_museum_b,
				R.drawable.miro_museum_i,
				"Opened to public in 1975, the construction of this museum started in 1972 i.e. it took three years before it came into existence. Also known by the name of ‘Fundació Joan Miró’, the museum is a part of foundation, built in 1972, with a vision to encourage young artists to experiment with contemporary art. Based on the Modernisme style of architecture, this site offers a moment of beatitude and calm. Not only the works presented in the museum is exceptional, but also the physical fabric of museum is worth a view. Designed by Josep Lluis Sert, the building has courtyards and terraces, which also create a natural path for visitors to move through the building.\n\nSituated in the magnificent environment of Montjuic, full of natural light, Miró’s iconic figures like birds, women and stars produce an impressive atmosphere. It was expanded in 1986 and as of now, it contains almost 15,000 pieces of art. Alongside more than 8,000 drawings one can find 217 paintings, 178 sculptures as well as ceramics, textiles and graphic works. One of the interesting facts about the museum is a separate space in the museum named ‘Espai 13’. The reason why this space was created holds in the belief of Miro of promoting emerging artists. Miró dedicated his pieces to the Fundació to be displayed only under the condition that a space be reserved to exhibit the works of emerging artists. In keeping their word, the Fundació created Espai 13, which holds exhibits of upcoming contemporary artists.");
		createArchitecture(
				"MNAC/Montjuic Palace",
				"Eugenio Cendoya,Enric Catà, Pere Domènech i Roura",
				"1929",
				"Catalan Renaissance",
				"Museum",
				"Parc de Montjuïc, 08038 Barcelona",
				41.368333,
				2.153333,
				"General: 12\n(valid for two days within a month of purchase)\nStudents: 30% discount (with valid card)\nFree entrance:Saturdays from 3 pm,\n1st Sunday of each month\nMay 18th: International Museum Day",
				"May-Sep: Tue-Sat: 10:00-20:00\nOct-Apr: Tue-Sat: 10:00-18:00\nSunday and public holidays: 10:00-15:00\nMonday:closed (except public holidays)\nClosed: January 1st, May 1st and December 25th",
				R.drawable.montjuic_palace_b,
				R.drawable.montjuic_palace_i,
				"The Montjuic Palace, or in Catalan El Palau Nacional, was also built for the International Exhibition of 1929. It was conceived through a competition won by the architects Eugenio Cendonya, Enric Catá, and Pere Domènech i Roura.  Juan Claude Nicolas Forestier and Nicolás María Rubió i Tudurí landscaped the gardens. The construction of the Palace consists of the combination of traditional systems based on symmetry, as clearly outlined in its composition, and modern techniques based on building procedure, which was dominated by modern materials such concrete.  The façade consists of a central body flanked by two smaller side ones, the center topped by a large dome reminiscent of that of St. Paul’s in London or that of St. Peter’s Basilica in the Vatican, with two smaller domes on each side. At the four angles corresponding to those of the Great Hall, stands four towers resembling those of the Cathedral of Santiago de Compastella or that of la Giralda of Seville.\n\nThe art in the interior consisted of works that prevailed in Catalonia in the noucentisme style. The decoration of this building is interesting because of the fact that there are so many different styles that are coming together so nicely. It really brings out the classical architecture that were used in earlier Spanish and European times in while using modern building techniques, and contrasts it with the decoration of 20th century art, attributed to several renown artists.  There grand medley of art by different artists were all through the same theme of showing the grandeur of Spain, one of them specifically, Francesc d’Assís Galí, through four fields: Religion, Science, Fine Arts, and Land.\n\nA restoration of the building was needed in 1934 when it became el Museo Nacional de Arte en Catalunya simply because of the fact that it was meant to be a temporary building. The architect Ramon Reventós was appointed in charge of the renovation, in which they removed excess interior decorations and smoothed the wall surfaces to prepare for the display of paintings. They also undertook the creation of an exterior water collection network to prevent moisture leaks and repaired the cracks, which had become visible on some of the wall surfaces.\n\nSince the 1960’s, the architects Gae Aulenti, Enric Steegman, Josep Benedito Rovira and Agustí Obiol have extended the palace, with the objective of creating space to accommodate the entire collection.");

		createArchitecture(
				"Monestir de Pedralbes",
				"Elisenda de Montcada (founder)",
				"1326",
				"Gothic",
				"Monastery",
				"Baixada del Monestir, 9, 08034 Barcelona",
				41.395556,
				2.112222,
				"General entry- 7.00 \nStudent price- 5.00 \nOther discount: Free with Barcelona Card",
				"Apr-Sep:\nTue-Fri: 10:00-17:00, Sat: 10:00-19:00, Sun: 10:00-20:00\n Hdys: 10:00-14:00 \n Oct-March: Tue-Fri: 10:00-14:00, Sat & Sun: 10:00-17:00 \nHdys: 10:00-14:00 \n CLOSED: Jan 1, May 1, Jun 24, & Dec 25",
				R.drawable.monestir_de_pedralbes_b,
				R.drawable.monestir_de_pedralbes_i,
				"This monastery was founded by King James II of Aragon for his wife, Elisenda de Montcada in the year 1326.  It housed a community of Poor Clares, mostly members of noble families. The queen gave the monastery a series of privileges, including the direct protection of the city of Barcelona, through the Consell de Cent (\"Council of the Hundred\"), who had the task to defend it in case of danger.\n\nOriginally the monastery (built in white stone, pertas albes in Catalan, whence its denomination) was defended by a line of walls, of which today only two towers and one gate remain.  The church itself has a single nave, with rib vaults and a polygonal apse, and houses a Gothic altarpiece by Jaume Huguet. The façade is characterized by a large rose window. Many of the paintings inside shoe the influence of Italian paintings by Giotto.\n\nThe monastery certainly wouldn't be as beautiful and impressive without its gardens in the courtyard.   Large palm trees and cypresses provide cool shade. From the lower cloister, there are several ways to the gardens.\n\nThe monastery was built in just one year, and thus it was not influenced by any other architectural styles.  It is a fine example of particularly homogeneous gothic architecture in Catalonia. In 1984 it was finally opened to the public, and in ‘91 it was declared a national monument. You won't find a quieter place in all Barcelona.");
		createArchitecture(
				"Montjuic Castle",
				"Juan Martin Cermeño",
				"1640",
				"Castle",
				"Castle",
				"Carretera de Montjuïc, 66, 08038 Barcelona, Spain",
				41.363381,
				2.166232,
				"Free",
				"Oct-Mar: Mon-Sun: 9:00-19:00\nApr-Sep: Mon-Sun: 9:00-21:00",
				R.drawable.montjuic_castle_b,
				R.drawable.montjuic_castle_i,
				"This ancient military fortress held a very important role in the history of the city of Barcelona. It sits atop Montjuic Mountain and overlooks the city, its industrial district, and the touristic cruise ship entrances. The first building erected upon the summit of the mountain was a watchtower that would inform the people how close boats were to the city. In the 17th century during the revolt against Felipe IV, the first fortification of Montjuic was constructed. A small earthen fort was created. In the late 17th century, a castle was built that wrapped around the previous small fortification.\n\nDuring the War of Succession the castle was lost twice (once to the hands of the Duke of Peterborough in 1705 retrieved on April of 1706 and lost again in May 12, 1706). In 1808 Napoleon’s troops climbed the mountain of Montjuic to take possession of the castle successfully.\n\nThe Pit of Santa Elena was created during the outbreak of the Civil War to murder soldiers, priests, students, businessmen, and anyone who was considered right wing.\n\nDuring the Franco regime over 4,000 Catalan republican prisoners in the castle were executed. It served as a military prison until 1960 when it was ceded to the city.");
		createArchitecture(
				"Montjuic Communications Tower",
				"Santiago Calatrava",
				"1989",
				"Modernism",
				"Communications Tower, Monument",
				"Parc de Montjuïc, near the Palau Sant Jordi",
				41.364167,
				2.150556,
				"N/A",
				"N/A",
				R.drawable.montjuic_communications_tower_b,
				R.drawable.montjuic_communications_tower_i,
				"Located near the summit of Montjuic, this tower underwent construction in 1989 and was completed in 1992. Architect Santiago Calatrava was in charge of the design and its purpose was to provide television coverage of the 1992 Summer Olympic Games in Barcelona. It is 136 m (446 ft.) tall and is made to represent an athlete holding the Olympic Flame. It also works as a giant sundial, which uses the Europa square to indicate the hour.");

		createArchitecture(
				"Palau de La Musica Catalana",
				"Lluís Domènech i Montaner",
				"1908",
				"Modernism, Catalan Renaissance",
				"Concert Hall",
				"C/ Palau de la Música, 4-6, 08003 Barcelona",
				41.3875,
				2.175,
				"N/A \nDepends on the concert",
				"Daily: 10:00-15:30\n Easter & July: 10:00-18:00 \n Aug: 9:00-20:00 \n Other times: consult availability",
				R.drawable.palau_de_la_musica_b,
				R.drawable.palau_de_la_musica_i,
				"UNESCO World Heritage Site 1997\nCatalan Modernism, Renaixença (Catalan Rebirth); Concert Hall\n\nAnother modernist building in Barcelona, the Palau de la Musica Catalana (Palace of Catalan Music) is a concert hall built between 1905 and 1908 for a choral society called Orfeo Catala. This society was one of the leading forces in the Catalan cultural movement Renaixenca (Catalan renaissance). This building was inaugurated in February 9, 1908. Though mainly financed by the society, many contributions were made by Barcelona’s wealthy industrialists and bourgeoisie. The architect won an award in 1909 for best building built during the previous year from the Barcelona City Council. Today it hosts many musical performances that range from symphonic to jazz and many others that are attended by more than half a million people per year.\n\nThe design of the building is a typical style of Catalan modernism in which curved lines dominate the extent of the building itself leaving the straight lines nearly nonexistent. Dynamic shapes are created over static forms and many organic (alluding to nature) references are used extensively.");

		createArchitecture(
				"Palau Güell",
				"Antoni Gaudi",
				"1886",
				"Modernism, Art Nouveau",
				"Museum, Housing",
				"Carrer Nou de la Rambla, 3-5, 08001 Barcelona",
				41.379183,
				2.174445,
				"General entry- 12.00 \nStudent price- 8.00",
				"Oct-Mar: Tue-Sun: 10:00-17:30 \n Apr-Sep: Tue-Sun: 10:00-20:00 \n Last access times one hour before \n CLOSED: Mon except Hdys, Dec 25 & 26, Jan 1 & 6-13",
				R.drawable.palau_guell_b,
				R.drawable.palau_guell_i,
				"World Heritage Site by UNESCO in 1984.\n\nThis mansion, designed by Gaudi, was constructed for the Catalan tycoon Eusebi Guell. This house is centered on a main room that was for entertaining guests. From the outside, the first thing that is noticed would probably be the two large parabolically shaped gates and the intricately decorate and oddly shaped chimneys on the roof.  When inside, the main party room has a high ceiling with small holes near the top where lanterns were hung at night from the outside to give the appearance of a starlit sky.\n\nThe guests entered in carriages through the front gates, consisting of intricate organic patterns typical of the modernist style of Catalonia, from which animals would be taken into the stable in the basement while the guests visited. \n\nIn 1945 the building was given to the municipality of the city of Barcelona. Renovations to this building were made in 2004 causing the site to be completely suspended from public visits. It reopened in 2008 and all the restoration work completed in April 2011.");
		createArchitecture(
				"Parc de Joan Miró",
				"Beth Galí",
				"1979",
				"Urban Park",
				"Garden, Park",
				"Aragó, 2  08015 Barcelona",
				41.377778,
				2.146944,
				"General entry- 2.23 \nStudent price- 1.42 \nOther discounts: \nFor groups (+15 students): 10% \nFree on Sunday, Wednesday & September 24",
				"Dec-Feb: 10:00-18:00\nMar & Nov: 10:00-19:00\nApr & Oct: 10:00-20:00\nMay-Sep: 10:00-21:00",
				R.drawable.parc_de_joan_miro_b,
				R.drawable.parc_de_joan_miro_i,
				"Parc de Joan Miró is just a simple public space where people go to relax and lie down in the grass.  The Parc de Joan Miró now uses the area of a park to the former slaughterhouse, the Parc de l'Escorxador.  It is distinguished by two areas: the higher elevated green space where at the end stands the 22 meter structure Dona i Ocell, (woman and bird) designed by Joan Miró and Joan Gardy Artigas, and a lower area with a small walkway and sanded space containing plants which include flowers as well as eucalyptus, pine, and palm trees.  The park is a nice intervention with the public space called streets, which produce plenty of unwanted noise throughout the city.  A constantly light wind makes a certain coolness even on hot days.");
		createArchitecture(
				"Parc de La Ciutadella",
				"Fountain by Josep Fontserè, J.C.N. Forestier",
				"1887",
				"Historic Park",
				"Garden, Park",
				"Pg Picasso, 21  08018 Barcelona",
				41.38806,
				2.1875,
				"Free",
				"Dec-Feb: 10:00-18:00\nMar & Nov: 10:00-19:00\nApr & Oct: 10:00-20:00\nMay-Sep: 10:00-21:00",
				R.drawable.parc_de_la_ciutadella_b,
				R.drawable.parc_de_la_ciutadella_i,
				"Developed in 1887, it is historic park spread over 30 hectare forming a green oasis close to the water. The park is laid out nicely with wide promenades, colorful flowerbeds and many palm trees. Initially, it was a fortress ‘The Citadel’ built by Kings Philip V in 1714, which was the largest fortress in the Europe. But by 1841, the city authorities decided to destroy the fortress, which was not the popular support among the masses. After some time, after opposition, the authorities finally were able to restore the park and thus, parc de la Ciutadella was founded.\n\nOne of the main attractions of the park is its fountain, named ‘The Cascada’, which was designed by Josep Fontsere. One of the notable things was that Antoni Gaudi, one of the unknown architecture students at that time, helped him. The architecture of this fountain loosely resembles the style followed by the Trevi Fountain of Rome. Apart from that, there is a lake in the park, which characterizes the park as scenery enjoyable for romantics. Also, it has a zoo, which was developed over the left over area of buildings of Universal Exposition of 1888.   ");

		createArchitecture(
				"Parc del Guinardo",
				"Jean-Claude Nicolas Forestier",
				"1918",
				"Historic Park",
				"Garden, Park",
				"Plaça del Nen de la Rutlla, Carrer de Garriga i Roca and Carrer de Florència",
				41.419536,
				2.169669,
				"Free",
				"24/7",
				R.drawable.parc_del_guinardo_b,
				R.drawable.parc_del_guinardo_i,
				"Parc del Guinardo is a park of the 3 Turons located in the higher mountainous regions of Barcelona. For many centuries, the territory was mainly devoted to agriculture with a focus on the cultivation of the fruit of the vine. In 1916, construction was completed and was one of the first municipal parks in the city. This park was a result of the remodelling process to the lower area which was extended to the main entrance to the park (Placa del Nen de la Rutlla). \n\nThe park contains one of the most popular fountains in the neighborhood, the Cuento fountain. This name comes from the fact that the water flowed very slowly and people told stories or “Cuentos” as they waited to collect water. ");
		createArchitecture(
				"Parc del Laberint",
				"Joan Antoni Desvalls i d'Ardena, Domenico Bagutti, Elies Rogent",
				"1880",
				"Neoclassical, 19th Century Romantic",
				"Garden, Park",
				"Pg. dels Castanyers, 1 Barcelona",
				41.440278,
				2.145556,
				"General entry- 2.23 \nStudent price- 1.42 \nOther discounts: \nFor groups (+15 students): 10%\nFree on Sunday, Wednesday & September 24",
				"May-Aug: 10:00-21:00\nApril & Sep: 10:00-20:00\nMar & Oct: 10:00-19:00\nNov-Feb: 10:00-18:00",
				R.drawable.parc_del_laberint_b,
				R.drawable.parc_del_laberint_i,
				"        	The Parc del Laberint d'Horta is a historical garden in the Horta-Guinardó district in Barcelona, and the oldest of its kind in the city. Located in the former estate of the Desvalls family, next to the Collserola ridge, the park comprises an 18th century neoclassical garden and a 19th century romantic garden. The maze, which is what gives the park its name, is made up of 750 meters of cypress trees.  The park is surrounded by a Mediterranean forest.\n\nWorks began in 1791 when marquis Joan Antoni Desvalls i d'Ardena, owner of the lot, created the design of a neoclassical garden in collaboration with Italian architect Domenico Bagutti. Execution was made under direction of master builders Jaume and Andreu Valls as well as French gardener Joseph Delvalet.\n\nThe descendants of Marquis hired architect Elies Rogent to expand the park, in the 19th century. A romantic garden with flowerbeds, gazebos, huge trees and a waterfall were added through the middle of the park. A water canal was also added to the garden, connecting the upper terrace and the intermediate one.\n\nIn 1880 a domestic garden was created beside the Desvalls palace.  At the end of the 19th century, the Desvalls estate became the venue of social and cultural events including open-air theatre performances, and was given to the city of Barcelona in 1967.\n\nThe park is a very interesting work of art, combining the two different styles of Romantic and Neoclassical. It almost goes unnoticed through the wonderful garden designs that help the styles blend so nicely.");
		createArchitecture(
				"Parc de la Espanya Industrial",
				"Luis Peña Ganchegui, Anton Pagola, Monserrat Ruiz",
				"1985",
				"Urban Park",
				"Park",
				"Carrer Muntadas, 37, 08014 Barcelona",
				41.376962,
				2.140485,
				"Free",
				"Dec-Feb, Mon-Sun: 10:00-18.00 \n Mar-Nov, Mon-Sun: 10:00-19:00\n Apr-Oct, Mon-Sun: 10:00-20:00\n May-Sept, Mon-Sun: 10:00-21:00",
				R.drawable.parc_de_la_espanya_industrial_b,
				R.drawable.parc_de_la_espanya_industrial_i,
				"The park site was originally a former textile mill, L’Espanya Industrial, founded by the Muntades family in 1847, and is now portrays the exemplary effort of Barcelona authorities and people and their recovery plan put into 1980s. In 1985 after a series of local demands, the decision was made to use the old factory land for building houses and creating the park that is there today. \n\nThe project approached the idea of the park as a suitable response to the urban layout of the neighborhood and the environs of Sants Railway Station. It features a wooded area and was designed as a flexible, multi-purpose space. A pond at the base of the sculpture of the dragon of Saint George is central to the layout of the park proposed by Peña. It is guarded by a sculpture of Neptune, which similar to what he sculpted for the waterfall in the Parc de la Ciutadella. At the edge, there is a modern Venus who is also reflected upon the water. \n\nNine tall towers, like monumental lighthouses, stand at the top of the steps beneath which flows the park’s central element: the artificial boating lake, the modern equivalent of a public baths, which is guarded by the monumental steel dragon sculpture by Andrés Nagel which doubles up as a slide to the delight of the children. The lake also forms a true division between the park and the station. All this together with the principal elements of fire, water and earth make up the Parc de l'Espanya Industrial.\n\nInteresting fact: One of the most important sports center within Sants-Montjuic area, this large sports center was built on the same grounds in 1992 for the Barcelona Olympics. It was used for the weightlifting competition during the games.");

		createArchitecture(
				"Parc Güell",
				"Antoni Gaudi",
				"1914",
				"Modernism, Art Nouveau",
				"Garden, Park",
				"Carrer d'Olot, 5, 08024 Barcelona",
				41.4134884,
				2.1531281,
				"Free",
				"Dec-Feb: 10:00-18:00\nMar&Nov: 10:00-19:00\nApr&Oct: 10:00-20:00\nMay-Sep: 10:00-21:00",
				R.drawable.parc_guell_b,
				R.drawable.parc_guell_i,
				"Origin: Commercially unsuccessful housing site, the idea of counts Eusebi Güell \n\nPark Güell is skillfully designed and composed to bring the peace and calm that one would expect from a park. The buildings flanking the entrance, though very original and remarkable with fantastically shaped roofs with unusual pinnacles, fit in well with the use of the park as pleasure gardens and seem relatively inconspicuous in the landscape when one considers the flamboyance of other buildings designed by Gaudí. The focal point of the park is the main terrace, surrounded by a long bench in the form of a sea serpent. The curves of the serpent bench form a number of enclaves, creating a more social atmosphere. Gaudí incorporated many motifs of Catalan nationalism, and elements from religious mysticism and ancient poetry, into the Park.\n\nRoadways around the park to service the intended houses were designed by Gaudí as structures jutting out from the steep hillside or running on viaducts, with separate footpaths in arcades formed under these structures. This minimized the intrusion of the roads, and Gaudí designed them using local stone in a way that integrates them closely into the landscape. His structures echo natural forms, with columns like tree trunks supporting branching vaulting under the roadway, and the curves of vaulting and alignment of sloping columns designed in a similar way to his Church of Colònia Güell so that the inverted catenary arch shapes form perfect compression structures.\n\nThe large cross at the Park's high-point offers the most complete view of Barcelona and the bay. It is possible to view the main city in panorama, with the Sagrada Família and the Montjuïc area visible at a distance.\n\nThe park supports a wide variety of wildlife, notably several of the non-native species of parrot found in the Barcelona area. Other birds can be seen from the park, with records including Short-toed eagle. The park also supports a population of Hummingbird hawk moths. \n\nInternational significance: Patrimony of Mankind by the UNESCO in 1984.");

		createArchitecture(
				"Museu Picasso",
				"Jaime Sabartés (founder)",
				"1963",
				"Gothic civic Catalan",
				"Museum",
				"Carrer de Montcada, 15-23, 08003 Barcelona",
				41.3853,
				2.1806,
				"General entry- 11 \nOther discount: \nFree on first Sunday of every month\nFree on every Sunday after 3 pm.",
				"Tue-Sun: 10:00-20:00",
				R.drawable.picasso_museum_b,
				R.drawable.picasso_museum_i,
				"This museum, apart from holding more than 3,500 works by Pablo Picasso, is composed of five different buildings dating from the 13th and 14th centuries. \n\nPalau Aguilar: This was the first building occupied by the museum. This building dates from the 13th Century though it underwent many alterations between the 15th and 18th centuries. The palace was named after Berenguer Aguilar, who bought it from its previous owners in 1400. \n\nPalau Baró de Castellet: This building was built during the 13th century. It was then purchased by the City Council in the 1950s. It derived its named after the owner of it at the time Mariano Alegre Aparici Amat received the noble title of Baron Castle in 1797. \n\nPalau Meca: This building was built between the 13th and 14th centuries undergoing restoration in the 18th century. It’s named derives from the first owner, Joseph Mecca Hunter, who bestowed this name to the building. It was given to the city council in 1977 and reopened as part of the Picasso museum in 1982. \n\nCasa Mauri: Part of this structure actually dates from Roman times. The facade is wood, which is one of the few examples in Barcelona of a locking system typical of the 18th century. \n\nPalau Finestres: This building has foundation dating to the 13th century and occupies a former Roman necropolis (a large ancient cemetery). The city acquired the building in 1970 from the owner of Casa Mauri. It is currently used as exhibition space.");
		createArchitecture(
				"Plaça d'Espanya",
				"Josep Amargos, Josep Maria Jujol, Miquel Blay, Nicolau Maria Rubió i Tudurí",
				"1929",
				"N/A",
				"Plaza",
				"Placa d'Espanya, Barcelona",
				41.3750931,
				2.1491682,
				"Free",
				"24/7",
				R.drawable.placa_espanya_b,
				R.drawable.placa_espanya_i,
				"Plaza Espanya, with 5 ends and today the intersection of four major streets, Gran Via, Paral-lel, Carrer de la Creu Coberta, and Carrer de Tarragona, is one of Barcelona’s most important squares, at the foot of Montjuic, constructed for the International Exhibition of 1929. On one end is The Palacio Nacional, also known as the Montjuic Palace, which hosts MNAC, Museu Nacional d’art de Catalunya.  It is the biggest intersection of Barcelona right now and has true historical meaning with all the buildings, structures and areas that are located on its perimeter. \n\nJosep Maria Jujol, a collaborator of Antoni Gaudí, designed the fountain at the center of the square while Miquel Blay designed the statues. The buildings were designed by Nicolau Maria Rubió i Tudurí. These include:  Las Arenas de Barcelona, El Palacio Nacional, Las Torres Venecianas, and Parc de Joan Miro.  This plaza has become an everyday node for locals getting to and from the city.");

		createArchitecture(
				"Plaça del Rey",
				"Francesc Daniel Molina",
				"1300",
				"Gothic, Catalan Renaissance",
				"Plaza",
				"Plaza del Rey, s/n 08002 Barcelona",
				41.384164,
				2.177567,
				"Free",
				"24/7",
				R.drawable.placa_del_rey_b,
				R.drawable.placa_del_rey_i,
				"This plaza is widely considered to be the oldest and most beautiful space in the Gothic Quarter. It’s historical significance lies in the fact that when Christopher Columbus return home after his discovery of New World, he was greeted by the King Ferdinand and Queen Isabella on the steps of Palau Reial Major that spreads out into the square. As for its architecture, designed by Francesc Daniel Molina, it possesses a unique style having influences of Gothic and Renaissance art. Originally built as a Romanesque fortress leaning against the Roman Walls, the palace was home to the counts of Barcelona and kings of Aragon who reigned during the Middle Ages. The palace's main attraction is the Salo del Tinell, a magnificent grand hall that was added in the mid-14th century and is now used for temporary exhibitions of the Museu d'Història de la Ciutat, the city’s history museum. To the left of the Tinell Hall is the King Martin's Watchtower (Torre Mirador del Rei Martí). Built in the 15th century as a lookout tower, and it still watches over visitors in the square. To the right of the Tinell Hall is the 14th century Royal Chapel of St. Agatha which has a unique octagonal bell tower shaped like a crown and is home to one of the finest examples of Catalan Gothic art, the 15th century painting by Jaume Huguet entitled Epiphany. Opposite the chapel is the Palau del Lloctinent, a 16th century Gothic structure that was home to the viceroy or lieutenant of the Catalan Parliament. Its beautiful courtyard is publicly accessible.");
		createArchitecture(
				"Plaça Reial",
				"Francesc Daniel Molina i Casamajó",
				"1800s",
				"Art Nouveau",
				"Plaza",
				"Placa Reial, Barri Gotic, Barcelona",
				41.38,
				2.175,
				"Free",
				"24/7",
				R.drawable.placa_reial_b,
				R.drawable.placa_reial_i,
				"It translates into ‘Royal Plaza’ in English. Designed by Francesc Daniel Molina i Casamajó, this plaza follows the Nouveau Art style of architecture. The square is site of the Hotel Roma Reial and club. Even though it is a beautiful attraction to visit in the daytime, it is well- known tourist location for nighttime. If you are a party/club loving person, it is a must visit location. It has some of the city’s most famous nightclubs including Sidecar, Jamboree or Karma. The aesthetics of its urban design and architecture, the fact that it is a meeting place for a broad cross-section of people, the ambiance created by its many bars, terraces and nightspots and the history experienced within its four walls are just some of the elements that make it unique.\n\nAntoni Gaudi has also been part of this plaza construction as he designed the lanterns. The Three Graces cast-iron fountain and the streetlamps, designed by him, with their arms sprouting at different heights like the branches of a tree, were added at the end of the 19th century. The two street lamps in the Plaça Reial have a dark-marble base, and the central part of the column is crowned by two snakes coiled around a stick and a winged helmet symbolizing Mercury, the protector and god of trade, one of the city’s most characteristic activities. The column also bears the coat of arms of Barcelona, just as in the Arc de Triomf. The square was remodeled by the architects Frederic de Correa and Alfons Milà between 1982 and 1984, who focused on the removal of traffic from the square and the planting of palm trees. The square is twinned with Plaza Garibaldi, in Mexico City. \n\nInteresting fact: The film ‘Salvador’ has some of its scene shot in the plaza.");

		createArchitecture(
				"Santa Maria del Mar",
				"Berenguer de Montagut and Ramon Despuig",
				"1383",
				"Catalan Gothic",
				"Church-Basilica",
				"Plaça de Santa Maria, 1, 08003 Barcelona",
				41.383611,
				2.181944,
				"Free",
				"Mon-Sat: 9:00-13:30 & 16:30-20:00 \n Sun & Hdys: 10:30-13:30 & 16:30-20:00",
				R.drawable.santa_maria_del_mar_b,
				R.drawable.santa_maria_del_mar_i,
				"This church in the Ribera district began construction when the foundation stone, laid by king Alfonso IV of Aragon (III of Catalonia), was placed on the 25 March of 1329. The walls, the side chapels, and the facades were finished by 1350 but the final stone was not laid until the 3 November of 1383. Consecration of the church happened almost a year later on the 15 August of 1384. A few damages, the destruction of the rose window in the west end and the destruction of images and the Baroque altar, were caused due to an earthquake in 1428 and a fire in 1936 respectively.\n\nThe interior of the church follows the typical basilica style (the formation of a single space built up of three aisles that has no halls branching from it all). The windows are arranged in a way that brings in abundant daylight.");
		createArchitecture(
				"Temple Expiatori de Sagrat Cor",
				"Enric Sagnier and son Josep Maria Sagnier i Vida",
				"1961",
				"Romanesque, Neogothic",
				"Church",
				"Carrer Muntadas, 37  08014 Barcelona",
				41.42208,
				2.11886,
				"Free \nTo visit the viewing platform (elevator)- 2.00",
				"The church- 08:00-19:00 \nThe lift- 10:30-19:00",
				R.drawable.temple_expiatori_de_sagrat_cor_b,
				R.drawable.temple_expiatori_de_sagrat_cor_i,
				"The Expiatory Church of the Sacred Heart of Jesus is a Roman Catholic church and minor basilica located on the summit of Mount Tibidabo in Barcelona, Spain.  Having been designed by Enric Sagnier, it was completed by his son Josep Maria Sagnier i Vidal.  One of the building's most notable features is the large statue of Jesus that is centrally located at the top of the building.  \n\nThe church was consecrated by the Bishop Gregorio Modrego Casaus during the 35th Eucharistic Congress held in Barcelona in 1952. The towers were completed afterwards, with work officially ending in 1961. On 29 October 1961 the church received the title of minor basilica from Pope John XXIII.\n\nThe external appearance of the temple is of a Romanesque fortress of stone from Montjuïc (the crypt), topped by a monumental neo-Gothic church accessed by two grand outdoor stairways.  The interior is divided into three naves with semicircular apses, with stained glasses and four rose windows on the facades. In the main altar stands the great crucifix, a work by Joan Puigdollers.  The windows of the four towers contain the Latin phrase tibi dabo (\"I'll give you,” from Matthew 4:9), the name of the mountain.\n\nThe crypt was designed in a neo-Byzantine style, which combines Gothic and classical elements, and decoration close to Modernisme. The space of the crypt consists of five naves separated by columns, the central one being wider, all with semicircular apses.\n\nThe church took 60 years to construct.  It’s amazing and extraordinarily precise detail, like all Gothic and Romanesque structures, adds a great view onto the mountain skyline of Barcelona.");
		createArchitecture(
				"Torre de Collserola",
				"Norman Foster",
				"1992",
				"Contemporary",
				"Communications Tower",
				"Carretera Vallvidrera-Tibidabo, S/N, Tibidabo hill, 08017 Barcelona",
				41.4172,
				2.1142,
				"General entry- 5.00 \nStudent price- 3.00 \nOther discoutnt: Free with Barcelona Card",
				"Apr-June, Sept: Wed-Fri: 11:00-14:00, 15:30-18:00\nSat-Sun: 11:00-14:00, 15:30-19:00\nJuly & Aug: Wed-Fri: 11:00-14:00, 15:30-19:00\nSat-Sun: 11:00-14:00, 15:30-20:00\nOct-Mar Wed-Fri: 11:00-14:00, 15:30-17:00; Sat-Sun: 1111:00-14:00, 15:30-18:00",
				R.drawable.torre_de_collserola_b,
				R.drawable.torre_de_collserola_i,
				"This telecommunications tower is one of two that were built for the 1992 Olympics in Barcelona.  It was designed to be a new symbol of the city, a representation of the new future it would have due to the events, stable and powerful.  It was given a location on el Pico de la Vilana, the highest point on the mountain. The tower was actually the winner of a design competition.  The main criteria included an adaptation to the landscape, lower impact, slenderness, and innovation.  This design went well with its landscape, having a massive robust look to it.\n\nIt contrasted the second telecommunications tower, by Santiago Calatrava, that was situated next to the stadiums that hosted the Olympics across the city on the mountain of Montjuic, nicely. The two designs were entirely different. It is surprising, because Calatrava was actually also an engineer, but his design looked more “artsy,” which is typical of designers such as architects.  The two can be seen from different areas of the city and definitely have an interesting effect on the look of the city.");
		createArchitecture(
				"Torres Venecianas",
				"Ramon Reventòs",
				"1927-1929",
				"Romanesque",
				"Monument",
				"Intersection of Gran Via and Avenida del Parallel (Plaza de España)",
				41.37403,
				2.149801,
				"N/A",
				"They are closed as of 2013",
				R.drawable.torres_venecianes_b,
				R.drawable.torres_venecianes_i,
				"This the popular name for a pair of towers on Avinguda de la Reina Maria Cristina at its junction with Plaça d'Espanya in Barcelona, Catalonia, Spain. There is one tower on either side of the street. These two towers were designed by Ramon Reventòs and follow the architecture style of Romanesque, which is characterized by semi-circular arches. The two twin towers is divided into three structures; bottom section, built of artificial stone, the main section of red brick, and the top section is a colonnaded viewing gallery built of artificial stone, and topped by a pyramidal copper roof. Not before long, these towers were open to the public and people were allowed to visit to the very top section and climb the internal stairs to the viewing galleries, but they are now closed. Built in 1929, these towers as part of the redevelopment of the area for the 1929 Barcelona International Exposition.");

		return true;

	}
}