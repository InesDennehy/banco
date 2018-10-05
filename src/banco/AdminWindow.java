package banco;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class AdminWindow extends JFrame {

	private JTextArea txtConsulta;
	private CustomButton botonBorrar;
	private CustomButton btnEjecutar;
	private CustomButton btnInfoTables;
	private JTable tabla;    
	private JScrollPane scrConsulta;

	/**
	 * Create the frame.
	 */
	public AdminWindow() {
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
     	
     	JScrollPane sp = new JScrollPane();
	 	tabla = new JTable();
	 	tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if( !isSelected ) {
			         Color c = table.getBackground();
			         if( (row%2)==0 &&
			                 c.getRed()>10 && c.getGreen()>10 && c.getBlue()>10 )
			            setBackground(new Color( c.getRed()-10,
			                                     c.getGreen()-10,
			                                     c.getBlue()-10));
			         else
			            setBackground(c);
			      }
			      return super.getTableCellRendererComponent( table, value,isSelected,hasFocus,row,column);
		   }
			
		});
	 	sp.add(tabla);
	 	c.gridx = 0;
	 	c.gridy = 3;
	 	c.ipadx = 765;
	 	c.ipady = 400;
	 	c.gridwidth = 2;
	 	
	    getContentPane().add(new JScrollPane(tabla), c);
	}

	private void refreshTable() {
		try{
			DefaultTableModel tm = Connector.getConnection().getQuery(txtConsulta.getText());
			if(tm != null) {
				tabla.setModel(tm);
			}
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
