package com.linkzip.linkzip.data.di

import android.content.Context
import com.linkzip.linkzip.data.room.GroupDao
import com.linkzip.linkzip.data.room.IconDao
import com.linkzip.linkzip.data.room.LinkDao
import com.linkzip.linkzip.data.room.LinkRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): LinkRoomDataBase = LinkRoomDataBase.getDatabase(context)

    @Singleton
    @Provides
    fun provideGroupDao(roomDatabase: LinkRoomDataBase): GroupDao = roomDatabase.groupDao()

    @Singleton
    @Provides
    fun provideLinkDao(roomDatabase: LinkRoomDataBase): LinkDao = roomDatabase.linkDao()

    @Singleton
    @Provides
    fun provideIconDao(roomDatabase: LinkRoomDataBase): IconDao = roomDatabase.iconDao()
}