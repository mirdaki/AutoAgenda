package com.codecaptured.autoagenda;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codecaptured.autoagendacore.entities.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment
{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	View RootView;

	/** Spinner components */
	Spinner sortSpinner, tagSpinner;
	ArrayList<String> tagList = new ArrayList<String>();

	/** Recycler View components */
	RecyclerView mRecyclerView;
	public static ListFragmentAdapter mAdapter;
	RecyclerView.LayoutManager mLayoutManager;
	public static List<UserTask> finalTaskList, tagTaskList;

	int check = 0, check2 = 0;


	public ListFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ListFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ListFragment newInstance(String param1, String param2)
	{
		ListFragment fragment = new ListFragment();
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
		RootView = inflater.inflate(R.layout.fragment_list, container, false);

		// Task list
		finalTaskList = new ArrayList<UserTask>();
		tagTaskList = new ArrayList<UserTask>();

		// Establish recycler view
		mRecyclerView = (RecyclerView) RootView.findViewById(R.id.recycler_view);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(RootView.getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new ListFragmentAdapter(finalTaskList);
		mRecyclerView.setAdapter(mAdapter);

		// Setup sort spinner
		sortSpinner = (Spinner) RootView.findViewById(R.id.sortSpinner);
		ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(RootView.getContext(), R.array.sortSpinnerArray, android.R.layout.simple_spinner_item);
		sortSpinner.setAdapter(sortAdapter);

		sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				if(++check > 1)
				{
					if(position == 0)
						sortListByDate();
					else if(position == 1)
						sortListByPriority();
					else if(position == 2)
						sortByShortest();
				}
			} // to close the onItemSelected
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		// Setup tag spinner
		tagList.add("All tags");
		tagList.add("Gym");
		tagList.add("School");
		tagList.add("Work");
		tagSpinner = (Spinner) RootView.findViewById(R.id.tagSpinner);
		ArrayAdapter<String> tagAdapter =
						new ArrayAdapter<String>(RootView.getContext(), R.layout.support_simple_spinner_dropdown_item, tagList);
		tagSpinner.setAdapter(tagAdapter);
		tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				if(++check2 > 1)
				{
					sortListByTag(tagSpinner.getSelectedItem().toString());
				}
			} // to close the onItemSelected
			public void onNothingSelected(AdapterView<?> parent)
			{

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

	private List<UserTask> createList(int size) {


		String[] temp = {"School"};
		for (int i=1; i <= size; i++) {
			UserTask userTask = new UserTask("title " + i, "description " + i, true, Calendar.getInstance().getTime(), 5, 2, temp );
			finalTaskList.add(userTask);

		}

		return finalTaskList;
	}

	public void sortListByDate(){
		Collections.sort(finalTaskList, new Comparator<UserTask>() {
			public int compare(UserTask u1, UserTask u2) {
				if (u1.getDueDate() == null || u2.getDueDate() == null)
					return 0;
				return u1.getDueDate().compareTo(u2.getDueDate());
			}
		});
		reloadRecyclerView();
	}

	public void sortListByPriority()
	{
		Collections.sort(finalTaskList, new Comparator<UserTask>()
		{
			public int compare(UserTask u1, UserTask u2)
			{
				return u2.priorityLevel - u1.priorityLevel;
			}

		});
		reloadRecyclerView();
	}

	public void sortByShortest(){
		Collections.sort(finalTaskList, new Comparator<UserTask>()
		{
			public int compare(UserTask u1, UserTask u2)
			{
				return u1.timeRequiredInMinutes - u2.timeRequiredInMinutes;
			}

		});
		reloadRecyclerView();
	}

	public void sortListByTag(String tagString){

		finalTaskList = tagTaskList;


		if(tagString.equals("All tags"))
		{
			return;
		}

//		for(int i = 0; i < finalTaskList.size(); i++){
//			if(!finalTaskList.get(i).tags.contains(tagString.toLowerCase()));
//			finalTaskList.remove(i);
//		}

		reloadRecyclerView();
	}

	public void reloadRecyclerView()
	{

		mAdapter.notifyDataSetChanged();
		CalendarFragment fragment = (CalendarFragment)
						getFragmentManager().findFragmentById(R.id.pager);

		getFragmentManager().beginTransaction()
						.detach(fragment)
						.attach(fragment)
						.commit();
	}
}
