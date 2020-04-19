//assuming waiting queue takes ram 

main(){
    load process from files into allocationQueue

    cpuTime = 0;

    longTermSchedule();    

    incrementCputTime(100);

    while queuesNotEmpty(){
        if(cpuTime % 200 == 0)
            longTermSchedule()
        simulateCycle()
        incrementCputTime(1);
    }
}

incrementCputTime(n){
    cpuTime += n
    try{
        Thread.sleep(n);
    }catch(interuptedException e){
    }
}

simulateCycle(){
    
    if(currentProcces.cpuBursts[currentProcces.IOCounter] == 0 ){
        if(currentProcces.IOCounter == currentProcces.IOBursts.length){
            currentProcces.state = "terminated";
            finished.add(currentProcces);
        }else{
            currentProcces.state = "waiting";
            currentProcces.IOCounter++;
            waitingQueue.push(currentProcces)
        }
        currentProcces = getFromReadyQueue()
    }else if(checkForPreemption()){
        currentProcces.incrementPreemtionCounter();
        currentProcces.state = "waiting";        
        waitingQueue.push(currentProcces)

        currentProcces = getFromReadyQueue()
    }

    currentProcces.cpuBursts[currentProcces.IOCounter]--;
    currentProcces.incrementTotalCPUTime();
    
    Memory.decrementWaitingTime();

}
decrementWaitingTime(){
    foreach(p : waitingQueue){
        if(p.IOBursts[p.IOCounter - 1] > 0){
            p.IOBursts[p.IOCounter - 1]--;
            p.incrementTotalIOTime()
        }
        //p is waiting for memory
        if(ram + p.memory[p.IOCounter - 1] < 1024){
            currentProcces.state = "ready";
            readyQueue.push(currentProcces)
        }
        else
            p.incrementMemmoryCounter()
        
    }
}


getFromReadyQueue(){
    if(readyQueue.empty)
        return null;
    cp = (readyQueue.serve())
    cp.incrementCPUCOunter();
    cp.state = "running";
    return cp;
}


longTermSchedule(){
    if(allocationQueue.empty())
        return;

    while(allocationQueue.peek.memory[0] + ram < 0.85 * 1024)
        procces = allocationQueue.pop()
        procces.state = "ready";
        readyQueue.push(procces);
}