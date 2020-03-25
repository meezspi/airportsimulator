\\Mia Spiri

    public class Runway
    {
        private int landTime;  
	private int takeOffTime;
	private int timeLeft;                         

	public Runway(int land, int take)  
	{
	    landTime = land;
	    takeOffTime = take;
	    timeLeft = 0;
	}

	public boolean isBusy( )  
	{
	    return (timeLeft > 0);
	}
  
	public void reduceRemainingTime( ) 
	{
	    if (timeLeft > 0)
	        timeLeft--;
	} 
	   
	public void startLand( ) 
	{
	    if (timeLeft > 0)
	        throw new IllegalStateException("Runway busy.");
	    timeLeft = landTime;
	}

	public void startTakeOff() {
	    if (timeLeft > 0)
	        throw new IllegalStateException("Runway busy.");
		timeLeft = takeOffTime;
	}
    }

