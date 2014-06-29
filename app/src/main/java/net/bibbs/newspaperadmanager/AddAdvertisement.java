package net.bibbs.newspaperadmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class AddAdvertisement extends Activity {
    private String baseUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertisement);
        Intent intent = getIntent();
        baseUrl = intent.getStringExtra(main.BASE_URL);
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
        new HttpRequestAdvertisementAdd().execute();
        finish();
    }

    private class HttpRequestAdvertisementAdd extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                final String uri = baseUrl + "advertisement/";

                EditText advertisementNameText = (EditText) findViewById(R.id.editTextAdvertisementName);
                EditText advertisementNameDescriptionText = (EditText) findViewById(R.id.editTexAdvertisementDescription);
                Advertisement advertisement = new Advertisement();
                advertisement.setAdvertisementName(advertisementNameText.getText().toString().trim());
                advertisement.setAdvertisementDescription(advertisementNameDescriptionText.getText().toString().trim());

                if (advertisement.getAdvertisementName().length() != 0) {
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    return restTemplate.postForObject(uri, advertisement, String.class);
                }
            } catch (Exception e) {
                Log.e("AddNewspaperActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String params) {

            Context context = getApplicationContext();
            CharSequence text = "Advertisement Added";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}
