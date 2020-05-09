package cst8284.asgmt2.room;
/* Course Name: CST8284
Student Name: Yajun ZHANG 040958689
Class name: Boardroom.java
Date: 2020-02-22
*/

public final class Boardroom extends Room{
	
	private int seats;
	
	public Boardroom() {}

	@Override
	protected int getSeats() {		
		return 16;
	}

	@Override
	protected String getRoomType() {		
		return "board room";
	}

	@Override
	protected String getDetails() {		
		return "conference call enabled";
	}

}
