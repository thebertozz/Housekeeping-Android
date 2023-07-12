package it.thebertozz.android.housekeeping.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/** Model per la gestione dei container e degli oggetti */

@Entity
data class Container(
    @PrimaryKey
    val id: String,
    var name: String,
    var description: String,
    var type: String
)
@Entity(
    foreignKeys = [ForeignKey(
        entity = Container::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("container"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Item(
    @PrimaryKey
    val itemId: String,
    var name: String,
    var type: String,
    @ColumnInfo(name = "expire_timestamp")
    val expireTimestamp: Long,
    val container: String
)
