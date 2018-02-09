package com.codecaptured.autoagendacore.usecases;

import com.codecaptured.autoagendacore.domain.Task;

import java.util.Date;
import java.util.UUID;

/**
 * User task extend the Task base class so they can be used outside of the domain layer
 */

public class UserTask extends Task
{
	/**
	 * Create a task, some thing that take some amount of time and may have a a due date
	 * Extends the Task base class
	 * @param id Unique ID
	 * @param title Name for the task
	 * @param description Description providing more information about the task
	 * @param dueDate Date when the task is due. Set to the default date if their is no due date
	 * @param timeRequiredInMinutes The amount of time the task might take up in minutes
	 * @param priorityLevel A priority level between 0 and 10, where 0 is the lowest priority
	 * @param tags Tags or categories associated with the task. Used to organize the task
	 */
	protected UserTask(UUID id, String title, String description, Date dueDate, int timeRequiredInMinutes,
	            int priorityLevel, String[] tags)
	{
		super(id, title, description, dueDate, timeRequiredInMinutes, priorityLevel, tags);
	}

}
