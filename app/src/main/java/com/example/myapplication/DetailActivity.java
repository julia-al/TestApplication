package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView FirstName, LastName, Departament, Birthday, Phone;
    String avUrl;
    ImageView avatar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        FirstName = (TextView) findViewById(R.id.dFirstName);
        LastName = (TextView) findViewById(R.id.dLastName);
        Departament = (TextView) findViewById(R.id.dDepartament);
        Birthday = (TextView) findViewById(R.id.dBirthday);
        Phone = (TextView) findViewById(R.id.dPhone);
        avatar = (ImageView) findViewById(R.id.dImage);

        Intent i = this.getIntent();
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            FirstName.setText(mBundle.getString("firstname"));
            LastName.setText(mBundle.getString("lastname"));
            Departament.setText(mBundle.getString("departament"));
            Birthday.setText(mBundle.getString("birthday"));
            Phone.setText(mBundle.getString("phone"));
            avUrl = i.getExtras().getString("avatar");

            Picasso.get()
                    .load(avUrl)
                    .placeholder(R.drawable.sample_img)
                    .fit()
                    .centerCrop()
                    .into(avatar);
            //avatar.setImageResource(R.drawable.sample_img);
        }
    }
}
