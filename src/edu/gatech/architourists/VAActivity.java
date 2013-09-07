/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.gatech.architourists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import edu.gatech.architourists.database.ArchitectureDataSource;

public class VAActivity extends FragmentActivity implements
		ActionBar.TabListener {
	String[] tabTitles = { "All", "Architect", "Style", "Period" };
	static String[] period;
	static List<String> allArchNames;
	static Map<String, List<String>> architects, stylesite, periodsite;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the three primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_va);
		
		ArchitectureDataSource ds = new ArchitectureDataSource(this);
		ds.open();
		allArchNames =  ds.getSiteList();		
		architects = ds.getArchitectList();
		stylesite = ds.getStyleList();
		periodsite = ds.getPeriodList();
		ds.close();
		
		architects = makeAlphabetical(architects);
		stylesite = makeAlphabetical(stylesite);
		periodsite = makeAlphabetical(periodsite);

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// // Specify that the Home/Up button should not be enabled, since there
		// is no hierarchical
		// // parent.
		// actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(tabTitles[i])
					.setTabListener(this));
		}
		
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				AllArchFragment allArchFrag = new AllArchFragment();
				return allArchFrag;

			case 1:
				Fragment fragment1 = new ArchitectFragment();
				// Bundle args = new Bundle();
				// args.putInt(ArchitectureFragment.ARG_SECTION_NUMBER, i + 1);
				// fragment.setArguments(args);
				return fragment1;

			case 2:
				Fragment fragment2 = new StyleFragment();
				// Bundle args = new Bundle();
				// args.putInt(ArchitectureFragment.ARG_SECTION_NUMBER, i + 1);
				// fragment.setArguments(args);
				return fragment2;
			case 3:
				Fragment fragment3 = new PeriodFragment();
				// Bundle args = new Bundle();
				// args.putInt(ArchitectureFragment.ARG_SECTION_NUMBER, i + 1);
				// fragment.setArguments(args);
				return fragment3;
			default:
				// The other sections of the app are dummy placeholders.
				return null;

			}
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "Section " + (position + 1);
		}

	}

	public Map<String, List<String>> makeAlphabetical(Map<String, List<String>> map){
		Object[] mapArray = map.keySet().toArray();
		Arrays.sort(mapArray);
		Map<String, List<String>> newmap = new LinkedHashMap<String, List<String>>();
		for(Object s : mapArray){
			newmap.put((String) s, map.get(s));
		}
		return newmap;
	}
	 
	
	public static class ArchitectFragment extends Fragment {
		// public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_architecture,
					container, false);

			// Set up our adapter
			ExpandableListView listView = (ExpandableListView) rootView
					.findViewById(R.id.expandable_list);
			ExpandableListAdapter mAdapter = new MyExpandableListAdapter();
			listView.setAdapter(mAdapter);
			listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent,
						View view, int groupPosition, int childPosition, long id) {
					Intent intent = new Intent(parent.getContext(),
							ArchInfoActivity.class);

					//String name = textDeterminer(groupPosition, childPosition);
					//String name = architectsite[groupPosition][childPosition];
					String something = (String) (architects.keySet().toArray())[groupPosition];
					String name = (String) (architects.get(something).toArray())[childPosition];
					intent.putExtra(ReviewActivity.SITE_NAME, name);
					startActivity(intent);
					return true;
				}
			});
			registerForContextMenu(listView);
			return rootView;
		}

		/**
		 * A simple adapter which maintains an ArrayList of photo resource Ids.
		 * Each photo is displayed as an image. This adapter supports clearing
		 * the list of photos and adding a new photo.
		 * 
		 */
		public class MyExpandableListAdapter extends BaseExpandableListAdapter {
			// Sample data set. children[i] contains the children (String[]) for
			// groups[i].
			

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				//return textDeterminer(groupPosition, childPosition);
				//return architectsite[groupPosition][childPosition];
				String architectname = (String) (architects.keySet().toArray())[groupPosition];
				String childname = (String) (architects.get(architectname).toArray())[childPosition];
				return childname;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				//return architectsite[groupPosition].length;
				return architects.get(architects.keySet().toArray()[groupPosition]).size();
			}

			public TextView getGenericView() {
				// Layout parameters for the ExpandableListView
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);

				TextView textView = new TextView(
						ArchitectFragment.this.getActivity());
				textView.setLayoutParams(lp);
				textView.setTypeface(null, Typeface.BOLD);
				
				
				// Center the text vertically
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				// Set the text starting position
				textView.setPadding(36, 0, 0, 0);
				return textView;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				TextView textView = getGenericView();

				textView.setText(getChild(groupPosition, childPosition)
						.toString());
				textView.setTypeface(null, Typeface.NORMAL);
				return textView;
			}

			@Override
			public Object getGroup(int groupPosition) {
				return(architects.keySet().toArray())[groupPosition];
				//return architects.get(position)	;
			}

			@Override
			public int getGroupCount() {
				return architects.size();
				//return architects.length;
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				TextView textView = getGenericView();
				textView.setText(getGroup(groupPosition).toString());
				return textView;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}
		}

		/*
		public String textDeterminer(int groupPosition, int childPosition) {
			String site = "";
			if (groupPosition == 0) {
				if (childPosition == 0)
					site = "Sagrada Familia";
				if (childPosition == 1)
					site = "Parc Guell";
				if (childPosition == 2)
					site = "Casa Mila";
			} else if (groupPosition == 1) {
				if (childPosition == 0)
					site = "MACBA";
			}
			return site;
		}
		*/
	}

	/**
	 * A fragment that shows all architectures in a list
	 * 
	 * @author Leshi
	 * 
	 */
	public static class AllArchFragment extends Fragment {
		ListView listView;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_allarchitecture,
					container, false);

			// Set up our adapter
			ListView listView = (ListView) rootView
					.findViewById(R.id.archlistview);
			ArchListAdapter mAdapter = new ArchListAdapter(getActivity(),
					android.R.layout.simple_list_item_1, allArchNames);
			listView.setAdapter(mAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(parent.getContext(),
							ArchInfoActivity.class);

					String name = allArchNames.get(position);

					intent.putExtra(ReviewActivity.SITE_NAME, name);
					startActivity(intent);
				}
			});
			registerForContextMenu(listView);
			return rootView;
		}

		// private array adapter for AllArchFragment
		private class ArchListAdapter extends ArrayAdapter<String> {
			HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

			public ArchListAdapter(Context context, int textViewResourceId,
					List<String> objects) {
				super(context, textViewResourceId, objects);
				for (int i = 0; i < objects.size(); ++i) {
					mIdMap.put(objects.get(i), i);
				}
			}

			@Override
			public long getItemId(int position) {
				String item = getItem(position);
				return mIdMap.get(item);
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class StyleFragment extends Fragment {
		// public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_stylearchitecture,
					container, false);

			// Set up our adapter
			ExpandableListView listView = (ExpandableListView) rootView
					.findViewById(R.id.expandableListView1);
			ExpandableListAdapter mAdapter = new StyleListAdapter();
			listView.setAdapter(mAdapter);
			listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent,
						View view, int groupPosition, int childPosition, long id) {
					Intent intent = new Intent(parent.getContext(),
							ArchInfoActivity.class);

					//String name = textDeterminer(groupPosition, childPosition);
					//String name = stylesite[groupPosition][childPosition];
					
					String something = (String) (stylesite.keySet().toArray())[groupPosition];
					String name = (String) (stylesite.get(something).toArray())[childPosition];
					
					intent.putExtra(ReviewActivity.SITE_NAME, name);
					startActivity(intent);
					return true;
				}
			});
			registerForContextMenu(listView);
			return rootView;
		}

		/**
		 * A simple adapter which maintains an ArrayList of photo resource Ids.
		 * Each photo is displayed as an image. This adapter supports clearing
		 * the list of photos and adding a new photo.
		 * 
		 */
		public class StyleListAdapter extends BaseExpandableListAdapter {
			// Sample data set. children[i] contains the children (String[]) for
			// groups[i].
			

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				//return textDeterminer(groupPosition, childPosition);
				String architectname = (String) (stylesite.keySet().toArray())[groupPosition];
				String childname = (String) (stylesite.get(architectname).toArray())[childPosition];
				return childname;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return stylesite.get(stylesite.keySet().toArray()[groupPosition]).size();
			}

			public TextView getGenericView() {
				// Layout parameters for the ExpandableListView
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);

				TextView textView = new TextView(
						StyleFragment.this.getActivity());
				textView.setLayoutParams(lp);
				textView.setTypeface(null, Typeface.BOLD);

				// Center the text vertically
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				// Set the text starting position
				textView.setPadding(36, 0, 0, 0);
				return textView;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				TextView textView = getGenericView();

				textView.setText(getChild(groupPosition, childPosition)
						.toString());
				textView.setTypeface(null, Typeface.NORMAL);
				return textView;
			}

			@Override
			public Object getGroup(int groupPosition) {
				return(stylesite.keySet().toArray())[groupPosition];
			}

			@Override
			public int getGroupCount() {
				return stylesite.size();
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				TextView textView = getGenericView();
				textView.setText(getGroup(groupPosition).toString());
				return textView;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}
		}

		/*
		public String textDeterminer(int groupPosition, int childPosition) {
			String site = "";
			if (groupPosition == 0) {
				if (childPosition == 0)
					site = "Sagrada Familia";
				if (childPosition == 1)
					site = "Parc Guell";
				if (childPosition == 2)
					site = "Casa Mila";
			} else if (groupPosition == 1) {
				if (childPosition == 0)
					site = "MACBA";
			}
			return site;
		}
		*/
	}
		
	
	
	public static class PeriodFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_periodarchitecture,
					container, false);

			// Set up our adapter
			ExpandableListView listView = (ExpandableListView) rootView
					.findViewById(R.id.expandableListView1);
			ExpandableListAdapter mAdapter = new PeriodListAdapter();
			listView.setAdapter(mAdapter);
			listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent,
						View view, int groupPosition, int childPosition, long id) {
					Intent intent = new Intent(parent.getContext(),
							ArchInfoActivity.class);

					//String name = textDeterminer(groupPosition, childPosition);
					//String name = stylelist[groupPosition][childPosition];
					String something = (String) (periodsite.keySet().toArray())[groupPosition];
					String name = (String) (periodsite.get(something).toArray())[childPosition];

					
					intent.putExtra(ReviewActivity.SITE_NAME, name);
					startActivity(intent);
					return true;
				}
			});
			registerForContextMenu(listView);
			return rootView;
		}

		/**
		 * A simple adapter which maintains an ArrayList of photo resource Ids.
		 * Each photo is displayed as an image. This adapter supports clearing
		 * the list of photos and adding a new photo.
		 * 
		 */
		public class PeriodListAdapter extends BaseExpandableListAdapter {
			// Sample data set. children[i] contains the children (String[]) for
			// groups[i].
			

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				//return textDeterminer(groupPosition, childPosition);
				String architectname = (String) (periodsite.keySet().toArray())[groupPosition];
				String childname = (String) (periodsite.get(architectname).toArray())[childPosition];
				return childname;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return periodsite.get(periodsite.keySet().toArray()[groupPosition]).size();
			}

			public TextView getGenericView() {
				// Layout parameters for the ExpandableListView
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);

				TextView textView = new TextView(
						PeriodFragment.this.getActivity());
				textView.setLayoutParams(lp);
				textView.setTypeface(null, Typeface.BOLD);

				// Center the text vertically
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				// Set the text starting position
				textView.setPadding(36, 0, 0, 0);
				return textView;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				TextView textView = getGenericView();

				textView.setText(getChild(groupPosition, childPosition)
						.toString());
				textView.setTypeface(null, Typeface.NORMAL);

				return textView;
			}

			@Override
			public Object getGroup(int groupPosition) {
				return(periodsite.keySet().toArray())[groupPosition];
			}

			@Override
			public int getGroupCount() {
				return periodsite.size();
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				TextView textView = getGenericView();
				textView.setText(getGroup(groupPosition).toString());

				return textView;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}
		}
	}

}
