package net.bibbs.newspaperadmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class AddNewspaper extends Activity {

    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newspaper);
        Intent intent = getIntent();
        baseUrl = intent.getStringExtra(main.BASE_URL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_newspaper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  public void sendMessage(View view){
      new HttpRequestNewspaperAdd().execute();
      finish();
  }

    private class HttpRequestNewspaperAdd extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                final String uri = baseUrl + "newspaper/";

                EditText newspaperNameText = (EditText) findViewById(R.id.editTextNewspaperName);
                Newspaper newspaper = new Newspaper();
                newspaper.setNewspaperName(newspaperNameText.getText().toString().trim());

                if (newspaper.getNewspaperName().length() != 0) {

                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    return restTemplate.postForObject(uri, newspaper, String.class);
                }
            } catch (Exception e) {
                Log.e("AddNewspaperActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String params) {

            Context context = getApplicationContext();
            CharSequence text = "Newspaper Added";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}
