//package com.codecaptured.autoagendacore.usecases;
//
//import com.codecaptured.autoagendacore.entities.Event;
//import com.codecaptured.autoagendacore.entities.Schedule;
//import com.codecaptured.autoagendacore.entities.Task;
//import com.codecaptured.autoagendacore.entities.TimeFence;
//
//import java.util.HashMap;
//import java.util.UUID;
//
///**
// * Created by matthew on 2/21/18.
// */
//
//public class LoadSaveData
//{
//	public static void loadDataToSchedule(ScheduleInfo dataSource)
//	{
//		// Load data from source into data source
//		dataSource.loadData();
//
//		// Event data
//		// Get the current events data and convert it to the entities event class
//		HashMap<UUID, Event> userEvents = new HashMap<>();
//		for (EventInteractor.UserEvent event : dataSource.getCurrentEvents())
//		{
//			userEvents.put(event.getId(), EventInteractor.userEventToEvent(event));
//		}
//
//		// Load the current events data into the schedule class
//		Schedule.setCurrentEvents(userEvents);
//
//		// Get the old events data and convert it to the entities event class
//		HashMap<UUID, Event> oldEvents = new HashMap<>();
//		for (EventInteractor.UserEvent event : dataSource.getOldEvents())
//		{
//			oldEvents.put(event.getId(), EventInteractor.userEventToEvent(event));
//		}
//
//		// Load the old events data into the schedule class
//		Schedule.setOldEvents(oldEvents);
//
//		// Task data
//		// Get the current tasks data and convert it to the entities task class
//		HashMap<UUID, Task> userTasks = new HashMap<>();
//		for (TaskInteractor.UserTask task : dataSource.getCurrentTasks())
//		{
//			userTasks.put(task.getId(), TaskInteractor.userTaskToTask(task));
//		}
//
//		// Load the current tasks data into the schedule class
//		Schedule.setCurrentTasks(userTasks);
//
//		// Get the old tasks data and convert it to the entities task class
//		HashMap<UUID, Task> oldTasks = new HashMap<>();
//		for (TaskInteractor.UserTask task : dataSource.getOldTasks())
//		{
//			oldTasks.put(task.getId(), TaskInteractor.userTaskToTask(task));
//		}
//
//		// Load the old tasks data into the schedule class
//		Schedule.setOldTasks(oldTasks);
//
//		// TimeFence data
//		// Get the current time fences data and convert it to the entities time fences class
//		HashMap<UUID, TimeFence> userTimeFences = new HashMap<>();
//		for (TimeFenceInteractor.UserTimeFence timeFence : dataSource.getCurrentTimeFences())
//		{
//			userTimeFences.put(timeFence.getId(),
//							TimeFenceInteractor.userTimeFenceToTimeFence(timeFence));
//		}
//
//		// Load the current time fences data into the schedule class
//		Schedule.setCurrentTimeFences(userTimeFences);
//
//		// Get the old time fences data and convert it to the entities time fences class
//		HashMap<UUID, TimeFence> oldTimeFences = new HashMap<>();
//		for (TimeFenceInteractor.UserTimeFence timeFence : dataSource.getOldTimeFences())
//		{
//			oldTimeFences.put(timeFence.getId(), TimeFenceInteractor.userTimeFenceToTimeFence(timeFence));
//		}
//
//		// Load the old time fences data into the schedule class
//		Schedule.setOldTimeFences(oldTimeFences);
//	}
//
//	public static void saveDataFromSchedule(ScheduleInfo dataSource)
//	{
//		// Event data
//		// Get the scheduled current events
//		Event[] currentScheduledEvents = new Event[dataSource.getCurrentEvents().length];
//		currentScheduledEvents = Schedule.getCurrentEvents().values().toArray(currentScheduledEvents);
//
//		// Get the previously saved current events
//		EventInteractor.UserEvent[] currentSavedEvents = dataSource.getCurrentEvents();
//
//		// Set the scheduled events to be saved
//		dataSource.setCurrentEvents(EventInteractor.eventsToUserEvents(currentScheduledEvents,
//						currentSavedEvents));
//
//		// Get the scheduled current events
//		Event[] oldScheduledEvents = new Event[dataSource.getOldEvents().length];
//		oldScheduledEvents = Schedule.getOldEvents().values().toArray(oldScheduledEvents);
//
//		// Get the previously saved current events
//		EventInteractor.UserEvent[] oldSavedEvents = dataSource.getOldEvents();
//
//		// Set the scheduled events to be saved
//		dataSource.setOldEvents(EventInteractor.eventsToUserEvents(oldScheduledEvents, oldSavedEvents));
//
//		// Task data
//		// Get the scheduled current tasks
//		Task[] currentScheduledTasks = new Task[dataSource.getCurrentTasks().length];
//		currentScheduledTasks = Schedule.getCurrentTasks().values().toArray(currentScheduledTasks);
//
//		// Get the previously saved current tasks
//		TaskInteractor.UserTask[] currentSavedTasks = dataSource.getCurrentTasks();
//
//		// Set the scheduled tasks to be saved
//		dataSource.setCurrentTasks(TaskInteractor.tasksToUserTasks(currentScheduledTasks,
//						currentSavedTasks));
//
//		// Get the scheduled current tasks
//		Task[] oldScheduledTasks = new Task[dataSource.getOldTasks().length];
//		oldScheduledTasks = Schedule.getOldTasks().values().toArray(oldScheduledTasks);
//
//		// Get the previously saved current tasks
//		TaskInteractor.UserTask[] oldSavedTasks = dataSource.getOldTasks();
//
//		// Set the scheduled tasks to be saved
//		dataSource.setOldTasks(TaskInteractor.tasksToUserTasks(oldScheduledTasks, oldSavedTasks));
//
//		// TimeFence data
//		// Get the scheduled current time fences
//		TimeFence[] currentScheduledTimeFences =
//						new TimeFence[dataSource.getCurrentTimeFences().length];
//		currentScheduledTimeFences =
//						Schedule.getCurrentTimeFences().values().toArray(currentScheduledTimeFences);
//
//		// Get the previously saved current time fences
//		TimeFenceInteractor.UserTimeFence[] currentSavedTimeFences = dataSource.getCurrentTimeFences();
//
//		// Set the scheduled time fences to be saved
//		dataSource.setCurrentTimeFences(TimeFenceInteractor.timeFencesToUserTimeFences(
//						currentScheduledTimeFences, currentSavedTimeFences));
//
//		// Get the scheduled current time fences
//		TimeFence[] oldScheduledTimeFences = new TimeFence[dataSource.getOldTimeFences().length];
//		oldScheduledTimeFences = Schedule.getOldTimeFences().values().toArray(oldScheduledTimeFences);
//
//		// Get the previously saved current time fences
//		TimeFenceInteractor.UserTimeFence[] oldSavedTimeFences = dataSource.getOldTimeFences();
//
//		// Set the scheduled time fences to be saved
//		dataSource.setOldTimeFences(TimeFenceInteractor.timeFencesToUserTimeFences(
//						oldScheduledTimeFences, oldSavedTimeFences));
//
//		// Commit new data to be saved to the data source
//		dataSource.saveData();
//	}
//
//	// TODO: Should included some sort of message for failures
//	public interface ScheduleInfo
//	{
//		// Get and set data
//		EventInteractor.UserEvent[] getCurrentEvents();
//		void setCurrentEvents(EventInteractor.UserEvent[] events);
//		EventInteractor.UserEvent[] getOldEvents();
//		void setOldEvents(EventInteractor.UserEvent[] events);
//		TaskInteractor.UserTask[] getCurrentTasks();
//		void setCurrentTasks(TaskInteractor.UserTask[] tasks);
//		TaskInteractor.UserTask[] getOldTasks();
//		void setOldTasks(TaskInteractor.UserTask[] tasks);
//		TimeFenceInteractor.UserTimeFence[] getCurrentTimeFences();
//		void setCurrentTimeFences(TimeFenceInteractor.UserTimeFence[] timeFences);
//		TimeFenceInteractor.UserTimeFence[] getOldTimeFences();
//		void setOldTimeFences(TimeFenceInteractor.UserTimeFence[] timeFences);
//
//		/**
//		 * Save the data in this object to the source
//		 */
//		void saveData();
//
//		/**
//		 * Load data from source into this object
//		 */
//		void loadData();
//	}
//}
