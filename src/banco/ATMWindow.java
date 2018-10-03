package banco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

public class ATMWindow extends JFrame{
	
	private JLabel saldo;
	private JTable tabla;
	
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
		int nroCuenta = Connector.getConnection().getCuenta(tarjeta);
		JLabel infoCuenta = new JLabel("Cuenta nro "+nroCuenta);
		infoCuenta.setFont(LoginWindow.font);
		infoCuenta.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		getContentPane().add(infoCuenta, c);

		c.gridy = 1;
		
		saldo = new JLabel("$"+Integer.toString(Connector.getConnection().getSaldoTarjeta(tarjeta)));
		saldo.setFont(new Font("Segan-Light", Font.PLAIN, 50));
		getContentPane().add(saldo, c);
		
		c.gridy = 2;
		
		tabla = Connector.getConnection().getUltimasTransacciones(nroCuenta);
		
		getContentPane().add(tabla, c);
		
		
	}
}
