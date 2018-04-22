package com.codecaptured.autoagenda.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.codecaptured.autoagenda.database.room.entities.Task;

/**
 * Created by matthew on 3/28/18.
 */

@Dao
public interface TaskDao
{
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertTasks(Task... task);

	@Update
	void updateTasks(Task... tasks);

	@Delete
	void deleteTasks(Task... tasks);

	@Query("DELETE FROM Task")
	public void deleteAll();

	@Query("SELECT * FROM Task")
	Task[] loadAllTasks();
}