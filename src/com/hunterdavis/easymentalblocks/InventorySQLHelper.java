package com.hunterdavis.easymentalblocks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;


public class InventorySQLHelper extends android.database.sqlite.SQLiteOpenHelper {
	private static final String DATABASE_NAME = "easymentalblocks.db";
	private static final int DATABASE_VERSION = 2;

	// Table name
	public static final String TABLE = "pyramids";

	// Columns
	public static final String TOPBLOCK = "topblock";
	public static final String BLOCKONE = "blockone";
	public static final String BLOCKTWO = "blocktwo";
	public static final String BLOCKTHREE = "blockthree";
	public static final String BLOCKFOUR = "blockfour";
	public static final String BLOCKFIVE = "blockfive";
	public static final String BLOCKSIX = "blocksix";
	
	
	public static final String CHECKEDONE = "checkedone";
	public static final String CHECKEDTWO = "checkedtwo";
	public static final String CHECKEDTHREE = "checkedthree";
	public static final String CHECKEDFOUR = "checkedfour";
	public static final String CHECKEDFIVE = "checkedfive";
	public static final String CHECKEDSIX = "checkedsix";
	

	public static final String COLORONE = "colorone";
	public static final String COLORTWO = "colortwo";
	public static final String COLORTHREE = "colorthree";
	public static final String COLORFOUR = "colorfour";
	public static final String COLORFIVE = "colorfive";
	public static final String COLORSIX = "colorsix";
	public static final String COLORTOP = "colortop";
	

	public InventorySQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + "( " + BaseColumns._ID
				+ " integer primary key autoincrement, " +
				TOPBLOCK + " text not null,"+
				BLOCKONE + " text not null,"+
				BLOCKTWO + " text not null,"+
				BLOCKTHREE + " text not null,"+
				BLOCKFOUR + " text not null,"+
				BLOCKFIVE + " text not null,"+
				BLOCKSIX + " text not null,"+
				
				CHECKEDONE + " integer,"+
				CHECKEDTWO + " integer,"+
				CHECKEDTHREE + " integer,"+
				CHECKEDFOUR + " integer,"+
				CHECKEDFIVE + " integer,"+
				CHECKEDSIX + " integer,"+
				
				COLORONE + " integer,"+
				COLORTWO + " integer,"+
				COLORTHREE + " integer,"+
				COLORFOUR + " integer,"+
				COLORFIVE + " integer,"+
				COLORSIX + " integer,"+
				COLORTOP + " integer);";
		db.execSQL(sql);
		sql = "create table " + "pyramidsnotes" + "( " + BaseColumns._ID
				+ " integer primary key autoincrement, " +
				"block" + " text not null,"+
				"data" + " text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)
			return;

		String sql = null;
		if (oldVersion == 1)
			sql = "create table " + "pyramidsnotes" + "( " + BaseColumns._ID
			+ " integer primary key autoincrement, " +
			"block" + " text not null,"+
			"data" + " text);";
		if (oldVersion == 2)
			sql = "";

		Log.d("Inventory", "onUpgrade	: " + sql);
		if (sql != null)
			db.execSQL(sql);
	}

}
