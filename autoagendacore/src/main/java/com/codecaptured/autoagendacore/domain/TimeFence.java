package com.codecaptured.autoagendacore.domain;

/**
 * TimeFence describe times that are dedicated for specific types of tasks
 */

public class TimeFence
{
	private int id;
	private TimeBlock timeBlock;
	private String[] tag;
//	Repeat information

	/**
	 * Creates a time fence that describe times that are dedicated for specific types of tasks
	 * @param id Unique ID
	 * @param timeBlock Start and length of time fence
	 * @param tag Tags or categories associated with the time fence. Used to organize the time fence
	 */
	protected TimeFence(int id, TimeBlock timeBlock, String[] tag)
	{
		this.id = id;
		this.timeBlock = timeBlock;
		this.tag = tag;
	}

	protected int getId()
	{
		return id;
	}

	protected void setId(int id)
	{
		this.id = id;
	}

	protected TimeBlock getTimeBlock()
	{
		return timeBlock;
	}

	protected void setTimeBlock(TimeBlock timeBlock)
	{
		this.timeBlock = timeBlock;
	}

	protected String[] getTag()
	{
		return tag;
	}

	protected void setTag(String[] tag)
	{
		this.tag = tag;
	}
}


