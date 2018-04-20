package com.codecaptured.autoagenda;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.codecaptured.autoagenda.database.room.AppDatabase;
import com.codecaptured.autoagenda.database.room.dao.TaskDao;
import com.codecaptured.autoagenda.database.room.entities.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataBaseTest
{
	private TaskDao mTaskDao;
	private AppDatabase mDb;

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
		mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

		mTaskDao = mDb.taskDao();
	}

	@After
	public void closeDb() throws IOException
	{
		mDb.close();
	}

	@Test
	public void insertOneTaskAndRead() throws Exception
	{
//		User user = TestUtil.createUser(3);
//		user.setName("george");
//		mTaskDao.insert(user);
//		List<User> byName = mTaskDao.findUsersByName("george");
//		assertThat(byName.get(0), equalTo(user));
//		mDb.insertData(mDb, TestData.tempTask1);
//		Task[] tasks = mDb.getTasks();
		mTaskDao.insertTasks(TestData.tempTask1);
		Task[] tasks = mTaskDao.loadAllTasks();
		assertTrue(0 == tasks[0].getDueDate().compareTo(TestData.tempTask1.getDueDate()));
	}

	@Test
	public void insertManyTaskAndRead() throws Exception
	{
		mTaskDao.insertTasks(TestData.tempTask1, TestData.tempTask2);
		Task[] tasks = mTaskDao.loadAllTasks();
		assertTrue(0 == tasks[0].getDueDate().compareTo(TestData.tempTask1.getDueDate()));
		assertTrue(0 == tasks[1].getDueDate().compareTo(TestData.tempTask2.getDueDate()));
	}

	@Test
	public void getTasksWhenNoTasksInserted() throws InterruptedException
	{
		Task[] tasks = mTaskDao.loadAllTasks();
		assertTrue(tasks.length == 0);
	}

	@Test
	public void insertOneTaskAndDelete() throws Exception
	{
		mTaskDao.insertTasks(TestData.tempTask1);
		mTaskDao.deleteTasks(TestData.tempTask1);

		Task[] tasks = mTaskDao.loadAllTasks();
		assertTrue(tasks.length == 0);
	}

	@Test
	public void insertOneTaskAndUpdate() throws Exception
	{
		mTaskDao.insertTasks(TestData.tempTask3);
		TestData.tempTask3.setDescription("changed");
		mTaskDao.updateTasks(TestData.tempTask3);

		Task[] tasks = mTaskDao.loadAllTasks();
		assertTrue(0 == tasks[0].getDescription().compareTo(TestData.tempTask3.getDescription()));
	}

}
