package program;

import adt.Node;
import adt.SCLList;

/**
 * The Class Staff that defines a simple staff.
 */
public class Staff {

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The tasks (SCLList object). */
	private SCLList<Task> tasks;

	/**
	 * Instantiates a new staff.
	 *
	 */
	public Staff(String username, String password) {
		this.username = username;
		this.password = password;
		tasks = new SCLList<>();
	}

	/**
	 * Adds the task.
	 *
	 */
	public void addTask(Task task) {
		tasks.add(task);
	}

	/**
	 * Removes the task.
	 *
	 */
	public boolean removeTask(Task task) {
		return tasks.delete(task);
	}

	/**
	 * Gets the task with max priority.
	 *
	 */
	public Task getTaskWithMaxPriority() {
		Task max = null; // Declare task that will contain maximum priority.
		for (Node<Task> next = tasks.getFirst(); next != tasks.getLast(); next = next.getNext()) { 
			// Loop through the all tasks (except last).

			if ((max == null || next.getData().getPriority() > max.getPriority()) && !next.getData().isCompleted()) {
				// If max task is still null or current (next) task in loop has a higher priority and
				// of course this task shouldn't be completed.

				max = next.getData(); // Update max.
			}
		}
		if (max == null) { // If all tasks in a loop are completed.
			if (tasks.getLast().getData().isCompleted()) { // If the last task is also completed.
				return null;
			} else { // If the last task isn't completed.
				return tasks.getLast().getData();
			}
		} else { // If max is not null.
			if (tasks.getLast().getData().getPriority() > max.getPriority() && !tasks.getLast().getData().isCompleted()) {
				return tasks.getLast().getData();
			}
		}
		return max;
	}

	/**
	 * Gets the username.
	 *
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the tasks.
	 *
	 */
	public SCLList<Task> getTasks() {
		return tasks;
	}

}
