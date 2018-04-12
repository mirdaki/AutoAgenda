package com.codecaptured.autoagenda;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		UserTask userTask = taskList.get(i);
		taskViewHolder.mDescription.setText(userTask.getDescription());
		taskViewHolder.mCompleted.setText(userTask.getCompleted().toString());
		taskViewHolder.mDueDate.setText(userTask.getDueDate().toString());
		taskViewHolder.mTitle.setText(userTask.getTitle());
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

		public TaskViewHolder(View v) {
			super(v);
			mDescription =  (TextView) v.findViewById(R.id.description);
			mCompleted = (TextView)  v.findViewById(R.id.completed);
			mDueDate = (TextView)  v.findViewById(R.id.dueDate);
			mTitle = (TextView) v.findViewById(R.id.title);
		}
	}
}