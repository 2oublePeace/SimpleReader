package alexb.roommvvmrxjava2kotlin.ui.view

import alexb.roommvvmrxjava2kotlin.R
import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.user_activity.*
import java.io.Serializable

class UserActivity : AppCompatActivity() {

    private var people: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)
        if (intent.hasExtra(EXTRA_EDIT_USER)) {
            people = intent.extras?.get(EXTRA_EDIT_USER) as User
        }
        initViews(people)
    }

    private fun initViews(people: User?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (people != null) {
            et_first_name.setText(people.firstName)
            et_last_name.setText(people.lastName)
            et_sport.setText(people.sport)
        }
    }

    private fun saveUser() {
        val firstName: String = et_first_name.text.toString()
        val lastName: String = et_last_name.text.toString()
        val sport: String = et_sport.text.toString()
        if (sport.trim { it <= ' ' }.isEmpty() || lastName.trim { it <= ' ' }
                .isEmpty() || firstName.trim { it <= ' ' }
                .isEmpty()
        ) {
            Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            when (people) {
                null -> intent.putExtra(
                    EXTRA_REQUEST,
                    User(firstName = firstName, lastName = lastName, sport = sport) as Serializable
                )
                else -> intent.putExtra(
                    EXTRA_REQUEST,
                    User(
                        people?.id,
                        firstName = firstName,
                        lastName = lastName,
                        sport = sport
                    ) as Serializable
                )
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_card -> {
                saveUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}