public class PCB {
    private int PID;
    private String state;
    private int burstTime;
    private int requiredMemory;
    private int turnaroundTime;
    private int waitingTime;
    private int priority;
    
    public PCB(int pid, int burstTime, int priority, int requiredMemory, String state ) {
        this.PID = pid;
        this.state = state;
        this.burstTime = burstTime;
        this.requiredMemory = requiredMemory;
        this.priority = priority;
    }

    // Getters and Setters
    public int getPID() {
        return PID;
    }
    public void setPID(int PID) {
        this.PID = PID;
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
    public void setRequiredMemory(int requiredMemory) {
        this.requiredMemory = requiredMemory;
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


}