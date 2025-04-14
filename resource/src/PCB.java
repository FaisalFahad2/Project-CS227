public class PCB {
    private final int PID;
    private String state;
    private int burstTime;
    private final int requiredMemory;
    private int turnaroundTime;
    private int waitingTime;
    private int priority;
    private boolean loaded = false;
    private int waitingCycles;

    public PCB(int pid, int burstTime, int priority, int requiredMemory) {
        this.PID = pid;
        this.burstTime = burstTime;
        this.requiredMemory = requiredMemory;
        this.priority = priority;
        this.state = "NEW";
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.waitingCycles=0;
    }

    // Getters and Setters
    public int getPid() {
        return PID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {

        this.state = state;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getRequiredMemory() {
        return requiredMemory;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String toString() {
        return "PID: " + PID + " State: " + state + " Burst Time: " + burstTime + " Required Memory: " + requiredMemory + " Priority: " + priority;
    }


    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public int getWaitingCycles() {
        return waitingCycles;
    }
    public void incrementWaitingCycles() {
        this.waitingCycles++;
    }
}