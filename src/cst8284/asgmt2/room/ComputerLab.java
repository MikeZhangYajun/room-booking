package cst8284.asgmt2.room;
/* Course Name: CST8284
Student Name: Yajun ZHANG 040958689
Class name: ComputerLab.java
Date: 2020-02-22
*/

public class ComputerLab extends Room {

	private int seats;
	private static final int DEFAULT_SEATS = 30;

	public ComputerLab() {}

	@Override
	protected int getSeats() {
		return DEFAULT_SEATS;
	}

	@Override
	protected String getRoomType() {
		return "computer lab";
	}

	@Override
	protected String getDetails() {
		return "contains outlets for 30 laptops";
	}

}
