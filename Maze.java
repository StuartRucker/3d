/*
cells stores cells with visited status
horiz stores horizontal walls (parallel to x-axis)
verti stores vertical walls (parallel to y-axis)
 001122334455
0 - - - - - - 
0| |   |     |
1         -   
1| | |   |   |
2     - -   - 
2|   |   | | |
3   -   -     
3|     |   | |
4 - -     -   
4| |   |   | |
5     - -     
6|   |   | | |
6   -     -   
7|   | |     |
7 -   - -     
8|         | |
8 - - - - - - 
To get appropriate coordinate, for walls or cells, see example above.
*/

import java.util.Stack;
import java.util.ArrayList;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Maze1 {
	static public final boolean WALL = false;
	static public final boolean VISITED = true;
	
	public final float WIDTH = 30;
	public final float HEIGHT = 30; 

	private int h, w;
	private boolean[][] cells;
	private boolean[][] horiz;
	private boolean[][] verti;
	
	public Texture texture = null;
	
	try {
		texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("/maze.jpg"));
	}
	catch (IOException e) {
		e.printStackTrace();
	}

	public Maze1(int width, int height) {
		//Creates maze with width w and height h
		h = height;
		w = width;
		cells = new boolean[h][w];
		horiz = new boolean[h + 1][w];
		verti = new boolean[h][w + 1];
		generateMaze();
	}

	public void generateMaze() {
		//Generates the maze using DFS
		Stack<Point> s = new Stack<Point>();
		int visited = 0;
		int total = w * h;
		Point none = new Point(-1, -1);
		Point current = new Point((int) (Math.random() * w), (int) (Math.random() * h));
		while (visited < total - 1) {
			Point random = getRandomNeighbor(current);
			if (random.equals(none)) {
				current = s.pop();
			} else {
				knock(current, random);
				s.push(current);
				current = random;
				visited++;
			}
		}
	}

	private Point getRandomNeighbor(Point p) {
		//Returns a random neighbor that is "intact," and -1,-1 if none exist
		ArrayList<Point> list = new ArrayList<Point>(4);
		Point tmp;
		int x = (int) p.getX();
		int y = (int) p.getY();
		tmp = new Point(x - 1, y);
		if (isIntact(tmp)) list.add(tmp);
		tmp = new Point(x + 1, y);
		if (isIntact(tmp)) list.add(tmp);
		tmp = new Point(x, y - 1);
		if (isIntact(tmp)) list.add(tmp);
		tmp = new Point(x, y + 1);
		if (isIntact(tmp)) list.add(tmp);

		if (list.size() == 0) return new Point(-1, -1);
		return list.get((int) (Math.random() * list.size()));
	}

	private boolean isIntact(Point p) {
		//Returns true if intact, false if out of bounds or non intact
		int x = (int) p.getX();
		int y = (int) p.getY();
		if (x < 0 || x >= w || y < 0 || y >= h) return false;
		return !(horiz[y][x] || horiz[y + 1][x] || verti[y][x] || verti[y][x + 1]);
	}

	private void knock(Point current, Point random) {
		//Knocks down wall between current and random, and marks current as visited
		int xc = (int) current.getX();
		int yc = (int) current.getY();
		int xr = (int) random.getX();
		int yr = (int) random.getY();
		if (xr == xc - 1) verti[yc][xc] = !WALL;
		else if (xr == xc + 1) verti[yc][xc + 1] = !WALL;
		else if (yr == yc - 1) horiz[yc][xc] = !WALL;
		else if (yr == yc + 1) horiz[yc + 1][xc] = !WALL;
		cells[yc][xc] = VISITED;
	}

	public String toString() {
		//Returns string of ASCII representation of maze (see header)
		String rtn = "";
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				rtn += " ";
				if (horiz[j][i] == WALL) rtn += "-";
				else rtn += " ";
			}
			rtn += "\n";
			for (int i = 0; i <= w; i++) {
				if (verti[j][i] == WALL) rtn += "|";
				else rtn += " ";
				rtn += " ";
			}
			rtn += "\n";
		}
		for (int i = 0; i < w; i++) {
			rtn += " ";
			if (horiz[h][i] == WALL) rtn += "-";
			else rtn += " ";
		}
		rtn += "\n";
		return rtn;
	}

	public boolean[][] getCells() {
		return cells;
	}

	public boolean[][] getHoriz() {
		return horiz;
	}

	public boolean[][] getVerti() {
		return verti;
	}
	public ArrayList<ScreenObj> getObj(){
		ArrayList<ScreenObj> newWalls = new ArrayList<ScreenObj>();
		for(int r = 0; r < horiz.length; r ++){
			  for(int c = 0; c < horiz[0].length; c ++){
				   if(horiz[r][c]){
					  newWalls.add(new Wall(r*WIDTH, c*WIDTH,r*WIDTH,c*WIDTH + WIDTH,HEIGHT, texture));
				   }
			   }
		 }
		for(int r = 0; r < verti.length; r ++){
			  for(int c = 0; c < verti[0].length; c ++){
				   if(verti[r][c]){
					  newWalls.add(new Wall(r*WIDTH, c*WIDTH,r*WIDTH + WIDTH,c*WIDTH,HEIGHT, texture));
				   }
			   }
		 }
		return newWalls;
	}
	
	
	/*public static void main(String[] args) {
		Maze m = new Maze(100,100);
		System.out.println(m);
	}*/
	

}
