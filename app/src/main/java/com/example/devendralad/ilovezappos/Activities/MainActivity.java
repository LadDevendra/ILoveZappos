package com.example.devendralad.ilovezappos.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.devendralad.ilovezappos.Fragment.ProductPage;
import com.example.devendralad.ilovezappos.Model.Product;
import com.example.devendralad.ilovezappos.Model.UserResponse;
import com.example.devendralad.ilovezappos.R;
import com.example.devendralad.ilovezappos.Service.ZapposAPIService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {

    EditText searchkey_edittext;
    Button search_button;
    TextView display_textView;
    FrameLayout fragment_frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get UI elements
        searchkey_edittext = (EditText)findViewById(R.id.searchKey_editText);
        search_button = (Button)findViewById(R.id.search_button);
        display_textView = (TextView) findViewById(R.id.display_textView);
        fragment_frame = (FrameLayout)findViewById(R.id.fragment_frame);

        // Search click listener
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = "b743e26728e16b81da139182bb2094357c31d331";
                getProducts(searchkey_edittext.getText().toString(), key);
            }
        });
    }

    private void getProducts(String keyWord, String key)
    {
        //retrofit call
        Product p = new Product();
        String url = "https://api.zappos.com/";
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        ZapposAPIService service = retrofit.create(ZapposAPIService.class);
        Call<UserResponse> call = service.getProducts(keyWord, key);

        //retroft Async Callback
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Response<UserResponse> response, Retrofit retrofit) {

                //get the first product and pass it to product page fragment for data binding
                Product first_prod = null;
                if(response.body().getCurrentResultCount() >  0)
                {
                    display_textView.setVisibility(View.INVISIBLE);
                    fragment_frame.setVisibility(View.VISIBLE);
                    first_prod = response.body().getResults().get(0);

                    //String test = response.body().getStatusCode();
                    //call fragment and pass data object
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("first_prod", first_prod);
                    android.app.FragmentManager manager = getFragmentManager();
                    ProductPage productPageFragment = new ProductPage();
                    productPageFragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.fragment_frame, productPageFragment).commit();
                }
                else
                {
                    // no produts for current keyword
                    fragment_frame.setVisibility(View.INVISIBLE);
                    display_textView.setVisibility(View.VISIBLE);
                    display_textView.setText("No Products.. try something else");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                    display_textView.setText("fail");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
