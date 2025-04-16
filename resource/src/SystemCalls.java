public interface SystemCalls {


    public void exec(PCB process);

    public void terminated(PCB process);

    public boolean allocate(PCB process);

    public void deallocate(PCB process);

    public void getpid(PCB process);

    public void print(String str);

    public void crateProcess(int pid, int burstTime, int priority, int requiredMemory);

    public boolean isJobQueueEmpty();

    public PCB getJobQueue();

    public PCB getNextReadyQueue();

    public boolean removeJob(PCB process);

    public void addToReadyQueue(PCB process);

    public int getMemorySize();

}