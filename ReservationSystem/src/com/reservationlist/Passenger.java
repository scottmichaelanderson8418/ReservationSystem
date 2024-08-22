
package com.reservationlist;


import java.io.Serializable;
import java.text.DecimalFormat;

//Name: Scott Michael Anderson
//Date: 10/18/2023
//Project 01 - Reservation System

//aka "Node Class"
public class Passenger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// this is the cost the passenger pain (each seat may have a different price)
	private int cost;
	DecimalFormat f = new DecimalFormat("#,###,###");
	// name of passenger
	private String name;
	// This is a reference to the passenger that follows this passenger in
	// the list
	Passenger passengerNext;
	// This is a reference to the passenger that precedes this passenger in
	// the list
	Passenger passengerPrev;

	// phone number of passenger
	private String phone;

	// The seat number assigned
	private int seat;

	// default constructor for Passenger class
	public Passenger() {

		this.name = "";
		this.phone = "";
		this.seat = 0;
		this.cost = 0;
		this.passengerNext = null;
		this.passengerPrev = null;

	}

	public Passenger(Passenger activeNode) {

		this.name = activeNode.name;
		this.phone = activeNode.phone;
		this.seat = activeNode.seat;
		this.cost = activeNode.cost;
		this.passengerNext = activeNode.passengerNext;
		this.passengerPrev = activeNode.passengerPrev;

	}

	// overloaded constructor for passenger class
	public Passenger(String name, String phone, int seat, int cost) {

		this.name = name;
		this.phone = formatPhoneNumber(phone);
		this.seat = seat;
		this.cost = cost;

	}

	public String formatPhoneNumber(String phoneNumber) {
		String formattedNumber = "";
		formattedNumber = phoneNumber.replaceAll("^[0-9]", "");

		formattedNumber = phoneNumber.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");

		return formattedNumber;

	}

	// getters
	public int getCost() {
		return cost;
	}

	public String getName() {
		return name;
	}

	public Passenger getPassengerNext() {
		return passengerNext;
	}

	public Passenger getPassengerPrev() {
		return passengerPrev;
	}

	public String getPhone() {
		return phone;
	}

	public int getSeat() {
		return seat;
	}

	// setters
	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassengerNext(Passenger passengerNext) {
		this.passengerNext = passengerNext;
	}

	public void setPassengerPrev(Passenger passengerPrev) {
		this.passengerPrev = passengerPrev;
	}

	public void setPhone(String phone) {

		phone = formatPhoneNumber(phone);

		this.phone = phone;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String toString() {

		return "name: " + this.getName() + "\n" + "phone:  " + this.getPhone() + "\n" + "seat: " +
				this.getSeat() + "\n" + "cost: $" + f.format(this.getCost()) + "\n";

	}
}
