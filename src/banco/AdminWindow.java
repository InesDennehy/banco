package banco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import quick.dbtable.DBTable;

public class AdminWindow extends JDialog {

	private JTextArea txtConsulta;
	private CustomButton botonBorrar;
	private CustomButton btnEjecutar;
	private CustomButton btnInfoTables;
	private DBTable tabla;    
	private JScrollPane scrConsulta;

	/**
	 * Create the frame.
	 */
	public AdminWindow(DBTable t) {
		this.setResizable(false);
		setPreferredSize(new Dimension(800, 600));
        this.setBounds(0, 0, 800, 600);
        setVisible(true);
        this.setTitle("BD Bank - Admin");
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(232, 236, 242));
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 600;
        c.ipady = 200;
        c.gridheight = 3;
        c.fill = GridBagConstraints.BOTH;
        
        scrConsulta = new JScrollPane();
        txtConsulta = new JTextArea();
        scrConsulta.setViewportView(txtConsulta);
        txtConsulta.setTabSize(3);
        txtConsulta.setColumns(30);
        txtConsulta.setText("SELECT legajo FROM Empleado;");
	 	txtConsulta.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        txtConsulta.setFont(LoginWindow.font);
        txtConsulta.setRows(8); 
        
        getContentPane().add(scrConsulta, c);
		
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 80;
        c.weighty = 0.5;
        c.gridheight = 1;
        c.insets = new Insets(0,0,0,0);
        btnEjecutar = new CustomButton("EJECUTAR", 130, 45, "medium");
        btnEjecutar.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				AdminWindow.this.refreshTable();
			}
	        });
        getContentPane().add(btnEjecutar, c);
        
        c.gridy = 1;
     	botonBorrar = new CustomButton("BORRAR", 130, 45, "medium");
     	botonBorrar.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				txtConsulta.setText("");   
			}
	        });
     	getContentPane().add(botonBorrar,c);
     	
     	c.gridy = 2;
     	btnInfoTables = new CustomButton("INFO TABLAS", 130, 45, "medium");
     	btnInfoTables.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				InfoTablesWindow frame = new InfoTablesWindow();
				frame.setVisible(true);
			}
	        });
     	getContentPane().add(btnInfoTables,c);
     	
	 	tabla = t;
	 	tabla.setBackground(new Color(247, 247, 247));
	 	c.gridx = 0;
	 	c.gridy = 3;
	 	c.ipadx = 765;
	 	c.ipady = 400;
	 	c.gridwidth = 2;
	 	
	 	// Agrega la tabla al frame (no necesita JScrollPane como Jtable)
	     getContentPane().add(tabla, c);
	     
	    // setea la tabla para sólo lectura (no se puede editar su contenido)  
	    tabla.setEditable(false);
	}

	private void refreshTable() {
		try{
			// seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
			tabla.setSelectSql(this.txtConsulta.getText().trim());

			// obtenemos el modelo de la tabla a partir de la consulta para 
			// modificar la forma en que se muestran de algunas columnas  
			tabla.createColumnModelFromQuery();
			for (int i = 0; i < tabla.getColumnCount(); i++){ // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
				if (tabla.getColumn(i).getType()==Types.TIME){    		 
					tabla.getColumn(i).setType(Types.CHAR);  
				}
	    		// cambiar el formato en que se muestran los valores de tipo DATE
	    		if (tabla.getColumn(i).getType()==Types.DATE){
	    			tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
	    		}
	        }  
			// actualizamos el contenido de la tabla.   	     	  
	    	tabla.refresh();
	    	// No es necesario establecer  una conexión, crear una sentencia y recuperar el 
	    	// resultado en un resultSet, esto lo hace automáticamente la tabla (DBTable) a 
	    	// patir de la conexión y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.	  
		    }
		    catch (SQLException ex) {
		    	// en caso de error, se muestra la causa en la consola
		    	System.out.println("SQLException: " + ex.getMessage());
		    	System.out.println("SQLState: " + ex.getSQLState());
		    	System.out.println("VendorError: " + ex.getErrorCode());
		    	JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
		                                      ex.getMessage() + "\n", 
		                                      "Error al ejecutar la consulta.",
		                                      JOptionPane.ERROR_MESSAGE);
		         
		    }
	}
}
