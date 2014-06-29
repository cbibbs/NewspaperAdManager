package net.bibbs.newspaperadmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class main extends Activity {
    final String baseUrl = "http://192.168.168.101:4646/pillarNewspaper/";
    public final static String BASE_URL = "net.bibbs.NewspaperAdManager.BASEURL";
    private ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice);

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
        new HttpRequestNewspaper().execute();
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
        if (id == R.id.action_list_newspapers) {
            new HttpRequestNewspaper().execute();
            return true;
        }
        if (id == R.id.action_list_advertisements) {
            new HttpRequestAdvertisement().execute();
            return true;
        }
        if(id == R.id.action_add_newspaper) {
            Intent intent = new Intent(this, AddNewspaper.class);
            intent.putExtra(BASE_URL, baseUrl);
            startActivity(intent);
        }
        if(id == R.id.action_add_advertisement) {
            Intent intent = new Intent(this, AddAdvertisement.class);
            intent.putExtra(BASE_URL, baseUrl);
            startActivity(intent);
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


    private class HttpRequestNewspaper extends AsyncTask<Void, Void, Newspaper[]> {
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
            List<String> newspaperList = new ArrayList<String>();



           for(int i = 0; i < newspapers.length; i++){
                newspaperList.add(newspapers[i].getNewspaperName());
            }

            newspaperListView.setAdapter(listAdapter);
            //listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, android.R.id.text1, newspaperList);
            //newspaperListView.setAdapter(listAdapter);
            /*
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(newspapers[0].getId());
            greetingContentText.setText(newspapers[0].getNewspaperName());
            */
        }

    }
    private class HttpRequestAdvertisement extends AsyncTask<Void, Void, Advertisement[]> {
        @Override
        protected Advertisement[] doInBackground(Void... params) {
            try {
                final String url = baseUrl + "advertisement";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, Advertisement[].class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Advertisement[] advertisements) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);

            greetingIdText.setText(advertisements[0].getId());
            greetingContentText.setText(advertisements[0].getAdvertisementName());
        }

    }
}
