package com.codecaptured.autoagenda.database.room.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.codecaptured.autoagenda.database.room.Converters;
import com.codecaptured.autoagendacore.usecases.EventInteractor;
import com.codecaptured.autoagendacore.usecases.TaskInteractor;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 3/28/18.
 */

@Entity
@TypeConverters({Converters.class})
public class Event implements EventInteractor.UserEvent
{
	// Values set by software
	@PrimaryKey
	@NonNull
	private UUID id;

	// Values set by user
	private String title;
	private String description;

	@Ignore
	private TimeBlock eventTime;

	private DataTimeBlock dataEventTime;
	private int priorityLevel;
	private String[] tags;

	public Event(String title, String description, DataTimeBlock dataEventTime, int priorityLevel, String[] tags, @NonNull UUID id)
	{
		this.title = title;
		this.description = description;
		this.id = id;
		this.dataEventTime = dataEventTime;
		this.priorityLevel = priorityLevel;
		this.tags = tags;

		this.eventTime = new TimeBlock(eventTime.getStartTime(), eventTime.getNumberOfMinutes());
	}

	// Constructor
	public Event(EventInteractor.UserEvent event)
	{
		this.title = event.getTitle();
		this.description = event.getDescription();
		this.id = event.getId();
		this.eventTime = event.getEventTime();
		this.priorityLevel = event.getPriorityLevel();
		this.tags = event.getTags();

		this.dataEventTime = new DataTimeBlock(event.getEventTime().getStartTime(), event.getEventTime().getNumberOfMinutes());
	}

	public Event(com.codecaptured.autoagendacore.entities.Event event)
	{
		this.title = event.getTitle();
		this.description = event.getDescription();
		this.id = event.getId();
		this.eventTime = event.getEventTime();
		this.priorityLevel = event.getPriorityLevel();
		this.tags = event.getTags();

		this.dataEventTime = new DataTimeBlock(eventTime.getStartTime(), eventTime.getNumberOfMinutes());
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

	// TODO: Make a use case version of this
	public DataTimeBlock getDataEventTime()
	{
		return this.dataEventTime;
	}

	public void setDataEventTime(DataTimeBlock dataTimeBlocks)
	{
		this.dataEventTime = dataTimeBlocks;
		this.eventTime = new DataTimeBlock(dataTimeBlocks);
	}

	@Override
	public TimeBlock getEventTime()
	{
		return this.eventTime;
	}

	@Override
	public void setEventTime(TimeBlock timeBlock)
	{
		this.eventTime = timeBlock;
		this.dataEventTime = new DataTimeBlock(timeBlock.getStartTime(), timeBlock.getNumberOfMinutes());
	}
}

