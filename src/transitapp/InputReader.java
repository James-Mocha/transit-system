package transitapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This class is an input reader for a String (line from events.txt) which fits the following the format and 
 * also acts as a controller that calls relevant methods in various classes to change the model or satisfy 
 * the user's command. It prints whether the command was successful or failed and the output of the command
 * after the command is executed.
 * It knows the map of the city (which the user has to create first), and also sets up a unique Transit System 
 * accordingly.
 * 
 * @author Hui Zhu
 */

public class InputReader {
	TransitSystem tsystem;
	Mapp map;
	
	/**
	 * Constructor for InputRead.
	 * Temporillarily initiallizes its map and system to null, which should be filled by Mapp and 
	 * TransitSystem objects later.
	 */
	public InputReader() {
		this.map = null;
		this.tsystem = null;
	}
	
	/**
	 * This method reads a String input and execute if fits the following format and 
	 * also prints output (whether successful/failed or desired information from client) in the console.
	 * 
	 * @param input     a String input, command from client
	 * 
	 * ====== Precondition ======
	 * input in following format:
	 * 
	 * Create new Map					CreateMap, Map name, Subway fare, Bus fare, Cap fare
	 * Create Bus Route:                CreateRoute, BUS, [stopA, stopB, ...], route name
	 * Create Subway Route:             CreateRoute, SUBWAY, [stationA, stationB, ...], route name
	 * Create new Card:                 CreateCard, userName, cardID(5 int)
	 * Register User:                   CreateUser, name, email
	 * Enter/Exit Station:              TapCard, IN/OUT, BUS/SUBWAY, time(yyyy-MM-dd HH:mm), cardID, station name
	 * Add balance to existing Card:    AddBalance, cardID, amount(20/30/50)
	 * View balace of existing Card:    ViewBalance, cardID
	 * Suspend Stolen Card:             SuspendCard, cardID
	 * Reactivate Card:                 ReactivateCard, cardID
	 * Add Card:                        AddCard, userName, cardID
	 * Remove Card:						RemoveCard, userName, cardID
	 * Change User name:				ChangeName, old userName, new userName 
	 * View 3 recent Trips:             ViewRecent, userName
	 * View cost(stats):               	ViewOperatingCost, yyyy-mm-dd
	 * Get monthly travel cost:         ViewMonthlyCost, userName, yyyy, m(int)
	 * 
	 * More explanation on these commands can be found in features.txt
	 */
	
