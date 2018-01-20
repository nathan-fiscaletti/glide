package two.d.engine.graphics;

import java.awt.image.BufferedImage;

public abstract class Textures {
	
	protected SpriteSheet sprite_sheet = null;
	
	public BufferedImage des1,des2,des3;
	
	public abstract void loadTextures();
	
	public Textures(SpriteSheet spriteSheet){
		sprite_sheet =  spriteSheet;
		this.loadTextures();
	}
}
