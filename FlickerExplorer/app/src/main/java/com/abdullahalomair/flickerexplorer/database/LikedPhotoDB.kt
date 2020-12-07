package com.abdullahalomair.flickerexplorer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class LikedPhotoDB (@PrimaryKey val id: UUID = UUID.randomUUID(),
    var photo_id : String = "",
    var title : String = "",
    var url: String = "",
    var latitude: String = "",
    var longitude:String = ""

)