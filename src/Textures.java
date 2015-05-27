import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

//self explanatory class
public class Textures{
	public static Texture speed,jump,height;
	public static void init(){
		try{
			speed = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/speed.png"));
			jump = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/jump.png"));
			height = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/height.png"));
		}catch(Exception e){}
	}
}