package com.example.azhar.folders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by azhar on 10/11/2017.
 */

public class VideoSongsAdapter extends RecyclerView.Adapter<VideoSongsAdapter.myView>   {

    private LayoutInflater inflater;
    private Context context;
    VideoSongs songsClass;
    ArrayList<VideoSongs> list;
     static String nameofSOngs;
    static Bitmap sourceofImage;
    static int pos;
    private int lastPosition = -1;


    //
    public VideoSongsAdapter(Context context, ArrayList<VideoSongs> arraylist    ) {
        inflater = LayoutInflater.from(context);
        this.list = arraylist;
         this.context = context;


    }

    @Override
    public myView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_video, parent, false);
        return new myView(view);

    }


    @Override
    public void onBindViewHolder(final myView holder, final int position) {

        lastPosition = position;

        Log.d(getClass().getSimpleName(), "#" + position);
        holder.bind(position);

        songsClass = list.get(position);
        holder.title.setText(songsClass.getName());
//        holder.proimage.setImageBitmap(songsClass.getImage());
        holder.sizeofVideo.setText(songsClass.getSize());
            Picasso.with(context).load(String.valueOf(songsClass.getImage())).skipMemoryCache().placeholder(R.drawable.head).error(R.drawable.head).fit().centerInside().into(holder.proimage);


        //        holder.title.setText(songs.get(position).get("displayname"));
//        holder.artist.setText(songs.get(position).get("artist"));
        holder.duration.setText(songsClass.getDuration());
//        holder.title.setText(songs.get(position).get("title"));
//        String cove = songs.get(position).get("albumart");
//        Bitmap art = BitmapFactory.decodeFile(cove);
//        holder.proimage.setImageBitmap(art);
//         Picasso.with(context).load(String.valueOf(songsClass.getImage()))
//                .placeholder(com.example.azhar.playerapp.R.drawable.ic_android_black_24dp).into(holder.proimage);
//        com.bumptech.glide.Glide.with(context).load(songsClass.getImage()).placeholder(android.R.drawable.list_selector_background).into(holder.proimage);
//        holder.proimage.setImageResource(songs.get(position).get(""));

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//
////                holder.title.setText(list.get(position).getName());
////                holder.proimage.setImageBitmap(list.get(position).getImage());
////                Context context = view.getContext();
////                Intent intent = new Intent(context, AudioDetail.class);
////
////                intent.putExtra("name",list.get(position).getName());
////                intent.putExtra("imageId",list.get(position).getImage());
////                context.startActivity(intent);
//            }
//        });


        holder.setItemClickListener(new onItemClickListener() {
            @Override
            public void onClick(View view, int itemPos) {
                Log.i("","onCLick" + itemPos);
                Log.i("NAME",list.get(itemPos).getName());
                Log.i("DATA",list.get(itemPos).getData());
                Log.i("DURATION",list.get(itemPos).getDuration());
                Log.i("DURATION",list.toString());
            }});
    }
    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();

    }



    public class myView extends RecyclerView.ViewHolder  implements View.OnClickListener  {
        public TextView title,duration,artist,id,sizeofVideo;

        //        TextView proname;
        ImageView proimage;
        onItemClickListener itemClickListener;
        public myView(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.last_text);
            duration = (TextView) itemView.findViewById(R.id.last_text_time);
            sizeofVideo = (TextView) itemView.findViewById(R.id.sizeOfSons);

            proimage = (ImageView) itemView.findViewById(R.id.past_icon);
//            proname = (TextView) itemView.findViewById(R.id.last_text);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(itemView.getContext(),AudioDetail.class);
//                    intent.putExtra("name",title.getText());
//                    intent.putExtra("imageId",);
//                    itemView.getContext().startActivity(intent);
//                }
//            });
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            title.setText(String.valueOf(listIndex));
        }
        @Override
        public void onClick(View v) {
            this.itemClickListener.onClick(v, getLayoutPosition());
        }

        public void setItemClickListener(onItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }




}
