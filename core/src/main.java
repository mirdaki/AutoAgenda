import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Alex on 2/13/18.
 */

public class main 
{


	public static void main(String[] args) {
		
		// Test program for the addTask scheduler algorithm 
		
		/*
		
		// Allocate New Task Map 
		HashMap<UUID, Task> taskMap = new HashMap<UUID, Task>();
		
		String[] tags1 = {"HW", "DSP"};
		String[] tags2 = {"HW", "MicroP"};
		String[] tags3 = {"HW", "Design"};
		
		// Dates of tasks 
		Calendar dueTask1 = new GregorianCalendar(2018,3,21,23,59,00);
		Calendar dueTask2 = new GregorianCalendar(2018,4,11,23,59,00);
		Calendar dueTask3 = new GregorianCalendar(2018,3,28,23,59,00);
		
		//Date dueDateTask1 = new Date(118, 2, 26, 23, 59);
		//Date dueDateTask2 = new Date(118, 2, 25, 23, 59);
		//Date dueDateTask3 = new Date(118, 3, 28, 23, 59);
		
		Date dueDateTask1 = dueTask1.getTime();
		Date dueDateTask2 = dueTask2.getTime();
		Date dueDateTask3 = dueTask3.getTime();
		
		//System.out.println("Due Date of Task 1 is " + dueDateTask1);
		
		// Random Task IDs
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		UUID id3 = UUID.randomUUID();
		
		// Create three tasks already scheduled
		Task task1 = new Task(id1, "Homework", "DSP",false, dueDateTask1, 60, 1, tags1);
		Task task2 = new Task(id2, "Lab", "MicroP", false, dueDateTask2, 120, 2, tags2);
		Task task3 = new Task(id3, "Project", "DigitalDesign", false, dueDateTask3, 240, 3, tags3);
		
		
		//Date currentDate = new Date();
		Calendar startTask1 = new GregorianCalendar(2018,2,21,13,00,00);
		Calendar startTask2 = new GregorianCalendar(2018,3,11,10,00,00);
		Calendar startTask3 = new GregorianCalendar(2018,2,28,15,00,00);
		
		Date startDate1 = startTask1.getTime();
		Date startDate2 = startTask2.getTime();
		Date startDate3 = startTask3.getTime();
		
		//System.out.println(futureDate1);
		//System.out.println(futureDate2);
		//System.out.println(futureDate3);
		
		TimeBlock block1 = new TimeBlock(startDate1 ,60);
		TimeBlock block2 = new TimeBlock(startDate2 ,80);
		TimeBlock block3 = new TimeBlock(startDate3 , 100);
		
		TimeBlock[] taskTime1 = {block1};
		TimeBlock[] taskTime2 = {block2};
		TimeBlock[] taskTime3 = {block3};
		
		task1.setTaskTimes(taskTime1);
		task2.setTaskTimes(taskTime2);
		task3.setTaskTimes(taskTime3);
		
		
		// Put tasks in HashMap
		taskMap.put(id1, task1);
		taskMap.put(id2, task2);
		taskMap.put(id3, task3);
		
		System.out.println();
		
		
		/*
		// Print task map of tasks already scheduled 
		for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
		{
			UUID key = entry.getKey();
			System.out.println(key);
			
			//Task value = entry.getValue();
			//TimeBlock[] tb = value.getTaskTimes();
			//System.out.println(key + " : " + tb[0].getStartTime());
		}
		
		// this is a new task to be scheduled
		UUID id4 = UUID.randomUUID();
//		Date dueDateTask4 = new Date(118, 4, 28, 23, 59);
		Calendar dueTask4 = new GregorianCalendar(2018,4,28,23,59,00);
		Date dueDateTask4 = dueTask4.getTime();
		Task task4 = new Task(id4, "StudyForExam", "DSP",false, dueDateTask4, 60, 1, null);
		System.out.println("New Task Due:"+ dueDateTask4);
	
		Scheduler.addTask(task4, taskMap);
		
		**/
		
		// New HashMap for tasks
		HashMap<UUID, Task> taskMap = new HashMap<UUID, Task>();
		
		// New HashMap for events
		HashMap<UUID, Event> eventMap = new HashMap<UUID, Event>();
		
		// Dates of tasks 
		Calendar dueTask1 = new GregorianCalendar(2018,3,3,7,00,00);
		Calendar dueTask2 = new GregorianCalendar(2018,3,2,23,59,00);
		Calendar dueTask3 = new GregorianCalendar(2018,3,2,23,59,00);
		
		Date dueDateTask1 = dueTask1.getTime();
		Date dueDateTask2 = dueTask2.getTime();
		Date dueDateTask3 = dueTask3.getTime();
				
				
		// Random Task IDs
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		UUID id3 = UUID.randomUUID();
				
		// Create three tasks already scheduled
		Task task1 = new Task(id1, "Sleep", "sleeping",false, dueDateTask1, 420, 0, null);
		Task task2 = new Task(id2, "Lab", "MicroP", false, dueDateTask2, 120, 0, null);
		Task task3 = new Task(id3, "HW", "Design", false, dueDateTask3, 60, 0, null);
				
		
		// **************************************************************
		//
		//   Update start times for tasks when testing at a later date.	
		//
		// **************************************************************
		//Date currentDate = new Date();
		Calendar startTask1 = new GregorianCalendar(2018,3,2,23,00,00);
		Calendar startTask2 = new GregorianCalendar(2018,3,2,16,00,00);
		Calendar startTask3 = new GregorianCalendar(2018,3,2,18,00,00);
		
		Date startDate1 = startTask1.getTime();
		Date startDate2 = startTask2.getTime();
		Date startDate3 = startTask3.getTime();
		
		System.out.println(startDate1);
		System.out.println(startDate2);
		System.out.println(startDate3);
				
		TimeBlock block1 = new TimeBlock(startDate1 ,420);
		TimeBlock block2 = new TimeBlock(startDate2 ,120);
		TimeBlock block3 = new TimeBlock(startDate3 ,60);
				
		TimeBlock[] taskTime1 = {block1};
		TimeBlock[] taskTime2 = {block2};
		TimeBlock[] taskTime3 = {block3};
				
		task1.setTaskTimes(taskTime1);
		task2.setTaskTimes(taskTime2);
		task3.setTaskTimes(taskTime3);
			
				
		// Put tasks in HashMap
		taskMap.put(id1, task1);
		taskMap.put(id2, task2);
		taskMap.put(id3, task3);
				
		System.out.println();
		
		/**
		
		//****** un-comment out when testing addTask *********
		
		// this is a new task to be scheduled
		UUID id4 = UUID.randomUUID();
		Calendar dueTask4 = new GregorianCalendar(2018,3,2,23,00,00);
		Date dueDateTask4 = dueTask4.getTime();
		Task task4 = new Task(id4, "HW", "DSP", false, dueDateTask4, 60, 0, null);
		System.out.println("New Task Due: "+ dueDateTask4);
		
		System.out.println();
		
		TimeBlock[] tb = {};
						
		tb = Scheduler.addTask(task4, taskMap, eventMap);
				
		System.out.println("Task start date: " + tb[0].getStartTime());
		
		System.out.println("Mins Required: " + tb[0].getNumberOfMinutes());
		
		System.out.println();
		
		*/
		
		
		System.out.println("ID Sleep       = " + id1);
		System.out.println("ID Micro Lab   = " + id2);
		System.out.println("ID HW Design   = " + id3);
		
		System.out.println();
		
		// Want to remove Microprocessors Lab from schedule: UUID = id2
		// initially scheduled in from 4 PM until 6 PM
		
		Scheduler.removeTask(id2, taskMap, eventMap);
		

	}

}
