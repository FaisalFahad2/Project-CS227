public class JobLoaderThread implements Runnable {

  private SystemCalls sysCall;

  public JobLoaderThread(SystemCalls systemCalls) {
    if (systemCalls == null) {
      throw new IllegalArgumentException("System Call cannot be null");
    }
    this.sysCall = systemCalls;
  }

  public synchronized void run() {
    while (!sysCall.isJobQueueEmpty()) {
      PCB process = sysCall.getJobQueue();

      if (process == null)
        continue;

      synchronized (sysCall) {
        while (!sysCall.allocate(process)) {
//          sysCall.print("Memory allocation failed for process " + process.getPid());
          try {
            sysCall.wait();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
        sysCall.addToReadyQueue(process);
      }

    }
  }
}