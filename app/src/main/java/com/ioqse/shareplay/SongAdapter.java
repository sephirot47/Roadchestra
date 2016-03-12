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
        TextView textSongDuration = (TextView) convertView.findViewById(R.id.textSongDuration);

        textSongName.setText(song.name);

        int minutes = (song.duration % 3600) / 60;
        int seconds = song.duration % 60;
        String durationString = "";
        if(minutes < 10) durationString = String.format("%01d:%02d", minutes, seconds);
        else durationString = String.format("%02d:%02d", minutes, seconds);

        textSongDuration.setText(durationString);

        textSongName.setSelected(true);
        return convertView;
    }
}