package transitapp;

import java.time.LocalDateTime;

public class Busstation extends Station{

	public String name;
	/**
	 * for Busstation class,it's the subclass of the Station
	 */
	
	
	
	/**
	 * create a Buswaystation
	 * @param name a String contains the name of the buswaystation
	 */
	public Busstation(String name) {
		super(name);
	}
	
	
	/**
	 * a method when someone get in the Busstation,return a point
	 * @param time: the LocalDateTime of the point
	 * @return a point that someone take the Busstation at this point
	 */
	public Point take(LocalDateTime time) {
		String s = "take bus";
		return new Point(time, this, s);
	}
	
	
	
	/**
	 * a method when someone get off the Busstation,return a point
	 * @param time: the LocalDateTime of the point
	 * @return  a point that someone take off the Busstation at this point
	 */
	public Point takeoff(LocalDateTime time) {
		String s = "takeoff bus";
		return new Point(time, this, s);
	}

}
