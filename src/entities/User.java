package entities;

/**
 * 
 * @author Daniel RADEAU
 *
 */

public class User {

	private Integer id;
	private String email;
	private String password;
	private String firstname;
	private String name;
	private String phone;
	
	public User(Integer id, String email, String password, String name, String firstname, String phone) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.name = name;
		this.phone = phone;
	}
	
	public User(String email, String password, String name, String firstname, String phone) {
		this.id = null;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.name = name;
		this.phone = phone;
	}	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
