
public class Wall extends ScreenObj {
	float height;
	float[] start;
	float[] end;
	float width;
	
	public Wall(int x1, int y1, int x2, int y2, int h, int w){
		start[0] = x1;
		start[1] = y1;
		end[0] = x2;
		end[1] = y2;
		height = h;
		width = w;
	}
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	public boolean isZCollision(float[] coord, float[] velocity,
			float[] dimensions) {
		// TODO Auto-generated method stub
		return false;
	}
	public void horCollision(float[] coord, float[] velocity, float[] dimensions) {
		// TODO Auto-generated method stub
		
	}
}
