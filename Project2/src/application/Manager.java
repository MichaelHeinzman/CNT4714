/*
Name: Michael Seth Heinzman
Course: CNT 4714 Fall 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date: October 4, 2020
Class: CNT4714
*/


package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Manager 
{
	static String fileName = "config.txt";
	static Conveyors[] Conveyors;
	static Station[] Stations;
	
	static int[] WorkLoads;
	static int NumberOfStations;

	public static void main(String[] args) throws FileNotFoundException 
	{
		// Read in config file.
		File file = new File(fileName);
		Scanner read = new Scanner(file);
		
		NumberOfStations = read.nextInt();
		WorkLoads = new int[NumberOfStations];
		
		// Create the conveyors.
		Conveyors = new Conveyors[NumberOfStations];
		
		// Create each instance of conveyor.
		for(int i = 0; i < Conveyors.length; i++)
			Conveyors[i] = new Conveyors(i);
			 
		
		// Form Stations.
		Stations = new Station[NumberOfStations];
		
		// Instantiate Thread pool.
		ExecutorService ShippingSimulator = Executors.newFixedThreadPool(NumberOfStations);
		
		for(int i = 0; i < WorkLoads.length && i < Conveyors.length && i < Stations.length; i++) 
		{
			// Give the workload each station can handle.
			WorkLoads[i] = read.nextInt();
			
			Stations[i] = new Station(WorkLoads[i], i, NumberOfStations);

			Stations[i].SetTheInput(Conveyors[Stations[i].GetTheInputConNum()]);
			Stations[i].SetTheOutput(Conveyors[Stations[i].GetTheOutputConNum()]);
			
			//start up the stations
			try
			{
					ShippingSimulator.execute(Stations[i]);
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}

		}
		
		// Terminate Loose Ends.... (lol just wanted to say that)
		ShippingSimulator.shutdown();
		read.close();
	}

}