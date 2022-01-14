import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent; 
import javafx.geometry.Pos; 
import javafx.geometry.Insets;
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.stage.Stage; 
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;  
import javafx.event.EventHandler; 
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import java.lang.Math;
import javafx.collections.ObservableList;
import javafx.scene.Node;



public class FinalProject extends Application 
{ 

	private int col = 0;
	private String direction;
	private static ArrayList<GamePixel> snakeBody = new ArrayList<GamePixel>();
	private GamePixel snakeHead;
	private GamePixel snakeFood;
	private int snakeDirection = 1; //0 is up, 1 is right, 2 is down, 3 is left
	private GamePixel[][] gamepixel = new GamePixel[30][30];
	private GridPane grid = new GridPane();
	private AnimationTimer timer;
	private boolean keyLocked = false;

	
	 
	@Override 
	public void start(Stage stage) throws FileNotFoundException 
	{         
          
          
		
		//Create an image view array
		
      
		//set the size of the image view 
		//imageView.setFitHeight(300); 
      
		//preserve the ratio of the image 
		//imageView.setPreserveRatio(true); 
		 
      	
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(4);
		grid.setVgap(4);
    	grid.setPadding(new Insets(4, 4, 4, 4));
    	grid.setMinWidth(500);
    	grid.setMinHeight(500);
    	grid.setBorder(new Border(new BorderStroke(Paint.valueOf("WHITE"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(15))));
    	stage.setResizable(false);
		
		
		//load in all of the game pixels at the beginning of the game
		renderPixels();
		snakeHead = gamepixel[10][15];
		snakeBody.add(snakeHead);
		snakeFood = gamepixel[20][15];
    	updateRender();
    	timer = new GridRenderer(this);
    	timer.start();
    	
    	
    	
    	Scene scene = new Scene(grid); 
    	scene.setFill(Paint.valueOf("BLACK"));
      
      

    	//Handling the key typed event 
    	EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() { 
    		@Override 
    		public void handle(KeyEvent event) { 
    			//System.out.println(event);
    			if(!keyLocked)
    			{
					if(event.getCharacter().equals("d"))
					{ 
						if (snakeDirection != 3)
						{
							snakeDirection = 1;
						}
					}
					if(event.getCharacter().equals("a"))
					{ 
						if (snakeDirection != 1)
						{
							snakeDirection = 3;
						}
					}
					if(event.getCharacter().equals("w"))
					{ 
						if (snakeDirection != 2)
						{
							snakeDirection = 0;
						}
					}
					if(event.getCharacter().equals("s"))
					{ 
						if (snakeDirection != 0)
						{
							snakeDirection = 2;
						}
					}
				}
				keyLocked = true;
				
			}           
		};              
        
		//Adding an event handler to the scene 
    	scene.addEventHandler(KeyEvent.KEY_TYPED, keyHandler); 
      
    	//Setting title to the Stage 
    	stage.setTitle("Snake");  
      
    	//Adding scene to the stage 
    	stage.setScene(scene);
      
      
    	//Displaying the contents of the stage 
    	stage.show(); 
    	
      
	}  
	
	public void renderPixels()
	{
		for (int x = 0; x < 30; x++)
    	{
    		for(int y = 0; y < 30; y++)
    		{ 
    			gamepixel[x][y] = new GamePixel(x, y, "empty");
    			gamepixel[x][y].loadPixel();
				grid.add(gamepixel[x][y].getImageView(), x, y); //place the image in column 0, row 0
    		}
    	}
	}
	
	public void updateRender()
	{
		grid.getChildren().clear();
		for (int x = 0; x < 30; x++)
    	{
    		for(int y = 0; y < 30; y++)
    		{ 
    			if(snakeBody.contains(gamepixel[x][y]))
    			{
    				gamepixel[x][y].setType("body");
    			}
    			else if (snakeFood == gamepixel[x][y])
    			{
    				gamepixel[x][y].setType("food");
    			}
    			else
    			{
					gamepixel[x][y].setType("empty");
				}
				gamepixel[x][y].loadPixel();
				grid.add(gamepixel[x][y].getImageView(), x, y); //place the image in column 0, row 0
				
    		}
    	}
    	keyLocked = false;
	}
	
	public void moveSnake()
	{
		if (snakeDirection == 0)
		{
			if(snakeHead.getGridY() > 0)
			{
				snakeHead = gamepixel[snakeHead.getGridX()][snakeHead.getGridY() - 1];
			}
			else
			{
				timer.stop();
			}
		}
		if (snakeDirection == 1)
		{
			if(snakeHead.getGridX() < 29)
			{
				snakeHead = gamepixel[snakeHead.getGridX() + 1][snakeHead.getGridY()];
			}
			else
			{
				timer.stop();
			}
		}
		if (snakeDirection == 2)
		{
			if(snakeHead.getGridY() < 29)
			{
				snakeHead = gamepixel[snakeHead.getGridX()][snakeHead.getGridY() + 1];
			}
			else
			{
				timer.stop();
			}
		}
		if (snakeDirection == 3)
		{
			if(snakeHead.getGridX() > 0)
			{
				snakeHead = gamepixel[snakeHead.getGridX() - 1][snakeHead.getGridY()];
			}
			else
			{
				timer.stop();
			}
		}
		
		
		if (snakeBody.contains(snakeHead))
		{
			timer.stop();
		}
		else if(snakeHead != snakeFood)
		{
			snakeBody.remove(0);
			snakeBody.add(snakeHead);
		}
		else
		{
			snakeBody.add(snakeHead);
			while(snakeBody.contains(snakeFood))
			{
				snakeFood = gamepixel[(int)(Math.random()*30)][(int)(Math.random()*30)];
			}
		}

		
	}
	
	
	public static void main(String args[]) 
	{ 
		launch(args); 
	} 
	
	
}