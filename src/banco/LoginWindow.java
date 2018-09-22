package banco;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
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
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tfUser = new JTextField();
		contentPane.add(tfUser, BorderLayout.NORTH);
		tfUser.setColumns(10);
		
		tfPassword = new JTextField();
		contentPane.add(tfPassword, BorderLayout.CENTER);
		tfPassword.setColumns(10);
		
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
		contentPane.add(btnOk, BorderLayout.SOUTH);
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean b = Connector.getConnection().disconnect(LoginWindow.this);
				if(b) {
					tfPassword.setText("se desconecto re pillo");
				}
			}
		});
		contentPane.add(btnDesconectar, BorderLayout.EAST);
	}
}
