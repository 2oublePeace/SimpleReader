package alexb.roommvvmrxjava2kotlin.data.repository

import alexb.roommvvmrxjava2kotlin.data.local.dao.UserDao
import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

class UserRepository(private val userDao: UserDao) {

    fun getUser(): Maybe<List<User>> {
        return userDao.getUsers()
    }

    fun insertUser(user: User): Completable {
        return userDao.insertUser(user)
    }

    fun updateUser(user: User): Completable {
        return userDao.updateUser(user)
    }

    fun deleteUser(user: User): Completable {
        return userDao.deleteUser(user)
    }
}