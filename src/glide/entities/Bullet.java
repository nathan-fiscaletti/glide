package glide.entities;

import glide.SinglePlayerGame;
import glide.Glide;

public class Bullet extends Entity{
	public Bullet(double x, double y, SinglePlayerGame game){
		super(x, y, game);
		this.setType(Entity.Type.BULLET);
		this.setEntityImage(game.getTextures().bullet);
		Glide.s_shoot.play();
	}
	@Override
	public void tick(){
		this.setY(this.getY() - 20);
	}
}
