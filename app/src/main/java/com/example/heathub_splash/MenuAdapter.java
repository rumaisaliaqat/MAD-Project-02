package com.example.heathub_splash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuAdapter extends ArrayAdapter<String> {

    Context context;
    String[] titles;
    int[] images;

    public MenuAdapter(Context c, String[] titles, int[] images) {
        super(c, R.layout.list_item, titles); // <- row layout ka name
        this.context = c;
        this.titles = titles;
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_item, parent, false);

        ImageView icon = row.findViewById(R.id.itemIcon);
        TextView title = row.findViewById(R.id.itemText);

        icon.setImageResource(images[position]);
        title.setText(titles[position]);

        return row;
    }
}
