package it.thebertozz.android.housekeeping.models

import androidx.room.Embedded
import androidx.room.Relation

data class Inventory(
    @Embedded
    val container: Container,
    @Relation(
        parentColumn = "id",
        entityColumn = "container"
    )
    val items: List<Item>
)