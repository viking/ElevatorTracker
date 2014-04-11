package net.pillageandplunder.elevatortracker;

import net.pillageandplunder.elevatortracker.DbContract.ElevatorRides;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.os.Build;

public class ViewData extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_data);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_data, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_view_data,
					container, false);

			DbHelper dbHelper = new DbHelper(rootView.getContext());
			SQLiteDatabase db = dbHelper.getReadableDatabase();

			String[] projection = {
					ElevatorRides._ID,
					ElevatorRides.DATE_STARTED_AT
							+ ElevatorRides.AS_STARTED_AT,
					ElevatorRides.COLUMN_NAME_CURRENT_FLOOR,
					ElevatorRides.COLUMN_NAME_TARGET_FLOOR,
					ElevatorRides.COLUMN_NAME_STOP_COUNT };

			// How you want the results sorted in the resulting Cursor
			String sortOrder = ElevatorRides.DATETIME_STARTED_AT + " DESC";

			Cursor cursor = db.query(ElevatorRides.TABLE_NAME, // The table to
																// query
					projection, // The columns to return
					null, // The columns for the WHERE clause
					null, // The values for the WHERE clause
					null, // don't group the rows
					null, // don't filter by row groups
					sortOrder // The sort order
					);

			String[] fromColumns = { ElevatorRides.COLUMN_NAME_STARTED_AT,
					ElevatorRides.COLUMN_NAME_CURRENT_FLOOR,
					ElevatorRides.COLUMN_NAME_TARGET_FLOOR,
					ElevatorRides.COLUMN_NAME_STOP_COUNT };
			int[] toViews = { R.id.RideDate, R.id.RideCurrentFloor,
					R.id.RideTargetFloor, R.id.RideStopCount };

			SimpleCursorAdapter adapter = new SimpleCursorAdapter(
					rootView.getContext(), R.layout.elevator_ride, cursor,
					fromColumns, toViews, 0);
			ListView listView = (ListView) rootView.findViewById(R.id.RidesList);
			listView.setAdapter(adapter);

			return rootView;
		}
	}

}
