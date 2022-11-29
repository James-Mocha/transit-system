package transitapp;

import java.time.LocalDateTime;
/**
 * for Point class: the point is the check point 
 * that contain the local date time, location and the current status.
 * @author pengkaicheng
 *
 */

public class Point{
	public LocalDateTime time;
	public Station location;
	public String status = "";
	
	
	
	
	/**
	 * create the point
	 * @param time: is the LocalDateTime
	 * @param location:the station of the point
	 * @param status:a String contains the status of the point
	 */
	public Point(LocalDateTime time, Station location,String status) {
		this.location = location;
		this.time = time;
		this.status = status;
	}
	

	
	
	/**
	 * 
	 * @return the LocalDateTime of the Point
	 */
	public LocalDateTime get_Time() {
		return this.time;
	}
	
	/**
	 * 
	 * @return the location of the Point
	 */
	public Station get_location() {
		return this.location;
	}
	
	
	/**
	 * print the Point's time and point's location and the Point's status
	 */
	public void print() {
		System.out.println(this.time + ", " + this.location + ", " + this.status);
	}
}
