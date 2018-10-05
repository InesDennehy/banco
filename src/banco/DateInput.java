package banco;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DateInput extends JPanel{
	private JCTextField d, m, a;
	
	public DateInput() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 25;
		d = new JCTextField();
		d.setPlaceholder("dd");
		this.add(d, c);
		
		c.gridx = 1;
		c.ipadx = 5;
		JLabel bar1 = new JLabel("/");
		bar1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		this.add(bar1, c);
		
		c.gridx = 2;
		c.ipadx = 25;
		m = new JCTextField();
		m.setPlaceholder("mm");
		this.add(m, c);
		
		c.gridx = 3;
		c.ipadx = 5;
		JLabel bar2 = new JLabel("/");
		bar2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		this.add(bar2, c);
		
		c.gridx = 4;
		c.ipadx = 35;
		a = new JCTextField();
		a.setPlaceholder("aaaa");
		this.add(a, c);
	}

	public void setDate(String f) {
		// TODO Auto-generated method stub
		d.setText(Character.toString(f.charAt(0))+Character.toString(f.charAt(1)));
		m.setText(Character.toString(f.charAt(3))+Character.toString(f.charAt(4)));
		a.setText(Character.toString(f.charAt(6))+Character.toString(f.charAt(7))+
				Character.toString(f.charAt(8))+Character.toString(f.charAt(9)));
	}

	public String getDate() {
		// TODO Auto-generated method stub
		return d.getText()+"/"+m.getText()+"/"+a.getText();
	}
}
