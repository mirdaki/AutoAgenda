package com.codecaptured.autoagendacore.entities;

import java.util.Date;


/**
 * Time blocks are times dedicated to working on a specific task
 */

public class TimeBlock
{
	final static int SECONDS_TO_MILLI = 1000;
	final static int  MIN_TO_SECONDS = 60;

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


	// ************* NEW CHANGES BELOW FOR THE SCHEDULER ADD TASK METHOD **************

	public long getEpochStartTime()
	{
		return startTime.getTime();
	}


	public Date getEndingTime()
	{
		Date endDate = new Date();

		long strTime = startTime.getTime();

		long duration = numberOfMinutes * MIN_TO_SECONDS * SECONDS_TO_MILLI;

		long endTime = strTime + duration;

		endDate.setTime(endTime);

		return endDate;
	}

	public long getEpochEndTime()
	{
		long strTime = startTime.getTime();

		long duration = numberOfMinutes * MIN_TO_SECONDS * SECONDS_TO_MILLI;

		long epochEndTime = strTime + duration;

		return epochEndTime;
	}

}

