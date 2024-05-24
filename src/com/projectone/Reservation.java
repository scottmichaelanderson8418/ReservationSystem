package com.projectone;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Name: Scott Michael Anderson
//Date: 10/18/2023
//Project 01 - Reservation System

// The Reservation class will function as a doubly linked list that contains
// Passenger objects This class will contain our doubly linked list
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	int addPassengerCount = 0;

	DecimalFormat f = new DecimalFormat("#,###,###");

	// A dummy node - a blank Passenger Object that will represent the first
	// passenger in the reservation list
	private Passenger head;

	int printCurrentCount = 0;

	// The number of reservations in the reservations list
	private int size;

	// Passenger instance to represent the tail of the list
	private Passenger tail;

	// instantiate the Passenger object for the overloaded Reservation constructor
	Reservation existingReservationList;

	// Default constructor
	public Reservation() {
		// To Do: No-arg constructor should connect the head and tail nodes of the
		// double linked list
		head = new Passenger();
		tail = new Passenger();
		head.setPassengerNext(tail);
		tail.setPassengerPrev(head);
		size = 0;

	}

	// Overloaded constructor
	public Reservation(Reservation existingReservationList) {
		if (existingReservationList.getSize() != 0) {
			head = new Passenger();
			tail = new Passenger();
			head.setPassengerNext(tail);
			tail.setPassengerPrev(head);
			this.existingReservationList = existingReservationList;

		}
	}

	// Overloaded constructor
	public Reservation(Passenger passenger) {

		this.head = null;
		this.tail = null;
		this.size = 0;

	}

	// Method to add instance/object of Passenger class to the instance of the
	// Reservation class (AKA Reservation List)

	public Passenger addPassenger(final Scanner scanner, Passenger newNode, Passenger activeNode,
			Reservation list) throws Exception {

		if (newNode.getName().equals("Dummy Tail") || newNode.getName().equals("Dummy Head")) {

			// LL is empty
			// Check to make sure there is no head node in this
			if (this.head == null) {

				// if list is empty then create a new node and make it equal to the head of the
				// list newNode
				// list-->head = newNode
				list.setHead(newNode);

				// list-->tail = newNode
				list.setTail(newNode);

			} else {

				// list--> tail-->next = newNode
				list.getTail().passengerNext = newNode;
				// newNode --> prev = list--> tail
				newNode.passengerPrev = list.getTail();
				// list-->tail = newNode
				list.setTail(newNode);

			}

		} else {

			// get user input for the newNode
			newNode = getAllInput(scanner, newNode);

			// point the previous pointer of newNode to the passenger before the tail node
			newNode.passengerPrev = list.getTail().passengerPrev;

			// newNode-->next = list-->tail
			newNode.passengerNext = list.getTail();

			// list-->tail-->prev-->next = newNode
			list.getTail().passengerPrev.passengerNext = newNode;

			// list-->tail-->prev = newNode
			list.getTail().passengerPrev = newNode;

			if (addPassengerCount == 0) {
				activeNode = newNode;
				addPassengerCount++;
			}

			this.size += 1;

		}
		return activeNode;

	}

	// Method to add an instance of the Passenger Class after the passenger object
	// currently pointed to by the Reservation Class instance/object
	public void addPassengerAfter(final Scanner scanner, Passenger curNode, Passenger newNode,
			Passenger sucNode, Reservation list) throws Exception {

		newNode = getAllInput(scanner, newNode);

		sucNode = curNode.passengerNext;

		// newNode->next = curNode-->next
		newNode.passengerNext = curNode.passengerNext;

		// newNode-->prev = curNode
		newNode.passengerPrev = curNode;

		// curNode-->next = newNode
		curNode.passengerNext = newNode;

		// curNode-->next-->prev = newNode
		sucNode.passengerPrev = newNode;

		this.size += 1;

	}

	// Delete current passenger
	public void deletePassenger(final Scanner scanner, Reservation list, Passenger activeNode)
			throws Exception {

		// // create a reference to the sucNode
		// Passenger sucNode = activeNode.passengerNext;
		//
		// // create a reference to the prevNode
		// Passenger prevNode = activeNode.passengerPrev;

		System.out
				.println("-- CurrentPassenger = " + activeNode.getName() + " has been deleted ---");

		list.setSize(list.getSize() - 1);

		Passenger sucNode = activeNode.passengerNext;
		Passenger predNode = activeNode.passengerPrev;

		if (sucNode != null) {
			sucNode.passengerPrev = predNode;
		}

		if (predNode != null) {
			predNode.passengerNext = sucNode;
		}

		if (activeNode == list.getHead()) {
			list.setHead(sucNode);
		}

		if (activeNode == list.getTail()) {

			list.setTail(predNode);
		}

		// // sucNode-->prev = prevNode
		// activeNode.passengerNext.passengerPrev = activeNode.passengerPrev;
		//
		// // prevNode-->next = sucNode
		// activeNode.passengerPrev.passengerNext = activeNode.passengerNext;

		// set activeNode to the passenger after the passenger being deleted
		activeNode = list.skipToNextPassenger(activeNode, list);

	}

	// Method to get user input for each instance of the Passenger Class
	public Passenger getAllInput(final Scanner scanner, Passenger passenger) throws Exception {

		getNameInput(scanner, passenger);

		getPhoneNumberInput(scanner, passenger);

		getSeatInput(scanner, passenger);

		getCostInput(scanner, passenger);

		return passenger;
	}

	// Method to get the user input for the passenger cost
	public void getCostInput(final Scanner scanner, Passenger passenger) throws Exception {

		boolean done = false;

		int value = 0;

		while (!done) {
			try {

				System.out.print("Enter cost of the passenger: ");

				value = Integer.parseInt(scanner.nextLine());

				verifyCostInput(value);

				passenger.setCost(value);

				done = true;

			} catch (NumberFormatException e) {

				System.out.println("Invalid input...please enter a numeric choice !\n");

			} catch (Exception e) {
				throw new Exception(e.getMessage());

			}
		}
	}

	// list-->tail-->prev = curNode
	public Passenger getHead() {
		return head;
	}

	// Method to get user input for the passenger name
	public void getNameInput(final Scanner scanner, Passenger passenger) throws Exception {

		boolean done = false;

		String value = "";

		while (!done) {

			System.out.print("Enter the name of passenger: ");

			value = scanner.nextLine();

			done = verifyNameInput(scanner, value);

			if (done != true) {

				System.out.println("\n--- PLEASE ENTER A STRING VALUE ---\n");
				pressEnter(scanner);

			} else {

				passenger.setName(value);
			}

		}

	}

	// Method to get user input for the passenger object
	public void getPhoneNumberInput(final Scanner scanner, Passenger passenger) throws Exception {

		boolean done = false;

		String value = "";

		while (!done) {

			System.out.print("Enter the phone number of passenger (###-###-####): ");

			value = scanner.nextLine();

			done = verifyPhoneNumber(value);

			if (done == true) {
				passenger.setPhone(value);
			}

		}
	}

	// Method to get the input for the passenger seat
	public void getSeatInput(final Scanner scanner, Passenger passenger) throws Exception {

		boolean done = false;

		int value = 0;

		while (!done) {
			try {

				System.out.print("Enter the seat of passenger: ");

				value = Integer.parseInt(scanner.nextLine());

				verifySeatInput(value);

				passenger.setSeat(value);

				done = true;

			} catch (NumberFormatException e) {

				System.out.println("--- INVALID CHOICE, PLEASE ENTER INTEGER VALUES ---!\n");

			} catch (Exception e) {
				throw new Exception(e.getMessage());

			}
		}
	}

	public int getSize() {

		return size;
	}

	public Passenger getTail() {
		return tail;
	}

	public void pressEnter(final Scanner scanner) {
		scanner.nextLine();

	}

	// Print the current passenger that is currently pointed to in the reservation
	// list
	public void printCurrentPassenger(Passenger activeNode, Passenger newNode, Reservation list) {

		int count = 1;
		boolean booA = false;

		// create a reference variable for a Passenger object
		// set the reference variable to the Reservation list passenger at the head of
		// the list

		// System.out.println(list.getHead().passengerNext);

		Passenger currentPassenger = list.getHead().passengerNext;

		// for the first time through the printCurrentMethod() use the following logic
		if (printCurrentCount == 0) {

			// if the list is empty the currentPassenger will be the "dummy tail"
			if (currentPassenger.getName() == "") {
				System.out.println("--- RESERVATION LIST IS EMPTY ---");

			} else {
				System.out.println("*** CURRENT PASSENGER ***");
				System.out.print("Passenger #" + count);
				System.out.println();
				System.out.println(currentPassenger.toString());

			}

			// printCurrentMethod() is already called once so use this logic
		} else {

			// start with head of list and loop through until the "activeNode" is found
			while (!booA) {
				// System.out.println(currentPassenger.toString());
				if (currentPassenger.getName() == activeNode.getName()) {

					booA = true;

				} else {

					currentPassenger = currentPassenger.passengerNext;

					count++;
				}

			}

			// if the list is empty the currentPassenger will be the "dummy tail"
			if (activeNode.getName() == "Dummy Tail") {
				System.out.println("--- RESERVATION LIST IS EMPTY ---");

			} else {
				System.out.println("*** CURRENT PASSENGER ***");
				System.out.print("Passenger #" + count);
				System.out.println();
				System.out.println(activeNode.toString());

				// System.out.println("Passenger #" + list.getSize() + list.getTail());

			}

		}

		printCurrentCount++;

	}

	// Method to print the current ReservationList
	public void printList(Reservation list) throws Exception {

		System.out.println(list.toString());

	}

	public void setHead(Passenger head) {
		this.head = head;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTail(Passenger tail) {
		this.tail = tail;
	}

	// Method changes the current passenger to the passenger after the current
	// passenger
	public Passenger skipToNextPassenger(Passenger activeNode, Reservation list) {

		if (activeNode.passengerNext == list.getTail()) {

			activeNode = getHead().passengerNext;

			// getHead().passengerNext = activeNode;

		} else {

			activeNode = activeNode.passengerNext;
			// activeNode.passengerNext = activeNode;

		}

		return activeNode;

	}

	// Method changes current passenger to the previous passenger
	public Passenger skipToPreviousPassenger(Passenger activeNode, Reservation list) {

		// if the previous passenger equals the list head then set the active node to
		// the node before the dummy tail node
		if (activeNode.passengerPrev == list.getHead()) {

			activeNode = getTail().passengerPrev;

		} else {

			activeNode = activeNode.passengerPrev;
			// activeNode.passengerPrev = activeNode;
		}

		return activeNode;

	}

	// Modified toString() method for Reservation Class
	public String toString() {
		int count = 1;
		int sum = 0;

		String printAll = "";

		Passenger current = this.getHead().passengerNext;

		System.out.println("****** Reservation List ******");

		while (current != null) {
			if (current.getName().equals("") || current.getName().equals("")) {
				current = null;
			} else {

				printAll += "Passenger #" + count + "\n" + current.toString() + "\n";

				sum += current.getCost();

				current = current.passengerNext;
				count++;
			}
		}

		return printAll + "\nReservation List Size = " + this.getSize() + "\nTotal sum = $" +
				f.format(sum);
	}

	public void validateChoice(int choice) throws Exception {
		if (choice < 1 || choice > 8) {
			throw new Exception("Invalid selection. Please enter integer value 1-10");
		}

	}

	public void verifyCostInput(int integerValue) throws Exception {

		if (integerValue <= 0) {
			throw new Exception("--- PLEASE ENTER A POSITIVE INTEGER VALUE ---");
		}

	}

	// Method to verify the input for the passenger name
	public boolean verifyNameInput(final Scanner scanner, String name) throws Exception {

		// Define a regular expression pattern that matches only letters (a-zA-Z).
		Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");

		// Use a Matcher to check if the input matches the pattern.
		Matcher matcher = pattern.matcher(name);

		return matcher.matches();

		// Return true if the input contains only letters, false otherwise.

	}

	// Method to verify the input for the passenger phone number
	public boolean verifyPhoneNumber(String phoneNumber) throws Exception {

		boolean phoneBoo = false;

		// Define a regular expression pattern to match a valid phone number.
		// This example pattern allows digits, hyphens, spaces, and parentheses.
		String pattern = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$";// You may need to adjust this pattern based
														// on your
		// requirements.

		String pattern2 = "^[0-9]{3}[0-9]{3}[0-9]{4}$";
		// Use the matches method to check if the input matches the pattern.
		if (phoneNumber.matches(pattern) || phoneNumber.matches(pattern2)) {

			phoneBoo = true;
		}

		System.out.println("Phone verification Boolean = " + phoneBoo);

		if (phoneBoo != true) {
			System.out.println("--- ENTER INTEGER VALUES WITH OR WITHOUT DASHES ---");
			System.out.println("--- PHONE NUMBER FORMAT:  ###-###-#### ---");

		}

		return phoneBoo;
	}

	// Method to verify input for the passenger seat
	public void verifySeatInput(int integerValue) throws Exception {

		if (integerValue <= 0) {
			throw new Exception("--- ENTER INTEGER VALUES ---");
		}

	}

}
