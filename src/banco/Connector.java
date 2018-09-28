package banco;

import javax.swing.JOptionPane;

import quick.dbtable.DBTable;

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
	
	public DBTable adminLogin(LoginWindow w, String user, String password) {
		
		if(user.equals("admin") && password.equals("admin")) {
			DBTable tabla = null;
			try{
				tabla = new DBTable();
				String driver ="com.mysql.cj.jdbc.Driver";
	        	String server = "localhost:3306";
	            String database = "banco";
	            String uriConnection = "jdbc:mysql://" + server + "/" + database +"?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	            connection = DriverManager.getConnection(uriConnection, user, password);
				tabla.connectDatabase(driver, uriConnection, user, password);
	         }
	         catch (SQLException ex){
	            JOptionPane.showMessageDialog(w,
	                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
	                                           "Error",
	                                           JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	            tabla = null;
	         } catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return tabla;
		}
		else {
			return null;
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
