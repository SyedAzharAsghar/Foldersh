package com.example.azhar.folders;//package com.example.azhar.playerapp;
//

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VideoFolder extends AppCompatActivity {
    private File file;
    ArrayList<File> filesarray = new ArrayList<File>();
    private List<String> myList;

    private List<Integer> fileFolderTypeList = null;
    public static final int AUDIO_FILE = 3;
    private TextView pathTextView;
    private String mediapath = new String(Environment.getExternalStorageDirectory().getAbsolutePath());
    MediaMetadataRetriever metaRetriver;
    private final static String[] acceptedExtensions = {"mp3", "mp2", "mp4", "wav", "flac", "ogg", "au", "snd", "mid", "midi", "kar"
            , "mga", "aif", "aiff", "aifc", "m3u", "oga", "spx"};
    private String filename;
    private String path;

    private File list[] = null;
    MediaPlayer mediaPlayer;
    private String album, albumid;
    private String title;
    private String id;
    private ImageView imgAlbum;
    FolderAdapter folderAdapter;
    RecyclerView recyclerView;
    List<String> folders_list;
    private Bitmap bitmap;
    ArrayList<VideoSongs> videoActivitySongsList;
    Cursor videoCursorActivity;
    private int video_column_index;
    RecyclerView recyclerViewFiles;

    private Cursor videoCursor;


    ProgressDialog progressDialog;
    ProgressDialog prodialog;
    public int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private String root_sd;
    private VideoSongsAdapter videoSongsAdapter;
    private String artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_folder);
        progressDialog = new ProgressDialog(VideoFolder.this);
        progressDialog.setIndeterminate(false);


        pathTextView = (TextView) findViewById(R.id.path);
        videoActivitySongsList = new ArrayList<VideoSongs>();
        folders_list = new ArrayList<String>();
//        myList = new ArrayList<String>();
        recyclerView = (RecyclerView) findViewById(R.id.foldersrecycler);
        recyclerViewFiles = (RecyclerView) findViewById(R.id.videoRecycler);
        recyclerViewFiles.setNestedScrollingEnabled(true);
        recyclerViewFiles.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager filesLm = new LinearLayoutManager(this);
        filesLm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerViewFiles.setLayoutManager(filesLm);


//        folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
//        recyclerView.setAdapter(folderAdapter);


//         root_sd = Environment.getExternalStorageDirectory().toString();
//        Log.e("Root", root_sd);
//
//
//     /* if ( Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ) {  // we can read the External Storage...
//        list=getAllFilesOfDir(Environment.getExternalStorageDirectory());
//    }*/
//
//        pathTextView.setText("Folders");
//
//        file = new File(root_sd);
//        list = file.listFiles(new AudioFilter());
//
//        if(list != null){
//
//
//        Log.e("Size of list ", "" + list.length);
//        //LoadDirectory(root_sd);
//
//        for (int i = 0; i < list.length; i++) {
//
////            if (list[i].isHidden()) {
////                System.out.println("hidden path files.." + list[i].getAbsolutePath());
////            }
//
//            String name = list[i].getName();
//            int count = getAudioFileCount(list[i].getAbsolutePath());
//            Log.e("Count : " + count, list[i].getAbsolutePath());
//            Log.i("Counts of Data", String.valueOf(count));
////            if (count != 0)
////                myList.add(name);
//
//            Products products = new Products();
//            products.setFolders_name(name);
//            products.setTotal_count(String.valueOf(count));
//            if (count != 0)
////                myList.add(String.valueOf(products));
//                folders_list.add(name);
//        /*int count=getAllFilesOfDir(list[i]);
//        Log.e("Songs count ",""+count);
//
//        */
//
//        }
//        }
//        folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
//        recyclerView.setAdapter(folderAdapter);
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (checkStoragePermission()) {

                showData();
            } else {
                requestStoragePermission();
            }

            //            String root_sd = Environment.getExternalStorageDirectory().toString();
