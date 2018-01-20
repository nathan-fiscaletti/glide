package glide.game.entitycontrollers;

import java.util.LinkedList;

import glide.engine.Entity;
import glide.engine.EntityController;
import glide.engine.Screen;
import glide.engine.Vector;
import glide.engine.graphics.Bounds;
import glide.game.Glide;
import glide.game.Glide.Difficulty;
import glide.game.entities.Enemy;
import glide.game.entities.Meteor;
import glide.game.entities.MultiDirectionalBullet;
import glide.game.entities.SmallMeteor;
import glide.game.entities.Enemy.ProtectorType;
import glide.game.screens.SinglePlayerGame;

public class SinglePlayerGameController extends EntityController {
	
	public SinglePlayerGameController(Screen screen) {
		super(screen);
	}

	public final SinglePlayerGame getGame()
	{
		return (this.parentScreen() instanceof SinglePlayerGame) 
				? (SinglePlayerGame)this.parentScreen()
				: null;
	}
	
	@Override
	protected void runControlTick() {
		if(Glide.mdb_cheat){
			this.getGame().mdbs = 5;
		}
		
		if(Glide.shield_cheat){
			this.getGame().isPlasmaActive = true;
			this.getGame().getPlayer().setPlasma(true);
		}
		
		if(Glide.beam_cheat){
			this.getGame().getPlayer().setBeaming(true);
		}
		
		if(Glide.cod_cheat){
			this.getGame().cods = this.getGame().max_cods;
		}
	}
	
	@Override
	public final void iterateEntityPerTick(Entity entity)
	{
		// Check Player collision
		if (! entity.isDead()) {
			if (entity.isCollidingWith(this.getGame().getPlayer())) {
				this.getGame().getPlayer().onCollide(entity);
			}
		}
		
		// Ad-Hoc check for circle.
		if (! entity.isDead()) {
			if (entity instanceof Enemy || entity instanceof Meteor || entity instanceof SmallMeteor) {
				if(Bounds.intersectsWith(this.getGame().circle, entity) && this.getGame().isCircling){
					if (entity instanceof Enemy) {
						Enemy enemy = (Enemy)entity;
						if (! enemy.isDead) {
							if (enemy.isBomb) {
								enemy.kill(false);
							} else {
								enemy.kill();
							}
							
							Glide.s_explosion.play();
						}
					}
					
					else if (entity instanceof Meteor) {
						((Meteor)entity).kill(false);
					}
					
					else if (entity instanceof SmallMeteor) {
						entity.kill();
					}
				}
			}
		}
	}
	
	public final boolean isBombSpawned(){
		LinkedList<Entity> en = this.getAllEntities();
		for(Entity e : en){
			if (e instanceof Enemy) {
				if(((Enemy)e).isBomb){
					return true;
				}
			}
		}
		return false;
	}
	
	public final void spawnBomb()
	{
		/* Bomb */
		this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98), this.getGame(), false, true, false, ProtectorType.None));
		
		/* Protectors */
		if(Glide.difficulty == Difficulty.Normal){
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68), this.getGame(), false, false, true, ProtectorType.Normal));
		}else if(Glide.difficulty == Difficulty.Hard){
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
		}else if(Glide.difficulty == Difficulty.Expert){
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68), this.getGame(), false, false, true, ProtectorType.Normal));
			
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
			this.spawnEntity(new Enemy(new Vector((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
		}
	}
	
	public final void shootMDB(Vector position)
	{
		MultiDirectionalBullet m = new MultiDirectionalBullet(position, this.getGame(), 1);
		MultiDirectionalBullet m1 = new MultiDirectionalBullet(position, this.getGame(), 2);
		MultiDirectionalBullet m2 = new MultiDirectionalBullet(position, this.getGame(), 3);
		MultiDirectionalBullet m3 = new MultiDirectionalBullet(position, this.getGame(), 4);
		MultiDirectionalBullet m4 = new MultiDirectionalBullet(position, this.getGame(), 5);
		MultiDirectionalBullet m5 = new MultiDirectionalBullet(position, this.getGame(), 6);
		MultiDirectionalBullet m6 = new MultiDirectionalBullet(position, this.getGame(), 7);
		MultiDirectionalBullet m7 = new MultiDirectionalBullet(position, this.getGame(), 8);
		this.spawnEntity(m);
		this.spawnEntity(m);
		this.spawnEntity(m1);
		this.spawnEntity(m2);
		this.spawnEntity(m3);
		this.spawnEntity(m4);
		this.spawnEntity(m5);
		this.spawnEntity(m6);
		this.spawnEntity(m7);
	}
}
