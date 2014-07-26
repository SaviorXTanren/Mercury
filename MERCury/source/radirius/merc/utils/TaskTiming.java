package radirius.merc.utils;

import java.util.concurrent.CopyOnWriteArrayList;

import radirius.merc.exceptions.TaskException;

/**
 * An easy to use utility for one-shot, and reccuring timertasks. Operates on
 * seperate thread.
 * 
 * @author wessles
 */

public class TaskTiming implements Runnable {
  private static boolean running = true;
  
  private static CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<Task>();
  
  /** Initializes the timing thread. */
  public static void init() {
    Thread t = new Thread(new TaskTiming());
    t.setName("merc_timertask");
    t.start();
  }
  
  /** Adds a task. */
  public static void addTask(Task task) {
    tasks.add(task);
  }
  
  /** Cleans up the timing thread. */
  public static void cleanup() {
    running = false;
  }
  
  @Override
  public void run() {
    while (running) {
      // No need to do this if there is nothing to loop.
      if (tasks.isEmpty())
        continue;
      
      for (Task task : tasks) {
        long time = System.currentTimeMillis();
        long past = time - task.birth;
        
        if (past > task.timeout) {
          task.run();
          
          if (task.recur == 0)
            tasks.remove(task);
          else {
            if (task.recur > 0)
              task.recur--;
            
            task.birth = time;
          }
        }
      }
      
      // This should fix a LOT of laggy issues. Yes, accuracy will be off
      // by 1% of a second, but I doubt that you will have too many
      // issues.
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  public abstract static class Task {
    public int recur;
    public long birth, timeout;
    
    public Task(long timeout) {
      this(timeout, 0);
    }
    
    public Task(long timeout, int reccurances) {
      recur = reccurances;
      
      birth = System.currentTimeMillis();
      
      if (timeout <= 0)
        try {
          throw new TaskException("Timeout must be at least one millisecond!");
        } catch (TaskException e) {
          e.printStackTrace();
        }
      this.timeout = timeout;
    }
    
    public abstract void run();
  }
}