	public void read(String input) {
		String[] command = input.split(",");
		for (int i = 0; i < command.length; i++) { 
            command[i] = command[i].trim();
        } 
		if (command[0].equals("CreateMap")) {
			double subwayFare = Double.parseDouble(command[2]);
			double busFare = Double.parseDouble(command[3]);
			double capFare = Double.parseDouble(command[4]);
			this.map = new Mapp(command[1], subwayFare, busFare, capFare);
			if (this.map != null) {
				this.tsystem = new TransitSystem(this.map);
				System.out.println("Map" + map.map_Name + " is successfully created!");
			} else {
				System.out.println("Map" + map.map_Name + " creation failed.");
			}
		}
		
		if (command[0].equals("CreateRoute")) {
			int i = 2;
			int end_i = this.findIndex("]", command);
			command[i] = command[i].substring(1);
			command[end_i] = command[end_i].substring(0, command[end_i].length() - 1);
			String[] stops = Arrays.copyOfRange(command, i, end_i + 1);
			ArrayList<Station> route = new ArrayList<Station>();
			if (command[1].trim().equals("BUS")) {
				for(String stop : stops) {
					Busstation s = new Busstation(stop);
					route.add(s);
				}
			} else if (command[1].trim().equals("SUBWAY")) {
				for(String stop : stops) {
					Subwaystation s = new Subwaystation(stop); 
					route.add(s);
				}
			}
			if (route.isEmpty()) {
				System.out.println("Route" + command[end_i + 1] + " appears to be empty.");
			} else {
				System.out.println("Route" + command[end_i + 1] + " successfully added!");
				this.map.add_Line(route, command[end_i + 1]);
			}
		}
		
		if (command[0].equals("CreateUser")) {
			User user = new User(command[1], command[2]);
			this.tsystem.UserList.add(user);
			System.out.println("User" + user.getUserName() + " successfully created!");
		}
		
		if (command[0].equals("TapCard")) {
			String type = command[2];
			LocalDateTime datetime = this.getDateTime(command[3]);
			int cardID = Integer.parseInt(command[4]);
			Station station = map.findStation(command[5], command[2]);
			if (command[1].equals("IN")) {
				if (this.tsystem.onTraffic(type, station, datetime, cardID) == 1) {
					System.out.println(command[4] + " successfully entered " + command[2] + 
							command[5] + " at " + command[3]);
				} else {
					System.out.println(command[4] + " failed to enter " + command[2] + 
							command[5] + " at " + command[3]);
				}
			} else if (command[1].equals("OUT")) {
				int output = this.tsystem.offTraffic(type, station, datetime, cardID);
				if (output == 0) {
					System.out.println(command[4] + " successfully exited " + command[2] + 
							command[5] + " at " + command[3]);
				} else {
					System.out.println(command[4] + " failed to exit " + command[2] + 
							command[5] + " at " + command[3]);
				}
			} 
		}
	
		if (command[0].equals("AddBalance")) {
			int id = Integer.parseInt(command[1]);
			Card card = this.tsystem.getCard(id);
			if (card == null) {
				System.out.println("Card not found.");
			} else {
				int amount = Integer.parseInt(command[2]);
				card.addBalance(amount);
				System.out.println("Card " + command[1] + " added balance $" + command[2]);
			}
		}
		
		if (command[0].equals("ViewBalance")) {
			int id = Integer.parseInt(command[1]);
			Card card = this.tsystem.getCard(id);  
			if (card == null) {
				System.out.println("Card not found.");
			} else {
				System.out.println("Card " + command[1] + ": $" + card.getBalance());
			}
		}
		
		if (command[0].equals("SuspendCard")) {
			
			int id = Integer.parseInt(command[1]);
			Card card = this.tsystem.getCard(id);
			if (card == null) {
				System.out.println("Card not found.");
			} else {
				card.setFrozen();
				System.out.println("Card " + command[1] + " has been suspended.");
			}
		}

		if (command[0].equals("ReactivateCard")) {
			int id = Integer.parseInt(command[1]);
			Card card = this.tsystem.getCard(id);
			if (card == null) {
				System.out.println("Card not found.");
			} else {
				card.setValid();
				System.out.println("Card " + command[1] + " is now reactivated.");
			}
		}
		
		if (command[0].equals("AddCard")) {
			User user = this.tsystem.getUser(command[1]);
			int id = Integer.parseInt(command[2]);
			Card card = tsystem.getCard(id); 
			if (card != null) {
				System.out.println("Card already in wallet.");
			} else if (user == null) {
				System.out.println("User not found.");
			} else {
				user.addBuscard(card);  
				System.out.println("User " + command[1] + " added card " + command[2]);
			}   
		}
		
		if (command[0].equals("RemoveCard")) {
			User user = this.tsystem.getUser(command[1]);
			int id = Integer.parseInt(command[2]);
			Card card = tsystem.getCard(id); 
			if (card == null) {
				System.out.println("Card not found.");
			} else if (user == null) {
				System.out.println("User not found.");
			} else {
				user.deletCard(card);
				this.tsystem.CardList.remove(card);
				System.out.println("User " + command[1] + " deleted card " + command[2]);
			}   
		}
		
		if (command[0].equals("ChangeName")) {
			User user = this.tsystem.getUser(command[1]);
			if (user == null) {
				System.out.println("User not found.");
			} else {
				user.ChangeUserName(command[2]);
				System.out.println("User " + command[1] + " changed name to " + command[2]);  //needs
			}
		}
		
		if (command[0].equals("CreateCard")) {
			User user = this.tsystem.getUser(command[1]);
			if (user == null) {
				System.out.println("User" + command[1] + " not registered");
			} else {
				int id = Integer.parseInt(command[2]);
				if (this.tsystem.getCard(id) != null) {
					System.out.println("Card " + command[2] + " already exists!");
				} else {
					Card card = new Card(id, user);
					this.tsystem.CardList.add(card);
					user.addBuscard(card);
					System.out.println("User" + command[1] + " successfully bought card " 
					+ command[2]);
				}
			}
		}
		
		if (command[0].equals("ViewRecent")) {
			User user = this.tsystem.getUser(command[1]);
			if (user == null) {
				System.out.println("User not found.");
			} else {
				System.out.println("Last 3 trips for user " + command[1] + " are following:");
				List<Trip> trips = user.getLastTrips();
				for (Trip trip : trips) {
					trip.print();
				}
			}
		}
		
		if (command[0].equals("ViewOperatingCost")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			formatter = formatter.withLocale(Locale.US);
			LocalDate date = LocalDate.parse(command[1], formatter);
			System.out.println(this.tsystem.operatingCost(date));
		}
		
		if (command[0].equals("ViewMonthlyCost")) {
			User user = this.tsystem.getUser(command[1]);
			if (user == null) {
				System.out.println("User not found.");
			} else {
				int month = Integer.parseInt(command[3]);
				int year = Integer.parseInt(command[2]);
				double cost = user.getMonthlyCost(year, month);
				System.out.println("In " + command[2] + ", User " + command[1] + "'s traffaic cost is $" + cost);
				
			}
		}
	}
	
	/**
	 * This method finds and returns the index of the first element in Array of String lst 
	 * if any element has the String target, return -1 if no element in lst contains target.
	 * 
	 * @param input     a String input, command from client
	 * @return the index of the first element in Array of String lst if any element has the String target, 
	 * return -1 if no element in lst contains target.
	 */
	public int findIndex(String target, String[] lst) {
		int i = 0;
		while (i < lst.length) { 
			  
            if (lst[i].contains(target)) { 
                return i; 
            } 
            else { 
                i = i + 1; 
            }
            
        } 
		return -1;
	}
	
	/**
	 * This method creates and returns a LocalDateTime object from given String input
	 * 
	 * @param input     a String input, in format of yyyy-MM-dd HH:mm
	 * @return a LocalDateTime object with given input
	 */
	public LocalDateTime getDateTime(String input) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dt = LocalDateTime.parse(input, formatter);
        return dt;
	}
	
}
