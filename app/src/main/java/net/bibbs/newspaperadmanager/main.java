package net.bibbs.newspaperadmanager;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


public class main extends Activity {
    final String baseUrl = "http://192.168.168.101:4646/pillarNewspaper/";
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetNewsPaperList().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            new GetNewsPaperList().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    private class GetNewsPaperList extends AsyncTask<Void, Void, Newspaper[]> {
        @Override
        protected Newspaper[] doInBackground(Void... params) {
            try {
                final String url = baseUrl + "newspaper";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, Newspaper[].class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Newspaper[] newspapers) {
            ListView newspaperListView = (ListView) findViewById(R.id.listView);
            ArrayList<String> newspaperList = new ArrayList<String>;

          //  TextView greetingIdText = (TextView) findViewById(R.id.id_value);
          //  TextView greetingContentText = (TextView) findViewById(R.id.content_value);

            for(int i = 0; i < newspapers.length; i++){
                newspaperList.add(newspapers[i].getNewspaperName());
            }

            listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, newspaperList);

            //greetingIdText.setText(newspapers[0].getId());
           // greetingContentText.setText(newspapers[0].getNewspaperName());
        }

    }

}
