package net.pillageandplunder.elevatortracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.pillageandplunder.elevatortracker.DbContract.ElevatorRides;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "ElevatorTracker.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ", ";

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ ElevatorRides.TABLE_NAME + " (" + ElevatorRides._ID
			+ INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP
			+ ElevatorRides.COLUMN_NAME_ELEVATOR_NUMBER + INTEGER_TYPE
			+ COMMA_SEP + ElevatorRides.COLUMN_NAME_CURRENT_FLOOR
			+ INTEGER_TYPE + COMMA_SEP + ElevatorRides.COLUMN_NAME_TARGET_FLOOR
			+ INTEGER_TYPE + COMMA_SEP + ElevatorRides.COLUMN_NAME_STOP_COUNT
			+ INTEGER_TYPE + COMMA_SEP + ElevatorRides.COLUMN_NAME_STARTED_AT
			+ TEXT_TYPE + COMMA_SEP + ElevatorRides.COLUMN_NAME_ENDED_AT
			+ TEXT_TYPE + " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ ElevatorRides.TABLE_NAME;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public String formatDate(Date d) {
		return dateFormat.format(d);
	}
}
