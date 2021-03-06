package banco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JCPasswordTextField extends JPasswordField{

    private Dimension d = new Dimension(200,25);
    private String placeholder = "";
    private Color phColor= new Color(0,0,0);
    private boolean band = true;

    
    public JCPasswordTextField(){
        super();
        setSize(d);
        setFont(LoginWindow.font);
        setPreferredSize(d);
        setVisible(true);
        setMargin( new Insets(0,5,0,0));
    	setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                band = (JCPasswordTextField.this.getPassword().length>0) ? false:true ;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                band = false;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {}

        });
        JCPasswordTextField.this.addFocusListener(new FocusListener() {

		    @Override
		    public void focusGained(FocusEvent e) {
		    	JCPasswordTextField.this.setBackground(new Color(255, 246, 214));
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		    	JCPasswordTextField.this.setBackground(Color.white);
		    }

		});
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder=placeholder;
    }

    public String getPlaceholder()
    {
        return placeholder;
    }

    public Color getPhColor() {
        return phColor;
    }

    public void setPhColor(Color phColor) {
        this.phColor = phColor;
    }    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //color de placeholder 
        g.setColor( new Color(phColor.getRed(),phColor.getGreen(),phColor.getBlue(),90));
        //dibuja texto
        g.drawString((band)?placeholder:"",
                     getMargin().left,
                     (getSize().height)/2 + getFont().getSize()/2 );
      }

}