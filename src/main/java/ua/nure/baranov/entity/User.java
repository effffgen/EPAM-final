package ua.nure.baranov.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User extends Entity implements Serializable {

	private static final long serialVersionUID = -7041579973128009753L;

	private User() {

	}

	private String username;
	private String password;
	private Role role;
	private String email;
	private Calendar creationDate;
	private String firstName;
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	public Date getCreationTime() {
		return creationDate.getTime();
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", email="
				+ email + ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}

	public static UserBuilder builder() {
		return new User().new UserBuilder();
	}

	public class UserBuilder {
		private UserBuilder() {
		}

		public User build() {
			return User.this;
		}

		public UserBuilder setId(Integer id) {
			User.this.id = id;
			return this;
		}

		public UserBuilder setFirstName(String firstName) {
			User.this.firstName = firstName;
			return this;
		}

		public UserBuilder setLastName(String lastName) {
			User.this.lastName = lastName;
			return this;
		}

		public UserBuilder setPassword(String password) {
			User.this.password = password;
			return this;
		}

		public UserBuilder setRole(Role role) {
			User.this.role = role;
			return this;
		}

		public UserBuilder setUsername(String username) {
			User.this.username = username;
			return this;
		}

		public UserBuilder setEmail(String email) {
			User.this.email = email;
			return this;
		}

		public UserBuilder setCreationDate(Calendar creationDate) {
			User.this.creationDate = creationDate;
			return this;
		}
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
