package banco;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TransferenceWindow extends JFrame{
	private JPanel contentPane;
	private JCTextField tfUser, tfPassword;
	private CustomButton btnOk;
	private GridBagConstraints c;
	private JLabel errorMessage;
	
	public TransferenceWindow(long tarjeta, int caja, ATMWindow frame) {
		this.setTitle("BD Bank");
		this.setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridBagLayout());
		contentPane.setBackground(new Color(232, 236, 242));
		setContentPane(contentPane);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 0;
		c.ipady = 0;
		c.weighty = 1;
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.insets = new Insets(0,15,0,15);
		JLabel lblLogin = new JLabel("BD Bank - Transferencia");
		lblLogin.setFont(LoginWindow.font);
		contentPane.add(lblLogin, c);

		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,10,5,5);
		
		tfUser = new JCTextField();
		contentPane.add(tfUser, c);
		tfUser.setColumns(10);
		tfUser.setPlaceholder("Nro de cuenta destino");
		
		c.gridy = 2;
		
		tfPassword = new JCTextField();
		contentPane.add(tfPassword, c);
		tfPassword.setColumns(10);
		tfPassword.setPlaceholder("Monto");

		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0;
		c.gridwidth = 2;
		
		errorMessage = new JLabel("");
		errorMessage.setForeground(new Color(214, 65, 57));
		errorMessage.setFont(LoginWindow.font2);
		contentPane.add(errorMessage, c);
		
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.weightx = 0.8;
		c.weighty = 1;
		c.insets = new Insets(0,5,0,0);
		
		btnOk = new CustomButton("OK", 123, 30, "normal");
		btnOk.setFont(LoginWindow.font2);
		btnOk.addMouseListener(new MouseListener() {
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
				int n = JOptionPane.showConfirmDialog(
                        frame, "Desea confirmar la extracción de $"+tfUser.getText()+"?",
                        "Confirmar transacción",
                        JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					String s = Connector.getConnection().doTransaction("call transferencia("+caja+", '"+tfUser.getText()+"', '"+tfPassword.getText()+
							"', "+tarjeta+", @salida);", 
							"select @salida;");
					if(s != null) {
						JDialog response = new JDialog();
						response.setVisible(true);
						JLabel msg = new JLabel(s);
						msg.setFont(LoginWindow.font2);
						msg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
						response.setBounds(100,100,100,100);
						response.setContentPane(msg);
					}
					frame.actualizarSaldo();
					frame.actualizarUltimas();
					TransferenceWindow.this.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} else if (n == JOptionPane.NO_OPTION) {
					TransferenceWindow.this.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} else {
				
				}
			}
		});
		contentPane.add(btnOk, c);
	}
}
