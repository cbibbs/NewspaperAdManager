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

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class main extends Activity {
    final String baseUrl = "http://192.168.168.101:4646/pillarNewspaper/";
    public final static String BASE_URL = "net.bibbs.NewspaperAdManager.BASEURL";
    private ArrayAdapter<String> listAdapter;
    private ArrayList newspaperResults = new ArrayList();
    private ArrayList advertisementResults = new ArrayList();
    private ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
/*
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice);
        ListView listView = (ListView) findViewById(R.id.mainListView);
        newspaperResults.add( new Newspaper("Dummy","0"));
        ArrayList image_details = getNewspaperListData();
*/

       // listView.setAdapter(new NewspaperListAdapter(this, image_details));
/*
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        /

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Newspaper newsData = (Newspaper) o;
                Toast.makeText(main.this, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
            }

        });
*/
    }
    private ArrayList getNewspaperListData() {
        return newspaperResults;
    }

    private ArrayList getAdvertisementData() {
        return advertisementResults;
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
           ListView newspaperListView = (ListView) findViewById(R.id.mainListView);
           List<String> newspaperList = new ArrayList<String>();

           newspaperResults.clear();
           for(int i = 0; i < newspapers.length; i++){
                newspaperResults.add(newspapers[i]);
            }

            ArrayList image_details = getNewspaperListData();
            newspaperListView.setAdapter(new NewspaperListAdapter(main.this, image_details));
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
            advertisementResults.clear();
            for(int i = 0; advertisements != null && i < advertisements.length; i++ ){
                advertisementResults.add(advertisements[i]);
            }
            ListView advertisementListView = (ListView) findViewById(R.id.mainListView);
            List<String> advertisementList = new ArrayList<String>();
            ArrayList image_details = getAdvertisementData();
            advertisementListView.setAdapter(new AdvertisementListAdapter(main.this, image_details));
        }

    }
}
