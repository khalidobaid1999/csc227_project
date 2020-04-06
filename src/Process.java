import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
	public Process(Scanner sc) {
		this.CPUBursts = new ArrayList<Integer>();
		this.IOBursts = new ArrayList<Integer>();
		this.memoryUsage = new ArrayList<Integer>();
		this.id = idCounter++;
		sc.nextLine();
		this.name = sc.nextLine();
		System.out.println("name: " + name);
		int m = sc.nextInt();
		for (int i = 0; i < m; i++) {
			CPUBursts.add(sc.nextInt());
			memoryUsage.add(sc.nextInt());
			IOBursts.add(sc.nextInt());
		}
		CPUBursts.add(sc.nextInt());
		memoryUsage.add(sc.nextInt());
		if (sc.nextInt() != -1)
			throw new InputMismatchException();
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
