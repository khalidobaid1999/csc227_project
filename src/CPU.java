import java.util.Iterator;

public class CPU {
    public Process runningProcess;
    private int currentCPUTime;
    private Memory m;

    // WAITING FOR MEMORY IMPLEMENTATION
    public CPU(Memory m) {
	this.m = m;
    }

    public void simulateMachineExecuteCycle() {
	System.out.println(currentCPUTime + " " + m.waitingQueue.size() + " " + m.readyQueue.size() + " " + runningProcess + " " + m.size);        
	// System.out.println(currentCPUTime);
	// System.out.println(m.readyQueue);
	// System.out.println(m.waitingQueue);
	currentCPUTime++;

	if (runningProcess == null) {
	    if (isDeadlock()) {
		System.out.println("DEADLOCK! deleting largest waiting process");
		m.terminateLargestWaitingProcess();
	    }
	    Process p = getFromReadyQueue();
	    if (p == null) {
		m.decrementWaitingTime(1);
		return;
	    }
	    runningProcess = p;
	}

	if (runningProcess.CPUBursts.get(runningProcess.getIOCounter()) == 0) {
//	    System.out.println(runningProcess);

	    if (runningProcess.getIOCounter() == runningProcess.IOBursts.size()) {
		runningProcess.setState(ProcessState.TERMINATED);
		m.finishedProcesses.add(runningProcess);
	    } else {
		runningProcess.setState(ProcessState.WAITING);
		runningProcess.incrementIOCounter();
		m.waitingQueue.add(runningProcess);
	    }

	    runningProcess = null;

	} else if (checkForPreemption()) {
	    runningProcess.incrementPreemptionCounter();
	    runningProcess.setState(ProcessState.WAITING);
	    m.readyQueue.add(runningProcess);
	    runningProcess = getFromReadyQueue();
	}

	m.decrementWaitingTime(1);

	executeRunningProcess(1);
    }

    public void incrementCPUTime(int delta) {
	this.currentCPUTime += delta;
	try {
	    Thread.sleep(delta);
	} catch (Exception e) {
	}
    }

    private Process getFromReadyQueue() {
	if (m.readyQueue.isEmpty())
	    return null;
	Process cp = (m.readyQueue.poll());
	cp.incrementCPUCounter();
	cp.setState(ProcessState.RUNNING);
	cp.setReadyQueueEmtryTime(currentCPUTime);
	return cp;
    }

    private boolean checkForPreemption() {
	Process peek = m.readyQueue.peek();
	if (peek != null && peek.CPUBursts.get(peek.getIOCounter()) < runningProcess.CPUBursts.get(runningProcess.getIOCounter())) {
	    return true;
	}
	return false;
    }

    private void executeRunningProcess(int time) {
	if (runningProcess == null) {
	    return;
	}
	int i = runningProcess.getIOCounter();
	runningProcess.CPUBursts.set(i, runningProcess.CPUBursts.get(i) - time);
	runningProcess.incrementCPUTime();
    }

    public boolean isDeadlock() {

	if (runningProcess != null)
	    return false;
	if (!m.readyQueue.isEmpty())
	    return false;
	if (m.waitingQueue.isEmpty())
	    return false;
	Iterator<Process> waitingIterator = m.waitingQueue.iterator();

	while (waitingIterator.hasNext()) {
	    Process p = waitingIterator.next();
	    if (p.IOBursts.get(p.getIOCounter() - 1) != 0)
		return false;
	}

	return true;

    }

}
