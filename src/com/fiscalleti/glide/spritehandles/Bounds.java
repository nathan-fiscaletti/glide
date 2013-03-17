package com.fiscalleti.glide.spritehandles;

import java.awt.Rectangle;

import com.fiscalleti.glide.entities.Entity;

public class Bounds {
	public static boolean intersectsWith(Entity a, Entity b){
		Rectangle r = new Rectangle();
		r.setBounds((int)a.getX(), (int)a.getY(), a.getEntityImage().getWidth(), a.getEntityImage().getHeight());
		
		Rectangle r2 = new Rectangle();
		r2.setBounds((int)b.getX(), (int)b.getY(), b.getEntityImage().getWidth(), b.getEntityImage().getHeight());
		
		return (r.intersects(r2));
	}
}
