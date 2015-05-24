import java.util.ArrayList;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MazeObj extends ScreenObj {

	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;

	Maze m;
	ArrayList<Wall> list;
	static public Texture texture;
	static public Texture end;
	boolean hasTexture;
	WinWall winWall;

	public MazeObj(Maze m) {

		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/texture.png"));
			end = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/end.png"));
			hasTexture = true;
		} catch (Exception e) {
			hasTexture = false;
			//e.printStackTrace();
		}

		this.m = m;
		list = new ArrayList<Wall>();
		generate();
	}

	private void generate() {
		boolean[][] horiz = m.getHoriz();
		boolean[][] verti = m.getVerti();
		for (int r = 0; r < horiz.length; r ++) {
			for (int c = 0; c < horiz[0].length; c ++) {
				if (horiz[r][c] == Maze.WALL) {
					if (hasTexture) {
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH, c * WIDTH + WIDTH, HEIGHT, texture));
					} else {
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH, c * WIDTH + WIDTH, HEIGHT));
					}
				}
			}
		}
		for (int r = 0; r < verti.length; r ++) {
			for (int c = 0; c < verti[0].length; c ++) {
				if (verti[r][c] == Maze.WALL) {
					if (hasTexture) {
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH + WIDTH, c * WIDTH, HEIGHT, texture));
					} else {
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH + WIDTH, c * WIDTH, HEIGHT));
					}
				}
			}
		}
		if(m.getOpenings(false) == true){
			int r = verti.length-1;
			int c = verti[0].length-1;
			winWall = new WinWall(r * WIDTH, c * WIDTH, r * WIDTH + WIDTH, c * WIDTH, HEIGHT*2,end);
			list.add((Wall)winWall);
		}else{
			int r = horiz.length-1;
			int c = horiz[0].length-1;
			winWall = new WinWall(r * WIDTH, c * WIDTH, r * WIDTH, c * WIDTH + WIDTH, HEIGHT*2, end);
			list.add((Wall)winWall);
		}
	}

	public void draw() {
		for (Wall w : list) {
			w.draw();
		}
	}

	public boolean isZCollision(float[] coord, float velocity[], float dimensions[]) {
		return false;
	}

	public void horCollision(float[] coord, float velocity[], float dimensions[]) {
		//Get coordinates and only check if wall exists around it
		//check if in z range
		if (coord[2] >= 0 && coord[2] - dimensions[2] <= HEIGHT ) {
			//check for x collis
			int xpos = (int) coord[0] / WIDTH;
			int ypos = (int) coord[1] / HEIGHT;

			int wallsAroundx[] = { -1, 0, 1};
			int wallsAroundy[]  = { -1, 0, 1};
			for (int i = 0; i < wallsAroundy.length; i ++) {
				for (int a = 0; a < wallsAroundx.length; a ++) {
					if (((xpos + wallsAroundx[i]) < m.getHoriz().length && (xpos + wallsAroundx[i]) >= 0 && (ypos + wallsAroundy[a]) >= 0 && (ypos + wallsAroundy[a]) < m.getHoriz()[0].length )) {
						if (m.getHoriz()[(xpos + wallsAroundx[i])][(ypos + wallsAroundy[a])] == Maze.WALL) {
							Wall checkMe = new Wall((xpos + wallsAroundx[i]) * WIDTH, (ypos + wallsAroundy[a]) * WIDTH,
							                        (xpos + wallsAroundx[i]) * WIDTH, (ypos + wallsAroundy[a]) * WIDTH + WIDTH, HEIGHT);
							checkMe.horCollision(coord, velocity, dimensions);
						}
					}
					if (((xpos + wallsAroundx[i]) < m.getVerti().length && (xpos + wallsAroundx[i]) >= 0 && (ypos + wallsAroundy[a]) >= 0 && (ypos + wallsAroundy[a]) < m.getVerti()[0].length )) {
						if (m.getVerti()[(xpos + wallsAroundx[i])][(ypos + wallsAroundy[a])] == Maze.WALL) {
							Wall checkMe = new Wall((xpos + wallsAroundx[i]) * WIDTH, (ypos + wallsAroundy[a]) * WIDTH,
							                        (xpos + wallsAroundx[i]) * WIDTH + WIDTH, (ypos + wallsAroundy[a]) * WIDTH, HEIGHT);
							checkMe.horCollision(coord, velocity, dimensions);
						}
					}
				}
			}

		}
		winWall.horCollision(coord,velocity,dimensions);
	}
}


