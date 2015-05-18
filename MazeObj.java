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

	public MazeObj(Maze m) {

		try {
			texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("/maze.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
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
					list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH, c * WIDTH + WIDTH, HEIGHT, texture));
				}
			}
		}
		for (int r = 0; r < verti.length; r ++) {
			for (int c = 0; c < verti[0].length; c ++) {
				if (verti[r][c] == Maze.WALL) {
					list.add(new Wall(r * WIDTH, c * WIDTH, r * WIDTH + WIDTH, c * WIDTH, HEIGHT, texture));
				}
			}
		}
	}
	public void draw() {

	}
	public boolean isZCollision(float[] coord, float velocity[], float dimensions[]) {

	}
	public void horCollision(float[] coord, float velocity[], float dimensions[]) {
		//Get coordinates and only check if wall exists around it
	}
}