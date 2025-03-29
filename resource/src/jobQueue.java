import java.util.ArrayList;
import java.util.List;

public class jobQueue {
  private List<PCB> jobQueueList;
  private int currentProcessIndex;

  public jobQueue() {
    this.jobQueueList = new ArrayList<>();
    this.currentProcessIndex =  -1; 
  }

  public synchronized void addJob(PCB process) {
    if (process == null) {
      System.out.println("Process is null");
      return;
    }

    if (jobQueueList.contains(process)) {
      System.out.println("Process already exists in the job queue");
      return;
    }
    jobQueueList.add(process);
  }

  // get for ready queue 
  public synchronized PCB getJob() {

    if (jobQueueList.isEmpty()) {
      System.out.println("Job queue is empty");
      return null;
    }

    currentProcessIndex = (currentProcessIndex + 1) % jobQueueList.size();
    return jobQueueList.get(currentProcessIndex);
  }

  public synchronized boolean isEmpty() {
    return jobQueueList.isEmpty();
  }

  // remove if terminated
  public synchronized boolean removeJob(PCB process) {
    if (process == null) {
      System.out.println("Process is null");
      return false;
    }

    if (jobQueueList.remove(process)) {
                if (!isEmpty()) {
                    currentProcessIndex = (currentProcessIndex - 1 + jobQueueList.size()) % jobQueueList.size();
                } else {
                    currentProcessIndex = -1;
                }
                return true;
            }
            return false;
  }

  public void printJobQueue() {
    System.out.println("Job Queue:");
    for (PCB process : jobQueueList) {
      System.out.println(process.toString());
    }
  }
  public List<PCB> getJobQueue() {
    return jobQueueList;
  }
}
