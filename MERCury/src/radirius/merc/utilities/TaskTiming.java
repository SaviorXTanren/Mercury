package radirius.merc.utilities;

import java.util.concurrent.CopyOnWriteArrayList;

import radirius.merc.exceptions.MERCuryException;

/**
 * An easy to use utility for one-shot, and reccuring timertasks. Operates on
 * seperate thread.
 * 
 * @author wessles
 */

public class TaskTiming {
	private static CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<Task>();

	/**
	 * Adds a task.
	 * 
	 * @param task
	 *            The task to add.
	 */
	public static void addTask(Task task) {
		tasks.add(task);
	}

	/** Updates the tasks. */
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

		/**
		 * @param timeout
		 *            The time to pass before the task is started.
		 */
		public Task(long timeout) {
			this(timeout, 1);
		}

		/**
		 * @param timeout
		 *            The time to pass before the task is started.
		 * @param reccurances
		 *            The number of times the task repeats (infinite if less
		 *            than 1).
		 */
		public Task(long timeout, int reccurances) {
			this(timeout, reccurances, false);
		}

		/**
		 * @param timeout
		 *            The time to pass before the task is started.
		 * @param loop
		 *            Whether the task is to recur infinitely.
		 */
		public Task(long timeout, boolean loop) {
			this(timeout, loop ? -1 : 1);
		}

		/**
		 * @param timeout
		 *            The time to pass before the task is started.
		 * @param reccurances
		 *            The number of times the task repeats (infinite if less
		 *            than 1).
		 * @param loop
		 *            Whether the task is to recur infinitely.
		 */
		public Task(long timeout, int reccurances, boolean loop) {
			recur = reccurances;

			birth = System.currentTimeMillis();

			if (timeout <= 0)
				try {
					throw new MERCuryException("Timeout must be at least one millisecond!");
				} catch (MERCuryException e) {
					e.printStackTrace();
				}
			this.timeout = timeout;
		}

		public boolean cancelled = false;

		public void cancel() {
			cancelled = true;
		}

		public abstract void run();
	}
}