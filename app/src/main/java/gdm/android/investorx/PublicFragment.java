package gdm.android.investorx;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PublicFragment extends ListFragment {

    private ArrayAdapter<String> mAdapter;
    List<String> data = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String url = "https://557b0105.ngrok.io/services";
        RequestQueue queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>()
                {
                    @Override public void onResponse(JSONArray response) {
                        try {
                            int j = 0;
                            for (int i = 0; i < response.length(); i++) {
                                if (!response.getJSONObject(i).getBoolean("internal")) {
                                    data.add(j, response.getJSONObject(i).getString("name"));
                                    j++;
                                }
                            }

                            mAdapter = new ArrayAdapter<String>(
                                    getActivity(),
                                    android.R.layout.simple_list_item_1,
                                    data);
                            setListAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        queue.add(getRequest);

        getListView().setBackgroundColor(Color.parseColor("#00a63f"));
    }

    public void setVisibility(int visibility) {
        View view = getView();
        if (view != null) {
            view.setVisibility(visibility);
        }
    }
}
