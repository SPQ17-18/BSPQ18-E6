package deusto.bspq18.e6.DeustoBox.Server.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public static BDConnection getBD() {
		Connection con = initBD();
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

	public static Connection initBD() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String servidor = "jdbc:mysql://" + host + "/" + BD_name;
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
					"(id_user integer primary key autoincrement,"
					+ "username text not null, "
					+ "password text not null, "
					+ "email text not null)");
			} catch (SQLException e) {} // If it exits, nothing to do		
			return stat;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public boolean registerUser(Statement st, String name, String password, String email) {
		String sentSQL = "";
		try {
			sentSQL = "insert into user (username, password, email) values(" + "'" + name + "'," + "'" + password + "',"
					+ "'" + email + "')";
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
			ResultSet rs = st.executeQuery("select username, password from user");
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String together = username + " " + password;

				if (user.equals(together)) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
