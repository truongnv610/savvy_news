package com.savvy.app.di

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class FragmentModule {

    @Provides
    fun provideArgument(fragment: Fragment): Bundle = fragment.arguments ?: Bundle.EMPTY
}
