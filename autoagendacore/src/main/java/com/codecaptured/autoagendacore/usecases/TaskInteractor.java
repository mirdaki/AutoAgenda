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
	public static void addTask(UserTask newTask)
	{
		// Create a new ID
		UUID id = UUID.randomUUID();

		// Add ID to UserTask
		newTask.setId(id);

		// Make the task
		Task task = new Task(newTask.getId(), newTask.getTitle(), newTask.getDescription(),
						newTask.getCompleted(), newTask.getDueDate(), newTask.getTimeRequiredInMinutes(),
						newTask.getPriorityLevel(), newTask.getTags());

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addTask(task, Schedule.getCurrentTasks());
	}

	/**
	 * Change an existing scheduled event and reschedule it if using the default date/time
	 * @param originalTask The original task to be changed
	 * @param newTask The new changed task
	 */
	public static void modifyTask(UserTask originalTask, UserTask newTask)
	{
		// Make the IDs the same
		newTask.setId(originalTask.getId());

		// Remove the old task
		Scheduler.removeTask(originalTask.getId());

		// Make the task
		Task task = new Task(newTask.getId(), newTask.getTitle(), newTask.getDescription(),
						newTask.getCompleted(), newTask.getDueDate(), newTask.getTimeRequiredInMinutes(),
						newTask.getPriorityLevel(), newTask.getTags());

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addTask(task, Schedule.getCurrentTasks());
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
