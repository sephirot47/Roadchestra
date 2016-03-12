package com.ioqse.shareplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter  extends ArrayAdapter<Song>
{
    public SongAdapter(Context context, ArrayList<Song> songs)
    {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Song song = (Song) this.getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_song, parent, false);
        }

        TextView textSongName = (TextView) convertView.findViewById(R.id.textSongName);

        textSongName.setText(song.name);

        return convertView;
    }
}