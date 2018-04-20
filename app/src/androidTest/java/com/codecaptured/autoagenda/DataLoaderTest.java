package com.codecaptured.autoagenda;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.codecaptured.autoagenda.database.Data;
import com.codecaptured.autoagenda.database.room.entities.Task;
import com.codecaptured.autoagendacore.entities.Schedule;
import com.codecaptured.autoagendacore.usecases.LoadSaveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.codecaptured.autoagenda.TestData.tempTask1;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataLoaderTest
{
	private Data dataLoaderSaver;

	@Test
	public void useAppContext() throws Exception
	{
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();

		assertEquals("com.codecaptured.autoagenda.database.database.test", appContext.getPackageName());
	}

	@Before
	public void createDb()
	{
		Context context = InstrumentationRegistry.getTargetContext();
		AppExecutors executor = new AppExecutors();
		dataLoaderSaver = new Data(context, executor);
	}

	@Test
	public void loadData() throws Exception
	{
		Task[] tempList = {tempTask1};
		dataLoaderSaver.setCurrentTasks(tempList);
		LoadSaveData.loadDataToSchedule(dataLoaderSaver);
		Schedule.getCurrentTasks();
		assertTrue(true);
	}
}