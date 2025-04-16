import java.util.*;

public class RoundRobin {
    private final int quantum;
    private int currentTime;
    private ReadyQueue readyQueue;
    private static String processNum;
    private static String processTime;
    private static int numberOfSpace;
    private double avgWaitingTime;
    private double avgTurnaroundTime;
    private double totalWaitingTime = 0;
    private double totalTurnaroundTime = 0;
    private boolean detailedMode=false;

    public RoundRobin(int quantum,boolean detailedMode) {

        this.quantum = quantum;
        this.currentTime = 0;
        this.detailedMode=detailedMode;
    }

    public void schedule(jobQueue jq, SystemCalls sc, ReadyQueue readyQueue) {

        if (readyQueue == null) {
            throw new IllegalArgumentException("ReadyQueue cannot be null");
        }
        int num = 0;
        processNum = "";
        processTime = "";
        int oo = 0;

        while (!jq.isEmpty() && num != jq.getNumOfProcsess()) {

            List<PCB> executedProcesses = readyQueue.getReadyProcesses();
            for (PCB currentProcess : executedProcesses) {
                if (detailedMode) {
                    readyQueue.printReadyQueue();
                    System.out.println("Selected Process: " + currentProcess);
                }
                // Calculate waiting time for this quantum
                int waitingTime = currentTime - currentProcess.getTurnaroundTime();
                if (waitingTime > 0) {
                    currentProcess.setWaitingTime(currentProcess.getWaitingTime() + waitingTime);
                }

                // Execute for quantum time or until process completes
                if (detailedMode){
                    System.out.println("Scheduled Process (now running): " + currentProcess);
                }
                int executeTime = Math.min(quantum, currentProcess.getBurstTime());
                if (detailedMode){
                    System.out.println("Executing P" + currentProcess.getPid() + " for " + executeTime + " ms.");
                    System.out.println("Remaining Burst Before: " + currentProcess.getBurstTime());
                }
                currentProcess.setBurstTime(currentProcess.getBurstTime() - executeTime);
                processTime += currentTime + " ";
                processNum += currentProcess.getPid() + " ";
                currentTime += executeTime;

                // If process is not finished, put it back in ready queue
                if (currentProcess.getBurstTime() > 0) {
                    if (detailedMode){
                        System.out.println("P" + currentProcess.getPid() + " still has " + currentProcess.getBurstTime() + " burst time left.");
                    }
                    sc.addToReadyQueue(currentProcess);
                } else {
                    // Process has completed
                    int turnaroundTime = currentTime; // Total time from start to completion
                    currentProcess.setTurnaroundTime(turnaroundTime);
                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;
                    sc.terminated(currentProcess);
                    if (detailedMode){
                        System.out.println("Terminated Process: " + currentProcess);
                    }
                    num++;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        if (jq.getNumOfProcsess() > 0) {
            avgWaitingTime = totalWaitingTime / jq.getNumOfProcsess();
            avgTurnaroundTime = totalTurnaroundTime / jq.getNumOfProcsess();
        } else {
            avgWaitingTime = 0;
            avgTurnaroundTime = 0;
        }

        printGanttChart();

    }

    public void printGanttChart() {
        calculateNumOfSpace(processNum);
        for (int i = 0; i < numberOfSpace; i++)
            System.out.print("-");
        System.out.println();
        printGanttChartProcess(processNum);
        System.out.println();
        for (int i = 0; i < numberOfSpace; i++)
            System.out.print("-");
        System.out.println();
        printGanttChartTime(processTime, processNum);
        System.out.print(currentTime);
        System.out.println();
        System.out.println();
        System.out.println("Average waiting time: " + avgWaitingTime);
        System.out.println("Average turnaround time: " + avgTurnaroundTime);
        System.out.println();
    }

    public void calculateNumOfSpace(String proNum) {
        numberOfSpace = 0;
        String[] numberOfPro = proNum.split(" ");
        for (int i = 0; i < numberOfPro.length; i++) {
            if (numberOfPro[i].length() < 2)
                numberOfSpace += 7;
            else
                numberOfSpace += 8;
        }
    }

    public void printGanttChartProcess(String proNum) {
        String[] numberOfPro = proNum.split(" ");
        for (int i = 0; i < numberOfPro.length; i++)
            System.out.print("|  P" + numberOfPro[i] + "  ");
        System.out.print("|");
    }

    public void printGanttChartTime(String time, String process) {
        String[] processTime = time.split(" ");
        String[] numberOfPro = process.split(" ");
        for (int i = 0; i < processTime.length; i++) {
            if (numberOfPro[i].length() < 2) {

                if (i == 0)
                    System.out.print(processTime[i] + "      ");
                else if (i + 1 != processTime.length && processTime[i].length() <= 2
                        && processTime[i + 1].length() <= 2)
                    System.out.print(processTime[i] + "     ");
                else if (i + 1 != processTime.length && processTime[i].length() <= 2 && processTime[i + 1].length() > 2)
                    System.out.print(processTime[i] + "     ");
                else if (i + 1 != processTime.length
                        && ((processTime[i].length() <= 3 && processTime[i + 1].length() > 3)
                        || (processTime[i].length() <= 4 && processTime[i + 1].length() > 3)))
                    System.out.print(processTime[i] + "   ");
                else
                    System.out.print(processTime[i] + "    ");

            } else {
                if (i == 0)
                    System.out.print(processTime[i] + "       ");
                else if (i + 1 != processTime.length && processTime[i].length() <= 2
                        && processTime[i + 1].length() <= 2)
                    System.out.print(processTime[i] + "      ");
                else if (i + 1 != processTime.length && processTime[i].length() <= 2 && processTime[i + 1].length() > 2)
                    System.out.print(processTime[i] + "     ");
                else if (i + 1 != processTime.length
                        && ((processTime[i].length() <= 3 && processTime[i + 1].length() > 3)
                        || (processTime[i].length() <= 4 && processTime[i + 1].length() > 3)))
                    System.out.print(processTime[i] + "    ");
                else
                    System.out.print(processTime[i] + "     ");
            }
        }

    }


}
