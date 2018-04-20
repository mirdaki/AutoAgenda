package com.codecaptured.autoagenda;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codecaptured.autoagendacore.entities.TimeBlock;
import com.codecaptured.autoagendacore.usecases.TaskInteractor;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.TaskViewHolder> {



	public List<UserTask> taskList;
	UserTask userTask;
	int currPosition;

	public static View ListFragmentAdapterView;

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
		String myFormat = "E, MMM dd 'at' hh:mm aa";
		String myFormat2 = "MM/dd/yy";
		String myFormat3 = "hh:mm aa";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, java.util.Locale.US);
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat(myFormat2, java.util.Locale.US);
		java.text.SimpleDateFormat sdf3 = new java.text.SimpleDateFormat(myFormat3, java.util.Locale.US);

		userTask = taskList.get(i);
		currPosition = i;

		if(userTask.priorityLevel == 1)
			priority = "Low";
		else if(userTask.priorityLevel == 2)
			priority = "Medium";
		else
			priority = "High";

		taskViewHolder.mDescription.setText(userTask.getDescription());

		// Set due date
		if(sdf2.format(userTask.getDueDate()).toString().equals(sdf2.format(Calendar.getInstance().getTime()).toString()))
			taskViewHolder.mDueDate.setText("(Due: Today at " + sdf3.format(userTask.getDueDate()).toString() + ")");
		else
			taskViewHolder.mDueDate.setText("(Due: " + sdf.format(userTask.getDueDate()).toString() + ")");

		taskViewHolder.mTitle.setText(userTask.getTitle());

		TimeBlock[] temp = userTask.getTimeBlocks();

		// Set scheduled date
		if(sdf2.format(userTask.thisTimeBlock.getStartTime()).toString().equals(sdf2.format(Calendar.getInstance().getTime()).toString()))
			taskViewHolder.mScheduleDate.setText("Today at " + sdf3.format(userTask.thisTimeBlock.getStartTime()).toString());
		else
			taskViewHolder.mScheduleDate.setText(sdf.format(userTask.thisTimeBlock.getStartTime()).toString());


		taskViewHolder.mDuration.setText("Duration: " + userTask.getTimeRequiredInMinutes() + "mins");
		taskViewHolder.mPriority.setText("Priority: " + priority);

		taskViewHolder.mDeleteButton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				deleteButtonClicked();
			}
		});
		taskViewHolder.mCompleteButton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				completeButtonClicked();
			}
		});

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
		protected Button mDeleteButton;
		protected Button mCompleteButton;

		public TaskViewHolder(View v) {
			super(v);

			mDescription =  (TextView) v.findViewById(R.id.description);
			//mCompleted = (TextView)  v.findViewById(R.id.completed);
			mDueDate = (TextView)  v.findViewById(R.id.dueDate);
			mTitle = (TextView) v.findViewById(R.id.title);
			mScheduleDate = (TextView) v.findViewById(R.id.scheduleDate);
			mDuration = (TextView) v.findViewById(R.id.durationTextView);
			mPriority = (TextView) v.findViewById(R.id.priorityTextView);
			mDeleteButton = (Button) v.findViewById(R.id.deleteButton);
			mCompleteButton = (Button) v.findViewById(R.id.completeButton);
		}
	}

	public void deleteButtonClicked(){
		final AlertDialog.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder = new AlertDialog.Builder(ListFragment.RootView.getContext(), android.R.style.Theme_Material_Dialog_Alert);
		} else {
			builder = new AlertDialog.Builder(ListFragment.RootView.getContext());
		}
		builder.setTitle("Delete")
						.setMessage("Are you sure?")
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								//TaskInteractor.removeTask(userTask);
								taskList.remove(currPosition);
								ListFragment.reloadRecyclerView();
							}
						})
						.setNegativeButton("NO", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

							}
						})
						.show();
	}

	public void completeButtonClicked(){
		final AlertDialog.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder = new AlertDialog.Builder(ListFragment.RootView.getContext(), android.R.style.Theme_Material_Dialog_Alert);
		} else {
			builder = new AlertDialog.Builder(ListFragment.RootView.getContext());
		}
		builder.setTitle("Delete")
						.setMessage("Are you sure?")
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								//TaskInteractor.removeTask(userTask);
								taskList.remove(currPosition);
								ListFragment.reloadRecyclerView();
							}
						})
						.setNegativeButton("NO", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

							}
						})
						.show();
	}
}