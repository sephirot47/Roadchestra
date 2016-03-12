package com.ioqse.shareplay;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivityFragment extends Fragment
{
    private SongList songList;
    private TextView textAddASongToTheQueue;

    public MainActivityFragment()
    {
        MainActivity.frag = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        textAddASongToTheQueue = (TextView) v.findViewById(R.id.textAddASongToTheQueue);
        songList = (SongList) v.findViewById(R.id.songList);
        return v;
    }

    public void OnSongSelected(Intent intent)
    {
        Uri uri = intent.getData();
        String scheme = uri.getScheme();

        String title = "";
        int duration = 0;

        if (scheme.equals("file"))
        {
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

                title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)); //secs
                duration /= 1000;

                Song song = new Song(title, duration);
                songList.AddSong(song);

                Toast.makeText(MainActivity.context, "Sending...", Toast.LENGTH_LONG);
            }


            if (cursor != null) cursor.close();
        }

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(MediaStore.Audio.Media.CONTENT_TYPE);
        sharingIntent.setComponent(new ComponentName("com.android.bluetooth", "com.android.bluetooth.opp.BluetoothOppLauncherActivity"));
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivityForResult(sharingIntent, 2);
        textAddASongToTheQueue.setVisibility(View.INVISIBLE);
    }
}
