package com.codecaptured.autoagenda;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;

import com.codecaptured.autoagendacore.entities.TimeBlock;
import com.codecaptured.autoagendacore.usecases.TaskInteractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link taskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link taskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class taskFragment extends DialogFragment
{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	/** The rootview from Main */
	View RootView;

	/** Log tag. */
	private static final String LOG_TAG = taskFragment.class.getSimpleName();

	/** The code to open date picker dialog */
	private static final int DATEINIT_DIALOG = 999;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	public static android.widget.TextView testTextView;

	/** The edittext used to select the task name */
	private android.widget.EditText taskEditText;

	/** The edittext used to select the description name */
	private android.widget.EditText descriptionEditText;

	/** The edittext used to select the task date */
	private android.widget.EditText dateEditText;
	Date selectedDate;
	String selectedTime;

	/** The edittext used to select the task time */
	private android.widget.EditText timeEditText;

	/** The edittext used to select the task required time */
	private android.widget.EditText timeRequiredEditText;

	/** Calendar instance */
	java.util.Calendar calendar = java.util.Calendar.getInstance();

	/** Repeat spinner */
	Spinner repeatSpinner;

	/** Priority spinner */
	Spinner prioritySpinner;

	/** Reminder spinner */
	Spinner reminderSpinner;

	/** Cancel Button */
	Button cancelButton;

	/** Add Button */
	Button addButton;

	/** Radio */
	RadioGroup mRadioGroup;
	RadioButton mRadioTask, mRadioEvent;

	/** Date picker dialog */
	android.app.DatePickerDialog.OnDateSetListener date;

	public taskFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment taskFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static taskFragment newInstance(String param1, String param2)
	{
		taskFragment fragment = new taskFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		RootView = inflater.inflate(com.codecaptured.autoagenda.R.layout.fragment_task, container, false);
		//		testTextView = (android.widget.TextView) RootView.findViewById(com.codecaptured.autoagenda.R.id.textView);
		//		testTextView.setText(mParam2);
		dateEditText = (android.widget.EditText) RootView.findViewById(com.codecaptured.autoagenda.R.id.dateEditText);
		//timeEditText = (android.widget.EditText) RootView.findViewById(com.codecaptured.autoagenda.R.id.timeEditText);

		taskEditText = (android.widget.EditText) RootView.findViewById(R.id.taskEditText);
		timeRequiredEditText = (android.widget.EditText) RootView.findViewById(R.id.timeRequiredEditText);
		descriptionEditText = (android.widget.EditText) RootView.findViewById(R.id.descriptionEditText);

		dateEditText.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				new android.app.DatePickerDialog(getContext(), date, calendar
								.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH),
								calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();

				java.util.Calendar mcurrentTime = java.util.Calendar.getInstance();
				int hour = mcurrentTime.get(java.util.Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(java.util.Calendar.MINUTE);
				android.app.TimePickerDialog mTimePicker;
				mTimePicker = new android.app.TimePickerDialog(getContext(), new android.app.TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
						calendar.set(Calendar.HOUR, selectedHour);
						calendar.set(Calendar.MINUTE, selectedMinute);
					}
				}, hour, minute, false);//Yes 24 hour time
				mTimePicker.show();
			}
		});

		date = new android.app.DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
			                      int dayOfMonth) {
				// TODO Auto-generated method stub
				calendar.set(java.util.Calendar.YEAR, year);
				calendar.set(java.util.Calendar.MONTH, monthOfYear);
				calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
				updateDate();
				selectedDate = calendar.getTime();
			}

		};

//		// Setup repeat spinner
//		repeatSpinner = (Spinner) RootView.findViewById(R.id.repeatSpinner);
//		ArrayAdapter<CharSequence> repeatAdapter = ArrayAdapter.createFromResource(RootView.getContext(), R.array.repeatSpinnerArray, android.R.layout.simple_spinner_item);
//		repeatSpinner.setAdapter(repeatAdapter);

		// Setup priority spinner
		prioritySpinner = (Spinner) RootView.findViewById(R.id.prioritySpinner);
		ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(RootView.getContext(), R.array.prioritySpinnerArray, android.R.layout.simple_spinner_item);
		prioritySpinner.setAdapter(priorityAdapter);

