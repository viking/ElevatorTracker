package net.pillageandplunder.elevatortracker;

import android.provider.BaseColumns;

public final class DbContract {

	public DbContract() {	}

    public static abstract class ElevatorRides implements BaseColumns {
        public static final String TABLE_NAME = "rides";
        public static final String COLUMN_NAME_ELEVATOR_NUMBER = "elevator_number";
        public static final String COLUMN_NAME_CURRENT_FLOOR = "current_floor";
        public static final String COLUMN_NAME_TARGET_FLOOR = "target_floor";
        public static final String COLUMN_NAME_STOP_COUNT = "stop_count";
        public static final String COLUMN_NAME_STARTED_AT = "started_at";
        public static final String COLUMN_NAME_ENDED_AT = "ended_at";
        public static final String COLUMN_NAME_NULLABLE = "null";
        
        public static final String DATE_STARTED_AT = "date(" + COLUMN_NAME_STARTED_AT + ")";
        public static final String DATETIME_STARTED_AT = "datetime(" + COLUMN_NAME_STARTED_AT + ")";
        public static final String AS_STARTED_AT = " AS started_at";
        public static final String DATETIME_ENDED_AT = "datetime(" + COLUMN_NAME_ENDED_AT + ")";
        public static final String AS_ENDED_AT = " AS ended_at";
    }
}
