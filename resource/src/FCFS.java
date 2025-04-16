import java.util.ArrayList;
import java.util.List;

public class FCFS {
    private ReadyQueue readyQueue;
    int numberOfSpace;
    int currentTime;
    double avgWaitingTime = 0;
    double avgTurnaroundTime = 0;
    private boolean detailedMode = false;
    private ArrayList<Integer> processList = new ArrayList<>();
    private ArrayList<Integer> timeList = new ArrayList<>();

    public FCFS(ReadyQueue readyQueue, boolean detailedMode) {
        if (readyQueue == null) {
            throw new IllegalArgumentException("ReadyQueue cannot be null");
        }
        this.readyQueue = readyQueue;
        this.detailedMode = detailedMode;
    }

    public void schedule(ReadyQueue readyQueue, jobQueue jq, SystemCalls sc) {
        numberOfSpace = 0;
        int num = 0;
        currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        while (!readyQueue.isEmpty() && num != jq.getNumOfProcsess()) {
            List<PCB> executedProcesses = readyQueue.getReadyProcesses();
            for (PCB p : executedProcesses) {
                if (detailedMode) {
                    readyQueue.printReadyQueue();
                    System.out.println("Selected Process: " + p);
                }
                int waitingTime = currentTime;
                int turnaroundTime = waitingTime + p.getBurstTime();
                timeList.add(currentTime);
                processList.add(p.getPid());
                currentTime = turnaroundTime;
                p.setWaitingTime(waitingTime);
                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;
                p.setTurnaroundTime(turnaroundTime);
                p.setState("RUNNING");
                if (detailedMode) {
                    System.out.println("Scheduled Process (now running): " + p);
                }
                sc.terminated(p);
                if (detailedMode) {
                    System.out.println("Terminated Process: " + p);
                }
                num++;

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        System.out.println();
        System.out.println("Average waiting time: " + avgWaitingTime);
        System.out.println("Average turnaround time: " + avgTurnaroundTime);
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
