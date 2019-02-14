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

/**
 * RecyclerView adapter for displaying jokes from the database.
 */
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

    /**
     * Binds joke View data to Recyclerview item
     * @param holder of item Views
     * @param position of the item
     */
    @Override
    public void onBindViewHolder(@NonNull JokesAdapter.ViewHolder holder, int position) {
        holder.jokeTextView.setText(jokesData.get(position));
    }

    /**
     * Gets the size of jokes list
     * @return Number of jokes
     */
    @Override
    public int getItemCount() {
        return jokesData.size();
    }

    /**
     * Replaces current Adapter data with new one from parameter.
     * @param jokesData List of jokes
     */
    void replaceData(List<String> jokesData) {
        this.jokesData.clear();
        this.jokesData.addAll(jokesData);
        notifyDataSetChanged();
    }

    /**
     * Clears Adapter data
     */
    public void clearData() {
        this.jokesData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * View for displaying joke
         */
        private TextView jokeTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            jokeTextView = itemView.findViewById(R.id.text_jokedb);
        }
    }
}
