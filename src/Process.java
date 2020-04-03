import java.util.ArrayList;
import java.util.Timer;

public class Process {

	
	//class attributes
	private ProcessState state;
	private int id;
	private ArrayList<Integer> CPUBursts;
	private ArrayList<Integer> IOBursts;
	private ArrayList<Integer> memoryUsage;
	private Timer readyQueueEntryTime;
	private int CPUCounter;
	private int IOTotalCounter;
	private int IOTotalTime;
	private int memoryWaitCounter;
	private int terminationKillTime;
	private int preemptionCounter;
	
	
	
	//constructor
	public Process() {
		
	}
	
	
	public int getCPUBurstTime() {
		int length = this.CPUBursts.size();
		int sum_time = 0;
		for(int i=0; i<length; i++)
			sum_time += this.CPUBursts.get(i);
		return sum_time;
	}
	
	
	public void setState(ProcessState p) {
		this.state = p;
	}
	
	
	public void incrementCPUCounter() {
		this.CPUCounter++;
	}
	
	
	public void incrementIOCounter() {
		this.IOTotalCounter++;
	}
	
	
	public void incrementMemoryWaitCounter() {
		this.memoryWaitCounter++;
	}
	
	
	public void incrementPreemptionCounter() {
		this.preemptionCounter++;
	}
	
	
	public void addToTotalIOTime(int time) {
		this.IOTotalTime += time;
	}
	
	
}
