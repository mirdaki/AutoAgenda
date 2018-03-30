package com.codecaptured.autoagenda.database.room.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.codecaptured.autoagendacore.usecases.TaskInteractor;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 3/28/18.
 */

@Entity
public class Task implements TaskInteractor.UserTask
{
	// Values set by user
	private String title;
	private String description;
	private boolean completed;
	private Date dueDate;
	private int timeRequiredInMinutes;
	private int priorityLevel;
	private String[] tags;

	// Values set by software
	@PrimaryKey
	private UUID id;

	private TimeBlock[] timeBlocks;

	// Constructor
	public Task()
	{

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
	public void setTags(String[] tags)
	{
		this.tags = tags;
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
