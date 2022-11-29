package transitapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A bus system class can perform different operations on the card and the user, for example
 * Buy a bus pass and delete the daily revenue of the bus pass system
 * 
 * @author Junmin Fang & Hui Zhu
 *
 */
public class TransitSystem {

	
	ArrayList<User> UserList;
	ArrayList<Card> CardList;
	int id ;
	int flag = 0;// 0 不在车上，1 在车上
	LocalDateTime stime;
	LocalDateTime etime;
	Station startstation;
	Station endstation;
	String type;
	Mapp map;
	
	/**
	 * Create a new bus system by using the map given
	 * @param map
	 */
	public TransitSystem(Mapp map) {
		this.UserList = new ArrayList<User>();
		this.CardList = new ArrayList<Card>();
		this.map = map;
	}
	
	/**
	 * Returns a int to determine whether the card successfully landed
	 * The entry condition includes the status of the card, and the balance of the card is valid
	 * If you pull in successfully or get on the train, return 1
	 * 
	 * @param type: the tpye of Public transport : bus or subway
	 * @param station: The station where the user gets on the bus
	 * @param datetime: User boarding time
	 * @param id :card's id
	 * @return Return 1 if you get on or pull in successfully,0 for failed get on traffic or entry station
	 * 2 for can not find card's id
	 */
	public int onTraffic(String type, Station station,LocalDateTime datetime, int id) {// 1 上车成功，0 上车失败， 2找不到卡
		//String Id = String.valueOf(id); 
		for(int i =0; i<CardList.size();i++) {
			if(CardList.get(i).getId() == id) {
				if(CardList.get(i).getState() == 1 && CardList.get(i).isValid()) {//余额不足的情况
					if(CardList.get(i).getBalance() > 0) {
						CardList.get(i).setOnTraffic();
						stime = datetime;
						startstation = station;
						Act act = new Act(getCard(id));
						act.take(stime, startstation, type, this.map);
						flag = 1;
						return flag;
					}
					flag = 0;// 违规
					return flag;
					
				}
				flag = 0;// 没钱
				return flag;
				
			}
		}
		return 2;
	}
	
	/**
	 * Returns a int to determine whether the card successfully out of station or get off the bus
	 * The get off or out of station's condition includes the status of the card, and the balance of the card is valid
	 * If you out of station and get off the bus successfully  return 0
	 * 
	 * @param type: the tpye of Public transport : bus or subway
	 * @param station: The station where the user gets on the bus
	 * @param datetime: User boarding time
	 * @param id :card's id
	 * @return Return 0 if you get off or out of station in successfully, 1 for falied Get off or exit
	 * 2 for can not find card's id
	 */
	public int offTraffic(String type, Station station,LocalDateTime datetime, int id) {// 0 下车成功，2 找不到卡
		for(int i =0; i<CardList.size();i++ ) {
			if(CardList.get(i).getId() == id) {
				if(CardList.get(i).getState() == 0) {//下车不考虑余额不足的情况
					CardList.get(i).setOffTraffic();
					etime = datetime;
					endstation = station;
					Act act = new Act(getCard(id));
					act.takeoff(etime, startstation, endstation, type, this.map);
					flag = 0;
					return flag;
				}
				if (! CardList.get(i).isValid()) {
					return 2;
				}
				flag = 1;
				return flag;
			}
		}
		return 2;
	}
	
	/**
	 * return User by given user's name
	 * @param n
	 * @return User by given user's name
	 */
	public User getUser(String n) {
		for (int i =0; i < UserList.size();i++ ) {
			if(UserList.get(i).getUserName().equals(n)) {
				return UserList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * return a card by given card's id
	 * @param id
	 * @return a card by given card's id
	 */
	public Card getCard(int id) {
		for (int i =0; i < CardList.size();i++ ) {
			if(CardList.get(i).getId() == id) {
				return CardList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * return a String representing the sum of the system charges and 
	 * the total number of passenger stations per day by given date
	 * @param date
	 * @return a String representing the sum of the system charges and 
	 * the total number of passenger stations per day
	 */
	public String operatingCost(LocalDate date) {
		double sum = 0.0;
		int numStation = 0;
		for (Card card : this.CardList) {
			for (Trip t : card.getTripHistory()) {
				if ((t.getStartTime().toLocalDate()).equals(date)) {
					sum += t.getCost();
					Station start = t.getTrip().get(0).get_location();
					Station end = t.getTrip().get(t.getTrip().size() - 1).get_location();
					numStation += this.map.getNumStation(start, end);
				}
			}
		}
		
		String s = "Total cost of " + date + " is $" + sum + 
				". The number of total number of stops/stations reached by all cardholders is " + numStation;
		return s;
	}
	
}
