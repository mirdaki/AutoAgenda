package com.codecaptured.autoagendacore.entities;

import com.codecaptured.autoagendacore.entities.TimeBlock;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * Events occur at very specific times and are not necessarily scheduled with other tasks
 */

public class Event
{
	
	private UUID id;
	private String title;
	private String description;
	private TimeBlock eventTime;
	private int priorityLevel;
	private String[] tags;
	
	private boolean checking;
	private boolean displaced;
	
	private RecurrenceType recurrenceType;
	private Date recurrenceStartDate;
	private Date recurrenceEndDate;
	
	// time of day info
	private int hourOfDay;
	private int minOfHour;

	//	weekly info
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;

	// monthly info
	private int dayOfMonth;  // set to -1 if NA

	// yearly info
	private int monthOfYear; // set to -1 if NA

	/**
	 * Create an event that occurs at a specific time and and are not necessarily scheduled
	 * @param id Unique ID
	 * @param title Name of the event
	 * @param description Additional information on the event
	 * @param eventTime Start time and length of the event in minutes
	 * 			NOTE: For recurring event, only the length is used (Start time is ignored)
	 * @param priorityLevel Priority on a scale of 0 to 10, 5 being average
	 * @param tags Tags or categories associated with the event. Used to organize the event
	 */
	
	public Event(UUID id, String title, String description, TimeBlock eventTime, int priorityLevel, String[] tags)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.eventTime = eventTime;
		this.priorityLevel = priorityLevel;
		this.tags = tags;
		this.recurrenceType = RecurrenceType.NONE;
		this.checking = false;
		this.displaced = false;

		// recurrence settings
		this.hourOfDay = -1;
		this.minOfHour = -1;

		//	weekly info
		this.monday = false;
		this.tuesday = false;
		this.wednesday = false;
		this.thursday = false;
		this.friday = false;
		this.saturday = false;
		this.sunday = false;

		// monthly info
		this.dayOfMonth = -1;

		// yearly info
		this.monthOfYear = -1;
	}

	public void setRecurrenceInfo( RecurrenceType recurrenceType, Date recurrenceStartDate, Date recurrenceEndDate,
	int hourOfDay, int minOfHour,
	boolean monday, boolean tuesday, boolean wednesday, boolean thursday,
	boolean friday, boolean saturday, boolean sunday, int dayOfMonth, int monthOfYear)
	{
		this.recurrenceType = recurrenceType;

		Calendar eventCalndr = Calendar.getInstance();
		eventCalndr.setTime(recurrenceStartDate);
		eventCalndr.set(Calendar.HOUR_OF_DAY, hourOfDay); // 24 hour clock
		eventCalndr.set(Calendar.MINUTE, minOfHour);
		eventCalndr.set(Calendar.SECOND, 0);
		eventCalndr.set(Calendar.MILLISECOND, 0);

		this.recurrenceStartDate = eventCalndr.getTime();

		if( recurrenceEndDate != null)
		{
			eventCalndr.setTime(recurrenceEndDate);
			eventCalndr.set(Calendar.HOUR_OF_DAY, hourOfDay); // 24 hour clock
			eventCalndr.set(Calendar.MINUTE, minOfHour);
			eventCalndr.set(Calendar.SECOND, 0);
			eventCalndr.set(Calendar.MILLISECOND, 0);
			int duration = this.eventTime.getNumberOfMinutes();
			eventCalndr.add(Calendar.MINUTE, duration);
			this.recurrenceEndDate = eventCalndr.getTime();
		}
		else
		{
			this.recurrenceEndDate = recurrenceEndDate;
		}

		this.hourOfDay = hourOfDay;
		this.minOfHour = minOfHour;

		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;

		this.dayOfMonth = dayOfMonth;

		this.monthOfYear = monthOfYear;
	}

	public RecurrenceType getRecurrenceType()
	{
		return recurrenceType;
	}
	
	//public void setRecurrenceType( RecurrenceType recurrenceType )
	//{
	//	this.recurrenceType = recurrenceType;
	//}

	public Date getRecurrenceStartDate()
	{
		return recurrenceStartDate;
	}
	
	public void setRecurrenceStartDate( Date recurrenceStartDate )
	{
		this.recurrenceStartDate = recurrenceStartDate;
	}
	
	public void setRecurrenceEndDate( Date recurrenceEndDate )
	{
		this.recurrenceEndDate = recurrenceEndDate;
	}
	
	public Date getRecurrenceEndDate()
	{
		return recurrenceEndDate;
	}
	
	public int getHourOfDay()
	{
		return hourOfDay;
	}
	
	public void setHourOfDay( int hourOfDay)
	{
		this.hourOfDay = hourOfDay;
	}

	public int getMinOfHour()
	{
		return minOfHour;
	}
	
	public void setMinOfHour( int minOfHour)
	{
		this.minOfHour = minOfHour;
	}

	public boolean getMonday()
	{
		return monday;
	}
	
	public void setMonday( boolean set )
	{
		this.monday = set;
	}

	public boolean getTuesday()
	{
		return tuesday;
	}
	
	public void setTuesday( boolean set )
	{
		this.tuesday = set;
	}

	public boolean getWednesday()
	{
		return wednesday;
	}
	
	public void setWednesday( boolean set )
	{
		this.wednesday = set;
	}

	public boolean getThursday()
	{
		return thursday;
	}
	
	public void setThursday( boolean set )
	{
		this.thursday = set;
	}

	public boolean getFriday()
	{
		return friday;
	}
	
	public void setFriday( boolean set )
	{
		this.friday = set;
	}

	public boolean getSaturday()
	{
		return saturday;
	}
	
	public void setSaturday( boolean set )
	{
		this.saturday = set;
	}

	public boolean getSunday()
	{
		return sunday;
	}
	
	public void setSunday( boolean set )
	{
		this.sunday = set;
	}
	
	public int getMonthOfYear()
	{
		return monthOfYear;
	}
	
	public void setMonthOfYear(int monthOfYear)
	{
		this.monthOfYear = monthOfYear;
	}
	
	public int getDayOfMonth()
	{
		return dayOfMonth;
	}
	
	public void setDayOfMonth(int dayOfMonth)
	{
		this.dayOfMonth = dayOfMonth;
	}
	
	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public TimeBlock getEventTime()
	{
		return eventTime;
	}

	public void setEventTime(TimeBlock eventTime)
	{
		this.eventTime = eventTime;
	}

	public int getPriorityLevel()
	{
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel)
	{
		this.priorityLevel = priorityLevel;
	}

	public String[] getTags()
	{
		return tags;
	}

	public void setTags(String[] tags)
	{
		this.tags = tags;
	}
	public boolean isChecking()
	{
		return checking;
	}
	
	public void setChecking(boolean checking)
	{
		this.checking = checking;
	}
	
	public boolean isDisplaced()
	{
		return displaced;
	}
	
	public void setDisplaced(boolean displaced)
	{
		this.displaced = displaced;
	}
	
	public void printEventInfo()
	{
		System.out.println();
		System.out.println("New Event: 		" + title);
		System.out.println("Event Start date: 	" + eventTime.getStartTime());
		System.out.println("Event due date:     	" + eventTime.getEndingTime());
		System.out.println("Event priority level: 	" + priorityLevel);
		System.out.println();
	}
	
}
