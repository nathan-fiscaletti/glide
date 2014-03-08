package glide.input;

import glide.Glide.Difficulty;
import glide.SinglePlayerGame;
import glide.Glide;
import glide.entities.Bullet;
import glide.entities.Drop;
import glide.entities.Enemy;
import glide.entities.EnemyBullet;
import glide.entities.Entity;
import glide.entities.Meteor;
import glide.entities.MultiDirectionalBullet;
import glide.entities.SmallMeteor;
import glide.entities.Enemy.ProtectorType;
import glide.spritehandles.Bounds;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;


public class Controller {
	public LinkedList<Bullet> b = new LinkedList<Bullet>();
	public LinkedList<EnemyBullet> eb = new LinkedList<EnemyBullet>();
	public LinkedList<Enemy> e = new LinkedList<Enemy>();
	public LinkedList<Drop> drops = new LinkedList<Drop>();
	public LinkedList<MultiDirectionalBullet> mdb = new LinkedList<MultiDirectionalBullet>();
	
	/* Meteors */
	public LinkedList<Meteor> meteors = new LinkedList<Meteor>();
	public LinkedList<SmallMeteor> small_meteors = new LinkedList<SmallMeteor>();
	
	Random r = new Random();
	SinglePlayerGame game;
	public Controller(SinglePlayerGame game){
		this.game = game;
	}
	
