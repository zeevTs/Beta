package com.example.beta1.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.beta1.Objs.Animal;
import com.example.beta1.Objs.User;
import com.example.beta1.R;

import java.util.ArrayList;

public class AnimalAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Animal> animalList;
    private LayoutInflater inflater;

    public AnimalAdapter(Context context , ArrayList<Animal> animalList){
        this.context = context;
        this.animalList = animalList;
        this.inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return animalList.size();
    }

    @Override
    public Object getItem(int position) {
        return animalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.custom_animal_layout,null);
        LinearLayout llAnimal = view.findViewById(R.id.llAnimal);
        ImageView iVanimal = view.findViewById(R.id.iVanimal);
        TextView tVage= view.findViewById(R.id.tVage);
        TextView tVname= view.findViewById(R.id.tVname);
        tVname.setText(String.valueOf(animalList.get(position).getName()));
        tVage.setText("age: "+String.valueOf(animalList.get(position).getAge()));
        Glide.with(context)
                .asBitmap()
                .load(animalList.get(position).getUrl())
                .into(iVanimal);
        return view;
    }


}
