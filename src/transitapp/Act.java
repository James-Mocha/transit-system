package transitapp;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Map;

public class Act {
	private Card card;
	
	/**
	 * Construct a act object with the given card.
	 * focus on which card take/take off the bus/subway 
	 * 
	 * @param card       the card focused on
	 */
	public Act(Card card) {
		this.card = card;
		
	}
	
	/**
	 * the card take bus or subway and record the act in the history list
	 * 
	 * @param stime              the time card start the journey
	 * @param startlocation      the start station 
	 * @param type               take "bus" or "subway"
	 * @param map                on which city's map
	 * @return a string that express actions,"take bus" if success take bus and "take subway" if success take subway
	 *                                        or "no money" if the balance is negative, or "error" else
	 */
	
	public String take(LocalDateTime stime,
			Station startlocation, String type, Mapp map) {
		if(this.card.getBalance() < 0 ) {
			return "no money"; 
		}
		if (type.equals("BUS")) {
			Point point = new Point(stime, startlocation, "take bus");
			if (is_con(card, point)) {
				Trip trip = card.getTripHistory().get(card.getTripHistory().size() - 1);
				trip.addPoint(point);
				if(trip.getCost() <= map.cap - map.bus_fee) {
					card.pay(map.bus_fee);
					trip.addcost(map.bus_fee);
				}else if (trip.getCost() <= map.cap) {
					double money = map.cap - trip.getCost();
					card.pay(money);
					trip.addcost(money);
				}
			}
			else {
				Trip trip = new Trip();
				card.addHistory(trip);
				card.getHolder().addHistory(trip);
				trip.addPoint(point);
				card.pay(map.bus_fee);
				trip.addcost(map.bus_fee);	
			}
			return "take bus";
		}else if (type.equals("SUBWAY")) {
			Point point = new Point(stime, startlocation, "take subway");
			if (is_con(card, point)) {
				Trip trip = card.getTripHistory().get(card.getTripHistory().size() - 1);
				trip.addPoint(point);
			}
			else {
				Trip trip = new Trip();
				card.addHistory(trip);
				card.getHolder().addHistory(trip);
				trip.addPoint(point);
			}
			return "take subway";
		}
		return "error";
	}
	
	/**
	 * the card take off bus or subway and record the act in the history list
	 * 
	 * @param etime              the time card end the journey
	 * @param startlocation      the start station 
	 * @param endlocation        the end station 
	 * @param type               take "bus" or "subway"
	 * @param map                on which city's map
	 * @return a string that express actions,"take off bus" if success take off bus 
	 *         and "take off subway" if success take off  subway, or "error" else
	 */
	
	public String takeoff(LocalDateTime etime,Station startlocation, Station endlocation, String type, Mapp map) {
		if (type.equals("BUS")) {
			Point point = new Point(etime, endlocation, "takeoff bus");
			Trip trip = card.getTripHistory().get(card.getTripHistory().size() - 1);
			trip.addPoint(point);
			return "take off bus";
		}else if (type.equals("SUBWAY")) {
			Point point = new Point(etime, endlocation, "takeoff subway");
			Trip trip = card.getTripHistory().get(card.getTripHistory().size() - 1);
			double money = countmoney(map, startlocation, endlocation);                                      
			if (trip.getTrip().size() == 1) {
				this.card.pay(money);
				trip.addPoint(point);
				trip.addcost(money);
			}
			else {
				if(trip.getCost() <= map.cap - money) {
					card.pay(money);
					trip.addPoint(point);
					trip.addcost(money);
				}else if (trip.getCost() <= map.cap) {
					double money2 = map.cap - trip.getCost();
					this.card.pay(money2);
					trip.addPoint(point);
					trip.addcost(money2);
				}
			}
			return "take off subway";
		}
		return "error";
	}
	
	/**
	 * determiner this journey is continuous(less than 2 hours and the same station) to the last trip
	 * @param card       the card focused on
	 * @param point      this journey focused on
	 * @return if it is continuous, return true. if it is not continuous, return false
	 */
	private static boolean is_con(Card card, Point point) {
		if (card.getTripHistory().size() == 0) {
			return false;
		}
		Trip trip = card.getTripHistory().get(card.getTripHistory().size() - 1);
		LocalDateTime i = trip.getTrip().get(0).get_Time();
		LocalDateTime j = point.get_Time();
		Duration duration = Duration.between (i, j);
		if (duration.toMinutes() <= 120 && trip.getTrip().get(trip.getTrip().size() - 1)
				.get_location().getName().equals( point.get_location().getName())) {
			return true;
		}
		return false;
		
		
	}
	/**
	 * count the total money on a whole subway journey
	 * 
	 * @param map                 which city's map
	 * @param startstation2       the start station
	 * @param endstation2         the end station
	 * @return the total money on this city's over a whole subway journey.
	 */
	private static double countmoney(Mapp map, Station startstation2, Station endstation2) {
		ArrayList<Station> list = new  ArrayList<Station>();
		for (Map.Entry<String, ArrayList<Station>> name : map.big_map.entrySet()) {
			ArrayList<Station> route = name.getValue();
			if (route.contains(startstation2) && route.contains(endstation2)) {
				list = route;
			}
		}
		int n = list.indexOf(startstation2);
		int m = list.indexOf(endstation2);
		return map.subway_fee * Math.abs(m - n);
	}
	
}
