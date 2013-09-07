package edu.gatech.architourists;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import edu.gatech.architourists.database.GaudiTour;
import edu.gatech.architourists.database.GothicTour;
import edu.gatech.architourists.database.MontjuicTour;
import edu.gatech.architourists.database.PlacaEspanyaTour;

public class TourActivity extends Activity {
	TextView tourTitle;
	TextView tourTime;
	ListView tourList;
	ImageView tourImage, red_x2, red_x;
	Button tourInfo, btn_BackPopup, tourMap;
	PopupWindow popUp, popUpImage;
	TextView popupTitle, popupText, popupTitle2, popupText2;
	private boolean click = true;
	private boolean click2 = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour);
		Intent i = getIntent();
		tourTitle = (TextView) findViewById(R.id.text_tourtitle);
		tourTime = (TextView) findViewById(R.id.text_tourtime);
		tourList = (ListView) findViewById(R.id.listview_tour);
		tourInfo = (Button) findViewById(R.id.btn_tourinfo);
		tourMap = (Button) findViewById(R.id.btn_tourimage);

		tourTitle.setText(i.getStringExtra(PRActivity.TOUR_NAME));
		if (i.getStringExtra(PRActivity.TOUR_NAME).equals("Gaudi Tour")) {
			tourTime.setText("Walking: " + GaudiTour.getWalkingTime()
					+ "\nMetro: " + GaudiTour.getMetroTime());
			// tourImage.setImageResource(GaudiTour.getImage());
			tourList.setAdapter(new TourListAdapter(this, GaudiTour
					.getSiteList(), GaudiTour.getRouteInfo(), GaudiTour
					.getSiteTime()));
			tourList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> lv, View v,
						int position, long id) {
					((TourListAdapter) tourList.getAdapter()).toggle(position);

				}

			});
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Old City - Gothic Quarter Tour")) {
			tourTime.setText("Walking: " + GothicTour.getWalkingTime()
					+ "\nMetro: " + GothicTour.getMetroTime());
			tourList.setAdapter(new TourListAdapter(this, GothicTour
					.getSiteList(), GothicTour.getRouteInfo(), GothicTour
					.getSiteTime()));
			tourList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> lv, View v,
						int position, long id) {
					((TourListAdapter) tourList.getAdapter()).toggle(position);

				}

			});
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Montjuic Tour")) {
			tourTime.setText("Walking: " + MontjuicTour.getWalkingTime()
					+ "\nMetro: " + MontjuicTour.getMetroTime());
			tourList.setAdapter(new TourListAdapter(this, MontjuicTour
					.getSiteList(), MontjuicTour.getRouteInfo(), MontjuicTour
					.getSiteTime()));
			tourList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> lv, View v,
						int position, long id) {
					((TourListAdapter) tourList.getAdapter()).toggle(position);

				}

			});
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Plaça d'Espanya Tour")) {
			tourTime.setText("Walking: " + PlacaEspanyaTour.getWalkingTime()
					+ "\nMetro: " + PlacaEspanyaTour.getMetroTime());
			tourList.setAdapter(new TourListAdapter(this, PlacaEspanyaTour
					.getSiteList(), PlacaEspanyaTour.getRouteInfo(),
					PlacaEspanyaTour.getSiteTime()));
			tourList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> lv, View v,
						int position, long id) {
					((TourListAdapter) tourList.getAdapter()).toggle(position);

				}

			});
		}

		popUp = new PopupWindow(this);

		final RelativeLayout layout = new RelativeLayout(this);

		tourInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (click) {
					popUp.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
					popUp.update(0, 50,
							((RelativeLayout) v.getParent()).getWidth() - 100,
							((RelativeLayout) v.getParent()).getHeight() - 100);
					click = false;
				} else {
					popUp.dismiss();
					click = true;
				}
			}
		});
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layout.setPadding(20, 20, 20, 20);

		// layout.setOrientation(LinearLayout.VERTICAL);
		// layout.addView((View) R.id.popupbutton1, params);
		popupTitle = new TextView(this);
		popupText = new TextView(this);
		popupTitle.setText(i.getStringExtra(PRActivity.TOUR_NAME) + ":");
		popupTitle.setTextColor(Color.WHITE);
		popupTitle.setTextSize(25);
		if (i.getStringExtra(PRActivity.TOUR_NAME).equals("Gaudi Tour")) {
			popupText.setText(GaudiTour.getIntro());
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Old City - Gothic Quarter Tour")) {
			popupText.setText(GothicTour.getIntro());
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Montjuic Tour")) {
			popupText.setText(MontjuicTour.getIntro());
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Plaça d'Espanya Tour")) {
			popupText.setText(PlacaEspanyaTour.getIntro());
		}

		popupText.setTextColor(Color.WHITE);
		popupText.setMovementMethod(new ScrollingMovementMethod());

		red_x = new ImageView(this);
		red_x.setImageResource(R.drawable.red_x);

		red_x.setLayoutParams(params);
		red_x.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popUp.dismiss();
				click = true;
			}

		});
		LinearLayout llayout = new LinearLayout(this);
		llayout.setOrientation(LinearLayout.VERTICAL);
		ScrollView scroll = new ScrollView(this);
		llayout.addView(popupTitle);
		scroll.addView(popupText);
		llayout.addView(scroll);

		// LayoutParams param1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT);

		

		RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		param3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		param3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layout.addView(red_x, param3);
		
		RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		param2.addRule(RelativeLayout.LEFT_OF, red_x.getId());
		layout.addView(llayout, param2);

		popUp.setContentView(layout);

		// //////////////////////////////////////////////
		popUpImage = new PopupWindow(this);

		final RelativeLayout layout2 = new RelativeLayout(this);

		tourMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (click2) {
					popUpImage.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
					popUpImage.update(0, 50,
							((RelativeLayout) v.getParent()).getWidth() - 100,
							((RelativeLayout) v.getParent()).getHeight() - 100);
					click2 = false;
				} else {
					popUpImage.dismiss();
					click2 = true;
				}
			}
		});
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layout2.setPadding(20, 20, 20, 20);

		// layout.setOrientation(LinearLayout.VERTICAL);
		// layout.addView((View) R.id.popupbutton1, params);
		popupTitle2 = new TextView(this);
		popupText2 = new TextView(this);
		tourImage = new ImageView(this);
		
		popupTitle2.setText(i.getStringExtra(PRActivity.TOUR_NAME) + ":");
		popupTitle2.setTextColor(Color.WHITE);
		popupTitle2.setTextSize(25);
		if (i.getStringExtra(PRActivity.TOUR_NAME).equals("Gaudi Tour")) {
			popupText2.setText(GaudiTour.getIntro());
			tourImage.setImageResource(GaudiTour.getImage());
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Old City - Gothic Quarter Tour")) {
			popupText2.setText(GothicTour.getIntro());
			tourImage.setImageResource(GothicTour.getImage());
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Montjuic Tour")) {
			popupText2.setText(MontjuicTour.getIntro());
			tourImage.setImageResource(MontjuicTour.getImage());
		} else if (i.getStringExtra(PRActivity.TOUR_NAME).equals(
				"Plaça d'Espanya Tour")) {
			popupText2.setText(PlacaEspanyaTour.getIntro());
			tourImage.setImageResource(PlacaEspanyaTour.getImage());
		}

		popupText2.setTextColor(Color.WHITE);
		popupText2.setMovementMethod(new ScrollingMovementMethod());

		red_x2 = new ImageView(this);
		red_x2.setImageResource(R.drawable.red_x);
	//	btn_BackPopup.setBackgroundColor(Color.WHITE);

		red_x2.setLayoutParams(params);
		red_x2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popUpImage.dismiss();
				click2 = true;
			}

		});
		LinearLayout llayout2 = new LinearLayout(this);
		llayout2.setOrientation(LinearLayout.VERTICAL);
		ScrollView scroll2 = new ScrollView(this);
		llayout2.addView(popupTitle2);
		scroll2.addView(tourImage);
		llayout2.addView(scroll2);

		// LayoutParams param1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT);

		RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		param4.addRule(RelativeLayout.LEFT_OF, red_x2.getId());
		layout2.addView(llayout2, param4);

		RelativeLayout.LayoutParams param5 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		param5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layout2.addView(red_x2, param5);

		popUpImage.setContentView(layout2);

	}

	/**
	 * A sample ListAdapter that presents content from arrays of speeches and
	 * text.
	 * 
	 */
	private class TourListAdapter extends BaseAdapter {
		public TourListAdapter(Context context, ArrayList<String> sites,
				ArrayList<String> routeInfo, ArrayList<String> siteTime) {
			mContext = context;
			mSites = sites;
			mRouteInfo = routeInfo;
			mSiteTime = siteTime;
		}

		/**
		 * The number of items in the list is determined by the number of
		 * speeches in our array.
		 * 
		 * @see android.widget.ListAdapter#getCount()
		 */
		public int getCount() {
			return mSites.size();
		}

		/**
		 * Since the data comes from an array, just returning the index is
		 * sufficent to get at the data. If we were using a more complex data
		 * structure, we would return whatever object represents one row in the
		 * list.
		 * 
		 * @see android.widget.ListAdapter#getItem(int)
		 */
		public Object getItem(int position) {
			return position;
		}

		/**
		 * Use the array index as a unique id.
		 * 
		 * @see android.widget.ListAdapter#getItemId(int)
		 */
		public long getItemId(int position) {
			return position;
		}

		/**
		 * Make a SpeechView to hold each row.
		 * 
		 * @see android.widget.ListAdapter#getView(int, android.view.View,
		 *      android.view.ViewGroup)
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			SpeechView sv;
			if (convertView == null) {
				sv = new SpeechView(mContext, mSites.get(position) + " ("
						+ mSiteTime.get(position) + " mins)",
						mRouteInfo.get(position), mExpanded[position]);
			} else {
				sv = (SpeechView) convertView;
				sv.setTitle(mSites.get(position) + " ("
						+ mSiteTime.get(position) + " mins)");
				sv.setDialogue(mRouteInfo.get(position));
				sv.setExpanded(mExpanded[position]);
			}

			return sv;
		}

		public void toggle(int position) {
			mExpanded[position] = !mExpanded[position];
			notifyDataSetChanged();
		}

		/**
		 * Remember our context so we can use it when constructing views.
		 */
		private Context mContext;

		/**
		 * Our data, part 1.
		 */

		private ArrayList<String> mSites;

		/**
		 * Our data, part 2.
		 */
		private ArrayList<String> mRouteInfo;

		/**
		 * Our data, part 3.
		 */
		private boolean[] mExpanded = { true, false, false, false, false,
				false, false, false, false };

		private ArrayList<String> mSiteTime;
	}

	/**
	 * We will use a SpeechView to display each speech. It's just a LinearLayout
	 * with two text fields.
	 * 
	 */
	private class SpeechView extends LinearLayout {
		public SpeechView(Context context, String title, String dialogue,
				boolean expanded) {
			super(context);

			this.setOrientation(VERTICAL);

			// Here we build the child views in code. They could also have
			// been specified in an XML file.

			mTitle = new TextView(context);
			mTitle.setText(title);
			addView(mTitle, new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			mDialogue = new TextView(context);
			mDialogue.setText(dialogue);
			addView(mDialogue, new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}

		/**
		 * Convenience method to set the title of a SpeechView
		 */
		public void setTitle(String title) {
			mTitle.setText(title);
		}

		/**
		 * Convenience method to set the dialogue of a SpeechView
		 */
		public void setDialogue(String words) {
			mDialogue.setText(words);
		}

		/**
		 * Convenience method to expand or hide the dialogue
		 */
		public void setExpanded(boolean expanded) {
			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}

		private TextView mTitle;
		private TextView mDialogue;
	}
}
