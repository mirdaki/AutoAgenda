package com.codecaptured.autoagenda;

import android.app.Application;
import android.support.annotation.NonNull;

import com.codecaptured.autoagenda.database.room.AppDatabase;
import com.codecaptured.autoagenda.database.room.dao.EventDao;
import com.codecaptured.autoagenda.database.room.dao.TaskDao;
import com.codecaptured.autoagenda.database.room.entities.DataTimeBlock;
import com.codecaptured.autoagendacore.entities.Event;
import com.codecaptured.autoagendacore.entities.Schedule;
import com.codecaptured.autoagendacore.entities.Task;
import com.codecaptured.autoagendacore.usecases.EventInteractor;
import com.codecaptured.autoagendacore.usecases.TaskInteractor;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

// Example:
// Pull in database info
//		TaskInteractor.UserTask[] task = DataInteractor.loadTaskData(getApplication());

public class DataInteractor
{
	private AppDatabase database;

	/**
	 * Load task data from database into schedule and return a list of tasks
	 * @param application
	 * @return
	 */
	public static TaskInteractor.UserTask[] loadTaskData(Application application)
	{
		TaskDao tDao = ((BasicSetUp) application).getDatabase().taskDao();
		TaskInteractor.UserTask[] tasks = tDao.loadAllTasks();

		HashMap<UUID, Task> userTasks = new HashMap<>();
		for (TaskInteractor.UserTask task : tasks)
		{
			userTasks.put(task.getId(), TaskInteractor.userTaskToTask(task));
		}

		Schedule.setCurrentTasks(userTasks);

		return tasks;
	}

	/**
	 * Load event data from database into schedule and return a list of events
	 * @param application
	 * @return
	 */
	public static EventInteractor.UserEvent[] loadEventData(Application application)
	{
		EventDao eDao = ((BasicSetUp) application).getDatabase().eventDao();
		EventInteractor.UserEvent[] events = eDao.loadAllEvents();

		HashMap<UUID, Event> userEvents = new HashMap<>();
		for (EventInteractor.UserEvent event : events)
		{
			userEvents.put(event.getId(), EventInteractor.userEventToEvent(event));
		}

		Schedule.setCurrentEvents(userEvents);

		return events;
	}

	/**
	 * Save data from schedule into database
	 * @param application
	 */
	public static void saveData(Application application)
	{
		TaskDao tDao = ((BasicSetUp) application).getDatabase().taskDao();

		Task[] currentScheduledTasks = new Task[tDao.loadAllTasks().length];
		currentScheduledTasks = Schedule.getCurrentTasks().values().toArray(currentScheduledTasks);

		for (Task task : currentScheduledTasks)
		{
			tDao.insertTasks(new com.codecaptured.autoagenda.database.room.entities.Task(task));
		}

		EventDao eDao = ((BasicSetUp) application).getDatabase().eventDao();

		Event[] currentScheduledEvents = new Event[eDao.loadAllEvents().length];
		currentScheduledEvents = Schedule.getCurrentEvents().values().toArray(currentScheduledEvents);

		for (Event event : currentScheduledEvents)
		{
			eDao.insertEvents(new com.codecaptured.autoagenda.database.room.entities.Event(event));
		}
	}

//	public Task[] getTempTaskS()
//	{
//		Calendar calendar= Calendar.getInstance();
//		Date tempDueDate;
//		Date tempStartDate;
//
//		calendar.set(2018, Calendar.APRIL, 17, 10, 00, 00);
//		tempDueDate = calendar.getTime();
//
//		calendar.set(2018, Calendar.APRIL, 20, 10, 00, 00);
//		tempStartDate = calendar.getTime();
//
//		String[] tempTags  = {"work"};
//		DataTimeBlock[] DATA_TIME_BLOCK_ARRAY_1 = {new DataTimeBlock(tempStartDate, 120)};
//
//		Task tempTask = new Task("Temp", "Hello", false, tempDueDate, 120, 3, tempTags, UUID.randomUUID(), DATA_TIME_BLOCK_ARRAY_1);
//		Task[] tasks = {tempTask};
//		return tasks;
//	}
}
