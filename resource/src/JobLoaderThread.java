public class JobLoaderThread implements Runnable {

  private SystemCalls sysCall;
  private jobQueue jobQueue;

  public JobLoaderThread(SystemCalls systemCalls, jobQueue jobQueue) {
    if (systemCalls == null) {
      throw new IllegalArgumentException("System Call cannot be null");
    }
    this.sysCall = systemCalls;
    this.jobQueue = jobQueue;
  }

  public synchronized void run() {
    int numberOfProcesses = jobQueue.getNumOfProcsess();
    int count = 0;
    while (count < numberOfProcesses) {
      PCB process = sysCall.getJobQueue();

      if (process == null)
        continue;

      if (sysCall.getMemorySize() < process.getRequiredMemory()) {
        System.out.println("Not enough memory for process " + process.getPid() + " with required memory " + process.getRequiredMemory());
        jobQueue.removeJob(process);
        continue;
      }

      synchronized (sysCall) {
        while (!sysCall.allocate(process)) {
          try {
            sysCall.wait();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
        sysCall.addToReadyQueue(process);
      }
      count++;
    }
  }
}
