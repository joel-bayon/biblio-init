package users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	String userName;
	String password;
	List<String> roles = new ArrayList<String>();
	
	static Map<String,User> users = new HashMap<String, User>();
	
	static {
		User u= new User("durand", "paul", "user");
		users.put(u.getUserName()+u.getPassword(), u);
		u= new User("martin", "jean", "admin");
		users.put(u.getUserName()+u.getPassword(), u);
		u= new User("dupont", "george", "user");
		users.put(u.getUserName()+u.getPassword(), u);
		u= new User("a", "1", "admin");
		users.put(u.getUserName()+u.getPassword(), u);
	}
	
	public User(String userName, String password, String role) {
		this.userName = userName;
		this.password = password;
		roles.add(role);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public static Map<String, User> getUsers() {
		return users;
	}
	public static void setUsers(Map<String, User> users) {
		User.users = users;
	}
}
