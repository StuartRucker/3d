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
 

public class GameStateManager{
	public static int PAUSE = 0;
	public static int TITLE = 1;
	public static int PLAY = 2;
	static ArrayList<ScreenObj> r;
	static Player player;
	static float mxspd = -.005f;
	static int width = 800;
	static int height = 600;
	
	//title
	static Texture background;
	static boolean hasTexture;
	

	public static int state = GameStateManager.PLAY;
	
	public static void init(){
	  //loop setup
	  r = new ArrayList<ScreenObj>();
	  Platform floor = new Platform(450,450,0,1000,1000,10);
      floor.setColor(1, 0, 1);
      floor.enableCheckers(true);
      floor.setColor2(1, 0, 0);
      r.add(floor);
      Maze s = new Maze(30,30);
      r.add(new MazeObj(s));
      player = new Player(0,0,20,0,0,0);

      //title setup
		try {
			background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/background.png"));
			hasTexture = true;
		} catch (Exception e) {
			hasTexture = false;
			//e.printStackTrace();
		}

	}

	public static void manage(){
		if(state == PLAY){
			playLoop();
		}else if(state == TITLE){
			titleLoop();
		}
	}
	public static void setState(int a){
		state = a;
		if(a == PLAY){
			  GL11.glMatrixMode(GL11.GL_PROJECTION);
		      GL11.glLoadIdentity();
		      GL11.glEnable(GL11.GL_DEPTH_TEST);
		      GLU.gluPerspective(60,((float)width/(float)height),1,1500);
		      GL11.glMatrixMode(GL11.GL_MODELVIEW);
     		  Mouse.setGrabbed(true);
		}else if(a == TITLE){
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 800, 0, 600, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			Mouse.setGrabbed(false);
		}
	}
	public static void playLoop(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		GL11.glLoadIdentity();
		player.look();
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			player.move(Player.LEFT);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			player.move(Player.RIGHT);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			player.move(Player.BACK);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			player.move(Player.FORWARD);
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				switch (Keyboard.getEventKey()) {
				case (Keyboard.KEY_SPACE):
					player.jump();
				}
			}
		}
		
		player.rotate((float) (Mouse.getDX())*mxspd);
		for(ScreenObj a: r){
			a.draw();
		}
		
		//handle playerPhysics
		player.physics(r);

	}
	public static void titleLoop(){
		
		// Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
 
 
	    
	    if(!hasTexture){
			glColor3f(1,1,1);
		}
		if(hasTexture){
			glColor3f(1f,1f,1f);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, background.getTextureID());
		}

			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0);
				GL11.glVertex2f(0,height);
				GL11.glTexCoord2f(1,0);
				GL11.glVertex2f(width,height);
				GL11.glTexCoord2f(1,1);
				GL11.glVertex2f(width,0);
				GL11.glTexCoord2f(0,1);
				GL11.glVertex2f(0,0);
			GL11.glEnd();

	    while (Keyboard.next()) {
		    if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() == Keyboard.KEY_P) {
		        	setState(PLAY);
		        }
		    }
    	}
	}
}
