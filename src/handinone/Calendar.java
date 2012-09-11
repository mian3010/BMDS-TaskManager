package handinone;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "cal")
@XmlType(propOrder={"users", "tasks"})
public class Calendar {
	
	private ArrayList<User> users;
	private ArrayList<Task> tasks;
	
	public Calendar() {
		users = new ArrayList<User>();
		tasks = new ArrayList<Task>();
	}
	
	public Calendar(ArrayList<User> users, ArrayList<Task> tasks) {
		this.users = users;
		this.tasks = tasks;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	
	@XmlElementWrapper(name = "users")
	@XmlElement(name = "user")
	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void removeTask(Task task) {
		tasks.remove(task);
	}
	
	@XmlElementWrapper(name = "tasks")
	@XmlElement(name = "task")
	public ArrayList<Task> getTasks() {
		return tasks;
	}


	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public static void main (String[] args) {
		Calendar cal = new Calendar();
		cal.addTask(new Task("42", "Cool Task", "12-12-12", "not done", "This is a cool task", "gunit"));
		cal.addTask(new Task("2", "OP: dbag", "04-11-91", "completed", "noob around", "sidi"));
		
		User user = new User();
		user.setName("Ole Andersen");
		cal.addUser(new User("Ole Andersen", "pw123"));
		cal.addUser(new User("Lise Jensen", "bieberrox"));
		
		//marshall
		CalendarMarshaller.marshall(cal, "./bossen.xml");
		System.out.println("Marshall succesfull");
		//unmarshall
		try {
			Calendar cal2 = (Calendar) CalendarMarshaller.getUnmarshaller(cal).unmarshal(new File("./bossen.xml"));
			String idCheck = cal2.getTasks().get(0).getId(); //Okay, maybe we should use an ordered list instead of a set :)
			if (idCheck.equals("42")) { 
				System.out.println("Unmarshall succesfull");
			}
		} catch (Exception ex) {
			System.out.println("Unmarshall failed");
		}
	}
	

	
}
