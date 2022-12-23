package emiryanvl.simplereader.data.local.dao

import emiryanvl.simplereader.data.local.entity.Book
import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getBooks(): Maybe<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book): Completable

    @Delete
    fun deleteBook(user: Book): Completable

    @Update
    fun updateBook(user: Book): Completable
}