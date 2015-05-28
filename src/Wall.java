import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;


public class Wall extends ScreenObj {
	public float height;
	public  float[] start = new float[2];
	public float[] end = new float[2];
	public float color[] = new float[3];
	public Texture texture;
	public boolean hasTexture;
	public final float WIDTH = 5;
	
	//used for collision detection
	public float bufD = 5;//distance from wall
	public float segments[][] = new float[4][4];
	public float vertices[][] = new float[4][2];

public Wall(float xa1, float ya1, float xa2, float ya2, float h, Texture tex) {
		float x1 = (float)Math.min(xa1,xa2);
		float x2 = (float)Math.max(xa1,xa2);
		float y1 = (float)Math.min(ya1,ya2);
		float y2 = (float)Math.max(ya1,ya2);
		if (x1 == x2) { //extends in y direction
			vertices[0][0] = x1 - WIDTH / 2;
			vertices[0][1] = y1;
			vertices[1][0] = x1 + WIDTH / 2;
			vertices[1][1] = y1;
			vertices[2][0] = x2 - WIDTH / 2;
			vertices[2][1] = y2;
			vertices[3][0] = x2 + WIDTH / 2;
			vertices[3][1] = y2;
		} else {
			vertices[0][0] = x1;
			vertices[0][1] = y1 - WIDTH / 2;
			vertices[1][0] = x2;
			vertices[1][1] = y2 - WIDTH / 2;
			vertices[2][0] = x1;
			vertices[2][1] = y1 + WIDTH / 2;
			vertices[3][0] = x2;
			vertices[3][1] = y2 + WIDTH / 2;	
		}
		generateSegs();
		height = h;
		texture = tex;
		hasTexture = true;
		start[0] = x1;
		start[1] = y1;
		end[0] = x2;
		end[1] = y2;
	}

