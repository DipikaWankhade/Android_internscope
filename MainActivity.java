package com.example.api_use;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText firstEdt,lastEdt,phoneEdt,emailEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing our views
        firstEdt = findViewById(R.id.idEdtFirst);
        lastEdt = findViewById(R.id.idEdtLast);
        phoneEdt = findViewById(R.id.idEdtPhone);
        emailEdt = findViewById(R.id.idEdtEmail);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        // adding on click listener to our button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (firstEdt.getText().toString().isEmpty() && lastEdt.getText().toString().isEmpty() && phoneEdt.getText().toString().isEmpty() && emailEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postDataUsingVolley(firstEdt.getText().toString(), lastEdt.getText().toString() , phoneEdt.getText().toString(), emailEdt.getText().toString());
            }
        });
    }
    

    private void postDataUsingVolley(String first_name, String last_name, String phone, String email_id) {
        // url to post our data
        String url = "http://3.108.21.44:3010/signup";
        loadingPB.setVisibility(View.VISIBLE);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                loadingPB.setVisibility(View.GONE);
                firstEdt.setText("");
                lastEdt.setText("");
                phoneEdt.setText("");
                emailEdt.setText("");

                // on below line we are displaying a success toast message.
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String first_name = respObj.getString("first_name");
                    String last_name = respObj.getString("last_name");
                    String phone = respObj.getString("phone");
                    String email = respObj.getString("email_id");

                    // on below line we are setting this string s to our text view.
                    responseTV.setText("First_name : " + first_name + "\n" + "Last_name : " + last_name + "\n" + "Phone : " + phone + "\n" + "Email_id : " + email_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Failed" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("phone", phone);
                params.put("email_id", email_id);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}