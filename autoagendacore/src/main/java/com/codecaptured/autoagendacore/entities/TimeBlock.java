package com.codecaptured.autoagendacore.entities;

import java.util.Date;

/**
 * Time blocks are times dedicated to working on a specific task
 */
public class TimeBlock
{
	private Date startTime;
	private int numberOfMinutes;

	/**
	 * Create a time blocks that starts and lasts for specified times
	 * @param startTime Time it starts
	 * @param timeInMinutes Amount of time the block takes up
	 */
	public TimeBlock(Date startTime, int timeInMinutes)
	{
		this.startTime = startTime;
		this.numberOfMinutes = timeInMinutes;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public int getNumberOfMinutes()
	{
		return numberOfMinutes;
	}

	public void setNumberOfMinutes(int timeInMinutes)
	{
		this.numberOfMinutes = timeInMinutes;
	}
}
