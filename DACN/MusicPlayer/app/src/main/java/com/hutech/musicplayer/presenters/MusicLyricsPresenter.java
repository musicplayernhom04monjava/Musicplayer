package com.hutech.musicplayer.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hutech.musicplayer.R;
import com.hutech.musicplayer.models.LrcRow;
import com.hutech.musicplayer.views.MusicLyricsFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class MusicLyricsPresenter {
    View view;
    IMusicLyrics iMusicLyrics;
    final long ONE_MEGABYTE = 1024*1024;
    public MusicLyricsPresenter(){}
    public MusicLyricsPresenter(View v, IMusicLyrics iMusicLyrics)
    {
        this.view = v;
        this.iMusicLyrics = iMusicLyrics;
    }
    public void getFromAssets(String fileName)
    {
        try {
            String result = "";
            /*Toast.makeText(view.getContext(),"fileLrc: " + fileName,
                    Toast.LENGTH_SHORT).show();*/
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileName);
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    String lyric = new String(bytes);
                    iMusicLyrics.getLyrics(lyric);
                    //Log.d("MusicLrcPresenter","lyric = "+lyric);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("MusicLrcPresenter","Exception: "+e);
                }
            });
            //InputStreamReader inputStreamReader = new InputStreamReader(view.getResources().openRawResource(fileName));
            /*BufferedReader bufferedReader = new BufferedReader();
            String line = "";
            String Result = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                if(!line.trim().equals(""))
                {
                    Result = Result + line + "\r\n";
                }
            }
            Log.d("MusicLrcPresenter","Result: "+ Result);
            bufferedReader.close();*/
            //return result;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //return "false";
    }

    public List<LrcRow> getLrcRows(String rawLrc)
    {
        List<LrcRow> rows = new ArrayList<LrcRow>();
        if(rawLrc == null || rawLrc.length() == 0)
        {
            return null;
        }
        StringReader reader = new StringReader(rawLrc);
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        try{

            do{
                line = br.readLine();
                //Log.d("MusicLrcPresenter","lrc raw line"+ line);
                if (line != null && line.length() > 0)
                {
                    List<LrcRow> lrcRows = LrcRow.createRows(line);
                    if(lrcRows != null && lrcRows.size() > 0)
                    {
                        for (LrcRow row:lrcRows) {
                            rows.add(row);
                            //Log.d("MusicLrcPresenter","lrc row "+ row.toString());
                        }

                    }
                }
            }while (line != null);
            if (rows.size() > 0)
            {
                // sort by time:
                Collections.sort(rows);
            }
        }catch (Exception e){
            Log.d("MusicLrcPresenter","parse fail:"+e.getMessage());
        }
        finally {
            try{
                br.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
            reader.close();
        }
        return rows;
    }
    public void getLrc(String lrc)
    {
        //String lrc = getFromAssets(fileName);
        List<LrcRow> rows = getLrcRows(lrc);
        iMusicLyrics.getLyrics(rows);
    }
}
