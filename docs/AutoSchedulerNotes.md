#Auto Scheduler Notes: 
---

Constraints on Scheduler:  

  * Tasks cannot overlap  
  * Off-limit time blocks(example: sleeping)  
  * Use "Leveling"  
  * Definition from web: **leveling works by delaying tasks  
    or splitting them such that the resources (time required)   
    that are assigned to those tasks are no longer overloaded** 

Scheduler will have finite time blocks (example: 15 minutes):  

  * Each day would be made up of 24 x 4 time blocks   
  * 8 hrs sleep  ( 32 time blocks off-limits)   

Scheduler will have to keep track of blocks: 4 kinds of blocks:  

  * Block occupied by task
  * Block occupied by event 
  * Block free 
  * Block off-limits 

Following are assumed: when task is entered.  
It will have a due date, priority level, and est. time of completion   
**(example: Homework due on Feb. 2nd 11:59 pm.  High priority, 2 hrs est.)**  

---

Auto Scheduler steps:  

  1. Compare due date/time with current date/time + est. time of completion.    
     If due time/date < current date/time + est. time of completion, then task cannot be scheduled.   
     **(Example: Time is 11 pm on Feb. 2nd.  Auto Scheduler will not be able to schedule task)**  

  2. Check if contiguous time blocks are available between current date/time   
     and due date/time to accommodate new task and its duration.    
  3. If there is, then fit new task into schedule using some algorithm (TBD) to maximize “leveling”  
  4. If there is no fit, then there is 3 choices:  
		* Return cannot add task to the existing schedule
		* Break task into smaller pieces to fit existing schedule if task is breakable 
		* Shift lower priority tasks around to create a “hole” in schedule to fit the new task 
		
Question: Should the schedule be able to break up tasks to fit them into the schedule.    
And if so, need to add a check box to task entry to GUI to say “Task can be broken up”.  

---     		
  