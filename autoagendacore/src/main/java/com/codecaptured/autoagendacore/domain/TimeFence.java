package com.codecaptured.autoagendacore.domain;

import java.util.Date;

/**
 * Created by matthew on 1/26/18.
 */

public class TimeFence
{
	private int id;
	private Date startTime;
	private Date endTime;
	private String[] tag;
	// Repeat information

	protected TimeFence(int id, Date startTime, Date endTime, String[] tag)
	{
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
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

	protected java.util.Date getStartTime()
	{
		return startTime;
	}

	protected void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	protected Date getEndTime()
	{
		return endTime;
	}

	protected void setEndTime(Date endTime)
	{
		this.endTime = endTime;
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


