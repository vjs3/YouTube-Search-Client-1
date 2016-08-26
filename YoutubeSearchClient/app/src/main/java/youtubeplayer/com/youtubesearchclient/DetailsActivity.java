package youtubeplayer.com.youtubesearchclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    String id;
    ImageView thumbnail;
    TextView title,uploadedBy,views,likes,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        title = (TextView) findViewById(R.id.video_title);
        thumbnail = (ImageView) findViewById(R.id.video_thumbnail);
        uploadedBy = (TextView) findViewById(R.id.video_uploadedBy);
        likes = (TextView) findViewById(R.id.video_likes);
        views = (TextView) findViewById(R.id.video_views);
        category = (TextView) findViewById(R.id.video_category);
        id= getIntent().getStringExtra("VIDEO_ID");
        final String Sthumbnail = getIntent().getStringExtra("VIDEO_THUMBNAIL");
        String Stitle = getIntent().getStringExtra("VIDEO_TITLE");
        String SuploadedBy = getIntent().getStringExtra("VIDEO_UPLOADEDBY");
        String Slikes = getIntent().getStringExtra("VIDEO_LIKES");
        String Sviews = getIntent().getStringExtra("VIDEO_VIEWS");
        String Scategory = getIntent().getStringExtra("VIDEO_CATEGORY");
        title.setText(Stitle);
        uploadedBy.setText(SuploadedBy);
        views.setText(Sviews);
        likes.setText(Slikes);
        category.setText(Scategory);
        Picasso.with(getApplicationContext()).load(Sthumbnail).placeholder(R.mipmap.ic_launcher).into(thumbnail);
        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id)));

            }
        });
    }
}