	public Wall(float xa1, float ya1, float xa2, float ya2, float h) {
		float x1 = (float)Math.min(xa1,xa2);
		float x2 = (float)Math.max(xa1,xa2);
		float y1 = (float)Math.min(ya1,ya2);
		float y2 = (float)Math.max(ya1,ya2);
		if (x1 == x2) { //extends in y direction
			vertices[0][0] = x1 - WIDTH / 2;
			vertices[0][1] = y1;
			vertices[1][0] = x1 + WIDTH / 2;
			vertices[1][1] = y1;
			vertices[2][0] = x2 - WIDTH / 2;
			vertices[2][1] = y2;
			vertices[3][0] = x2 + WIDTH / 2;
			vertices[3][1] = y2;
		} else {
			vertices[0][0] = x1;
			vertices[0][1] = y1 - WIDTH / 2;
			vertices[1][0] = x2;
			vertices[1][1] = y2 - WIDTH / 2;
			vertices[2][0] = x1;
			vertices[2][1] = y1 + WIDTH / 2;
			vertices[3][0] = x2;
			vertices[3][1] = y2 + WIDTH / 2;
		}
		generateSegs();
		start[0] = x1;
		start[1] = y1;
		end[0] = x2;
		end[1] = y2;
		height = h;
		for (int i = 0; i < 3; i ++) {
			color[i] = (float) (Math.random());
		}
		hasTexture = false;
		for (int i = 0; i < 3; i ++) {
			color[i] = (float) (Math.random());
		}
	}
	public void draw() {

		if (!hasTexture) {
			glColor3f(color[0], color[1], color[2]);
		}
		if (hasTexture) {
			glColor3f(1f, 1f, 1f);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		}
		//indicates which vertices sets of vertices form a wall
		int a[][] = {{0, 1}, {0, 2}, {3, 1}, {3, 2}};

		for (int i = 0; i < a.length; i ++) {
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(vertices[a[i][0]][0], vertices[a[i][0]][1], 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(vertices[a[i][1]][0], vertices[a[i][1]][1], 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(vertices[a[i][1]][0], vertices[a[i][1]][1], height);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(vertices[a[i][0]][0], vertices[a[i][0]][1], height);
			GL11.glEnd();
		}

		//draw roof
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(vertices[0][0], vertices[0][1], height);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(vertices[1][0], vertices[1][1], height);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(vertices[3][0], vertices[3][1], height);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(vertices[2][0], vertices[2][1], height);
		GL11.glEnd();



		if (hasTexture) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}

	}
	public boolean isZCollision(float[] coord, float[] velocity,
	                            float[] dimensions) {

			if(coord[0] + dimensions[0]/2 >= vertices[0][0] && coord[0] - dimensions[0]/2 <= vertices[1][0]){//x range
				if(coord[1] - dimensions[1]/2 <= vertices[2][1] && coord[1] + dimensions[1]/2 >= vertices[0][1]){//y range
					if(coord[2] > height && height >= coord[2]  + velocity[2]){ //its going down, I'm yelling thunder
						return true;
					}
				}
			}
		return false;
	}
	public void horCollision(float[] coord, float[] velocity, float[] dimensions) {
		//check if in z range
		if (coord[2] >= 0 && coord[2] - dimensions[2] <= height ) {
			//check for collision on segment 0
			float t = (segments[0][0]-coord[0])/velocity[0];
			if(t > 0 && t <= 1){ //intersecting segment
				if(velocity[0] > 0){ //entering wall
					float nextY = coord[1] + velocity[1]*t;
					if(nextY <= vertices[2][1] + bufD && nextY >= vertices[0][1] - bufD){//collision happens on segment
						velocity[0] = 0;
					}
				}
			}

			//check for collision on segment 1
			t = (segments[1][0]-coord[0])/velocity[0];
			if(t > 0 && t <= 1){ //intersecting segment
				if(velocity[0] < 0){ //entering wall
					float nextY = coord[1] + velocity[1]*t;
					if(nextY <= vertices[3][1] + bufD && nextY >= vertices[1][1] - bufD){//collision happens on segment
						velocity[0] = 0;
					}
				}
			}

			//check for collision on segment 2
			t = (segments[2][1]-coord[1])/velocity[1];
			if(t > 0 && t <= 1){ //intersecting segment
				if(velocity[1] > 0){ //entering wall
					float nextX = coord[0] + velocity[0]*t;
					if(nextX <= vertices[1][0] +bufD && nextX >= vertices[0][0] - bufD){//collision happens on segment
						velocity[1] = 0;
					}
				}
			}

			//check for collision on segment 3
			t = (segments[3][1]-coord[1])/velocity[1];
			if(t > 0 && t <= 1){ //intersecting segment
				if(velocity[1] < 0){ //entering wall
					float nextX = coord[0] + velocity[0]*t;
					if(nextX <= vertices[3][0] + bufD && nextX >= vertices[2][0]-bufD){//collision happens on segment
						velocity[1] = 0;
					}
				}
			}

			/*
			//intersection of circle and line 
			//approximate t
			float closestDist = Float.MAX_VALUE;
			for(float tApprox = 0; tApprox <= 1; tApprox += .05f){
				float d = dist(coord[0] + velocity[0] * tApprox, coord[1] + velocity[1] * tApprox, vertices[0][0],vertices[0][1]);
				if(Math.abs(d - bufD) < closestDist){
					//right quadrant
					if(coord[0] + velocity[0] * tApprox < vertices[0][0] && coord[1] + velocity[1] * tApprox < vertices[0][1]){
						t = tApprox;
						closestDist = Math.abs(d - bufD);
					}
				}
			}
			float d = dist(coord[0] + velocity[0] * t, coord[1] + velocity[1] * t, vertices[0][0],vertices[0][1]);
			float error = 1f;
			if(Math.abs(d-bufD)< error){
				float x = coord[0] + velocity[0] * t;
				float y = coord[1] + velocity[1] * t;
			}*/
		}	
			
	}
	public float dist(float x1, float y1, float x2, float y2){
		return (float) Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
	}
	private void generateSegs(){
		segments[0][0] = vertices[0][0]-   bufD;
		segments[0][1] = vertices[0][1];
		segments[0][2] = vertices[2][0]-   bufD;
		segments[0][3] = vertices[2][1];
		segments[1][0] = vertices[1][0]+   bufD;
		segments[1][1] = vertices[1][1];
		segments[1][2] = vertices[3][0]+   bufD;
		segments[1][3] = vertices[3][1];
		segments[2][0] = vertices[0][0];
		segments[2][1] = vertices[0][1]-   bufD;
		segments[2][2] = vertices[1][0];
		segments[2][3] = vertices[1][1]-   bufD;
		segments[3][0] = vertices[2][0];
		segments[3][1] = vertices[2][1]+   bufD;
		segments[3][2] = vertices[3][0];
		segments[3][3] = vertices[3][1]+   bufD;
	}   
}

