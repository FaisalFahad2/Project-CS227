import java.util.ArrayList;
import java.util.List;
public class Schedular {
	
	public static void main( String args[]) {

		jobQueue j = new jobQueue();
		ReadyQueue r = new ReadyQueue();
		memoryManagment m = new memoryManagment();
		SystemCallsImp s = new SystemCallsImp(j, r, m);
		List<PCB> jobQueueList = j.getJobQueue();

		FileReaderThread io = new FileReaderThread("src/job.txt", s);
		Thread t1 = new Thread(io);
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		j.printJobQueue();
		
		JobLoaderThread jobLoaderThread = new JobLoaderThread(s);
		Thread t2 = new Thread(jobLoaderThread);
		t2.start();

		try{
		Thread.sleep(3000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("First");
		r.printReadyQueue();
		s.terminated(jobQueueList.get(0));

		try{
			Thread.sleep(3000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("After Termination of Process 1");
		r.printReadyQueue();
		

		try{
			Thread.sleep(3000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		
		s.terminated(jobQueueList.get(0));
		try{
			Thread.sleep(3000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("After Termination of Process 2");
		r.printReadyQueue();

		try{
			Thread.sleep(3000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		s.terminated(jobQueueList.get(0));
		try{
			Thread.sleep(3000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("After Termination of Process 3");
		r.printReadyQueue();
	

		System.out.println("/////////////////////////////////////////////////////////");
	}

}