//		// Setup reminder spinner
//		reminderSpinner = (Spinner) RootView.findViewById(R.id.reminderSpinner);
//		ArrayAdapter<CharSequence> reminderAdapter = ArrayAdapter.createFromResource(RootView.getContext(), R.array.reminderSpinnerArray, android.R.layout.simple_spinner_item);
//		reminderSpinner.setAdapter(reminderAdapter);

		// Setup cancel button
		cancelButton = (Button) RootView.findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				cancelButtonClicked(view);
			}
		});

		addButton = (Button) RootView.findViewById(R.id.addUpdateButton);
		addButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				addButtonClicked(view);
			}
		});

		// Set up radio buttons
		mRadioGroup = (RadioGroup) RootView.findViewById(R.id.radio_buttons);
		mRadioTask = (RadioButton) RootView.findViewById(R.id.radio_task);
		mRadioEvent = (RadioButton) RootView.findViewById(R.id.radio_event);
		mRadioGroup.check(R.id.radio_task);

		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.radio_task:
						dateEditText.setHint("Due Date");
						break;
					case R.id.radio_event:
						dateEditText.setHint("Scheduled Date");
						break;
				}
			}

		});

		return RootView;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri)
	{
		if (mListener != null)
		{
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener)
		{
			mListener = (OnFragmentInteractionListener) context;
		} else
		{
			throw new RuntimeException(context.toString()
							+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener
	{
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}

	public void setDate(View view) {
		new android.app.DatePickerDialog(getContext(), date, calendar
						.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH),
						calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
	}

	private void updateDate() {
		String myFormat = "MM/dd/yy hh:mm aa";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, java.util.Locale.US);

		dateEditText.setText(sdf.format(calendar.getTime()));
	}

	public void addButtonClicked(View view){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		String[] tempTags  = {"work"};
		String[] tempTags2  = {"school", "gym"};

		if(postIsProper() == true && dateIsProper(selectedDate) == true)
		{
			UserTask tempTask1 = new UserTask(taskEditText.getText().toString(), "" + descriptionEditText.getText().toString(), false, selectedDate, Integer.parseInt(timeRequiredEditText.getText().toString()), prioritySpinner.getSelectedItemPosition() + 1, tempTags2);
			TaskInteractor.addTask(tempTask1);
			if(mRadioTask.isChecked())
				tempTask1.isEvent = false;
			else
				tempTask1.isEvent = true;
			for(int i = 0; i < tempTask1.timeBlocks.length; i++){
				UserTask temp = tempTask1;
				temp.thisTimeBlock = temp.timeBlocks[i];
				ListFragment.finalTaskList.add(temp);
			}
			//ListFragment.finalTaskList.add(tempTask1);
			ListFragment.reloadRecyclerView();
			dismiss();
		}


		else{
			AlertDialog.Builder builder;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				builder = new AlertDialog.Builder(RootView.getContext(), android.R.style.Theme_Material_Dialog_Alert);
			} else {
				builder = new AlertDialog.Builder(RootView.getContext());
			}
			builder.setTitle("Oops!")
							.setMessage("All required fields must be filled. Selected time cannot be in the past.")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {

								}
							})
							.show();
		}


		// TODO: Create a notification (just use the Task ID for nwo)

	}

	public void cancelButtonClicked(View view){
		dismiss();
	}

	public boolean postIsProper(){
		boolean isProper = true;
		if (taskEditText.getText().toString().equals("") || timeRequiredEditText.getText().toString().equals("") || dateEditText.getText().toString().equals("") ||
						taskEditText.getText().toString().equals(null) || timeRequiredEditText.getText().toString().equals(null) || dateEditText.getText().toString().equals(null))
		{
			isProper = false;
		}

		return isProper;
	}

	public boolean dateIsProper(Date date){
		boolean isProper = true;

		Date today = Calendar.getInstance().getTime();

		if(date.before(today)){
			isProper = false;
		}


		return isProper;

	}
}



