import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Alex on 2/13/18.
 */

public class main 
{

	public static void main (String[] args) 
	{
		
		
		// Test program for the addTask scheduler algorithm 
		
		// Allocate New Task Map 
		HashMap<UUID, Task> taskMap = new HashMap<UUID, Task>();
		
		String[] tags1 = {"HW", "DSP"};
		String[] tags2 = {"HW", "MicroP"};
		String[] tags3 = {"HW", "Design"};
		
		// Dates of tasks  
		Date dueDateTask1 = new Date(118, 1, 26, 23, 59);
		Date dueDateTask2 = new Date(118, 1, 25, 23, 59);
		Date dueDateTask3 = new Date(118, 3, 28, 23, 59);
		
		System.out.println("Due Date of Task 1 is " + dueDateTask1);
		System.out.println("Due Date of Task 2 is " + dueDateTask2);
		System.out.println("Due Date of Task 3 is " + dueDateTask3);
		
		// Random Task IDs
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		UUID id3 = UUID.randomUUID();
		
		// Create new tasks
		Task task1 = new Task(id1, "Homework", "DSP", dueDateTask1, 60, 1, tags1);
		Task task2 = new Task(id2, "Lab", "MicroP", dueDateTask2, 120, 2, tags2);
		Task task3 = new Task(id3, "Project", "DigitalDesign", dueDateTask3, 240, 3, tags3);
		
		//Date currentDate = new Date();

		Date futureDate1 = new Date(1519099356118L);
		Date futureDate2 = new Date(1519199356118L);
		Date futureDate3 = new Date(1519299356118L);
		
		System.out.println(futureDate1);
		System.out.println(futureDate2);
		System.out.println(futureDate3);
		
		TimeBlock block1 = new TimeBlock(futureDate1 ,60);
		TimeBlock block2 = new TimeBlock(futureDate2 ,80);
		TimeBlock block3 = new TimeBlock(futureDate3 , 100);
		
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
		
		
		// Print task map 
		for ( Map.Entry<UUID, Task> entry : taskMap.entrySet() )
		{
			UUID key = entry.getKey();
			System.out.println(key);
			
			//Task value = entry.getValue();
			//TimeBlock[] tb = value.getTaskTimes();
			//System.out.println(key + " : " + tb[0].getStartTime());
		}
		
		
		Scheduler.addTask(task1, taskMap);
		Scheduler.addTask(task2, taskMap);
		Scheduler.addTask(task3, taskMap);
		
		
		
		
		
	}

}
