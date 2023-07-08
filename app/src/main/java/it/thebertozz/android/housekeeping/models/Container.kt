package it.thebertozz.android.housekeeping.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Container(
    @PrimaryKey
    val id: String,
    var name: String?,
    var description: String?
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
    @ColumnInfo(name = "expire_timestamp")
    val expireTimestamp: Long,
    val container: String
)
