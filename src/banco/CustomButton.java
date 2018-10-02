package banco;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import quick.dbtable.DBTable;


public class CustomButton extends JLabel{

	private String mode;
	public CustomButton(String text, int x, int y,  String m) {
		super();
		this.setLayout(new CardLayout());
		this.setOpaque(false);
		this.mode = m;
		ImageIcon imageIcon = new ImageIcon();
		if(mode == "normal") {
			imageIcon = new ImageIcon("boton.png"); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);  // transform it back
		} else if(mode == "tall") {
			imageIcon = new ImageIcon("botonTall.png"); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);  // transform it back
		} else if(mode == "medium") {
			imageIcon = new ImageIcon("botonMedium.png"); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);  // transform it back
		}
		
		this.setIcon(imageIcon);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		JLabel texto = new JLabel(text);
		texto.setHorizontalAlignment(CENTER);
		texto.setForeground(Color.white);
		texto.setFont(LoginWindow.font2);
		add(texto);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(mode == "normal") {
					ImageIcon imageIcon = new ImageIcon("mouseEntered.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if(mode == "medium") {
					ImageIcon imageIcon = new ImageIcon("mouseEnteredMedium.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if (mode == "tall") {
					ImageIcon imageIcon = new ImageIcon("mouseEnteredTall.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(mode == "normal") {
					ImageIcon imageIcon = new ImageIcon("boton.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if(mode == "medium") {
					ImageIcon imageIcon = new ImageIcon("botonMedium.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if (mode == "tall") {
					ImageIcon imageIcon = new ImageIcon("botonTall.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(mode == "normal") {
					ImageIcon imageIcon = new ImageIcon("mousePressed.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if (mode == "tall") {
					ImageIcon imageIcon = new ImageIcon("mousePressedTall.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if(mode == "medium") {
					ImageIcon imageIcon = new ImageIcon("mousePressedMedium.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(mode == "normal") {
					ImageIcon imageIcon = new ImageIcon("mouseEntered.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if (mode == "tall") {
					ImageIcon imageIcon = new ImageIcon("mouseEnteredTall.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
				if(mode == "medium") {
					ImageIcon imageIcon = new ImageIcon("mouseEnteredMedium.png"); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);  // transform it back
					CustomButton.this.setIcon(imageIcon);
				}
			}
		});
	}
}
