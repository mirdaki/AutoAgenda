package com.codecaptured.autoagendacore;

import java.util.Date;

/**
 * Created by matthew on 1/26/18.
 */

public abstract class Event
{
	private int id;
	private String title;
	private String description;
	private Date startTime;
	private Date endTime;
	private int priorityLevel;
	private String[] tag;
	private int[] notificationTimes;
	// Location
	// Repeat information

	public Event(int id, String title, String description, Date startTime, Date endTime,
	             int priorityLevel, String[] tag, int[] notificationTimes)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public java.util.Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(java.util.Date startTime)
	{
		this.startTime = startTime;
	}

	public java.util.Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(java.util.Date endTime)
	{
		this.endTime = endTime;
	}

	public int getPriorityLevel()
	{
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel)
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
