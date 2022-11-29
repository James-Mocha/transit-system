package transitapp;
import java.util.ArrayList;

/**
 * This class keeps track of all information of a Bus card.
 * 
 * Each card has a unique id (length 5 int), it knows its current ballance and its User.
 * All new card start at $19.0, user can add fund to it in amount of 20/30/50.
 * It knows if it is valid or not, and whether it is currently during a trip or not.
 * It knows all its trip.
 * It can pay (decreasing balance).
 * 
 * @author Hui Zhu
 */

public class Card {
	private int id;
	private double balance;
	private User holder;
	private boolean valid;
	private int state = 1;
	private ArrayList<Trip> History;
	
	/**
	 * Constructs a new Card with given id and user, and an empty History.
	 * A new card starts at $19.0 and valid.
	 * 
	 * @param id	       length 5 int, unique id
	 * 		  holder       a User object, this Card's holder
	 */
	public Card(int id, User holder) {
		this.id = id;
		this.balance = 19.00;
		this.holder = holder;
		this.valid = true;
		this.History = new ArrayList<Trip>();
	}
	
	/**
	 * Gets this card's state, whether during a trip or not
	 * 
	 * @return int 		1 if free, 0 if busy
	 */
	public int getState() {
		return this.state;
	}
	
	/**
	 * Sets this card's state to 1, off traffic
	 */
	public void setOffTraffic() {
		this.state = 1;
	}
	
	/**
	 * Sets this card's state to 0, on traffic
	 */
	public void setOnTraffic() {
		this.state = 0;
	}
	
	/**
	 * Gets this card's user
	 * 
	 * @return User	object
	 */
	public User getHolder() {
		return this.holder;
	}
	
	/**
	 * Gets this card's id
	 * 
	 * @return int, this card's id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns this card is valid or not
	 * 
	 * @return bool   True if valid, False if not
	 */
	public boolean isValid() {
		return this.valid;
	}
	
	/**
	 * Returns this card's balance
	 * 
	 * @return double    this card's balance
	 */
	public double getBalance() {
		return this.balance;
	}
	
	/**
	 * Sets this card to invalid
	 */
	public void setFrozen() {
		this.valid = false;
	}
	
	/**
	 * Sets this card to valid
	 */
	public void setValid() {
		this.valid = true;
	}
	
	/**
	 * Add amount to this card's balance
	 * 
	 * @param double   amount add to this card's balance
	 */
	public void addBalance(double amount) {
		this.balance += amount;
	}
	
	/**
	 * This card pay amount / decreases this card's balance by amount
	 * 
	 * @param double   amount paid by this card
	 */
	public void pay(double amount) {
		this.balance -= amount;
	}
	
	/**
	 * Returns this card's trip history
	 * 
	 * @preturn this card's all trips 
	 */
	public ArrayList<Trip> getTripHistory(){
		return this.History;
	}
	
	/**
	 * Adds a trip to this card's history
	 * 
	 * @param Trip object t   trip wanted to add to history
	 */
	public void addHistory(Trip t) {
		this.History.add(t);
	}
	
}
