package banco;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import quick.dbtable.DBTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Connector {
	
	private Connection connection;
	private static Connector connector = new Connector();
	
	private Connector() {}
	
	public static Connector getConnection() {
		return connector;
	}
	
	public LoginInfo login(LoginWindow w, String user, String password) {
		LoginInfo loginInfo = null;
		if(user.equals("admin") && password.equals("admin")) {
			try{
				loginInfo = new LoginInfo();
				loginInfo.setStatus("admin");
				loginInfo.setTable(new DBTable());
				String driver ="com.mysql.cj.jdbc.Driver";
	        	String server = "localhost:3306";
	            String database = "banco";
	            String uriConnection = "jdbc:mysql://" + server + "/" + database +"?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	            connection = DriverManager.getConnection(uriConnection, user, password);
				loginInfo.getTable().connectDatabase(driver, uriConnection, user, password);
	         }
	         catch (SQLException ex){
	            JOptionPane.showMessageDialog(w,
	                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
	                                           "Error",
	                                           JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	            loginInfo = null;
	         } catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return loginInfo;
		}
		else {
			try{
				loginInfo = new LoginInfo();
				loginInfo.setStatus("empleado");
				loginInfo.setTable(new DBTable());
				String driver ="com.mysql.cj.jdbc.Driver";
	        	String server = "localhost:3306";
	            String database = "banco";
	            String uriConnection = "jdbc:mysql://" + server + "/" + database +"?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	            connection = DriverManager.getConnection(uriConnection, "empleado", "empleado");
				Statement stmt = connection.createStatement();
		        ResultSet rs = stmt.executeQuery("select legajo from empleado where "+user+" = legajo and md5("+password+") = password;");
		        if(rs != null && rs.next()) {
					loginInfo.setTable(new DBTable());
					loginInfo.setNumber(Integer.parseInt(user));
		        }
		        else {
		        	loginInfo = new LoginInfo();
					loginInfo.setStatus("atm");
					loginInfo.setTable(new DBTable());
		            connection = DriverManager.getConnection(uriConnection, "atm", "atm");
		            stmt = connection.createStatement();
			        rs = stmt.executeQuery("select nro_tarjeta from Tarjeta where "+user+" = nro_tarjeta and md5("+password+") = pin;");
			        if(rs != null && rs.next()) {
						loginInfo.setTable(new DBTable());
						loginInfo.setNumber(Integer.parseInt(user));
			        }
			        else loginInfo = null;
		        }
	         }
	         catch (SQLException ex){
	            JOptionPane.showMessageDialog(w,
	                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
	                                           "Error",
	                                           JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	            loginInfo = null;
	         }
			return loginInfo;
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
	
	@SuppressWarnings("finally")
	public DefaultListModel<String> getTables() {
		java.sql.Statement stmt = null;
	    String query = "SHOW TABLES;";
	    DefaultListModel<String> list = new DefaultListModel<String>();
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	list.addElement(rs.getString(1));
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	        return list;
	    }
	}
	
	@SuppressWarnings("finally")
	public DefaultListModel<String> getAttributes(String table){
		java.sql.Statement stmt = null;
	    String query = "DESCRIBE "+table+";";
	    DefaultListModel<String> list = new DefaultListModel<String>();
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	list.addElement(rs.getString(1));
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	        return list;
	    }
	}

	public int getSaldoTarjeta(long tarjeta) {
		// TODO Auto-generated method stub
		java.sql.Statement stmt = null;
	    String query = "select saldo from tarjeta natural join trans_cajas_ahorro where nro_tarjeta = "+Long.toString(tarjeta);
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        if (rs != null && rs.next()) {
	        	return rs.getInt(1);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	    }
		return 0;
	}

	public int getCuenta(long tarjeta) {
		// TODO Auto-generated method stub
		java.sql.Statement stmt = null;
	    String query = "select nro_ca from tarjeta natural join trans_cajas_ahorro where nro_tarjeta = "+Long.toString(tarjeta);
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        if (rs != null && rs.next()) {
	        	return rs.getInt(1);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	    }
		return 0;
	}

	public JTable getUltimasTransacciones(int nroCuenta) {
		// TODO Auto-generated method stub
		java.sql.Statement stmt = null;
	    String query = "select nro_trans, fecha, hora, tipo, monto from trans_cajas_ahorro where nro_ca = "+Integer.toString(nroCuenta);
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        JTable tabla = new JTable(buildTableModel(rs));
	        return tabla;
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	    }
		return null;
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	        	vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
}
