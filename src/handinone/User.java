package handinone;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "user")
@XmlType(propOrder={"id", "name", "password"})
public class User {
  private static int nextId = 0;
	
	private String name, password;
	private int userId;
	
	public User() {}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.userId = ++nextId;
	}
	
	@XmlAttribute
	public int getId() {
	  return userId;
	}
	
	public void setId(int id) {
	  this.userId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
