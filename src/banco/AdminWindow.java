package banco;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

	private JPanel contentPane;
	private JPanel pnlConsulta;
	private JTextArea txtConsulta;
	private JButton botonBorrar;
	private JButton btnEjecutar;
	private DBTable tabla;    
	private JScrollPane scrConsulta;

	/**
	 * Create the frame.
	 */
	public AdminWindow() {
		setPreferredSize(new Dimension(800, 600));
        this.setBounds(0, 0, 800, 600);
        setVisible(true);
        BorderLayout thisLayout = new BorderLayout();
        this.setTitle("Consultas (Utilizando DBTable)");
        getContentPane().setLayout(thisLayout);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        
        pnlConsulta = new JPanel();
        getContentPane().add(pnlConsulta, BorderLayout.NORTH);
        scrConsulta = new JScrollPane();
        pnlConsulta.add(scrConsulta);
        txtConsulta = new JTextArea();
        scrConsulta.setViewportView(txtConsulta);
        txtConsulta.setTabSize(3);
        txtConsulta.setColumns(80);
        txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
        txtConsulta.setText("SELECT legajo FROM Empleados;");
        txtConsulta.setFont(new java.awt.Font("Monospaced",0,12));
        txtConsulta.setRows(10); 
		
        btnEjecutar = new JButton();
        pnlConsulta.add(btnEjecutar);
        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              AdminWindow.this.refreshTable();
           }
        });
     	botonBorrar = new JButton();
     	pnlConsulta.add(botonBorrar);
     	botonBorrar.setText("Borrar");            
     	botonBorrar.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent arg0) {
     		  txtConsulta.setText("");            			
     		}
     	});
	  
	  
	 	// crea la tabla  
	 	tabla = new DBTable();
	 	
	 	// Agrega la tabla al frame (no necesita JScrollPane como Jtable)
	     getContentPane().add(tabla, BorderLayout.CENTER);           
	               
	    // setea la tabla para sólo lectura (no se puede editar su contenido)  
	    tabla.setEditable(false);

	}

	private void refreshTable() {
		try{    
    	  // seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
    	  tabla.setSelectSql(this.txtConsulta.getText().trim());

    	  // obtenemos el modelo de la tabla a partir de la consulta para 
    	  // modificar la forma en que se muestran de algunas columnas  
    	  //tabla.createColumnModelFromQuery();    	    
    	  for (int i = 0; i < tabla.getColumnCount(); i++){ // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
    		 if	 (tabla.getColumn(i).getType()==Types.TIME){    		 
    		  tabla.getColumn(i).setType(Types.CHAR);  
  	       	 }
    		 // cambiar el formato en que se muestran los valores de tipo DATE
    		 if	 (tabla.getColumn(i).getType()==Types.DATE){
    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
    		 }
          }  
    	  // actualizamos el contenido de la tabla.   	     	  
    	  tabla.refresh();
    	  // No es necesario establecer  una conexión, crear una sentencia y recuperar el 
    	  // resultado en un resultSet, esto lo hace automáticamente la tabla (DBTable) a 
    	  // patir de la conexión y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.
          
    	  
	    	  
	      }
	      catch (SQLException ex){
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
