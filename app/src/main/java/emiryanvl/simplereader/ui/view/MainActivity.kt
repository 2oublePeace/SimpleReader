package emiryanvl.simplereader.ui.view

import emiryanvl.simplereader.R
import emiryanvl.simplereader.data.local.entity.Book
import emiryanvl.simplereader.ui.adapter.BookAdapter
import emiryanvl.simplereader.ui.adapter.SwipeToDeleteCallback
import emiryanvl.simplereader.ui.viewmodel.CacheBookViewModel
import emiryanvl.simplereader.utils.PermissionUtils
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var cacheBookViewModel: CacheBookViewModel
    private lateinit var deletedUser: Book
    private val permissionStorage = 101
    private var bookAdapter = BookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, permissionStorage)
        }
        initViews()
        initSwiper()
        setListener()
        cacheBookViewModel = ViewModelProviders.of(this).get(CacheBookViewModel::class.java)
        cacheBookViewModel.getBooks()
        cacheBookViewModel.user.observe(this) {
            if (it.isNotEmpty()) {
                bookAdapter.setBook(it)
            }
        }
    }

    private fun setListener() {
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, CloudBookActivity::class.java)
            startActivityForResult(intent, ADD_USER_REQUEST)
        }
        bookAdapter.onClick = { book ->
            val intent = Intent(this, BookActivity::class.java)
            intent.putExtra(
                EXTRA_CHOOSE_BOOK,
                book
            )
            startActivityForResult(intent, EDIT_USER_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val book: Book? = null
        if (requestCode == ADD_USER_REQUEST && resultCode == Activity.RESULT_OK) {
            book.let { cacheBookViewModel.saveBook(data?.extras?.get(EXTRA_REQUEST) as Book) }

        } else if (requestCode == EDIT_USER_REQUEST && resultCode == Activity.RESULT_OK) {
            book.let { cacheBookViewModel.updateBook(data?.extras?.get(EXTRA_REQUEST) as Book) }
        }
    }

    private fun initSwiper() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_user.adapter as BookAdapter
                deletedUser = adapter.removeAt(viewHolder.adapterPosition)
                cacheBookViewModel.deleteUser(deletedUser)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(rv_user)
    }

    private fun initViews() {
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_user.adapter = bookAdapter
    }
}

//TODO:Если не нало удалить
const val ADD_USER_REQUEST = 1
//TODO:Если не нало удалить
const val EDIT_USER_REQUEST = 2
const val EXTRA_CHOOSE_BOOK = "EXTRA_CHOOSE_BOOK"
//TODO:Если не нало удалить
const val EXTRA_REQUEST = "EXTRA_USER"