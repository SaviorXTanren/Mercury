package com.radirius.merc.util;

import java.util.concurrent.CopyOnWriteArrayList;

import com.radirius.merc.exc.TaskException;

/**
 * An easy to use utility for one-shot, and reccuring timertasks. Operates on
 * seperate thread.
 * 
 * @from MERCury in com.radirius.merc.util
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class TaskTiming implements Runnable
{
    private static boolean running = true;
    
    private static CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<Task>();
    
    /** Initializes the timing thread. */
    public static void init()
    {
        Thread t = new Thread(new TaskTiming());
        t.setName("merc_timertask");
        t.start();
    }
    
    /** Adds a task. */
    public static void addTask(Task task)
    {
        tasks.add(task);
    }
    
    /** Cleans up the timing thread. */
    public static void cleanup()
    {
        running = false;
    }
    
    @Override
    public void run()
    {
        while (running)
            for (Task task : tasks)
            {
                long time = System.currentTimeMillis();
                long past = time - task.birth;
                
                if (past > task.timeout)
                {
                    task.run();
                    
                    if (task.recur == 0)
                        tasks.remove(task);
                    else
                    {
                        if (task.recur > 0)
                            task.recur--;
                        
                        task.birth = time;
                    }
                }
            }
    }
    
    public abstract static class Task
    {
        public int recur;
        public long birth, timeout;
        
        public Task(long timeout)
        {
            this(timeout, 0);
        }
        
        public Task(long timeout, int reccurances)
        {
            recur = reccurances;
            
            birth = System.currentTimeMillis();
            
            if (timeout <= 0)
                try
                {
                    throw new TaskException("Timeout must be at least one millisecond!");
                } catch (TaskException e)
                {
                    e.printStackTrace();
                }
            this.timeout = timeout;
        }
        
        public abstract void run();
    }
}