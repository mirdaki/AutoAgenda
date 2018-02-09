package com.codecaptured.autoagendacore.domain;

/**
 * Created by matthew on 1/26/18.
 */

// TODO: Maybe have a current schedule and a list of older tasks and events for suggestions

public class Schedule
{
	private Task[] tasks;
	private Event[] events;
	private TimeFence[] timeFences;

	protected Schedule(Task[] tasks, Event[] events, TimeFence[] timeFences)
	{
		this.tasks = tasks;
		this.events = events;
		this.timeFences = timeFences;
	}

	protected Task[] getTasks()
	{
		return tasks;
	}

	protected void setTasks(Task[] tasks)
	{
		this.tasks = tasks;
	}

	protected Event[] getEvents()
	{
		return events;
	}

	protected void setEvents(Event[] events)
	{
		this.events = events;
	}

	protected TimeFence[] getTimeFences()
	{
		return timeFences;
	}

	protected void setTimeFences(TimeFence[] timeFences)
	{
		this.timeFences = timeFences;
	}
}
