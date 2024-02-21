package com.example.isustain_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class reuse_bottle extends AppCompatActivity {
    Button buzz_btn;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    Toolbar toolbar;
    Dialog dialog;

    private int point = 2;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuse_bottle);

        dialog = new Dialog(this);
        buzz_btn = findViewById(R.id.buzz_btn_bottle);
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buzz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPointss();
            }
        });

    }

    private void getPointss() {
        dialog.setContentView(R.layout.congratz_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.img_close);
        Button btnOk = dialog.findViewById(R.id.buttonOK);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               // Toast.makeText(reuse_bottle.this, "Dialog Close", Toast.LENGTH_SHORT).show();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //Toast.makeText(reuse_bottle.this, "Button OK", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
       // point = point + 2;

        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("points").child(id);
        Intent i = getIntent();
        String key = i.getStringExtra("KEY");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    int getPoints = snapshot.child("user_points").getValue(Integer.class);
                    int p = point + getPoints;

                    databaseReference.child("user_points").setValue(p);


//                    String key = snapshot.getKey();
//                    String user_id = snapshot.child(key).child("user_id").getValue().toString();
//
//                    if(id.equals(user_id)){
//                        int take_points = snapshot.child(key).child("user_points").getValue(Integer.class);
//                        total = take_points+2;
//                        databaseReference.child(key).child("user_points").setValue(total);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(id);
//
//        databaseReference.child("points").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                Integer prevPoints = Integer.valueOf(snapshot.getValue(Integer.class));
//                if(prevPoints!=null){
//                    int finalScore = (Integer.valueOf(prevPoints+point));
//                    //int finalScore = Integer.parseInt(String.valueOf(prevPoints+point));
//
//                    databaseReference.child("points").setValue(finalScore);
//
//                }
//
//                //databaseReference.child("points").setValue(point).toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    }
    }


}