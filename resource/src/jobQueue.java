import java.util.ArrayList;

public class jobQueue {
  private ArrayList<PCB> jobQueue = new ArrayList<>();
  private int currentProcessIndex = 0;
  
  public synchronized void addJob(PCB process) {
      jobQueue.add(process);
  }

  // get for ready queue 
  public synchronized PCB getNextJob() {
      return jobQueue.get(currentProcessIndex++);
  }

  public synchronized boolean isEmpty() {
      return jobQueue.isEmpty();
  }

  // remove if terminated
  public synchronized boolean removeJob(PCB process) {
      return jobQueue.remove(process);
  }
}
