package com.ioqse.shareplay;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivityFragment extends Fragment
{
    private SongList songList;

    public MainActivityFragment()
    {
        MainActivity.frag = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        songList = (SongList) v.findViewById(R.id.songList);
        return v;
    }

    public void OnSongSelected(Intent intent)
    {
        Uri uri = intent.getData();
        String scheme = uri.getScheme();

        if (scheme.equals("file"))
        {
            //title = uri.getLastPathSegment();
            Log.d("a", "WRONG SCHEEEEEEEEEEEEEEMEEEEEEEEEEEEEEE");
        }
        else if (scheme.equals("content"))
        {
            String[] proj = { MediaStore.Audio.Media.TITLE,
                              MediaStore.Audio.Media.DURATION };

            Cursor cursor = MainActivity.context.getContentResolver().
                                query(uri, proj, null, null, null);

            if (cursor != null && cursor.getCount() != 0)
            {
                cursor.moveToFirst();

                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)); //secs

                Song song = new Song(title, duration);
                songList.AddSong(song);
            }


            if (cursor != null) cursor.close();
        }
    }
}
