package com.codecaptured.autoagenda.database.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.codecaptured.autoagenda.database.room.dao.TaskDao;
import com.codecaptured.autoagenda.database.room.entities.Task;
import com.codecaptured.autoagenda.database.room.entities.TimeBlock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 3/28/18.
 */

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase
{
	// Only allow a singe instance of room
	private static AppDatabase databaseInstance;

	public static final String DATABASE_NAME = "autoagenda-db";

	// Type of data to be stored
	public abstract TaskDao taskDao();

	// Flag to see if database
	private static AppDatabase appDatabase;

	/**
	 * Get the instance of the database
	 * @param context
	 * @return
	 */
	public static AppDatabase getInstance(final Context context)
	{
		// Check to see if the database has already been created
		if (databaseInstance == null)
		{
			synchronized (AppDatabase.class)
			{
				if (databaseInstance == null)
				{
					databaseInstance = buildDatabase(context.getApplicationContext());
				}
			}
		}
		return databaseInstance;
	}

	/**
	 * Build the database. {@link Builder#build()} only sets up the database configuration and
	 * creates a new instance of the database.
	 * The SQLite database is only created when it's accessed for the first time.
	 */
	private static AppDatabase buildDatabase(final Context appContext)
	{
		return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
						.addCallback(new Callback()
						{
							@Override
							public void onCreate(@NonNull SupportSQLiteDatabase db)
							{
								super.onCreate(db);
								// Generate the data for pre-population
								AppDatabase database = AppDatabase.getInstance(appContext);

								// TODO: Create default data
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								Date tempDueDate = new Date();
								Date tempStartDate = new Date();
								try
								{
									tempDueDate = sdf.parse("21/12/2018");
									tempStartDate = sdf.parse("10/12/2018");
								}
								catch (ParseException e)
								{
									e.printStackTrace();
								}

								String[] tempTags  = {"work"};

								Task tempTask = new Task("Temp", "Hello", false,
												tempDueDate, 120, 3, tempTags,
												UUID.randomUUID(), new TimeBlock(tempStartDate, 120));

								insertData(database, tempTask);
								// notify that the database was created and it's ready to be used
							}
						}).build();
	}

	public static void insertData(final AppDatabase database, final Task... tasks)
	{
		database.taskDao().insertTasks(tasks);
	}

}