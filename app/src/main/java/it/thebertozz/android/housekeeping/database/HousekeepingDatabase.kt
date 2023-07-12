package it.thebertozz.android.housekeeping.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.thebertozz.android.housekeeping.models.Container
import it.thebertozz.android.housekeeping.models.Item

/** Classe astratta che estende RoomDatabase */

@Database(entities = [Container::class, Item::class], version = 1)
abstract class HousekeepingDatabase : RoomDatabase() {
    abstract fun housekeepingDao(): HousekeepingDao
}