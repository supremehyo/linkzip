package com.linkzip.linkzip.data.di

import com.linkzip.linkzip.repository.GroupRepository
import com.linkzip.linkzip.usecase.AllViewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCase {
    @Provides
    @Singleton
    fun provideAllGroupsUseCase(
        repository: GroupRepository
    ) : AllViewUseCase {
        return AllViewUseCase(
            groupRepository = repository
        )
    }
}