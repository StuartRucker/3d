import java.util.ArrayList;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MazeObj extends ScreenObj {

	static public final int WIDTH = 30;
	static public final int HEIGHT = 30;

	Maze m;
	ArrayList<Wall> list;
	Texture texture;
	boolean hasTexture;

	public MazeObj(Maze m) {

		try {
			texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("/maze.jpg"));
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
					if(hasTexture){
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH, c * WIDTH + WIDTH, HEIGHT, texture));
					}else{
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH, c * WIDTH + WIDTH, HEIGHT));
					}
				}
			}
		}
		for (int r = 0; r < verti.length; r ++) {
			for (int c = 0; c < verti[0].length; c ++) {
				if (verti[r][c] == Maze.WALL) {
					if(hasTexture){
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH + WIDTH, c * WIDTH, HEIGHT, texture));
					}else{
						list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH + WIDTH, c * WIDTH, HEIGHT));
					}
				}
			}
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
		if(coord[2] >= 0 && coord[2] - dimensions[2] <= HEIGHT ){
			//check for x collis
			int xpos = (int) coord[0]/WIDTH;
			int ypos = (int) coord[1]/HEIGHT;


			//check for y collis
			
		}
	}
}