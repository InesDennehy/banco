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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NewLoanWindow extends JDialog{
	private JPanel contentPane;
	private JCTextField tfUser, tfPassword, tfMonto;
	private JComboBox<Integer> cuotas;
	private CustomButton btnOk;
	private GridBagConstraints c;
	private JLabel errorMessage;
	
	/**
	 * Create the frame.
	 */
	public NewLoanWindow(int legajo, StaffWindow sw) {
		this.setTitle("BD Bank - Nuevo Préstamo");
		this.setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 250);
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
		c.gridwidth = 3;
		c.insets = new Insets(0,15,0,15);
		JLabel lblLogin = new JLabel("Ingrese los datos del cliente");
		lblLogin.setFont(LoginWindow.font);
		contentPane.add(lblLogin, c);
		
		c.gridy = 1;
		c.ipady = 0;
		c.insets = new Insets(5,10,5,5);
		
		tfUser = new JCTextField();
		contentPane.add(tfUser, c);
		tfUser.setColumns(10);
		tfUser.setPlaceholder("Tipo de documento");
		
		c.gridy = 2;
		
		tfPassword = new JCTextField();
		contentPane.add(tfPassword, c);
		tfPassword.setColumns(10);
		tfPassword.setPlaceholder("Nro de Documento");
		
		c.gridy = 3;
		c.gridwidth = 1;
		c.ipadx = 50;
		c.ipady = 0;
		tfMonto = new JCTextField();
		contentPane.add(tfMonto, c);
		tfMonto.setColumns(7);
		tfMonto.setPlaceholder("Monto");
		
		c.gridx = 1;
		c.ipadx = 0;
		cuotas = new JComboBox<Integer>(Connector.getConnection().getMesesPosibles());
		cuotas.setFont(LoginWindow.font);
		contentPane.add(cuotas, c);
		
		c.gridx = 2;
		JLabel cu = new JLabel("cuotas");
		cu.setFont(LoginWindow.font2);
		contentPane.add(cu, c);

		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 0;
		c.gridwidth = 3;
		
		errorMessage = new JLabel("");
		errorMessage.setForeground(new Color(214, 65, 57));
		errorMessage.setFont(LoginWindow.font2);
		contentPane.add(errorMessage, c);
		
		c.gridy = 5;
		c.ipadx = 0;
		c.ipady = 0;
		c.weighty = 1;
		c.insets = new Insets(0,5,0,0);
		
		btnOk = new CustomButton("CREAR PRESTAMO", 170, 30, "normal");
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
				try {
					int nroCliente = Connector.getConnection().getNroCliente(tfUser.getText(), tfPassword.getText());
					boolean noTienePrestamo = true;
					int maxMonto = Connector.getConnection().getMaxMonto();
					if(nroCliente != 0) {
						noTienePrestamo = Connector.getConnection().noTienePrestamo(nroCliente);
					}
					if(nroCliente != 0 && noTienePrestamo && Double.parseDouble(tfMonto.getText()) < maxMonto) {
						Connector.getConnection().insertarPrestamo(nroCliente, Double.parseDouble(tfMonto.getText()), (Integer)cuotas.getSelectedItem(), legajo);
						sw.actualizarPrestamos();
						NewLoanWindow.this.dispatchEvent(new WindowEvent(NewLoanWindow.this, WindowEvent.WINDOW_CLOSING));
					}
					else if (Double.parseDouble(tfMonto.getText()) > maxMonto) {
						NewLoanWindow.this.setBounds(100, 100, 300, 290);
						StringBuilder sb = new StringBuilder(64);
					    sb.append("<html>El monto ingresado es mayor a la cantidad máxima permitida</html>");

					    errorMessage.setText(sb.toString());
						errorMessage.setBounds(100, 100, 300, 50);
						tfUser.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
						tfPassword.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
					}
					else if (nroCliente == 0) {
						NewLoanWindow.this.setBounds(100, 100, 300, 290);
						StringBuilder sb = new StringBuilder(64);
					    sb.append("<html>El número y tipo de documento no pertenece a un cliente del banco</html>");

					    errorMessage.setText(sb.toString());
						errorMessage.setBounds(100, 100, 300, 50);
						tfUser.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
						tfPassword.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
					}
					else if(!noTienePrestamo){
						NewLoanWindow.this.setBounds(100, 100, 300, 290);
						StringBuilder sb = new StringBuilder(64);
					    sb.append("<html>El cliente ingresado ya tiene un préstamo vigente y no se le puede otorgar otro</html>");

					    errorMessage.setText(sb.toString());
						errorMessage.setBounds(100, 100, 300, 50);
						tfUser.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
						tfPassword.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createLineBorder(new Color(214, 65, 57)), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnOk, c);
	}
}
