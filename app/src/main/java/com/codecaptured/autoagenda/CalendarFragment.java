package com.codecaptured.autoagenda;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements com.prolificinteractive.materialcalendarview.OnDateSelectedListener, com.prolificinteractive.materialcalendarview.OnMonthChangedListener
{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	View RootView;


	private static final java.text.DateFormat FORMATTER = java.text.SimpleDateFormat.getDateInstance();
	private com.prolificinteractive.materialcalendarview.MaterialCalendarView mCalendarView;
	private android.widget.TextView tempTextView;

	private OnFragmentInteractionListener mListener;

	public CalendarFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment CalendarFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static CalendarFragment newInstance(String param1, String param2)
	{
		CalendarFragment fragment = new CalendarFragment();
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

		RootView = inflater.inflate(com.codecaptured.autoagenda.R.layout.fragment_calendar, container, false);
		tempTextView = (android.widget.TextView) RootView.findViewById(com.codecaptured.autoagenda.R.id.calTextView);
		mCalendarView = (com.prolificinteractive.materialcalendarview.MaterialCalendarView) RootView.findViewById(R.id.calendarView);
		mCalendarView.setOnDateChangedListener(this);




		// Inflate the layout for this fragment
		return RootView;
	}

	@Override
	public void onDateSelected(@android.support.annotation.NonNull com.prolificinteractive.materialcalendarview.MaterialCalendarView widget, @android.support.annotation.Nullable com.prolificinteractive.materialcalendarview.CalendarDay date, boolean selected) {
		tempTextView.setText(getSelectedDatesString());
	}

	@Override
	public void onMonthChanged(com.prolificinteractive.materialcalendarview.MaterialCalendarView widget, com.prolificinteractive.materialcalendarview.CalendarDay date) {
		//noinspection ConstantConditions
		//getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
	}

	private String getSelectedDatesString() {
		com.prolificinteractive.materialcalendarview.CalendarDay date = mCalendarView.getSelectedDate();
		if (date == null) {
			return "No Selection";
		}
		return FORMATTER.format(date.getDate());
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
}
