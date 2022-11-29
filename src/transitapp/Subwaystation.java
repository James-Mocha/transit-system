package transitapp;

import java.time.LocalDateTime;

public class Subwaystation extends Station{
	/**
	 * for Subwaystation class,it's the subclass of the Station
	 */
	public String name;
	
	/**
	 * create a Subwaystation
	 * @param name a String contains the name of the subwaystation
	 */
	public Subwaystation(String name) {
		super(name);
	}
	
	/**
	 * a method when someone get in the subwaystation,return a point
	 * @param time: the LocalDateTime of the point
	 * @return a point that someone take the subwaystation at this point
	 */
	public Point take(LocalDateTime time){
		String s = "take subway";
		return new Point(time, this, s);
	}
	
	
	/**
	 * a method when someone get off the subwaystation,return a point
	 * @param time: the LocalDateTime of the point
	 * @return  a point that someone take off the subwaystation at this point
	 */
	public Point takeoff(LocalDateTime time) {
		String s = "takeoff subway";
		return new Point(time, this, s);
	}

}
