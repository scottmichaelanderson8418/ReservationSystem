package com.reservationlist;

import java.util.Scanner;

//Name: Scott Michael Anderson
//Date: 10/18/2023
//Project 01 - Reservation System

public class Demo {

	// Demo Class will serve to accept user input and manipulate the Reservation
	// doubly linked list The Demo Class will instantiate a Reservation object and
	// display a menu to the user that will allow for the following actions:
	//
	// 1. Add a new Passenger to the reservation system
	// 2. Insert a new passenger after the passenger that is currently pointed
	// 3. Print the contents of the reservation list
	// 4. Display the current passenger for the first time -Display
	// name, phone, seat, and cost
	// 5. Cancel the current reservation (meaning that remove the current passenger)
	// -Display message that the reservation of that specific passenger is canceled
	// - When removing the current passenger, the new current passenger will be the
	// passenger that director follow the removed passenger
	// 6. Skip to the next passenger - If you are at the end of the
	// reservation list, the next Passenger should be the first Passenger in the
	// reservation list
	// 7. Return to the previous passenger
	// 8. Exit
	// Demo class will be used to make modifications to the Reservation list

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);

		// load up the task list from a file...
		// TaskManager taskManager = new TaskManager(new ArrayList<Task> ());
		// Reservation list = FileManager.readFromFile();

		// Create an instance of the Reservation class
		// The reservation class will be the doubly linked list and will contain the
		// Passenger objects
		Reservation list = new Reservation();
		//
		// boolean variable for exiting the program
		boolean done = false;

		// create reference variable "activeNode" for the Passenger Class
		// the reference variable will keep track of the current passenger the doubly
		// linked list is pointing to
		Passenger activeNode = new Passenger();

		// System.out.println(activeNode);
		while (!done) {

			// creates instance of Integer class named selection
			Integer selection = displayMenu(scanner);

			// create instance of a new Passenger named "newNode"
			Passenger newNode = new Passenger();

			switch (selection) {

			case 1: {

				// 1. Add a new Passenger to the reservation system"
				activeNode = list.addPassenger(scanner, newNode, activeNode, list);

				break;
			}

			case 2:

				// Insert a new passenger after the passenger that is currently pointed
				Passenger sucNode = new Passenger();

				if (list.getSize() == 0) {
					activeNode = list.addPassenger(scanner, newNode, activeNode, list);

				} else {

					list.addPassengerAfter(scanner, activeNode, newNode, sucNode, list);
				}

				break;

			case 3:

				// "3. Print the contents of the reservation list"
				if (list.getSize() > 0) {

					list.printList(list);

				} else {
					System.out.println("--- EMPTY LIST ---");
				}

				break;

			case 4:

				if (list.getSize() > 0) {
					// "4. Display the current passenger"
					list.printCurrentPassenger(activeNode, newNode, list);
				} else {
					System.out.println("--- EMPTY LIST ---");
				}

				break;

			case 5:

				// 5. Cancel the current reservation (meaning that remove the current passenger
				// Passenger curPassenger = list.getTail().passengerPrev;
				if (list.getSize() > 0) {

					list.deletePassenger(scanner, list, activeNode);
					System.out.println(activeNode);
					activeNode = list.skipToNextPassenger(activeNode, list);

					break;

				} else {
					System.out.println("--- EMPTY LIST ---");
					break;
				}

			case 6:

				// "6. Skip to the next passenger"
				System.out.println(list.getSize());
				if (list.getSize() == 0) {
					System.out.println("--- EMPTY LIST ---");
					break;
				} else {
					activeNode = list.skipToNextPassenger(activeNode, list);

					break;
				}

			case 7:

				// "7. Return to the previous passenger"
				if (list.getSize() == 0) {
					System.out.println("--- EMPTY LIST ---");

				} else {
					activeNode = list.skipToPreviousPassenger(activeNode, list);

				}
				break;

			case 8:

				// "8. Exit"

				System.out.println("Goodbye...");

				done = true;

				break;

			default:
				System.out.println("Invalid choice...please try again");
				pressEnter(scanner);
				break;
			}

		}
		// write the task list to a file...

		scanner.close();
	}

	// -------------------------------------------------------------------------------
	// Display the Menu
	public static int displayMenu(final Scanner scanner) throws Exception {

		boolean done = false;
		int choice = 0;

		System.out.println();
		System.out.println("*************** Reservation Main Menu ***************");
		System.out.println("1. Add a new Passenger to the reservation system");

		System.out
				.println("2. Insert a new passenger after the passenger that is currently pointed");
		System.out.println("3. Print the contents of the reservation list");
		System.out.println("4. Display the current passenger for the first time");
		System.out.println("5. Cancel the current reservation (remove the current passenger)");
		System.out.println("6. Skip to the next passenger");
		System.out.println("7. Return to the previous passenger");
		System.out.println("8. Exit");

		while (!done) {

			try {

				choice = Integer.parseInt(scanner.nextLine());

				done = validateChoice(choice);

			} catch (NumberFormatException e) {

				System.out.println("Invalid input...please enter a numeric choice !\n");

			} catch (Exception e) {
				throw new Exception(e.getMessage());

			}

		}

		return choice;

	}

	public static boolean validateChoice(int choice) throws Exception {

		try {
			if (choice < 1 || choice > 8) {

				throw new Exception("Invalid selection. Please enter integer value 1-8");

			} else {
				return true;
			}
		} catch (Exception eee) {

			System.out.println(eee.getMessage());
			return false;
		}

	}

	public static void pressEnter(final Scanner scanner) {
		scanner.nextLine();

	}

}
