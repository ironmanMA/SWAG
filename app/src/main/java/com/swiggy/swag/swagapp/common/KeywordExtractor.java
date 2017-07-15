package com.swiggy.swag.swagapp.common;

import java.util.*;
import java.util.Set;
import java.util.Map.Entry;

import static com.swiggy.swag.swagapp.common.StopWordRemoval.removeStopWords;


/**
 * Created by 127.0.0.1.ma on 15/07/17.
 */

public class KeywordExtractor {

    public static final String[] food_dict = {"P-1 Bellassai Pizza", "P-3 Fantasy Pizza", "P-5 Indian Pizza", "P-6 Manali Pizza", "P-7 Margherita Pizza", "P-12 Spinichi Pizza", "P-61 French Fries", "P-62 Garlic Bread", "P-63 Garlic Bread with Cheese", "P-64 Garlic Bread with Cheese and Mushrooms", "P-65 Nachos with Cheese Sauce", "P-66 Tortilla Chips with Salsa Sauce", "P-67 Nachos with Refried Beans", "P-64 Mushroom Cheese Garlic Bread", "P-68 Masala Cheese Garlic Bread", "P-2 Bombay Pizza", "P-4 Exotic Pizza", "P-8 Mediterranean Pizza", "P-9 Nawabi Pizza", "P-11 Pizza Assorted Pizza", "P-13 Tabasco Pizza", "P-18 Sicilian Pizza", "P-30 Aglio Olio E Pepperoncini Pasta", "P-31 All Arrabbiata Pasta", "P-32 Matriciana Pasta", "P-33 Alla Firritiana Pasta", "P-35 Pasta Del Barone Pasta", "P-36 Spaghetti Bolognese Pasta", "P-40 Pomodoro Pasta", "P-38 Pasta Barbaresca", "P-37 Penne Del Fattore Pasta", "P-39 Pasta All Broccoli", "9 Four Pepper Falafel Pizza", "9 Four Pepper & Famous Five Pizza", "9 Four Pepper Salami Pizza", "9 Chipotle Veggie Dream Pizza", "9 Chipotle Gourmet Veggie Pizza", "9 Chipotle Keema & Sausage Pizza", "9 Classic Picante Pizza", "Willy Wonka", "Oreo Shot", "Chocoholics", "Ferrero Rocher", "Brownie Break Big", "Fruit Exotica", "Candy Land", "Double Chicken Roll", "Double Mutton Roll", "Boneless Chicken Tikka Kebab (5 Pcs)", "Chicken Reshmi Kebab", "Chicken Tandoori (Half)", "Chicken Tandoori (Full)", "Green Salad", "Paneer Butter Masala", "Chicken Tawa (Half)", "Chicken Kasha", "Boneless Chicken Butter Masala", "Chicken Tikka Butter Masala", "Mutton Rogan Josh", "Mughlai Chicken Biryani", "Mughlai Mutton Biryani", "Dal Fry", "Zafrani Phirni", "Gobi Manchurian", "Half Chicken Tandoori (Dry)", "Boneless Murgh Tikka", "Full Empire Special Chicken Kebab (Dry)", "Boneless Empire Special Chicken Kebab (Dry)", "Full Chilli Kebab (Dry)", "Chicken Lollipop (Dry) (5 Pcs)", "Half Grilled Chicken (Dry)", "Veg Korma", "Paneer Butter Masala", "Full Butter Chicken Boneless Gravy", "Chicken Varaval Gravy", "Chicken Tikka Masala Gravy", "Boneless Hyderabadi Chicken Gravy (North Indian)", "Mutton Varaval Gravy", "Dal Fry", "Chicken Biryani", "Kulcha", "Special Paratha", "Kerala Paratha", "Malabar Paratha", "Coin Paratha", "Non Veg Lunch Thali", "Non Veg Dinner Thali", "Paneer Manchurian Gravy", "Baby corn Manchurian Gravy", "A2B Special Curry", "Dal Tadka", "Veg Hyderabadi Biryani", "Navaratna Pulao", "A2B Special Bread Basket", "Schezwan Noodles", "Dahi Aloo Chaat", "Masala Bhelpuri", "Masala Pav Bhaji", "Ellu Murukku", "Mixed Grilled Sandwich", "Green Salad", "Onion Capsicum Pizza", "A2B North indian Combo", "Special South indian Thali Meals", "Onion Rava Masala Dosa", "Curd Vada (1 Pc)", "Sambar Vada (1 Pc)", "14 Idli", "Mini Tiffin", "Chicken Grilled Sandwich", "Greek Salad", "Mutton Rogan Josh", "Dal Makhani", "Muttar Pulao", "Pork Chilli Oyster", "Chicken Hong Kong", "Pork Hakka Noodles", "Chicken Tibetan Platter", "Veg Tingmo", "Chicken Gyathuk", "Idli", "Idli Vada", "Uddin Vada", "Curd Vada", "Open Dosa", "Benne Dosa", "Benne Khali Dosa", "Mixed Bhath", "Chow Chow Bath", "Paddu", "Poori Saagu", "Ragi Kilsa", "Mirchi (3 Pcs)", "Avalakki Oggarane", "Aam Rise+", "Grand Madras Tiffin", "Dal Makhani with Laccha Parathas", "Shahi Paneer Rice Bowl", "Butter Chicken with Laccha Parathas", "Butter Chicken Rice Bowl", "Chocolate Lava Cake", "Green Mint Cooler", "Hazelnut Cold Coffee", "Himsal Nacho Salad", "Paneer Croquette", "BBQ Chicken Sandwich", "Grilled Chicken Burger", "Classic Margherita Pizza", "Veg Bistro Oui Special Pizza", "Bistro Oui Chicken Steak", "Creamy Wild Green Veggie Wrap", "Chicken Olivetti with Paprika Aioli", "D Hide Special Citrus Salad", "The All Veg Pizza on a Thin Crust", "Jamaican Jerk Chicken", "Belgium Chocolate Truffle", "The Farmer's Breakfast", "The Porky Hole 9 Yards Hash", "The All English Breakfast", "Just Beat It Continental", "Just Beat It Martian", "Stuff on Toast (Eggless)", "French Toast Stuffed with Peanut Butter and Banana", "Double Grilled Chicken Sandwich", "Our Club Sandwich", "The House Veggie Burger", "The Chicken Steak Burger", "Meaty Double Beef Cheese Burger (Plain)", "Meaty Beef Cheese Burger with Bacon and Egg", "So Cheesy Fries", "Beef Me Up Scottie", "Hash Browns", "Bacon (Pork) (Extra)", "Veg Pasta Red Sauce", "Funky Monkey Waffles", "Pancakes", "Oreo Shake", "The Hole Lotta Breakfast Smoothie", "Cold Coffee", "Peach Iced Tea", "Tomato Basil Soup", "Spicy Spinach Soup", "Fish & Chips", "Eden Garden Salad", "Pepper Chilli Chicken Crepe", "Grilled Chicken Sandwich", "Jerk Chicken Burger", "Crispy Chicken Burger", "11 Chicken Pepperoni Pizza", "Veg Thai Curry with Rice", "Fish Curry with Steamed Rice", "Waffle", "Double Ka Meetha", "Blueberry Cheese Cake", "Peanut Butter Brownie Shake", "Lemon Iced Tea", "Orange Lemonade", "Cappuccino", "Coffee-Wood Smoked Caesar", "Fresh Vegetable Crunch Sandwich", "Pepper-Crusted Grilled Chicken", "Millet Lasagne", "Poutine", "Chicken Burger", "Chicken One Minute", "Steak in Choice of Sauce", "Beef Burger", "The 2-Egg Brekkie", "Proteinated"};

