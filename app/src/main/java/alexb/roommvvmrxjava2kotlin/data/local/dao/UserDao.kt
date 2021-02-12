package alexb.roommvvmrxjava2kotlin.data.local.dao

import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): Maybe<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: User?): Completable

    @Delete
    fun deleteUser(user: User): Completable

    @Update
    fun updateUser(user: User?): Completable
}