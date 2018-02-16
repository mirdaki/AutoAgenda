package com.codecaptured.autoagendacore.entities;

import java.util.UUID;

/**
 * Events occur at very specific times and are not necessarily scheduled with other tasks
 */
public class Event
{
	private UUID id;
	private String title;
	private String description;
	private TimeBlock eventTime;
	private int priorityLevel;
	private String[] tags;
//	Notification Times;
//	Location
//	Repeat information

	/**
	 * Create an event that occurs at a specific time and and are not necessarily scheduled
	 * @param id Unique ID
	 * @param title Name of the event
	 * @param description Additional information on the event
	 * @param eventTime Start time and length of the event in minutes
	 * @param priorityLevel Priority on a scale of 0 to 10, 5 being average
	 * @param tags Tags or categories associated with the event. Used to organize the event
	 */
	public Event(UUID id, String title, String description, TimeBlock eventTime, int priorityLevel,
	             String[] tags)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.eventTime = eventTime;
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

	public TimeBlock getEventTime()
	{
		return eventTime;
	}

	public void setEventTime(TimeBlock eventTime)
	{
		this.eventTime = eventTime;
	}

	public int getPriorityLevel()
	{
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	public String[] getTags()
	{
		return tags;
	}

	public void setTags(String[] tag)
	{
		this.tags = tags;
	}

}
