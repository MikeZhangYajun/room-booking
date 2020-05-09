package cst8284.asgmt2.room;
/* Course Name: CST8284
Student Name: Yajun ZHANG 040958689
Class name: Classroom.java
Date: 2020-02-22
*/

public final class Classroom extends Room{
	
	private int seats;
	private static final int DEFAULT_SEATS = 120;
	
	public Classroom() {}
	
	@Override
	protected int getSeats() {
		return DEFAULT_SEATS;

	}

	@Override
	protected String getRoomType() { return "class room"; }

	
	@Override
	protected String getDetails() {		
		return "contains overhead projector";
	}

}
