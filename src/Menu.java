import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.Font;
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
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;


//draws stuff directly to screen
public class Menu{
	//contstants for what button was clicked
	public static int PAUSE = 21;
	public static int TITLE = 22;
	public static int PLAY_AGAIN = 23;
	
	//animation constants
	public final int BAR_HEIGHT  = 70;
	public final int BAR_WIDTH = 300;
	public final int BUFFER = 150;
	
	//screen coordinates
	public int width = WalkAround.width;
	public int height = WalkAround.height;
	
	//needs these objects to display the info
	public GamePlay gamePlay;
	public int lastLoop;
	public PowerUpManager powerup;

	//fonts
	Font awtfont = new Font("Calibri", Font.BOLD, 36);
	TrueTypeFont font = new TrueTypeFont(awtfont, true);
	int fontheight = font.getHeight();
	int fontwidthresume = font.getWidth("Resume");
	int fontwidthmenu = font.getWidth("Menu");
	int fontwidthtryagain = font.getWidth("Try Again on Same Maze");
	int fontwidthbacktomenu = font.getWidth("Back to Menu");
	int fontwidthTime = font.getWidth("Your Time: 3");
	
	
	
	//the 2 vertices of 4 different buttons (used for detecting clicking)
	float definingVerts[][] = new float[4][4];
	
	public Menu(GamePlay g, PowerUpManager p){
		gamePlay= g;
		powerup = p;
	}

	//draw the pause menu (i.e. when you press escape)
	public void drawPause(){
		glColor3f(1f, 1f, 1f);
		
		//menu button
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		
		
		float top = height-200;
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top-BAR_HEIGHT);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top-BAR_HEIGHT);
		GL11.glEnd();
		definingVerts[1][0] = width/2-BAR_WIDTH/2;
		definingVerts[1][1] = height-top;
		definingVerts[1][2] = width/2+BAR_WIDTH/2;
		definingVerts[1][3] = height - (top-BAR_HEIGHT);
		
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		TextureImpl.bindNone();
		
		font.drawString(width/2-fontwidthmenu/2, (top - BAR_HEIGHT/2 - fontheight/2), "Menu", Color.black);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		
		//resume button
		float top2 = top - BAR_HEIGHT-BUFFER;
		glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top2);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top2);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top2-BAR_HEIGHT);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top2-BAR_HEIGHT);
		GL11.glEnd();
		definingVerts[0][0] = width/2-BAR_WIDTH/2;
		definingVerts[0][1] = height-top2;
		definingVerts[0][2] = width/2+BAR_WIDTH/2;
		definingVerts[0][3] = height-(top2-BAR_HEIGHT);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		TextureImpl.bindNone();
		
		font.drawString(width/2-fontwidthresume/2, (top2 - BAR_HEIGHT/2 - fontheight/2), "Resume", Color.black);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		lastLoop = GameStateManager.PAUSE;
	}
	
	//counts down the seconds before you begin
	public void drawCountDown(){
		//draw number of seconds left
		Integer secondsLeft = gamePlay.timeLeft();
		if(secondsLeft <0) secondsLeft = 0;
		glColor3f(.13f*(6f - (float)secondsLeft), 1f, 1f);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		
		
		//should display the seconds left
		float top = height/2+BAR_HEIGHT/2;
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top);
			GL11.glVertex2f(width/2+BAR_WIDTH/2, top-BAR_HEIGHT);
			GL11.glVertex2f(width/2-BAR_WIDTH/2, top-BAR_HEIGHT);
		GL11.glEnd();
		lastLoop = GameStateManager.COUNT_DOWN;
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		TextureImpl.bindNone();
		
		font.drawString(width/2-font.getWidth(secondsLeft.toString())/2, (top - BAR_HEIGHT/2 - fontheight/2), secondsLeft.toString(), Color.black);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		
	}
	
	//draw the menu that appears when you win
	public void drawWin(){
		//"try again on same maze" button
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		
		float w = 500;
		float h = 200;
		float top = height/2-.25f*h;
		glColor3f(0f, 0f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-w/2, top);
			GL11.glVertex2f(width/2+w/2, top);
			GL11.glVertex2f(width/2+w/2, top-h);
			GL11.glVertex2f(width/2-w/2, top-h);
		GL11.glEnd();
		definingVerts[2][0] = width/2-w/2;
		definingVerts[2][1] = height-top;
		definingVerts[2][2] = width/2+w/2;
		definingVerts[2][3] = height-(top-h);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		TextureImpl.bindNone();
		
		String str = "Your Time: " + gamePlay.getWinTime();
		font.drawString(width/2-fontwidthTime/2, (top - h/2 - 3*fontheight/2), str, Color.black);
		font.drawString(width/2-fontwidthtryagain/2, (top - h/2 - fontheight/2), "Try Again on Same Maze", Color.black);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		
		//back to menu
		w -= 200;
		h = 150;
		top = height/2 + .5f*h;
		glColor3f(0f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width/2-w/2, top);
			GL11.glVertex2f(width/2+w/2, top);
			GL11.glVertex2f(width/2+w/2, top+h);
			GL11.glVertex2f(width/2-w/2, top+h);
		GL11.glEnd();
		definingVerts[3][0] = width/2-w/2;
		definingVerts[3][1] = height-(top+h);
		definingVerts[3][2] = width/2+w/2;
		definingVerts[3][3] = height-top;
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		TextureImpl.bindNone();
		
		font.drawString(width/2-fontwidthbacktomenu/2, (top + h/2 - fontheight/2), "Back to Menu", Color.black);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		lastLoop = GameStateManager.WIN;
	}
	public void drawPlay(){
		Integer secondsTaken = gamePlay.getGameTime();
		//display the time is is taking you to solve the maze in a corner of screen
		float w = 80;
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		glColor3f(1f, 0f, 0f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(w, 0);
			GL11.glVertex2f(w, w);
			GL11.glVertex2f(0, w);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		TextureImpl.bindNone();
		
		font.drawString(w/2-font.getWidth(secondsTaken.toString())/2, (w/2 - fontheight/2), secondsTaken.toString(), Color.black);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	//returns what button was clicked
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
	
	//draws the green power up bar indicating the amount of time left
	public void drawPowerUp	(){
		float timeLeft = powerup.getTimeLeft();
		if(timeLeft < 1750) glColor3f(1f, 0f, 0f);
		else glColor3f(0f, 1f, 0f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0, height);
			GL11.glVertex2f(0, height- BAR_HEIGHT);
			GL11.glVertex2f(timeLeft/15, height- BAR_HEIGHT);
			GL11.glVertex2f(timeLeft/15, height);
		GL11.glEnd();
	}
}	
