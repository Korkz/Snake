import javafx.animation.AnimationTimer;

public class GridRenderer extends AnimationTimer
{
	private long lastUpdate;
	private FinalProject finalproject;
	private int snakeDirection = 1;
	
	public GridRenderer(FinalProject fp)
	{
		finalproject = fp;
	}
	
	public void handle(long now)
	{
		if (now - lastUpdate < 250000000)
		{
			return;
		}
		lastUpdate = now;	
		
		
		finalproject.moveSnake();
		finalproject.updateRender();
		
	}
	
	public void setDirection(int d)
	{
		snakeDirection = d;
	}
	
	
}