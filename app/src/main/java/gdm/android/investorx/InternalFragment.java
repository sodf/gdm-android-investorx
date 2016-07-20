package gdm.android.investorx;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class InternalFragment extends ListFragment {

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
                                if (response.getJSONObject(i).getBoolean("internal")) {
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
