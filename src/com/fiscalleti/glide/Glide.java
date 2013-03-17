package com.fiscalleti.glide;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Glide {
	public static JFrame frame = new JFrame(MainMenu.TITLE);
	public static Game game;
	public static MainMenu mm;
	public static HTPMenu htp;
	public static void main(String[] args){
		mm = new MainMenu();
		mm.setPreferredSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
		mm.setMaximumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
		mm.setMinimumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
		frame = new JFrame(MainMenu.TITLE);
		frame.add(mm);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		mm.start();
	}
}
