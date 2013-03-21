package glide.input;

import glide.Game;
import glide.Glide;
import glide.entities.Bullet;
import glide.entities.Drop;
import glide.entities.Enemy;
import glide.entities.EnemyBullet;
import glide.spritehandles.Bounds;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;


public class Controller {
	private LinkedList<Bullet> b = new LinkedList<Bullet>();
	private LinkedList<EnemyBullet> eb = new LinkedList<EnemyBullet>();
	private LinkedList<Enemy> e = new LinkedList<Enemy>();
	private LinkedList<Drop> drops = new LinkedList<Drop>();
	Random r = new Random();
	Game game;
	public Controller(Game game){
		this.game = game;
	}
	
	public void tick(){
		for(int i = 0; i < b.size(); i++){
			b.get(i).tick();
			if(b.get(i).getY() < 0)
				removeBullet(b.get(i));
		}
		for(int i = 0; i < eb.size(); i++){
			try{
			eb.get(i).tick();
			if(Bounds.intersectsWith(eb.get(i), game.getPlayer())){
				if(!game.getPlayer().isPlasma()){
					int h = (game.getHealthBar().getHealth() > 1) ? game.getHealthBar().getHealth() - 1 : 5;
					if(h == 5){
						game.lose();
					}else{
						removeEnemyBullet(eb.get(i));
						game.getHealthBar().setHealth(h);
						game.getPlayer().hurt();
					}
					Glide.hurt.play();
				}else{
					removeEnemyBullet(eb.get(i));
				}
			}
			
				if(eb.get(i).getY() > (Glide.HEIGHT * Glide.SCALE))
					removeEnemyBullet(eb.get(i));
				
			}catch(Exception ex){
				
			}
		}
		try{
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
							e.get(i).die();
						}else{
							e.get(i).lives --;
						}
						removeBullet(b.get(i2));
						Glide.explosion.play();
					}
				}
			}
			if(Bounds.intersectsWith(e.get(i), game.getPlayer())){
				if(!e.get(i).isDead()){
					if(!game.getPlayer().isPlasma()){
						int h = (game.getHealthBar().getHealth() > 1) ? game.getHealthBar().getHealth() - 1 : 5;
						if(h == 5){
							game.lose();
						}else{
							if(e.get(i).isBomb){
									h = 5;
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
						Glide.hurt.play();
					}else{
						if(e.get(i).isBomb){
							e.get(i).die(false);
						}else{
							e.get(i).die();
							Glide.explosion.play();
						}
					}
				}
			}
		}
		for(int i = 0; i < drops.size();i++){
			drops.get(i).tick();
			if(Bounds.intersectsWith(drops.get(i), game.getPlayer())){
				if(!drops.get(i).isDead()){
					if(drops.get(i).getType() == Drop.TYPE_HEALTHPACK){
						game.getHealthBar().setHealth(game.getHealthBar().getHealth() + 1);
					}
					if(drops.get(i).getType() == Drop.TYPE_BEAM){
						game.getPlayer().setBeaming(true);
					}
					if(drops.get(i).getType() == Drop.TYPE_DIAMOND){
						game.setScore(game.getScore() + 15);
					}
					if(drops.get(i).getType() == Drop.TYPE_PLASMA){
						game.getPlayer().setPlasma(true);
						game.plasma = true;
					}
					removeDrop(drops.get(i));
					Glide.pickup.play();
				}
			}
		}
		}catch(Exception e){
			
		}
		
	}
	public void spawnEnemy(){
		r = new Random();
		Random r2 = new Random();
		int g = r2.nextInt(3);
		boolean doit = (g == 2) ? true : false;
		addEnemy(new Enemy(r.nextInt(Glide.WIDTH * Glide.SCALE), -5, game, doit));
	}
	
	public void spawnDrop(double x, double y, int type){
		addDrop(new Drop(x, y, game, type));
	}
	
	public void render(Graphics g){
		for(int i = 0; i < b.size(); i++){
			try{
				b.get(i).render(g);
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
	}
	
	public void addBullet(Bullet block){
		b.add(block);
	}
	public void removeBullet(Bullet block){
		b.remove(block);
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
	
	public void removeAll(){
		while(e.size() > 0)
			e.remove();
		while(b.size() > 0)
			b.remove();
		while(drops.size() > 0)
			drops.remove();
		while(eb.size() > 0){
			eb.remove();
		}
	}
}
