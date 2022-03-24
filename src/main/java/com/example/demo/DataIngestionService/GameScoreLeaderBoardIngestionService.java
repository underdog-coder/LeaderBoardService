package com.example.demo.DataIngestionService;

import com.example.demo.Core.Utils;
import com.example.demo.Models.GameScoreDetails;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
/*
This class will be continuously ingesting the game_score_details to the leaderboard cache and maintains the top scores and
the user ids corresponding to it.
*/


public class GameScoreLeaderBoardIngestionService {

    public static void main(String[] args) {

        File file = new File("/Users/rohitkvrm/IdeaProjects/demo/src/main/resources/GameScore.txt"); // this file stores
                                // the game score details
        GameScoreLeaderBoardIngestionService gameScoreLeaderBoardIngestionService = new GameScoreLeaderBoardIngestionService();
        gameScoreLeaderBoardIngestionService.ingestScoreLeaderBoardData(file);
    }

    public void ingestScoreLeaderBoardData(File file) { // this method will implement the logic for the ingestion in the cache

        String line;
        Gson gsonObject = new Gson();

        Jedis jedis = new Jedis("localhost", 6379); // Establish connection to the redis

        boolean firstRead = true;  // when the file is read first time

        Integer currOffset = 0;   // current processing offset file
        Integer finalOffset = 0; // last offset of file in current iteration

        String redisKey = "game_score";  // key against which sorted set of userid are stored according to their respective score

        boolean minScoreSet = false;  // This is used for finding if the minimum score in the redis is set for the first time
        double minScore = 0;  // stores the min score present in redis so we don't need to find min every time from redis

        while (true) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); // fore reading the file
                if (firstRead) {
                    while ((line = bufferedReader.readLine()) != null) {

                        GameScoreDetails gameScoreDetails = gsonObject.fromJson(line, GameScoreDetails.class);
                        // creating gamedetails object from the json input line
                        Integer gameScore = gameScoreDetails.getUserScore();
                        String userId = gameScoreDetails.getUserID().toString();
                        String gameId = gameScoreDetails.getGameId().toString();
                        // computing attributes of the game score

                        if (jedis.zcard(redisKey) < Utils.TOP_RECORD_COUNT) {  // if the size is less than the required count
                                                            // we will be adding to the res sorted set
                            jedis.zadd(redisKey, gameScore, gameId + ":" + userId); // combination of gameId + userID is
                                                            // used for storing in the redis
                        } else {
                            if (minScoreSet && Double.compare(minScore, gameScore) > 0) { // if the minscore is already set
                                                             // and the new gamescore is less then the min present in redis
                                                            // then we ignore the coming record
                                currOffset++;       // for maintaining how many records we have read from the file
                                continue;
                            }
                            List<Tuple> elements = jedis.zrangeWithScores(redisKey, 0, 0); // if the coming score is higer then
                                                                // the min score then we remove the min score and add the new score and update the min
                            minScoreSet = true;
                            for (Tuple tuple : elements) {
                                minScore = tuple.getScore();
                                if (tuple.getScore() < gameScore) {
                                    jedis.zadd(redisKey, gameScore, gameId + ":" + userId);
                                    jedis.zrem(redisKey, tuple.getElement());

                                    List<Tuple> minRecord = jedis.zrangeWithScores(redisKey, 0, 0); // here we have to find the
                                                                            // min score present in the sorted set
                                    for(Tuple tuple1 : elements){
                                        minScore =  tuple.getScore();
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        currOffset++;
                    }
                    firstRead = false;
                } else {     // same logic as above just to handle that no records that are read previously are again read;
                    while ((line = bufferedReader.readLine()) != null) {

                        if (currOffset < finalOffset) {
                            currOffset++;
                            continue;
                        }
                        GameScoreDetails gameScoreDetails = gsonObject.fromJson(line, GameScoreDetails.class);

                        Integer gameScore = gameScoreDetails.getUserScore();
                        String userId = gameScoreDetails.getUserID().toString();
                        String gameId = gameScoreDetails.getGameId().toString();

                        if (jedis.zcard(redisKey) < Utils.TOP_RECORD_COUNT) {
                            jedis.zadd(redisKey, gameScore, gameId + ":" + userId);
                        } else {
                            if (minScoreSet && Double.compare(minScore, gameScore) > 0) {
                                currOffset++;
                                continue;
                            }
                            List<Tuple> elements = jedis.zrangeWithScores(redisKey, 0, 0);
                            minScoreSet = true;
                            for (Tuple tuple : elements) {
                                minScore = tuple.getScore();
                                if (tuple.getScore() < gameScore) {
                                    jedis.zadd(redisKey, gameScore, gameId + ":" + userId);
                                    jedis.zrem(redisKey, tuple.getElement());
                                    List<Tuple> minRecord = jedis.zrangeWithScores(redisKey, 0, 0); // here we have to find the
                                    // min score present in the sorted set
                                    for(Tuple tuple1 : elements){
                                        minScore =  tuple.getScore();
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        currOffset++;
                    }
                }
                System.out.println(finalOffset);
                finalOffset = currOffset;
                currOffset = 0;

                System.out.println("Wating for the input in the game score file");
                bufferedReader.close();
                Thread.sleep(20000);
            } catch (IOException e) {
                System.out.println("Score File not present/ Error opening the score file" + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Error reading game score file" + e.getMessage());
            }
        }

    }
}
