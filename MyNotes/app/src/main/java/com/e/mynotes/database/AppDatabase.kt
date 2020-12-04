package com.e.mynotes.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.e.mynotes.model.Task

private const val TAG = "DB"

private const val DATABASE_NAME = "ToDoTasks.db"
private const val DATABASE_VERSION = 3
private const val TABLE_NAME = "Tasks"
private const val ID = "_ID"
private const val TASK_NAME = "Name"
private const val TASK_DESCRIPTION = "Description"


class AppDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                " $ID INTEGER PRIMARY KEY NOT NULL," +
                " $TASK_NAME TEXT NOT NULL," +
                " $TASK_DESCRIPTION TEXT);"
        Log.d(TAG, query)
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME};")
        onCreate(db)
    }

    fun addTask(task: Task) {
        val database = this.writableDatabase
        val newRow = ContentValues().apply {
            put(TASK_NAME, task.name)
            put(TASK_DESCRIPTION, task.description)
        }
        database.insert(TABLE_NAME, null, newRow)
    }

    fun updateTask(task: Task) {
        val database = this.writableDatabase
        val updatedRow = ContentValues().apply {
            put(TASK_NAME , task.name )
            put(TASK_DESCRIPTION , task.description)
        }
        //Log.d(TAG, "$task.id , $task.taskName , $task.taskDescription")
        database.update(TABLE_NAME,updatedRow,"$ID=?" , arrayOf(task.id.toString()))
    }

    fun deleteTask(task: Task) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME,"$ID=?", arrayOf(task.id.toString()))
    }

    fun deleteDatabase() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME,null,null)
    }

    fun readAllData(): Cursor {
        val query = "SELECT * FROM $TABLE_NAME;"
        val database = this.readableDatabase
        return database.rawQuery(query , null)
    }

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: AppDatabase(context).also { instance = it }
            }
    }
}