package com.codecaptured.autoagenda;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends android.support.v4.app.FragmentActivity implements com.codecaptured.autoagenda.taskFragment.OnFragmentInteractionListener,
				com.codecaptured.autoagenda.AgendaFragment.OnFragmentInteractionListener,
				com.codecaptured.autoagenda.CalendarFragment.OnFragmentInteractionListener
{

		/** Number of pages in the view pager */
		static final int NUM_ITEMS = 3;

		/** Adapter for view pager */
		HomePageAdapter mAdapter;

		/** View Pager object */
		android.support.v4.view.ViewPager mPager;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
	                setContentView(com.codecaptured.autoagenda.R.layout.fragment_task);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up view pager
	      mAdapter = new HomePageAdapter(getSupportFragmentManager());
	      mPager = (android.support.v4.view.ViewPager)findViewById(R.id.pager);
	      mPager.setAdapter(mAdapter);

//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		    // Watch for button clicks to pages.
		    android.widget.Button button = (android.widget.Button)findViewById(R.id.goto_first);
		    button.setOnClickListener(new android.view.View.OnClickListener() {
			    @Override
			    public void onClick(android.view.View v) {
				    mPager.setCurrentItem(0);
			    }
		    });
	    button = (android.widget.Button)findViewById(com.codecaptured.autoagenda.R.id.goto_second);
	    button.setOnClickListener(new android.view.View.OnClickListener() {
		    @Override
		    public void onClick(android.view.View v) {
			    mPager.setCurrentItem(1);
		    }
	    });
		    button = (android.widget.Button)findViewById(com.codecaptured.autoagenda.R.id.goto_third);
		    button.setOnClickListener(new android.view.View.OnClickListener() {
			    @Override
			    public void onClick(android.view.View v) {
				    mPager.setCurrentItem(2);
			    }
		    });

//	    // Locate the button in activity_main.xml
//	    android.widget.Button button = (android.widget.Button) findViewById(com.codecaptured.autoagenda.R.id.tempButton);
//
//	    // Capture button clicks
//	    button.setOnClickListener(new android.view.View.OnClickListener() {
//		    public void onClick(android.view.View arg0) {
//
//			    // Start NewActivity.class
//			    android.content.Intent myIntent = new android.content.Intent(MainActivity.this,
//							    com.codecaptured.autoagenda.taskActivity.class);
//			    startActivity(myIntent);
//		    }
//	    });
    }

	public static class HomePageAdapter extends android.support.v4.app.FragmentPagerAdapter
	{
		public HomePageAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			switch (position) {
				case 0: // Fragment # 0 - This will show taskFragment
					return com.codecaptured.autoagenda.taskFragment.newInstance("0", "Page # 1");
				case 1: // Fragment # 1 - This will show
					return com.codecaptured.autoagenda.AgendaFragment.newInstance("1", "Page # 2");
				case 2: // Fragment # 2 - This will show
					return com.codecaptured.autoagenda.CalendarFragment.newInstance("2", "Page # 3");
				default:
					return null;
			}
		}

		// Returns the page title for the top indicator
		@Override
		public CharSequence getPageTitle(int position) {
			return "Page " + position;
		}
	}

	public static class ArrayListFragment extends android.support.v4.app.ListFragment
	{
		int mNum;

		/**
		 * Create a new instance of CountingFragment, providing "num"
		 * as an argument.
		 */
		static ArrayListFragment newInstance(int num) {
			ArrayListFragment f = new ArrayListFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);

			return f;
		}

		/**
		 * When creating, retrieve this instance's number from its arguments.
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		}

		/**
		 * The Fragment's UI is just a simple text view showing its
		 * instance number.
		 */
		@Override
		public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
		                                      Bundle savedInstanceState) {
			android.view.View v = inflater.inflate(R.layout.home_fragment_pager_list, container, false);
			android.view.View tv = v.findViewById(R.id.text);
			((TextView)tv).setText("Fragment #" + mNum);
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onListItemClick(android.widget.ListView l, android.view.View v, int position, long id) {
			android.util.Log.i("FragmentList", "Item clicked: " + id);
		}
	}

	@Override
	public void onFragmentInteraction(android.net.Uri uri){
		//you can leave it empty
	}

}
