package com.codecaptured.autoagendacore.domain;

import java.util.Date;

/**
 * Created by matthew on 1/22/18.
 */

public class Task
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

	protected Task(int id, String title, String description, Date dueDate, double timeRequired,
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

	protected int getId()
	{
		return id;
	}

	protected void setId(int id)
	{
		this.id = id;
	}

	protected String getTitle()
	{
		return title;
	}

	protected void setTitle(String title)
	{
		this.title = title;
	}

	protected String getDescription()
	{
		return description;
	}

	protected void setDescription(String description)
	{
		this.description = description;
	}

	protected Date getDueDate()
	{
		return dueDate;
	}

	protected void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	protected double getTimeRequired()
	{
		return timeRequired;
	}

	protected void setTimeRequired(double timeRequired)
	{
		this.timeRequired = timeRequired;
	}

	protected int getPriorityLevel()
	{
		return priorityLevel;
	}

	protected void setpPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	protected String[] getTag()
	{
		return tag;
	}

	protected void setTag(String[] tag)
	{
		this.tag = tag;
	}

	protected int[] getNotificationTimes()
	{
		return notificationTimes;
	}

	protected void setNotificationTimes(int[] notificationTimes)
	{
		this.notificationTimes = notificationTimes;
	}

}
