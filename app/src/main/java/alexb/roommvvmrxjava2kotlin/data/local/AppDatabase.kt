package alexb.roommvvmrxjava2kotlin.data.local

import alexb.roommvvmrxjava2kotlin.data.local.dao.UserDao
import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .createFromAsset("database/user.db")
                        .build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}