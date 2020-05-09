package cst8284.asgmt2.roomScheduler;
/* Course Name: CST8284
   Student Name: Yajun ZHANG 040958689
   Class name: RoomSchedulerLauncher.java
   Date: 2020-02-22
*/
import cst8284.asgmt2.room.ComputerLab;

public class RoomSchedulerLauncher {

	public static void main(String[] args) {
	
		new RoomScheduler(new ComputerLab() {{
			setRoomNumber("B119");
		}}).lauch();
	}
}
