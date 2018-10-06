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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class StaffWindow extends JFrame {
	
	private JTable morosos, prestamos, cuotas;
	
	public StaffWindow(int legajo) {
		this.setResizable(false);
		setPreferredSize(new Dimension(800, 600));
        this.setBounds(0, 0, 800, 600);
        setVisible(true);
        this.setTitle("BD Bank - Empleado");
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(232, 236, 242));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,5,0,5);
        
        JLabel labelMorosos = new JLabel("Clientes Morosos");
        labelMorosos.setFont(LoginWindow.font);
        getContentPane().add(labelMorosos, c);
        
        c.gridy = 1;
        c.ipadx = 450;
        c.ipady = 400;
        c.weightx = 0;
        c.gridheight = 4;
        morosos = new JTable();
		actualizarMorosos();
		morosos.setBorder(null);
		morosos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
		JScrollPane sp = new JScrollPane(morosos);
		sp.setBackground(new Color(232, 236, 242));
		getContentPane().add(sp, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        c.gridheight = 1;
        
        JLabel p = new JLabel("Prestamos a pagar");
        p.setFont(LoginWindow.font);
        getContentPane().add(p, c);
        
        c.gridy = 1;
        c.gridheight = 4;
        c.ipadx = 0;
        c.ipady = 400;
        prestamos = new JTable();
        prestamos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        actualizarPrestamos();
        ListSelectionModel listSelectionModel = prestamos.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
	        	actualizarCuotas();
			}
        });
        getContentPane().add(new JScrollPane(prestamos), c);
        
        c.gridx = 2;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        c.insets = new Insets(5,5,5,5);
        c.gridheight = 2;
        CustomButton np = new CustomButton("NUEVO PRESTAMO", 150, 30, "normal");
        getContentPane().add(np, c);
        np.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				NewLoanWindow w = new NewLoanWindow(legajo, StaffWindow.this);
				w.setVisible(true);
				w.setAlwaysOnTop(true);
			}
        });
        
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.gridheight = 1;
        c.insets = new Insets(0,0,0,0);
        JLabel cu = new JLabel("Cuotas a pagar");
        cu.setFont(LoginWindow.font);
        cu.setHorizontalAlignment(JLabel.CENTER);
        cu.setHorizontalTextPosition(JLabel.CENTER);
        getContentPane().add(cu, c);;
        
        c.gridy = 3;
        c.ipadx = 0;
        c.ipady = 400;
        cuotas = new JTable();
        c.insets = new Insets(5,5,5,5);
        cuotas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        getContentPane().add(new JScrollPane(cuotas), c);
        
        c.gridy = 4;
        c.ipadx = 0;
        c.ipady = 0;
        CustomButton pagar = new CustomButton("PAGAR CUOTAS", 150, 30, "normal");
        pagar.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(cuotas.getSelectedRowCount() > 0) {
					int selectedRow[] = cuotas.getSelectedRows();
					int nroCliente = Connector.getConnection().getNroCliente((String)prestamos.getValueAt(prestamos.getSelectedRow(), 0), (String)prestamos.getValueAt(prestamos.getSelectedRow(), 1));
					int nroCuenta = Connector.getConnection().getNroPrestamo(nroCliente);
		            for (int i : selectedRow) {
		                try {
							Connector.getConnection().getQuery("update pago set fecha_pago = curdate() where nro_prestamo  = "
								+nroCuenta+" and nro_pago = "+cuotas.getValueAt(i, 0)+";");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
					actualizarCuotas();
				}
			}
        });
        getContentPane().add(pagar, c);
        
	}
	
	public void actualizarPrestamos() {
		 try {
				prestamos.setModel(Connector.getConnection().getQuery("select tipo_doc, nro_doc from prestamo a natural join cliente where exists "
						+ "(select nro_pago from pago b where a.nro_prestamo = b.nro_prestamo and fecha_pago is NULL)"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private void actualizarCuotas() {
		try {
			if(prestamos.getSelectedRow() != -1) {
				cuotas.setModel(Connector.getConnection().getQuery("select nro_pago, valor_cuota, fecha_venc from pago "
						+ "natural join prestamo natural join cliente where '"+prestamos.getValueAt(prestamos.getSelectedRow(), 0)+"' "
						+ "= cliente.tipo_doc and "+prestamos.getValueAt(prestamos.getSelectedRow(), 1)+" = cliente.nro_doc "
						+ "and fecha_pago is NULL;"));
				actualizarMorosos();
			}
			if(cuotas.getRowCount() == 0) {
				actualizarPrestamos();
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void actualizarMorosos() {
		try {
			morosos.setModel(Connector.getConnection().getQuery("select nro_cliente, tipo_doc, nro_doc, apellido, nombre, "
					+ "nro_prestamo, monto, cant_meses, valor_cuota, count(a.nro_pago) as 'cuotas atrasadas' "
					+ "from cliente natural join prestamo natural join (select * from pago where fecha_pago is NULL and "
					+ "fecha_venc < curdate()) a group by nro_prestamo having count(a.nro_pago) >= 2;"));
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(morosos.getModel());
			morosos.setRowSorter(sorter);

			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
