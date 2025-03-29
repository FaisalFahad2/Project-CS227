
public class SystemCallsImp implements SystemCalls {

    private jobQueue jobQueue;
    private ReadyQueue readyQueue;
    private memoryManagment memoryManager;
    
    public SystemCallsImp(jobQueue jobQueue, ReadyQueue readyQueue, memoryManagment memoryManagment) {
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
        this.memoryManager = memoryManagment;
    }
    
    public void crateProcess(int pid, int burstTime, int priority, int requiredMemory) {
        PCB process = new PCB(pid, burstTime, priority, requiredMemory);
        jobQueue.addJob(process);
    }

    public boolean allocate(PCB process) {
        int requiredMemory = process.getRequiredMemory();  
        return memoryManager.allocateMemory(requiredMemory);
    }
    
    public void exec(PCB process) {
        
    }
    
    public void terminated(PCB process) {
        
        process.setState("TERMINATED");
        jobQueue.removeJob(process);
        readyQueue.removeProcess(process);
        deallocate(process);

        synchronized (this) {
            this.notify();
        }
    }
    
    public void deallocate(PCB process) {
        int requiredMemory = process.getRequiredMemory();
        memoryManager.deallocateMemory(requiredMemory);
    }
    
    public void getpid(PCB process) {
        
    }

    public boolean isJobQueueEmpty() {
        return jobQueue.isEmpty();
    }
    
    public void print(String str) {
        System.out.println(str);
    }
    
    public PCB getJobQueue() {
        return jobQueue.getJob();
    }
    
    public PCB getNextReadyQueue() {
        return readyQueue.getNextProcess();
    }
    public boolean removeJob(PCB process) {
        return jobQueue.removeJob(process);
    }
    public void addToReadyQueue(PCB process) {
        readyQueue.addProcess(process);
        process.setState("READY");
    }
   

}