import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

//imports for font
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


//this determines which type of loops should be run
public class GameStateManager {
	//constants to denote different states
	public static int PAUSE = 0;
	public static int TITLE = 1;
	public static int PLAY = 2;
	public static int COUNT_DOWN = 3;
	public static int WIN = 4;
	
	//list of all objects in game
	private static ArrayList<ScreenObj> r;
	private static Player player;
	
	//for mouse input
	private static float mxspd = -.005f;
	private static float myspd = -.005f;
	private static int width = 800;
	private static int height = 600;

	//title
	private static Texture background;
	private static boolean hasTexture;
	public static GamePlay gamePlay;
	public static PowerUpManager p;
	
	static Menu menu;


	public static int state = -3;

	public static void init() {
		//loop setup
		r = new ArrayList<ScreenObj>();
		Platform floor = new Platform(450, 450, 0, 900, 900, 10);
		r.add(floor);
		Maze s = new Maze(30, 30);
		r.add(new MazeObj(s));
		player = new Player();
		p = new PowerUpManager(player, s);
		r.add(p);

		//title setup
		try {
			background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/background.png"));
			hasTexture = true;
		} catch (Exception e) {
			hasTexture = false;
			//e.printStackTrace();
		}

		gamePlay = new GamePlay();
		menu = new Menu(gamePlay,p);

	}

	//determines which loop should be executed 
	public static void manage() {
		if (state == PLAY || state == PAUSE || state == COUNT_DOWN || state == WIN) {
			playLoop();
		} else if (state == TITLE) {
			titleLoop();
		}
	}

	//set up code when the state of the game is changed
	public static void setState(int a) {
		if (a == PLAY) {
			if(state == COUNT_DOWN || state == TITLE){
				gamePlay.startGame();
			}
			if(state== PAUSE){
				gamePlay.resume();
			}
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GLU.gluPerspective(60, ((float)width / (float)height), 1, 1500);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			Mouse.setGrabbed(true);
		} else if (a == TITLE) {
			for(int i = 0; i < r.size(); i++){

				if(r.get(i) instanceof MazeObj){
					r.set(i,(ScreenObj)new MazeObj(new Maze(30,30)));
				}else if(r.get(i) instanceof PowerUpManager){
					((PowerUpManager) r.get(i)).generateObjs();
				}
			}
			player.init();
			make2D();
			Mouse.setGrabbed(false);
		} else if(a == PAUSE){
			gamePlay.pause();
			Mouse.setGrabbed(false);
		}else if(a == COUNT_DOWN){
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GLU.gluPerspective(60, ((float)width / (float)height), 1, 1500);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				player.init();
				gamePlay.startCountDown(3);
		}else if(a == WIN){
			Mouse.setGrabbed(false);
			gamePlay.end();
		}
		state = a;
	}
	
	//the game loop that executes if you are playing the game (i.e. not paused)
	public static void playLoop() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		player.look();

		if(state == PLAY){
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				player.move(Player.LEFT);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				player.move(Player.RIGHT);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				player.move(Player.BACK);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				player.move(Player.FORWARD);
			}
			player.rotatex((float) (Mouse.getDX()) * mxspd);
			player.rotatey((float) (Mouse.getDY()) * myspd);
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				switch (Keyboard.getEventKey()) {
					case (Keyboard.KEY_SPACE):
						if(state == PLAY)
							player.jump();
					break;
					case (Keyboard.KEY_ESCAPE):
						if(state == PAUSE)
							setState(GameStateManager.PLAY);
						else if(state == PLAY)
							setState(GameStateManager.PAUSE);
					break;
					default: break;
				}
			}
		}


		// TODO: Draw skybox here
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		SkyBox.drawSkybox(player.getPosX(), player.getPosY(), player.getPosZ() + player.PLAYERHEIGHT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		for (ScreenObj a : r) {
			a.draw();
		}
		
		if(state == PAUSE){//drawing title screen!
			make2D();
			menu.drawPause();
			make3D();
			if(Mouse.isButtonDown(0)){
				int constant = menu.getMouseClicked(Mouse.getX(),Mouse.getY());
				if(constant == Menu.TITLE){
					setState(TITLE);
				}else if(constant == Menu.PAUSE){
					setState(PLAY);
				}
			}
		}
		if(state == COUNT_DOWN){
			make2D();
			menu.drawCountDown();
			make3D();
			if(gamePlay.timeLeft() < 0){
				setState(PLAY);
			}
		}

		if(state == WIN){
			make2D();
			menu.drawWin();
			make3D();
			if(Mouse.isButtonDown(0)){
				int constant = menu.getMouseClicked(Mouse.getX(),Mouse.getY());
				if(constant == Menu.PLAY_AGAIN){
					for(int i = 0; i < r.size(); i++)//i++ increments i
						if(r.get(i) instanceof PowerUpManager)
							((PowerUpManager) r.get(i)).generateObjs();//coded by 420 textbook maker
					setState(COUNT_DOWN);
				}else if(constant == Menu.TITLE){
					setState(TITLE);
				}
			}
		}
		if(state == PLAY){
			//draw time left
			make2D();
			menu.drawPlay();
			menu.drawPowerUp();
			make3D();
			//handle playerPhysics
			player.physics(r);
			p.update();
		}


	}
	
	//loop executed when title is displayed
	public static void titleLoop() {

		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);



		if (!hasTexture) {
			glColor3f(1, 1, 1);
		}
		if (hasTexture) {
			glColor3f(1f, 1f, 1f);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, background.getTextureID());
		}

		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, background.getHeight());
			GL11.glVertex2f(0, height);
			GL11.glTexCoord2f(background.getWidth(), background.getHeight());
			GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(background.getWidth(), 0);
			GL11.glVertex2f(width, 0);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
		GL11.glEnd();

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_P) {
					setState(COUNT_DOWN);
				}
			}
		}
	}
	
	//these two methods sandwhich things drawn directly to the screen
	//they switch between ortho, and perspective
	protected static void make2D() {
        //Remove the Z axis
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glLoadIdentity();
    }
 
    protected static void make3D() {
        //Restore the Z axis
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
}
