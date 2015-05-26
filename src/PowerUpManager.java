import java.util.*;

public class PowerUpManager extends ScreenObj{
	private Player player;
	private Maze maze;
	private final double frequency = .05; // X/1 of squares have tiles
	private long started;
	private long duration = 5000;
	private long end;
	ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	public PowerUpManager(Player p, Maze s){
		started = System.currentTimeMillis();
		end = started;
		player = p;
		maze = s;
		generateObjs();
		//why is player null?
		player.method();
	}
	private void generateObjs(){
		for(int r = 0; r < maze.getHoriz().length; r ++){
			for(int c = 0; c < maze.getHoriz()[0].length; c ++){
				if(Math.random() <= frequency){
					//create new object
					powerups.add(new PowerUp((int)(15 + r * MazeObj.WIDTH),(int) (15 + c* MazeObj.HEIGHT)));
				}
			}
		}
		powerups.add(new PowerUp(15,15));
	}
	public  void draw(){
		for(PowerUp p: powerups){
			p.draw();
		}
	}
	public boolean isZCollision(float[] coord, float velocity[], float dimensions[]){return false;}
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
	private void startClock(){
		started = System.currentTimeMillis();
		end = started + duration;
	}
	public int getTimeLeft(){
		return (int) ((end -System.currentTimeMillis())/1000);
	}
	public void update(){
		if(getTimeLeft() < 0){
			player.removePowerUp();
		}
	}
}