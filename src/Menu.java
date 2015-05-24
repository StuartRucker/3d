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


public class Menu{
	public static Texture pause;
	public static Texture menu;
	public static int PAUSE = 21;
	public static int TITLE = 22;
	public static int PLAY_AGAIN = 23;
	public final int BAR_HEIGHT  = 70;
	public final int BAR_WIDTH = 300;
	public final int BUFFER = 150;
	public int width = WalkAround.width;
	public int height = WalkAround.height;
	public GamePlay gamePlay;
	public int lastLoop;

	float definingVerts[][] = new float[4][4];
	public Menu(GamePlay g){
		gamePlay= g;
		try{
			//pause = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/pause.png"));
			//menu = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/menu.png"));
		}catch(Exception e){

		}
	}
	public void drawPause(){
		glColor3f(1f, 1f, 1f);
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, menu.getTextureID());
		

		float top = height-200;
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top-BAR_HEIGHT);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top-BAR_HEIGHT);
		GL11.glEnd();
		definingVerts[0][0] = width/2-BAR_WIDTH/2;
		definingVerts[0][1] = top-BAR_HEIGHT;
		definingVerts[0][2] = width/2+BAR_WIDTH/2;
		definingVerts[0][3] = top+BAR_HEIGHT;


		float top2 = top - BAR_HEIGHT-BUFFER;
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top2);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top2);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top2-BAR_HEIGHT);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top2-BAR_HEIGHT);
		GL11.glEnd();
		definingVerts[1][0] = width/2-BAR_WIDTH/2;
		definingVerts[1][1] = top2-BAR_HEIGHT;
		definingVerts[1][2] = width/2+BAR_WIDTH/2;
		definingVerts[1][3] = top2+BAR_HEIGHT;

		lastLoop = GameStateManager.PAUSE;
	}
	public void drawCountDown(){
		//draw number of seconds left
		int secondsLeft = gamePlay.timeLeft();
		glColor3f(1f/(float)secondsLeft, 1f, 1f);

		

		float top = height-200;
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top-BAR_HEIGHT);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top-BAR_HEIGHT);
		GL11.glEnd();
		lastLoop = GameStateManager.COUNT_DOWN;
	}
	public void drawWin(){
		//"try again on same maze" button
		float w = 500;
		float h = 200;
		float top = height/2+h;
		glColor3f(0f, 0f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-w/2, top);
			GL11.glVertex2f(width/2+w/2, top);
			GL11.glVertex2f(width/2+w/2, top-h);
			GL11.glVertex2f(width/2-w/2, top-h);
		GL11.glEnd();
		definingVerts[2][0] = width/2-w/2;
		definingVerts[2][1] = top-h;
		definingVerts[2][2] = width/2+w/2;
		definingVerts[2][3] = top;

		
		w -= 200;
		h = 150;
		top = top-2*h;
		glColor3f(0f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-w/2, top);
			GL11.glVertex2f(width/2+w/2, top);
			GL11.glVertex2f(width/2+w/2, top-h);
			GL11.glVertex2f(width/2-w/2, top-h);
		GL11.glEnd();
		definingVerts[3][0] = width/2-w/2;
		definingVerts[3][1] = top-h;
		definingVerts[3][2] = width/2+w/2;
		definingVerts[3][3] = top;

		lastLoop = GameStateManager.WIN;
	}
	public int getMouseClicked(float x, float y){
		if(lastLoop == GameStateManager.PAUSE){
			if(x > definingVerts[0][0] && y > definingVerts[0][1] && x < definingVerts[0][2] && y < definingVerts[0][3]){
				return PAUSE;
			}
			if(x > definingVerts[1][0] && y > definingVerts[1][1] && x < definingVerts[1][2] && y < definingVerts[1][3]){
				return TITLE;
			}
		}else if(lastLoop == GameStateManager.WIN){
			if(x > definingVerts[2][0] && y > definingVerts[2][1] && x < definingVerts[2][2] && y < definingVerts[2][3]){
				return PLAY_AGAIN;
			}
			if(x > definingVerts[3][0] && y > definingVerts[3][1] && x < definingVerts[3][2] && y < definingVerts[3][3]){
				return TITLE;
			}
		}
		return -1;
	}
}	