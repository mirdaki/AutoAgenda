package com.codecaptured.autoagendacore.entities;

import java.util.UUID;

/**
 * TimeFence describe times that are dedicated for specific types of tasks
 */
public class TimeFence
{
	private UUID id;
	private String title;
	private TimeBlock timeBlock;
	private String[] tag;
//	Repeat information

	/**
	 * Creates a time fence that describe times that are dedicated for specific types of tasks
	 * @param id Unique ID
	 * @param title The name of the time fence
	 * @param timeBlock Start and length of time fence
	 * @param tag Tags or categories associated with the time fence. Used to organize the time fence
	 */
	public TimeFence(UUID id, String title, TimeBlock timeBlock, String[] tag)
	{
		this.id = id;
		this.title = title;
		this.timeBlock = timeBlock;
		this.tag = tag;
	}

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public TimeBlock getTimeBlock()
	{
		return timeBlock;
	}

	public void setTimeBlock(TimeBlock timeBlock)
	{
		this.timeBlock = timeBlock;
	}

	public String[] getTag()
	{
		return tag;
	}

	public void setTag(String[] tag)
	{
		this.tag = tag;
	}
}


