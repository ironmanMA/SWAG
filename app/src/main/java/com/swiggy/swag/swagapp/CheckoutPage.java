package com.swiggy.swag.swagapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gaurav on 7/16/17.
 */
public class CheckoutPage extends Activity{
    ListView lv;
    Gson gson = new Gson();
    TextView totalCostOfDishes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);
        lv= (ListView) findViewById(R.id.selectedDishList);
        totalCostOfDishes= (TextView) findViewById(R.id.totalCalculatedCostTextView);
        ArrayList<RecommendedDishResponseDAO> selectedDishes = getIntent().getParcelableArrayListExtra("selectedDishes");
        Log.v("ON INTENT : ", selectedDishes.get(0).getDishName());
        ArrayList<HashMap<String, String>> selectedDishesMap=new ArrayList<>();
        double totalCost = 0.0;
        for(RecommendedDishResponseDAO recommendedDishResponseDAO:selectedDishes){
            HashMap<String,String> map = new HashMap<String,String>();
            map = (HashMap<String,String>) gson.fromJson(recommendedDishResponseDAO.toString(), map.getClass());
            selectedDishesMap.add(map);
            totalCost=totalCost + Double.parseDouble(recommendedDishResponseDAO.getDishPrice());
        }
        Log.v("ON INTENT MAP: ", selectedDishesMap.get(0).get("foodTitle"));
        Log.v("ON INTENT MAP: ", selectedDishesMap.get(0).get("foodPrice"));
        ListAdapter adapter = new SimpleAdapter(
                this, selectedDishesMap,
                R.layout.checked_out_dish_item, new String[] {"foodTitle", "foodPrice"},
                new int[] { R.id.selectDishTextView,
                R.id.selectedDishCostTextView });
        lv.setAdapter(adapter);
        totalCostOfDishes.setText(Double.toString(totalCost));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_swipe_deck, menu);
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
