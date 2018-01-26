package com.codecaptured.autoagendacore;

import java.util.Date;

/**
 * Created by matthew on 1/22/18.
 */

public abstract class Task
{
	private int id;
	private String title;
	private String description;
	private Date dueDate;
	private double timeRequired;
	private int priorityLevel;
	private String[] tag;
	private int[] notificationTimes;
	// Location
	// Repeat information

	public Task(int id, String title, String description, Date dueDate, double timeRequired,
	            int priorityLevel, String[] tag, int[] notificationTimes)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.timeRequired = timeRequired;
		this.priorityLevel = priorityLevel;
		this.tag = tag;
		this.notificationTimes = notificationTimes;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public Date getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public double getTimeRequired()
	{
		return timeRequired;
	}

	public void setTimeRequired(double timeRequired)
	{
		this.timeRequired = timeRequired;
	}

	public int getPriorityLevel()
	{
		return priorityLevel;
	}

	public void setpPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	public String[] getTag()
	{
		return tag;
	}

	public void setTag(String[] tag)
	{
		this.tag = tag;
	}

	public int[] getNotificationTimes()
	{
		return notificationTimes;
	}

	public void setNotificationTimes(int[] notificationTimes)
	{
		this.notificationTimes = notificationTimes;
	}

}
