package com.example.jokesapp2.categories;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jokesapp2.R;

import javax.inject.Inject;

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
    private String[] categoriesData;

    /**
     * Click listener for recyclerview items
     */
    private RecyclerItemClickListener onItemClickListener;

    /**
     * Photo array of category items
     */
    private final Drawable[] photos;

    @Inject
    public CategoryAdapter(Context context, String[] categories, RecyclerItemClickListener onItemClickListener) {
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
        viewHolder.categoryText.setText(categoriesData[i]);
        Glide.with(context).load(photos[i % photos.length]).into(viewHolder.categoryImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(categoriesData[i]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesData.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView categoryImage;
        private TextView categoryText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.text_category);
            categoryImage = itemView.findViewById(R.id.tile_image);
        }
    }
}

