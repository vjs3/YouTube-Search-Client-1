package youtubeplayer.com.youtubesearchclient;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText searchInput;
    private ListView videosFound;
    private Handler handler;
    String id,title,thumbnail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchInput = (EditText) findViewById(R.id.search_input);
        videosFound = (ListView) findViewById(R.id.videos_found);
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("VIDEO_ID", searchResults.get(position).getId());
                intent.putExtra("VIDEO_ID", searchResults.get(position).getId());
                intent.putExtra("VIDEO_TITLE", searchResults.get(position).getTitle());
                intent.putExtra("VIDEO_THUMBNAIL",searchResults.get(position).getThumbnailURL());
                intent.putExtra("VIDEO_LIKES", searchResults.get(position).getLikes());
                intent.putExtra("VIDEO_UPLOADEDBY",searchResults.get(position).getUploadedBy());
                intent.putExtra("VIDEO_VIEWS",searchResults.get(position).getViews());
                intent.putExtra("VIDEO_CATEGORY",searchResults.get(position).getCategory());
                startActivity(intent);
            }
        });
        handler = new Handler();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }
        });

    }

    private List<VideoItem> searchResults;

    private void searchOnYoutube(final String keywords){
        new Thread(){
            public void run(){
                YoutubeConnector yc = new YoutubeConnector(MainActivity.this,keywords);
                searchResults = yc.getVideos();
                handler.post(new Runnable(){
                    public void run(){updateVideosFound();}
                });
            }
        }.start();
    }

    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.video_item, searchResults) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);

                    viewHolder = new ViewHolder();
                    viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.video_thumbnail);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.video_title);
                    viewHolder.uploadedBy = (TextView) convertView.findViewById(R.id.video_uploadedBy);
                    viewHolder.duration = (TextView) convertView.findViewById(R.id.video_duration);

                    convertView.setTag(viewHolder);
                }
                viewHolder = (ViewHolder)convertView.getTag();
                VideoItem searchResult = searchResults.get(position);
                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).placeholder(R.mipmap.ic_launcher).into(viewHolder.thumbnail);
                viewHolder.title.setText(searchResult.getTitle());
                viewHolder.uploadedBy.setText(searchResult.getUploadedBy());
                viewHolder.duration.setText(searchResult.getDuration());
                return convertView;
            }

        };


        videosFound.setAdapter(adapter);
    }
    public class ViewHolder{
        ImageView thumbnail;
        TextView title;
        TextView uploadedBy;
        TextView duration;

    }

}
