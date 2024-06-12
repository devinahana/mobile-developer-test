package com.suitmedia.suitmediatest.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suitmedia.suitmediatest.data.remote.response.UserDetail

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserDetail>)

    @Query("SELECT * FROM user_table")
    fun getAllUser(): PagingSource<Int, UserDetail>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}