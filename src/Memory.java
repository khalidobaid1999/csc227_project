import java.util.List;
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
        this.size = size;
    }

    public void longTermScheduele(){

    }

    public boolean isDeadlock(){
        return true;
    }

    public boolean areQueuesEmpty(){
        return true;
    }

    public void decrementWaitingTime(int time){

    }
	
	private void terminateLargestWaitingProcess(){

    }
}
