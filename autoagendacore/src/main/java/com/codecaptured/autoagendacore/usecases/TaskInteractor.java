package com.codecaptured.autoagendacore.usecases;

import java.util.UUID;
import java.util.Date;
import com.codecaptured.autoagendacore.domain.Task;
import com.codecaptured.autoagendacore.domain.Scheduler;

/**
 * Created by matthew on 2/2/18.
 */

public class TaskInteractor
{
	public static final String DEFAULT_TITLE = "";
	public static final String DEFAULT_DESCRIPTION = "";
	public static final Date DEFAULT_DUE_DATE = new Date(0);
	public static final int DEFAULT_TIME_REQUIRED_IN_MINUTES = 0;
	public static final int DEFAULT_PRIORITY_LEVEL = 5;
	public static final String[] DEFAULT_TAGS = {""};

	// TODO: Should also return some sort of message to indicate if it was scheduled properly or not.
	public static Task addTask(String title, String description, Date dueDate,
	                           int timeRequiredInMinutes, int priorityLevel, String[] tags)
	{
		// Create a new ID
		UUID id = UUID.randomUUID();

		// Make the task
		Task newTask = new UserTask(id, title, description, dueDate, timeRequiredInMinutes,
						priorityLevel, tags);

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addTask(newTask);

		return newTask;
	}

	public static Task modifyTask(UUID id, String title, String description, Date dueDate,
	                              int timeRequiredInMinutes, int priorityLevel, String[] tags)
	{
		// Make a new task with old ID
		Task newTask = new UserTask(id, title, description, dueDate, timeRequiredInMinutes,
						priorityLevel, tags);

		// Delete old task
		Scheduler.removeTask(id);

		// Add new task
		Scheduler.addTask(newTask);

		return newTask;
	}

	public static void removeTask(UUID id)
	{
		// Delete old task
		Scheduler.removeTask(id);
	}



}
