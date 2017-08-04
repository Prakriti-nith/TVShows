package gupta.p.tvepisodes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import gupta.p.tvepisodes.R;

public class MainActivity extends AppCompatActivity {
    EditText editTextShow;
    Button buttonDone;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.img);

        Glide.with(this)
                .load(R.drawable.tv)
                .crossFade()
                .into(img);
        init();
        methodListener();
    }

    private void init() {
        buttonDone = (Button) findViewById(R.id.buttonDone);
        editTextShow = (EditText) findViewById(R.id.editTextShow);
    }

    private void methodListener() {

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextShow.getText().toString();
                Intent i = new Intent(MainActivity.this, EpisodesList.class);
                i.putExtra("name",name);
                i.putExtra("year","");
                startActivity(i);
            }
        });
    }
}
