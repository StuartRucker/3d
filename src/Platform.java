import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.opengl.GL11;


public class Platform extends ScreenObj{
	
	float w;
	float l;
	float h;
	float colR,colG,colB,colR2,colG2,colB2;
	boolean checkers = false;
	int checkerWidth;
	
	public Platform(float x, float y, float z, float Width, float Length, float height){
		coordinates[0] = x;
		coordinates[1] = y;
		coordinates[2] = z;
		w = Width;
		l = Length;
		colR = 1;
		colG = 1;
		colB = 0;
		this.h = height;
	}
	public void draw(){
		if(!checkers){
			glColor3f(colR,colG,colB);
			GL11.glBegin(GL11.GL_QUADS);
				glVertex3f(coordinates[0]+ w/2,coordinates[1] + l/2,coordinates[2] + h/2);
			 	glVertex3f(coordinates[0]- w/2,coordinates[1] + l/2,coordinates[2] + h/2);				 	
			 	glVertex3f(coordinates[0]- w/2,coordinates[1] - l/2,coordinates[2] + h/2);
			 	glVertex3f(coordinates[0]+ w/2,coordinates[1] - l/2,coordinates[2] + h/2);
			GL11.glEnd();
		}
		if(checkers){
			boolean lastColor = true;
			for(float i = coordinates[0] - w/2; i <= coordinates[0] + w/2; i += 10 ){
				for(float j = coordinates[1] - l/2; j <= coordinates[1] + l/2; j += 10 ){
					if(lastColor) glColor3f(colR,colG,colB);
					else glColor3f(colR2,colG2,colB2);
					lastColor = !lastColor;
					GL11.glBegin(GL11.GL_QUADS);
						glVertex3f(i,j,coordinates[2] + h/2);
						glVertex3f(i+10,j,coordinates[2]  + h/2);
						glVertex3f(i+10,j+10,coordinates[2]  + h/2);
						glVertex3f(i,j+10,coordinates[2]  + h/2);
				 	GL11.glEnd();
				 	GL11.glBegin(GL11.GL_QUADS);
						glVertex3f(i,j,coordinates[2] - h/2);
						glVertex3f(i+10,j,coordinates[2]  - h/2);
						glVertex3f(i+10,j+10,coordinates[2]  - h/2);
						glVertex3f(i,j+10,coordinates[2]  - h/2);
				 	GL11.glEnd();
				}
			}
				lastColor= true;
				for(float i = coordinates[0] - w/2; i <= coordinates[0] + w/2; i += 10 ){
				
					if(lastColor) glColor3f(colR,colG,colB);
					else glColor3f(colR2,colG2,colB2);
					lastColor = !lastColor;
					GL11.glBegin(GL11.GL_QUADS);
						glVertex3f(i,coordinates[1] + l/2,coordinates[2] + h/2);
						glVertex3f(i+10,coordinates[1] + l/2,coordinates[2] + h/2);
						glVertex3f(i+10,coordinates[1] + l/2,coordinates[2] - h/2);
						glVertex3f(i,coordinates[1] + l/2,coordinates[2] - h/2);
					GL11.glEnd();
					GL11.glBegin(GL11.GL_QUADS);
						glVertex3f(i,coordinates[1] - l/2,coordinates[2] + h/2);
						glVertex3f(i+10,coordinates[1] - l/2,coordinates[2] + h/2);
						glVertex3f(i+10,coordinates[1] - l/2,coordinates[2] - h/2);
						glVertex3f(i,coordinates[1] - l/2,coordinates[2] - h/2);
					GL11.glEnd();
				}
				for(float i = coordinates[1] - l/2; i <= coordinates[1] + l/2; i += 10 ){
					
					if(lastColor) glColor3f(colR,colG,colB);
					else glColor3f(colR2,colG2,colB2);
					lastColor = !lastColor;
					GL11.glBegin(GL11.GL_QUADS);
						glVertex3f(coordinates[0] + w/2,i,coordinates[2]  + h/2);
						glVertex3f(coordinates[0] + w/2,i+ 10,coordinates[2]  + h/2);
						glVertex3f(coordinates[0] + w/2,i+ 10,coordinates[2] - h/2);
						glVertex3f(coordinates[0] + w/2,i,coordinates[2] - h/2);
					GL11.glEnd();
					GL11.glBegin(GL11.GL_QUADS);
					glVertex3f(coordinates[0] - w/2,i,coordinates[2] + h/2);
					glVertex3f(coordinates[0] - w/2,i+ 10,coordinates[2] + h/2);
					glVertex3f(coordinates[0] - w/2,i+ 10,coordinates[2] - h/2);
					glVertex3f(coordinates[0] - w/2,i,coordinates[2] - h/2);
				GL11.glEnd();
				
			}
		}
		
		
	}
	public boolean isZCollision(float [] coord, float[] velocity, float dimensions[]) {
		if(coord[0] <= coordinates[0] + w/2 && coord[0] >= coordinates[0] - w/2 && coord[1] <= coordinates[1] + l/2 && coord[1] >= coordinates[1] - l/2){
			if(coord[2] >= this.coordinates[2] && coord[2] + velocity[2] <= this.coordinates[2]){
				return true;
			}
			if(coord[2] + dimensions[2] <= this.coordinates[2] && coord[2] + velocity[2] + dimensions[2] >= this.coordinates[2]){
				return true;
			}
		}
		return false;
	}
	public void setColor(int a, int b, int c){
		colR = a;
		colG = b;
		colB = c;
	}
	public void setColor2(int a, int b, int c){
		colR2 = a;
		colG2 = b;
		colB2 = c;
	}
	public int mod(int a, int b){
		while(a < 0) a += b;
		return a % b;
	}
	
	public void enableCheckers(boolean s){
		checkers = s;
	}
	@Override
	public void horCollision(float[] coord, float[] velocity, float[] dimensions) {
		if(coord[2] < coordinates[2] + h/2 && coord[2] + dimensions[2] > coordinates[2] - h/2 ){
			//check in range
			if(coord[0] <= coordinates[0] + w/2 && coord[0] >= coordinates[0] - w/2 && coord[1] <= coordinates[1] + l/2 && coord[1] >= coordinates[1] - l/2){
				//positive x direction
				if(coord[0] + dimensions[0]/2 < coordinates[0]-w/2 && coord[0] + dimensions[0]/2 + velocity[0] > coordinates[0]-w/2){
					velocity[0] = 0;
				}
				//negative x direction
				if(coord[0] - dimensions[0]/2 > coordinates[0]-w/2 && coord[0] - dimensions[0]/2 + velocity[0] < coordinates[0]-w/2){
					velocity[1] = 0;
				}
				//positive y direction
				if(coord[1] + dimensions[1]/2 < coordinates[1]-l/2 && coord[1] + dimensions[1]/2 + velocity[1] > coordinates[1]-l/2){
					velocity[1] = 0;
				}
				//negative y direction
				if(coord[1] - dimensions[1]/2 > coordinates[1]-l/2 && coord[1] - dimensions[1]/2 + velocity[1] < coordinates[1]-l/2){
					velocity[1] = 0;
				}
			}
		}
		
	}
	
	
}
