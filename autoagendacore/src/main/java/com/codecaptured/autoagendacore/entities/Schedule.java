package com.codecaptured.autoagendacore.entities;

import java.util.HashMap;
import java.util.UUID;

/**
 * Storage for all tasks and events being used in the app
 */

// TODO: Maybe have a current schedule and a list of older tasks and events for suggestions

public class Schedule
{
	private HashMap<UUID, Task> currentTasks;
	private HashMap<UUID, Task> oldTasks;
	private HashMap<UUID, Event> currentEvents;
	private HashMap<UUID, Event> oldEvents;
	private HashMap<UUID, TimeFence> currentTimeFences;
	private HashMap<UUID, TimeFence> oldTimeFences;

	/**
	 * Storage for all tasks and events being used in the app for scheduled times and organizations
	 * @param currentTasks Tasks that have yet to be completed
	 * @param oldTasks Tasks that have been completed
	 * @param currentEvents Events that have yet to be completed
	 * @param oldEvents Events that have been completed
	 * @param currentTimeFences Time fences still in use
	 * @param oldTimeFences Previously used time fences
	 */
	public Schedule(HashMap<UUID, Task> currentTasks, HashMap<UUID, Task> oldTasks,
	                HashMap<UUID, Event> currentEvents, HashMap<UUID, Event> oldEvents,
	                HashMap<UUID, TimeFence> currentTimeFences, HashMap<UUID,
									TimeFence> oldTimeFences)
	{
		this.currentTasks = currentTasks;
		this.oldTasks = oldTasks;
		this.currentEvents = currentEvents;
		this.oldEvents = oldEvents;
		this.currentTimeFences = currentTimeFences;
		this.oldTimeFences = oldTimeFences;
	}

	public HashMap<UUID, Task> getCurrentTasks()
	{
		return currentTasks;
	}

	public void setCurrentTasks(HashMap<UUID, Task> currentTasks)
	{
		this.currentTasks = currentTasks;
	}

	public HashMap<UUID, Task> getOldTasks()
	{
		return oldTasks;
	}

	public void setOldTasks(HashMap<UUID, Task> oldTasks)
	{
		this.oldTasks = oldTasks;
	}

	public HashMap<UUID, Event> getCurrentEvents()
	{
		return currentEvents;
	}

	public void setCurrentEvents(HashMap<UUID, Event> currentEvents)
	{
		this.currentEvents = currentEvents;
	}

	public HashMap<UUID, Event> getOldEvents()
	{
		return oldEvents;
	}

	public void setOldEvents(HashMap<UUID, Event> oldEvents)
	{
		this.oldEvents = oldEvents;
	}

	public HashMap<UUID, TimeFence> getCurrentTimeFences()
	{
		return currentTimeFences;
	}

	public void setCurrentTimeFences(HashMap<UUID, TimeFence> currentTimeFences)
	{
		this.currentTimeFences = currentTimeFences;
	}

	public HashMap<UUID, TimeFence> getOldTimeFences()
	{
		return oldTimeFences;
	}

	public void setOldTimeFences(HashMap<UUID, TimeFence> oldTimeFences)
	{
		this.oldTimeFences = oldTimeFences;
	}

	// TODO: Implement the below functions
	public String[] getTags()
	{
		return new String[] {""};
	}

	public HashMap<UUID, Task> getTagTasks(String tag)
	{
		return currentTasks;
	}

	public HashMap<UUID, Event> getTagEvents(String tag)
	{
		return currentEvents;
	}

}
