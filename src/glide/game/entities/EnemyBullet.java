package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.GlideTextures;
import glide.game.screens.SinglePlayerGame;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

public class EnemyBullet extends Entity<GlideEngine> {
	
	int speed = 10;
	
	public EnemyBullet(Vector position, Screen<GlideEngine> screen, int orientation){
		super(position, screen);
		this.velocity = new Vector(0, speed);
		
		switch (orientation) {
			case 0 :
				this.renderedSprite = Entity.getTextures(GlideTextures.class).enemybullet;
				break;
			case 1 : 
				this.renderedSprite = Entity.getTextures(GlideTextures.class).enemybulletleft;
				this.velocity = this.velocity.plusX(-(speed/2));
				break;
			case 2 : 
				this.renderedSprite = Entity.getTextures(GlideTextures.class).enemybulletright;
				this.velocity = this.velocity.plusX((speed/2));
				break;
		}
	}
	
	@Override
	public final void onCollide(Entity<GlideEngine> collidedWith)
	{
		// Handle collision with player
		if (collidedWith instanceof Player) {
			Player player = (Player)collidedWith;
			if(!player.plasma && !this.parentEngine.cheats.health_cheat){
				int h = (this.getGame().getHealthBar().health > 1) ? this.getGame().getHealthBar().health - 1 : 8;
				if(h == 8){
					this.getGame().lose();
				}else{
					this.getGame().getHealthBar().health = h;
					player.hurting = true;
				}
				this.parentEngine.sounds.s_hurt.play(this.parentEngine);
			}
			
			this.parentScreen.controller.deSpawnEntity(this);
		} 
	}
	
	private SinglePlayerGame getGame()
	{
		return (SinglePlayerGame)this.parentScreen;
	}
}
