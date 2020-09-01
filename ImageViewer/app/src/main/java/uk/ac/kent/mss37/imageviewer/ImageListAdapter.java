package uk.ac.kent.mss37.imageviewer;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import static uk.ac.kent.mss37.imageviewer.R.id.progressBar;

/**
 * Created by Lifeowner on 16/11/2017.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    //variables
    private Context context;
    public ArrayList<ImageInfo> imageList = new ArrayList<ImageInfo>();
    private ProgressBar progressBar1;

    public ImageListAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView imageTitle;
        public NetworkImageView mainImage;


        View.OnClickListener imageClick = new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);

                int position = ViewHolder.this.getLayoutPosition();

                // Add Parameters
                intent.putExtra("PHOTO_POSITION", position);

                context.startActivity(intent);

            }
        };


        public ViewHolder(View itemView) {
            super(itemView);
            //initialize varaibles
            imageTitle = (TextView) itemView.findViewById(R.id.imageTitle);
            mainImage = (NetworkImageView) itemView.findViewById(R.id.mainImage);
            mainImage.setOnClickListener(imageClick);
            progressBar1 = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cell_image_card, parent, false);
        ImageListAdapter.ViewHolder vh = new ImageListAdapter.ViewHolder(v);
        //make progress bar visible
        progressBar1.setVisibility(View.VISIBLE);
        return vh;



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageInfo imageInfo = imageList.get(position);

        holder.imageTitle.setText(imageInfo.title);

        holder.mainImage.setImageUrl(imageInfo.url_m, NetworkMgr.getInstance(context).imageLoader);



    }



    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
