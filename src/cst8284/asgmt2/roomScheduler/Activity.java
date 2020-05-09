package cst8284.asgmt2.roomScheduler;
/* Course Name: CST8284 Object Oriented Programming (Java)
   Student Name: Yajun ZHANG 040958689
   Class name: Activity
   Date: 2020-01-28
*/

import java.io.Serializable;

public class Activity implements Serializable{
	public static final long serialVersionUID = 1L;

	private String description, category;

	public Activity(String category, String description) {
		setCategory(category);
		setDescription(description);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "Event: " + getCategory() + "\nDescription: " + getDescription() + "\n";
	}

}
