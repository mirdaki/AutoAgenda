package com.codecaptured.autoagenda.database.room.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.Date;

/**
 * Created by matthew on 4/2/18.
 */

// TODO: Needs to inherit from a DataTimeBlock interactor or something similar
public class DataTimeBlock extends com.codecaptured.autoagendacore.entities.TimeBlock
{
	public DataTimeBlock(Date startTime, int numberOfMinutes)
	{
		super(startTime, numberOfMinutes);
	}

	public DataTimeBlock(TimeBlock timeBlock)
	{
		super(timeBlock.getStartTime(), timeBlock.getNumberOfMinutes());
	}
}
