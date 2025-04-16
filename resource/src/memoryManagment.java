public class memoryManagment {
  private final int memory = 2048;
  private int usedmemory;

  public memoryManagment() {
    this.usedmemory = 0;
  }
  
  public synchronized boolean allocateMemory(int requiredMemory) {

    if (requiredMemory <= 0) {
//      System.out.println("Memory request must be greater than zero");
      return false;
    }

    if (requiredMemory + usedmemory <= memory) {
      usedmemory += requiredMemory;
      return true;
    }
    return false;
  }

  public synchronized void deallocateMemory(int requiredMemory) {
    if (requiredMemory <= 0) {
      System.out.println("Memory request must be greater than zero");
      return;
    }
    
    if (requiredMemory <= usedmemory) {
      usedmemory -= requiredMemory;
    } else {
      System.out.println("Cannot deallocate more memory than allocated");
    }
  }
  public int getFreeMemory() {
    return memory - usedmemory;
  }

  public int getUsedMemory() {
    return usedmemory;
  }

  public int getTotalMemory() {
    return memory;
  }
}