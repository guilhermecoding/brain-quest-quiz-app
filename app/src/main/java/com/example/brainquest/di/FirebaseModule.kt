package com.example.brainquest.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Or the appropriate component for your scope
object FirebaseModule { // You can name this object as you like, e.g., AppModule

    @Provides
    @Singleton // Use @Singleton if you want a single instance throughout the app
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}
