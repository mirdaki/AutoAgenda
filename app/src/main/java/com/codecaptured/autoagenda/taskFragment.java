package com.codecaptured.autoagenda;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;

import com.codecaptured.autoagendacore.usecases.TaskInteractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	/** The edittext used to select the task date */
	private android.widget.EditText dateEditText;

	/** The edittext used to select the task time */
	private android.widget.EditText timeEditText;

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

	/** ADd Button */
	Button addButton;


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
		timeEditText = (android.widget.EditText) RootView.findViewById(com.codecaptured.autoagenda.R.id.timeEditText);

		dateEditText.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				new android.app.DatePickerDialog(getContext(), date, calendar
								.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH),
								calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
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
			}

		};

		timeEditText.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				java.util.Calendar mcurrentTime = java.util.Calendar.getInstance();
				int hour = mcurrentTime.get(java.util.Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(java.util.Calendar.MINUTE);
				android.app.TimePickerDialog mTimePicker;
				mTimePicker = new android.app.TimePickerDialog(getContext(), new android.app.TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
						timeEditText.setText( selectedHour + ":" + selectedMinute);
					}
				}, hour, minute, true);//Yes 24 hour time
				mTimePicker.setTitle("Select Time");
				mTimePicker.show();

			}
		});

		// Setup repeat spinner
		repeatSpinner = (Spinner) RootView.findViewById(R.id.repeatSpinner);
		ArrayAdapter<CharSequence> repeatAdapter = ArrayAdapter.createFromResource(RootView.getContext(), R.array.repeatSpinnerArray, android.R.layout.simple_spinner_item);
		repeatSpinner.setAdapter(repeatAdapter);

		// Setup priority spinner
		prioritySpinner = (Spinner) RootView.findViewById(R.id.prioritySpinner);
		ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(RootView.getContext(), R.array.prioritySpinnerArray, android.R.layout.simple_spinner_item);
		prioritySpinner.setAdapter(priorityAdapter);

		// Setup reminder spinner
		reminderSpinner = (Spinner) RootView.findViewById(R.id.reminderSpinner);
		ArrayAdapter<CharSequence> reminderAdapter = ArrayAdapter.createFromResource(RootView.getContext(), R.array.reminderSpinnerArray, android.R.layout.simple_spinner_item);
		reminderSpinner.setAdapter(reminderAdapter);

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
		String myFormat = "MM/dd/yy"; //In which you need put here
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, java.util.Locale.US);

		dateEditText.setText(sdf.format(calendar.getTime()));
	}

	public void addButtonClicked(View view){
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		Date tempDate = new Date();
//		try
//		{
//			tempDate = sdf.parse("21/12/2018");
//		}
//		catch (ParseException e)
//		{
//			e.printStackTrace();
//		}
//
//		String[] tempTags  = {"work"};
//
//		UserTask tempTask = new UserTask("Temp", "Hello", false, tempDate, 120, 3, tempTags);
//
//		TaskInteractor.addTask(tempTask);
//
//		System.out.println(tempTask);

		com.codecaptured.autoagenda.database.room.AppDatabase.getInstance(getContext());
	}

	public void cancelButtonClicked(View view){
		dismiss();
	}
}

