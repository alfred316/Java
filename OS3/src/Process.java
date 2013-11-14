public class Process {
	//each process needs an ID, a priority level, a burst time, and arrival time
	public int processID;
	public double burstTime;
	public double arrivalTime;
	public double waitTime; //in ms
	public double turnAroundTime;
	
	public Process (int pid, double burst, double arrive)
	{
		 processID =pid;
		burstTime = burst;
		arrivalTime = arrive;
		waitTime = 0;
		turnAroundTime = 0;
	}
	
	public void waitLonger()
	{
		waitTime += 0.1;
		waitTime *= 10;
		waitTime = Math.round(waitTime);
		waitTime /= 10;
		
		turnAroundTime += 0.1;
		turnAroundTime *= 10;
		turnAroundTime = Math.round(turnAroundTime);
		turnAroundTime /= 10;
	}
	
	public void compute()
	{
		burstTime -= 0.1;
		burstTime *= 10;
		burstTime = Math.round(burstTime);
		burstTime /= 10;
		
		turnAroundTime += 0.1;
		turnAroundTime *= 10;
		turnAroundTime = Math.round(turnAroundTime);
		turnAroundTime /= 10;
	}
}