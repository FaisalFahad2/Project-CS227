import java.util.ArrayList;
import java.util.List;

public class ReadyQueue {
    private List<PCB> readyQueue;
    private int numReady;
    private int currentProcessIndex;
    
    public ReadyQueue() {
        numReady = 0;
        currentProcessIndex = -1; // -1 because we increment before returning
        readyQueue = new ArrayList<>();
    }
    
    public synchronized void addProcess(PCB process) {

        if (process == null) {
            System.out.println("Process is null");
            return;
        }

        readyQueue.add(process);
        numReady++;
    }
    public synchronized PCB getNextProcess() {
        if (isEmpty()) {
            System.out.println("No processes in ready queue");
            return null;
        }
        currentProcessIndex = (currentProcessIndex + 1) % numReady;
        return readyQueue.get(currentProcessIndex);
    }

    public synchronized boolean isEmpty() {
        return numReady == 0;
    }
    public synchronized void removeProcess(PCB process) {
        if (process == null) {
            System.out.println("Process is null");
            return;
        }
        if (isEmpty()) {
            System.out.println("Ready queue is empty");
            return;
        }
        if (readyQueue.remove(process)) {
            numReady--;
            if (currentProcessIndex >= numReady && numReady > 0) {
                currentProcessIndex = numReady - 1;
            }
        } else {
            System.out.println("Process not found in ready queue");
        }
    }
    
    //testing
    public synchronized void printReadyQueue() {
        System.out.println("Ready Queue:");
        for (PCB process : readyQueue) {
            System.out.println(process.toString());
        }
    }
}
