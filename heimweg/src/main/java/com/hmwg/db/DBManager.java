package com.hmwg.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by eric on 16-2-27.
 */
public class DBManager {

    @Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, foreignKeysSupported = true)
    public class AppDatabase {
        public static final String NAME = "database";
        public static final int VERSION = 1;
    }

}
