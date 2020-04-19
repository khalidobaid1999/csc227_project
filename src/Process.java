import java.util.ArrayList;

public class Process {
	private static int idCounter = 0;
	// class attributes
	private ProcessState state;
	private String name;
	private int id;
	public ArrayList<Integer> CPUBursts;
	public ArrayList<Integer> IOBursts;
	public ArrayList<Integer> memoryUsage;
	private int readyQueueEntryTime;
	private int CPUCounter;
	private int IOTotalCounter;
	private int IOTotalTime;
	private int memoryWaitCounter;
	private int terminationKillTime;
	private int preemptionCounter;
	private int totalCPUTime;

	// constructor
	public Process(String name,ArrayList<Integer> CPUBursts,ArrayList<Integer> IOBursts,ArrayList<Integer> memoryUsage) {
		this.name = name;
		this.CPUBursts = CPUBursts;
		this.IOBursts = IOBursts;
		this.memoryUsage = memoryUsage;
		this.id = (int) Math.random();		
	}

	public int getCPUBurstTime() {
		int length = this.CPUBursts.size();
		int sum_time = 0;
		for (int i = 0; i < length; i++)
			sum_time += this.CPUBursts.get(i);
		return sum_time;
	}

	public void setReadyQueueEmtryTime(int t) {
		this.readyQueueEntryTime = t;
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

	public int getIOCounter() {
		return IOTotalCounter;
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

	public void incrementCPUTime() {
		this.totalCPUTime++;
	}

	@Override
	public String toString() {
		return name + " " + CPUBursts.toString() + memoryUsage.toString() + IOBursts.toString();
	}
}
