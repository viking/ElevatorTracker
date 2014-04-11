package net.pillageandplunder.elevatortracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.pillageandplunder.elevatortracker.DbContract.ElevatorRides;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.os.Build;

public class ElevatorRide extends ActionBarActivity {

	private int elevatorNumber;
	private int currentFloor;
	private int targetFloor;
	private int stopCount;
	private Date startedAt;
	private Date endedAt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elevator_ride);

		Intent intent = getIntent();
		elevatorNumber = intent.getIntExtra(MainActivity.ELEVATOR_NUMBER, 0);
		currentFloor = intent.getIntExtra(MainActivity.CURRENT_FLOOR, 0);
		targetFloor = intent.getIntExtra(MainActivity.TARGET_FLOOR, 0);
		stopCount = 0;
		startedAt = new Date();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elevator_ride, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void incrementStopCount(View view) {
		stopCount++;
	}

	public void done(View view) {
		endedAt = new Date();

		DbHelper dbHelper = new DbHelper(view.getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ElevatorRides.COLUMN_NAME_ELEVATOR_NUMBER, elevatorNumber);
		values.put(ElevatorRides.COLUMN_NAME_CURRENT_FLOOR, currentFloor);
		values.put(ElevatorRides.COLUMN_NAME_TARGET_FLOOR, targetFloor);
		values.put(ElevatorRides.COLUMN_NAME_STOP_COUNT, stopCount);
		values.put(ElevatorRides.COLUMN_NAME_STARTED_AT,
				dbHelper.formatDate(startedAt));
		values.put(ElevatorRides.COLUMN_NAME_ENDED_AT,
				dbHelper.formatDate(endedAt));

		db.insert(ElevatorRides.TABLE_NAME, ElevatorRides.COLUMN_NAME_NULLABLE,
				values);

		finish();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_elevator_ride,
					container, false);
			NumberPicker stopCountView = (NumberPicker) rootView
					.findViewById(R.id.stopCount);
			stopCountView.setMinValue(0);
			stopCountView.setMaxValue(MainActivity.MAX_FLOORS - 1);
			return rootView;
		}
	}

}
