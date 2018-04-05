package com.codecaptured.autoagenda.database.room.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 3/28/18.
 */

@Entity
public class Task
{
	// Values set by user
	public String title;
	public String description;
	public boolean completed;
	public Date dueDate;
	public int timeRequiredInMinutes;
	public int priorityLevel;
	public String[] tags;

	// Values set by software
	@PrimaryKey
	@NonNull
	public UUID id;

	@Embedded
	public TimeBlock timeBlocks;

	// Constructor
	public Task(String title, String description, boolean completed, Date dueDate,
	            int timeRequiredInMinutes, int priorityLevel, String[] tags,
	            @NonNull UUID id, TimeBlock timeBlocks)
	{
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.dueDate = dueDate;
		this.timeRequiredInMinutes = timeRequiredInMinutes;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
		this.id = id;
		this.timeBlocks = timeBlocks;
	}
}

