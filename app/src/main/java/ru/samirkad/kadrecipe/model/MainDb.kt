package ru.samirkad.kadrecipe.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RecipeEntity::class],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val recipeDao: RecipeDao

    companion object {
        @Volatile
        private var instance: MainDb? = null

        fun getInstance(context: Context): MainDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context, MainDb::class.java, "appRecipe.db" // name data base
            ).allowMainThreadQueries() // main thread
                .build()
    }
}