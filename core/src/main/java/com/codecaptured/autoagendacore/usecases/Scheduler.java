package com.codecaptured.autoagendacore.usecases;

import com.codecaptured.autoagendacore.entities.RecurrenceType;
import com.codecaptured.autoagendacore.entities.Schedule;
import com.codecaptured.autoagendacore.entities.Task;
import com.codecaptured.autoagendacore.entities.Event;
import com.codecaptured.autoagendacore.entities.TimeBlock;
import com.codecaptured.autoagendacore.entities.TimeFence;

import java.util.*;



public class Scheduler
{

	final static int SECONDS_TO_MILLI = 1000;
	final static int  MIN_TO_SECONDS = 60;
	final static int MIN_TO_MILLI = 60000;


	public static TimeBlock[] addTask(Task newTask, HashMap<UUID, Task> taskMap, HashMap<UUID, Event> eventMap)
	{
		
		// Boundary Check immediately! 
		

		Date errorDate = new Date(0);  // 1970
				
	 	TimeBlock[] errorTB = new TimeBlock[1];
	    
	 	errorTB[0] = new TimeBlock(errorDate,0);

		if( newTask == null)
		{
		    return(errorTB);
		}

		Date dueDate = newTask.getDueDate();
		if( dueDate == null)
		{
		    return(errorTB);
		}

		Date curDate = new Date();
		if( dueDate.before(curDate) || dueDate.equals(curDate) )
		{
		    return(errorTB);
		}
		
		if( newTask.getTimeRequiredInMinutes() <= 0 )
		{
		    return(errorTB);
		}
		
	   /*
		Before a task can be added to the schedule, the schedule needs to
		figure out "openings" or holes in current schedule.

		Go thru existing tasks and create a linked list of schedule holes. 	
		The nodes of the link list can be TimeBlock class.

		After the linked list is created, then try to place a new task by
		going thru the list and finding the biggest hole.
	    */



		// First try to fit the new task into the schedule for all tasks and events.
		// No preference for priority yet.  
		
		final int ALL_PRIORITIES = -1;
		
		LinkedList<TimeBlock> holelist = findOpenings(newTask, ALL_PRIORITIES, newTask.getDueDate(), taskMap, eventMap);

		

		/**
	
		// Debug print outs 

		for(int z = 0; z < holelist.size(); z++)
		{
			TimeBlock holeTimeBlk = holelist.get(z);
			System.out.println("Hole index: " + z);
			System.out.println("Hole start time: " + holeTimeBlk.getStartTime());
			System.out.println("Hole end time: " + holeTimeBlk.getEndingTime());
			System.out.println("Hole duration time: " + holeTimeBlk.getNumberOfMinutes());
		}

		 **/

		System.out.println();


		// At this point we have gone thru all existing tasks + events and have
		// created a linked list of Time schedule openings or "holes" without consideration of priority

		// ******** Will treat sleeping as DAILY recurring event **********


		// Now we can try to fit in the new task in schedule

		TimeBlock newTaskSchedule = fitNewTask(newTask.getTimeRequiredInMinutes(), holelist );

		if( newTaskSchedule != null )
		{
			TimeBlock[] newSched = new TimeBlock[1];

			newSched[0] = new TimeBlock(newTaskSchedule);

			addToTaskMap(newTask, newSched, taskMap);

			//System.out.println("Success.");
			//System.out.println();

			return (newSched);
		}
		else
		{
			// cannot fit task as a single block

			// try split task into pieces
			TimeBlock[] splitTB = splitter(newTask.getTimeRequiredInMinutes(),holelist );

			if( splitTB != null )
			{

				addToTaskMap(newTask, splitTB, taskMap);

				return (splitTB);
			}
			else
			{
				// cannot fit task by splitting

				// try priority scheduling by displacing lower priority tasks
		    	 LinkedList<TimeBlock> holelist2 = findOpenings(newTask, newTask.getPriorityLevel(),
		    			 										newTask.getDueDate(), taskMap, eventMap);

		 		
		 		// Now we can try to fit in the new task in schedule

				TimeBlock newPriTaskSchedule = fitNewTask(newTask.getTimeRequiredInMinutes(), holelist2 );
				
			     if( newPriTaskSchedule != null )
			     {
			    	// now the real fun starts
					Date newTaskStartTime = newPriTaskSchedule.getStartTime();
					Date newTaskEndTime = newPriTaskSchedule.getEndingTime();
					   
			    	// go thru task map and mark any "displaced" lower priority tasks
			    	for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
			 		{

			 			Task myTask = entry.getValue();

			 			// skip over completed tasks
						if( myTask.isCompleted() == true )
						{
							continue;
						}
			 			// skip over displaced tasks
						if( myTask.isDisplaced() == true )
						{
							continue;
						}		
				
						// only concerned about tasks lower than set priority
						// priority level between 0 and 10, where 0 is the lowest priority

						if( myTask.getPriorityLevel() >= newTask.getPriorityLevel() )
						{
							continue;
						}
						
						TimeBlock[] taskTimes = myTask.getTaskTimes();
						
						// If task does not have start time, then ignore
						if( taskTimes == null)
						{
							continue;
						}
						
						// finally can loop thru the task start times
						
						for(int j = 0; j < taskTimes.length; j++)
						{
						   Date taskStartTime = taskTimes[j].getStartTime();
						   Date taskEndTime = taskTimes[j].getEndingTime();
						   
						   if( ( taskStartTime.before(newTaskStartTime) &&
							     taskEndTime.after(newTaskStartTime) ) 
								   				||
							   ( ( taskStartTime.after(newTaskStartTime) ||
								   taskStartTime.equals(newTaskStartTime) ) &&
								   taskStartTime.before(newTaskEndTime) ) )   
								   
								   //if( s < S ) AND (e > S ) then conflict
								   //if( ( s >= S ) AND ( s < E )) then conflict
						   {
							   	// conflict
							   // mark task as "displaced"
							   myTask.isDisplaced( true);
							   break;
						   }
						}
			 		} // end: go thru task map and mark any "displaced" lower priority tasks
						
			    	TimeBlock[] newSched = new TimeBlock[1];
	    	    
	    	    	newSched[0] = new TimeBlock(newPriTaskSchedule);
	    	    
	    	    	addToTaskMap(newTask, newSched, taskMap);
	    	    
					// now try reschedule displaced tasks
					for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
			 		{
						Task myTask = entry.getValue();

			 			//  displaced tasks
						if( myTask.isDisplaced() == true )
						{
						  // try establish new start time
						
						  // since this is a displaced task
						  // it will not be re-added to taskmap
						  addTask(myTask, taskMap, eventMap);
						}
					}
	    	 		return (newSched);
			     }
			     else
			     {
					 // cannot fit task as a single block 
			    	 //System.out.println("Failure.");
			    	 return(null); // could not schedule task
			     }
		     }
		}

	}

