package transitapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * for Mapp class:we using the HashMap as the map of our transit system,
 * key of the HashMap is the name of a single line
 * the value of the HashMap is the ArrayList that contains all the station in this line
 * @author pengkaicheng
 *
 */

public class Mapp{
	/**
	 * @param big_map: a HashMap that contains all the station for the transit system
	 * @param map_name: a string that shows the name of the map
	 * @param bus_fee: a double number shows the fee that use to let use pay for the bus
	 * @param subway_fee: a double number shows the fee that use to let use pay for the subway
	 * @param cap : a double that show the maximum fee the user going to pay during one trip
	 */

	HashMap<String,ArrayList<Station>> big_map = new HashMap<String,ArrayList<Station>>();
	public String map_Name;
	public double bus_fee;
	public double subway_fee;
	public double cap;
	
	
	/**
	 * create the map
	 * @param name: a String contains the name of the map
	 * @param subway_fee:a double that is the unit fee of the subway
	 * @param bus_fee: a double that is the fee of the bus
	 * @param cap a double that show the maximum fee the user going to pay during one trip
	 */
	public Mapp(String name,double subway_fee, double bus_fee, double cap) {
		this.map_Name = name;
		this.big_map = new HashMap<String,ArrayList<Station>>();
		this.bus_fee = bus_fee;
		this.subway_fee = subway_fee;
		this.cap = cap;
	}

	
	
	
	
	/**
	 * add a line into to the map,a line including the name of the line and the station that this line contains
	 * @param value: a ArrayList<Station> contains all the station in the line
	 * @param line_Name : a String that contains the name of the line
	 */
	public void add_Line(ArrayList<Station> value,String line_Name){
		this.big_map.put(line_Name,value);
	}
	
	
	
	
	
	/**
	 * @return the map
	 */
	public HashMap<String, ArrayList<Station>> get_Map(){
		return this.big_map;
		
	}
	
	
	
	/**
	 * input the name and the type of station, the method find the station that the name and type is matched
	 * @param name : a String that contains the name of the station.
	 * @param type: a String that contains the type of the station.
	 * @return a Station if the method successfully found the Station,null if the Station is not exist.
	 */
	public Station findStation(String name, String type) {
		for (Map.Entry<String, ArrayList<Station>> routeName : this.big_map.entrySet()) {
			ArrayList<Station> route = routeName.getValue();
			for (Station station : route) {
				if (station.getName().equals(name)) {
					if (type.equals("BUS") && (station instanceof  Busstation)) {
						return station;
					} else if (type.equals("SUBWAY") && (station instanceof Subwaystation)) {
						return station;
					}
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * give the start and end station and return the number of station passed between this two station
	 * @param start : a station where it starts
	 * @param end: a station where it ends
	 * @return an integer that means the number of the station passed.
	 */
	public int getNumStation(Station start, Station end) {
		ArrayList<Station> list = new  ArrayList<Station>();
		for (Map.Entry<String, ArrayList<Station>> name : this.big_map.entrySet()) {
			ArrayList<Station> route = name.getValue();
			if (route.contains(start) && route.contains(end)) {
				list = route;
			}
		}
		int n = list.indexOf(start);
		int m = list.indexOf(end);
		return Math.abs(m - n);
	}
}
