import java.util.ArrayList;
import java.util.List;

public class PrioritySchedulingSummary {
    private SystemCalls systemCalls;
    private ReadyQueue readyQueue;
    private int currentTime = 0;
    private int totalWaitingTime = 0;
    private int totalTurnaroundTime = 0;
    private int processCount = 0;
    private int StarvationThreshold = 5;
    int numberOfSpace;
    private ArrayList<Integer> starvationProcess = new ArrayList<>();
    private ArrayList<Integer> processList = new ArrayList<>();
    private ArrayList<Integer> timeList = new ArrayList<>();

    public PrioritySchedulingSummary(SystemCalls sCalls, ReadyQueue readyQueue) {
        this.systemCalls = sCalls;
        this.readyQueue = readyQueue;
    }

    public ArrayList<Integer> getStarvationProcess() {
        return starvationProcess;
    }

    public void run(jobQueue jq) {
        int num = 0;
        while (num != jq.getNumOfProcsess()) {
            List<PCB> readyProcesses = readyQueue.getReadyProcesses();
            if (readyProcesses.isEmpty()) {
                System.out.println("Ready Queue is empty. Scheduling complete.");
                break;
            }
            PCB highestPriorityProcess = readyProcesses.get(0);
            for (PCB process : readyProcesses) {
                process.incrementWaitingCycles();
                if (process.getWaitingCycles() >= StarvationThreshold) {
                    if (!starvationProcess.contains(process.getPid()))
                        starvationProcess.add(process.getPid());
                }
                if (process.getPriority() > highestPriorityProcess.getPriority()) {
                    highestPriorityProcess = process;
                }
            }
            num++;
            highestPriorityProcess.setWaitingTime(currentTime);
            highestPriorityProcess.setState("RUNNING");
            timeList.add(highestPriorityProcess.getWaitingTime());
            currentTime += highestPriorityProcess.getBurstTime();
            highestPriorityProcess
                    .setTurnaroundTime(highestPriorityProcess.getWaitingTime() + highestPriorityProcess.getBurstTime());
            if (!processList.contains(highestPriorityProcess.getPid()))
                processList.add(highestPriorityProcess.getPid());
            systemCalls.terminated(highestPriorityProcess);
            processCount++;
            totalWaitingTime += highestPriorityProcess.getWaitingTime();
            totalTurnaroundTime += highestPriorityProcess.getTurnaroundTime();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double averageWaitingTime = (double) totalWaitingTime / processCount;
        double averageTurnaroundTime = (double) totalTurnaroundTime / processCount;

            System.out.println("\n=== Statistics ===");
        printGanttChart();
        System.out.println("Total processes executed: " + processCount);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println("Average turnaround time: " + averageTurnaroundTime);
        System.out.println("Starvation processes: " + getStarvationProcess());
        System.out.println();

    }

    public void printGanttChart() {
        calculateNumOfSpaceٍ(processList);
        for (int i = 0; i < numberOfSpace; i++)
            System.out.print("-");
        System.out.println();
        printGanttChartProcess(processList);
        System.out.println();
        for (int i = 0; i < numberOfSpace; i++)
            System.out.print("-");
        System.out.println();
        printGanttChartTime(timeList);
        System.out.print(currentTime);
        System.out.println();
//		System.out.println("average waiting times: " + avgWaitingTime + ".");
//		System.out.println("average turnaround times: " + avgTurnaroundTime + ".");
        System.out.println();
    }

    public void calculateNumOfSpaceٍ(ArrayList<Integer> process) {
        numberOfSpace = 0;
        for (int i = 0; i < process.size(); i++) {
            if (process.get(i) < 10)
                numberOfSpace += 7;
            else
                numberOfSpace += 8;
        }
    }

    public void printGanttChartProcess(ArrayList<Integer> process) {
        for (int i = 0; i < process.size(); i++)
            System.out.print("|  P" + process.get(i) + "  ");
        System.out.print("|");
    }

    public void printGanttChartTime(ArrayList<Integer> time) {
        for (int i = 0; i < time.size(); i++) {
            if (i + 1 < 10) {
                if (i == 0)
                    System.out.print(time.get(i) + "      ");
                else if (i + 1 != time.size() && time.get(i) / 100 <= 0 && time.get(i + 1) / 100 <= 0)
                    System.out.print(time.get(i) + "     ");
                else if (i + 1 != time.size() && time.get(i) / 100 <= 0 && time.get(i + 1) / 100 > 0)
                    System.out.print(time.get(i) + "    ");
                else if (i + 1 != time.size() && ((time.get(i) / 1000 <= 0 && time.get(i + 1) / 1000 > 0)
                        || (time.get(i) / 10000 <= 0 && time.get(i + 1) / 1000 > 0)))
                    System.out.print(time.get(i) + "   ");
                else
                    System.out.print(time.get(i) + "    ");

            } else {
                if (i == 0)
                    System.out.print(time.get(i) + "       ");
                else if (i + 1 != time.size() && time.get(i) / 100 <= 0 && time.get(i + 1) / 100 <= 0)
                    System.out.print(time.get(i) + "      ");
                else if (i + 1 != time.size() && time.get(i) / 100 <= 0 && time.get(i + 1) / 100 > 0)
                    System.out.print(time.get(i) + "     ");
                else if (i + 1 != time.size() && ((time.get(i) / 1000 <= 0 && time.get(i + 1) / 1000 > 0)
                        || (time.get(i) / 10000 <= 0 && time.get(i + 1) / 1000 > 0)))
                    System.out.print(time.get(i) + "    ");
                else
                    System.out.print(time.get(i) + "     ");
            }
        }

    }
}