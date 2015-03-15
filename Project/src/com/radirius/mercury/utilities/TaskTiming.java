package com.radirius.mercury.utilities;

import com.radirius.mercury.exceptions.MercuryException;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An easy to use utility for one-shot, and recurring timer-tasks. Operates on separate thread.
 *
 * @author wessles
 */
public class TaskTiming {
	private static CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();

	/**
	 * Adds a task.
	 *
	 * @param task
	 * 		The task to add.
	 */
	public static void addTask(Task task) {
		tasks.add(task);
	}

	/**
	 * Updates the tasks.
	 */
	public static void update() {
		for (Task task : tasks) {
			long time = System.currentTimeMillis();
			long past = time - task.birth;

			if (past > task.timeout) {
				if (task.recur == 0 || task.cancelled) {
					tasks.remove(task);
					continue;
				}

				task.run();

				if (task.recur > 0)
					task.recur--;

				task.birth = time;
			}
		}

	}

	public static abstract class Task {
		public int recur;
		public long birth, timeout;
		public boolean cancelled = false;

		/**
		 * @param timeout
		 * 		The time to pass before the task is started.
		 */
		public Task(long timeout) {
			this(timeout, 1);
		}

		/**
		 * @param timeout
		 * 		The time to pass before the task is started.
		 * @param recurrences
		 * 		The number of times the task repeats (infinite if less than 1).
		 */
		public Task(long timeout, int recurrences) {
			this(timeout, recurrences, false);
		}

		/**
		 * @param timeout
		 * 		The time to pass before the task is started.
		 * @param loop
		 * 		Whether the task is to recur infinitely.
		 */
		public Task(long timeout, boolean loop) {
			this(timeout, loop ? -1 : 1);
		}

		/**
		 * @param timeout
		 * 		The time to pass before the task is started.
		 * @param recurrences
		 * 		The number of times the task repeats (infinite if less than 1).
		 * @param loop
		 * 		Whether the task is to recur infinitely.
		 */
		public Task(long timeout, int recurrences, boolean loop) {
			recur = recurrences;

			birth = System.currentTimeMillis();

			if (timeout <= 0)
				try {
					throw new MercuryException("Timeout must be at least one millisecond!");
				} catch (MercuryException e) {
					e.printStackTrace();
				}
			this.timeout = timeout;
		}

		public void cancel() {
			cancelled = true;
		}

		public abstract void run();
	}
}
