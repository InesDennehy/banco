package banco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InfoTablesWindow extends JDialog {
	private JList<String> tablas, atributos;
	
	public InfoTablesWindow() {
		this.setResizable(false);
		setPreferredSize(new Dimension(400, 300));
        this.setBounds(0, 0, 400, 300);
        setVisible(true);
        this.setTitle("BD Bank - Tables Information");
        getContentPane().setLayout(new GridBagLayout());
        this.setAlwaysOnTop(true);
        getContentPane().setBackground(new Color(232, 236, 242));
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 170;
        c.ipady = 240;
        c.fill = GridBagConstraints.BOTH;
        tablas = new JList<String>(getTables());
        tablas.setFont(LoginWindow.font2);
        tablas.setLayoutOrientation(JList.VERTICAL);
        JScrollPane tablasScroller = new JScrollPane(tablas);
        tablas.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        getContentPane().add(tablasScroller, c);
        
        c.gridx = 1;
        atributos = new JList<String>();
        atributos.setFont(LoginWindow.font2);
        atributos.setLayoutOrientation(JList.VERTICAL);
        atributos.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        JScrollPane atributosScroller = new JScrollPane(atributos);
        getContentPane().add(atributosScroller, c);
        
        tablas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) {
                atributos.setModel(getAttributes((String)tablas.getSelectedValue()));
            }
        });
	}
	
	private DefaultListModel<String> getTables() {
		return Connector.getConnection().getTables();
	}
	private DefaultListModel<String> getAttributes(String table) {
		return Connector.getConnection().getAttributes(table);
	}
}
