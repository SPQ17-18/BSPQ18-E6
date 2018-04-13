package deusto.bspq18.e6.DeustoBox.Server.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class BDConnection {

	private Connection conexion;
	private Statement stat;
	private static Data data = new Data();
	private static String host = data.getProperties("resources/DeustoBox.properties", "host");
	private static String BD_name = data.getProperties("resources/DeustoBox.properties", "name");
	private static String user = data.getProperties("resources/DeustoBox.properties", "user");
	private static String pass = data.getProperties("resources/DeustoBox.properties", "password");

	public BDConnection(Connection conexion, Statement stat) {
		this.conexion = conexion;
		this.stat = stat;
	}

	public static BDConnection getBD(boolean aExterna) {
		Connection con = initBD(aExterna);
		Statement stat = useBD(con);
		return new BDConnection(con, stat);
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public Statement getStat() {
		return stat;
	}

	public void setStat(Statement stat) {
		this.stat = stat;
	}

	public static Connection initBD(boolean aExterna) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String servidor = null;
			if(aExterna) {
				servidor = "jdbc:mysql://" + host + "/" + BD_name;
			}else {
				Class.forName("org.sqlite.JDBC");
				servidor = "jdbc:sqlite:DeustoBox.db";
			}
			return DriverManager.getConnection(servidor, user, pass);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexi�n " + e);
			;
			return null;
		}
	}

	public static Statement useBD(Connection con) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);
			return statement;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Statement createTable() {
		try {
			stat = conexion.createStatement();
			stat.setQueryTimeout(30);
			try {
				stat.executeUpdate("create table user " +
					"(email text primary key, "
					+ "username text not null, "
					+ "password text not null)");
			} catch (SQLException e) {} // If it exits, nothing to do		
			return stat;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public boolean registerUser(Statement st, String email, String name, String password) {
		String sentSQL = "";
		try {
			sentSQL = "insert into user (email, username, password) values(" + "'" + email + "'," + "'" + name + "',"
					+ "'" + password + "')";
			int val = st.executeUpdate(sentSQL);
			if (val != 1) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean userExits(String user, Statement st, Connection con) {

		try {
			ResultSet rs = st.executeQuery("select email, password from user");
			while (rs.next()) {
				String email = rs.getString("email");
				String password = rs.getString("password");
				String together = email + " " + password;

				if (user.equals(together)) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public ArrayList<String> getUsers() {
		ArrayList<String> users = new ArrayList<String>();
		try {
			ResultSet rs = stat.executeQuery("select * from user");
			while (rs.next()) {
				//String username = rs.getString("username");
				//String password = rs.getString("password");
				String email = rs.getString("email");
				users.add(email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
