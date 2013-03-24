package glide.entities;

import java.util.Random;

import glide.Game;

public class SmallMeteor extends Entity{

	public SmallMeteor(double x, double y, Game game) {
		super(x, y, game);
		this.setType(TYPE_METEORSMALL);
		Random r = new Random();
		this.setEntityImage((r.nextBoolean()) ? game.getTextures().smallmeteor1 : game.getTextures().smallmeteor2);
	}

}
