package cst8284.asgmt2.roomScheduler;
/* Course Name: CST8284
   Student Name: Yajun ZHANG 040958689
   Class name: RoomScheduler.java
   Date: 2020-02-22
*/

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import cst8284.asgmt2.room.Room;

public class RoomScheduler {
	private static Scanner scan = new Scanner(System.in);
	private ArrayList<RoomBooking> roomBookings = new ArrayList<>();
	private Room room;
	private static final int DISPLAY_ROOM_INFORMATION = 1, ENTER_ROOM_BOOKING = 2, DELETE_BOOKING = 3,
			CHANGE_BOOKING = 4, DISPLAY_BOOKING = 5, DISPLAY_DAY_BOOKINGS = 6, SAVE_BOOKINGS_TO_FILE = 7,
			LOAD_BOKKINGS_FROM_FILE = 8, EXIT = 0;

	public RoomScheduler(Room room) {
		setRoom(room);
	}
	
	public void lauch() {
		loadBookingsFromFile();
		int choice;
		do {
			choice = displayMenu();
			executeMenuItem(choice);
		} while (choice != EXIT);
		saveBookingsToFile();
	}

	
	private int displayMenu() {
		System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", "Enter a selection from the following menu:",
				DISPLAY_ROOM_INFORMATION + ". Display room information", ENTER_ROOM_BOOKING + ". Enter a room booking",
				DELETE_BOOKING + ". Remove a room booking", CHANGE_BOOKING + ". Change a room booking",
				DISPLAY_BOOKING + ". Display a booking",
				DISPLAY_DAY_BOOKINGS + ". Disaplay room bookings for the whole day",
				SAVE_BOOKINGS_TO_FILE + ". Backup current bookings to file",
				LOAD_BOKKINGS_FROM_FILE + ". Load current bookings from file", EXIT + ". Exit program");
		int choice = scan.nextInt();
		scan.nextLine();
		System.out.println();
		return choice;
	}

	
	private void executeMenuItem(int choice) {
		switch (choice) {
		case DISPLAY_ROOM_INFORMATION:
			displayRoomInfo();
			break;
		case ENTER_ROOM_BOOKING:
			saveRoomBooking(makeBookingFromUserInput());
			break;
		case DELETE_BOOKING:
			System.out.println("Enter booking to delete");
			deleteBooking(makeCalendarFromUserinput(null, true));
			break;
		case CHANGE_BOOKING:
			System.out.println("Enter booking to change");
			changeBooking(makeCalendarFromUserinput(null, true));
			break;
		case DISPLAY_BOOKING:
			displayBooking(makeCalendarFromUserinput(null, true));
			break;
		case DISPLAY_DAY_BOOKINGS:
			displayDayBookings(makeCalendarFromUserinput(null, false));
			break;
		case SAVE_BOOKINGS_TO_FILE:
			saveBookingsToFile();
			break;
		case LOAD_BOKKINGS_FROM_FILE:
			loadBookingsFromFile();
			break;
		case EXIT:
			System.out.print("Exiting Room Booking Application");
			break;
		default:
			System.out.println("Invalid input choice. Please try again.");
		}
		System.out.println();		
	}

	private void displayRoomInfo() {
		System.out.print(getRoom());
	}
	
	private boolean saveRoomBooking(RoomBooking booking) {
		int count = booking.getTimeBlock().duration();
		Calendar startTime = booking.getTimeBlock().getStartTime();
		Calendar markTime = (Calendar) startTime.clone();
		for (int i = 0; i < count; i++) {
			markTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY) + i);
			if (findBooking(markTime) != null) {
				displayBooking(markTime);
				System.out.println("Attempt failed! Conflicts with an existing booking above.");
				return false;
			}
		}
		System.out.println("Booking time and date saved.");
		return getRoomBookings().add(booking);
	}

	private boolean deleteBooking(Calendar cal) {
		RoomBooking rm = displayBooking(cal);
		if (rm != null) {
			System.out.print("Press 'Y' to confirm deletion, any other key to abort: ");
			char key = scan.nextLine().charAt(0);
			if (key == 'Y') {
				getRoomBookings().remove(rm);
				System.out.println("Booking deleted");
			}
			return key == 'Y';
		} else
			return false;
	}
	
	private boolean changeBooking(Calendar cal) {
		RoomBooking rm = displayBooking(cal);
		if (rm != null) {
			Calendar newStartTime = makeCalendarFromUserinput(cal, false);
			System.out.print("Enter New ");
			Calendar newEndTime = makeCalendarFromUserinput(cal, true);

			Calendar markStartTime = (Calendar) newStartTime.clone();
			int hour = markStartTime.get(Calendar.HOUR_OF_DAY);
			for (; hour < newEndTime.get(Calendar.HOUR_OF_DAY); hour++) {
				markStartTime.set(Calendar.HOUR_OF_DAY, hour);
				if (findBooking(markStartTime) != null && findBooking(markStartTime) != rm) {
					displayBooking(markStartTime);
					System.out.println("Cannot change booking. Conflicts with an existing booking above.");
					return false;
				}
			}
			rm.getTimeBlock().setStartTime(newStartTime);
			rm.getTimeBlock().setEndTime(newEndTime);
			System.out.println("Booking has been changed to:");
			displayBooking(newStartTime);
			return true;
		} else
			return false;
	}

	private RoomBooking displayBooking(Calendar cal) {
		RoomBooking booking = findBooking(cal);
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		System.out.print((booking != null) ? "---------------\n" + booking + "---------------\n"
				: "No booking scheduled between " + hr + ":00 and " + (hr + 1) + ":00\n");
		return booking;
	}

	private void displayDayBookings(Calendar cal) {
		for (int i = 8; i < 24; i++) {
			cal.set(Calendar.HOUR_OF_DAY, i);
			RoomBooking roomBooking = displayBooking(cal);
			if (roomBooking != null)
				i += (roomBooking.getTimeBlock().duration() - 1);
		}
	}

	private boolean saveBookingsToFile() {
		try (FileOutputStream fos = new FileOutputStream("CurrentRoomBookings.book");
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			for (RoomBooking rm : getRoomBookings())
				oos.writeObject(rm);
			System.out.println("Current room bookings backed up to file");
			return true;
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {}
		return false;
	}
	
	private ArrayList<RoomBooking> loadBookingsFromFile() {
		getRoomBookings().clear();
		try (FileInputStream fis = new FileInputStream("CurrentRoomBookings.book");
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			while (true)
				getRoomBookings().add((RoomBooking) ois.readObject());
		} catch (EOFException ex) {
			System.out.println("Current room bookings loaded from file");
		} catch (FileNotFoundException | ClassNotFoundException ex) {
		} catch (IOException ex) {}
		return getRoomBookings();
	}
	
	private static String getResponseTo(String s) {
		System.out.print(s);
		return (scan.nextLine());
	}

	private RoomBooking makeBookingFromUserInput() {
		String[] fullName = getResponseTo("Enter Client Name (as FirstName LastName): ").split(" ");
		String phoneNumber = getResponseTo("Phone Number (e.g. 613-555-1212): ");
		String organization = getResponseTo("Organization (optional): ");
		String category = getResponseTo("Enter event category: ");
		String description = getResponseTo("Enter detailed description of event: ");
		Calendar startTime = makeCalendarFromUserinput(null, true);
		Calendar endTime = makeCalendarFromUserinput(startTime, true);
		return new RoomBooking(new TimeBlock(startTime, endTime),
				new ContactInfo(fullName[0], fullName[fullName.length - 1], phoneNumber, organization),
				new Activity(category, description));
	}
	
	
	
	// java.util.Calendar - Javatpoint[Webpage]
	// Retrieved from: https://www.javatpoint.com/java-util-calendar
	private Calendar makeCalendarFromUserinput(Calendar cal, boolean requestHour) {
		if (cal == null) {
			cal = Calendar.getInstance();
			cal.clear();
			String ddmmyyyy = getResponseTo("Event Date (entered as DDMMYYYY): ");
			int day = Integer.parseInt(ddmmyyyy.substring(0, 2));
			int month = Integer.parseInt(ddmmyyyy.substring(2, 4)) - 1;
			int year = Integer.parseInt(ddmmyyyy.substring(4));
			cal.set(year, month, day, 0, 0, 0);
			if (requestHour == true)
				cal.set(Calendar.HOUR_OF_DAY, processTimeString(getResponseTo("Start Time: ")));
		} else {
			Calendar endTime = (Calendar) cal.clone();
			endTime.set(Calendar.HOUR_OF_DAY,
					processTimeString(getResponseTo((requestHour == true) ? "End Time: " : "Enter New Start Time: ")));
			return endTime;
		}
		return cal;
	}

	
	// Java String - javatpoint[Webpage]
	// Retrieved from: https://www.javatpoint.com/java-string
	private static int processTimeString(String t) {
		return (t.trim().toLowerCase().contains("p")) ? (Integer.parseInt(t.split(" |:")[0]) + 12)
				: Integer.parseInt(t.split(" |:")[0]);
	}

	
	private RoomBooking findBooking(Calendar cal) {
		Calendar endTime = (Calendar) cal.clone();
		endTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
		TimeBlock timeBlock = new TimeBlock(cal, endTime);
		for (RoomBooking rb : getRoomBookings()) {
			if (rb == null)
				continue;
			if (rb.getTimeBlock().overlaps(timeBlock))
				return rb;
		}
		return null;
	}

	
	private ArrayList<RoomBooking> getRoomBookings() { return roomBookings; }

	private void setRoom(Room room) { this.room = room; }
	
	private Room getRoom() { return room; }

}
