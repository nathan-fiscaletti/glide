package com.fiscalleti.glide.entities;

import com.fiscalleti.glide.Game;

public class Drop extends Entity{
	public static final int TYPE_HEALTHPACK = 8;
	public static final int TYPE_BEAM = 9;
	public static final int TYPE_DIAMOND = 10;
	public Drop(double x, double y, Game game, int Type) {
		super(x, y, game);
		this.setType(Type);
		if(Type == Drop.TYPE_HEALTHPACK){
			this.setEntityImage(game.getTextures().healthpack);
		}else if(Type == Drop.TYPE_BEAM){
			this.setEntityImage(game.getTextures().beam);
		}else if(Type == Drop.TYPE_DIAMOND){
			this.setEntityImage(game.getTextures().diamond);
		}
	}
}
