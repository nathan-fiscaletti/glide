package glide.entities;

import glide.SinglePlayerGame;
import glide.Glide;

public class EnemyBullet extends Entity {
	public EnemyBullet(double x, double y, SinglePlayerGame game){
		super(x, y, game);
		this.setType(Entity.Type.ENEMYBULLET);
		this.setEntityImage(game.getTextures().enemybullet);
		Glide.s_shoot.play();
	}
	@Override
	public void tick(){
		this.setY(this.getY() + 10);
	}
}
