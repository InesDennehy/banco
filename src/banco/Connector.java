package banco;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	
	private Connection connection;
	private static Connector connector = new Connector();
	
	private Connector() {}
	
	public static Connector getConnection() {
		return connector;
	}
	
	public boolean adminLogin(LoginWindow w, String user, String password) {
		if(user.equals("admin") && password.equals("admin")) {
			try{
				String driver ="com.mysql.cj.jdbc.Driver";
	        	String server = "localhost:3306";
	            String database = "banco";
	            String uriConnection = "jdbc:mysql://" + server + "/" + database +"?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	            connection = DriverManager.getConnection(uriConnection, user, password);
	         }
	         catch (SQLException ex){
	            JOptionPane.showMessageDialog(w,
	                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
	                                           "Error",
	                                           JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean disconnect(LoginWindow w) {
		try {
			if(connection != null)
				connection.close();
				connection = null;
				return true;
		}
		catch (SQLException ex){
            JOptionPane.showMessageDialog(w,
                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
                                           "Error",
                                           JOptionPane.ERROR_MESSAGE);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
		return false;
	}
}
