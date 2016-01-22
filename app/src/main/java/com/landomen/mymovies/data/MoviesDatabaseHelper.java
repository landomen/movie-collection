package com.landomen.mymovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.landomen.mymovies.data.models.Movie;
import com.landomen.mymovies.data.models.SimpleObject;
import com.landomen.mymovies.rest.models.MovieResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the database and interactions.
 * Use getInstance() to retrieve an instance of this helper.
 */
public class MoviesDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "moviesDatabase";
    private static final int DATABASE_VERSION = 1;

    //Tables
    private static final String TABLE_MOVIE = "Movie";
    private static final String TABLE_RATING = "Rating";
    private static final String TABLE_COUNTRY = "Country";
    private static final String TABLE_LANGUAGE = "Language";
    private static final String TABLE_PERSON = "Person";
    private static final String TABLE_GENRE = "Genre";
    private static final String TABLE_MOVIE_ACTOR = "Movie_has_Actor";
    private static final String TABLE_MOVIE_DIRECTOR = "Movie_has_Director";
    private static final String TABLE_MOVIE_WRITER = "Movie_has_Writer";
    private static final String TABLE_MOVIE_LANGUAGE = "Movie_has_Language";
    private static final String TABLE_MOVIE_COUNTRY = "Movie_has_Country";
    private static final String TABLE_MOVIE_GENRE = "Movie_has_Genre";

    // Shared Columns
    private static final String COL_ID = "Id";
    private static final String COL_MOVIE_ID = "Movie_Id";
    private static final String COL_TITLE = "Title";

    //Table Movie columns
    private static final String COL_MOVIE_IMDB_ID = "ImdbId";
    private static final String COL_MOVIE_YEAR = "Year";
    private static final String COL_MOVIE_RELEASED = "Released";
    private static final String COL_MOVIE_RUNTIME = "Runtime";
    private static final String COL_MOVIE_METASCORE = "Metascore";
    private static final String COL_MOVIE_IMDBRATING = "ImdbRating";
    private static final String COL_MOVIE_PLOT = "Plot";
    private static final String COL_MOVIE_IMAGE = "Image";
    private static final String COL_MOVIE_RATING_ID = "RatingId";
    private static final String COL_MOVIE_WATCHED = "Watched";

    // Other Columns
    private static final String COL_MOVIE_WRITER_ID = "Writer_Id";
    private static final String COL_MOVIE_ACTOR_ID = "Actor_Id";
    private static final String COL_MOVIE_DIRECTOR_ID = "Director_Id";
    private static final String COL_MOVIE_LANGUAGE_ID = "Language_Id";
    private static final String COL_MOVIE_COUNTRY_ID = "Country_Id";
    private static final String COL_MOVIE_GENRE_ID = "Genre_Id";

    private static MoviesDatabaseHelper dbInstance;

    private MoviesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Returns an instance of database.
     *
     * @param context
     * @return
     */
    public static synchronized MoviesDatabaseHelper getInstance(Context context) {
        if (dbInstance == null)
            dbInstance = new MoviesDatabaseHelper(context.getApplicationContext());
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE +
                "(" +
                COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COL_MOVIE_IMDB_ID + " TEXT," +
                COL_TITLE + " TEXT," +
                COL_MOVIE_YEAR + " INTEGER," +
                COL_MOVIE_RELEASED + " TEXT," +
                COL_MOVIE_RUNTIME + " INTEGER," +
                COL_MOVIE_METASCORE + " INTEGER," +
                COL_MOVIE_IMDBRATING + " REAL," +
                COL_MOVIE_PLOT + " TEXT," +
                COL_MOVIE_IMAGE + " TEXT," +
                COL_MOVIE_WATCHED + " TEXT," +
                COL_MOVIE_RATING_ID + " INTEGER" +
                ")";

        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_COUNTRY +
                "(" +
                COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT" +
                ")";

        String CREATE_LANGUAGE_TABLE = "CREATE TABLE " + TABLE_LANGUAGE +
                "(" +
                COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT" +
                ")";

        String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON +
                "(" +
                COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT" +
                ")";

        String CREATE_RATING_TABLE = "CREATE TABLE " + TABLE_RATING +
                "(" +
                COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT" +
                ")";

        String CREATE_GENRE_TABLE = "CREATE TABLE " + TABLE_GENRE +
                "(" +
                COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT" +
                ")";

        String CREATE_MOVIE_ACTOR_TABLE = "CREATE TABLE " + TABLE_MOVIE_ACTOR +
                "(" +
                COL_MOVIE_ID + " INTEGER NOT NULL," +
                COL_MOVIE_ACTOR_ID + " INTEGER NOT NULL" +
                ")";


        String CREATE_MOVIE_DIRECTOR_TABLE = "CREATE TABLE " + TABLE_MOVIE_DIRECTOR +
                "(" +
                COL_MOVIE_ID + " INTEGER NOT NULL," +
                COL_MOVIE_DIRECTOR_ID + " INTEGER NOT NULL" +
                ")";

        String CREATE_MOVIE_WRITER_TABLE = "CREATE TABLE " + TABLE_MOVIE_WRITER +
                "(" +
                COL_MOVIE_ID + " INTEGER NOT NULL," +
                COL_MOVIE_WRITER_ID + " INTEGER NOT NULL" +
                ")";

        String CREATE_MOVIE_LANGUAGE_TABLE = "CREATE TABLE " + TABLE_MOVIE_LANGUAGE +
                "(" +
                COL_MOVIE_ID + " INTEGER NOT NULL," +
                COL_MOVIE_LANGUAGE_ID + " INTEGER NOT NULL" +
                ")";

        String CREATE_MOVIE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_MOVIE_COUNTRY +
                "(" +
                COL_MOVIE_ID + " INTEGER NOT NULL," +
                COL_MOVIE_COUNTRY_ID + " INTEGER NOT NULL" +
                ")";

        String CREATE_MOVIE_GENRE_TABLE = "CREATE TABLE " + TABLE_MOVIE_GENRE +
                "(" +
                COL_MOVIE_ID + " INTEGER NOT NULL," +
                COL_MOVIE_GENRE_ID + " INTEGER NOT NULL" +
                ")";

        db.execSQL(CREATE_MOVIE_TABLE);
        db.execSQL(CREATE_COUNTRY_TABLE);
        db.execSQL(CREATE_LANGUAGE_TABLE);
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_RATING_TABLE);
        db.execSQL(CREATE_GENRE_TABLE);
        db.execSQL(CREATE_MOVIE_COUNTRY_TABLE);
        db.execSQL(CREATE_MOVIE_LANGUAGE_TABLE);
        db.execSQL(CREATE_MOVIE_WRITER_TABLE);
        db.execSQL(CREATE_MOVIE_DIRECTOR_TABLE);
        db.execSQL(CREATE_MOVIE_ACTOR_TABLE);
        db.execSQL(CREATE_MOVIE_GENRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Adds a new row to the basic tables.
     *
     * @param table TABLE_COUNTRY, TABLE_LANGUAGE, TABLE_RATING, TABLE_GENRE or TABLE_PERSON
     * @param title Title to insert
     * @return ID of the new rating, -1 if error
     */
    private long addSimpleItem(String table, String title) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long id = -1;
        try {
            ContentValues values = new ContentValues(1);
            values.put(COL_TITLE, title);
            id = db.insertOrThrow(table, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return id;
    }

    /**
     * Adds a new row to one of the Many-to-many tables.
     *
     * @param table          TABLE_MOVIE_LANGUAGE, TABLE_MOVIE_WRITER, TABLE_MOVIE_DIRECTOR, TABLE_MOVIE_ACTOR, TABLE_MOVIE_GENRE, TABLE_MOVIE_COUNTRY
     * @param secondIdColumn Name of the second columns, ex.: COL_MOVIE_LANGUAGE_ID
     * @param movieId        Id value of the movie
     * @param secondId       Id value of the second column
     * @return
     */
    private boolean addConnection(String table, String secondIdColumn, long movieId, long secondId) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        boolean success = true;
        try {
            ContentValues values = new ContentValues(2);
            values.put(COL_MOVIE_ID, movieId);
            values.put(secondIdColumn, secondId);
            db.insertOrThrow(table, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            db.endTransaction();
        }
        return success;
    }

    /**
     * Returns an object that represents a row in basic tables.
     *
     * @param table TABLE_COUNTRY, ...
     * @param title
     * @return
     */
    private SimpleObject getSimpleItem(String table, String title) {
        SQLiteDatabase db = getReadableDatabase();
        SimpleObject item = new SimpleObject();
        Cursor cursor = null;
        try {
            cursor = db.query(table, new String[]{COL_ID, COL_TITLE}, COL_TITLE + "=?", new String[]{title}, null, null, null);
            if (cursor.moveToFirst()) {
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return item;
    }

    /**
     * Gets all rows from basic tables that are connected to the movie.
     *
     * @param table1         TABLE_COUNTRY, ...
     * @param table2         TABLE_MOVIE_LANGUAGE, ...
     * @param table2IdColumn COL_MOVIE_GENRE_ID, ...
     * @param movieId        Id of the movie for which to retrieve
     * @return
     */
    private List<SimpleObject> getSimpleItems(String table1, String table2, String table2IdColumn, int movieId) {
        SQLiteDatabase db = getReadableDatabase();
        List<SimpleObject> items = new ArrayList<>();
        Cursor cursor = null;
        try {
            String sqlQuery = String.format("SELECT t1.* FROM %s t1 JOIN %s t2 ON t2.%s=t1.%s WHERE t2.%s=?", table1, table2, table2IdColumn, COL_ID, COL_MOVIE_ID);
            cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(movieId)});
            while (cursor.moveToNext()) {
                items.add(new SimpleObject(cursor.getInt(0), cursor.getString(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return items;
    }

    /**
     * Gets a list of all genres.
     *
     * @return
     */
    public List<SimpleObject> getGenres() {
        SQLiteDatabase db = getReadableDatabase();
        List<SimpleObject> items = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_GENRE, null, null, null, null, null, COL_TITLE);
            while (cursor.moveToNext()) {
                items.add(new SimpleObject(cursor.getInt(0), cursor.getString(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return items;
    }

    /**
     * Checks if movie exists based on its imdb Id.
     *
     * @param imdbId
     * @return
     */
    public boolean doesMovieExist(String imdbId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;
        try {
            cursor = db.query(TABLE_MOVIE, new String[]{COL_ID}, COL_MOVIE_IMDB_ID + "=?", new String[]{imdbId}, null, null, null);
            if (cursor.moveToFirst()) {
                exists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return exists;
    }

    /**
     * Deletes a specific movie from the database.
     * Languages, countries and others will still be stored.
     *
     * @param id
     * @return True if successfully deleted
     */
    public boolean deleteMovie(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        boolean success = true;
        try {
            db.delete(TABLE_MOVIE_GENRE, COL_MOVIE_ID + "=" + id, null);
            db.delete(TABLE_MOVIE_COUNTRY, COL_MOVIE_ID + "=" + id, null);
            db.delete(TABLE_MOVIE_LANGUAGE, COL_MOVIE_ID + "=" + id, null);
            db.delete(TABLE_MOVIE_WRITER, COL_MOVIE_ID + "=" + id, null);
            db.delete(TABLE_MOVIE_DIRECTOR, COL_MOVIE_ID + "=" + id, null);
            db.delete(TABLE_MOVIE_ACTOR, COL_MOVIE_ID + "=" + id, null);
            db.delete(TABLE_MOVIE, COL_ID + "=" + id, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            db.endTransaction();
        }
        return success;
    }

    /**
     * Updates the "seen" status of the movie.
     *
     * @param id
     * @param seen
     * @return
     */
    public boolean updateSeenStatus(int id, boolean seen) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues(1);
            values.put(COL_MOVIE_WATCHED, seen ? "1" : "0");
            return db.update(TABLE_MOVIE, values, COL_ID + "=" + id, null) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Adds a new movie, along with languages, countries, ratings, ...
     *
     * @param movieResponse
     * @return True on success.
     */
    public boolean addMovie(MovieResponse movieResponse) {
        //check if movie exists
        if (doesMovieExist(movieResponse.getImdbID())) {
            return false;
        }

        SQLiteDatabase db = getWritableDatabase();
        boolean success = true;
        db.beginTransaction();
        try {
            //first add movie
            ContentValues values = new ContentValues();
            values.put(COL_MOVIE_IMDB_ID, movieResponse.getImdbID());
            values.put(COL_TITLE, movieResponse.getTitle());
            values.put(COL_MOVIE_YEAR, movieResponse.getYear());
            values.put(COL_MOVIE_RELEASED, movieResponse.getReleased());
            values.put(COL_MOVIE_RUNTIME, Integer.parseInt(movieResponse.getRuntime().replace("min", "").trim()));
            values.put(COL_MOVIE_METASCORE, movieResponse.getMetascore());
            values.put(COL_MOVIE_IMDBRATING, movieResponse.getImdbRating());
            values.put(COL_MOVIE_PLOT, movieResponse.getPlot());
            values.put(COL_MOVIE_IMAGE, movieResponse.getPoster());
            values.put(COL_MOVIE_WATCHED, "0");
            //get rating id
            long ratingId = getSimpleItem(TABLE_RATING, movieResponse.getRated()).getId();
            if (ratingId == -1)
                ratingId = addSimpleItem(TABLE_RATING, movieResponse.getRated());
            values.put(COL_MOVIE_RATING_ID, ratingId);
            long movieId = db.insertOrThrow(TABLE_MOVIE, null, values);
            if (movieId == -1) {
                throw new SQLException("Movie wasn't inserted!");
            }

            //add country
            String[] countries = movieResponse.getCountry().split(",");
            for (String country :
                    countries) {
                long cId = getSimpleItem(TABLE_COUNTRY, country.trim()).getId();
                if (cId == -1) {
                    cId = addSimpleItem(TABLE_COUNTRY, country.trim());
                }
                addConnection(TABLE_MOVIE_COUNTRY, COL_MOVIE_COUNTRY_ID, movieId, cId);
            }

            //add language
            String[] languages = movieResponse.getLanguage().split(",");
            for (String lang :
                    languages) {
                long lId = getSimpleItem(TABLE_LANGUAGE, lang.trim()).getId();
                if (lId == -1) {
                    lId = addSimpleItem(TABLE_LANGUAGE, lang.trim());
                }
                addConnection(TABLE_MOVIE_LANGUAGE, COL_MOVIE_LANGUAGE_ID, movieId, lId);
            }

            //add genre
            String[] genres = movieResponse.getGenre().split(",");
            for (String genre :
                    genres) {
                long gId = getSimpleItem(TABLE_GENRE, genre.trim()).getId();
                if (gId == -1) {
                    gId = addSimpleItem(TABLE_GENRE, genre.trim());
                }
                addConnection(TABLE_MOVIE_GENRE, COL_MOVIE_GENRE_ID, movieId, gId);
            }

            //add writer
            String[] writers = movieResponse.getWriter().split(",");
            for (String writer :
                    writers) {
                long wId = getSimpleItem(TABLE_PERSON, writer.trim()).getId();
                if (wId == -1) {
                    wId = addSimpleItem(TABLE_PERSON, writer.trim());
                }
                addConnection(TABLE_MOVIE_WRITER, COL_MOVIE_WRITER_ID, movieId, wId);
            }

            //add director
            String[] directors = movieResponse.getDirector().split(",");
            for (String director :
                    directors) {
                long dId = getSimpleItem(TABLE_PERSON, director.trim()).getId();
                if (dId == -1) {
                    dId = addSimpleItem(TABLE_PERSON, director.trim());
                }
                addConnection(TABLE_MOVIE_DIRECTOR, COL_MOVIE_DIRECTOR_ID, movieId, dId);
            }

            //add actor
            String[] actors = movieResponse.getActors().split(",");
            for (String actor :
                    actors) {
                long aId = getSimpleItem(TABLE_PERSON, actor.trim()).getId();
                if (aId == -1) {
                    aId = addSimpleItem(TABLE_PERSON, actor.trim());
                }
                addConnection(TABLE_MOVIE_ACTOR, COL_MOVIE_ACTOR_ID, movieId, aId);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            db.endTransaction();
        }
        return success;
    }

    /**
     * Retrieves all movies in the database.
     *
     * @param searchTitle Pass null for all
     * @return
     */
    public List<Movie> getMovies(String searchTitle) {
        SQLiteDatabase db = getReadableDatabase();
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = null;
        try {
            //Get all movies
            String sql = "SELECT m.*, r.%s as RatingTitle FROM %s m JOIN %s r ON r.%s=m.%s ORDER BY %s";
            if (searchTitle == null) {
                String sqlQuery = String.format("SELECT m.*, r.%s as RatingTitle FROM %s m JOIN %s r ON r.%s=m.%s ORDER BY %s", COL_TITLE, TABLE_MOVIE, TABLE_RATING, COL_ID, COL_MOVIE_RATING_ID, COL_TITLE);
                cursor = db.rawQuery(sqlQuery, null);
            } else {
                String sqlQuery = String.format("SELECT m.*, r.%s as RatingTitle FROM %s m JOIN %s r ON r.%s=m.%s WHERE m.%s LIKE %s ORDER BY %s", COL_TITLE, TABLE_MOVIE, TABLE_RATING, COL_ID, COL_MOVIE_RATING_ID, COL_TITLE, "?", COL_TITLE);
                cursor = db.rawQuery(sqlQuery, new String[]{"%" + searchTitle + "%"});
            }

            while (cursor.moveToNext()) {
                // Create a new movie
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                movie.setImdbId(cursor.getString(cursor.getColumnIndex(COL_MOVIE_IMDB_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                movie.setYear(cursor.getInt(cursor.getColumnIndex(COL_MOVIE_YEAR)));
                movie.setRuntime(cursor.getInt(cursor.getColumnIndex(COL_MOVIE_RUNTIME)));
                movie.setMetascore(cursor.getInt(cursor.getColumnIndex(COL_MOVIE_METASCORE)));
                movie.setImdbRating(cursor.getFloat(cursor.getColumnIndex(COL_MOVIE_IMDBRATING)));
                movie.setPlot(cursor.getString(cursor.getColumnIndex(COL_MOVIE_PLOT)));
                movie.setPoster(cursor.getString(cursor.getColumnIndex(COL_MOVIE_IMAGE)));
                movie.setWatched(cursor.getString(cursor.getColumnIndex(COL_MOVIE_WATCHED)).equals("1"));
                movie.setRating(new SimpleObject(cursor.getInt(cursor.getColumnIndex(COL_MOVIE_RATING_ID)), cursor.getString(cursor.getColumnIndex("RatingTitle"))));
                //genres
                movie.setGenres(getSimpleItems(TABLE_GENRE, TABLE_MOVIE_GENRE, COL_MOVIE_GENRE_ID, movie.getId()));
                //countries
                movie.setCountries(getSimpleItems(TABLE_COUNTRY, TABLE_MOVIE_COUNTRY, COL_MOVIE_COUNTRY_ID, movie.getId()));
                //languages
                movie.setLanguages(getSimpleItems(TABLE_LANGUAGE, TABLE_MOVIE_LANGUAGE, COL_MOVIE_LANGUAGE_ID, movie.getId()));
                //writers
                movie.setWriters(getSimpleItems(TABLE_PERSON, TABLE_MOVIE_WRITER, COL_MOVIE_WRITER_ID, movie.getId()));
                //directors
                movie.setDirectors(getSimpleItems(TABLE_PERSON, TABLE_MOVIE_DIRECTOR, COL_MOVIE_DIRECTOR_ID, movie.getId()));
                //actors
                movie.setActors(getSimpleItems(TABLE_PERSON, TABLE_MOVIE_ACTOR, COL_MOVIE_ACTOR_ID, movie.getId()));

                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return movies;
    }

}
