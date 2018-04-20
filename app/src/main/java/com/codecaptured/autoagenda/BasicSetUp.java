package com.codecaptured.autoagenda;

import android.app.Application;

import com.codecaptured.autoagenda.database.Data;
import com.codecaptured.autoagenda.database.room.AppDatabase;
import com.codecaptured.autoagendacore.usecases.LoadSaveData;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicSetUp extends Application
{

	private AppExecutors mAppExecutors;

	@Override
	public void onCreate() {
		super.onCreate();

		mAppExecutors = new AppExecutors();
	}

	public AppDatabase getDatabase()
	{
		return AppDatabase.getInstance(this, mAppExecutors);
	}

}

