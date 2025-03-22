import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderThread implements Runnable {
    
    private String fileName;
    private jobQueue jobQueue;

    public FileReaderThread(String fileName, jobQueue jobQueue){
      this.fileName = fileName;
      this.jobQueue = jobQueue;
    }
    
    public void run() {
      try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

        String line = reader.readLine();

        while (line != null) {
          String[] parts = line.split("[:;]");

          // Validate the line format
          if (parts.length!= 4) {
            System.err.println("Invalid line format: " + line);
            continue;
          }

          // Create a new process and add it to the job queue
          int processID = Integer.parseInt(parts[0]);
          int burstTime = Integer.parseInt(parts[1]);
          int priority = Integer.parseInt(parts[2]);
          int memoryRequired = Integer.parseInt(parts[3]);
          PCB process = new PCB(processID, burstTime, priority, memoryRequired);

          jobQueue.addJob(process);
          line = reader.readLine();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  
}
