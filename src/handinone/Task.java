package handinone;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "task")
@XmlType(propOrder={"id", "name", "date", "status", "description", "attendant"})
public class Task {

	private String id, name, date, status, description, attendant;
	
	public Task() {}
	
	public Task(String id, String name, String date, String status, String description, String attendant) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.status = status;
		this.description = description;
		this.attendant = attendant;
	}
	
	@XmlAttribute
	public String getId() {
		return id;
	}
	
	@XmlAttribute
	public String getName() {
		return name;
	}

	@XmlAttribute
	public String getDate() {
		return date;
	}

	@XmlAttribute
	public String getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}
	
	public String getAttendant() {
		return attendant;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAttendant(String attendant) {
		this.attendant = attendant;
	}
	
	@Override
	public String toString(){
	  String print = "";
	  print += "#1 Task ID: " + id + "\n";
	  print += "#2 Task name: " + name + "\n";
	  print += "#3 Task date: " + date + "\n";
	  print += "#4 Task status: " + status + "\n";
	  print += "#5 Task description: " + description + "\n";
	  print += "#6 Task attendant: " + attendant;
	  return print;
	}
}
