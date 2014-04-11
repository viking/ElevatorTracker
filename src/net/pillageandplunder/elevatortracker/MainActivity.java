package net.pillageandplunder.elevatortracker;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class MainActivity extends ActionBarActivity {

	public final static String ELEVATOR_NUMBER = "net.pillageandplunder.elevatortracker.ELEVATOR_NUMBER";
	public final static String CURRENT_FLOOR = "net.pillageandplunder.elevatortracker.CURRENT_FLOOR";
	public final static String TARGET_FLOOR = "net.pillageandplunder.elevatortracker.TARGET_FLOOR";

	static final int ELEVATOR_DATA_REQUEST = 1;

	static final int MAX_FLOORS = 12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_view_data) {
			viewData();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void viewData() {
		Intent intent = new Intent(this, ViewData.class);
		startActivity(intent);
	}

	public void selectElevator(View view) {
		String tag = (String) view.getTag();
		int elevatorNumber = Integer.parseInt(tag);

		NumberPicker currentFloorView = (NumberPicker) findViewById(R.id.CurrentFloor);
		int currentFloor = currentFloorView.getValue();

		NumberPicker targetFloorView = (NumberPicker) findViewById(R.id.TargetFloor);
		int targetFloor = targetFloorView.getValue();

		Intent intent = new Intent(this, ElevatorRide.class);
		intent.putExtra(ELEVATOR_NUMBER, elevatorNumber);
		intent.putExtra(CURRENT_FLOOR, currentFloor);
		intent.putExtra(TARGET_FLOOR, targetFloor);
		startActivityForResult(intent, ELEVATOR_DATA_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ELEVATOR_DATA_REQUEST) {
			if (resultCode == RESULT_OK) {
				// Swap current and target floor
				NumberPicker currentFloorView = (NumberPicker) findViewById(R.id.CurrentFloor);
				NumberPicker targetFloorView = (NumberPicker) findViewById(R.id.TargetFloor);
				int value = currentFloorView.getValue();
				currentFloorView.setValue(targetFloorView.getValue());
				targetFloorView.setValue(value);
			}
		}
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			NumberPicker currentFloorView = (NumberPicker) rootView
					.findViewById(R.id.CurrentFloor);
			currentFloorView.setMinValue(1);
			currentFloorView.setMaxValue(MAX_FLOORS);

			NumberPicker targetFloorView = (NumberPicker) rootView
					.findViewById(R.id.TargetFloor);
			targetFloorView.setMinValue(1);
			targetFloorView.setMaxValue(MAX_FLOORS);

			return rootView;
		}
	}

}
