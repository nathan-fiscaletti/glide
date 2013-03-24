package glide.entities;

import java.util.Random;

import glide.Game;

public class Meteor extends Entity{

	public Meteor(double x, double y, Game game) {
		super(x, y, game);
		this.setType(TYPE_METEORBIG);
		Random r = new Random();
		Random r2 = new Random();
		this.setEntityImage((r.nextBoolean()) ? game.getTextures().meteor1 : ((r2.nextBoolean()) ? game.getTextures().meteor2 : game.getTextures().meteor3));
	}
	
	public void die(){
		SmallMeteor s1 = new SmallMeteor(this.getX(), this.getY(), this.game);
		SmallMeteor s2 = new SmallMeteor(this.getX(), this.getY(), this.game);
		SmallMeteor s3 = new SmallMeteor(this.getX(), this.getY(), this.game);
		
		//Soon to be..
	}

}
