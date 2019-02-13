package com.example.jokesapp2.jokedetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jokesapp2.R;
import com.example.jokesapp2.model.Joke;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

    private ArrayList<String> jokesData;

    JokesAdapter(ArrayList<String> jokesData) {
        this.jokesData = jokesData;
    }

    @NonNull
    @Override
    public JokesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokesAdapter.ViewHolder holder, int position) {
        holder.jokeTextView.setText(jokesData.get(position));
    }

    @Override
    public int getItemCount() {
        return jokesData.size();
    }

    void replaceData(List<String> jokesData) {
        this.jokesData.clear();
        this.jokesData.addAll(jokesData);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.jokesData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView jokeTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            jokeTextView = itemView.findViewById(R.id.text_jokedb);
        }
    }
}
