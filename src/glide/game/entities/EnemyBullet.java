package glide.game.entities;

import glide.engine.Entity;
import glide.engine.Screen;
import glide.engine.Vector;
import glide.game.Glide;
import glide.game.screens.SinglePlayerGame;

public class EnemyBullet extends Entity {
	
	int speed = 10;
	
	public EnemyBullet(Vector position, Screen screen, int orientation){
		super(position, screen);
		this.velocity = new Vector(0, speed);
		
		switch (orientation) {
			case 0 :
				this.renderedSprite = Entity.getTextures().enemybullet;
				break;
			case 1 : 
				this.renderedSprite = Entity.getTextures().enemybulletleft;
				this.velocity = this.velocity.plusX(-(speed/2));
				break;
			case 2 : 
				this.renderedSprite = Entity.getTextures().enemybulletright;
				this.velocity = this.velocity.plusX((speed/2));
				break;
		}
	}
	
	@Override
	public final void onCollide(Entity collidedWith)
	{
		// Handle collision with player
		if (collidedWith instanceof Player) {
			Player player = (Player)collidedWith;
			if(!player.plasma && !Glide.health_cheat){
				int h = (this.getGame().getHealthBar().health > 1) ? this.getGame().getHealthBar().health - 1 : 8;
				if(h == 8){
					this.getGame().lose();
				}else{
					this.getGame().getHealthBar().health = h;
					player.hurting = true;
				}
				Glide.s_hurt.play();
			}
			
			this.parentScreen.controller.deSpawnEntity(this);
		} 
	}
	
	private SinglePlayerGame getGame()
	{
		return (SinglePlayerGame)this.parentScreen;
	}
}
