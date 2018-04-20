package com.codecaptured.autoagenda;

import com.codecaptured.autoagendacore.usecases.TaskInteractor;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 3/20/18.
 */

public class UserTask implements TaskInteractor.UserTask
{
	// Values set by user
	String title;
	String description;
	boolean completed;
	Date dueDate;
	int timeRequiredInMinutes;
	int priorityLevel;
	String[] tags;
	TimeBlock thisTimeBlock;
	boolean isEvent = false;
	UUID eventID = null;

	// Values set by software
	UUID id;
	TimeBlock[] timeBlocks;

	// Constructor
	public UserTask(String title, String description, boolean completed, Date dueDate, int timeRequiredInMinutes, int priorityLevel, String[] tags)
	{
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.dueDate = dueDate;
		this.timeRequiredInMinutes = timeRequiredInMinutes;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
	}

	// Getters and setters
	// Values set by user
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
	public Boolean getCompleted()
	{
		return completed;
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
	public void setTimeRequiredInMinutes(int timeRequiredInMinutes)
	{
		this.timeRequiredInMinutes = timeRequiredInMinutes;
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
	public ArrayList<String> getTag()
	{
		return new ArrayList<>(Arrays.asList(tags));
	}
	public void setTags(String[] tags)
	{
		this.tags = tags;
	}
	public void setTag(ArrayList<String> tag)
	{
		this.tags = tag.toArray(new String[tag.size()]);
	}

	// Set by software
	public UUID getId()
	{
		return id;
	}
	public void setId(UUID id)
	{
		this.id = id;
	}
	// TODO: Make a use case version of this
	public TimeBlock[] getTimeBlocks()
	{
		return timeBlocks;
	}
	public void setTimeBlocks(TimeBlock[] timeBlocks)
	{
		this.timeBlocks = timeBlocks;
	}
}
