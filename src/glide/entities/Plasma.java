package glide.entities;

import glide.Game;

public class Plasma extends Entity{

	public Plasma(double x, double y, Game game) {
		super(x, y, game);
		this.setEntityImage(game.getTextures().plasmaplayer);
		this.setType(Entity.TYPE_PLASMAPLAYER);
	}

}
