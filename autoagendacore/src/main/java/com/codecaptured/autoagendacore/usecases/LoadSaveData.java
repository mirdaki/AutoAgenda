package com.codecaptured.autoagendacore.usecases;

import com.codecaptured.autoagendacore.entities.Event;
import com.codecaptured.autoagendacore.entities.Schedule;
import com.codecaptured.autoagendacore.entities.Task;
import com.codecaptured.autoagendacore.entities.TimeFence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by matthew on 2/21/18.
 */

public class LoadSaveData
{
	public static void loadDataToSchedule(ScheduleInfo dataSource)
	{
		// Load data from source into data source
		dataSource.loadData();

		// Get the current events data and convert it to the entities event class
		HashMap<UUID, Event> userEvents = new HashMap<>();
		for (EventInteractor.UserEvent event : dataSource.getEvents())
		{
			userEvents.put(event.getId(), EventInteractor.userEventToEvent(event));
		}

		// Load the current events data into the schedule class
		Schedule.setCurrentEvents(userEvents);

		// Get the old events data and convert it to the entities event class
		HashMap<UUID, Event> oldEvents = new HashMap<>();
		for (EventInteractor.UserEvent event : dataSource.getEvents())
		{
			oldEvents.put(event.getId(), EventInteractor.userEventToEvent(event));
		}

		// Load the old events data into the schedule class
		Schedule.setOldEvents(oldEvents);

		// Get the current tasks data and convert it to the entities task class
		HashMap<UUID, Task> userTasks = new HashMap<>();
		for (TaskInteractor.UserTask task : dataSource.getTasks())
		{
			userTasks.put(task.getId(), TaskInteractor.userTaskToTask(task));
		}

		// Load the current tasks data into the schedule class
		Schedule.setCurrentTasks(userTasks);

		// Get the old tasks data and convert it to the entities task class
		HashMap<UUID, Task> oldTasks = new HashMap<>();
		for (TaskInteractor.UserTask task : dataSource.getTasks())
		{
			oldTasks.put(task.getId(), TaskInteractor.userTaskToTask(task));
		}

		// Load the old tasks data into the schedule class
		Schedule.setOldTasks(oldTasks);

		// Get the current time fences data and convert it to the entities time fences class
		HashMap<UUID, TimeFence> userTimeFences = new HashMap<>();
		for (TimeFenceInteractor.UserTimeFence timeFence : dataSource.getTimeFences())
		{
			userTimeFences.put(timeFence.getId(), TimeFenceInteractor.userTimeFenceToTimeFence(timeFence));
		}

		// Load the current time fences data into the schedule class
		Schedule.setCurrentTimeFences(userTimeFences);

		// Get the old time fences data and convert it to the entities time fences class
		HashMap<UUID, TimeFence> oldTimeFences = new HashMap<>();
		for (TimeFenceInteractor.UserTimeFence timeFence : dataSource.getTimeFences())
		{
			oldTimeFences.put(timeFence.getId(), TimeFenceInteractor.userTimeFenceToTimeFence(timeFence));
		}

		// Load the old time fences data into the schedule class
		Schedule.setOldTimeFences(oldTimeFences);
	}

	public static void saveDataFromScehdule(ScheduleInfo dataSource)
	{
		// Get the scheduled current values
		List<Event> list = new ArrayList<>(Schedule.getCurrentEvents().values());

		EventInteractor.UserEvent[] events = dataSource.getEvents();

		events[0] = EventInteractor.eventToUserEvent(list.get(0), events[0]);

		dataSource.saveData();
	}

	// TODO: Should included some sort of message for failures
	public interface ScheduleInfo
	{
		// Get and set data
		EventInteractor.UserEvent[] getEvents();
		void setEvents(EventInteractor.UserEvent[] events);
		TaskInteractor.UserTask[] getTasks();
		void setTasks(TaskInteractor.UserTask[] tasks);
		TimeFenceInteractor.UserTimeFence[] getTimeFences();
		void setTimeFences(TimeFenceInteractor.UserTimeFence[] timeFences);

		/**
		 * Save the data in this object to the source
		 */
		void saveData();

		/**
		 * Load data from source into this object
		 */
		void loadData();
	}
}
