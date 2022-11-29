package transitapp;

public class Station{
	/**
	 * For Station's class, it's a class that contain's the name of the station
	 */

	private String name;
	
	
	/**
	 * create a station
	 * @param name:String that contains the name
	 */
	public Station(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return the name of the station
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}








