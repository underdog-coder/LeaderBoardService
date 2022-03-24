package com.example.demo.ApiService;

import com.example.demo.Core.PostgresHelper;
import com.example.demo.Core.Utils;
import com.example.demo.Models.LeaderBoardItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
   This Class acts as a helper for the leaderboard API, this will be actually calculating the data for the leaderboard,
   Other details  of the users are also fetched by this helper only, it packages all the result in json format and returns
   to the leaderboard API.
*/


public class LeaderBoardServiceHelper {

    public static String getLeaderBoard(){

        Jedis jedis = new Jedis("localhost", 6379);  //  Using jedis client library to connect to the redis cache

        PostgresHelper postgresHelper = new PostgresHelper("userdb"); // this  is a helper class for posetgres queries
                                                                    // provides methods for querying and updating records in database

        ResultSet leaderBoardUserIdResultSet = null;   // this  result set will hold the user id and user name of the top
                                                        // 5 (or n) scorers;

        List<Tuple> elements = jedis.zrangeWithScores(Utils.REDIS_KEY,0,Utils.TOP_RECORD_COUNT - 1);
                                                            // this will extract the user id of the top 5 scorers from the
                                                            // redis

        ArrayList<LeaderBoardItem> userList = new ArrayList<>(); // This will be used to store the result  of the leader board

        String resultJson = "";

        for(Tuple tuple: elements){ // We have to iterate over all the top 5 user id and retrieve information from the
                                    // USER DATABASE

            String query = "select user_name,user_id from public.user_details where user_id = ";

            query += tuple.getElement().split(":")[1]; // Since I am storing a combination of gameID + userId in redis
                                                        // here extracting the user id from the combination

            System.out.println(query);

            leaderBoardUserIdResultSet = postgresHelper.read(query); // reading the user info from user db

            String userName;
            String userId;

            try {
                while (leaderBoardUserIdResultSet.next()) {
                    userName = leaderBoardUserIdResultSet.getString("user_name");
                    userId = leaderBoardUserIdResultSet.getString("user_id");
                    userList.add(new LeaderBoardItem(userName,tuple.getScore(),userId)); // Adding the new leaderboard item in the
                                                                    // result list
                }
            } catch (Exception e){
                System.out.println("Error calculating the leaderboard" + e.getMessage());
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            resultJson = gson.toJson(userList); // Creating json data from the leaderboard item list
        }
        return resultJson;
    }

}
