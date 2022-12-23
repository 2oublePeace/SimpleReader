package emiryanvl.simplereader.ui.viewmodel

import emiryanvl.simplereader.data.local.AppDatabase
import emiryanvl.simplereader.data.local.entity.Book
import emiryanvl.simplereader.data.repository.BookRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class CacheBookViewModel(application: Application) : AndroidViewModel(application), Serializable {

    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<List<Book>>()
    var user: LiveData<List<Book>> = _user

    private var bookRepository: BookRepository =
        BookRepository(AppDatabase.getDatabase(application).userDao())

    fun getBooks() {
        bookRepository.getBook()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.isNullOrEmpty()) {
                    _user.postValue(it)
                } else {
                    _user.postValue(listOf())
                }
            }, {
            }).let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    fun updateBook(user: Book) {
        bookRepository.updateBook(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getBooks()
            }, {
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun saveBook(book: Book) {
        bookRepository.insertBook(book)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getBooks()
            }, {
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteUser(user: Book) {
        bookRepository.deleteBook(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getBooks()
            }, {
            }).let {
                compositeDisposable.add(it)
            }
    }
}