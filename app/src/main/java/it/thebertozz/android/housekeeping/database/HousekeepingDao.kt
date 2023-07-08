package it.thebertozz.android.housekeeping.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import it.thebertozz.android.housekeeping.models.Container
import it.thebertozz.android.housekeeping.models.Inventory
import it.thebertozz.android.housekeeping.models.Item

@Dao
interface HousekeepingDao {
    @Insert
    suspend fun save(container: Container)

    @Insert
    suspend fun save(vararg item: Item)

    @Transaction
    @Query("SELECT * FROM container")
    suspend fun getAll(): List<Inventory>

    @Transaction
    @Query("SELECT * FROM container WHERE id = :id")
    suspend fun getByContainerId(id: String): Inventory
}