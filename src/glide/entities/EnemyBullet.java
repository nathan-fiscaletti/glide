package glide.entities;

import glide.Game;
import glide.Glide;

public class EnemyBullet extends Entity {
	public EnemyBullet(double x, double y, Game game){
		super(x, y, game);
		this.setType(Entity.TYPE_ENEMYBULLET);
		this.setEntityImage(game.getTextures().enemybullet);
		Glide.shoot.play();
	}
	@Override
	public void tick(){
		this.setY(this.getY() + 10);
	}
}
