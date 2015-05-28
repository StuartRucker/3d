import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;

//if you collide with this wall, you Win!!!!!
public class WinWall extends Wall{
	
	public WinWall(float x1, float y1, float x2, float y2, float h, Texture tex) {
		super(x1,  y1,  x2,  y2,  h,  tex);
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
						GameStateManager.setState(GameStateManager.WIN);
					}
					if (coord[0] > start[0] + WIDTH / 2 && coord[0] + velocity[0] - dimensions[0] / 2  - WIDTH / 2 < start[0]) {
						velocity[0] = 0;
						GameStateManager.setState(GameStateManager.WIN);
					}
				}

				//check for collision on small face
				if (Math.abs(coord[0] - start[0]) <= WIDTH / 2 + .1) { //in x range
					if (coord[1] < start[1] && coord[1] + velocity[1] + dimensions[1] / 2  > start[1]) {
						velocity[1] = 0;
						GameStateManager.setState(GameStateManager.WIN);
					}
					if (coord[1] > end[1] && coord[1] + velocity[1] - dimensions[1] / 2  < end[1]) {
						velocity[1] = 0;
						GameStateManager.setState(GameStateManager.WIN);
					}
				}
			}

			//check for wall which extends in x direction
			else {

				//check for collisions on primary face
				if (coord[0] >= start[0] && coord[0] <= end[0]) {
					if (coord[1] < start[1] - WIDTH / 2 && coord[1] + velocity[1] + dimensions[1] / 2 + WIDTH / 2 > start[1]) {
						velocity[1] = 0;
						GameStateManager.setState(GameStateManager.WIN);
					}
					if (coord[1] > start[1] + WIDTH / 2 && coord[1] + velocity[1] - dimensions[1] / 2 - WIDTH / 2 < start[1]) {
						velocity[1] = 0;
						GameStateManager.setState(GameStateManager.WIN);
					}
				}

				//check for collision on small face
				if (Math.abs(coord[1] - start[1]) <= WIDTH / 2 + .1) { //in x range
					if (coord[0] < start[0] && coord[0] + velocity[0] + dimensions[0] / 2  > start[0]) {
						velocity[0] = 0;
						GameStateManager.setState(GameStateManager.WIN);
					}
					if (coord[0] > end[0] && coord[0] + velocity[0] - dimensions[0] / 2  < end[0]) {
						velocity[0] = 0;
						GameStateManager.setState(GameStateManager.WIN);
					}
				}
			}
		}



	}
}