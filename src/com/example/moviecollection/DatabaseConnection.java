package com.example.moviecollection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DatabaseConnection {
	
	
	private static final String DATABASE_title = "Usermovies";
    
	   private SQLiteDatabase database; // for interacting with the database
	   private DatabaseOpenHelper databaseOpenHelper; // creates the database

	   // public constructor for DatabaseConnector
	   public DatabaseConnection(Context context) 
	   {
	      // create a new DatabaseOpenHelper
	      databaseOpenHelper = 
	         new DatabaseOpenHelper(context, DATABASE_title, null, 1);
	   }

	   // open the database connection
	   public void open() throws SQLException 
	   {
	      // create or open a database for reading/writing
	      database = databaseOpenHelper.getWritableDatabase();
	   }

	   // close the database connection
	   public void close() 
	   {
	      if (database != null)
	         database.close(); // close the database connection
	   } 

	   // inserts a new contact in the database
	   public long insertContact(String title, String year, String director,  
	      String rating) 
	   {
	      ContentValues movie = new ContentValues();
	      movie.put("title", title);
	      movie.put("year",year);
	      movie.put("director", director);
	      movie.put("rating", rating);
	      open(); // open the database
	      long rowID = database.insert("movies", null, movie);
	      close(); // close the database
	      return rowID;
	   } 

	   // updates an existing contact in the database
	   public void updateContact(long id, String title, String year, 
	      String director, String rating) 
	   {
	      ContentValues editContact = new ContentValues();
	      editContact.put("title", title);
	      editContact.put("year", year);
	      editContact.put("director", director);
	      editContact.put("rating", rating);
	      open(); // open the database
	      database.update("movies", editContact, "_id=" + id, null);
	      close(); // close the database
	   } // end method updateContact

	   // return a Cursor with all contact titles in the database
	   public Cursor getAllmovies() 
	   {
	      return database.query("movies", new String[] {"_id", "title"}, 
	         null, null, null, null, "title");
	   } 

	   // return a Cursor containing specified contact's information 
	   public Cursor getOneContact(long id) 
	   {
	      return database.query(
	         "movies", null, "_id=" + id, null, null, null, null);
	   } 

	   // delete the contact specified by the given String title
	   public void deleteContact(long id) 
	   {
	      open(); // open the database
	      database.delete("movies", "_id=" + id, null);
	      close(); // close the database
	   } 
	   
	   private class DatabaseOpenHelper extends SQLiteOpenHelper 
	   {
	      // constructor
	      public DatabaseOpenHelper(Context context, String title,
	         CursorFactory factory, int version) 
	      {
	         super(context, title, factory, version);
	      }

	      // creates the movies table when the database is created
	      @Override
	      public void onCreate(SQLiteDatabase db) 
	      {
	         // query to create a new table titled movies
	         String createQuery = "CREATE TABLE movies" +
	            "(_id integer primary key autoincrement," +
	            "title TEXT, year TEXT, director TEXT, " +
	            "rating TEXT);";
	                  
	         db.execSQL(createQuery); // execute query to create the database
	      } 

	      @Override
	      public void onUpgrade(SQLiteDatabase db, int oldVersion, 
	          int newVersion) 
	      {
	      }
	   } // end class DatabaseOpenHelper


}
