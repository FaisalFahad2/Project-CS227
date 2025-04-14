import java.util.ArrayList;
import java.util.List;

public class PriorityScheduling implements Runnable {
    private SystemCalls systemCalls;
    private ReadyQueue readyQueue;
    private int currentTime = 0;
    private int totalWaitingTime = 0;
    private int totalTurnaroundTime = 0;
    private int processCount = 0;
    private int StarvationThreshold = 5;

    public PriorityScheduling(SystemCalls sCalls, ReadyQueue readyQueue) {
        this.systemCalls = sCalls;
        this.readyQueue = readyQueue;
    }

    public void run() {

        while (true) {
            List<PCB> readyProcesses = readyQueue.getAllProcesses();
            if (readyProcesses.isEmpty()) {
                System.out.println("Ready Queue is empty. Scheduling complete.");
                break;
            }
            // print for test
            readyQueue.printReadyQueue();
            PCB highestPriorityProcess = readyProcesses.get(0);
            for (PCB process : readyProcesses) {
                process.incrementWaitingCycles();
                if (process.getWaitingCycles() >= StarvationThreshold) {
                    System.out.println("Starvation detected for process: " + process);
                }
                if (process.getPriority() > highestPriorityProcess.getPriority()) {
                    highestPriorityProcess = process;
                }
            }
            System.out.println("Selected Highest Priority Process: " + highestPriorityProcess);
            highestPriorityProcess.setWaitingTime(currentTime);
            //print wating time for test
            System.out.println(highestPriorityProcess.getWaitingTime());
            highestPriorityProcess.setState("RUNNING");
            System.out.println("Scheduled Process (now running): " + highestPriorityProcess);

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime += highestPriorityProcess.getBurstTime();
            highestPriorityProcess.setTurnaroundTime(highestPriorityProcess.getWaitingTime() + highestPriorityProcess.getBurstTime());
            systemCalls.terminated(highestPriorityProcess);
            processCount++;
            totalWaitingTime += highestPriorityProcess.getWaitingTime();
            totalTurnaroundTime += highestPriorityProcess.getTurnaroundTime();
            System.out.println("Terminated Process: " + highestPriorityProcess);
        }
            double averageWaitingTime = (double) totalWaitingTime / processCount;
            double averageTurnaroundTime = (double) totalTurnaroundTime / processCount;

            System.out.println("\n=== Statistics ===");
            System.out.println("Total processes executed: " + processCount);
            System.out.println("Average waiting time: " + averageWaitingTime + " time units");
            System.out.println("Average turnaround time: " + averageTurnaroundTime + " time units");

    }
}