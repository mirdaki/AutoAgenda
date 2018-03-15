package com.codecaptured.autoagendacore.usecases;

import com.codecaptured.autoagendacore.entities.Task;
import com.codecaptured.autoagendacore.entities.Event;
import com.codecaptured.autoagendacore.entities.TimeBlock;
import com.codecaptured.autoagendacore.entities.TimeFence;

import java.util.*;


/**
 * Created by matthew on 2/7/18.
 */

public class Scheduler
{
	final static int SECONDS_TO_MILLI = 1000;
	final static int  MIN_TO_SECONDS = 60;


	// return epoch time of new task
	// later will need eventMap
	public static long addTask(Task newTask, HashMap<UUID, Task> taskMap)
	{
	   /*
		Before a task can be added to the schedule, the schedule needs to
		figure out "openings" or holes in current schedule.

		Go thru existing tasks and create a linked list of schedule holes. 	The nodes of the link list can be TimeBlock class.

		After the linked list is created, then try to place a new task by
		going thru the list and finding the biggest hole.
	    */

		// declare local instance of a task
		Task myTask;

		// Start the "hole list" with a single node/hole with a time
		// span from current time to the due date.

		LinkedList<TimeBlock> holelist = new LinkedList<TimeBlock>();

		// create Date instance with current time
		Date curTime = new Date();

		// create Date instance with new task due date/time
		Date newTaskDueDate = newTask.getDueDate();

		int timeRequired = newTask.getTimeRequiredInMinutes();

		// Create TimeBlock for the first Hole
		TimeBlock firsthole = new TimeBlock(curTime, timeRequired);

		// set the start time
		firsthole.setStartTime(curTime);


		int numMinutesDuration = (int) (newTaskDueDate.getTime()- curTime.getTime());


		firsthole.setNumberOfMinutes(numMinutesDuration);

		// Add the TimeBlock to the hole list
		holelist.addFirst(firsthole);

		// [            first hole in memory                ]



		// loop thru existing task list
		// we only care about tasks occurring before the new task due date
		// existing tasks occurring AFTER the new task due date are Don't  Cares

		// loop thru all existing tasks


		// TODO: PAST TASKS LOGIC BUG NEED FIX


		for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
		{

			myTask = entry.getValue();

			TimeBlock[] taskTimes = myTask.getTaskTimes();

			// loop thru the task start times
			for(int j = 0; j < taskTimes.length; j++)
			{
				Date taskStartTime = taskTimes[j].getStartTime();

				Date taskEndTime = taskTimes[j].getEndingTime();

				// only care about existing tasks occurring BEFORE the new task due date
				if( taskStartTime.before(newTaskDueDate))
				{
					// Traverse hole list
					// if existing task lies within hole, then 					   // fit it in the hole: "break" hole apart

					for(int num=0; num < holelist.size(); num++)
					{
						TimeBlock tb = holelist.get(num);

						if((taskStartTime.after(tb.getStartTime())) && (taskEndTime.before(tb.getEndingTime())))
						{
							// break apart this hole

							// save off original hole end time
							long holeEndTime = tb.getEpochEndTime();

							// update duration of hole that will precede task
							long duration = taskStartTime.getTime() - tb.getEpochStartTime();

							int numOfMins = (int) duration / ( MIN_TO_SECONDS * SECONDS_TO_MILLI);

							tb.setNumberOfMinutes(numOfMins);

							// Another new TimeBlock with a start time
							// being the task end time and a duration
							// which is the difference between original
							// hole end time the task end time

							TimeBlock newHole = new TimeBlock(taskEndTime, numOfMins);

							newHole.setStartTime(taskEndTime);

							// update duration of hole that will proceed task
							duration = holeEndTime - taskEndTime.getTime();

							numOfMins = (int) duration / (MIN_TO_SECONDS * SECONDS_TO_MILLI);

							newHole.setNumberOfMinutes(numOfMins);

							// insert this new hole in link list AFTER the hole we broke apart

							int index = num + 1;
							holelist.add(index, newHole);

							// task fitted, now stop and go to next task
							break;
						}

					} //for(int num=0; num < holelist.size(); num++)

				} // 	if( taskStartTime.before(newtaskDueDate) )

			} // for(int j = 0; j < taskTimes.length; j++)

		} // for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )



		for(int z = 0; z < holelist.size(); z++)
		{
			TimeBlock holeTimeBlk = holelist.get(z);
			System.out.println("Hole index: " + z);
			System.out.println("Hole start time: " + holeTimeBlk.getStartTime());
			System.out.println("Hole duration time: " + holeTimeBlk.getNumberOfMinutes());
		}



		// At this point we have gone thru all existing tasks and have
		// created a linked list of Time schedule openings or "holes"

		// Now do the same thing for all existing events
		// Idea: treat sleeping as a re-occurring "event" stored in the
		// event table


		// Now we can try to fit in the new task in schedule

		// find the biggest hole in the linked list of holes
		int largestHoleIndex = -1;
		int largestDuration = -1;
		for(int idx=0; idx < holelist.size(); idx++)
		{
			TimeBlock holeTimeBlk = holelist.get(idx);
			int duration = holeTimeBlk.getNumberOfMinutes();

			if( duration > largestDuration )
			{
				largestDuration = duration;
				largestHoleIndex = idx;
			}
		}

		if( largestHoleIndex != -1 )
		{
			TimeBlock holeTB = holelist.get(largestHoleIndex);

			long holeDuration = holeTB.getNumberOfMinutes();

			long taskDuration = newTask.getTimeRequiredInMinutes();

			if(taskDuration <= holeDuration)
			{
				// found place in schedule
				// now fit new task in the middle of this time block
				// everything in epoch time

				long holeStartTime = holeTB.getEpochStartTime();

				long newTaskStartTime = ( holeStartTime + (holeDuration/2) * MIN_TO_SECONDS * SECONDS_TO_MILLI) - (taskDuration/2) * MIN_TO_SECONDS * SECONDS_TO_MILLI;

				return (newTaskStartTime);
			}
		}

		return(-1); // could not schedule task
	}

	public static void removeTask(UUID id)
	{

	}

	public static void addEvent(Event event)
	{

	}

	public static void removeEvent(UUID id)
	{

	}

	public static void addTimeFence(TimeFence timeFence)
	{

	}

	public static void removeTimeFence(UUID id)
	{

	}

	public static void reschedule()
	{

	}


}
