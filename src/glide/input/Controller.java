package glide.input;

import glide.Game;
import glide.entities.Bullet;
import glide.entities.Drop;
import glide.entities.Enemy;
import glide.entities.Player;
import glide.spritehandles.Bounds;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;


public class Controller {
	private LinkedList<Bullet> b = new LinkedList<Bullet>();
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
		try{
		for(int i = 0; i < e.size(); i++){
			e.get(i).tick();
			if(e.get(i).getY() > (Game.HEIGHT * Game.SCALE)){
				removeEnemy(e.get(i));
			}
			for(int i2 = 0; i2 < b.size(); i2++){
				if(Bounds.intersectsWith(e.get(i), b.get(i2))){
					if(!e.get(i).isDead()){
						e.get(i).die();
						removeBullet(b.get(i2));
					}
				}
			}
			if(Bounds.intersectsWith(e.get(i), game.getPlayer())){
				if(!e.get(i).isDead()){
					int h = (game.getHealthBar().getHealth() > 1) ? game.getHealthBar().getHealth() - 1 : 3;
					if(h == 3){
						removeAll();
						game.getHealthBar().setHealth(3);
						game.setScore(0);
						double x = game.getPlayer().getX();
						double y = game.getPlayer().getX();
						game.setPlayer(new Player(x, y, game));
						game.getPlayer().setX(((Game.WIDTH * Game.SCALE) / 2) - 16);
						game.getPlayer().setY((Game.HEIGHT * Game.SCALE) - 52);
						
					}else{
						removeEnemy(e.get(i));
						game.getHealthBar().setHealth(h);
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
					removeDrop(drops.get(i));
				}
			}
		}
		}catch(Exception e){
			
		}
		
	}
	public void spawnEnemy(){
		r = new Random();
		Random r2 = new Random();
		int g = r2.nextInt(4);
		boolean doit = (g == 3) ? true : false;
		addEnemy(new Enemy(r.nextInt(Game.WIDTH * Game.SCALE), -5, game, doit));
	}
	
	public void spawnDrop(double x, double y, int type){
		addDrop(new Drop(x, y, game, type));
	}
	
	public void render(Graphics g){
		for(int i = 0; i < b.size(); i++){
			b.get(i).render(g);
		}
		for(int i = 0; i < e.size(); i++){
			e.get(i).render(g);
		}
		for(int i = 0; i < drops.size(); i++){
			drops.get(i).render(g);
		}
	}
	
	public void addBullet(Bullet block){
		b.add(block);
	}
	public void removeBullet(Bullet block){
		b.remove(block);
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
	}
}
