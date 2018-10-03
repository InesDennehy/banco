package banco;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JCTextField tfUser;
	private JCPasswordTextField tfPassword;
	private CustomButton btnOk;
	private CustomButton btnDesconectar;
	private GridBagConstraints c;
	private JLabel errorMessage;
	public static Font font = new Font("Segan-Light", Font.PLAIN, 14);
	public static Font font2 = new Font("Segan-Light", Font.PLAIN, 12);

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
		c.ipadx = 30;
		c.ipady = 5;
		c.weighty = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,15,0,15);
		JLabel lblLogin = new JLabel("BD Bank - Login");
		lblLogin.setFont(font);
		contentPane.add(lblLogin, c);
		
		c.gridy = 1;
		c.ipady = 0;
		c.insets = new Insets(5,10,5,5);
		
		tfUser = new JCTextField();
		contentPane.add(tfUser, c);
		tfUser.setColumns(10);
		tfUser.setPlaceholder("Usuario");
		
		c.gridy = 2;
		
		tfPassword = new JCPasswordTextField();
		contentPane.add(tfPassword, c);
		tfPassword.setColumns(10);
		tfPassword.setPlaceholder("Contraseña");

		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0;
		c.gridwidth = 2;
		
		errorMessage = new JLabel("");
		errorMessage.setForeground(new Color(214, 65, 57));
		errorMessage.setFont(font2);
		contentPane.add(errorMessage, c);
		
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.weightx = 0.8;
		c.weighty = 1;
		c.insets = new Insets(0,5,0,0);
		
		btnOk = new CustomButton("OK", 123, 30, "normal");
		btnOk.setFont(font2);
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
				LoginInfo info = Connector.getConnection().login(LoginWindow.this ,tfUser.getText(), tfPassword.getText());
				if(info != null && info.getStatus() == "admin") {
					try {
						AdminWindow frame = new AdminWindow(info.getTable());
						frame.setVisible(true);
						errorMessage.setText("");
						LoginWindow.this.setBounds(100, 100, 300, 220);
						tfUser.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
						tfPassword.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
						LoginWindow.this.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(info != null && info.getStatus() == "atm") {
					try {
						ATMWindow frame = new ATMWindow(info.getNumber());
						frame.setVisible(true);
						errorMessage.setText("");
						LoginWindow.this.setBounds(100, 100, 300, 220);
						tfUser.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
						tfPassword.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
						LoginWindow.this.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					LoginWindow.this.setBounds(100, 100, 300, 260);
					StringBuilder sb = new StringBuilder(64);
	                sb.append("<html>El usuario o contraseña ingresados no son correctos, por favor inténtelo de nuevo</html>");

	                errorMessage.setText(sb.toString());
					errorMessage.setBounds(100, 100, 300, 50);
					tfUser.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
					tfPassword.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
				}
				
			}
		});
		contentPane.add(btnOk, c);
		
		c.gridx = 1;
		c.insets = new Insets(0,0,0,10);
		c.weightx = 0.2;
		
		btnDesconectar = new CustomButton("DESCONECTAR",123,30, "normal");
		btnDesconectar.addMouseListener(new MouseListener() {
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
				Connector.getConnection().disconnect(LoginWindow.this);
			}
		});
		contentPane.add(btnDesconectar, c);
	}
}
