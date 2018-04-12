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
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
								Date tempDate1 = new Date();
								Date tempDate2 = new Date();
								Date tempDate3 = new Date();
								Date tempDate4 = new Date();
								Date tempDate5 = new Date();
								Date tempDate6 = new Date();
								Date tempDate7 = new Date();
								Date tempDate8 = new Date();
								Date tempDate9 = new Date();
								Date tempDate10 = new Date();
								Date tempStartDate = new Date();

								try
								{
									tempDate1 = sdf.parse("13/4/2018 12:00:00");
									tempDate2 = sdf.parse("13/4/2018 12:00:00");
									tempDate3 = sdf.parse("13/4/2018 12:00:00");
									tempDate4 = sdf.parse("13/4/2018 12:00:00");
									tempDate5 = sdf.parse("13/4/2018 12:00:00");
									tempDate6 = sdf.parse("13/4/2018 12:00:00");
									tempDate7 = sdf.parse("13/4/2018 12:00:00");
									tempDate8 = sdf.parse("13/4/2018 12:00:00");
									tempDate9 = sdf.parse("13/4/2018 12:00:00");
									tempDate10 = sdf.parse("13/4/2018 12:00:00");
									tempStartDate = sdf.parse("10/4/2018 12:00:00");
								}
								catch (ParseException e)
								{
									e.printStackTrace();
								}

								String[] tempTags  = {"work"};

								Task tempTask1 = new Task("Temp1", "Hello", false, tempDate1, 120, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask2 = new Task("Temp2", "Hello", false, tempDate2, 15, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask3 = new Task("Temp3", "Hello", false, tempDate3, 20, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask4 = new Task("Temp4", "Hello", false, tempDate4, 60, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask5 = new Task("Temp5", "Hello", false, tempDate5, 100, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask6 = new Task("Temp6", "Hello", false, tempDate6, 15, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask7 = new Task("Temp7", "Hello", false, tempDate7, 120, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask8 = new Task("Temp8", "Hello", false, tempDate8, 20, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask9 = new Task("Temp9", "Hello", false, tempDate9, 60, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));
								Task tempTask10 = new Task("Temp10", "Hello", false, tempDate10, 120, 3, tempTags, UUID.randomUUID(), new TimeBlock(tempStartDate, 120));

								insertData(database, tempTask1, tempTask2, tempTask3, tempTask4, tempTask5, tempTask6, tempTask7, tempTask8, tempTask9, tempTask10);
								// notify that the database was created and it's ready to be used
							}
						}).build();
	}

	public static void insertData(final AppDatabase database, final Task... tasks)
	{
		database.taskDao().insertTasks(tasks);
	}

}