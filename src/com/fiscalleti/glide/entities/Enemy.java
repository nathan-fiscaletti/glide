package com.fiscalleti.glide.entities;

import java.util.Random;

import com.fiscalleti.glide.Game;

public class Enemy extends Entity{
	public int speed;
	public boolean drop;
	public Enemy(double x, double y, Game game, boolean drop) {
		super(x, y, game);
		this.drop = drop;
		this.setType(Entity.TYPE_ENEMY);
		this.setEntityImage(game.getTextures().enemy);
		Random r = new Random();
		speed = r.nextInt(4 - 1 + 1) + 1;
	}
	
	@Override
	public void tick(){
		setY(getY() + speed);
	}

	public void die(){
		if(drop){
			Random r = new Random();
			int go = r.nextInt(4);
			if(go == 1){
				this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_HEALTHPACK);
			}else if(go == 2){
				this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_BEAM);
			}else if(go == 3){
				this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_DIAMOND);
			}
		}
	}
	

}
