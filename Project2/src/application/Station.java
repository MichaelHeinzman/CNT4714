package application;

public class Station implements Runnable 
{
	private int maxNumStations;
	private int StationNumber;
	private int inputConNum;
	private int outputConNum;
	private int workLoad = 0;
	
	private Conveyors input;
	private Conveyors  output;
	
	
	public Station(int workLoad, int stationNum, int maxNumStations)
	{
		this.workLoad = workLoad;
		this.StationNumber = stationNum;
		this.maxNumStations = maxNumStations;
		this.SetTheInputConNum();
		this.SetTheOutputConNum();
		
		System.out.println("Station " + stationNum + ": Workload set. Station " + this.StationNumber + " has " + this.workLoad + " package groups to move");
	}
	
	
	@Override
	public void run() { 
		
		// Loop runs until workload is completed.
		while(this.workLoad != 0)
		{
			
			// Locate and recieve input lock.
			if(input.accessLock.tryLock())
			{
				System.out.println("Station " + this.StationNumber + ": granted access to conveyer " + this.inputConNum);
				
				// Locate and recieve output lock.
				if(output.accessLock.tryLock())
				{
					System.out.println("Station " + this.StationNumber + ": granted access to conveyer " + this.outputConNum);
					doWork();
				}
				else
				{	
					// Output lock not recieved, release input lock.
					System.out.println("Station " + this.StationNumber + ": released access to conveyer " + this.inputConNum);
					input.accessLock.unlock();
					
					try 
					{
						Thread.sleep(1500);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				
				// Disable/Release locks.
				if(input.accessLock.isHeldByCurrentThread())
				{
					System.out.println("Station " + this.StationNumber + ": released access to conveyer " + this.inputConNum);
					input.accessLock.unlock();

				}
				if(output.accessLock.isHeldByCurrentThread())
				{
					System.out.println("Station " + this.StationNumber + ": released access to conveyer " + this.outputConNum);
					output.accessLock.unlock();
				}
				
				
				try 
				{
					Thread.sleep(1500);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		// All Workload have been completed.
		System.out.println(); 
		System.out.println("* * Station " + StationNumber + ": Workload successfully completed * *");
		System.out.println();
	}
	
	
	public void doWork() 
	{	
		this.input.inputConnection(this.StationNumber);
		this.output.outputConnection(this.StationNumber);
		
		// Decreases workload by one.
		this.workLoad--;
		
		System.out.println("Station " + this.StationNumber + ": has " + this.workLoad + " package groups left to move");
	}
	
	
	// Set and Get the input of conveyor.
	public Conveyors GetTheInput() 
	{
		return input;
	}
	public void SetTheInput(Conveyors input)
	{
		this.input = input;
	}

	
	// Set and Get the output of conveyor.
	public Conveyors GetTheOutput()
	{
		return output;
	}
	public void SetTheOutput(Conveyors output) 
	{
		this.output = output;
	}
	
	// Get and Set the Input for conveyor number.
	public int GetTheInputConNum() 
	{
		return inputConNum;
	}
	public void SetTheInputConNum()
	{
		if(StationNumber == 0)
		{
			this.inputConNum = 0;
		}
		else
		{
			this.inputConNum = this.StationNumber - 1;
		}
		
	
		System.out.println("Station " + StationNumber + ": In-Connection set to conveyer " + this.inputConNum);
	}

	// Get and Set the Output for conveyor number.
	public int GetTheOutputConNum() 
	{
		return outputConNum;
	}
	public void SetTheOutputConNum() 
	{
		if(this.StationNumber == 0)
		{
			this.outputConNum = this.maxNumStations - 1;
		}
		else
		{
			this.outputConNum = this.StationNumber;
		}
		
		System.out.println("Station " + StationNumber + ": Out-Connection set to conveyer " + this.outputConNum);

	}
	
}