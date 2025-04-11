import java.util.List;


public class FCFS {
    private ReadyQueue readyQueue;


    public FCFS(ReadyQueue readyQueue) {
        if (readyQueue == null) {
            throw new IllegalArgumentException("ReadyQueue cannot be null");
        }
        this.readyQueue = readyQueue;
    }


    public void schedule() {
        List<PCB> processes = readyQueue.getReadyProcesses();
        
        if (processes == null || processes.isEmpty()) {
            System.out.println("No processes in the ready queue to schedule.");
            return;
        }
        
        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (PCB process : processes) {
            int waitingTime = currentTime;
            int turnaroundTime = waitingTime + process.getBurstTime();
            
            process.setWaitingTime(waitingTime);
            process.setTurnaroundTime(turnaroundTime);
            
     
        }
     
        
    int n = processes.size();
    double avgWaitingTime;
    double avgTurnaroundTime;

         if (n > 0) {
             avgWaitingTime = totalWaitingTime / n;
             avgTurnaroundTime = totalTurnaroundTime / n;
        } else {
            avgWaitingTime = 0;
            avgTurnaroundTime = 0;
         }

     
     }
}