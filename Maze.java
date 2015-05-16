import java.util.Stack;
import java.util.ArrayList;
import java.awt.Point;

public class Maze {
	public static boolean WALL = false;
	public static boolean VISITED = true;

	private int h, w;
	private boolean[][] cells;
	private boolean[][] horiz;
	private boolean[][] verti;

	public Maze(int width, int height) {
		h = height;
		w = width;
		cells = new boolean[h][w];
		horiz = new boolean[h + 1][w];
		verti = new boolean[h][h + 1];
		generateMaze();
	}

	public void generateMaze() {
		Stack<Point> s = new Stack<Point>();
		int visited = 0;
		int total = w * h;
		Point none = new Point(-1,-1);
		Point current = new Point((int) (Math.random() * h), (int) (Math.random() * w));
		while (visited < total) {
			Point random = getRandomNeighbor(current);
			System.out.print(visited + " ");
			if (random.equals(none)) {
				System.out.print("pop ");
				current = s.pop();
			} else {
				knock(current, random);
				s.push(current);
				System.out.print(""+current.getX()+" "+current.getY());
				current = random;
				visited++;
			}
			System.out.println();
		}
	}

	private Point getRandomNeighbor(Point p) {
		ArrayList<Point> list = new ArrayList<Point>(4);
		Point tmp;
		int x = (int) p.getX();
		int y = (int) p.getY();
		if (isIntact(tmp = new Point(x-1,y))) list.add(tmp);
		if (isIntact(tmp = new Point(x+1,y))) list.add(tmp);
		if (isIntact(tmp = new Point(x,y-1))) list.add(tmp);
		if (isIntact(tmp = new Point(x,y+1))) list.add(tmp);

		if (list.size() == 0) return new Point(-1,-1);
		return list.get((int) Math.random() * list.size());
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
		if (xr == xc-1) horiz[yc][xc] = !WALL;
		else if (xr == xc+1) horiz[yc][xc+1] = !WALL;
		else if (yr == yc-1) verti[yc][xc] = !WALL;
		else if (yr == yc+1) verti[yc+1][xc] = !WALL;
		cells[yc][xc] = VISITED;
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Maze m = new Maze(1000, 900);
		long endTime = System.nanoTime();

		System.out.println((endTime - startTime) / 1000000000f);
	}
}