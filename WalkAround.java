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
	final int height = 600;
	float mxspd = -.005f;

	
	ArrayList<ScreenObj> r = new ArrayList<ScreenObj>();
	

	public void start() {
        try {
	    Display.setDisplayMode(new DisplayMode(width,height));
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
 
      GL11.glMatrixMode(GL11.GL_PROJECTION);
      GL11.glLoadIdentity();
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      GLU.gluPerspective(60,((float)width/(float)height),1,1500);
      GL11.glMatrixMode(GL11.GL_MODELVIEW);
      
      Mouse.setGrabbed(true);
      
      
      
      //initialize objects
      Platform floor = new Platform(450,450,0,1000,1000,10);
      floor.setColor(1, 0, 1);
      floor.enableCheckers(true);
      floor.setColor2(1, 0, 0);
      r.add(floor);
      
      Maze s = new Maze(30,30);
      	for(ScreenObj a:s.getObj()){
      		r.add(a);
      	}
      /*
      for(int i = 0; i < 6; i ++){
    	  floor = new Platform(0,100 + i*100,15 + i*10,100,100,10);
          floor.setColor(0, 0, 1);
          floor.enableCheckers(true);
          floor.setColor2(1, 1, 1);
          r.add(floor);
      }*/
      

      Player player = new Player(0,0,20,0,0,0);
      
     
 
 
	while (!Display.isCloseRequested()) {
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

