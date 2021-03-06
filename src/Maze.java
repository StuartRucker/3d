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
import java.io.IOException;

public class Maze {
	public static final boolean WALL = false;
	public static final boolean VISITED = true;

	private int h, w;
	private boolean[][] cells;
	private boolean[][] horiz;
	private boolean[][] verti;
	private boolean startOpening, endOpening; //true denotes it is vertical


	public Maze(int width, int height) {
		//Creates maze with width w and height
		h = height;
		w = width;
		cells = new boolean[h][w];
		horiz = new boolean[h + 1][w];
		verti = new boolean[h][w + 1];
		generateMaze();
		addOpenings();
	}

	private void generateMaze() {
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

	private void addOpenings() {
		/*if (horiz[1][0] == !WALL) {
			horiz[0][0] = !WALL;
			startOpening = false;
		} else {
			verti[0][0] = !WALL;
			startOpening = true;
		}*/
		if (horiz[h - 1][w - 1] == !WALL) {
			horiz[h][w - 1] = !WALL;
			endOpening = false;
		} else {
			verti[h - 1][w] = !WALL;
			endOpening = true;
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

	public int getListSize() {
		return 2 * h * w + w + h;
	}
	public boolean getOpenings(boolean startOrEnd){
		if(startOrEnd){
			return startOpening;
		}else{
			return endOpening;
		}
	}

	/*public static void main(String[] args) {
		Maze m = new Maze(100,100);
		System.out.println(m);
	}*/

}
