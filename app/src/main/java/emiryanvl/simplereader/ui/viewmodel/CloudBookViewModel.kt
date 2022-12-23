package emiryanvl.simplereader.ui.viewmodel

import emiryanvl.simplereader.data.local.entity.Book
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CloudBookViewModel (application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    private val _cloudBooks = MutableLiveData<List<Book>>()
    var cloudBooks: LiveData<List<Book>> = _cloudBooks

    fun getCloudBooks() {
        val tempCloudBooks = mutableListOf<Book>()
        var database = Firebase.database.reference
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childDataSnapshot in dataSnapshot.children) {
                    var title = ""
                    var author = ""
                    var path = ""
                    for (children in childDataSnapshot.children) {
                        when(children.key) {
                            "title" ->  title = children.value.toString()
                            "author" ->  author = children.value.toString()
                            "path" ->  path = children.value.toString()
                        }
                    }
                    tempCloudBooks.add(
                        Book(
                            childDataSnapshot.key?.toIntOrNull(),
                            title,
                            author,
                            path
                        )
                    )
                }
                _cloudBooks.postValue(tempCloudBooks)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Exception("Ошибка при получении книг!")
            }
        }
        database.addValueEventListener(postListener)
    }
}