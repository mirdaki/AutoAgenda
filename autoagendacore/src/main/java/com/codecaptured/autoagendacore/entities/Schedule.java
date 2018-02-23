package com.codecaptured.autoagendacore.entities;

import java.util.HashMap;
import java.util.UUID;

/**
 * Storage for all tasks and events being used in the app
 */
public class Schedule
{
	private static HashMap<UUID, Task> currentTasks;
	private static HashMap<UUID, Task> oldTasks;
	private static HashMap<UUID, Event> currentEvents;
	private static HashMap<UUID, Event> oldEvents;
	private static HashMap<UUID, TimeFence> currentTimeFences;
	private static HashMap<UUID, TimeFence> oldTimeFences;

	/**
	 * Initialize all the values for the schedule
	 */
	public Schedule()
	{
		currentTasks = new HashMap<>();
		oldTasks = new HashMap<>();
		currentEvents = new HashMap<>();
		oldEvents = new HashMap<>();
		currentTimeFences = new HashMap<>();
		oldTimeFences = new HashMap<>();
	}

	public static HashMap<UUID, Task> getCurrentTasks()
	{
		return currentTasks;
	}

	public static void setCurrentTasks(HashMap<UUID, Task> currentTasks)
	{
		Schedule.currentTasks = currentTasks;
	}

	public static HashMap<UUID, Task> getOldTasks()
	{
		return oldTasks;
	}

	public static void setOldTasks(HashMap<UUID, Task> oldTasks)
	{
		Schedule.oldTasks = oldTasks;
	}

	public static HashMap<UUID, Event> getCurrentEvents()
	{
		return currentEvents;
	}

	public static void setCurrentEvents(HashMap<UUID, Event> currentEvents)
	{
		Schedule.currentEvents = currentEvents;
	}

	public static HashMap<UUID, Event> getOldEvents()
	{
		return oldEvents;
	}

	public static void setOldEvents(HashMap<UUID, Event> oldEvents)
	{
		Schedule.oldEvents = oldEvents;
	}

	public static HashMap<UUID, TimeFence> getCurrentTimeFences()
	{
		return currentTimeFences;
	}

	public static void setCurrentTimeFences(HashMap<UUID, TimeFence> currentTimeFences)
	{
		Schedule.currentTimeFences = currentTimeFences;
	}

	public static HashMap<UUID, TimeFence> getOldTimeFences()
	{
		return oldTimeFences;
	}

	public static void setOldTimeFences(HashMap<UUID, TimeFence> oldTimeFences)
	{
		Schedule.oldTimeFences = oldTimeFences;
	}

}
