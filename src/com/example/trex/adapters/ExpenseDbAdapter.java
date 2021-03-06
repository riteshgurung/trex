package com.example.trex.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDbAdapter {
	
	static String ETAG = "tag" ;
	static String AMOUNT = "amount" ;
	static String CATEGORY_ID = "cat_id" ;
	static String TIME_STAMP = "timestamp" ;
	

    private static final String TAG = "ExpenseDbAdapter";
    private static final String DATABASE_NAME = "trex";
    private static final String DATABASE_TABLE = "expenses";
    private static final int DATABASE_VERSION = 1;


   

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

   
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //     Log.w(TAG, "Upgrading database from version " + oldVersion + " to " //$NON-NLS-1$//$NON-NLS-2$
         //           + newVersion + ", which will destroy all old data"); //$NON-NLS-1$
            //db.execSQL("DROP TABLE IF EXISTS usersinfo"); //$NON-NLS-1$
     //       onCreate(db);
        }
    }


    public ExpenseDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }


    public ExpenseDbAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

   
    public void close() {
        this.mDbHelper.close();
    }


    public long insertExpense(String expenseTag, float expenseAmount,int catId,long timeStamp )
    {
    	ContentValues initialValues = new ContentValues() ;
    	initialValues.put(ETAG, expenseTag);
    	initialValues.put(AMOUNT, expenseAmount) ;
    	initialValues.put(CATEGORY_ID, catId);
    	initialValues.put(TIME_STAMP, timeStamp) ;
    	
		return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    	
    }


    public boolean deleteExpense(long ExpenseId) {

        return this.mDb.delete(DATABASE_TABLE, "_id = " + ExpenseId, null) > 0; //$NON-NLS-1$
    }


    public Cursor fetchAllExpenses(int cat_id) {

        Cursor mCursor = this.mDb.query(DATABASE_TABLE, new String[] { "_id",
                ETAG,AMOUNT,CATEGORY_ID,TIME_STAMP}, CATEGORY_ID + "="+ cat_id, null, null, null, null);
        return mCursor ;
    }


    public boolean updateExpense(int ExpenseId, ContentValues updatedValues)
    {
    	return this.mDb.update(DATABASE_TABLE, updatedValues,"_id ="+ ExpenseId , null) > 0 ;
    	
    }
    
    
    /*

    public boolean updateUser(long rowId, String name) {
        ContentValues args = new ContentValues();
        args.put(NAME, name);
        return this.mDb
                .update(DATABASE_TABLE, args, ROW_ID + "=" + rowId, null) > 0; //$NON-NLS-1$
    }
    
    */
}
	


