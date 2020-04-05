public class CPU {
    private Process runningProcess;
    private int currentCPUTime;
    private Memory m;
    //WAITING FOR MEMORY IMPLEMENTATION 
    public CPU(Memory m) {
        this.m = m;
    }

    public void simulateMachineExecuteCycle() {
        if (runningProcess.CPUBursts.get(runningProcess.getIOCounter()) == 0) {
            if (runningProcess.getIOCounter() == runningProcess.IOBursts.size()) {
                runningProcess.setState(ProcessState.TERMINATED);
                m.finished.add(runningProcess);
            } else {
                runningProcess.setState(ProcessState.WAITING);
                runningProcess.incrementIOCounter();
                m.waitingQueue.push(runningProcess);
            }
            runningProcess = getFromReadyQueue();
        } else if (checkForPreemption()) {
            runningProcess.incrementPreemptionCounter();
            runningProcess.setState(ProcessState.WAITING);
            m.waitingQueue.push(runningProcess);

            runningProcess = getFromReadyQueue();
        }
        executeRunningProcess(1);

        m.decrementWaitingTime();
    }

    public void incrementCPUTime(int delta) {
        this.currentCPUTime += delta;
        try {
            Thread.sleep(delta);
        } catch (Exception e) {
        }
    }


    private Process getFromReadyQueue() {
        if (m.readyQueue.empty())
            return null;
        Process cp = (m.readyQueue.serve());
        cp.incrementCPUCounter();
        cp.setState(ProcessState.RUNNING);
        cp.setReadyQueueEmtryTime(currentCPUTime);
        return cp;
    }

    private boolean checkForPreemption() {
        return false;
    }

    private void executeRunningProcess(int time) {
        int i = runningProcess.getIOCounter();
        runningProcess.CPUBursts.set(i, runningProcess.CPUBursts.get(i) - time);
        runningProcess.incrementCPUTime();
    }
}
