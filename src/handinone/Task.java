package handinone;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "task")
@XmlType(propOrder={"id", "attendantid", "name", "date", "status", "description"})
public class Task {

  private int id, attendantid;
	private String name, date, status, description;
	
	public Task() {}
	
	public Task(int id, String name, String date, String status, String description,int attendantid) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.status = status;
		this.description = description;
		this.attendantid = attendantid;
	}
	
	@XmlAttribute
	public int getId() {
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
	
	@XmlAttribute
	public int getAttendantid() {
		return attendantid;
	}
	
	public void setId(int id) {
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
	
	public void setAttendantid(int attendantid) {
		this.attendantid = attendantid;
	}
	
	@Override
	public String toString(){
	  String print = "";
	  print += "Task ID: " + id + "\n";
	  print += "Task name: " + name + "\n";
	  print += "Task date: " + date + "\n";
	  print += "Task status: " + status + "\n";
	  print += "Task description: " + description + "\n";
	  print += "Task attendant: " + attendantid;
	  return print;
	}
}