//            Log.e("Root", root_sd);
//
//
//            File list[] = null;
//    /* if ( Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ) {  // we can read the External Storage...
//        list=getAllFilesOfDir(Environment.getExternalStorageDirectory());
//    }*/
//
//            pathTextView.setText("Folders");
//
//            file = new File(root_sd);
//            list = file.listFiles(new AudioFilter());
//
//            Log.e("Size of list ", "" + list.length);
//            //LoadDirectory(root_sd);
//
//            folders_list.clear();
//            for (int i = 0; i < list.length; i++) {
//
////            if (list[i].isHidden()) {
////                System.out.println("hidden path files.." + list[i].getAbsolutePath());
////            }
//
//                String name = list[i].getName();
//                int count = getAudioFileCount(list[i].getAbsolutePath());
//                Log.e("Count : " + count, list[i].getAbsolutePath());
//                Log.i("Counts of Data", String.valueOf(count));
////            if (count != 0)
////                myList.add(name);
//
//                Products products = new Products();
//                products.setFolders_name(name);
//                products.setTotal_count(String.valueOf(count));
//                if (count != 0)
////                myList.add(String.valueOf(products));
//                    folders_list.add(name);
//        /*int count=getAllFilesOfDir(list[i]);
//        Log.e("Songs count ",""+count);
//
//        */
//
//            }
//            folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
//            recyclerView.setAdapter(folderAdapter);


        } else {
            if (checkStoragePermission()) {

                new GetAudioListAsynkTask(getApplicationContext()).execute();
            } else {
                requestStoragePermission();
            }
        }
//        showData();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                id = String.valueOf(position);
                                // do whatever
                                Log.i("", "OnItemClick");

                                File temp_file = new File(file, folders_list.get(position));

//                                File listFilesSongs[] = file.listFiles();
//                                folders_list.clear();
//                                if (listFilesSongs != null && listFilesSongs.length > 0) {
//
//                                    for (int i = 0; i < listFilesSongs.length; i++) {
//
//                                        if (listFilesSongs[i].isDirectory()) {
//
//                                        String name = listFilesSongs[i].getName();
//
//                                        int count = getAudioFileCount(listFilesSongs[i].getAbsolutePath());
//                                        Log.e("Count : " + count, listFilesSongs[i].getAbsolutePath());
//                                        Products products = new Products();
//                                        products.setFolders_name(name);
////                                        products.setTotal_count(String.valueOf(count));
//                                        if (count != 0) {
//                                            folders_list.add(name);
//                                        }
//                                        System.out.println("Filename" + name);
//                                        String fullPAth = file.toString();
//                                        String[] str = fullPAth.split("/");
//                                        String lastOne = str[str.length - 1];
//                                        System.out.println(lastOne);
//                                        pathTextView.setText(lastOne);
//////                                    System.out.println(Arrays.toString(separated));
////
////
//////                                    Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_LONG).show();
////
////
////                                    }
//                                    folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
//                                    recyclerView.setAdapter(folderAdapter);
//                                        } else {
//                                            if (listFilesSongs[i].getName().toLowerCase().endsWith(".mp4")
//                                                || listFilesSongs[i].getName().toUpperCase().endsWith(".mp4")) {
//
//                                            System.out.println("SOngs Name = " + listFilesSongs[i].getName());
//                                            int countc = getAudioFileCount(listFilesSongs[i].getName());
//                                            System.out.println(countc);
//                                            getFiles(listFilesSongs[i].getName());
//
//
//                                        }
//                                        }
//                                    videoSongsAdapter = new VideoSongsAdapter(VideoFolder.this, videoActivitySongsList);
//                                    recyclerViewFiles.setAdapter(videoSongsAdapter);
//                                        }
//
//                                    }
//                                }

                                if (!temp_file.isFile()) {
                                    System.out.println("I am not a file " + temp_file);
                                    //                                    List<File> files = getListFiles(file);
//                                    System.out.println("Filename or DirectoryName" + filesarray.addAll(files));
                                    file = new File(file, folders_list.get(position));
                                    File list[] = file.listFiles(new AudioFilter());
                                    folders_list.clear();
                                    videoActivitySongsList.clear();
                                    for (int i = 0; i < list.length; i++) {

                                        if (list[i].getName().toLowerCase().endsWith("mp4") ||
                                                list[i].getName().toLowerCase().endsWith("mKv")) {

                                            System.out.println("SOngs Name = " + list[i].getName());
                                            int countc = getAudioFileCount(list[i].getName());
                                            System.out.println(countc);
                                            getFiles(list[i].getName());


                                            videoSongsAdapter = new VideoSongsAdapter(VideoFolder.this, videoActivitySongsList);
                                            recyclerViewFiles.setAdapter(videoSongsAdapter);

                                        } else {

                                            String name = list[i].getName();
                                            path = list[i].getPath();
                                            int count = getAudioFileCount(list[i].getAbsolutePath());
                                            Log.e("Count : " + count, list[i].getAbsolutePath());
                                            Products products = new Products();
                                            products.setFolders_name(name);
                                            products.setTotal_count("Total :"+String.valueOf(count));
                                            if (count != 0) {
                                                folders_list.add(name);
                                            }
                                            System.out.println("Filename" + name);
                                            String fullPAth = file.toString();
                                            String[] str = fullPAth.split("/");
                                            String lastOne = str[str.length - 1];
                                            System.out.println(lastOne);
                                            pathTextView.setText(lastOne);
//                                    System.out.println(Arrays.toString(separated));


//                                    Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_LONG).show();
                                            folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
                                            recyclerView.setAdapter(folderAdapter);
                                        }


                                    }

//                                    videoSongsAdapter = new VideoSongsAdapter(VideoFolder.this, videoActivitySongsList);
//                                    recyclerViewFiles.setAdapter(videoSongsAdapter);


                                }

