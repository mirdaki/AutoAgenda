package com.codecaptured.autoagendacore.usecases;

import com.codecaptured.autoagendacore.entities.Schedule;
import com.codecaptured.autoagendacore.entities.Task;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.UUID;
import java.util.Date;

/**
 * This is used when interacting (creating, modifying, removing) tasks. Also controls the interface
 * needed for talking to this class
 */
public class TaskInteractor
{
	// TODO: Should also return some sort of message to indicate if it was scheduled properly or not

	/**
	 * Add a new task to be scheduled
	 * @param newTask The new task
	 */
	public static boolean addTask(UserTask newTask)
	{
		// Create a new ID
		UUID id = UUID.randomUUID();

		// Add ID to UserTask
		newTask.setId(id);

		// Make the task
		Task task = new Task(newTask.getId(), newTask.getTitle(), newTask.getDescription(),
						newTask.getCompleted(), newTask.getDueDate(), newTask.getTimeRequiredInMinutes(),
						newTask.getPriorityLevel(), newTask.getTags());

		// Fix error if Schedule has not be instantiated
		if (Schedule.getCurrentTasks() == null || Schedule.getCurrentEvents() == null)
		{
			new Schedule();
		}

		// Timeblock for debug
		TimeBlock[] tb = {};

		// Add to scheduler to decide where to put it in the schedule
		tb = Scheduler.addTask(task);

		if (tb != null)
		{
			System.out.println("Task start date:   " + tb[0].getStartTime());

			System.out.println("Mins Required:     " + tb[0].getNumberOfMinutes());

			System.out.println(" ");

			// Updated user task
			newTask.setTimeBlocks(task.getTaskTimes());

			return true;
		}
		else
		{
			//System.out.println("Timeblock is null.");
		}

		return false;
	}

	/**
	 * Change an existing scheduled event and reschedule it if using the default date/time
	 * @param originalTask The original task to be changed
	 * @param newTask The new changed task
	 */
	public static boolean modifyTask(UserTask originalTask, UserTask newTask)
	{
		// Make the IDs the same
		newTask.setId(originalTask.getId());

		// Remove the old task
		Scheduler.removeTask(originalTask.getId());

		// Make the task
		Task task = new Task(newTask.getId(), newTask.getTitle(), newTask.getDescription(),
						newTask.getCompleted(), newTask.getDueDate(), newTask.getTimeRequiredInMinutes(),
						newTask.getPriorityLevel(), newTask.getTags());

		// Timeblock for debug
		TimeBlock[] tb = {};

		// Add to scheduler to decide where to put it in the schedule
		tb = Scheduler.addTask(task);

		if (tb != null)
		{
			System.out.println("Task start date:   " + tb[0].getStartTime());

			System.out.println("Mins Required:     " + tb[0].getNumberOfMinutes());

			System.out.println(" ");

			// Updated user task
			newTask.setTimeBlocks(task.getTaskTimes());
		}

		if(tb.length == 0)
			return false;
		else
			return true;
	}

	/**
	 * Remove a task from the schedule
	 * @param oldTask The task to be removed
	 */
	public static void removeTask(UserTask oldTask)
	{
		// Delete old task
		Scheduler.removeTask(oldTask.getId());
	}

	/**
	 * Convert a user task to a task
	 * @param userTask User task to be based off of
	 * @return task with same data as userTask
	 */
	public static Task userTaskToTask(UserTask userTask)
	{
		// Make the event
		Task task =  new Task(userTask.getId(), userTask.getTitle(), userTask.getDescription(),
						userTask.getCompleted(), userTask.getDueDate(), userTask.getTimeRequiredInMinutes(),
						userTask.getPriorityLevel(), userTask.getTags());
		task.setTaskTimes(userTask.getTimeBlocks());
		return task;
	}

	/**
	 * Convert a task to a user event
	 * @param task Task to be based off of
	 * @param userTask Must be passed in (interfaces can't be instantiated)
	 * @return userTask with same data as task
	 */
	public static UserTask taskToUserTask(Task task, UserTask userTask)
	{
		// Set the passed user event to have all the attributes of the event
		userTask.setId(task.getId());
		userTask.setTitle(task.getTitle());
		userTask.setDescription(task.getDescription());
		userTask.setCompleted(task.isCompleted());
		userTask.setDueDate(task.getDueDate());
		userTask.setTimeRequiredInMinutes(task.getTimeRequiredInMinutes());
		userTask.setPriorityLevel(task.getPriorityLevel());
		userTask.setTags(task.getTags());
		userTask.setTimeBlocks(task.getTaskTimes());
		return userTask;
	}

	/**
	 * Convert multiple tasks to user tasks
	 * @param tasks Tasks to be based off of
	 * @param userTasks Must be passed in (interfaces can't be instantiated)
	 * @return user tasks with the same data as tasks
	 */
	protected static UserTask[] tasksToUserTasks(Task[] tasks, UserTask[] userTasks)
	{
		// Value to be returned
		UserTask[] convertedTasks = new UserTask[tasks.length];

		// Convert each task to a user task
		for (int i = 0; i < tasks.length; i++)
		{
			convertedTasks[i] = taskToUserTask(tasks[i], userTasks[0]);
		}

		// Return new user tasks
		return convertedTasks;
	}

	/**
	 * The task data object used to talk with the use cases. Includes default values for tasks
	 */
	public interface UserTask
	{
		// Default values
		String DEFAULT_TITLE = "";
		String DEFAULT_DESCRIPTION = "";
		Boolean DEFAULT_COMPLETED = false;
		Date DEFAULT_DUE_DATE = new Date(0);
		int DEFAULT_TIME_REQUIRED_IN_MINUTES = 0;
		int DEFAULT_PRIORITY_LEVEL = 5;
		String[] DEFAULT_TAGS = {""};

		// Getters and setters
		// Values set by user
		String getTitle();
		void setTitle(String title);
		String getDescription();
		void setDescription(String description);
		Boolean getCompleted();
		void setCompleted(Boolean completed);
		Date getDueDate();
		void setDueDate(Date dueDate);
		int getTimeRequiredInMinutes();
		void setTimeRequiredInMinutes(int timeRequiredInMinutes);
		int getPriorityLevel();
		void setPriorityLevel(int priorityLevel);
		String[] getTags();
		void setTags(String[] tags);

		// Set by software
		UUID getId();
		void setId(UUID id);
		TimeBlock[] getTimeBlocks();
		void setTimeBlocks(TimeBlock[] timeBlocks);
	}

}