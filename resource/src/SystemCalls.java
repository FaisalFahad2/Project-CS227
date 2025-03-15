public interface SystemCalls {
  
  public void swap(String filename); // maybe not necessary

  public void exec(PCB process);
  public void exit(PCB process);
  public void allocate(int size);
  public void deallocate(int size);
  public void getpid(PCB process);
  public void print(String str);
  public void fork (int pid, int burstTime, int priority, int requiredMemory, String state);
}