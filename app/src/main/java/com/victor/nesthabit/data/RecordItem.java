package com.victor.nesthabit.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by victor on 7/4/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class RecordItem extends DataSupport implements Parcelable{
    private long id;
    private String name;
    private String file_path;
    private int length;
    private long time_added;

    public RecordItem() {
    }
    public RecordItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        file_path = in.readString();
        length = in.readInt();
        time_added = in.readLong();
    }
    public static final Parcelable.Creator<RecordItem> CREATOR = new Parcelable.Creator<RecordItem>() {
        public RecordItem createFromParcel(Parcel in) {
            return new RecordItem(in);
        }

        public RecordItem[] newArray(int size) {
            return new RecordItem[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt((int)id);
        dest.writeString(name);
        dest.writeString(file_path);
        dest.writeInt(length);
        dest.writeLong(time_added);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getTime_added() {
        return time_added;
    }

    public void setTime_added(long time_added) {
        this.time_added = time_added;
    }
}
