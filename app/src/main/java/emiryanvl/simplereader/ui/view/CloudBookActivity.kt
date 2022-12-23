package emiryanvl.simplereader.ui.view

import emiryanvl.simplereader.R
import emiryanvl.simplereader.data.local.entity.Book
import emiryanvl.simplereader.ui.adapter.BookAdapter
import emiryanvl.simplereader.ui.viewmodel.CacheBookViewModel
import emiryanvl.simplereader.ui.viewmodel.CloudBookViewModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_cloud_books.*
import java.io.File
import java.io.Serializable

class CloudBookActivity : AppCompatActivity() {

    private var cloudBooksAdapter = BookAdapter()
    private lateinit var cacheBookViewModel: CacheBookViewModel
    private lateinit var cloudBookViewModel: CloudBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_books)

        initViews()
        setListener()

        cacheBookViewModel = ViewModelProviders.of(this).get(CacheBookViewModel::class.java)
        cloudBookViewModel = ViewModelProviders.of(this).get(CloudBookViewModel::class.java)
        cloudBookViewModel.getCloudBooks()
        cloudBookViewModel.cloudBooks.observe(this) {
            if (it.isNotEmpty()) {
                cloudBooksAdapter.setBook(it)
            }
        }
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rv_cloud_books.layoutManager = LinearLayoutManager(this)
        rv_cloud_books.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_cloud_books.adapter = cloudBooksAdapter
    }

    private fun setListener() {
        cloudBooksAdapter.onClick = { book ->
            val storage = Firebase.storage
            val storageRef = storage.reference
            val islandRef = storageRef.child(book.path)
            val localFile = File.createTempFile(book.title, "pdf")
            islandRef.getFile(localFile).addOnSuccessListener {
                val intentMain = Intent(this, MainActivity::class.java)
                intentMain.putExtra(
                    EXTRA_REQUEST,
                    Book(title = book.title as String, author = book.author, path = book.path) as Serializable)
                setResult(Activity.RESULT_OK, intentMain)
                finish()
            }.addOnFailureListener {
                Exception("Нету книги")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.user_menu, menu)
        return true
    }
}