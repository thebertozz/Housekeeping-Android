package it.thebertozz.android.housekeeping.managers

import android.content.Context
import androidx.room.Room
import it.thebertozz.android.housekeeping.database.HousekeepingDao
import it.thebertozz.android.housekeeping.database.HousekeepingDatabase

object DatabaseManager {

    private var db: HousekeepingDao? = null

    fun initializeDB(context: Context) {
        val database = Room.databaseBuilder(
            context,
            HousekeepingDatabase::class.java, "housekeeping-db"
        ).build()

        db = database.housekeepingDao()
    }

    fun getDB(): HousekeepingDao? {
        return db
    }
}