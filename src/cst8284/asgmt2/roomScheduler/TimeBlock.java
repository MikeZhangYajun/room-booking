package cst8284.asgmt2.roomScheduler;
/* Course Name: CST8284
   Student Name: Yajun ZHANG 040958689
   Class name: TimeBlock
   Date: 2020-01-28
*/

import java.io.Serializable;
import java.util.Calendar;

public class TimeBlock implements Serializable{
	public static final long serialVersionUID = 1L;

	private Calendar startTime, endTime;
	
	public TimeBlock() {
		this(new Calendar.Builder().set(Calendar.HOUR, 8).build(),
				new Calendar.Builder().set(Calendar.HOUR, 23).build());
	}
	
	public TimeBlock(Calendar start, Calendar end) {
		setStartTime(start);
		setEndTime(end);
	}
	
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	
	public Calendar getStartTime() {
		return startTime;
	}
	
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	
	public Calendar getEndTime() {
		return endTime;
	}
	
	public boolean overlaps(TimeBlock newBlock) {
		if (getStartTime().get(Calendar.YEAR) == newBlock.getStartTime().get(Calendar.YEAR)
				&& getStartTime().get(Calendar.DAY_OF_YEAR) == newBlock.getStartTime().get(Calendar.DAY_OF_YEAR))
			return !(getEndTime().get(Calendar.HOUR_OF_DAY) <= newBlock.getStartTime().get(Calendar.HOUR_OF_DAY)
					|| getStartTime().get(Calendar.HOUR_OF_DAY) >= newBlock.getEndTime().get(Calendar.HOUR_OF_DAY));
		return false;
	}
	
	public int duration() {
		return (getEndTime().get(Calendar.HOUR_OF_DAY) - getStartTime().get(Calendar.HOUR_OF_DAY));
	}
	
	@Override
	public String toString() {
		return getStartTime().get(Calendar.HOUR_OF_DAY) + ":00 - " + getEndTime().get(Calendar.HOUR_OF_DAY) + ":00\n";
	}

}
