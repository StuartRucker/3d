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
 
public class WalkAround {
	final int width = 800;
	final int height = 600;//if you change this, also change code in 
	

	
	ArrayList<ScreenObj> r = new ArrayList<ScreenObj>();
	

	public void start() {
        try {
	    Display.setDisplayMode(new DisplayMode(width,height));
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
 
      
 	GameStateManager.init();
 	GameStateManager.setState(GameStateManager.TITLE);
	while (!Display.isCloseRequested()) {
		GameStateManager.manage();		
		
	    Display.update();
	    Display.sync(40);
	}
 
	Display.destroy();
	}
	

	public static void drawCube(int p1, int p2){
		glColor3f(1f,1f,1f);
	    int vert = 10;
		glBegin(GL_QUADS);
	    	glVertex3f(p1,p1,vert);
	    	glVertex3f(p1,p2,vert);
	    	glVertex3f(p1,p2,p2-p1 + vert);
	    	glVertex3f(p1,p1,p2-p1 + vert);
	    glEnd();
	    
	    glColor3f(1f,0f,0f);
	    glBegin(GL_QUADS);
	    	glVertex3f(p2,p1,vert);
	    	glVertex3f(p2,p2,vert);
	    	glVertex3f(p2,p2,p2-p1 + vert);
	    	glVertex3f(p2,p1,p2-p1 + vert);
    	glEnd();
    	
    	glColor3f(0f,1f,0f);
	    glBegin(GL_QUADS);
	    	glVertex3f(p2,p2,vert);
	    	glVertex3f(p2,p1,vert);
	    	glVertex3f(p1,p1,vert);
	    	glVertex3f(p1,p2,vert);
    	glEnd();
    	
    	glColor3f(0f,0f,1f);
	    glBegin(GL_QUADS);
	    	glVertex3f(p2,p2,p2-p1+vert);
	    	glVertex3f(p2,p1,p2-p1+vert);
	    	glVertex3f(p1,p1,p2-p1+vert);
	    	glVertex3f(p1,p2,p2-p1+vert);
    	glEnd();
    	
    	glColor3f(1f,1f,0f);
	    glBegin(GL_QUADS);
	    	glVertex3f(p1,p1,vert);
	    	glVertex3f(p2,p1,vert);
	    	glVertex3f(p2,p1,p2-p1+vert);
	    	glVertex3f(p1,p1,p2-p1+vert);
    	glEnd();
    	
    	glColor3f(0f,1f,1f);
	    glBegin(GL_QUADS);
	    	glVertex3f(p1,p2,vert);
	    	glVertex3f(p2,p2,vert);
	    	glVertex3f(p2,p2,p2-p1+vert);
	    	glVertex3f(p1,p2,p2-p1+vert);
    	glEnd();
	}
	


    public static void main(String[] argv) {
        WalkAround asd = new WalkAround();
        asd.start();
    }
    
}

