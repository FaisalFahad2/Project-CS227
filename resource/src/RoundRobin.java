import java.util.HashMap;


 
public class RoundRobin{
    private ReadyQueue readyQueue;
    private final int quantum; 

    public RoundRobin(ReadyQueue readyQueue, int quantum) {
        if (readyQueue == null) {
            throw new IllegalArgumentException("ReadyQueue cannot be null");
        }
        
        
     
        this.readyQueue = readyQueue;
        this.quantum = quantum;
        
        
    }

    // this method may changes as it produces bad avg
    
    public void schedule() {
    	
    	
        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        int finishedProcessesCount = 0;
        
        // saves the pids and burst times
        
        HashMap<Integer, Integer> originalBurstTimes = new HashMap<>();

     
        
        while (!readyQueue.isEmpty()) {
            PCB process = readyQueue.getNextProcess();
            if (process == null) {
                break; 
            }
            
           
            if (!originalBurstTimes.containsKey(process.getPid())) {
                originalBurstTimes.put(process.getPid(), process.getBurstTime());
            }
            
            int remainingBurst = process.getBurstTime();
            if (remainingBurst > quantum) {
              
                Thread processThread = new Thread(() -> {
                    try {
                     
                        Thread.sleep(quantum);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                processThread.start();
                try {
                    processThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               
                
              
                process.setBurstTime(remainingBurst - quantum);
            } else {
                
                Thread processThread = new Thread(() -> {
                    try {
                        Thread.sleep(remainingBurst);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                processThread.start();
                try {
                    processThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
             
                process.setBurstTime(0);
                process.setTurnaroundTime(currentTime);
                int originalBurst = originalBurstTimes.get(process.getPid());
                int waitingTime = currentTime - originalBurst;
                process.setWaitingTime(waitingTime);              
                totalTurnaroundTime += currentTime;
                totalWaitingTime += waitingTime;
                finishedProcessesCount++;                
                readyQueue.removeProcess(process);
            }
        }
        
     
    }
}
