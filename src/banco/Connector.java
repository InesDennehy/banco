package banco;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.StringTokenizer;
import java.util.Vector;

public class Connector {
	
	private Connection connection;
	private static Connector connector = new Connector();
	
	private Connector() {}
	
	public static Connector getConnection() {
		return connector;
	}
	
	public LoginInfo AdminLogin(LoginWindow w, String user, String password) {
		LoginInfo loginInfo = null;
		if(user.equals("admin") && password.equals("admin")) {
			try{
				loginInfo = new LoginInfo();
				loginInfo.setStatus("admin");
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
	            loginInfo = null;
	         }
			return loginInfo;
		}
		
		return loginInfo;
	}
	public LoginInfo StaffLogin(LoginWindow w, String user, String password) {
		LoginInfo loginInfo = null;
		try {
			loginInfo = new LoginInfo();
			loginInfo.setStatus("empleado");
			String driver ="com.mysql.cj.jdbc.Driver";
        	String server = "localhost:3306";
            String database = "banco";
            String uriConnection = "jdbc:mysql://" + server + "/" + database +"?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
            connection = DriverManager.getConnection(uriConnection, "empleado", "empleado");
			Statement stmt = connection.createStatement();
			
	        ResultSet rs = stmt.executeQuery("select legajo from empleado where legajo="+user+" and password=md5('"+password+"');");
	        if(rs != null && rs.next()) {
				loginInfo.setNumber(Integer.parseInt(user));
	        }
	        else loginInfo = null;
		}
         catch (SQLException ex){
            loginInfo = null;
         }
		catch(NumberFormatException ex) {
			loginInfo = null;
		}
		return loginInfo;
	}
	public LoginInfo ATMLogin(LoginWindow w, String user, String password) {
		LoginInfo loginInfo = null;
		try{
		    int i = Integer.parseInt(password);  
        	loginInfo = new LoginInfo();
			loginInfo.setStatus("atm");
			String driver ="com.mysql.cj.jdbc.Driver";
        	String server = "localhost:3306";
            String database = "banco";
            String uriConnection = "jdbc:mysql://" + server + "/" + database +"?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
            connection = DriverManager.getConnection(uriConnection, "atm", "atm");
			Statement stmt = connection.createStatement();
            stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery("select nro_tarjeta from Tarjeta where "+user+" = nro_tarjeta and md5("+password+") = pin;");
	        if(rs != null && rs.next()) {
				loginInfo.setNumber(Integer.parseInt(user));
	        }
	        else loginInfo = null;
		}
		catch (SQLException ex){
            loginInfo = null;
         }
		catch(NumberFormatException ex) {
			loginInfo = null;
		}
		return loginInfo;
	}
	
	public DefaultTableModel getQuery(String query) throws SQLException{
		
		java.sql.Statement stmt = null;
		ResultSet rs = null;
    	StringTokenizer st = new StringTokenizer(query);
    	String sentencia = st.nextToken();
    	if(sentencia.toLowerCase().equals("insert") || sentencia.toLowerCase().equals("update")) {
	    	stmt = connection.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
    	}
    	else if(sentencia.toLowerCase().equals("select")) {
	        stmt = connection.createStatement();
	        rs = stmt.executeQuery(query);
			return this.buildTableModel(rs);
    	}
    	else {
    		stmt = connection.createStatement();
			stmt.execute(query);
			stmt.close();
    	}
    	return null;
	}
	
	public String doTransaction(String transaction, String query) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
    	try {
	        stmt = connection.createStatement();
			stmt.execute(transaction);
			rs = stmt.executeQuery(query);
			String s = null;
			if(rs!=null && rs.next()) {
				s =  rs.getString(1);
			}
			stmt.close();
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}
	
	public boolean disconnect(JFrame w) {
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

	public Double getSaldoTarjeta(long tarjeta) {
		// TODO Auto-generated method stub
		java.sql.Statement stmt = null;
	    String query = "select saldo from tarjeta natural join trans_cajas_ahorro where nro_tarjeta = "+Long.toString(tarjeta);
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        if (rs != null && rs.next()) {
	        	return rs.getDouble(1);
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
		return 0.00;
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

	public DefaultTableModel getATMQuery(int nroCuenta, String query) {
		// TODO Auto-generated method stub
		java.sql.Statement stmt = null;
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        return buildATMTableModel(rs);
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
	
	public DefaultTableModel buildATMTableModel(ResultSet rs) throws SQLException {

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
		        	if(metaData.getColumnType(columnIndex) == Types.DATE)
		        		vector.add(cambiarFecha(rs.getString(columnIndex)));
		        	else if(metaData.getColumnType(columnIndex) == Types.DECIMAL && (
		        		rs.getString(columnIndex-1).toLowerCase().equals("debito") || 
		        		rs.getString(columnIndex-1).toLowerCase().equals("extraccion") || 
		        		rs.getString(columnIndex-1).toLowerCase().equals("transferencia")))
		        		
		        			vector.add("-"+rs.getString(columnIndex));
	
		        	else
		        		vector.add(rs.getString(columnIndex));
		        }
		        data.add(vector);
		    }
		    DefaultTableModel dtm = new DefaultTableModel(data, columnNames) {
		    	@Override
		    	public boolean isCellEditable(int arg0, int arg1) {
		    		return false;
		    	}
		    };
		    return dtm;
	}
	
	public DefaultTableModel buildTableModel(ResultSet rs)
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
	        	if(metaData.getColumnType(columnIndex) == Types.DATE)
	        		vector.add(cambiarFecha(rs.getString(columnIndex)));
	        	else
	        		vector.add(rs.getString(columnIndex));
	        }
	        data.add(vector);
	    }

