package com.codecaptured.autoagendacore.usecases;

import com.codecaptured.autoagendacore.entities.Event;
import com.codecaptured.autoagendacore.entities.RecurrenceType;
import com.codecaptured.autoagendacore.entities.Schedule;
import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.Date;

/**
 * This is used when interacting (creating, modifying, removing) events. Also controls the interface
 * needed for talking to this class
 */
public class EventInteractor
{
	// TODO: Should also return some sort of message to indicate if it was scheduled properly or not.

	/**
	 * Add a new event to the schedule. Will be scheduled if using the default date/time
	 * @param newEvent The new event
	 */
	public static void addEvent(UserEvent newEvent)
	{
		// Create a new ID
		UUID id = UUID.randomUUID();

		// Add ID to UserEvent
		newEvent.setId(id);

		// Make the event
		Event event = userEventToEvent(newEvent);

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addEvent(event);
	}

	/**
	 * Adds Sleeping as an event to the schedule.
	 */
	public static void addSleepingEvent()
	{
		// Create a new ID
		UUID id = UUID.randomUUID();

		// Fix error if Schedule has not be instantiated
		if (Schedule.getCurrentTasks() == null || Schedule.getCurrentEvents() == null)
		{
			new Schedule();
		}

		// Hardcoded event info
		// Sleep 7 Hours
		int sleepingHours = 420;
		Calendar eventTime = new GregorianCalendar(2018,3,12,23,30,00);

		// Sleep from 11:30 - 6:30 each day
		Date startTimeEvent = eventTime.getTime();
		TimeBlock eventTB = new TimeBlock(startTimeEvent, sleepingHours);

		Event event = new Event(id, "Sleeping", "Time which user sleeps", eventTB, 10, null);

		//RecurrenceType recurrenceType, Date recurrenceStartDate, Date recurrenceEndDate,
		//int hourOfDay, int minOfHour,
		//boolean monday, boolean tuesday, boolean wednesday, boolean thursday,
		//boolean friday, boolean saturday, boolean sunday, int dayOfMonth, int monthOfYear)

		Calendar eventEndTime = new GregorianCalendar(2018,4,1,6,30,00);
		Date endTimeEvent = eventEndTime.getTime();

		event.setRecurrenceInfo(RecurrenceType.DAILY, startTimeEvent, endTimeEvent, 23, 30, false,
						false, false, false, false, false, false, -1,
						-1);

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addEvent(event);
	}



	/**
	 * Change an existing scheduled event and reschedule it if using the default date/time
	 * @param originalEvent The original event to be changed
	 * @param newEvent The new changed event
	 */
	public static void modifyEvent(UserEvent originalEvent, UserEvent newEvent)
	{
		// Make the IDs the same
		newEvent.setId(originalEvent.getId());

		// Remove the old event
		Scheduler.removeEvent(originalEvent.getId());

		// Make the event
		Event event = userEventToEvent(newEvent);

		// Add to scheduler to decide where to put it in the schedule
		Scheduler.addEvent(event);
	}

	/**
	 * Remove an event from the schedule
	 * @param oldEvent The event to be removed
	 */
	public static void removeEvent(UserEvent oldEvent)
	{
		// Delete old event
		Scheduler.removeEvent(oldEvent.getId());
	}

	/**
	 * Convert a user event to an event
	 * @param userEvent User event to be based off of
	 * @return event with same data as userEvent
	 */
	protected static Event userEventToEvent(UserEvent userEvent)
	{
		// Make the event
		return new Event(userEvent.getId(), userEvent.getTitle(), userEvent.getDescription(),
						userEvent.getEventTime(), userEvent.getPriorityLevel(), userEvent.getTags());
	}

	/**
	 * Convert an event to a user event
	 * @param event Event to be based off of
	 * @param userEvent Must be passed in (interfaces can't be instantiated)
	 * @return tempUserEvent with the same data as event
	 */
	protected static UserEvent eventToUserEvent(Event event, UserEvent userEvent)
	{
		// Set the passed user event to have all the attributes of the event
		userEvent.setId(event.getId());
		userEvent.setTitle(event.getTitle());
		userEvent.setDescription(event.getDescription());
		userEvent.setEventTime(event.getEventTime());
		userEvent.setPriorityLevel(event.getPriorityLevel());
		userEvent.setTags(event.getTags());
		return userEvent;
	}

	/**
	 * Convert multiple events to user events
	 * @param events Events to be based off of
	 * @param userEvents Must be passed in (interfaces can't be instantiated)
	 * @return user events with the same data as events
	 */
	protected static UserEvent[] eventsToUserEvents(Event[] events, UserEvent[] userEvents)
	{
		// Value to be returned
		UserEvent[] convertedEvents = new UserEvent[events.length];

		// Convert each event to a user event
		for (int i = 0; i < events.length; i++)
		{
			convertedEvents[i] = eventToUserEvent(events[i], userEvents[0]);
		}

		// Return new user events
		return convertedEvents;
	}

	/**
	 * The event data object used to talk with the use cases. Includes default values for events
	 */
	public interface UserEvent
	{
		// Default values
		String DEFAULT_TITLE = "";
		String DEFAULT_DESCRIPTION = "";
		TimeBlock DEFAULT_EVENT_TIME = new TimeBlock(new Date(0), 0);
		int DEFAULT_PRIORITY_LEVEL = 5;
		String[] DEFAULT_TAGS = {""};

		// Getters and setters
		// Values set by user
		String getTitle();
		void setTitle(String title);
		String getDescription();
		void setDescription(String description);
		TimeBlock getEventTime();
		void setEventTime(TimeBlock eventTime);
		int getPriorityLevel();
		void setPriorityLevel(int priorityLevel);
		String[] getTags();
		void setTags(String[] tags);

		// Set by software
		UUID getId();
		void setId(UUID id);
	}

}
