
public abstract class ScreenObj {
	public float coordinates[];
	public float velocity[];
	public abstract void draw();
	public abstract boolean isZCollision(float[] coord, float velocity[], float dimensions[]);
	public abstract void horCollision(float[] coord, float velocity[], float dimensions[]);
	public ScreenObj(){
		coordinates = new float[4];
	}

}
