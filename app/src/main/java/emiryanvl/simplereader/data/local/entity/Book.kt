package emiryanvl.simplereader.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
data class Book(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "path") val path: String
) : Serializable