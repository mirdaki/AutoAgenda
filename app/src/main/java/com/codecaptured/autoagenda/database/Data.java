package com.codecaptured.autoagenda.database;

import android.content.Context;

import com.codecaptured.autoagenda.AppExecutors;
import com.codecaptured.autoagenda.database.room.AppDatabase;
import com.codecaptured.autoagenda.database.room.dao.TaskDao;
import com.codecaptured.autoagenda.database.room.entities.Task;
import com.codecaptured.autoagendacore.usecases.LoadSaveData.ScheduleInfo;
import com.codecaptured.autoagendacore.usecases.TaskInteractor;

/**
 * Created by matthew on 3/28/18.
 */

// TODO: This should serve as a bit of an abstraction for different databases (i.e. I should be able
// to call this and it will use whatever data source(s) it wants without me caring)

public class Data implements ScheduleInfo
{
	private AppDatabase database;
	private TaskDao taskDao;

	public Data(Context context, AppExecutors executors)
	{
		database = AppDatabase.getInstance(context, executors);
		taskDao = database.taskDao();
	}

	// Get and set data
	// TODO: Old data is just an empty array for now
//	EventInteractor.UserEvent[] getCurrentEvents();
//	void setCurrentEvents(EventInteractor.UserEvent[] events);
//	EventInteractor.UserEvent[] getOldEvents();
//	void setOldEvents(EventInteractor.UserEvent[] events);
	public TaskInteractor.UserTask[] getCurrentTasks()
	{
		return taskDao.loadAllTasks();
	}

	public void setCurrentTasks(TaskInteractor.UserTask[] tasks)
	{
		for (TaskInteractor.UserTask task : tasks)
		{
			taskDao.insertTasks(new Task(task));
		}
	}

	public TaskInteractor.UserTask[] getOldTasks()
	{
		return taskDao.loadAllTasks();
	}

	public void setOldTasks(TaskInteractor.UserTask[] tasks)
	{
		for (TaskInteractor.UserTask task : tasks)
		{
			taskDao.insertTasks(new Task(task));
		}
	}
//	TimeFenceInteractor.UserTimeFence[] getCurrentTimeFences();
//	void setCurrentTimeFences(TimeFenceInteractor.UserTimeFence[] timeFences);
//	TimeFenceInteractor.UserTimeFence[] getOldTimeFences();
//	void setOldTimeFences(TimeFenceInteractor.UserTimeFence[] timeFences);

	/**
	 * Save the data in this object to the source
	 */
	public void saveData()
	{
		// Nothing to setup
	}

	/**
	 * Load data from database
	 */
	public void loadData()
	{
		// Nothing to setup
	}
}
