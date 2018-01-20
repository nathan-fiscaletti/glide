package glide.game.entities;

import glide.engine.Entity;
import glide.engine.Screen;
import glide.engine.Vector;
import glide.game.Glide;
import glide.game.Glide.Difficulty;

public class SmallMeteor extends Entity
{

	int speed = 1 + (int)(Math.random()*2);
	int xVelocityMultiplier = (random.nextBoolean()) ? -1 : 1;
	int meteorType = 1;
	
	int changeDirectionTick = 0;
	
	public SmallMeteor(Vector position, Screen screen) {
		super(position, screen);
		this.meteorType = random.nextInt(7);
		this.playDeathAnimation = true;
		
		switch (meteorType) {
			case 1 : this.renderedSprite = Entity.getTextures().smallmeteor1; break;
			case 2 : this.renderedSprite = Entity.getTextures().smallmeteor2; break;
			case 3 : this.renderedSprite = Entity.getTextures().smallmeteor3; break;
			case 4 : this.renderedSprite = Entity.getTextures().smallmeteor4; break;
			case 5 : this.renderedSprite = Entity.getTextures().smallmeteor5; break;
			case 6 : this.renderedSprite = Entity.getTextures().smallmeteor6; break;
			
			default : this.renderedSprite = Entity.getTextures().smallmeteor1;
		}
	}
	
	@Override
	public final void update()
	{
		this.position = this.position.plusY(speed);
		
		if(Glide.difficulty != Difficulty.Easy){
			this.position.plusX(speed*xVelocityMultiplier);
			
			// If the difficulty is anything besides EASY, 
			// move the meteor left or right as well.
			if(Glide.difficulty != Difficulty.Easy){
				
				// Move the meteor either left or right.
				this.velocity.x = speed*xVelocityMultiplier;
				
				// If you set the difficulty to expert, the meteors
				// should change their X velocity randomly every 60 ticks.
				if (Glide.difficulty == Difficulty.Expert) {
					
					// After every 60 ticks, we need to
					// change the left or right toggle.
					if (this.changeDirectionTick == 60) {
						this.xVelocityMultiplier = (this.random.nextBoolean()) ? -1 : 1;
					}
					
					// Update how many ticks it's been
					// since we last changed the
					// left or right toggle.
					this.changeDirectionTick += (this.changeDirectionTick == 60) ? -60 : 1;
				}
			}
		}
	}

}
