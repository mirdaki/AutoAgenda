package com.codecaptured.autoagendacore.domain;

import java.util.Date;

/**
 * Created by matthew on 1/26/18.
 */

public class Event
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

	protected Event(int id, String title, String description, Date startTime, Date endTime,
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

	protected Date getStartTime()
	{
		return startTime;
	}

	protected void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	protected Date getEndTime()
	{
		return endTime;
	}

	protected void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	protected int getPriorityLevel()
	{
		return priorityLevel;
	}

	protected void setPriorityLevel(int priorityLevel)
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
