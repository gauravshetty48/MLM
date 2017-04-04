package com.psaltech.mlm;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText idNumber, mobile, name, childTimesEngaged, sponsorTimesEngaged, sponsorID;
    TextView pay;
    AlertDialog.Builder payDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void onRegister(View view) {
        idNumber = (EditText) findViewById(R.id.et_id_number);
        mobile = (EditText) findViewById(R.id.et_mobile_number);
        name = (EditText) findViewById(R.id.et_name);
        childTimesEngaged = (EditText) findViewById(R.id.et_child_times_engaged);
        sponsorTimesEngaged = (EditText) findViewById(R.id.et_sponsor_times_engaged);
        sponsorID = (EditText) findViewById(R.id.et_sponsor_id);

        RequestQueue queue = Volley.newRequestQueue(this); // this = context
        String url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        payDialog = new AlertDialog.Builder(Registration.this);
                        LayoutInflater inflater = (LayoutInflater) Registration.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                        View layout = inflater.inflate(R.layout.dialog_pay,
                                null);
                        pay = (TextView) layout.findViewById(R.id.tv_pay);
                        pay.setText(Html.fromHtml(response));
                        payDialog.setView(layout);

                        payDialog.create();
                        payDialog.show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_number", idNumber.getText().toString());
                params.put("mobile", mobile.getText().toString());
                params.put("name", name.getText().toString());
                params.put("times_engaged", childTimesEngaged.getText().toString());
                params.put("sponsor_times_engaged", sponsorTimesEngaged.getText().toString());
                params.put("sponsor_id", sponsorID.getText().toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", AppConstants.authentication);
                return params;
            }
        };
        queue.add(postRequest);
    }

}