//                                if (temp_file.isFile()) {
//                                    System.out.println("I am  a file " + temp_file);
//                                    String parent = temp_file.getParent().toString();
//                                    file = new File(parent);
////                                    file = new File(file, folders_list.get(position));
//                                    File list[] = file.listFiles(new AudioFilter());
//
////
//                                    videoActivitySongsList.clear();
//                                    for (int i = 0; i < list.length; i++) {
//
//                                        if (list[i].getName().toLowerCase().endsWith(".mp4")
//                                                || list[i].getName().toUpperCase().endsWith(".mp4")) {
//
//                                            System.out.println("SOngs Name = " + list[i].getName());
//                                            int countc = getAudioFileCount(list[i].getName());
//                                            System.out.println(countc);
//                                            getFiles(list[i].getName());
//
//
//                                        }}
//                                    videoSongsAdapter = new VideoSongsAdapter(VideoFolder.this, videoActivitySongsList);
//                                    recyclerViewFiles.setAdapter(videoSongsAdapter);
//                                }
//                                   else if(temp_file.isFile()){
//                                        file = new File(file, folders_list.get(position));
//                                        File list[] = file.listFiles(new AudioFilter());
//                                        folders_list.clear();
////                                    videoActivitySongsList.clear();
//
//                                        for (int i = 0; i < list.length; i++) {
//                                            if (list[i].getName().toLowerCase().endsWith(".mp4")
//                                                    || list[i].getName().toUpperCase().endsWith(".mp4")) {
//
//                                                System.out.println("SOngs Name = " + list[i].getName());
//                                                int countc = getAudioFileCount(list[i].getName());
//                                                System.out.println(countc);
//                                                getFiles(list[i].getName());
//
//                                                videoSongsAdapter = new VideoSongsAdapter(VideoFolder.this, videoActivitySongsList);
//                                                recyclerViewFiles.setAdapter(videoSongsAdapter);
//                                            }
//                                        }
//                                    }
                                else {
//                                    System.out.println("I am  a file " + temp_file);

//                                    Toast.makeText(VideoFolder.this, SongPath + "Ready to Play", Toast.LENGTH_SHORT).show();


                                }


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
        );

