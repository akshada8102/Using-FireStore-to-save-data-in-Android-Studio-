package com.aksh.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aksh.firebase.Data.data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
EditText title;
EditText occup;
TextView t1,t2;
FirebaseFirestore ff=FirebaseFirestore.getInstance();
data d=new data();
DocumentReference df=ff.collection("Data").document("List");

CollectionReference cf=ff.collection("Data");
Button click,click1,click2,click3;

    @Override
    protected void onStart() {
        super.onStart();
        df.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null){
//                    data d=value.toObject(data.class);
//                    t1.setText(data.getTitle());
//                    t2.setText(data.getOccupation());

                    t1.setText(value.getString("title"));
                    t2.setText(value.getString("occupation"));

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=findViewById(R.id.title);
        occup=findViewById(R.id.occup);
        click=findViewById(R.id.button);
        click1=findViewById(R.id.show);
        click2=findViewById(R.id.update);
        click3=findViewById(R.id.delete);

        t1=findViewById(R.id.show1);
        t2=findViewById(R.id.show2);

        click3.setOnClickListener(v->{
            Map<String,Object> mp=new HashMap<>();
            mp.put("title", FieldValue.delete());
            df.update(mp);
        });

        click2.setOnClickListener(v->{
            String titlee=title.getText().toString().trim();
            String occupp=occup.getText().toString().trim();
            Map<String,Object> mp=new HashMap<>();
            mp.put("title",titlee);
            mp.put("occupation",occupp);
            df
                    .update(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

        });

        click.setOnClickListener(v->{
            InputMethodManager im= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
           im.hideSoftInputFromWindow(v.getWindowToken(),0);
//           d.setTitle(title.getText().toString());
//            d.setOccupation(occup.getText().toString());
//            cf.add(d);

            String titlee=title.getText().toString().trim();
            String occupp=occup.getText().toString().trim();
          Map<String,Object> mp=new HashMap<>();
          mp.put("title",titlee);
            mp.put("occupation",occupp);

            cf.add(mp);
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
//                        }
//                    });


        });
        click1.setOnClickListener(v->{
df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {
if(documentSnapshot.exists()){
    String a=documentSnapshot.getString("title");
    String b=documentSnapshot.getString("occupation");
    t1.setText(a);
    t2.setText(b);

}
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(getApplicationContext(),"Failed....",Toast.LENGTH_SHORT).show();

    }
});

        });


    }

}