package banco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JTable;

public class InfoTablesWindow extends JDialog {
	private JTable tablas, atributos;
	
	public InfoTablesWindow() {
		this.setResizable(false);
		setPreferredSize(new Dimension(600, 450));
        this.setBounds(0, 0, 600, 450);
        setVisible(true);
        this.setTitle("BD Bank - Tables Information");
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(232, 236, 242));
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        tablas = new JTable();
        getContentPane().add(tablas);
        
        c.gridx = 1;
        atributos = new JTable();
        getContentPane().add(atributos);
	}
}
