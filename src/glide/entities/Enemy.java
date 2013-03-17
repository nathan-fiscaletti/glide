package glide.entities;

import glide.Game;

import java.util.Random;


public class Enemy extends Entity{
	public int speed;
	public boolean drop;
	public Enemy(double x, double y, Game game, boolean drop) {
		super(x, y, game);
		this.drop = drop;
		this.setType(Entity.TYPE_ENEMY);
		Random r = new Random();
		speed = r.nextInt(4 - 1 + 1) + 1;
		if(speed == 4){
			this.setEntityImage(game.getTextures().enemy2);
			this.drop = true;
		}else{
			this.setEntityImage(game.getTextures().enemy);
		}
	}
	
	@Override
	public void tick(){
		setY(getY() + speed);
	}

	public void die(){
		if(drop){
			Random r = new Random();
			int go = r.nextInt(3);
			if(speed == 4){
				this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_DIAMOND);
				return;
			}
			if(go == 1){
				this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_HEALTHPACK);
			}else if(go == 2){
				this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_BEAM);
			}
		}
	}
	

}
