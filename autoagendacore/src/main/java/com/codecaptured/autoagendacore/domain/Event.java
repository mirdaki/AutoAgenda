package com.codecaptured.autoagendacore.domain;

/**
 * Events occur at very specific times and are not necessarily scheduled with other tasks
 */

public class Event
{
	private int id;
	private String title;
	private String description;
	private TimeBlock eventTime;
	private int priorityLevel;
	private String[] tags;
//	private int[] notificationTimes;
//	Location
//	Repeat information

	/**
	 * Create an event that occurs at a specific time and and are not necessarily scheduled
	 * @param id Unique ID
	 * @param title Name of the event
	 * @param description Additional information on the event
	 * @param eventTime Start time and length of the event in minutes
	 * @param tags Tags or categories associated with the event. Used to organize the event
	 */
	protected Event(int id, String title, String description, TimeBlock eventTime, String[] tags)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.eventTime = eventTime;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
//		this.notificationTimes = notificationTimes;
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

	protected TimeBlock getEventTime()
	{
		return eventTime;
	}

	protected void setEventTime(TimeBlock eventTime)
	{
		this.eventTime = eventTime;
	}

	protected int getPriorityLevel()
	{
		return priorityLevel;
	}

	protected void setPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	protected String[] getTags()
	{
		return tags;
	}

	protected void setTags(String[] tag)
	{
		this.tags = tags;
	}

//	protected int[] getNotificationTimes()
//	{
//		return notificationTimes;
//	}
//
//	protected void setNotificationTimes(int[] notificationTimes)
//	{
//		this.notificationTimes = notificationTimes;
//	}
}
