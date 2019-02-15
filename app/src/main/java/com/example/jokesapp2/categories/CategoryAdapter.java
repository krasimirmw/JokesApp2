package com.example.jokesapp2.categories;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jokesapp2.R;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView Adapter for categories.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    /**
     * Application context
     */
    private Context context;

    /**
     * Array of category names
     */
    private List<String> categoriesData;

    /**
     * Click listener for recyclerview items
     */
    private RecyclerItemClickListener onItemClickListener;

    /**
     * Photo array of category items
     */
    private final Drawable[] photos;

    public CategoryAdapter(Context context, List<String> categories, RecyclerItemClickListener onItemClickListener) {
        this.context = context;

        this.categoriesData = categories;
        this.onItemClickListener = onItemClickListener;
        Resources resources = context.getResources();
        TypedArray typedArray = resources.obtainTypedArray(R.array.category_photo);
        photos = new Drawable[typedArray.length()];

        for (int i = 0; i < photos.length; i++) {
            photos[i] = typedArray.getDrawable(i);
        }
        typedArray.recycle();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_tile, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.categoryText.setText(categoriesData.get(i));
        Glide.with(context).load(photos[i % photos.length]).into(viewHolder.categoryImage);

        viewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(categoriesData.get(i)));
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }

    void replaceData(String[] categoriesArray) {
        this.categoriesData.clear();
        this.categoriesData.addAll(Arrays.asList(categoriesArray));
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImage;
        private TextView categoryText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.text_category);
            categoryImage = itemView.findViewById(R.id.tile_image);
        }
    }
}

