package com.codecaptured.autoagendacore.domain;

import java.util.Date;

/**
 * Task are things that take some amount of time and may have a a due date
 */

public class Task
{
	private int id;
	private String title;
	private String description;
	private Date dueDate;
	private int timeRequiredInMinutes;
	private int priorityLevel;
	private String[] tags;
	private TimeBlock[] taskTimes;
//	private int[] notificationTimes;
//	Location
//	Repeat information

	/**
	 * Create a task, some thing that take some amount of time and may have a a due date
	 * @param id Unique ID
	 * @param title Name for the task
	 * @param description Description providing more information about the task
	 * @param dueDate Date when the task is due. Set to the default date if their is no due date
	 * @param timeRequiredInMinutes The amount of time the task might take up in minutes
	 * @param priorityLevel A priority level between 0 and 10, where 0 is the lowest priority
	 * @param tags Tags or categories associated with the task. Used to organize the task
	 */
	protected Task(int id, String title, String description, Date dueDate, int timeRequiredInMinutes,
	               int priorityLevel, String[] tags)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.timeRequiredInMinutes = timeRequiredInMinutes;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
//		this.notificationTimes = notificationTimes;
	}

	public int getId()
	{
		return id;
	}

	protected void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	protected void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	protected void setDescription(String description)
	{
		this.description = description;
	}

	public Date getDueDate()
	{
		return dueDate;
	}

	protected void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public int getTimeRequiredInMinutes()
	{
		return timeRequiredInMinutes;
	}

	protected void setTimeRequiredInMinutes(int timeRequired)
	{
		this.timeRequiredInMinutes = timeRequired;
	}

	public int getPriorityLevel()
	{
		return priorityLevel;
	}

	protected void setpPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	public String[] getTags()
	{
		return tags;
	}

	protected void setTags(String[] tags)
	{
		this.tags = tags;
	}

	public TimeBlock[] getTaskTimes()
	{
		return taskTimes;
	}

	protected void setTaskTimes(TimeBlock[] taskTimes)
	{
		this.taskTimes = taskTimes;
	}

//	public int[] getNotificationTimes()
//	{
//		return notificationTimes;
//	}
//
//	protected void setNotificationTimes(int[] notificationTimes)
//	{
//		this.notificationTimes = notificationTimes;
//	}

}
