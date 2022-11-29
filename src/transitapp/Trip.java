package transitapp;

import java.util.ArrayList;
/**
 * the trip class is implements Comparable<Trip>
 */
import java.time.LocalDateTime;

public class Trip implements Comparable<Trip>{
	private double allcost = 0;
	private ArrayList<Point> triplist;
	
	/**
	 * Construct a trip object.store a continuous journey.	
	*/
	public Trip() {
		this.triplist = new ArrayList<Point>();
	}
	
	/**
	 * 
	 * @return trip's trip list(a continuous journey)
	 */
	public ArrayList<Point> getTrip(){
		return this.triplist;
	}
	
	/**
	 * add a new journey to trip list
	 * @param p    the new journey
	 */
	public void addPoint(Point p){
		this.triplist.add(p);
	}
	
	/**
	 * 
	 * @return total cost of a trip
	 */
	public double getCost(){
		return this.allcost;
	}
	
	/**
	 * update if a new journey act
	 * @param n   new cost of a journey
	 */
	public void addcost(double n) {
		this.allcost += n;
	}
	/**
	 * 
	 * @return the first journey's start time
	 */
	public LocalDateTime getStartTime() {
		return this.triplist.get(0).get_Time();
	}
	
	/**
	 * show in human language
	 */
	public void print() {
		for (Point p : this.triplist) {
			p.print();
		}
		System.out.println("It costs:" + this.allcost);
	}
	
	/**
	 * to compare this trip and the first trip of a trip list start time
	 * @param t    the trip wanted to compare
	 * @return the integer of the start time
	 */
	public int compareTo(Trip t) {
        return this.triplist.get(0).get_Time().compareTo(t.triplist.get(0).get_Time());
    }

}
