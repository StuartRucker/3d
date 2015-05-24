import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;


public class Wall extends ScreenObj {
	public float height;
	public  float[] start = new float[2];
	public float[] end = new float[2];
	public float vertices[][] = new float[4][2];
	public float color[] = new float[3];
	public Texture texture;
	public boolean hasTexture;
	public final float WIDTH = 5;

	public Wall(float x1, float y1, float x2, float y2, float h, Texture tex) {

		if (x1 == x2) { //extends in y direction
			vertices[0][0] = x1 + WIDTH / 2;
			vertices[0][1] = y1;
			vertices[1][0] = x1 - WIDTH / 2;
			vertices[1][1] = y1;
			vertices[2][0] = x2 + WIDTH / 2;
			vertices[2][1] = y2;
			vertices[3][0] = x2 - WIDTH / 2;
			vertices[3][1] = y2;
		} else {
			vertices[0][0] = x1;
			vertices[0][1] = y1 + WIDTH / 2;
			vertices[1][0] = x1;
			vertices[1][1] = y1 - WIDTH / 2;
			vertices[2][0] = x2;
			vertices[2][1] = y2 + WIDTH / 2;
			vertices[3][0] = x2;
			vertices[3][1] = y2 - WIDTH / 2;
		}
		height = h;
		texture = tex;
		hasTexture = true;
		start[0] = x1;
		start[1] = y1;
		end[0] = x2;
		end[1] = y2;
	}

	public Wall(float x1, float y1, float x2, float y2, float h) {
		if (x1 == x2) { //extends in y direction
			vertices[0][0] = x1 + WIDTH / 2;
			vertices[0][1] = y1;
			vertices[1][0] = x1 - WIDTH / 2;
			vertices[1][1] = y1;
			vertices[2][0] = x2 + WIDTH / 2;
			vertices[2][1] = y2;
			vertices[3][0] = x2 - WIDTH / 2;
			vertices[3][1] = y2;
		} else {
			vertices[0][0] = x1;
			vertices[0][1] = y1 + WIDTH / 2;
			vertices[1][0] = x1;
			vertices[1][1] = y1 - WIDTH / 2;
			vertices[2][0] = x2;
			vertices[2][1] = y2 + WIDTH / 2;
			vertices[3][0] = x2;
			vertices[3][1] = y2 - WIDTH / 2;
		}
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

		
		return false;
	}
	public void horCollision(float[] coord, float[] velocity, float[] dimensions) {
		//check if in z range
		if (coord[2] >= 0 && coord[2] - dimensions[2] <= height ) {
			//check for wall which extends in y direction
			if (start[1] != end[1]) {

				//check for collisions on primary face
				if (coord[1] >= start[1] && coord[1] <= end[1]) {
					if (coord[0] < start[0] - WIDTH / 2 && coord[0] + velocity[0] + dimensions[0] / 2 + WIDTH / 2 > start[0]) {
						velocity[0] = 0;
					}
					if (coord[0] > start[0] + WIDTH / 2 && coord[0] + velocity[0] - dimensions[0] / 2  - WIDTH / 2 < start[0]) {
						velocity[0] = 0;
					}
				}

				//check for collision on small face
				if (Math.abs(coord[0] - start[0]) <= WIDTH / 2 + .1) { //in x range
					if (coord[1] < start[1] && coord[1] + velocity[1] + dimensions[1] / 2  > start[1]) {
						velocity[1] = 0;
					}
					if (coord[1] > end[1] && coord[1] + velocity[1] - dimensions[1] / 2  < end[1]) {
						velocity[1] = 0;
					}
				}

				//ToDo

				//check for diagonal entry
				float xNext = coord[0] + velocity[0];
				float yNext = coord[1] + velocity[1];
				//if(Math.abs(coord[0] - start[0]) > WIDTH/2 && ((xNext)||()) ){
				//}
			}

			//check for wall which extends in x direction
			else {

				//check for collisions on primary face
				if (coord[0] >= start[0] && coord[0] <= end[0]) {
					if (coord[1] < start[1] - WIDTH / 2 && coord[1] + velocity[1] + dimensions[1] / 2 + WIDTH / 2 > start[1]) {
						velocity[1] = 0;
					}
					if (coord[1] > start[1] + WIDTH / 2 && coord[1] + velocity[1] - dimensions[1] / 2 - WIDTH / 2 < start[1]) {
						velocity[1] = 0;
					}
				}

				//check for collision on small face
				if (Math.abs(coord[1] - start[1]) <= WIDTH / 2 + .1) { //in x range
					if (coord[0] < start[0] && coord[0] + velocity[0] + dimensions[0] / 2  > start[0]) {
						velocity[0] = 0;
					}
					if (coord[0] > end[0] && coord[0] + velocity[0] - dimensions[0] / 2  < end[0]) {
						velocity[0] = 0;
					}
				}
			}
		}



	}
	public float dist(float x1, float y1, float x2, float y2){
		return (float) Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
	}
}

