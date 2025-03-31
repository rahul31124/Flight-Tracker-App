package com.example.airvibes

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Category::class, WebCategory::class], version = 2, autoMigrations = [
    AutoMigration(from = 1, to = 2)
])
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun webCategoryDao(): WebsitesDao




    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val MIGRATION_1_2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Create the webcategoriess table if it's missing or update schema
                        database.execSQL("""
            CREATE TABLE IF NOT EXISTS webcategoriess (
                prices TEXT NOT NULL DEFAULT 'undefined',
                booklink TEXT NOT NULL DEFAULT 'undefined',
                wnames TEXT NOT NULL DEFAULT 'undefined',
                idws INTEGER NOT NULL DEFAULT 'undefined',
                wimage_urls TEXT NOT NULL DEFAULT 'undefined',
                PRIMARY KEY (idws)
            )
        """)
                        // Add the unique index as expected
                        database.execSQL("""
            CREATE UNIQUE INDEX IF NOT EXISTS index_webcategoriess_wnames_wimage_urls_prices_booklink 
            ON webcategoriess(wnames, wimage_urls, prices, booklink)
        """)
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "airvibesdb"
                )
                    .addMigrations(MIGRATION_1_2) // Adding custom migration here
                    .fallbackToDestructiveMigration()  // This will delete the database on migration failure
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