    public static HashMap<String, Double> intersect(HashMap<String, Double> curated_words) {
        HashMap<String, Double> BoW = new HashMap<String, Double>();
//        for(String dish : dict){
//            if(curated_words.containsKey(dish)){
//                if(!BoW.containsKey(dish))
//                    BoW.put(dish,curated_words.get(dish));
//            }
//        }

        Set<String> curatedKeys = curated_words.keySet();

        for (String s : curatedKeys) {
//            System.out.println(s);
            for (int i = 0; i < food_dict.length; i++) {
                if (food_dict[i].toLowerCase().contains(s.toLowerCase())) {
                    BoW.put(s, curated_words.get(s));
                    break;
                }
            }
        }

        return sortByValue(BoW);
    }

    private static HashMap<String, Double> sortByValue(Map<String, Double> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }

    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> findGreatest(Map<K, V> map, int n) {
        Comparator<? super Map.Entry<K, V>> comparator =
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e0, Map.Entry<K, V> e1) {
                        V v0 = e0.getValue();
                        V v1 = e1.getValue();
                        return v0.compareTo(v1);
                    }
                };
        PriorityQueue<Map.Entry<K, V>> highest =
                new PriorityQueue<Map.Entry<K, V>>(n, comparator);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            highest.offer(entry);
            while (highest.size() > n) {
                highest.poll();
            }
        }

        List<Map.Entry<K, V>> result = new ArrayList<Map.Entry<K, V>>();
        while (highest.size() > 0) {
            result.add(highest.poll());
        }
        return result;
    }


    public static void main(String[] args) {
        String str = "Excellent service..friendly staff...the Italian pizza was really delicious. ..toooo damn good...Pasta was yummy too....pasta which i ordered was dry..without grumbling the chef took it back and made it saucy..and as i couldnt finish the pasta they neatly packed the leftover..without me asking them to..worth the money..affordable...";
        HashMap<String, Double> curated_words = removeStopWords(str);
        HashMap<String, Double> final_map = intersect(curated_words);
        System.out.println(final_map);
    }
}