//        recyclerViewFiles.addOnItemTouchListener(
//                new RecyclerItemClickListener(getApplicationContext(), recyclerViewFiles,
//                        new RecyclerItemClickListener.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//
//
////                                Log.i("", "position" + position);
////                                if (videoCursorActivity != null || filename != null) {
////
////
////                                    video_column_index = videoCursorActivity
////                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
////                                    videoCursorActivity.moveToPosition(position);
////                                    filename = videoCursorActivity.getString(video_column_index);
////                                      title = videoCursorActivity.getString(
////                                            videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
////                                     id = String.valueOf(position);
////                                     artist = videoCursorActivity.getString(
////                                            videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
//////                                    Intent intent = new Intent(VideoFolder.this, VideoDetailActivityFliper.class);
//////                                    intent.putExtra("videofilename", filename);
//////                                    intent.putExtra("title", title);
//////                                    intent.putExtra("id", id);
//////                                    intent.putExtra("Showbuttons", false);
//////                                    intent.putExtra("ShowNoti", true);
////                                 Log.i("","Details" + filename +"\n"+title);
////                                    startActivity(intent);
//
////                                }
//
//                            }
//
//                            @Override
//                            public void onLongItemClick(View view, int position) {
//
//                            }
//                        }));
    }

    private void showData() {
        root_sd = Environment.getExternalStorageDirectory().toString();
        Log.e("Root", root_sd);



    /* if ( Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ) {  // we can read the External Storage...
        list=getAllFilesOfDir(Environment.getExternalStorageDirectory());
    }*/

        pathTextView.setText("Folders");
        file = new File(root_sd);
        list = file.listFiles(new AudioFilter());

        Log.e("Size of list ", "" + list.length);
        //LoadDirectory(root_sd);

        folders_list.clear();

        for (int i = 0; i < list.length; i++) {

//            if (list[i].isHidden()) {
//                System.out.println("hidden path files.." + list[i].getAbsolutePath());
//            }

            String name = list[i].getName();
            int count = getAudioFileCount(list[i].getAbsolutePath());
            Log.e("Count : " + count, list[i].getAbsolutePath());
            Log.i("Counts of Data", String.valueOf(count));
//            if (count != 0)
//                myList.add(name);

            Products products = new Products();
            products.setFolders_name(name);
            products.setTotal_count(String.valueOf(count));
            if (count != 0)
//                myList.add(String.valueOf(products));
                folders_list.add(name);
        /*int count=getAllFilesOfDir(list[i]);
        Log.e("Songs count ",""+count);

        */

        }
        folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
        recyclerView.setAdapter(folderAdapter);
//        folderAdapter.notifyDataSetChanged();
    }


    private int getAudioFileCount(String dirPath) {


        String selection = MediaStore.Video.Media.DATA + " like ?";
        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.ALBUM};
        String[] selectionArgs = {dirPath + "%"};
        Cursor cursor = managedQuery(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        return cursor.getCount();
    }

    private void getFiles(String songsName) {


        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] projection = {MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.DESCRIPTION,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE};

        String[] selectionArgs = {"%" + songsName + "%"};
//        String[] selectionArgs=new String[]{"%Swag-Se-Swagat-Song--Tiger-Zinda-Hai--Salman-Khan--Katrina-Kaif.mp4%"};

        System.out.println("DIRECTORY" + Arrays.toString(selectionArgs));
        videoCursorActivity =  getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
