// Skybox textures are from http://www.gametutorials.com/tutorial/sky-box/

import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


//a box which makes it seem like you are in the real world
public class SkyBox {

	static Texture[] skytex = new Texture[6];
	public static boolean skyload = false;
	public static float skywid = 10; //Actually width/2
	
	public static void setupSkybox() {
		try{
		skytex[0] = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream("../assets/Front.bmp"));
		skytex[1] = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream("../assets/Right.bmp"));
		skytex[2] = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream("../assets/Back.bmp"));
		skytex[3] = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream("../assets/Left.bmp"));
		skytex[4] = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream("../assets/Top.bmp"));
		skytex[5] = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream("../assets/Bottom.bmp"));
		skyload = true;
		}
		catch (Exception e) {
		}
		
		for (int i = 0; i < 6; i++) {
		}
		
	}
	
	public static void drawSkybox(float x, float y, float z) {
		if (skyload) {
			GL11.glColor3f(1f,1f,1f);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			//front
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, skytex[0].getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				glVertex3f(x-skywid,y+skywid,z+skywid);
				GL11.glTexCoord2f(1,0);
				glVertex3f(x-skywid,y-skywid,z+skywid);
				GL11.glTexCoord2f(1,1);
				glVertex3f(x-skywid,y-skywid,z-skywid);
				GL11.glTexCoord2f(0, 1);
				glVertex3f(x-skywid,y+skywid,z-skywid);
			GL11.glEnd();
			
			//right
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, skytex[1].getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				glVertex3f(x+skywid,y+skywid,z+skywid);
				GL11.glTexCoord2f(1,0);
				glVertex3f(x-skywid,y+skywid,z+skywid);
				GL11.glTexCoord2f(1,1);
				glVertex3f(x-skywid,y+skywid,z-skywid);
				GL11.glTexCoord2f(0, 1);
				glVertex3f(x+skywid,y+skywid,z-skywid);
			GL11.glEnd();
			
			//back
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, skytex[2].getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				glVertex3f(x+skywid,y-skywid,z+skywid);
				GL11.glTexCoord2f(1,0);
				glVertex3f(x+skywid,y+skywid,z+skywid);
				GL11.glTexCoord2f(1,1);
				glVertex3f(x+skywid,y+skywid,z-skywid);
				GL11.glTexCoord2f(0, 1);
				glVertex3f(x+skywid,y-skywid,z-skywid);
			GL11.glEnd();
			
			//left
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, skytex[3].getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				glVertex3f(x-skywid,y-skywid,z+skywid);
				GL11.glTexCoord2f(1,0);
				glVertex3f(x+skywid,y-skywid,z+skywid);
				GL11.glTexCoord2f(1,1);
				glVertex3f(x+skywid,y-skywid,z-skywid);
				GL11.glTexCoord2f(0, 1);
				glVertex3f(x-skywid,y-skywid,z-skywid);
			GL11.glEnd();
			
			//top
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, skytex[4].getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				glVertex3f(x+skywid,y+skywid,z+skywid);
				GL11.glTexCoord2f(1,0);
				glVertex3f(x+skywid,y-skywid,z+skywid);
				GL11.glTexCoord2f(1,1);
				glVertex3f(x-skywid,y-skywid,z+skywid);
				GL11.glTexCoord2f(0, 1);
				glVertex3f(x-skywid,y+skywid,z+skywid);
			GL11.glEnd();
			
			//bottom
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, skytex[5].getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				glVertex3f(x-skywid,y+skywid,z-skywid);
				GL11.glTexCoord2f(1,0);
				glVertex3f(x-skywid,y-skywid,z-skywid);
				GL11.glTexCoord2f(1,1);
				glVertex3f(x+skywid,y-skywid,z-skywid);
				GL11.glTexCoord2f(0, 1);
				glVertex3f(x+skywid,y+skywid,z-skywid);
			GL11.glEnd();
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
		else {
		}
	}
}
