package cst8284.asgmt2.roomScheduler;
/* Course Name: CST8284
   Student Name: Yajun ZHANG 040958689
   Class name: RoomBooking.java
   Date: 2020-01-28
*/

import java.io.Serializable;

public class RoomBooking implements Serializable{
	public static final long serialVersionUID = 1L;

	private ContactInfo contactInfo;
	private Activity activity;
	private TimeBlock timeBlock;

	public RoomBooking(TimeBlock timeBlock, ContactInfo contactInfo, Activity activity) {
		setTimeBlock(timeBlock);
		setContactInfo(contactInfo);
		setActivity(activity);
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setTimeBlock(TimeBlock timeBlock) {
		this.timeBlock = timeBlock;
	}

	public TimeBlock getTimeBlock() {
		return timeBlock;
	}

	@Override
	public String toString() {
		return getTimeBlock().toString() + getActivity().toString() + getContactInfo().toString();
	}

}