//        System.out.println("MAPing "+dirPath + "=media="+ MediaStore.Video.Media.EXTERNAL_CONTENT_URI );

        int totalvideoscount = videoCursorActivity.getCount();
        folders_list.clear();
        while (videoCursorActivity.moveToNext()) {
            filename = videoCursorActivity.getString(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            title = videoCursorActivity.getString(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
            String dura = videoCursorActivity.getString(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            String artist = videoCursorActivity.getString(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
            album = videoCursorActivity.getString(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
            String desc = videoCursorActivity.getString(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.DESCRIPTION));
            String res = videoCursorActivity.getString(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
            int size = videoCursorActivity.getInt(
                    videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
            int videoId = videoCursorActivity.getInt(videoCursorActivity.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
            Uri albumArtUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId);
            System.out.println(albumArtUri);

            System.out.println("Total SOngs" + totalvideoscount);
            System.out.println("Title" + title);


            VideoSongs songs = new VideoSongs();
            songs.setData(filename);
            songs.setImage(albumArtUri.toString());
            songs.setDuration(milliSecondsToTimer(Long.parseLong(dura)));
            songs.setName(title);
            songs.setArtist(artist);
            songs.setSize(getFileSize(size));
            videoActivitySongsList.add(songs);
        }


    }


    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

// Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
// Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

// Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

// return timer string
        return finalTimerString;
    }

    @Override
    public void onBackPressed() {
        try {
            String parent = file.getParent().toString();


            file = new File(parent);
            File list[] = file.listFiles(new AudioFilter());

            folders_list.clear();
            videoActivitySongsList.clear();

            for (int i = 0; i < list.length; i++) {
                String name = list[i].getName();
                int count = getAudioFileCount(list[i].getAbsolutePath());
                Log.e("Count : " + count, list[i].getAbsolutePath());

                String nullPath = list[i].getAbsolutePath();


                if (nullPath.equals("/storage/emulated/0")) {
                    folders_list.clear();
                    finish();
                    Log.e("Count Finish: ", nullPath);
                }
                if (count != 0)
                    folders_list.add(name);
            /*int count=getAllFilesOfDir(list[i]);
            Log.e("Songs count ",""+count);
            if(count!=0)*/

            }


            pathTextView.setText("Folders");
            //  Toast.makeText(getApplicationContext(), parent,          Toast.LENGTH_LONG).show();
//            listView.setAdapter(new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, myList));
            folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
            recyclerView.setAdapter(folderAdapter);

        } catch (Exception e) {
            finish();
        }


    }


    // class to limit the choices shown when browsing to SD card to media files
    public class AudioFilter implements FileFilter {

        // only want to see the following audio file types
        private String[] extension = {"mp3", "mp2", "mkv", "mp4", "wav", "flac", "ogg", "au", "snd", "mid", "midi", "kar"
                , "mga", "aif", "aiff", "aifc", "m3u", "oga", "spx"};

        @Override
        public boolean accept(File pathname) {

            // if we are looking at a directory/file that's not hidden we want to see it so return TRUE
            if ((pathname.isDirectory() || pathname.isFile()) && !pathname.isHidden()) {
                return true;
            }

            // loops through and determines the extension of all files in the directory
            // returns TRUE to only show the audio files defined in the String[] extension array
            for (String ext : extension) {
                if (pathname.getName().toLowerCase().endsWith(ext)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    private boolean checkStoragePermission() {
        return ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;


    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    showData();
                } else {
                    new GetAudioListAsynkTask(getApplicationContext()).execute();

                }
                Log.d("After Permission", "Should be read");
            } else {
                checkStoragePermission();

                Log.d("After Permission", "Not reading");
            }
        }

    }




    private class GetAudioListAsynkTask extends AsyncTask<Void, Void, Boolean> {
        private Context context;

        @Override
        protected void onPreExecute() {
            progressDialog.setCancelable(false);
            prodialog = progressDialog.show(VideoFolder.this, "", "Loading....", false);
        }

        public GetAudioListAsynkTask(Context context) {

            this.context = context;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {

            try {

                showData();
                prodialog.dismiss();


                return true;
            } catch (Exception e) {
                return false;

            }

        }

        @Override
        protected void onPostExecute(Boolean result) {

//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }

            prodialog.dismiss();
//            folderAdapter = new FolderAdapter(VideoFolder.this, folders_list);
//            recyclerView.setAdapter(folderAdapter);
            folderAdapter.notifyDataSetChanged();

        }

    }

    private class GetSongsFiles extends AsyncTask<String, Void, Void> {
        private Context context;



        public GetSongsFiles(Context context) {

            this.context = context;
        }


        @Override
        protected Void doInBackground(String... params) {


            getFiles(params[0]);
            return null;

        }
    }


}