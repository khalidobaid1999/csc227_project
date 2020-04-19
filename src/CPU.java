public class CPU {
    public Process runningProcess;
    private int currentCPUTime;
    private Memory m;
    //WAITING FOR MEMORY IMPLEMENTATION 
    public CPU(Memory m) {
        this.m = m;
    }

    public void simulateMachineExecuteCycle() {
        // System.out.println(currentCPUTime);          
        // System.out.println(m.readyQueue);
        // System.out.println(m.waitingQueue);
        currentCPUTime++;

        if(runningProcess == null){  
            Process p = getFromReadyQueue();       
            if(p == null){
                m.decrementWaitingTime(1);
                return;
            }
            runningProcess = p;
        }

        if (runningProcess.CPUBursts.get(runningProcess.getIOCounter()) == 0) {
            System.out.println(runningProcess);

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
        if(peek != null && peek.CPUBursts.get(peek.getIOCounter()) < runningProcess.CPUBursts.get(runningProcess.getIOCounter())){
            return true;        
        }
        return false;
    }

    private void executeRunningProcess(int time) {
        if(runningProcess == null){
            return;
        }
        int i = runningProcess.getIOCounter();
        runningProcess.CPUBursts.set(i, runningProcess.CPUBursts.get(i) - time);
        runningProcess.incrementCPUTime();
    }
}
