package com.hutech.musicplayer.managers;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.models.User;
import com.hutech.musicplayer.presenters.ContractDetailMusic;
import com.hutech.musicplayer.presenters.IFirebase;
import com.hutech.musicplayer.views.IDetailMusic;
import com.hutech.musicplayer.views.ISignIn;
import com.hutech.musicplayer.views.ISignUpActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseManager {
    public interface ICheckExist {
        void isCheck(boolean status);
    }
    private static  FirebaseManager sharedinstance;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    private IFirebase mifirebase;
    private ISignUpActivity iSignUpActivity;
    private ICheckExist checkExist;
    //private ContractDetailMusic contractDetailMusic;
    private String KEY_EMAIL = "email";
    private String KEY_FULL_NAME = "fullname";
    private String KEY_PASSWORD = "password";
    public static FirebaseManager getInstance(IFirebase mifirebase) {
            sharedinstance = new FirebaseManager(mifirebase);
        return sharedinstance;
    }
//    public static FirebaseManager getInstance(ContractDetailMusic contractDetailMusic)
//    {
//        if (sharedinstance == null){
//            sharedinstance = new FirebaseManager(contractDetailMusic);
//        }
//        return sharedinstance;
//    }
    public static FirebaseManager getInstance() {
        if (sharedinstance == null){
            sharedinstance = new FirebaseManager();
        }
        return sharedinstance;
    }

    public FirebaseManager(){ }

    public FirebaseManager(ISignUpActivity iSignUpActivity){
        this.iSignUpActivity = iSignUpActivity;
    }

//    public FirebaseManager(ContractDetailMusic contractDetailMusic){
//        this.contractDetailMusic = contractDetailMusic;
//    }
    public FirebaseManager(IFirebase mifirebase){
    this.mifirebase = mifirebase;
}

    
    public void loadSongs()
    {
        reference.child("songs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Song song = new Song();
                    List<Song> songs = new ArrayList<>();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        Log.d("FirebaseManager","data" + data.getValue());
                        song = data.getValue(Song.class);
                        Log.d("Firebase",String.format("title: %s, singer: %s, pathlrc: %s, pathmp3: %s",song.getTitle(), song.getSinger()
                                ,song.getPathlrc(),song.getPathMp3()));
                        songs.add(song);

                    }
                    mifirebase.fireBaseCallBack(songs);
//                    if(contractDetailMusic != null)
//                    {
//                        contractDetailMusic.getSongs(songs);
//                    }
                    /*song = dataSnapshot.getValue(Song.class);
                    Log.d("Firebase",String.format("title: %s, singer: %s, pathlrc: %s, pathmp3: %s",song.getTitle(), song.getSinger()
                    ,song.getPathLyric(),song.getPathMp3()));*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    

//    public void searchAlbum(String query){
//        reference.child("songs").orderByChild("title").equalTo(query).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("FirebaseManager","Search: "+dataSnapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
