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

public class PowerUp{
	public static int JUMP = 0;
	public static int HEIGHT = 1;
	public static int SPEED = 2;

	boolean used = false;
	int p1= -5,p2=5,vert=5;
	float x;
	float y;
	int type;

	int rotDeg = 0;
	public PowerUp(int x, int y){
		this.x = x;
		this.y = y;
		type = (int)(Math.random()*3);
	}
	public  void draw(){
		if(!used){
			GL11.glPushMatrix();
			GL11.glTranslatef(x,y,0);
			GL11.glRotatef(2*(rotDeg++), 0, 0, 1);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			if(type == JUMP){
				glColor3f(0f, 1f, 1f);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.jump.getTextureID());
			}
			else if(type == HEIGHT){
				glColor3f(1f, 0f, 1f);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.height.getTextureID());
			}
			else if(type == SPEED) {
				glColor3f(1f, 1f, 0f);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.speed.getTextureID());
			}

			drawCube();
			GL11.glPopMatrix();
		}
		
	
	}
	private void drawCube(){

		GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0, 1);
	    	GL11.glVertex3f(p1,p1,vert);
	    	GL11.glTexCoord2f(1, 1);
	    	GL11.glVertex3f(p1,p2,vert);
	    	GL11.glTexCoord2f(1, 0);
	    	GL11.glVertex3f(p1,p2,p2-p1 + vert);
	    	GL11.glTexCoord2f(0, 0);
	    	GL11.glVertex3f(p1,p1,p2-p1 + vert);
	    GL11.glEnd();
	    
	    GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0, 1);
	    	GL11.glVertex3f(p2,p1,vert);
	    	GL11.glTexCoord2f(1, 1);
	    	GL11.glVertex3f(p2,p2,vert);
	    	GL11.glTexCoord2f(1, 0);
	    	GL11.glVertex3f(p2,p2,p2-p1 + vert);
	    	GL11.glTexCoord2f(0, 0);
	    	GL11.glVertex3f(p2,p1,p2-p1 + vert);
    	GL11.glEnd();
    	
	    GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0, 1);
	    	GL11.glVertex3f(p2,p2,vert);
	    	GL11.glTexCoord2f(1, 1);
	    	GL11.glVertex3f(p2,p1,vert);
	    	GL11.glTexCoord2f(1, 0);
	    	GL11.glVertex3f(p1,p1,vert);
	    	GL11.glTexCoord2f(0, 0);
	    	GL11.glVertex3f(p1,p2,vert);
    	GL11.glEnd();

	    GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0, 1);
	    	GL11.glVertex3f(p2,p2,p2-p1+vert);
	    	GL11.glTexCoord2f(1, 1);
	    	GL11.glVertex3f(p2,p1,p2-p1+vert);
	    	GL11.glTexCoord2f(1, 0);
	    	GL11.glVertex3f(p1,p1,p2-p1+vert);
	    	GL11.glTexCoord2f(0, 0);
	    	GL11.glVertex3f(p1,p2,p2-p1+vert);
    	GL11.glEnd();
    	
	    GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0, 1);
	    	GL11.glVertex3f(p1,p1,vert);
	    	GL11.glTexCoord2f(1, 1);
	    	GL11.glVertex3f(p2,p1,vert);
	    	GL11.glTexCoord2f(1, 0);
	    	GL11.glVertex3f(p2,p1,p2-p1+vert);
	    	GL11.glTexCoord2f(0, 0);
	    	GL11.glVertex3f(p1,p1,p2-p1+vert);
    	GL11.glEnd();
    	
	    GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0, 1);
	    	GL11.glVertex3f(p1,p2,vert);
	    	GL11.glTexCoord2f(1, 1);
	    	GL11.glVertex3f(p2,p2,vert);
	    	GL11.glTexCoord2f(1, 0);
	    	GL11.glVertex3f(p2,p2,p2-p1+vert);
	    	GL11.glTexCoord2f(0, 0);
	    	GL11.glVertex3f(p1,p2,p2-p1+vert);
    	GL11.glEnd();
	}
	public  boolean isCollision(float[] coord, float velocity[], float dimensions[]){
		if(used) return false;
		float radius = (float)Math.sqrt(p1*p1+p2*p2+vert*vert);
		float dist = (float)Math.sqrt(
			Math.pow((coord[0]-x),2)+
			Math.pow((coord[1]-y),2)+
			Math.pow((coord[2]-vert),2)
			);
		if(dist <= radius) return true;
		return false;
	}
	public void use(){
		
		used = true;
	}
	public int getPowerUp(){
		return type; 
	}
}