	public void tick(){
		if(Glide.mdb_cheat){
			game.mdbs = 5;
		}
		if(Glide.shield_cheat){
			game.plasma = true;
			game.getPlayer().setPlasma(true);
		}
		if(Glide.beam_cheat){
			game.getPlayer().setBeaming(true);
		}
		if(Glide.cod_cheat){
			game.cods = game.max_cods;
		}
		
		try{
		for(int i = 0; i < mdb.size(); i++){
			mdb.get(i).tick();
			if(mdb.get(i).getX() < 0 || mdb.get(i).getY() < 0 || mdb.get(i).getY() > (Glide.HEIGHT * Glide.SCALE) || mdb.get(i).getX() > (Glide.WIDTH * Glide.SCALE)){
				removeMDBullet(mdb.get(i));
			}
		}
		for(int i = 0; i < b.size(); i++){
			try{
				b.get(i).tick();
			}catch(Exception e){
				
			}
			if(b.get(i).getY() < 0)
				removeBullet(b.get(i));
		}
		for(int i = 0; i < eb.size(); i++){
			try{
			eb.get(i).tick();
			if(Bounds.intersectsWith(eb.get(i), game.getPlayer())){
				if(!game.getPlayer().isPlasma() && !Glide.health_cheat){
					int h = (game.getHealthBar().getHealth() > 1) ? game.getHealthBar().getHealth() - 1 : 8;
					if(h == 8){
						game.lose();
					}else{
						removeEnemyBullet(eb.get(i));
						game.getHealthBar().setHealth(h);
						game.getPlayer().hurt();
					}
					Glide.s_hurt.play();
				}else{
					removeEnemyBullet(eb.get(i));
				}
			}
			
				if(eb.get(i).getY() > (Glide.HEIGHT * Glide.SCALE))
					removeEnemyBullet(eb.get(i));
				
			}catch(Exception ex){
				
			}
		}	try{
		for(int i = 0; i < e.size(); i++){
			e.get(i).tick();
			if(e.get(i).getY() > (Glide.HEIGHT * Glide.SCALE)){
				if(e.get(i).isBomb){
					//The bomb reached bottom of screen
					e.get(i).blowUpBomb();
					game.lose();
					
				}else{
					removeEnemy(e.get(i));
				}
			}
			for(int i2 = 0; i2 < b.size(); i2++){
				if(Bounds.intersectsWith(e.get(i), b.get(i2))){
					if(!e.get(i).isDead()){
						if(e.get(i).lives == 1){
							if(e.get(i).isBomb){
								game.boc ++;
								if(game.boc == 10){
									game.win();
								}
							}
							e.get(i).die();
						}else{
							e.get(i).lives --;
						}
						removeBullet(b.get(i2));
						Glide.s_explosion.play();
					}
				}
			}
			for(int i3 = 0; i3 < mdb.size(); i3 ++){
				if(Bounds.intersectsWith(e.get(i), mdb.get(i3))){
					if(!e.get(i).isDead()){
						if(e.get(i).isBomb){
							game.boc ++;
							if(game.boc == 10){
								game.win();
							}
						}
						e.get(i).die();
						///removeMDBullet(mdb.get(i3));
						Glide.s_explosion.play();
					}
				}
			}
			if(Bounds.intersectsWith(e.get(i), game.getPlayer())){
				if(!e.get(i).isDead()){
					if(!game.getPlayer().isPlasma() && !Glide.health_cheat){
						int h = (game.getHealthBar().getHealth() > 1) ? game.getHealthBar().getHealth() - 1 : 8;
						if(h == 8){
							game.lose();
						}else{
							if(e.get(i).isBomb){
									h = 8;
									game.lose();
							}else{
								if(e.get(i).isBomb){
									e.get(i).die(false);
								}else{
									removeEnemy(e.get(i));
								}
							}
							game.getHealthBar().setHealth(h);
							game.getPlayer().hurt();
						}
						Glide.s_hurt.play();
					}else{
						if(e.get(i).isBomb){
							if(e.get(i).isBomb){
								game.boc ++;
								if(game.boc == 10){
									game.win();
								}
							}
							e.get(i).die(false);
						}else{
							e.get(i).die();
							Glide.s_explosion.play();
						}
					}
				}
			}
			if(Bounds.intersectsWith(game.circle, e.get(i)) && game.isCircling()){
				if(e.get(i).isBomb){
					if(e.get(i).isBomb && !e.get(i).isDead()){
						game.boc ++;
						if(game.boc == 10){
							game.win();
						}
					}
					e.get(i).die(false);
				}else{
					e.get(i).die();
					Glide.s_explosion.play();
				}
			}
			
		}
		for(int i = 0; i < drops.size();i++){
			drops.get(i).tick();
			if(Bounds.intersectsWith(drops.get(i), game.getPlayer())){
				if(!drops.get(i).isDead()){
					if(drops.get(i).getType() == Entity.Type.HEALTHPACK){
						game.getHealthBar().setHealth(game.getHealthBar().getHealth() + 1);
					}else if(drops.get(i).getType() == Entity.Type.BEAM){
						game.getPlayer().setBeaming(true);
					}else if(drops.get(i).getType() == Entity.Type.DIAMOND){
						game.setScore(game.getScore() + 15);
					}else if(drops.get(i).getType() == Entity.Type.DIAMOND2){
						game.getHealthBar().setHealth(8);
					}else if(drops.get(i).getType() == Entity.Type.DIAMOND3){
						game.mdbs = 5;
					}else if(drops.get(i).getType() == Entity.Type.PLASMA){
						game.getPlayer().setPlasma(true);
						game.plasma = true;
					}else if(drops.get(i).getType() == Entity.Type.MDB){
						if(game.mdbs < 5){
							game.mdbs++;
						}
					}else if(drops.get(i).getType() == Entity.Type.COD){
						if(game.cods < game.max_cods){
							game.cods ++;
						}
					}
					removeDrop(drops.get(i));
					Glide.s_pickup.play();
				}
			}
		}
		}catch(Exception e){
			
		}
		for(int i = 0; i < meteors.size(); i++){
			meteors.get(i).tick();
			if(meteors.get(i).getY() > (Glide.HEIGHT * Glide.SCALE)){
				removeMeteor(meteors.get(i));
			}
			try{
			if(Bounds.intersectsWith(meteors.get(i), game.getPlayer())){
				removeMeteor(meteors.get(i));
				if(!game.getPlayer().isPlasma() && !Glide.health_cheat){
					int h = (game.getHealthBar().getHealth() > 1) ? game.getHealthBar().getHealth() - 1 : 8;
					if(h == 8){
						game.lose();
					}else{
						game.getHealthBar().setHealth(h);
						game.getPlayer().hurt();
						Glide.s_hurt.play();
					}
				}else{
					Glide.s_explosion.play();
				}
			}
			
			for(int i2 = 0;i2 < meteors.size(); i2++){
				if(!meteors.get(i2).equals(meteors.get(i))){
					if(Bounds.intersectsWith(meteors.get(i2), meteors.get(i))){
						meteors.get(i).die(true);
						meteors.get(i2).die(true);
						Glide.s_explosion.play();
					}
				}
			}
			
			for(int i3 = 0;i3 < e.size(); i3++){
				if(Bounds.intersectsWith(e.get(i3), meteors.get(i))){
					meteors.get(i).die(true);
					if(!e.get(i3).isProtector()){
						e.get(i3).die();
					}
				}
			}
			
			}catch(Exception e){}
			try{
			for(int i2 = 0; i2 < b.size(); i2++){
				
					if(Bounds.intersectsWith(meteors.get(i), b.get(i2))){
					
						removeBullet(b.get(i2));
						meteors.get(i).die(false);
						Glide.s_explosion.play();
					
					}
			}
			}catch(Exception e){}
			try{
			for(int i3 = 0; i3 < mdb.size(); i3 ++){
				
					if(Bounds.intersectsWith(meteors.get(i), mdb.get(i3))){
						
							meteors.get(i).die(false);
							Glide.s_explosion.play();
					
					}
			}
			}catch(Exception e){}
			if(Bounds.intersectsWith(game.circle, meteors.get(i)) && game.isCircling()){
				meteors.get(i).die(false);
			}
			
		}
		for(int i = 0; i < small_meteors.size(); i++){
			small_meteors.get(i).tick();
			if(small_meteors.get(i).getY() > (Glide.HEIGHT * Glide.SCALE))
				removeSmallMeteor(small_meteors.get(i));
			if(Bounds.intersectsWith(small_meteors.get(i), game.getPlayer())){
				removeSmallMeteor(small_meteors.get(i));
				if(!game.getPlayer().isPlasma()){
					int h = (game.getHealthBar().getHealth() > 1) ? game.getHealthBar().getHealth() - 1 : 8;
					if(h == 8){
						game.lose();
					}else{
						game.getHealthBar().setHealth(h);
						game.getPlayer().hurt();
						Glide.s_hurt.play();
					}
				}else{
					Glide.s_explosion.play();
				}
			}
			
			for(int i2 = 0; i2 < b.size(); i2++){
				try{
					if(Bounds.intersectsWith(small_meteors.get(i), b.get(i2))){
					
						removeSmallMeteor(small_meteors.get(i));
						Glide.s_explosion.play();
					
					}
				}catch(Exception e){}
			}
			for(int i3 = 0; i3 < mdb.size(); i3 ++){
				try{
					if(Bounds.intersectsWith(small_meteors.get(i), mdb.get(i3))){
					
							removeSmallMeteor(small_meteors.get(i));
							Glide.s_explosion.play();
					
					}
				}catch(Exception e){}
			}
			if(Bounds.intersectsWith(game.circle, small_meteors.get(i)) && game.isCircling()){
				removeSmallMeteor(small_meteors.get(i));
				Glide.s_explosion.play();
			}
		}
	}catch(Exception e){}
	}
	public void spawnEnemy(){
		r = new Random();
		Random r2 = new Random();
		addEnemy(new Enemy(r.nextInt(Glide.WIDTH * Glide.SCALE), -5, game, r2.nextBoolean(), false, false, ProtectorType.None));
	}
	public void spawnBomb(){
		/* Bomb */
		addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98, game, false, true, false, ProtectorType.None));
		
		/* Protectors */
		if(Glide.difficulty == Difficulty.Normal){
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68, game, false, false, true, ProtectorType.Normal));
		}else if(Glide.difficulty == Difficulty.Hard){
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68 + 34, game, false, false, true, ProtectorType.Hard));
		}else if(Glide.difficulty == Difficulty.Expert){
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34, game, false, false, true, ProtectorType.Normal));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68, game, false, false, true, ProtectorType.Normal));
			
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 72, -98 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 72, -98 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 + 36, -98 + 34 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32 - 36, -98 + 34 + 34, game, false, false, true, ProtectorType.Hard));
			addEnemy(new Enemy((Glide.WIDTH * Glide.SCALE) / 2 - 32, -98 + 68 + 34, game, false, false, true, ProtectorType.Hard));
		}
		
	}
	public void spawnDrop(double x, double y, Entity.Type type){
		addDrop(new Drop(x, y, game, type));
	}
	public void spawnMeteor(){
		r = new Random();
		addMeteor(new Meteor(r.nextInt(Glide.WIDTH * Glide.SCALE), -5, game));
	}
	
	public void shootMDB(double x, double y){
		MultiDirectionalBullet m = new MultiDirectionalBullet(x, y, this.game, 1);
		MultiDirectionalBullet m1 = new MultiDirectionalBullet(x, y, this.game, 2);
		MultiDirectionalBullet m2 = new MultiDirectionalBullet(x, y, this.game, 3);
		MultiDirectionalBullet m3 = new MultiDirectionalBullet(x, y, this.game, 4);
		MultiDirectionalBullet m4 = new MultiDirectionalBullet(x, y, this.game, 5);
		MultiDirectionalBullet m5 = new MultiDirectionalBullet(x, y, this.game, 6);
		MultiDirectionalBullet m6 = new MultiDirectionalBullet(x, y, this.game, 7);
		MultiDirectionalBullet m7 = new MultiDirectionalBullet(x, y, this.game, 8);
		addMDBullet(m);
		addMDBullet(m1);
		addMDBullet(m2);
		addMDBullet(m3);
		addMDBullet(m4);
		addMDBullet(m5);
		addMDBullet(m6);
		addMDBullet(m7);
	}
	
	
	
	public void render(Graphics g){
		for(int i = 0; i < b.size(); i++){
			try{
				b.get(i).render(g);
			}catch(Exception e){
				
			}
		}
		for(int i = 0; i < meteors.size(); i++){
			try{
				meteors.get(i).render(g);
			}catch(Exception e){
				
			}
		}
		for(int i = 0; i < small_meteors.size(); i++){
			try{
				small_meteors.get(i).render(g);
			}catch(Exception e){
				
			}
		}
		for(int i = 0; i < e.size(); i++){
			try{
				e.get(i).render(g);
			}catch(Exception e){
				
			}
		}
		for(int i = 0; i < drops.size(); i++){
			try{
				drops.get(i).render(g);
			}catch(Exception e){
				
			}
		}
		for(int i = 0; i < eb.size(); i++){
			try{
				eb.get(i).render(g);
			}catch(Exception e){
				
			}
		}
		for(int i = 0; i < mdb.size(); i++){
			try{
				mdb.get(i).render(g);
			}catch(Exception e){
				
			}
		}
		
	}
	
	public void addBullet(Bullet block){
		b.add(block);
	}
	public void removeBullet(Bullet block){
		b.remove(block);
	}
	public void addMDBullet(MultiDirectionalBullet block){
		mdb.add(block);
	}
	public void removeMDBullet(MultiDirectionalBullet block){
		mdb.remove(block);
	}
	public void addEnemyBullet(EnemyBullet enb){
		eb.add(enb);
	}
	public void removeEnemyBullet(EnemyBullet enb){
		eb.remove(enb);
	}
	
	public void addEnemy(Enemy block){
		e.add(block);
	}
	public void removeEnemy(Enemy block){
		e.remove(block);
	}
	
	public void addDrop(Drop block){
		drops.add(block);
	}
	public void removeDrop(Drop block){
		drops.remove(block);
	}
	
	public void addMeteor(Meteor m){
		meteors.add(m);
	}
	
	public void removeMeteor(Meteor m){
		meteors.remove(m);
	}
	
	public void addSmallMeteor(SmallMeteor m){
		small_meteors.add(m);
	}
	
	public void removeSmallMeteor(SmallMeteor m){
		small_meteors.remove(m);
	}
	
	public void removeAll(){
		while(e.size() > 0)
			e.remove();
		while(b.size() > 0)
			b.remove();
		while(drops.size() > 0)
			drops.remove();
		while(eb.size() > 0)
			eb.remove();
		while (mdb.size() > 0)
			mdb.remove();
		while(meteors.size() > 0)
			meteors.remove();
		while(small_meteors.size() > 0)
			small_meteors.remove();
	}
}
