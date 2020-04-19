import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Memory {

    public PriorityQueue<Process> readyQueue;
    public Queue<Process> waitingQueue; 
    public Queue<Process> allocationQueue;
    public List<Process> finishedProcesses;
    public int size;

    public Memory(Queue<Process> allocationQueue, int size){
        this.allocationQueue = allocationQueue;
        this.waitingQueue = new LinkedList<Process>();
        this.readyQueue = new PriorityQueue<Process>();
        this.finishedProcesses = new LinkedList<Process>();
        this.size = 0;
    }

    //TODO: Test memory usage logic
    public void longTermScheduele(){
        if(allocationQueue.isEmpty()){
            return;
        }
        
        while(!allocationQueue.isEmpty() && allocationQueue.peek().memoryUsage.get(0) + size < 0.85 * 1024){
            Process process = allocationQueue.poll();
            process.setState(ProcessState.READY);
            readyQueue.add(process);
            size += process.memoryUsage.get(0);
        }
        
    }

    //TODO: Impelement deadlock handling
    public boolean isDeadlock(){
        Iterator<Process> waitingIterator = waitingQueue.iterator();
        boolean flag = false;
                
        if(!readyQueue.isEmpty()){
            return false;
        }

        while(waitingIterator.hasNext()){
            Process p = waitingIterator.next();
            
            if(!waitingIterator.hasNext()){
                break;
            }

            Process p1 = waitingIterator.next();

            if(p.memoryUsage.get(p.getIOCounter() - 1) == p1.memoryUsage.get(p1.getIOCounter() - 1) ){
                flag = true;
            } else {
                flag = false;
            }
        }

        return flag;

    }

    public boolean areQueuesEmpty(){
        return allocationQueue.isEmpty() && waitingQueue.isEmpty() && readyQueue.isEmpty();
    }

    public void decrementWaitingTime(int time){
        Iterator<Process> queue = waitingQueue.iterator();

        while(queue.hasNext()){
            Process p = queue.next(); 
            int burst = p.IOBursts.get(p.getIOCounter() - 1).intValue(); 
            
            if(burst > 0){
                p.IOBursts.set(p.getIOCounter() - 1, --burst);
                p.addToTotalIOTime(time); 
            } else if(size + p.memoryUsage.get(p.getIOCounter() - 1) < 1024){
                p.setState(ProcessState.READY);
                queue.remove();
                readyQueue.add(p);
                size += p.memoryUsage.get(p.getIOCounter() - 1);
            } else {
                p.incrementMemoryWaitCounter();
            }
        }
    }
	
	public void terminateLargestWaitingProcess(){
        Iterator<Process> waitingQueueIterator =  this.waitingQueue.iterator();
        Process largestWaitingProcess = null;

        while(waitingQueueIterator.hasNext()){
            Process currentProcess = waitingQueueIterator.next();
            if(largestWaitingProcess == null || largestWaitingProcess.memoryUsage.get(largestWaitingProcess.getIOCounter() - 1) < currentProcess.memoryUsage.get(currentProcess.getIOCounter() - 1)){
                largestWaitingProcess = currentProcess;
            }
        }
        waitingQueue.remove(largestWaitingProcess);
    }
}