	    DefaultTableModel dtm = new DefaultTableModel(data, columnNames) {
	    	@Override
	    	public boolean isCellEditable(int arg0, int arg1) {
	    		return false;
	    	}
	    };
	    return dtm;
	}
	
	private String cambiarFecha(String old) {
		return (Character.toString(old.charAt(8))+Character.toString(old.charAt(9))+"/"+
				Character.toString(old.charAt(5))+Character.toString(old.charAt(6))+"/"+
				Character.toString(old.charAt(0))+Character.toString(old.charAt(1))+Character.toString(old.charAt(2))+Character.toString(old.charAt(3)));
	}

	public boolean noTienePrestamo(int nroCliente) {
		// TODO Auto-generated method stub
		java.sql.Statement stmt = null;
		ResultSet rs = null;
        try {
        	stmt = connection.createStatement();
			rs = stmt.executeQuery("select nro_prestamo from prestamo natural join cliente natural join pago where "
					+ "'"+nroCliente+"' = cliente.nro_cliente and pago.fecha_pago is NULL;");
			if(rs != null && rs.next()) {
				return false;
			}
			else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
	}
	
	public int getNroPrestamo(int nroCliente) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
        try {
        	stmt = connection.createStatement();
			rs = stmt.executeQuery("select distinct nro_prestamo from pago natural join prestamo natural join cliente where "
					+ ""+nroCliente+" = cliente.nro_cliente and pago.fecha_pago is NULL;");
			if(rs != null && rs.next())
				return rs.getInt(1);
			else
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}
	private int getPrestamo(int nroCliente) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
        try {
        	stmt = connection.createStatement();
			rs = stmt.executeQuery("select max(nro_prestamo) from prestamo natural join cliente where "
					+ ""+nroCliente+" = cliente.nro_cliente and prestamo.fecha = curdate() group by nro_cliente;");
			if(rs != null && rs.next())
				return rs.getInt(1);
			else
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}
	
	public int getNroCliente(String tipoDoc, String nroDoc) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
        try {
        	stmt = connection.createStatement();
			rs = stmt.executeQuery("(select nro_cliente from cliente where "
					+ "'"+tipoDoc+"' = tipo_doc and '"+nroDoc+"' "
					+ " = nro_doc)");
			if(rs != null && rs.next()) {
				int i = rs.getInt(1);
				return i;
			}
			else
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}
	
	public double getInteresPrestamo(double monto, int cantMeses) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
        try {
        	stmt = connection.createStatement();
			rs = stmt.executeQuery("select tasa from tasa_prestamo where "
					+ "periodo = "+cantMeses+" and '"+Double.toString(monto)+"' > monto_inf and '"+Double.toString(monto)+"' < monto_sup;");
			if(rs != null && rs.next())
				return rs.getInt(1);
			else
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}
	
	public Vector<Integer> getMesesPosibles() {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		Vector<Integer> ret = new Vector<Integer>();
        try {
        	stmt = connection.createStatement();
			rs = stmt.executeQuery("select distinct periodo from tasa_prestamo;");
			while(rs != null && rs.next())
				ret.add(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
	}

	public void insertarPrestamo(int nroCliente, double monto, Integer cuotas, int legajo) {
		// TODO Auto-generated method stub
		java.sql.Statement stmt = null;
		double tasa = getInteresPrestamo(monto, cuotas);
		double interes = (monto*tasa*cuotas)/1200;
        try {
        	stmt = connection.createStatement();
			stmt.execute("INSERT INTO Prestamo(nro_cliente,legajo,fecha,cant_meses,monto,tasa_interes,interes,valor_cuota) VALUES "
					+ "("+Integer.toString(nroCliente)+", "+Integer.toString(legajo)+", curdate(), "+Integer.toString(cuotas)+", "
					+ "'"+Double.toString(monto)+"', '"+Double.toString(tasa)+"', "+ Double.toString(interes)+", "
					+ ""+Double.toString((monto+interes)/cuotas)+");");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getMaxMonto() {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
        try {
        	stmt = connection.createStatement();
			rs = stmt.executeQuery("select max(monto_sup) from tasa_prestamo;");
			if(rs != null && rs.next())
				return rs.getInt(1);
			else
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}

}
