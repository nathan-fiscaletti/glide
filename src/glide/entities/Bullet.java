package glide.entities;

import glide.Game;

public class Bullet extends Entity{
	public Bullet(double x, double y, Game game){
		super(x, y, game);
		this.setType(Entity.TYPE_BULLET);
		this.setEntityImage(game.getTextures().bullet);
	}
	@Override
	public void tick(){
		this.setY(this.getY() - 10);
	}
}
