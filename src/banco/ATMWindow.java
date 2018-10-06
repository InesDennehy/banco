package banco;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ATMWindow extends JFrame{
	
	private JLabel saldo;
	private JTable tabla;
	private DateInput inicio, fin;
	
	public ATMWindow(long tarjeta) {
		this.setResizable(false);
		setPreferredSize(new Dimension(800, 600));
        this.setBounds(0, 0, 800, 600);
        setVisible(true);
        this.setTitle("BD Bank - ATM");
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(232, 236, 242));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 0;
		
		int nroCuenta = Connector.getConnection().getCuenta(tarjeta);
		JLabel infoCuenta = new JLabel("Cuenta nro "+nroCuenta);
		infoCuenta.setFont(LoginWindow.font);
		infoCuenta.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		getContentPane().add(infoCuenta, c);

		
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 2;
		
		saldo = new JLabel("$"+Integer.toString(Connector.getConnection().getSaldoTarjeta(tarjeta)));
		saldo.setFont(new Font("Segan-Light", Font.PLAIN, 50));
		saldo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		getContentPane().add(saldo, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.EAST;
		JLabel fSel = new JLabel("Mostrando transacciones entre las fechas:");
		fSel.setFont(LoginWindow.font);
		fSel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		getContentPane().add(fSel, c);
		
		c.gridy = 1;
		c.ipadx = 10;
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 1;
		c.insets = new Insets(5, 250, 0, 0);
		//agregar fecha inicio
		inicio = new DateInput();
		inicio.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		getContentPane().add(inicio, c);
		
		c.gridx = 2;
		c.insets = new Insets(5, 0, 0, 0);
		//agregar hasta
		JLabel hasta = new JLabel("hasta");
		hasta.setFont(LoginWindow.font2);
		getContentPane().add(hasta, c);
		
		c.gridx = 3;
		//agregar fecha fin
		fin = new DateInput();
		getContentPane().add(fin, c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 250, 0, 0);
		CustomButton buscar = new CustomButton("BUSCAR", 123, 30, "normal");
		buscar.addMouseListener(new MouseListener() {
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
				tabla.setModel(Connector.getConnection().getATMQuery(nroCuenta, "select fecha, hora, tipo, monto from trans_cajas_ahorro "
						+"where nro_ca = "+Integer.toString(nroCuenta)+" and fecha <= '"+Fechas.convertirStringADateSQL(fin.getDate())+"' and "
						+"fecha >= '"+Fechas.convertirStringADateSQL(inicio.getDate())+"' order by fecha desc, hora desc;"));
			}
			
		});
		getContentPane().add(buscar, c);
		
		
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		c.ipadx = 750;
		c.ipady = 440;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, 0, 0);

		tabla = new JTable();
		tabla.setModel(Connector.getConnection().getATMQuery(nroCuenta, "select fecha, hora, tipo, monto from trans_cajas_ahorro where nro_ca = "+Integer.toString(nroCuenta)+" order by fecha desc, hora desc limit 15;"));
		tabla.setBorder(null);
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
		JScrollPane sp = new JScrollPane(tabla);
		sp.setBackground(new Color(232, 236, 242));
		
		if(tabla.getRowCount() > 0) {
			inicio.setDate((String) tabla.getValueAt(tabla.getRowCount()-1, 0));
			fin.setDate((String) tabla.getValueAt(0, 0));
		}
		
		getContentPane().add(sp, c);
		
		
	}
}
