import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileReaderThread implements Runnable {
  private static final String spliitters = "[:;]";
  private static final int expectedParts = 4;
  private Set<Integer> processIds = new HashSet<>(); 

  private String fileName;
  private SystemCalls systemCalls;

  public FileReaderThread(String fileName, SystemCalls systemCalls) {
    if (fileName == null || systemCalls == null) {
      throw new IllegalArgumentException("fileName and systemCalls cannot be null");
    }
    this.fileName = fileName;
    this.systemCalls = systemCalls;
  }

  public void run() {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

      String line = reader.readLine();

      while (line != null) {
        String[] parts = line.split(spliitters);

        // Validate the line format
        if (parts.length != expectedParts) {
          System.out.println("Invalid line format: " + line);
          continue;
        }

        // Create a new process and add it to the job queue
        int processID = Integer.parseInt(parts[0].trim());
        
        // Check for duplicate process IDs
        if (processIds.contains(processID)) {
          System.out.println("Warning: Duplicate process ID " + processID + " found in the input file!");
        } else {
          processIds.add(processID);
        }

        int burstTime = Integer.parseInt(parts[1].trim());
        int priority = Integer.parseInt(parts[2].trim());
        int memoryRequired = Integer.parseInt(parts[3].trim());

        validateProcessParameters(burstTime, memoryRequired);

        systemCalls.crateProcess(processID, burstTime, priority, memoryRequired);
        
        line = reader.readLine();
      }

    } catch (IOException e) {
      System.out.println("Can't found file");
    } catch (NumberFormatException e) {
      System.out.println("Invalid number format in file");
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid argument: " + e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void validateProcessParameters(int burstTime, int memoryRequired) {
    if (burstTime <= 0) {
      throw new IllegalArgumentException("Burst time must be positive");
    }
    if (memoryRequired <= 0) {
      throw new IllegalArgumentException("Memory required must be positive");
    }
  }
}
