package alexb.roommvvmrxjava2kotlin.ui.view

import alexb.roommvvmrxjava2kotlin.R
import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import alexb.roommvvmrxjava2kotlin.ui.adapter.SwipeToDeleteCallback
import alexb.roommvvmrxjava2kotlin.ui.adapter.UserAdapter
import alexb.roommvvmrxjava2kotlin.ui.viewmodel.UserViewModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var deletedUser: User
    private var personAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initSwiper()
        setListener()
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getUsers()
        viewModel.user.observe(this, Observer {
            if (it.isNotEmpty()) {
                personAdapter.setUser(it)
            }
        })
    }

    private fun setListener() {
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, UserActivity::class.java)
            startActivityForResult(intent, ADD_USER_REQUEST)
        }
        personAdapter.onClick = { contact ->
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra(
                EXTRA_EDIT_USER,
                contact
            )
            startActivityForResult(intent, EDIT_USER_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val user: User? = null
        if (requestCode == ADD_USER_REQUEST && resultCode == Activity.RESULT_OK) {
            user.let { viewModel.saveUser(data?.extras?.get(EXTRA_REQUEST) as User) }

        } else if (requestCode == EDIT_USER_REQUEST && resultCode == Activity.RESULT_OK) {
            user.let { viewModel.updateUser(data?.extras?.get(EXTRA_REQUEST) as User) }

        }
    }

    private fun initSwiper() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_user.adapter as UserAdapter
                deletedUser = adapter.removeAt(viewHolder.adapterPosition)
                viewModel.deleteUser(deletedUser)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(rv_user)
    }

    private fun initViews() {
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_user.adapter = personAdapter
    }
}

const val ADD_USER_REQUEST = 1
const val EDIT_USER_REQUEST = 2
const val EXTRA_EDIT_USER = "EXTRA_EDIT_USER"
const val EXTRA_REQUEST = "EXTRA_USER"