	private static TimeBlock fitNewTask( int newTaskDuration, LinkedList<TimeBlock> holelist )
	{
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

			long taskDuration = newTaskDuration;

			if(taskDuration <= holeDuration)
			{
				// found place in schedule
				// now fit new task in the middle of this time block
				// everything in epoch time

				long holeStartTime = holeTB.getEpochStartTime();

				// Placing task to be scheduled in middle of hole
				long newTaskStartTime = ( holeStartTime + (holeDuration/2) * MIN_TO_MILLI) - ((taskDuration/2) * MIN_TO_MILLI);

				Date newTaskStartDate = new Date(newTaskStartTime);

				//TimeBlock[] newSched = new TimeBlock[1];

				TimeBlock newSched = new TimeBlock(newTaskStartDate,(int)taskDuration);


				return (newSched);
			}
		}

		return(null); // could not fit new task
	}

	// FindOpenings: figure out "openings" or holes in current schedule

	private static LinkedList<TimeBlock> findOpenings(Task newTask, int priorityLevel, Date newTaskDueDate, HashMap<UUID, Task> taskMap,HashMap<UUID, Event> eventMap )
	{
		// Start the "hole list" with a single node/hole with a time
		// span from current time to the due date.

		LinkedList<TimeBlock> holelist = new LinkedList<TimeBlock>();

		// create Date instance with current time
		Date curTime = new Date();

		// Create TimeBlock for the first Hole
		long numMinutesDuration = (long) ((newTaskDueDate.getTime() - curTime.getTime())/MIN_TO_MILLI) + 1;

		TimeBlock firsthole = new TimeBlock(curTime, (int)numMinutesDuration);

		// Add the TimeBlock to the hole list
		holelist.addFirst(firsthole);

		/**
		 //-------------------------------------------------------------
		 // block off times for daily events: e.g., sleeping
		 // for now set a event "sleeping" starting at 10:30 PM
		 // and lasting for 8 hours

		 int eventHourofDay = 22; // 24 hr clock
		 int eventMinute = 30;
		 int eventDurationMin = 480; // 8 hrs
		 //-------------------------------------------------------------

		 processDailyEvent( eventHourofDay,eventMinute,eventDurationMin, newTaskDueDate, holelist);

		 /////////////////////////////////////////////////////////////////////
		 ***/


		// Go thru Event map entries

		for ( Map.Entry<UUID, Event> entry : eventMap.entrySet() )
		{

			Event myEvent = entry.getValue();

			TimeBlock eventTime = myEvent.getEventTime();

			
			// skip events that don't have start time or duration
			if( eventTime == null )
			{
				continue;
			}

			
			// skip events that are displaced
			if( myEvent.isDisplaced() == true )
			{
				continue;
			}

			RecurrenceType recurrencetype = myEvent.getRecurrenceType();

			// if not a recurring event then simple process as is
			if( recurrencetype  == RecurrenceType.NONE)
			{
				Date eventStartTime = eventTime.getStartTime();
				Date eventEndTime = eventTime.getEndingTime();

				// only care about events occurring BEFORE the new task due date
				// AND have not completed BEFORE the current time.

				if( ( eventStartTime.before(newTaskDueDate)) && ( eventEndTime.after(curTime) ) )
				{
					// Traverse hole list
					// if existing event lies within hole, then fit it in the hole
					// "break" hole apart
					modifyHoleList( eventStartTime, eventEndTime, holelist );

				}
			}
			else if ((recurrencetype  == RecurrenceType.DAILY ) ||
							(recurrencetype  == RecurrenceType.WEEKLY ) ||
							(recurrencetype  == RecurrenceType.MONTHLY ) ||
							(recurrencetype  == RecurrenceType.YEARLY ) )
			{
				processRecurringEvent( myEvent, newTaskDueDate, holelist);
			}
		}

		for ( Map.Entry<UUID, TimeFence> entry : Schedule.getCurrentTimeFences().entrySet() )
		{
			TimeFence myFence = entry.getValue();

			TimeBlock fenceTime = myFence.getTimeBlock();

			if( fenceTime == null)
			{
				continue;
			}

			String[] fenceTags = myFence.getTags();

			String[] taskTags = newTask.getTags();

			boolean foundMatch = false;

			for(int i=0; i < taskTags.length; i++ )
			{
				for(int j = 0; j < fenceTags.length; j++ )
				{
					if( taskTags[i].equals(fenceTags[j]) )
					{
						// found tag match – do not deploy this fence
						foundMatch = true;
						break;
					}
				}

				if(foundMatch == true)
				{
					break;
				}
			}

			if(foundMatch == true )
			{
				// found tag match – do not deploy this fence
				continue;
			}
			// deploy fence

			Date startTime = fenceTime.getStartTime();

			Date endTime = fenceTime.getEndingTime();

			// only care about fences occurring BEFORE the new task due date

			if( startTime.before(newTaskDueDate))
			{
				modifyHoleList( startTime, endTime, holelist );
			}

		}


		// loop thru existing task list
		// we only care about tasks occurring before the new task due date
		// existing tasks occurring AFTER the new task due date are Don't  Cares

		// loop thru all existing tasks

		for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
		{

			Task myTask = entry.getValue();

			// skip over completed tasks
			if( myTask.isCompleted() == true )
			{
				continue;
			}

			// skip tasks lower than set priority
			// priority level between 0 and 10, where 0 is the lowest priority
			if( priorityLevel > -1)
			{

				// Example:
				// New task priority level = 5
				// Previous. task = 4
				// Task is not scheduled in right away.

				// Not allowing lower priority tasks to fill up schedule time and create holes.

				if( myTask.getPriorityLevel() < priorityLevel)
				{
					continue;
				}
			}

			TimeBlock[] taskTimes = myTask.getTaskTimes();

			//FIXED
			// If task does not have start time, then ignore
			if( taskTimes == null)
			{
				continue;
			}

			// loop thru the task start times
			for(int i = 0; i < taskTimes.length;i++)
			{
				Date taskStartTime = taskTimes[i].getStartTime();

				Date taskEndTime = taskTimes[i].getEndingTime();


				// Case where start time is after the end time.  Should never actually happen.
				if(  taskEndTime.before(taskStartTime) || taskEndTime.equals(taskStartTime) )
				{
					continue;
				}


				// only care about existing tasks occurring BEFORE the new task due date
				if( taskStartTime.before(newTaskDueDate))
				{
					// Traverse hole list
					// if existing task lies within hole, then fit it in the hole: "break" hole apart
					modifyHoleList( taskStartTime, taskEndTime, holelist );

				}

			}

		}


		return holelist;
	}

	private static void modifyHoleList( Date startTime, Date endTime, LinkedList<TimeBlock> holelist)
	{
		// perform sanity check on entered task time
		if(  endTime.before(startTime)  ||
						endTime.equals(startTime)      )
		{
			return;
		}

		for(int num=0; num < holelist.size(); num++)
		{
			TimeBlock tb = holelist.get(num);

			if( startTime.after(tb.getStartTime()) &&
							endTime.before(tb.getEndingTime()) )
			{
				// break apart this hole

				// save off original hole end time
				long holeEndTime = tb.getEpochEndTime();

				// update duration of hole that will precede task
				long duration = startTime.getTime() - tb.getEpochStartTime();

				long numOfMins = duration/MIN_TO_MILLI;

				tb.setNumberOfMinutes((int)numOfMins);

				// Another new TimeBlock with a start time
				// being the task end time and a duration
				// which is the difference between original
				// hole end time the task end time

				// update duration of hole that will proceed task
				duration = holeEndTime - endTime.getTime();

				numOfMins = duration/(MIN_TO_MILLI);

				TimeBlock newHole =
								new TimeBlock(endTime,(int)numOfMins);

				// insert this new hole in link list AFTER the
				// hole we broke apart

				int index = num + 1;
				holelist.add(index, newHole);

				// task fitted, now stop and go to next task
				break;

			}  // Handle Case #1
			else if(
							( startTime.before(tb.getStartTime()) ||
											startTime.equals(tb.getStartTime()) )
											&&
											( endTime.after(tb.getStartTime()) &&
															endTime.before(tb.getEndingTime()) ) )
			{
				// chop off front of hole
				// hole start time changes as well as duration

				long tmp = ((endTime.getTime() - tb.getEpochStartTime())/MIN_TO_MILLI);

				long newDuration = (long)tb.getNumberOfMinutes() - tmp;

				tb.setNumberOfMinutes((int)newDuration);

				tb.setStartTime(endTime);

				// task fitted,now stop and go next task
				break;

			}  // Handle Case #2
			else if(
							( endTime.after(tb.getEndingTime()) ||
											endTime.equals(tb.getEndingTime()) )
											&&
											( startTime.after(tb.getStartTime()) &&
															startTime.before(tb.getEndingTime()) ) )
			{
				// chop off back of hole
				// hole retains start time, but duration is reduced

				long tmp = (( tb.getEpochEndTime() - startTime.getTime())/MIN_TO_MILLI);

				long newDuration = (long)tb.getNumberOfMinutes() - tmp;

				tb.setNumberOfMinutes((int)newDuration);

				// task fitted,now stop and go next task
				break;

			}  // Handle Case #3
			else if ( ( startTime.equals(tb.getStartTime())||
							startTime.before(tb.getStartTime()) )
							&&
							( endTime.equals(tb.getEndingTime()) ||
											endTime.after(tb.getEndingTime()) )	)
			{
				// remove hole from link list
				holelist.remove(num);
				break;
			}

		}
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
			// FIXED:
			holearray[idx] = new TimeBlock(holeTimeBlk);
			//holearray[idx].clone(holeTimeBlk);
		}
		// second : bubble sort based on duration
		// bubble sort helps to determine if the tasks are can fitted after being broken apart
		// looking for the biggest holes

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

						long newTaskStartTime = ( holeStartTime + (holeDuration/2)*MIN_TO_MILLI) - ((newdur/2)*MIN_TO_MILLI);

						Date newTaskStartDate = new Date(newTaskStartTime);

						tbArray[j] = new TimeBlock(newTaskStartDate, (int)newdur);
					}
					// FIXED: return array
					return tbArray;
				}
			}

			divider++; // further divide
		}

		// FIXED: return null
		return null;
	}


	private static	void processRecurringEvent( Event myEvent, Date endDate, LinkedList<TimeBlock> holelist)
	{

		TimeBlock tb = myEvent.getEventTime();
		int eventDurationMin = tb.getNumberOfMinutes();
		int eventHourofDay = myEvent.getHourOfDay();
		int eventMinute = myEvent.getMinOfHour();

		// create Date instance with current time
		Date curTime = new Date();
		Date startDate = myEvent.getRecurrenceStartDate();

		Calendar eventCalndr = Calendar.getInstance();

		boolean backupOneDay = false;

		// set the starting date/time of event based on recurrence start date
		if( startDate == null)
		{	// no recurrence start date provided – assume now
			eventCalndr.setTime(curTime);
			backupOneDay = true;
		}
		else if( curTime.after(startDate) )
		{
			eventCalndr.setTime(curTime);
			backupOneDay = true;
		}
		else
		{
			eventCalndr.setTime(startDate);
			backupOneDay = false;
		}

		// extract the year, month, day of current date
		//int year = calendar.get(Calendar.YEAR);
		//int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		//int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

		// set date/time for the "recurring" event of that day
		// Calendar eventCalndr =
		// new GregorianCalendar(year, month, dayOfMonth );

		eventCalndr.set(Calendar.HOUR_OF_DAY, eventHourofDay); // 24 hour clock
		eventCalndr.set(Calendar.MINUTE, eventMinute);
		eventCalndr.set(Calendar.SECOND, 0);
		eventCalndr.set(Calendar.MILLISECOND, 0);
		
		// need to backup one day to in case current time happens to be when
		// a recurring event is taking place now
		if(backupOneDay == true)
		{
			// Now backup one calendar day
			eventCalndr.add(Calendar.DAY_OF_MONTH, -1);
		}

		// create Date and TimeBlock instances for event
		Date eventDate =  eventCalndr.getTime();

		TimeBlock eventTB = new TimeBlock(eventDate, eventDurationMin);

		// Now we have a “pseudo” event that we will iteratively
		// walk thru day after day up till we pass the due date of the
		// task we are trying to schedule
		// As we walk thru each day we will check the recurrence type
		// and accordingly check for a recurrence match whether it be
		// monthly, weekly, or daily.  If there is a match,then make the
		// event occupy time in the schedule.

		// only process daily events occurring before certain date
		while( eventDate.before(endDate))
		{
			Date eventStartTime = eventTB.getStartTime();
			Date eventEndTime = eventTB.getEndingTime();

			if( myEvent.getRecurrenceType() == RecurrenceType.YEARLY )
			{
				// check day of month - do need to process ?
				int monthOfYear = eventCalndr.get(Calendar.MONTH);

				if(monthOfYear == myEvent.getMonthOfYear())
				{
					// check day of month - do need to process ?
					int dayOfMonth = eventCalndr.get(Calendar.DAY_OF_MONTH);

					if(dayOfMonth == myEvent.getDayOfMonth())
					{
						// Traverse hole list
						// if event lies within hole, then fit it in the hole:
						modifyHoleList( eventStartTime, eventEndTime, holelist );
					}
				}
			}

			if( myEvent.getRecurrenceType() == RecurrenceType.MONTHLY )
			{
				// check day of month - do need to process ?
				int dayOfMonth = eventCalndr.get(Calendar.DAY_OF_MONTH);

				if(dayOfMonth == myEvent.getDayOfMonth())
				{
					// Traverse hole list
					// if event lies within hole, then fit it in the hole:
					modifyHoleList( eventStartTime, eventEndTime, holelist );
				}
			}
			else if( myEvent.getRecurrenceType() == RecurrenceType.WEEKLY )
			{
				// check day of week - do need to process ?
				int weekDay = eventCalndr.get(Calendar.DAY_OF_WEEK);

				if( ((weekDay == Calendar.MONDAY) && (myEvent.getMonday() == true))
								||
								((weekDay == Calendar.TUESDAY) &&
												(myEvent.getTuesday() == true))
								||
								((weekDay == Calendar.WEDNESDAY) &&
												(myEvent.getWednesday() == true))
								||
								((weekDay == Calendar.THURSDAY) &&
												(myEvent.getThursday() == true))
								||
								((weekDay == Calendar.FRIDAY) &&
												(myEvent.getFriday() == true))
								||
								((weekDay == Calendar.SATURDAY) &&
												(myEvent.getSaturday() == true))
								||
								((weekDay == Calendar.SUNDAY) &&
												(myEvent.getSunday() == true))  )
				{
					// Traverse hole list
					// if event lies within hole, then fit it in the hole:
					modifyHoleList( eventStartTime, eventEndTime, holelist );
				}
			}
			else if( myEvent.getRecurrenceType() == RecurrenceType.DAILY )
			{
				// Traverse hole list
				// if event lies within hole, then fit it in the hole:
				modifyHoleList( eventStartTime, eventEndTime, holelist );
			}

			// new increment pseudo-event by 1 day
			eventCalndr.add(Calendar.DAY_OF_MONTH, +1);
			eventDate =  eventCalndr.getTime();

			// update TimeBlock for next iteration
			eventTB.setStartTime(eventDate);
			eventTB.setNumberOfMinutes(eventDurationMin);
		}
	}

	private static void addToTaskMap(Task newTask, TimeBlock[] startTimes, HashMap<UUID, Task> taskMap)
	{

		newTask.setTaskTimes(startTimes);

		UUID id = newTask.getId();

		// Put task in HashMap
		taskMap.put(id, newTask);

	}


	public static void removeTask(UUID id)
	{
		//System.out.println("Micro Lab id:  " + id);
		
		for ( Map.Entry<UUID, Task> entry : Schedule.getCurrentTasks().entrySet() )
		{
			System.out.println();

			Task myTask = entry.getValue();

			System.out.println("Title of task: " + myTask.getTitle());

			UUID curID = myTask.getId();

			System.out.println("Id of task: " + curID);

			if( id == curID )
			{
				Schedule.getCurrentTasks().remove(curID);

				System.out.println();
				System.out.println("Task: " + myTask.getTitle() + " removed");
				break;
			}

		}

		reschedule();

	}

	//	addEvent()
	/**
	Called when wanting to add an event to the schedule and thereby, adding it to the event Map.
	
	The newEvent passed in already has a start time and length. That is, the operator has
	a known event ( course mtg time) with a predetermined start time and length. 
	In this case the scheduler goes thru and checks the schedule to see if whether the newEvent
	conflicts with something already in the schedule. If there is no conflict it returns true and adds the event to the event map, otherwise it returns true.

	The code has to handle both single instance (none recurring) events as well as recurring events.

	 * @param newEvent: the new event to be scheduled
	 * @param taskMap: Hashmap containing all the scheduled tasks
	 * @param eventMap: Hashmap containg all the scheduled events
	 **/
	public static boolean addEvent(Event newEvent, HashMap<UUID, Task> taskMap, HashMap<UUID, Event> eventMap)
	{	
		
		boolean status = processEvent(false, newEvent, taskMap, eventMap);
	
		return status;
	}
	

	public static boolean processEvent(boolean checking, Event newEvent, HashMap<UUID, Task> taskMap, HashMap<UUID, Event> eventMap)
	{	  
		// first check if new event has an assigned start time

		TimeBlock eventTime = newEvent.getEventTime();

		if( eventTime != null )
		{	
			// CASE 1. The newEvent passed in already has a start time and length.

			// scheduler works off an end time or due date; however, since events do
			// NOT have we will establish a pseudo due date for this new event
			// The pseudo due date will depend on the event recurrence properties

			RecurrenceType recurrencetype = newEvent.getRecurrenceType();

			Date newEventDueDate;

			// if not a recurring event then simply make pseudo due date the event endtime
			if( recurrencetype  == RecurrenceType.NONE)
			{
				newEventDueDate = eventTime.getEndingTime();
			}
			
			else
			{
				// get recurrence ending date
				Date recurEndDate = newEvent.getRecurrenceEndDate();
				if( recurEndDate == null)
				{
					// NO recurrence ending date ,so simply go 1 yr out

					// create Date instance with current time
					Date curTime = new Date();

					Calendar eventCalndr = Calendar.getInstance();
					eventCalndr.setTime(curTime);
					eventCalndr.add(Calendar.YEAR, +1);

					// create Date and TimeBlock instances for event
					newEventDueDate =  eventCalndr.getTime();
				}
				else
				{	// make the pseudo due date the recurrence ending date 
					Calendar eventCalndr = Calendar.getInstance();
					eventCalndr.setTime(recurEndDate);
					//TODO: do we need this ?
					//eventCalndr.add(Calendar.DAY_OF_MONTH, +1);

					// create Date and TimeBlock instances for event
					newEventDueDate =  eventCalndr.getTime();
				}
			}
			// Now we have established a pseudo due date for this event,
			// so now we can check schedule for conflicts
	 

			// Create hole list with only with events at higher or equal priority
			// If this is a conflict check then do all priorities (-1)
			int priority = -1;
			
			
			// if not checking then only create a hole list from the events 
			// that are higher priority then the event we're trying to add 
			if(checking == false )
			{
				priority = newEvent.getPriorityLevel();
			}
			
			LinkedList<TimeBlock> holelist = findOpeningsFromEvents(priority, newEventDueDate, eventMap);

			// if not a recurring event then simply check this one event for conflicts
			if( recurrencetype  == RecurrenceType.NONE)
			{
				boolean foundSpot = false;

				Date startTime = eventTime.getStartTime();
				Date endTime = eventTime.getEndingTime();

				foundSpot = findSpot( startTime, endTime, holelist);

				if( foundSpot == true)
				{
					addToEventMap( newEvent, eventMap);
					
					newEvent.printEventInfo();
		
					// only search for conflicts if this is a new event being added'
					// as opposed to an conflict check
					if( checking == false)
					{
						searchForConflicts(newEvent, taskMap, eventMap);
					}
				
					return true;
				}
				else
				{
					newEvent.printEventInfo();
					return false;
				}
			}
			
			else // DAILY, WEEKLY, MONTHLY, YEARLY	
			{  
				// check if can fit in the recurrence event
				// this gets tricky
				// only check if has a recurrence end date
		   
				Date recurEndDate = newEventDueDate;
				
				if(recurEndDate != null)
				{ 
					TimeBlock tb = newEvent.getEventTime();
					int eventDurationMin = tb.getNumberOfMinutes();
					int eventHourofDay = newEvent.getHourOfDay();
					int eventMinute = newEvent.getMinOfHour();

					// create Date instance with current time
					Date curTime = new Date();
					Date recStartDate = newEvent.getRecurrenceStartDate();
			
					Calendar eventCalndr = Calendar.getInstance();

					boolean advanceOneDay = false;

					// set the starting date/time of event based on recurrence start date
					if( recStartDate == null)
					{	// no recurrence start date provided – assume now
						eventCalndr.setTime(curTime);
						advanceOneDay = true;
					}
					
					else if( curTime.after(recStartDate) )
					{
						eventCalndr.setTime(curTime);
						advanceOneDay = true;
					}
					else // curTime before or equals 
					{
						eventCalndr.setTime(recStartDate);
						advanceOneDay = false;
					}

					eventCalndr.set(Calendar.HOUR_OF_DAY, eventHourofDay); // 24 hour clock
					eventCalndr.set(Calendar.MINUTE, eventMinute);
					eventCalndr.set(Calendar.SECOND, 0);
					eventCalndr.set(Calendar.MILLISECOND, 0);
					
					// need to backup one day to in case current time happens to be when
					// a recurring event is taking place now
					if(advanceOneDay == true)
					{
						// Now advance one calendar day
						eventCalndr.add(Calendar.DAY_OF_MONTH, +1);
					}	
			
					// create Date and TimeBlock instances for event
					Date eventDate =  eventCalndr.getTime();

					TimeBlock eventTB = new TimeBlock(eventDate, eventDurationMin);

					// Now we have a pseudo event that we will iteratively 
					// walk thru day after day 
					// As we walk thru each day we will check the recurrence type
					// and accordingly check for a recurrence match whether it be
					// monthly, weekly, or daily.  If there is a match, then if the
					// event fits in the schedule.

					// assume new event will fit, unless proven otherwise
					boolean eventFits = true;

					while( eventDate.before(recurEndDate) || eventDate.equals(recurEndDate) )
					{
						Date startTime = eventTB.getStartTime();
						Date endTime = eventTB.getEndingTime();
			
						if( newEvent.getRecurrenceType() == RecurrenceType.YEARLY )
						{
							// check day of month - do need to process ?
						int monthOfYear = eventCalndr.get(Calendar.MONTH);
						
						if(monthOfYear == newEvent.getMonthOfYear())
						{
						// check day of month - do need to process ?
							int dayOfMonth = eventCalndr.get(Calendar.DAY_OF_MONTH);

							if(dayOfMonth == newEvent.getDayOfMonth())
							{
								boolean foundSpot = findSpot( startTime, endTime, holelist);
								if( foundSpot == false)
								{
									eventFits = false; // conflict with schedule
									break;		    // stop checking further get out
								}
							}
						}
					}
					else if( newEvent.getRecurrenceType() == RecurrenceType.MONTHLY )
					{
						// check day of month - do need to process ?
						int dayOfMonth = eventCalndr.get(Calendar.DAY_OF_MONTH);

						if(dayOfMonth == newEvent.getDayOfMonth())
						{
							boolean foundSpot = findSpot( startTime, endTime, holelist);
							if( foundSpot == false)
							{
								eventFits = false; // conflict with schedule
								break;		    // stop checking further – get out
							}
						}
					}
					else if( newEvent.getRecurrenceType() == RecurrenceType.WEEKLY )
					{ 
						// check day of week - do need to process ?
						int weekDay = eventCalndr.get(Calendar.DAY_OF_WEEK);

						if( ((weekDay == Calendar.MONDAY) && (newEvent.getMonday() == true))
								||
							((weekDay == Calendar.TUESDAY) && (newEvent.getTuesday() == true))
								||
							((weekDay == Calendar.WEDNESDAY) && (newEvent.getWednesday() == true))
								||
							((weekDay == Calendar.THURSDAY) && (newEvent.getThursday() == true))
								||
							((weekDay == Calendar.FRIDAY) && (newEvent.getFriday() == true))
								||
							((weekDay == Calendar.SATURDAY) && (newEvent.getSaturday() == true))
								||
							((weekDay == Calendar.SUNDAY) && (newEvent.getSunday() == true))  )
						{
							boolean foundSpot = findSpot( startTime, endTime, holelist);
							if( foundSpot == false)
							{
								eventFits = false; // conflict with schedule
								break;		    // stop checking further – get out
							}
						}
					}
					else if( newEvent.getRecurrenceType() == RecurrenceType.DAILY )
					{
		     			boolean foundSpot = findSpot( startTime, endTime, holelist);
		     			if( foundSpot == false)
		     			{
							eventFits = false; // conflict with schedule
							break;		    // stop checking further – get out
		     			}
					}

					// new increment pseudo-event by 1 day
					eventCalndr.add(Calendar.DAY_OF_MONTH, +1); 
					eventDate =  eventCalndr.getTime();

					// update TimeBlock for next iteration
					eventTB.setStartTime(eventDate);
					eventTB.setNumberOfMinutes(eventDurationMin);

				} // end: while( eventDate.before(recurEndDate) . . .

				if(eventFits == true )
				{
				
					addToEventMap( newEvent, eventMap);
					
					newEvent.printEventInfo();
					
					 // only search for conflicts if this is a new event being added'
					 // as opposed to an conflict check
				    if( checking == false)
					{
						searchForConflicts(newEvent, taskMap, eventMap);
					}
					return true;
				}
				else
				{
					newEvent.printEventInfo();
					return false;
				}
				
		 }
		 else
	 	{	// recurring event has no recurrence end date
			// not sure how to handle this
			 newEvent.printEventInfo();
			 return false;
		}
		   
	   }
	} 
		// USER DIDN'T PROVIDE START TIME FOR THE EVENT 
		newEvent.printEventInfo();
		return false;
	}

	private static void addToEventMap(Event newEvent, HashMap<UUID, Event> eventMap)
	{	
			
		UUID id = newEvent.getId();

		// Put event back in HashMap
		eventMap.put(id, newEvent);
			
	}

	private static boolean findSpot( Date startTime, Date endTime, LinkedList<TimeBlock> holelist)
	{
	   boolean foundSpot = false;

	   
	   // go through holes
	   for(int idx=0; idx < holelist.size(); idx++)
	   {
			TimeBlock holeTB = holelist.get(idx);
			
				// Verify that event can be placed either between hole start time and end time
				if( ( startTime.after(holeTB.getStartTime()) ||
					  startTime.equals(holeTB.getStartTime()) )
						&& 
					( endTime.before(holeTB.getEndingTime()) ||
					  endTime.equals(holeTB.getEndingTime()) ) )
				{
					// event fits in current schedule - done !
					foundSpot = true;
					break;
				}
		}
		return foundSpot;
	}
	
	private static void searchForConflicts( Event newEvent, HashMap<UUID, Task> taskMap, HashMap<UUID, Event> eventMap)
	{

		for ( Map.Entry<UUID, Event> entry : eventMap.entrySet() )
		{

			Event myEvent = entry.getValue();
				
			TimeBlock eventTime = myEvent.getEventTime();

			// skipping events with no assigned times
			if( eventTime == null )
			{
				continue;
			} 
			
			// only need to check events lower in priority level
			if( myEvent.getPriorityLevel() >= newEvent.getPriorityLevel() )
			{
			     continue;
			}

			myEvent.setChecking(true);

			boolean status = processEvent(true, myEvent, taskMap, eventMap);

			// if event cannot be fitted, then mark as displaced
			if( status == false)
			{
				myEvent.setDisplaced(true);
			}
			
			myEvent.setChecking(false);

		}
		
		// loop thru all existing tasks

		for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
		{

			Task myTask = entry.getValue();

			// skip over completed tasks
			if( myTask.isCompleted() == true )
			{
				continue;
			}

			// skip over displaced tasks
			if( myTask.isDisplaced() == true )
			{
				continue;
			}
			
			TimeBlock[] taskTimes = myTask.getTaskTimes();

			// If task does not have start time, then ignore
			if( taskTimes == null)
			{
				continue;
			}
			
			Date taskDueDate = myTask.getDueDate();

			// create hole list from all events
			LinkedList<TimeBlock> holelist = findOpeningsFromEvents(-1, taskDueDate, eventMap);

			// assume no conflict, unless proven otherwise
			boolean conflicting = false;

			// loop thru the task start times
			for(int i = 0; i < taskTimes.length;i++)
			{
			   Date taskStartTime = taskTimes[i].getStartTime();

			   Date taskEndTime = taskTimes[i].getEndingTime();
			   
			   // Case where start time is after the end time.  Should never actually happen.  
               if(  taskEndTime.before(taskStartTime) || taskEndTime.equals(taskStartTime) )
               {
                   continue;
               }
			   
            	boolean foundSpot = findSpot( taskStartTime, taskEndTime, holelist);

	      		if( foundSpot == false)
	      		{
					// conflict – move on to next task
					conflicting = true;
					break;
				} 

			}
			if( conflicting == true)
			{
				myTask.isDisplaced(true);
				// update task map
				addToTaskMap(myTask, taskTimes, taskMap);
			}

		}
	}
	
	private static LinkedList<TimeBlock> findOpeningsFromEvents( int priorityLevel, Date newDueDate, HashMap<UUID, Event> eventMap )
	{
		// Start the "hole list" with a single node/hole with a time
		// span from current time to the due date.

		LinkedList<TimeBlock> holelist = new LinkedList<TimeBlock>();

		// create Date instance with current time
		Date curTime = new Date();

		// Create TimeBlock for the first Hole
		// add 1 to account for truncation after division
		long numMinutesDuration = (long) ((newDueDate.getTime() - curTime.getTime())/MIN_TO_MILLI) + 1;
		
		TimeBlock firsthole = new TimeBlock(curTime, (int)numMinutesDuration);

		// Add the TimeBlock to the hole list
		holelist.addFirst(firsthole);
		
		// Go thru Event map entries

		for ( Map.Entry<UUID, Event> entry : eventMap.entrySet() )
		{

			Event myEvent = entry.getValue();
				
			TimeBlock eventTime = myEvent.getEventTime();

			if( eventTime == null )
			{
				continue;
			}

			// skip event which is being checked for a conflict
			if( myEvent.isChecking() == true )
			{
				continue;
			}
			// skip event which are displaced
			if( myEvent.isDisplaced() == true )
			{
				continue;
			}

			// skip tasks lower than set priority
			// priority level between 0 and 10, where 0 is the lowest priority
			if( priorityLevel > -1)
			{
				
				// Example:
				// New event priority level = 5
				// Previous. event = 4
				
				// Not allowing lower priority events to fill up schedule time and create holes.  
				
				if( myEvent.getPriorityLevel() < priorityLevel)
				{
					continue;
				}
			}


			RecurrenceType recurrencetype = myEvent.getRecurrenceType();

			// if not a recurring event then simple process as is
			if( recurrencetype  == RecurrenceType.NONE)
			{
				Date eventStartTime = eventTime.getStartTime();
				Date eventEndTime = eventTime.getEndingTime();

				// only care about events occurring BEFORE the new task due date
				// AND have not completed BEFORE the current time.

			      if( ( eventStartTime.before(newDueDate)) && ( eventEndTime.after(curTime) ) )
				   {
					// Traverse hole list
					// if existing event lies within hole, then fit it in the hole					
			    	// "break" hole apart
					modifyHoleList( eventStartTime, eventEndTime, holelist );
					
				   }
			}
			else if ((recurrencetype == RecurrenceType.DAILY ) ||
			        (recurrencetype  == RecurrenceType.WEEKLY ) ||
			        (recurrencetype  == RecurrenceType.MONTHLY ) || 
			        (recurrencetype  == RecurrenceType.YEARLY ) )
			{
				processRecurringEvent( myEvent, newDueDate, holelist);
			}
		}
		return holelist;
	}

	public static void removeEvent(UUID id)
	{
		//System.out.println("Event id:  " + id);
		
		for ( Map.Entry<UUID, Event> entry : Schedule.getCurrentEvents().entrySet() )
		{
			System.out.println();

			Event myEvent = entry.getValue();

			System.out.println("Title of event: " + myEvent.getTitle());

			UUID curID = myEvent.getId();

			System.out.println("Id of event: " + curID);

			if( id == curID )
			{
				Schedule.getCurrentEvents().remove(curID);

				System.out.println();
				System.out.println("Event: " + myEvent.getTitle() + " removed");
				break;
			}

		}

		reschedule();

	}

	public static void addTimeFence(TimeFence timeFence)
	{
		Schedule.getCurrentTimeFences().put(timeFence.getId(),timeFence);
	}

	public static void removeTimeFence(UUID id)
	{
		Schedule.getCurrentTimeFences().remove(id);
	}

	public static void reschedule()
	{
		// TODO 
	}


}