import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  

public class GamePixel
{


	private ImageView imageview;
	private String type;
	private int gridX;
	private int gridY;
	private Image empty;
	private Image body;
	private Image food;
	
	public GamePixel(int x, int y, String t)
	{
		imageview = new ImageView(empty);
		empty = new Image("empty.png");
		body = new Image("body.png");
		food = new Image("food.png");
		type = t;
		gridX = x;
		gridY = y;
	}
	
	public ImageView getImageView()
	{
		return imageview;
	}
	
	public int getGridX()
	{
		return gridX;
	}
	
	public int getGridY()
	{
		return gridY;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String t)
	{
		type = t;
	}
	
	public void loadPixel()
	{
		if (type.equals("empty")){
			imageview.setImage(empty);
		}
		if (type.equals("body")){
			imageview.setImage(body);
		}
		if (type.equals("food")){
			imageview.setImage(food);
		}
	}
}