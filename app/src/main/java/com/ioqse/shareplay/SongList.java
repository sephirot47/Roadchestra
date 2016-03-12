package com.ioqse.shareplay;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class SongList extends ListView
{
    private ArrayList<Song> songs;
    private SongAdapter songAdapter;

    public SongList(Context context)
    {
        super(context);
        Init(context);
    }

    public SongList(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        Init(context);
    }

    public SongList(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        Init(context);
    }

    public void Init(Context context)
    {
        songs = new ArrayList<Song>();

        songAdapter = new SongAdapter(context, songs);
        setAdapter(songAdapter);
    }

    public void AddSong(Song s)
    {
        songs.add(s);
        songAdapter.notifyDataSetChanged();
    }

}
