package alexb.roommvvmrxjava2kotlin.ui.viewmodel

import alexb.roommvvmrxjava2kotlin.data.local.AppDatabase
import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import alexb.roommvvmrxjava2kotlin.data.repository.UserRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<List<User>>()
    var user: LiveData<List<User>> = _user

    private var userRepository: UserRepository? =
        UserRepository(AppDatabase.getDatabase(application).userDao())

    fun getUsers() {
        userRepository?.getUser()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (!it.isNullOrEmpty()) {
                    _user.postValue(it)
                } else {
                    _user.postValue(listOf())
                }
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    fun updateUser(user: User?) {
        userRepository?.updateUser(user)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                getUsers()
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun saveUser(user: User?) {
        userRepository?.insertUser(user)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                getUsers()
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun deleteUser(user: User) {
        userRepository?.deleteUser(user)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                getUsers()
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }
}

