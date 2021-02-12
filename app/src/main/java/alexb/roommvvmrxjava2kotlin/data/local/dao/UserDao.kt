package alexb.roommvvmrxjava2kotlin.data.local.dao

import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): Maybe<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: User): Completable

    @Delete
    fun deleteUser(user: User): Completable

    @Update
    fun updateUser(user: User): Completable
}