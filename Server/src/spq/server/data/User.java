package spq.server.data;

import spq.server.data.PersistenceCapable;
import spq.server.data.PrimaryKey;

	@PersistenceCapable
	public class User {

		@PrimaryKey
		private String email;
		private String name;
		private String password;
		// Faltan por implementar las reservations

		
		public User(String email, String name, String password) {
			super();
			this.email = email;
			this.name = name;
			this.password = password;
		}
		

		public User() {
			this.email = " ";
			this.name= " ";
			this.password = " ";

		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
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


		@Override
		public String toString() {
			return "User [email=" + email + ", name=" + name + ", password=" + password + "]";
		}
		
		

}
