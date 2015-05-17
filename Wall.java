import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.opengl.GL11;


public class Wall extends ScreenObj {
	float height;
	float[] start = new float[2];
	float[] end = new float[2];
	float width;
	float color[] = new float[3];
	
	public Wall(float x1, float y1, float x2, float y2, float h){
		start[0] = x1;
		start[1] = y1;
		end[0] = x2;
		end[1] = y2;
		height = h;
		for(int i = 0; i < 3; i ++){
			color[i] = (float) (Math.random());
		}
		
	}
	public void draw() {
		glColor3f(color[0],color[1],color[2]);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(start[0],start[1],0);
			GL11.glVertex3f(end[0],end[1],0);
			GL11.glVertex3f(end[0],end[1],height);
			GL11.glVertex3f(start[0],start[1],height);
		GL11.glEnd();
		
	}
	public boolean isZCollision(float[] coord, float[] velocity,
			float[] dimensions) {
		// TODO Auto-generated method stub
		return false;
	}
	public void horCollision(float[] coord, float[] velocity, float[] dimensions) {
		//check if in z range
		if(coord[2] >= 0 && coord[2] - dimensions[2] <= height ){
			//check for x collis
			if(start[1] != end[1]){
				if(coord[1] >= start[1] && coord[1] <= end[1]){
					if(coord[0] < start[0] && coord[0]+velocity[0]+dimensions[0]/2 > start[0]){
						velocity[0] = 0;
						return;
					}
					if(coord[0] > start[0] && coord[0]+velocity[0]- dimensions[0]/2 < start[0]){
						velocity[0] = 0;
						return;
					}
				}
			}
			
			//check for y collis
			else{
				if(coord[0] >= start[0] && coord[0] <= end[0]){
					if(coord[1] < start[1] && coord[1]+velocity[1]+dimensions[1]/2 > start[1]){
						velocity[1] = 0;
						return;
					}
					if(coord[1] > start[1] && coord[1]+velocity[1]- dimensions[1]/2 < start[1]){
						velocity[1] = 0;
						return;
					}
				}
			}
		}
		
		
		
	}
}

