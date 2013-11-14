import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Vector;



public class SJF extends Process{
	
    public static Vector<Process> processInMem = new Vector<Process>(5,1);
	public static double ms = 0.1;
    public static  Vector<Process> processInQue = new Vector<Process>(5,1);
	public static PrintWriter outToFile = null;
	public static boolean processComputing = false;
	public static String queueString = "none";
	
	
	
	
	 

	
	public SJF(int processID, double burst, double arrive) {
		super(processID, burst, arrive);
		

	}
	
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, InterruptedException
	{
		//create the processes and put them in the memory queue vector
	   Process P1 = new Process(1, 10, 0);
	   Process P2 = new Process(2, 8, 1);
	   Process P3 = new Process(3, 6, 1);
	   Process P4 = new Process(4, 1, 2);
	   Process P5 = new Process(5, 5, 3);
		
	   processInQue.add(P1);
	   processInMem.add(P2);
	   processInMem.add(P3);
	   processInMem.add(P4);
	   processInMem.add(P5);
		
		boolean newProcessAdded = false;
		//create the timeline for the scheduler. stops when queue is empty
		for(double elapsedTime = 0.0; processInMem.size() > 0; elapsedTime = roundNum(elapsedTime))
				{
			//System.out.println(processInQue.firstElement());
				//checks the processes in the memory vector, if the arrival time and elapsedTime are the same, put it in the que vector
					for(int i = 0; processInMem.size() > i; ++i)
					{
						if(processInMem.elementAt(i).arrivalTime == elapsedTime && elapsedTime > 0){
							processInQue.add(processInMem.elementAt(i));
							newProcessAdded=true;
							System.out.println("test" + " "+ processInQue.elementAt(i) +" " + newProcessAdded);
						}
					}
					queueString = "";
					for(int i = 1; i < processInQue.size(); ++i){
						queueString += "P" + processInQue.elementAt(i).processID + " ";
						//System.out.println("P" + processInQue.elementAt(i).processID);
					}
					System.out.println("Processes in queue: " + queueString);
					//System.out.println(processInQue.firstElement());
					//if a new process was added, sort the queue with a forked thread
					if(newProcessAdded){
						Thread T = new Thread(new output());
						T.start();
						T.join();
						System.out.println("At least one new process has arrived in the queue!");
						newProcessAdded = false;
					}
					
					//If the burst time of the process is 0, its removed from queue
					if(processInQue.firstElement().burstTime == 0.0){
						System.out.println(">>>>>>>>>>" + "P" + processInQue.firstElement().processID + " total wait time = " + processInQue.firstElement().waitTime);
						System.out.println(">>>>>>>>>>" + "P" + processInQue.firstElement().processID + " turnaround time = " + processInQue.firstElement().turnAroundTime);
						processInQue.remove(0);
					}
					//decrement this burst time by 0.1 (from systemClock)
					//output the process info
					if(processInQue.size() > 0){
					processInQue.firstElement().compute();
					System.out.println("Currently computing process: P" + processInQue.firstElement().processID);
					System.out.println("Burst time remaining: " + processInQue.firstElement().burstTime);
						for(int i = 1; i < processInQue.size(); ++i)
						{
							processInQue.elementAt(i).waitLonger();
						}
					}
				}//end of loop
		
	}
	
	public static void queueSort(Vector<Process> toBeSorted)
	{
		//sort the processes based on burst times
		int smallest;
		int vectorSize = toBeSorted.size();
		
		//if there is only one item in the queue, dont sort
		if(vectorSize < 2)
			return;
		
		for(int i = 0; i < vectorSize; i++)
		{
			//assumes smallest is where it should be to start
			smallest = i;
			for(int j = i + 1; j < vectorSize; j++)
			{
				//compares smallest and j, if j is smaller than smallest, than smallest is now j
				if(toBeSorted.elementAt(j).burstTime < toBeSorted.elementAt(smallest).burstTime)
					smallest = j;
			}
			//if the smallest value has changed based on the above comparisons, swap the location of i and smallest
			if(smallest != i)
				Collections.swap(toBeSorted, i, smallest);
			//the loop finishes once i has incremented to the size of the array
		}
	}
	
	public static double roundNum(double num)
	{
		num += ms;
		num *= 10;
		num = Math.round(num);
		num /= 10;
		return num;
	}
	
	public static class output implements Runnable{
		@Override
		public void run() {
	
			queueSort(processInQue);	
		}
	}
	
	
}
