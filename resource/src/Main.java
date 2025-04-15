import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

//    	
		int userInput;
		boolean state = true;
		do {
			try {
				System.out.println("Choose the algorithm:");
				System.out.println("1. First Come First Serve.");
				System.out.println("2. Round Robin.");
				System.out.println("3. Priority scheduling.");
				System.out.println("4. Use all algorithms.");
				System.out.println("5. exit.");
				System.out.print(">> ");
				userInput = input.nextInt();
				switch (userInput) {
				case 1:
					case_1();
					break;
				case 2:
					case_2();
					break;
				case 3:
					case_3();
					break;
				case 4:
					case_1();
					System.out.println();
					case_2();
					System.out.println();
					case_3();
					break;
				case 5:
					state = false;
					break;
				default:
					System.out.println();
					System.out.println("!Invalid input!");
					System.out.println();
				}
			} catch (InputMismatchException e) {
				System.out.println();
				System.out.println("!Invalid input!");
				System.out.println();
				input.nextLine();

			}

		} while (state);

	}
	public static void case_3() {
		System.out.println("=========================================");
		System.out.println("========== Priority scheduling ==========");
		jobQueue jobQueue = new jobQueue();
		ReadyQueue readyQueue = new ReadyQueue();
		memoryManagment memoryManager = new memoryManagment();
		SystemCalls system = new SystemCallsImp(jobQueue, readyQueue, memoryManager);
		FileReaderThread io = new FileReaderThread("src/job2.txt", system);
		Thread t1 = new Thread(io);
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JobLoaderThread jobLoaderThread = new JobLoaderThread(system);

		Thread t2 = new Thread(jobLoaderThread);
		t2.start();
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PriorityScheduling p = new PriorityScheduling(system,readyQueue);
		p.run(jobQueue);
		try {
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void case_2() {
		System.out.println("======================================");
		System.out.println("==========  Round Robin - 7 ==========");
		jobQueue jobQueue = new jobQueue();
		ReadyQueue readyQueue = new ReadyQueue();
		memoryManagment memoryManager = new memoryManagment();
		SystemCalls system = new SystemCallsImp(jobQueue, readyQueue, memoryManager);
		FileReaderThread io = new FileReaderThread("src/job2.txt", system);
		Thread t1 = new Thread(io);
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JobLoaderThread jobLoaderThread = new JobLoaderThread(system);

		Thread t2 = new Thread(jobLoaderThread);
		t2.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		RoundRobin r = new RoundRobin(7);
		r.schedule(readyQueue, jobQueue, system);
		try {
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void case_1() {
		System.out.println("=====================================");
		System.out.println("========== FCFS Scheduling ==========");
		jobQueue jobQueue = new jobQueue();
		ReadyQueue readyQueue = new ReadyQueue();
		memoryManagment memoryManager = new memoryManagment();
		SystemCalls system = new SystemCallsImp(jobQueue, readyQueue, memoryManager);
		FileReaderThread io = new FileReaderThread("src/job2.txt", system);
		Thread t1 = new Thread(io);
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JobLoaderThread jobLoaderThread = new JobLoaderThread(system);
		Thread t2 = new Thread(jobLoaderThread);
		t2.start();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		FCFS fcfs = new FCFS(readyQueue);

		fcfs.schedule(readyQueue, jobQueue, system);
		try {
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
