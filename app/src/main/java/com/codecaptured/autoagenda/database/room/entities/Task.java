package com.codecaptured.autoagenda.database.room.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.codecaptured.autoagenda.database.room.Converters;
import com.codecaptured.autoagendacore.usecases.TaskInteractor;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 3/28/18.
 */

@Entity
@TypeConverters({Converters.class})
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
	@NonNull
	private UUID id;

	private DataTimeBlock[] dataTimeBlocks;

	@Ignore
	private TimeBlock[] timeBlocks;

	// Constructor
	public Task(TaskInteractor.UserTask task)
	{
		this.title = task.getTitle();
		this.description = task.getDescription();
		this.completed = task.getCompleted();
		this.dueDate = task.getDueDate();
		this.timeRequiredInMinutes = task.getTimeRequiredInMinutes();
		this.priorityLevel = task.getPriorityLevel();
		this.tags = task.getTags();
		this.id = task.getId();
		this.timeBlocks = task.getTimeBlocks();

		this.dataTimeBlocks = new DataTimeBlock[task.getTimeBlocks().length];
		for (int i = 0; i < task.getTimeBlocks().length; i++)
		{
			this.dataTimeBlocks[i] = new DataTimeBlock(task.getTimeBlocks()[i]);
		}
	}

	public Task(com.codecaptured.autoagendacore.entities.Task task)
	{
		this.title = task.getTitle();
		this.description = task.getDescription();
		this.completed = task.isCompleted();
		this.dueDate = task.getDueDate();
		this.timeRequiredInMinutes = task.getTimeRequiredInMinutes();
		this.priorityLevel = task.getPriorityLevel();
		this.tags = task.getTags();
		this.id = task.getId();
		this.timeBlocks = task.getTaskTimes();

		this.dataTimeBlocks = new DataTimeBlock[task.getTaskTimes().length];
		for (int i = 0; i < task.getTaskTimes().length; i++)
		{
			this.dataTimeBlocks[i] = new DataTimeBlock(task.getTaskTimes()[i]);
		}
	}

	public Task(String title, String description, boolean completed, Date dueDate,
	            int timeRequiredInMinutes, int priorityLevel, String[] tags,
	            @NonNull UUID id, DataTimeBlock[] dataTimeBlocks)
	{
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.dueDate = dueDate;
		this.timeRequiredInMinutes = timeRequiredInMinutes;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
		this.id = id;
		this.dataTimeBlocks = dataTimeBlocks;

		this.timeBlocks = new TimeBlock[dataTimeBlocks.length];
		for (int i = 0; i < dataTimeBlocks.length; i++)
		{
			this.timeBlocks[i] = new TimeBlock(dataTimeBlocks[i].getStartTime(), dataTimeBlocks[i].getNumberOfMinutes());
		}
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
	public DataTimeBlock[] getDataTimeBlocks()
	{
		return dataTimeBlocks;
	}

	public void setDataTimeBlocks(DataTimeBlock[] dataTimeBlocks)
	{
		this.dataTimeBlocks = dataTimeBlocks;

		this.timeBlocks = new TimeBlock[dataTimeBlocks.length];
		for (int i = 0; i < dataTimeBlocks.length; i++)
		{
			this.timeBlocks[i] = new TimeBlock(dataTimeBlocks[i].getStartTime(), dataTimeBlocks[i].getNumberOfMinutes());
		}
	}

	public TimeBlock[] getTimeBlocks()
	{
		return this.timeBlocks;
	}

	public void setTimeBlocks(TimeBlock[] timeBlocks)
	{
		this.timeBlocks = timeBlocks;
		this.dataTimeBlocks = new DataTimeBlock[timeBlocks.length];
		for (int i = 0; i < timeBlocks.length; i++)
		{
			this.dataTimeBlocks[i] = new DataTimeBlock(timeBlocks[i]);
		}
	}

}

