package com.swiggy.swag.swagapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SwipeDeckActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeDeck cardStack;
    private Context context = this;
    Gson gson = new Gson();
    private SwipeDeckAdapter adapter;
    final List<RecommendedDishResponseDAO> selectedDishes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_deck);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(true);


        // Get data from server
        new RecommendedDishList().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    public class SwipeDeckAdapter extends BaseAdapter {

        private List<RecommendedDishResponseDAO> data;
        private Context context;

        public SwipeDeckAdapter(List<RecommendedDishResponseDAO> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.dish_card, parent, false);
            }
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            TextView restaurantNametextView = (TextView) v.findViewById(R.id.restaurantName);
            restaurantNametextView.setText( data.get(position).getRestaurant()+" ("+data.get(position).getRestaurantReviewCount()+")");

            TextView restaurantRatingtextView = (TextView) v.findViewById(R.id.restaurantRating);
            restaurantRatingtextView.setText("* " + data.get(position).getRestaurantRating());

            TextView restaurantDeliveryTimeTextView = (TextView) v.findViewById(R.id.deliveryTime);
            restaurantDeliveryTimeTextView.setText(data.get(position).getHotelEstimateDeliveryTime()+" min");

            TextView foodTitleTextView = (TextView) v.findViewById(R.id.dishName);
            foodTitleTextView.setText(data.get(position).getDishName());

            TextView foodPriceTextView = (TextView) v.findViewById(R.id.foodCost);
            foodPriceTextView.setText("Rs " + data.get(position).getDishPrice()+"/-");

            ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
            String imageUrl = data.get(position).getImageUrl().toString();
            if (imageUrl != null)
                Picasso.with(context).load(imageUrl).fit().centerCrop().into(imageView);
            else
                Picasso.with(context).load(R.drawable.food).fit().centerCrop().into(imageView);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hwardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                    //Intent i = new Intent(v.getContext(), BlankActivity.class);
                    //v.getContext().startActivity(i);
                }
            });
            return v;
        }
    }

    private class RecommendedDishList extends AsyncTask<Void, Void, List<RecommendedDishResponseDAO>> {

        @Override
        protected void onPostExecute(final List<RecommendedDishResponseDAO> responseData) {
            adapter = new SwipeDeckAdapter(responseData, getApplicationContext());
            cardStack.setAdapter(adapter);

            cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                @Override
                public void cardSwipedLeft(int position) {
                    Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                }

                @Override
                public void cardSwipedRight(int position) {
                    selectedDishes.add(responseData.get(position));
                    Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                }

                @Override
                public void cardsDepleted() {
                    LayoutInflater inflater = getLayoutInflater();
                    // normally use a viewholder
                    ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
                    View v = inflater.inflate(R.layout.no_dish_layout, view);
                    ImageView imageView = (ImageView) v.findViewById(R.id.not_available);
                    Picasso.with(context).load(R.drawable.not_available).fit().centerCrop().into(imageView);
                    Log.i("MainActivity", "no more cards");
                }

                @Override
                public void cardActionDown() {
                    Log.i(TAG, "cardActionDown");
                }

                @Override
                public void cardActionUp() {
                    Log.i(TAG, "cardActionUp");
                }

            });
            cardStack.setLeftImage(R.id.left_image);
            cardStack.setRightImage(R.id.right_image);


            Button checkoutButton = (Button) findViewById(R.id.CheckoutButton);
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("SELECTED DISHES SIZE : ", selectedDishes.get(0).getDishName());
                    Intent checkout_intent = new Intent(getApplicationContext(), CheckoutPage.class);
                    checkout_intent.putParcelableArrayListExtra("selectedDishes", (ArrayList<RecommendedDishResponseDAO>) selectedDishes);
                    startActivity(checkout_intent);
                }
            });
            FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("SELECTED DISHES SIZE : ", selectedDishes.get(0).getDishName());
                    Intent checkout_intent = new Intent(getApplicationContext(), CheckoutPage.class);
                    checkout_intent.putParcelableArrayListExtra("selectedDishes", (ArrayList<RecommendedDishResponseDAO>) selectedDishes);
                    startActivity(checkout_intent);
                }
            });
        }

        @Override
        protected List<RecommendedDishResponseDAO> doInBackground(Void... params) {
            ArrayList<RecommendedDishResponseDAO> responseData = new ArrayList<>();
            JSONParser jParser = new JSONParser();

            /*Uncomment to flow data from server instead of static data*/
            //String url = "endpoint_URL";
            //String jsonStr = jParser.getJSONFromUrl(url);

            ArrayList<RecommendedDishResponseDAO> list = getIntent().getParcelableArrayListExtra("MY_DATA");
            responseData.addAll(list);
//            if (jsonStr != null) {
//                try {
//                    JSONArray allMenuItems = new JSONArray(jsonStr);
//                    //JSONArray allMenuItems = jsonObj.getJSONArray("menu_items");
//                    for (int i = 0; i < allMenuItems.length(); i++) {
//                        JSONObject currentObject = allMenuItems.getJSONObject(i);
////                        String id = currentObject.get("id").toString();
////                        String restaurant = currentObject.get("restaurant").toString();
//                        RecommendedDishResponseDAO recommendedDishResponseDAO = new RecommendedDishResponseDAO();
////                        recommendedDishResponseDAO.setId(id);
////                        recommendedDishResponseDAO.setRestaurant(restaurant);
//                        String jsonString = currentObject.toString();
//                        recommendedDishResponseDAO = gson.fromJson(jsonString, RecommendedDishResponseDAO.class);
//                        responseData.add(recommendedDishResponseDAO);
//                    }
//                } catch (JSONException e) {
//                    Log.e("NOTIFICATION ERROR ", e.toString());
//                }
//            }

            return responseData;
        }
    }
}
