import java.util.HashMap;
import java.util.List;

public class RoundRobin {
	private final int quantum;
	int time[];
	public static String processNum;
	public static String processTime;
	static int numberOfSpace;
	int num = 0;
	int currentTime = 0;
	double totalWaitingTime = 0;
	double totalTurnaroundTime = 0;
	int finishedProcessesCount = 0;
	double avgWaitingTime;
	double avgTurnaroundTime;

	public RoundRobin(int quantum) {

		this.quantum = quantum;

	}

	// this method may changes as it produces bad avg

	public void schedule(ReadyQueue readyQueue, jobQueue jq, SystemCalls sc) {
		if (readyQueue == null) {
			throw new IllegalArgumentException("ReadyQueue cannot be null");
		}
		processNum = "";
		processTime = "";
		HashMap<Integer, Integer> originalBurstTimes = new HashMap<>();

		while (num != jq.getNumOfProcsess()) {
			PCB process = readyQueue.getNextProcess();
			if (process == null) {
				continue;
			}

			if (!originalBurstTimes.containsKey(process.getPid())) {
				originalBurstTimes.put(process.getPid(), process.getBurstTime());
			}

			int remainingBurst = process.getBurstTime();

			int timeSlice = Math.min(quantum, remainingBurst);
			try {
				Thread.sleep(timeSlice);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			processTime += currentTime + " ";
			currentTime += timeSlice;
			processNum += process.getPid() + " ";
			remainingBurst -= timeSlice;
			process.setBurstTime(remainingBurst);

			if (remainingBurst > 0) {
				readyQueue.addProcess(process);
			} else {
				process.setTurnaroundTime(currentTime);
				int originalBurst = originalBurstTimes.get(process.getPid());
				int waitingTime = currentTime - originalBurst;
				process.setWaitingTime(waitingTime);

				totalTurnaroundTime += process.getTurnaroundTime();
				totalWaitingTime += process.getWaitingTime();

				sc.terminated(process);
				num++;
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
		calculateNumOfSpaceٍ(processNum);
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

	public void calculateNumOfSpaceٍ(String proNum) {
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