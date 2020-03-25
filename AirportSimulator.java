import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
	import java.util.Queue;


	public class AirportSimulator {

		public static void main(String[] args){
			  simulate(4,0.05,6000,2,.05,2);
		}
		
		public static void simulate(int landTime, double landProb, int totalTime, int takeOffTime, double takeOffProb, int maxTimeInQueue) {
		  
	      Queue<Integer> arrivals = new LinkedList<Integer>( );  
	      Queue<Integer> departures = new LinkedList<Integer>( );
	      
	      int crashed = 0;
	      int landed = 0;
	      int tookOff = 0 ;
	     
	      BooleanSource landing = new BooleanSource(landProb);
	      BooleanSource takesOff = new BooleanSource(takeOffProb);
	      
	      Runway runway = new Runway(landTime, takeOffTime);
	      
	      Averager takeOffWaitTime = new Averager( );
	      Averager landingWaitTime = new Averager( );
	      
	      if (landTime <= 0  || takeOffProb < 0 || takeOffTime <= 0 || landProb < 0 || landProb > 1 || totalTime < 0)
	         throw new IllegalArgumentException("Values out of range"); 
	      
	      int currentMinute, next;
	      
	      takeOffWaitTime.addNumber(20);
	      landingWaitTime.addNumber(20);
	      
	      for (currentMinute = 0; currentMinute < totalTime; currentMinute++) { 
	    	 
	    	  if (landing.query())  {
	         	 arrivals.add(currentMinute);
	    	  }
	    	  
	    	  if((runway.isBusy()) && (!arrivals.isEmpty())){
	    		  next = arrivals.peek( );
	    		  if ((currentMinute-next)>=maxTimeInQueue){ 
	    			  crashed++;
	    			  landingWaitTime.addNumber(currentMinute-next);
	    			  arrivals.remove( ); 
	    		  }
	    	  }
	    	  
	    	  	
	    	  if ((!runway.isBusy( )) &&  (!arrivals.isEmpty( ))) {
	    		  next = arrivals.remove( );
	    		  landingWaitTime.addNumber(currentMinute - next);
	              runway.startLand();
	              landed++;
	    	  }
	      

	    	  if (takesOff.query( )){
	    		  departures.add(currentMinute);
	    	  }
	         	 
	         if ((!runway.isBusy( ))  &&  (!departures.isEmpty( ))  && (arrivals.isEmpty( ))) {
	             next = departures.remove( );
	             runway.startTakeOff();
	             takeOffWaitTime.addNumber(currentMinute - next);
	             tookOff++;
	         }
	    	  
	         runway.reduceRemainingTime();
	      }
		 
	     DecimalFormat avgs = new DecimalFormat("#.##"); 
	     System.out.println("Number of planes taken off: " + tookOff);
	     System.out.println("Number of planes landed: " + landed);
	     System.out.println("Number of planes crashed: " + crashed);
	     System.out.println("Average waiting time for taking off: " + avgs.format(takeOffWaitTime.average()));
	     System.out.println("Average waiting time for landing: " + avgs.format(landingWaitTime.average())); 
	   }
	   
	}

