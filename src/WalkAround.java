import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class WalkAround {
	private final int width = 800;
	private final int height = 600;//if you change this, also change code in

	ArrayList<ScreenObj> r = new ArrayList<ScreenObj>();

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}


		GameStateManager.init();
		GameStateManager.setState(GameStateManager.TITLE);
		while (!Display.isCloseRequested()) {
			GameStateManager.manage();

			Display.update();
			Display.sync(40);
		}

		Display.destroy();
	}

	public static void main(String[] argv) {
		WalkAround wa = new WalkAround();
		wa.start();
	}

}

