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
	final static int MIN_TO_MILLI = 60000;


	// return epoch time of new task
	// later will need eventMap
	public static TimeBlock[] addTask(Task newTask, HashMap<UUID, Task> taskMap)
	{
	   /*
		Before a task can be added to the schedule, the schedule needs to
		figure out "openings" or holes in current schedule.

		Go thru existing tasks and create a linked list of schedule holes.
		The nodes of the link list can be TimeBlock class.

		After the linked list is created, then try to place a new task by
		going thru the list and finding the biggest hole.
	    */

		LinkedList<TimeBlock> holelist = findOpenings(newTask.getDueDate(), taskMap);


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

				long newTaskStartTime =
								( holeStartTime + (holeDuration/2) * MIN_TO_MILLI) - ((taskDuration/2) * MIN_TO_MILLI);

				Date newTaskStartDate = new Date(newTaskStartTime);

				TimeBlock[] newSched = new TimeBlock[1];

				newSched[0] = new TimeBlock(newTaskStartDate,(int)taskDuration);

				return (newSched);
			}
			else
			{
				// try split task into pieces
				TimeBlock[] splitTB = splitter(newTask.getTimeRequiredInMinutes(),holelist );

				if( splitTB.length > 0 )
				{
					return (splitTB);
				}
			}
		}

		return(null); // could not schedule task

	}

	// FindOpenings: figure out "openings" or holes in current schedule

	private static LinkedList<TimeBlock>
	findOpenings( Date newTaskDueDate, HashMap<UUID, Task> taskMap )
	{
		// Start the "hole list" with a single node/hole with a time
		// span from current time to the due date.

		LinkedList<TimeBlock> holelist = new LinkedList<TimeBlock>();

		// create Date instance with current time
		Date curTime = new Date();

		// Create TimeBlock for the first Hole
		long numMinutesDuration = (long) (newTaskDueDate.getTime() - curTime.getTime())/MIN_TO_MILLI;

		TimeBlock firsthole = new TimeBlock(curTime, (int)numMinutesDuration);

		// Add the TimeBlock to the hole list
		holelist.addFirst(firsthole);

		// [            first hole in memory                ]

		/////////////////////////////////////////////////////////////////////
		// block off times for daily events: e.g., sleeping
		//-------------------------------------------------------------
		// TODO: How does scheduler get daily event info
		// for now set a event "sleeping" starting at 10:30 PM
		// and lasting for 8 hours

		int eventHourofDay = 22; // 24 hr clock
		int eventMinute = 30;
		int eventDurationMin = 480; // 8 hrs
		//-------------------------------------------------------------

		processDailyEvent( eventHourofDay,eventMinute,eventDurationMin, newTaskDueDate, holelist);

		/////////////////////////////////////////////////////////////////////

		// loop thru existing task list
		// we only care about tasks occurring before the new task due date
		// existing tasks occurring AFTER the new task due date are Don't  Cares

		// loop thru all existing tasks


		// TODO: PAST TASKS LOGIC BUG NEED FIX


		for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
		{

			Task myTask = entry.getValue();

			if( myTask.isCompleted() == true )
			{
				continue;
			}

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
					// if existing task lies within hole, then fit it in the hole: "break" hole apart
					// if existing task lies within hole, then fit it in the hole: "break" hole apart
					modifyHoleList( taskStartTime, taskEndTime, holelist );

				} // if( taskStartTime.before(newtaskDueDate) )

			} // for(int j = 0; j < taskTimes.length; j++)

		} // for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )

		for(int z = 0; z < holelist.size(); z++)
		{
			TimeBlock holeTimeBlk = holelist.get(z);
			System.out.println("Hole index: " + z);
			System.out.println("Hole start time: " + holeTimeBlk.getStartTime());
			System.out.println("Hole duration time: " + holeTimeBlk.getNumberOfMinutes());
		}

		return holelist;
	}

	private static void modifyHoleList( Date taskStartTime, Date taskEndTime,
	                                    LinkedList<TimeBlock> holelist)
	{
		for(int num=0; num < holelist.size(); num++)
		{
			TimeBlock tb = holelist.get(num);

			if( taskStartTime.after(tb.getStartTime()) &&
							taskEndTime.before(tb.getEndingTime()) )
			{
				// break apart this hole

				// save off original hole end time
				long holeEndTime = tb.getEpochEndTime();

				// update duration of hole that will precede task
				long duration = taskStartTime.getTime() - tb.getEpochStartTime();

				long numOfMins = duration/MIN_TO_MILLI;

				tb.setNumberOfMinutes((int)numOfMins);

				// Another new TimeBlock with a start time
				// being the task end time and a duration
				// which is the difference between original
				// hole end time the task end time

				// update duration of hole that will proceed task
				duration = holeEndTime - taskEndTime.getTime();

				numOfMins = duration/(MIN_TO_MILLI);

				TimeBlock newHole =
								new TimeBlock(taskEndTime,(int)numOfMins);

				// insert this new hole in link list AFTER the
				// hole we broke apart

				int index = num + 1;
				holelist.add(index, newHole);

				// task fitted, now stop and go to next task
				break;

			}  // Handle Case #1
			else if(
							( taskStartTime.before(tb.getStartTime()) ||
											taskStartTime.equals(tb.getStartTime()) )
											&&
											( taskEndTime.after(tb.getStartTime()) &&
															taskEndTime.before(tb.getEndingTime()) ) )
			{
				// chop off front of hole
				// hole start time changes as well as duration

				long tmp = ((taskEndTime.getTime() - tb.getEpochStartTime())/MIN_TO_MILLI);

				long newDuration = (long)tb.getNumberOfMinutes() - tmp;

				tb.setNumberOfMinutes((int)newDuration);

				tb.setStartTime(taskEndTime);

				// task fitted,now stop and go next task
				break;

			}  // Handle Case #2
			else if(
							( taskEndTime.after(tb.getEndingTime()) ||
											taskEndTime.equals(tb.getEndingTime()) )
											&&
											( taskStartTime.after(tb.getStartTime()) &&
															taskStartTime.before(tb.getEndingTime()) ) )
			{
				// chop off back of hole
				// hole retains start time, but duration is reduced

				long tmp = (( tb.getEpochEndTime() - taskStartTime.getTime())/MIN_TO_MILLI);

				long newDuration = (long)tb.getNumberOfMinutes() - tmp;

				tb.setNumberOfMinutes((int)newDuration);

				// task fitted,now stop and go next task
				break;

			}  // Handle Case #3
			else if ( taskStartTime.equals(tb.getStartTime()) &&
							taskEndTime.equals(tb.getEndingTime() ) )
			{
				// remove hole from link list
				holelist.remove(num);
				break;
			}

		} //for(int num=0; num < holelist.size(); num++)
	}

	private static TimeBlock[] splitter( int requiredTime, LinkedList<TimeBlock> holelist )
	{

		// sort hole list base on hole size: largest to smallest
		//-------------------------------------------------------
		//First : convert LinkedList to Array
		TimeBlock[] holearray = new TimeBlock[holelist.size()];

		for(int idx=0; idx < holelist.size(); idx++)
		{
			TimeBlock holeTimeBlk = holelist.get(idx);
			holearray[idx].clone(holeTimeBlk);
		}

		// second : bubble sort based on duration
		//
		boolean flag = true;

		int j = 0;
		int min1, min2;

		while ( flag )
		{
			flag = false;    //set flag to false awaiting a possible swap
			for( j=0;  j < holearray.length - 1;  j++ )
			{
				min1 = holearray[ j ].getNumberOfMinutes();
				min2 = holearray[ j+1 ].getNumberOfMinutes();

				if ( min1 < min2 )
				{
					TimeBlock tmp = new TimeBlock(holearray[ j ]);
					holearray[ j ].clone(holearray[ j+1 ]);
					holearray[ j+1 ].clone(tmp);

					flag = true;
				}
			}
		}
		//-------------------------------------------------------

		int divider  = 2; // start by dividing by 2
		long taskDuration = requiredTime;
		long newdur = 0;
		final long THRESHOLD = 30; // 30 minutes
		boolean cannotFit = false;
		TimeBlock[] tbArray = null;

		while( ( newdur = taskDuration/divider ) >= THRESHOLD ) // put limit on size of split
		{

			if( divider > holearray.length )
			{
				// not enough holes for a divided task : get out !
				break;
			}
			else
			{
				// have enough holes go ahead and see if holes are big enough
				// assume cannot fit until proven otherwise
				cannotFit  = false;

				for( j=0;  j < divider;  j++ )
				{
					if( newdur > holearray[j].getNumberOfMinutes() )
					{
						// hole not big enough for  this split timeblock
						cannotFit  = true;

						break; // cannot schedule split timeblock : get out
					}
				}

				if( cannotFit == false )
				{
					// yeah: split time block fits

					tbArray = new TimeBlock[divider];

					for( j=0;  j < divider;  j++ )
					{
						TimeBlock holeTB = new TimeBlock(holearray[j]);

						long holeDuration = holeTB.getNumberOfMinutes();
						long holeStartTime = holeTB.getEpochStartTime();

						// now fit new task in the middle of this time block
						// everything in epoch time

						long newTaskStartTime =
										( holeStartTime + (holeDuration/2)*MIN_TO_MILLI) - ((newdur/2)*MIN_TO_MILLI);

						Date newTaskStartDate = new Date(newTaskStartTime);

						tbArray[j] = new TimeBlock(newTaskStartDate, (int)newdur);
					}
				}
			}

			divider++; // further divide
		}

		// At this point the size of tbArray indicates success or not
		// if tbArray is not null, then splitting block got scheduled
		return tbArray;
	}
	private static void
	processDailyEvent( int eventHourofDay, int eventMinute,
	                   int eventDurationMin, Date endDate,
	                   LinkedList<TimeBlock> holelist)
	{
		// create Date instance with current time
		Date curTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curTime);

		// extract the year, month, day of current date
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

		// set date for the "daily" event of that day
		Calendar eventCalndr =
						new GregorianCalendar(year, month, dayOfMonth );
		eventCalndr.set(Calendar.HOUR_OF_DAY, eventHourofDay); // 24 hour clock
		eventCalndr.set(Calendar.MINUTE, eventMinute);

		// Now backup one calendar day
		eventCalndr.add(Calendar.DAY_OF_MONTH, -1);

		// create Date and TimeBlock instances for event
		Date eventDate =  eventCalndr.getTime();

		TimeBlock eventTB = new TimeBlock(eventDate, eventDurationMin);

		// only process daily events occuring before certain date
		while( eventDate.before(endDate))
		{

			Date eventStartTime = eventTB.getStartTime();
			Date eventEndTime = eventTB.getEndingTime();

			// Traverse hole list
			// if event lies within hole, then fit it in the hole:
			modifyHoleList( eventStartTime, eventEndTime, holelist );

			// new increment daily event by 1 day
			eventCalndr.add(Calendar.DAY_OF_MONTH, +1);
			eventDate =  eventCalndr.getTime();

			// update TimeBlock for next iteration
			eventTB.setStartTime(eventDate);
			eventTB.setNumberOfMinutes(eventDurationMin);
		}
	}

	public static void removeTask(UUID id)
	{

	}

	public static void addEvent()
	{

	}

	public static void removeEvent()
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