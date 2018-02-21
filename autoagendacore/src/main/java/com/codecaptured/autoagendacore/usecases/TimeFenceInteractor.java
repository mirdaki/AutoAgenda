package com.codecaptured.autoagendacore.usecases;

import com.codecaptured.autoagendacore.entities.TimeFence;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.UUID;
import java.util.Date;

/**
 * This is used when interacting (creating, modifying, removing) time fences. Also controls the
 * interface needed for talking to this class
 */
public class TimeFenceInteractor
{
	// TODO: Should also return some sort of message to indicate if it was scheduled properly or not.

	/**
	 * Add a new event to the schedule
	 * @param newTimeFence The new time fence
	 */
	public static void addTimeFence(UserTimeFence newTimeFence)
	{
		// Create a new ID
		UUID id = UUID.randomUUID();

		// Add ID to UserTimeFence
		newTimeFence.setId(id);

		// Make the timeFence
		TimeFence timeFence = new TimeFence(newTimeFence.getId(), newTimeFence.getTitle(),
						newTimeFence.getTimeBlock(), newTimeFence.getTags());

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addTimeFence(timeFence);
	}

	/**
	 * Change an existing time fence to new values
	 * @param originalTimeFence The original time fence to be changed
	 * @param newTimeFence The new chnaged time fence
	 */
	public static void modifyTimeFence(UserTimeFence originalTimeFence, UserTimeFence newTimeFence)
	{
		// Make the IDs the same
		newTimeFence.setId(originalTimeFence.getId());

		// Remove the old timeFence
		Scheduler.removeTimeFence(originalTimeFence.getId());

		// Make the timeFence
		TimeFence timeFence = new TimeFence(newTimeFence.getId(), newTimeFence.getTitle(),
						newTimeFence.getTimeBlock(), newTimeFence.getTags());

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addTimeFence(timeFence);
	}

	/**
	 * Remove time fence from schedule
	 * @param oldTimeFence The time fence to be removed
	 */
	public static void removeTimeFence(UserTimeFence oldTimeFence)
	{
		// Delete old timeFence
		Scheduler.removeTimeFence(oldTimeFence.getId());
	}

	/**
	 * The time fence data object used to talk with the use cases. Includes default values for time
	 * fences
	 */
	public interface UserTimeFence
	{
		// Default values
		String DEFAULT_TITLE = "";
		TimeBlock DEFAULT_TIME_BLOCK = new TimeBlock(new Date(0), 0);
		String[] DEFAULT_TAGS = {""};

		// Getters and setters
		// Values set by user
		String getTitle();
		void setTitle(String title);
		TimeBlock getTimeBlock();
		void setTimeBlock(TimeBlock timeBlocks);
		String[] getTags();
		void setTags(String[] tags);

		// Set by software
		UUID getId();
		void setId(UUID id);
	}

}
