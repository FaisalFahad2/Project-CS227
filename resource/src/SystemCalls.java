public interface SystemCalls {


    public void terminated(PCB process);

    public boolean allocate(PCB process);

    public void deallocate(PCB process);


    public void crateProcess(int pid, int burstTime, int priority, int requiredMemory);

    public boolean isJobQueueEmpty();

    public PCB getJobQueue();


    public void addToReadyQueue(PCB process);

    public int getMemorySize();

}