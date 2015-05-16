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

public class Maze {
	static public final boolean WALL = false;
	static public final boolean VISITED = true;

	private int h, w;
	private boolean[][] cells;
	private boolean[][] horiz;
	private boolean[][] verti;

	public Maze(int width, int height) {
		h = height;
		w = width;
		cells = new boolean[h][w];
		horiz = new boolean[h + 1][w];
		verti = new boolean[h][w + 1];
		generateMaze();
	}

	public void generateMaze() {
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
		int x = (int) p.getX();
		int y = (int) p.getY();
		if (x < 0 || x >= w || y < 0 || y >= h) return false;
		return !(horiz[y][x] || horiz[y + 1][x] || verti[y][x] || verti[y][x + 1]);
	}

	private void knock(Point current, Point random) {
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

	public void print() {
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				System.out.print(" ");
				if (horiz[j][i] == WALL) System.out.print("-");
				else System.out.print(" ");
			}
			System.out.println();
			for (int i = 0; i <= w; i++) {
				if (verti[j][i] == WALL) System.out.print("|");
				else System.out.print(" ");
				System.out.print(" ");
			}
			System.out.println();
		}
		for (int i = 0; i < w; i++) {
			System.out.print(" ");
			if (horiz[h][i] == WALL) System.out.print("-");
			else System.out.print(" ");
		}
		System.out.println();
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

	/*
	public static void main(String[] args) {
		Maze m = new Maze(8,6);
		m.print();
	}
	*/
}