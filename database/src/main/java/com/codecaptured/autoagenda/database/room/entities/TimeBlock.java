package com.codecaptured.autoagenda.database.room.entities;

import java.util.Date;

/**
 * Created by matthew on 4/2/18.
 */

// TODO: Needs to inherit from a TimeBlock interactor or something similar

public class TimeBlock
{
	public Date startTime;
	public int numberOfMinutes;

	public TimeBlock(Date startTime, int numberOfMinutes)
	{
		this.startTime = startTime;
		this.numberOfMinutes = numberOfMinutes;
	}
}
