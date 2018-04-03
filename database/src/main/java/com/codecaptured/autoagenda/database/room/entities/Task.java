package com.codecaptured.autoagenda.database.room.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.codecaptured.autoagendacore.usecases.TaskInteractor;
import com.codecaptured.autoagendacore.entities.TimeBlock;

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
	public Task()
	{
	}
}

