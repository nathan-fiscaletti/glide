package com.fiscalleti.glide.entities;

import com.fiscalleti.glide.Game;

public class HealthBar extends Entity{
	private int health = 3;
	private Game game;
	public HealthBar(double x, double y, Game game) {
		super(x, y, game);
		this.game = game;
		this.setType(Entity.TYPE_HEALTHBAR);
		this.setEntityImage(game.getTextures().healthbar1);
	}
	@Override
	public void tick(){
		if(health == 3){
			this.setEntityImage(game.getTextures().healthbar1);
		}else if(health == 2){
			this.setEntityImage(game.getTextures().healthbar2);
		}else if(health == 1){
			this.setEntityImage(game.getTextures().healthbar3);
		}else if(health > 3){
			health = 3;
		}else if(health < 1){
			health = 1;
		}else{
			health = 1;
		}
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
}
