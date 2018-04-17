package com.codecaptured.autoagenda;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codecaptured.autoagendacore.entities.TimeBlock;

import org.w3c.dom.Text;

import java.util.List;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.TaskViewHolder> {

	private List<UserTask> taskList;

	public ListFragmentAdapter(List<UserTask> taskList) {
		this.taskList = taskList;
	}

	@Override
	public int getItemCount() {
		return taskList.size();
	}

	@Override
	public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
		String priority;
		String myFormat = "MM/dd/yy hh:mm aa";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, java.util.Locale.US);

		UserTask userTask = taskList.get(i);

		if(userTask.priorityLevel == 1)
			priority = "Low";
		else if(userTask.priorityLevel == 2)
			priority = "Medium";
		else
			priority = "High";

		taskViewHolder.mDescription.setText(userTask.getDescription());
//		taskViewHolder.mCompleted.setText(userTask.getCompleted().toString());
		taskViewHolder.mDueDate.setText("Due: " + sdf.format(userTask.getDueDate()).toString());
		taskViewHolder.mTitle.setText(userTask.getTitle());

		TimeBlock[] temp = userTask.getTimeBlocks();
		taskViewHolder.mScheduleDate.setText("Scheduled for: " + sdf.format(userTask.thisTimeBlock.getStartTime()).toString());

		taskViewHolder.mDuration.setText("Duration: " + userTask.getTimeRequiredInMinutes() + "mins");
		taskViewHolder.mPriority.setText("Priority: " + priority);
	}

	@Override
	public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View itemView = LayoutInflater.
						from(viewGroup.getContext()).
						inflate(R.layout.task_card, viewGroup, false);

		return new TaskViewHolder(itemView);
	}

	public static class TaskViewHolder extends RecyclerView.ViewHolder {
		protected TextView mDescription;
		protected TextView mCompleted;
		protected TextView mDueDate;
		protected TextView mTitle;
		protected TextView mScheduleDate;
		protected TextView mDuration;
		protected TextView mPriority;

		public TaskViewHolder(View v) {
			super(v);
			mDescription =  (TextView) v.findViewById(R.id.description);
			//mCompleted = (TextView)  v.findViewById(R.id.completed);
			mDueDate = (TextView)  v.findViewById(R.id.dueDate);
			mTitle = (TextView) v.findViewById(R.id.title);
			mScheduleDate = (TextView) v.findViewById(R.id.scheduleDate);
			mDuration = (TextView) v.findViewById(R.id.durationTextView);
			mPriority = (TextView) v.findViewById(R.id.priorityTextView);
		}
	}
}