package com.codecaptured.autoagenda;

import com.codecaptured.autoagendacore.usecases.EventInteractor;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.UUID;

public class UserEvent implements EventInteractor.UserEvent
{
	// Values set by user
	String title;
	String description;
	TimeBlock eventTime;
	int priorityLevel;
	String[] tags;

	// Values set by software
	UUID id;

	public UserEvent(String title, String description, TimeBlock eventTime, int priorityLevel, String[] tags)
	{
		this.title = title;
		this.description = description;
		this.eventTime = eventTime;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
	}

	public UserEvent(EventInteractor.UserEvent userEvent)
	{
		this.title = userEvent.getTitle();
		this.description = userEvent.getDescription();
		this.eventTime = userEvent.getEventTime();
		this.priorityLevel = userEvent.getPriorityLevel();
		this.tags = userEvent.getTags();
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public TimeBlock getEventTime()
	{
		return this.eventTime;
	}

	@Override
	public void setEventTime(TimeBlock eventTime)
	{
		this.eventTime = eventTime;
	}

	@Override
	public int getPriorityLevel()
	{
		return this.priorityLevel;
	}

	@Override
	public void setPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	@Override
	public String[] getTags()
	{
		return this.tags;
	}

	@Override
	public void setTags(String[] tags)
	{
		this.tags = tags;
	}

	@Override
	public UUID getId()
	{
		return this.id;
	}

	@Override
	public void setId(UUID id)
	{
		this.id = id;
	}
}
