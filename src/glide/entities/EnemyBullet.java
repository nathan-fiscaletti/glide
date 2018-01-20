package glide.entities;

import glide.engine.Entity;
import glide.engine.Vector;
import glide.game.Glide;
import glide.game.screens.SinglePlayerGame;

public class EnemyBullet extends Entity {
	
	int speed = 10;
	
	public EnemyBullet(Vector position, SinglePlayerGame attachedGame, int orientation){
		super(position, attachedGame);
		this.velocity = new Vector(0, speed);
		
		switch (orientation) {
			case 0 :
				this.renderedSprite = attachedGame.getTextures().enemybullet;
				break;
			case 1 : 
				this.renderedSprite = attachedGame.getTextures().enemybulletleft;
				this.velocity = this.velocity.plusX(-(speed/2));
				break;
			case 2 : 
				this.renderedSprite = attachedGame.getTextures().enemybulletright;
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
				int h = (this.attachedGame.getHealthBar().health > 1) ? this.attachedGame.getHealthBar().health - 1 : 8;
				if(h == 8){
					this.attachedGame.lose();
				}else{
					this.attachedGame.getHealthBar().health = h;
					player.hurting = true;
				}
				Glide.s_hurt.play();
			}
			
			this.attachedGame.getController().deSpawnEntity(this);
		} 
	}
}
