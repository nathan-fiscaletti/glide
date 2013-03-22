package glide;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Choice extends JFrame{
	private static final long serialVersionUID = 8533596448876300257L;
	public boolean cont = false;
	public void init(){
		this.setTitle(Glide.TITLE);
		JLabel c = new JLabel("Would you like to run the game in a window, or full screen");
		JButton window = new JButton("Windowed");
		JButton full = new JButton("Full Screen");
		Image i = new ImageIcon(this.getClass().getResource("/images/icon.png")).getImage();
		this.setIconImage(i);
		GlideSystem.icon = i;
		///////////////
		
		window.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Glide.fullscreen = false;
				cont = true;
				setVisible(false);
			}
			
		});
		
		full.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Glide.fullscreen = true;
				cont = true;
				setVisible(false);
			}
			
		});
		
		//////////////
		
		this.setSize(400, 95);
		this.setLayout(new FlowLayout());
		this.add(c);
		this.add(window);
		this.add(full);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
}
