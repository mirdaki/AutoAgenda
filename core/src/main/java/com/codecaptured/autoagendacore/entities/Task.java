package com.codecaptured.autoagendacore.entities;

// TODO: Maybe use a different date format
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Task are things that take some amount of time and may have a a due date
 */
public class Task
{
	private UUID id;
	private String title;
	private String description;
	private Boolean completed;
	private Date dueDate;
	private int timeRequiredInMinutes;
	private int priorityLevel;
	private ArrayList<String> tags;
	private TimeBlock[] taskTimes;
//	Notification Times;
//	Location
//	Repeat information

	/**
	 * Create a task, some thing that take some amount of time and may have a a due date
	 * @param id Unique ID
	 * @param title Name for the task
	 * @param description Description providing more information about the task
	 * @param completed Specifies if the task has been completed or not
	 * @param dueDate Date when the task is due. Set to the default date if their is no due date
	 * @param timeRequiredInMinutes The amount of time the task might take up in minutes
	 * @param priorityLevel A priority level between 0 and 10, where 0 is the lowest priority
	 * @param tags Tags or categories associated with the task. Used to organize the task
	 */
	public Task(UUID id, String title, String description, Boolean completed, Date dueDate,
	            int timeRequiredInMinutes, int priorityLevel, ArrayList<String> tags)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.dueDate = dueDate;
		this.timeRequiredInMinutes = timeRequiredInMinutes;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
	}

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Boolean isCompleted()
	{
		return this.completed;
	}

	public void setCompleted(Boolean completed)
	{
		this.completed = completed;
	}

	public Date getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public int getTimeRequiredInMinutes()
	{
		return timeRequiredInMinutes;
	}

	public void setTimeRequiredInMinutes(int timeRequired)
	{
		this.timeRequiredInMinutes = timeRequired;
	}

	public int getPriorityLevel()
	{
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	public ArrayList<String> getTags()
	{
		return tags;
	}

	public void setTags(ArrayList<String> tags)
	{
		this.tags = tags;
	}

	public TimeBlock[] getTaskTimes()
	{
		return taskTimes;
	}

	public void setTaskTimes(TimeBlock[] taskTimes)
	{
		this.taskTimes = taskTimes;
	}

}
