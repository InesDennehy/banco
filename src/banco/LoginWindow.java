package banco;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfUser;
	private JTextField tfPassword;
	private JButton btnOk;
	private JButton btnDesconectar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 30;
		c.ipady = 5;
		c.gridwidth = 2;
		c.insets = new Insets(5,10,5,10);
		
		tfUser = new JTextField();
		contentPane.add(tfUser, c);
		tfUser.setColumns(10);
		
		c.gridy = 1;
		
		tfPassword = new JTextField();
		contentPane.add(tfPassword, c);
		tfPassword.setColumns(10);
		
		c.gridy = 2;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.weightx = 0.7;
		c.insets = new Insets(0,10,0,0);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean b = Connector.getConnection().adminLogin(LoginWindow.this ,tfUser.getText(), tfPassword.getText());
				if(b) {
					tfPassword.setText("Conexion exitosa");
				}
				else {
					tfPassword.setText("pone bien la contra imbecil");
				}
			}
		});
		contentPane.add(btnOk, c);
		
		c.gridx = 1;
		c.insets = new Insets(0,0,0,10);
		c.weightx = 0.3;
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean b = Connector.getConnection().disconnect(LoginWindow.this);
				if(b) {
					tfPassword.setText("se desconecto re pillo");
				}
			}
		});
		contentPane.add(btnDesconectar, c);
	}
}
