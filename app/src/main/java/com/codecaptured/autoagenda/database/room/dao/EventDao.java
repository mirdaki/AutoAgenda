package com.codecaptured.autoagenda.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.codecaptured.autoagenda.database.room.entities.Event;

/**
 * Created by matthew on 3/28/18.
 */

@Dao
public interface EventDao
{
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertEvents(Event... events);

	@Update
	void updateEvents(Event... events);

	@Delete
	void deleteEvents(Event... events);

	@Query("DELETE FROM Event")
	public void deleteAll();

	@Query("SELECT * FROM Event")
	Event[] loadAllEvents();
}