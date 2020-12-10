package application;

import java.util.concurrent.locks.ReentrantLock;

public class Conveyors 
{
	private int conveyorNum;
	
	// Controls synchronization with conveyor.
	public ReentrantLock accessLock = new ReentrantLock();

	public Conveyors(int conveyerNum)
	{
		this.conveyorNum = conveyerNum;
	}
	
	public void inputConnection(int stationNum) 
	{	
		System.out.println("Station " + stationNum + ": successfully moves packages on conveyer " + this.conveyorNum);
	}
	
	public void outputConnection(int stationNum) 
	{	
		System.out.println("Station " + stationNum + ": successfully moves packages on conveyer " + this.conveyorNum);
	}
	
}