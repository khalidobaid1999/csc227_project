import java.util.*;

public class Process {
	private static int idCounter = 0;
	// class attributes
	private ProcessState state;
	private String name;
	private int id;
	private int arrivalTime;
	public List<Integer> CPUBursts;
	public List<Integer> IOBursts;
	public List<Integer> memoryUsage;
	private int readyQueueEntryTime;
	private int CPUCounter;
	private int IOTotalCounter;
	private int IOTotalTime;
	private int memoryWaitCounter;
	private int terminationKillTime;
	private int preemptionCounter;
	private int totalCPUTime;

	public Process() {

	}

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

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Process.idCounter = idCounter;
	}

	public ProcessState getState() {
		return state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public List<Integer> getCPUBursts() {
		return CPUBursts;
	}

	public void setCPUBursts(List<Integer> CPUBursts) {
		this.CPUBursts = CPUBursts;
	}

	public List<Integer> getIOBursts() {
		return IOBursts;
	}

	public void setIOBursts(List<Integer> IOBursts) {
		this.IOBursts = IOBursts;
	}

	public List<Integer> getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(List<Integer> memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public int getReadyQueueEntryTime() {
		return readyQueueEntryTime;
	}

	public void setReadyQueueEntryTime(int readyQueueEntryTime) {
		this.readyQueueEntryTime = readyQueueEntryTime;
	}

	public int getCPUCounter() {
		return CPUCounter;
	}

	public void setCPUCounter(int CPUCounter) {
		this.CPUCounter = CPUCounter;
	}

	public int getIOTotalCounter() {
		return IOTotalCounter;
	}

	public void setIOTotalCounter(int IOTotalCounter) {
		this.IOTotalCounter = IOTotalCounter;
	}

	public int getIOTotalTime() {
		return IOTotalTime;
	}

	public void setIOTotalTime(int IOTotalTime) {
		this.IOTotalTime = IOTotalTime;
	}

	public int getMemoryWaitCounter() {
		return memoryWaitCounter;
	}

	public void setMemoryWaitCounter(int memoryWaitCounter) {
		this.memoryWaitCounter = memoryWaitCounter;
	}

	public int getTerminationKillTime() {
		return terminationKillTime;
	}

	public void setTerminationKillTime(int terminationKillTime) {
		this.terminationKillTime = terminationKillTime;
	}

	public int getPreemptionCounter() {
		return preemptionCounter;
	}

	public void setPreemptionCounter(int preemptionCounter) {
		this.preemptionCounter = preemptionCounter;
	}

	public int getTotalCPUTime() {
		return totalCPUTime;
	}

	public void setTotalCPUTime(int totalCPUTime) {
		this.totalCPUTime = totalCPUTime;
	}

	private String getListsString() {
		String strings[] = new String[CPUBursts.size()];
		for (int i = 0; i < CPUBursts.size(); i++) {
			strings[i] = String.join("|", CPUBursts.get(i).toString(),
					memoryUsage.get(i).toString(), IOBursts.get(i).toString());
		}
		return String.join("|", strings);
	}

	@Override
	public String toString() {
		return String.join("|", "" + id, name, "" + arrivalTime, getListsString());
	}
}
