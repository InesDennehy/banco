package banco;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import quick.dbtable.DBTable;

public class AdminWindow extends JFrame {

	private JPanel contentPane;
	private JPanel pnlConsulta;
	private JTextArea txtConsulta;
	private JButton botonBorrar;
	private JButton btnEjecutar;
	private DBTable tabla;    
	private JScrollPane scrConsulta;

	/**
	 * Create the frame.
	 */
	public AdminWindow() {
		setPreferredSize(new Dimension(800, 600));
        this.setBounds(0, 0, 800, 600);
        setVisible(true);
        BorderLayout thisLayout = new BorderLayout();
        this.setTitle("Consultas (Utilizando DBTable)");
        getContentPane().setLayout(thisLayout);
        this.setClosable(true);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setMaximizable(true);
        this.addComponentListener(new ComponentAdapter() {
           public void componentHidden(ComponentEvent evt) {
              thisComponentHidden(evt);
           }
           public void componentShown(ComponentEvent evt) {
              thisComponentShown(evt);
           }
        });
        
        pnlConsulta = new JPanel();
        getContentPane().add(pnlConsulta, BorderLayout.NORTH);
        scrConsulta = new JScrollPane();
        pnlConsulta.add(scrConsulta);
        txtConsulta = new JTextArea();
        scrConsulta.setViewportView(txtConsulta);
        txtConsulta.setTabSize(3);
        txtConsulta.setColumns(80);
        txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
        txtConsulta.setText("SELECT t.fecha, t.nombre_batalla, b.nombre_barco, b.id, b.capitan, r.resultado \n" +
                          "FROM batallas t, resultados r, barcos b \n" +
                          "WHERE t.nombre_batalla = r.nombre_batalla \n" +
                          "AND r.nombre_barco = b.nombre_barco \n" +
                          "ORDER BY t.fecha, t.nombre_batalla, b.nombre_barco");
        txtConsulta.setFont(new java.awt.Font("Monospaced",0,12));
        txtConsulta.setRows(10);   
          
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
	}

}
