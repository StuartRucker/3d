import java.util.*;

public class PowerUpManager extends ScreenObj{
	private Player player;
	private Maze maze;
	private final double frequency = .05f; // X/1 of squares have tiles
	
	//time that power up lasts
	private long started;
	private long duration = 5000;
	private long end;
	
	//list of all power ups
	ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	
	public PowerUpManager(Player p, Maze s){
		started = System.currentTimeMillis();
		end = started;
		player = p;
		maze = s;
		generateObjs();
	}
	
	//populates array list of powerups
	public void generateObjs(){
		powerups = new ArrayList<PowerUp>();
		for(int r = 0; r < maze.getHoriz().length-1; r ++){
			for(int c = 0; c < maze.getHoriz()[0].length; c ++){
				if(Math.random() <= frequency && (r != 0 || c != 0)){
					//create new object
					powerups.add(new PowerUp((int)(15 + r * MazeObj.WIDTH),(int) (15 + c* MazeObj.HEIGHT)));
				}
			}
		}
		powerups.add(new PowerUp(15,15));
	}
	
	//draws all power ups
	public  void draw(){
		for(PowerUp p: powerups){
			p.draw();
		}
	}
	
	//this method is only here because it extends screen obj
	public boolean isZCollision(float[] coord, float velocity[], float dimensions[]){return false;}
	
	//check to see if any powerups have been collided with, and the activate the power up
	public void horCollision(float[] coord, float velocity[], float dimensions[]){
		for(PowerUp p: powerups){
			if(p.isCollision(coord,velocity,dimensions)){
				player.removePowerUp();
				player.setPower(p.getPowerUp());
				startClock();
				p.use();
			}
		}
	}
	
	//the next three manage the amount of time that the power up lasts
	private void startClock(){
		started = System.currentTimeMillis();
		end = started + duration;
	}
	public float getTimeLeft(){
		return (float)((end -System.currentTimeMillis()));
	}
	public void update(){
		if(getTimeLeft() < 0){
			player.removePowerUp();
		}
	}
}