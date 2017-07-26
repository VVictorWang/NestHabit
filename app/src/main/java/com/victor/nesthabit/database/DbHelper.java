//package com.victor.nesthabit.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by victor on 7/26/17.
// * email: chengyiwang@hustunique.com
// * blog: www.victorwang.science                                            #
// */
//
//public class DbHelper extends SQLiteOpenHelper{
//    public static final String DATABASE_NAME = "nesthabit.db";
//    private static final int DATABASE_VERSION = 1;
//    public static final String DB_TABLE_NAME = "nests";
//    public static final String DB_COLUMN_ID = "id";    //标识名
//    public static final String DB_NEST_ID = "_id";
//    public static final String DB_COLUMN_NAME = "name";   //代办事项的标题
//    public static final String DB_COLUMN_DESC = "desc";   //内容
//    public static final String DB_COLUMN_MEMBER_LIMIT = "members_limit"; //截止时间
//    public static final String DB_COLUMN_START_TIME = "start_time";   //闹钟重复次数
//    public static final String DB_COLUMN_CHALLENDAYS = "challenge_days";    //创建时间
//    public static final String DB_COLUMN_OPEN = "open";    //重要程度（优先级）
//    public static final String DB_COLUMN_COVER_IMAGE = "cover_image";    //是否已完成
//    public static final String DB_COLUMN_CREATE_TIME = "create_time";   //闹钟音乐的data路径
//    public static final String DB_COLUMN_CREATOR = "creator"; //闹钟音乐的名字
//    public static final String DB_COLUMN_OWNER = "owner";
//    public static final String DB_COLUMN_MEMBERS_AMOUNT = "members_amount";
//
//
//    private DbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + DB_TABLE_NAME + "(" +
//                DB_COLUMN_ID + " INTEGER PRIMARY KEY, " +
//                DB_COLUMN_TITLE + " TEXT, " +
//                DB_COLUMN_CONTENT + " TEXT, " +
//                DB_COLUMN_FREQUENCY + " TEXT, " +
//                DB_COLUMN_TIME + " LONG, " + DB_TIME_CREATED +" text, " + DB_PRIORITY +" integer, "
//                +DB_ACCOMPLISHED + " integer, "+Alert_MUSIC+" text, "+Alert_MUSIC_NAME+" text)"
//        );
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
//        onCreate(db);
//    }
//
//
//    public long insertAlert(String title, String content, long time, int frequency,String time_created,int priority,int accomplished,String alert_music,String alert_MUSIC_NAME) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DB_COLUMN_TITLE, title);
//        values.put(DB_COLUMN_CONTENT, content);
//        values.put(DB_COLUMN_TIME, time);
//        values.put(DB_COLUMN_FREQUENCY, frequency);
//        values.put(DB_TIME_CREATED, time_created);
//        values.put(DB_PRIORITY, priority);
//        values.put(DB_ACCOMPLISHED, accomplished);
//        values.put(Alert_MUSIC, alert_music);
//        values.put(Alert_MUSIC_NAME, alert_MUSIC_NAME);
//        return db.insert(DB_TABLE_NAME, null, values);
//    }
//
//    public boolean updateAlert(Integer id, String title, String note, long time, int frequency,String time_created,int priority,int accomplished,String alert_music,String alert_MUSIC_NAME) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DB_COLUMN_CONTENT, note);
//        values.put(DB_COLUMN_TITLE, title);
//        values.put(DB_COLUMN_TIME, time);
//        values.put(DB_COLUMN_FREQUENCY, frequency);
//        values.put(DB_TIME_CREATED, time_created);
//        values.put(DB_PRIORITY, priority);
//        values.put(DB_ACCOMPLISHED, accomplished);
//        values.put(Alert_MUSIC,alert_music);
//        values.put(Alert_MUSIC_NAME, alert_MUSIC_NAME);
//        db.update(DB_TABLE_NAME, values, DB_COLUMN_ID + " = ? ",
//                new String[]{Integer.toString(id)});
//        return true;
//    }
//    //更新截止时间
//    public boolean updateTime(Integer id, long time) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DB_COLUMN_TIME, time);
//        db.update(DB_TABLE_NAME, values, DB_COLUMN_ID + " = ? ",
//                new String[]{Integer.toString(id)});
//
//        return true;
//    }
//
//    //根据id来得到cursor
//    public Cursor getItem(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " WHERE " +
//                DB_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
//    }
//
//    //根据创建时间的倒序得到cursor
//    public Cursor getAllItems() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_TIME_CREATED+ " DESC", null);
//    }
//
//    //根据截止时间的倒序得到cursor
//    public Cursor getAllItemsOrderByTime() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_COLUMN_TIME + " DESC",null);
//    }
//    //根据重要程度的倒序得到cursor
//    public Cursor getAllItemsOrderByPriority() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_PRIORITY + " DESC", null);
//    }
//    public Integer deleteItem(Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(DB_TABLE_NAME, DB_COLUMN_ID + " = ? ",
//                new String[]{Integer.toString(id)});
//    }
//
//    public boolean isEmpty() {
//        return getAllItems().getCount() == 0;
//    }
//}
