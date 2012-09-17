package handinone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "cal")
@XmlType(propOrder={"users", "tasks"})
public class Calendar {
  private static HashMap<Integer, User> users = new HashMap<>();
  private static HashMap<Integer, HashMap<Integer, Task>> tasks = new HashMap<>();
	
	public Calendar() {}
	
	public Calendar(ArrayList<User> userlist, ArrayList<Task> tasklist) {
	  for (User user : userlist) {
	    this.addUser(user);
	  }
		for (Task task : tasklist) {
		  if (!tasks.containsKey(task.getAttendantid())) throw new IllegalArgumentException("Orphaned task found");
		  tasks.get(task.getAttendantid()).put(task.getId(), task);
		}
	}
	
	public static User getUser(int id) {
	  return users.get(id);
	}
	
	public void addUser(User user) {
		users.put(user.getId(), user);
		tasks.put(user.getId(), new HashMap<Integer, Task>());
	}
	
	public void removeTask(int id){
	  for(HashMap<Integer, Task> tsks : tasks.values()){
	    if(tsks.containsKey(id)){
	      tsks.remove(id);
	      return;
	    }
	  }
	}
	
	public void removeUser(String userid) {
		users.remove(userid);
	}
	
	@XmlElementWrapper(name = "users")
	@XmlElement(name = "user")
	public ArrayList<User> getUsers() {
	  ArrayList<User> usersReturn = new ArrayList<>();
	  for (Map.Entry<Integer, User> user : users.entrySet()) {
	    usersReturn.add(user.getValue());
	  }
		return usersReturn;
	}

	public void setUsers(ArrayList<User> userlist) {
	  users.clear();
	  tasks.clear();
		for (User user : userlist) {
		  this.addUser(user);
		}
	}

	public void addTask(Task task) {
		tasks.get(task.getAttendantid()).put(task.getId(), task);
	}
	
	public void removeTask(Task task) {
		tasks.get(task.getAttendantid()).remove(task);
	}
	
	@XmlElementWrapper(name = "tasks")
	@XmlElement(name = "task")
	public ArrayList<Task> getTasks() {
	  ArrayList<Task> tasksReturn = new ArrayList<>();
	  for (Map.Entry<Integer, HashMap<Integer, Task>> taskslist : tasks.entrySet()) {
	    tasksReturn.addAll(taskslist.getValue().values());
	  }
		return tasksReturn;
	}


	public void setTasks(HashSet<Task> tasklist) {
		for (Task task : tasklist) {
		  if (tasks.containsKey(task.getAttendantid())) tasks.get(task.getAttendantid()).put(task.getId(), task);
		  else throw new IllegalArgumentException("No such user");
		}
	}
	
	public ArrayList<Task> getListOfTasks(int userid) {
	  ArrayList<Task> tasksReturn = new ArrayList<Task>();
	  tasksReturn.addAll(tasks.get(userid).values());
	  return tasksReturn;
	}
	
  public static void generateEmptyCalendar(File calendarfile) throws IOException {
    calendarfile.createNewFile();
    Calendar c = new Calendar();
    ObjectMarshaller.marshall(c, new FileOutputStream(calendarfile));
  }
  
  public static Calendar loadCalendar(File calendarfile) throws JAXBException, IOException {
    if (!calendarfile.exists()) Calendar.generateEmptyCalendar(calendarfile);
     return (Calendar) ObjectMarshaller.getUnmarshaller(new Calendar()).unmarshal(calendarfile);
  }
  
  public static void saveCalendar(Calendar calendar, File calendarfile) throws FileNotFoundException {
    ObjectMarshaller.marshall(calendar, new FileOutputStream(calendarfile));
  }

	public static void main (String[] args) {
		Calendar cal = new Calendar();
		
		User user1 = new User("Ole Andersen", "pw123");
		User user2 = new User("Lise Jensen", "bieberrox");
		Task task1 = new Task(42, "Cool Task", "12-12-12", "not done", "This is a cool task", user1.getId());
		Task task2 = new Task(2, "OP: dbag", "04-11-91", "completed", "noob around", user2.getId());
		
	    cal.addUser(user1);
	    cal.addUser(user2);
		cal.addTask(task1);
		cal.addTask(task2);
		
		//marshall
		ObjectMarshaller.marshall(cal, "./bossen.xml");
		System.out.println("Marshall succesfull");
		//unmarshall
		Calendar cal2 = new Calendar();
	    try {
	      cal2 = (Calendar) ObjectMarshaller.getUnmarshaller(cal2).unmarshal(new File("./bossen.xml"));
	  		for (Task task : cal2.getTasks()) {
	  		  System.out.println(task);
	  		}
	  		System.out.println("Unmarshall succesfull");
	    } catch (JAXBException e) {
	      e.printStackTrace();
	    }
	}
}
