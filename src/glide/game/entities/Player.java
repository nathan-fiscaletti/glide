package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.screens.SinglePlayerGame;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class Player extends Entity<GlideEngine>
{
    public boolean shooting = false;
    public boolean beaming = false;
    public boolean plasma = false;
    public boolean hurting = false;
    public boolean t = true;
    public boolean t2 = true;

    public static final int normalSpeed = 8;
    public static final int beemSpeed = 15;
    public static final int boostSpeed = 15;

    public int speed = Player.normalSpeed;

    private int beamtick = 0;
    private int hurttick = 0;
    private int plasmatick = 0;

    public Player(Vector position, Screen<GlideEngine> screen){
        super(position, screen);
        this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("player"));
        this.setPositionConstraint(Vector.Max(this.getParentEngine()).plusX(-this.getSize().getWidth()).plusY(-this.getSize().getHeight()));
    }

    @Override
    public final void updateSprite()
    {
        if (this.beaming) {
            this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("player2"));
        } else if (!t) {
            this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("player"));
        }

        if (hurttick < 10 && this.hurting) {
            this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("playerhurt"));
        } else if (!t2) {
            if(!this.beaming){
                this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("player"));
            }else{
                this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("player2"));
            }
        }
    }

    /*
     * Note, Since the player is handled outside the scope of the EntityController
     * any collision tests for it MUST be done IN THE PLAYER CLASS, and not in the
     * other entity class.
     */
    @Override
    public final void onCollide(Entity<GlideEngine> collidedWith)
    {
        // Handle Collision with Enemy
        if (collidedWith instanceof Enemy) {
            Enemy enemy = (Enemy)collidedWith;
            if (! enemy.isDead) {
                if(!this.plasma && !this.getParentEngine().cheats.health_cheat){
                    int h = (this.getGame().getHealthBar().health > 1) ? this.getGame().getHealthBar().health - 1 : 8;

                    if(h == 8){
                        this.getGame().lose();
                    }else{
                        if(enemy.isBomb){
                            h = 8;
                            this.getGame().lose();
                        } else {
                            enemy.kill();
                        }
                        this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());

                        this.getGame().getHealthBar().health = h;
                        this.hurting = true;
                    }
                    this.getParentEngine().sounds.s_hurt.play(this.getParentEngine());
                }else{
                    if(enemy.isBomb){
                        enemy.kill(false);
                    } else {
                        enemy.kill();
                    }

                    this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
                }
            }
        }

        // Handle collision with Enemy Bullet
        else if (collidedWith instanceof EnemyBullet) {
            if(! this.plasma && !this.getParentEngine().cheats.health_cheat){
                int h = (this.getGame().getHealthBar().health > 1) ? this.getGame().getHealthBar().health - 1 : 8;
                if(h == 8){
                    this.getGame().lose();
                }else{
                    this.getGame().getHealthBar().health = h;
                    this.hurting = true;
                }
                this.getParentEngine().sounds.s_hurt.play(this.getParentEngine());
            }

            this.getParentScreen().getController().deSpawnEntity(collidedWith);
        }

        // Handle Collision with Drop
        else if (collidedWith instanceof Drop) {
            Drop drop = (Drop)collidedWith;
            if (! drop.isDead) {
                if (drop.dropType == Drop.Type.HEALTHPACK) {
                    this.getGame().getHealthBar().health += 1;
                } else if(drop.dropType == Drop.Type.BEAM) {
                    this.setBeaming(true);
                } else if(drop.dropType == Drop.Type.DIAMOND) {
                    this.getGame().setScore(this.getGame().getScore() + 15);
                } else if(drop.dropType == Drop.Type.DIAMOND2) {
                    this.getGame().getHealthBar().health = 8;
                } else if(drop.dropType == Drop.Type.DIAMOND3) {
                    this.getGame().mdbs = 5;
                } else if(drop.dropType == Drop.Type.PLASMA) {
                    this.setPlasma(true);
                    this.getGame().isPlasmaActive = true;
                } else if(drop.dropType == Drop.Type.MDB) {
                    if(this.getGame().mdbs < 5){
                        this.getGame().mdbs++;
                    }
                } else if(drop.dropType == Drop.Type.COD) {
                    if (this.getGame().cods < this.getGame().max_cods) {
                        this.getGame().cods ++;
                    }
                }

                drop.kill();
                this.getParentEngine().sounds.s_pickup.play(this.getParentEngine());
            }
        }

        // Handle Collision with Meteor
        else if (collidedWith instanceof Meteor || collidedWith instanceof SmallMeteor) {
            this.getParentScreen().getController().deSpawnEntity(collidedWith);

            if(!this.plasma && !this.getParentEngine().cheats.health_cheat){
                int h = (this.getGame().getHealthBar().health > 1) ? this.getGame().getHealthBar().health - 1 : 8;
                if(h == 8){
                    this.getGame().lose();
                }else{
                    this.getGame().getHealthBar().health = h;
                    this.hurting = true;
                    this.getParentEngine().sounds.s_hurt.play(this.getParentEngine());
                }
            }else{
                this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
            }
        }
    }

    @Override
    public final void update()
    {
        if(this.beaming){
            if(!this.getGame().isPaused()  && !this.getGame().lost() && !this.getGame().won()){
                speed = Player.beemSpeed;
                this.getParentScreen().getController().spawnEntity(new Bullet(this.getPosition().plusY(-32), this.getParentScreen()));
                beamtick ++;
                if(beamtick == 60){
                    this.getGame().beam = 4;
                }else if(beamtick == 120){
                    this.getGame().beam = 3;
                }else if (beamtick == 180){
                    this.getGame().beam = 2;
                }else if (beamtick == 240){
                    this.getGame().beam = 1;
                }else if(beamtick == 300){
                    speed = Player.normalSpeed;
                    setBeaming(false);
                    beamtick = 0;
                    this.getGame().beam = 0;
                }
            }
            t = false;
        }else if(!t){
            t = true;
        }

        if(this.plasma){
            if(!this.getGame().isPaused() && !this.getGame().lost() && !this.getGame().won()){
                plasmatick++;

                if(plasmatick == 60){
                    this.getGame().shield = 4;
                    this.getGame().isPlasmaActive = true;
                }else if(plasmatick == 120){
                    this.getGame().shield = 3;
                    this.getGame().isPlasmaActive = true;
                }else if (plasmatick == 180){
                    this.getGame().shield = 2;
                    this.getGame().isPlasmaActive = true;
                }else if (plasmatick == 240){
                    this.getGame().shield = 1;
                    this.getGame().isPlasmaActive = true;
                }else if(plasmatick == 300){
                    setPlasma(false);
                    this.getGame().isPlasmaActive = false;
                    plasmatick = 0;
                    this.getGame().shield = 0;
                }
            }
        }


        if(hurttick < 10 && this.hurting){
            t2 = false;
            hurttick ++;
        }else if(!t2){
            hurttick = 0;
            t2 = true;
            this.hurting = false;
        }
    }

    public final void setBeaming(boolean beam) {
        this.beaming = beam;
        this.getGame().beam = (beam) ? 5 : 0;
        if(beam){
            this.beamtick = 0;
        }
    }

    public final void setPlasma(boolean plasma) {
        this.plasma = plasma;
        this.getGame().shield = (plasma) ? 5 : 0;
        if(plasma){
            this.plasmatick = 0;
        }
    }

    private SinglePlayerGame getGame()
    {
        return (SinglePlayerGame)this.getParentScreen();
    }
}
