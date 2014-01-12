package glide.entities;

import glide.SinglePlayerGame;

public class HealthBar extends Entity{
	private int health = 8;
	private SinglePlayerGame game;
	public HealthBar(double x, double y, SinglePlayerGame game) {
		super(x, y, game);
		this.game = game;
		this.setType(Entity.Type.HEALTHBAR);
		this.setEntityImage(game.getTextures().healthbar1);
	}
	@Override
	public void tick(){
		if(health == 8){
			this.setEntityImage(game.getTextures().healthbar1);
		}else if(health == 7){
			this.setEntityImage(game.getTextures().healthbar2);
		}else if(health == 6){
			this.setEntityImage(game.getTextures().healthbar3);
		}else if(health == 5){
			this.setEntityImage(game.getTextures().healthbar4);
		}else if(health == 4){
			this.setEntityImage(game.getTextures().healthbar5);
		}else if(health == 3){
			this.setEntityImage(game.getTextures().healthbar6);
		}else if(health == 2){
			this.setEntityImage(game.getTextures().healthbar7);
		}else if(health == 1){
			this.setEntityImage(game.getTextures().healthbar8);
		}else if(health > 8){
			health = 8;
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
