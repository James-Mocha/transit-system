package transitapp;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * For a user's class, the user can check trip History and get the user's email, name, buscard and other functions 
 * 
 * 
 * @author Junmin Fang & Hui Zhu
 *
 */
public class User {
	private String name;
	private String email;
	private ArrayList<Card> buscards;
	private ArrayList<Trip> History;
	
	/**
	 * Constructs an empty User with the given name and email
	 * 
	 * and include two internal Arraylists to represent cards and trips
	 * 
	 * @param name
	 * @param email
	 * 
	 * @author Junmin Fang
	 *
	 */
	public User(String name, String email) {
		this.name =name;
		this.email = email;
		this.buscards = new ArrayList<Card>();
		this.History = new ArrayList<Trip>();
	}
	
	/**
	 * return a ArrayList it includes all trips owned by the user
	 * 
	 * @return a Arraylist includes all user trips
	 */
	public ArrayList<Trip> getHistory() {
		return this.History;
	}
	
	/**
	 * Adds trip to the user's trips history by given trip t
	 * 
	 * @param t
	 */
	public void addHistory(Trip t) {
		this.History.add(t);
	}
	
	/**
	 * return a String which represent User's name
	 * 
	 * @return a User's name as a String
	 */
	public String getUserName() {
		return this.name;
		
	}
	
	/**
	 * return a String which represent User's email
	 * 
	 * @return a User's email as a String
	 */
	public String getEmail() {
		return this.email;
		
		
	}
	
	/**
	 * set User's name by give String n
	 * 
	 * @param n
	 */
	public void ChangeUserName(String n) {
		this.name = n;
	}
	
	/**
	 * return a Card by given int id to get this card
	 * 
	 * @param id
	 * @return a card by given id to get this card which related this id
	 */
	public Card getBuscard(int id) {
		for(int i =0; i< buscards.size();i++) {
			if(buscards.get(i).getId() == id) {
				return buscards.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Add the given Card c to the user's card package 
	 * 
	 * @param c 
	 */
	public void addBuscard(Card c) {
		buscards.add(c);
		
	}
	
	/**
	 * Freeze the card by give card's id so it doesn't work properly
	 * 
	 * @param id
	 */
	public void Frozen(int k) {
		buscards.get(k).setFrozen();
	}
	
	/**
	 * Activate the card by give card's id to make it work properly
	 * 
	 * @param id
	 */
	public void Activate(int k) {
		buscards.get(k).setValid();
	}

	/**
	 * Delete the card given from the user's card package
	 * @param c
	 */
	public void deletCard(Card c) {
		buscards.remove(c);
	}
	
	/**
	 * return an ArrayList that represent a card package contains all of the user's CARDS
	 * @return an ArrayList contains all of the user's CARDS
	 */
	public ArrayList<Card> getAllCards() {
		return this.buscards;
	}
	
	/**
	 * return a list that represent The user's last three TRIPS
	 * 
	 * @return a list contains the user's last three TRIPS
	 */
	public List<Trip> getLastTrips(){
		List<Trip> allTrips = this.getHistory();
		Collections.sort(allTrips);
		return allTrips.subList(allTrips.size() - 3, allTrips.size());
	}
	
	/**
	 * return The user's monthly bus expenses by given year and Month
	 * 
	 * @param year
	 * @param month
	 * @return The user's monthly bus expenses by given year and Month
	 */
	public double getMonthlyCost(int year, int month) {
		double sum = 0.0;
		for (Trip t : this.getHistory()) {
			if (t.getStartTime().getMonthValue() ==  month && t.getStartTime().getYear() == year) {
				sum += t.getCost();
			}
		}
		return sum;
	}

	
}
