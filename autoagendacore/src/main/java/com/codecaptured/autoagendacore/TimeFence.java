package com.codecaptured.autoagendacore;

import java.util.Date;

/**
 * Created by matthew on 1/26/18.
 */

public abstract class TimeFence
{
	private int id;
	private Date startTime;
	private Date endTime;
	private String[] tag;
	// Repeat information

	public TimeFence(int id, java.util.Date startTime, java.util.Date endTime, String[] tag)
	{
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tag = tag;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public java.util.Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(java.util.Date startTime)
	{
		this.startTime = startTime;
	}

	public java.util.Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(java.util.Date endTime)
	{
		this.endTime = endTime;
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


