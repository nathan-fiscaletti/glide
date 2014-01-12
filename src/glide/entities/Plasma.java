package glide.entities;

import glide.SinglePlayerGame;

public class Plasma extends Entity{

	public Plasma(double x, double y, SinglePlayerGame game) {
		super(x, y, game);
		this.setEntityImage(game.getTextures().plasmaplayer);
		this.setType(Entity.Type.PLASMAPLAYER);
	}

}
