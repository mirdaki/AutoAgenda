package com.codecaptured.autoagendacore;

/**
 * Created by matthew on 1/26/18.
 */

// TODO: Maybe have a current schedule and a list of older tasks and events for suggestions

public abstract class Schedule
{
	private Task[] tasks;
	private Event[] events;
	private TimeFence[] timeFences;

	public Schedule(Task[] tasks, Event[] events, TimeFence[] timeFences)
	{
		this.tasks = tasks;
		this.events = events;
		this.timeFences = timeFences;
	}

	public Task[] getTasks()
	{
		return tasks;
	}

	public void setTasks(Task[] tasks)
	{
		this.tasks = tasks;
	}

	public Event[] getEvents()
	{
		return events;
	}

	public void setEvents(Event[] events)
	{
		this.events = events;
	}

	public TimeFence[] getTimeFences()
	{
		return timeFences;
	}

	public void setTimeFences(TimeFence[] timeFences)
	{
		this.timeFences = timeFences;
	}
}
