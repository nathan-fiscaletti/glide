package glide.game.entitycontrollers;

import java.util.LinkedList;

import glide.game.Bounds;
import glide.game.Difficulty;
import glide.game.GlideEngine;
import glide.game.entities.Enemy;
import glide.game.entities.Meteor;
import glide.game.entities.MultiDirectionalBullet;
import glide.game.entities.SmallMeteor;
import glide.game.entities.Enemy.ProtectorType;
import glide.game.screens.SinglePlayerGame;

import jtwod.engine.drawable.Entity;
import jtwod.engine.EntityController;
import jtwod.engine.Scene;
import jtwod.engine.metrics.Vector;

public final class SinglePlayerGameController extends EntityController<GlideEngine> {

    public SinglePlayerGameController(Scene<GlideEngine> screen) {
        super(screen);
    }

    public final SinglePlayerGame getGame()
    {
        return (this.getParentScreen() instanceof SinglePlayerGame)
                ? (SinglePlayerGame)this.getParentScreen()
                : null;
    }

    @Override
    protected void runControlTick() {
        if(this.getParentEngine().cheats.mdb_cheat){
            this.getGame().mdbs = 5;
        }

        if(this.getParentEngine().cheats.shield_cheat){
            this.getGame().isPlasmaActive = true;
            this.getGame().getPlayer().setPlasma(true);
        }

        if(this.getParentEngine().cheats.beam_cheat){
            this.getGame().getPlayer().setBeaming(true);
        }

        if(this.getParentEngine().cheats.cod_cheat){
            this.getGame().cods = this.getGame().max_cods;
        }
    }

    @Override
    public final void iterateEntityPerTick(Entity<GlideEngine> entity)
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

                            this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
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
        LinkedList<Entity<GlideEngine>> en = this.getAllEntities();
        for(Entity<GlideEngine> e : en){
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
        this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32, -98), this.getGame(), false, true, false, ProtectorType.None));

        /* Protectors */
        if(this.getParentEngine().difficulty == Difficulty.Normal){
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32, -98 + 68), this.getGame(), false, false, true, ProtectorType.Normal));
        }else if(this.getParentEngine().difficulty == Difficulty.Hard){
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32, -98 + 68 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
        }else if(this.getParentEngine().difficulty == Difficulty.Expert){
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 72, -98), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 36, -98 + 34), this.getGame(), false, false, true, ProtectorType.Normal));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32, -98 + 68), this.getGame(), false, false, true, ProtectorType.Normal));

            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 72, -98 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 + 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32 - 36, -98 + 34 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
            this.spawnEntity(new Enemy(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2 - 32, -98 + 68 + 34), this.getGame(), false, false, true, ProtectorType.Hard));
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
