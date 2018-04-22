package com.codecaptured.autoagenda.database.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.codecaptured.autoagenda.AppExecutors;
import com.codecaptured.autoagenda.database.room.dao.EventDao;
import com.codecaptured.autoagenda.database.room.dao.TaskDao;
import com.codecaptured.autoagenda.database.room.entities.Event;
import com.codecaptured.autoagenda.database.room.entities.Task;

/**
 * Created by matthew on 3/28/18.
 */

@Database(entities = {Task.class, Event.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase
{
	// Only allow a singe instance of room
	private static AppDatabase databaseInstance;

	public static final String DATABASE_NAME = "autoagenda-db";

	// Type of data to be stored
	public abstract TaskDao taskDao();
	public abstract EventDao eventDao();

	// Flag to see if database
	private static AppDatabase appDatabase;

	/**
	 * Get the instance of the database
	 * @param context
	 * @return
	 */
	public static AppDatabase getInstance(final Context context, final AppExecutors executors)
	{
		// Check to see if the database has already been created
		if (databaseInstance == null)
		{
			synchronized (AppDatabase.class)
			{
				if (databaseInstance == null)
				{
					databaseInstance = buildDatabase(context.getApplicationContext(), executors);
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
	private static AppDatabase buildDatabase(final Context appContext,final AppExecutors executors)
	{
		return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries()
						.addCallback(new Callback()
						{
							@Override
							public void onCreate(@NonNull SupportSQLiteDatabase db)
							{
								super.onCreate(db);
								// Generate the data for pre-population
								AppDatabase database = AppDatabase.getInstance(appContext, executors);

								// notify that the database was created and it's ready to be used
							}
						}).build();
	}

}