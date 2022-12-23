package emiryanvl.simplereader.data.repository

import emiryanvl.simplereader.data.local.dao.BookDao
import emiryanvl.simplereader.data.local.entity.Book
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

class BookRepository(private val bookDao: BookDao) {

    fun getBook(): Maybe<List<Book>> {
        return bookDao.getBooks()
    }

    fun insertBook(book: Book): Completable {
        return bookDao.insertBook(book)
    }

    fun updateBook(book: Book): Completable {
        return bookDao.updateBook(book)
    }

    fun deleteBook(book: Book): Completable {
        return bookDao.deleteBook(book)
    }
}