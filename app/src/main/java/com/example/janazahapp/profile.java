package com.example.janazahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Profile extends AppCompatActivity {

    TextView tvPseudo;
    TextView emptyTv;

    FirebaseAuth mFirebaseAuth;
    ImageView profileImage;
    ImageView backPic;


    //for the event of the connected user
    RecyclerView card;
    MyProfileAdapter myAdapter;
    SharedPreferences preferences;
    ArrayList<Model> models = new ArrayList<>();
    //DATABASE
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFireBaseUser = mFirebaseAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Profile.this);

        tvPseudo = findViewById(R.id.tvPseudo);
        emptyTv=findViewById(R.id.emptyTv);
        profileImage = findViewById(R.id.pp);
        backPic = findViewById(R.id.backPic);
        tvPseudo.setTextColor(Color.parseColor("#FFFFFF"));
        tvPseudo.setText(account.getDisplayName());

        String photoUrl = account.getPhotoUrl().toString(); //not okay, value == null
        Picasso.with(this).load(photoUrl).into(profileImage);

        //for the events of the connected user
        card = findViewById(R.id.PrayersEvents);
        card.setNestedScrollingEnabled(false);

        getMyList();

    }


    @Override
    public boolean  onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return  true;
    }
    private void getMyList() {
        //DATABASE
        reff= FirebaseDatabase.getInstance().getReference().child("Event");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Profile.this);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("authorName").getValue().toString().equals(account.getDisplayName())){
                        // extraction db
                        String authorName = snapshot.child("authorName").getValue().toString();
                        String prayer = snapshot.child("choosePrayer").getValue().toString();
                        String description = snapshot.child("descriptionPrayer").getValue().toString();
                        String nameDead  = snapshot.child("nameDead").getValue().toString();
                        String mosqueAdress = snapshot.child("chooseMosque").getValue().toString();
                        String id=snapshot.getKey();
                        Log.d("profile", "id: "+ id);

                        Log.d("Files", " données de Firebase " + authorName  + " " + prayer + " " + description + " " + nameDead +" "+mosqueAdress );

                        // remplissage Arraylist models
                        Model e = new Model();
                        e.setName(authorName);
                        e.setMosque(mosqueAdress);
                        e.setPrayer(prayer);
                        e.setNbParticipants("0");
                        e.setDescription(description);
                        e.setId(id);
                        models.add(e);
                    }
                }
                if(models.size()==0){
                    emptyTv.setText("You don't have any event at the moment.");
                    emptyTv.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
                }
                // verification ArrayList models de remplissage
                for (Model object: models) {
                    //   System.out.println(object.getName());
                    Log.d("Files", " Verification Arraylist models "+ object.getName());

                }

                card.setLayoutManager(new LinearLayoutManager(Profile.this));
                myAdapter = new MyProfileAdapter(Profile.this, models);
                card.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Log.d("Files", "  HUEHUEUEHUEHUEH ");

        }
}
