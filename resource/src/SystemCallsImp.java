import java.util.ArrayList;
import java.util.List;

public class SystemCallsImp implements SystemCalls {

    private final int memory = 2400; // MB of memory
    private int memoryAvailable = memory;
    
    // maybe not put here
    private PCB currentProcess;
    private List<PCB> readyQueue;
    // maybe not put here
    
    public SystemCallsImp() {
        readyQueue = new ArrayList<>();
    }
    
    public void allocate(int size) {
        
    }

    public void swap(String filename) {
        
    }
    
    public void exec(PCB process) {
        
    }
    
    public void exit(PCB process) {
        
    }
    
    public void deallocate(int size) {
        
    }
    
    public void getpid(PCB process) {
        
    }
    
    public void print(String str) {
        
    }
    
    public void fork(int pid, int burstTime, int priority, int requiredMemory, String state) {
        
    }

}