package com.example.demo.DataIngestionService;

import com.example.demo.Core.PostgresHelper;
import com.example.demo.Models.GameScoreDetails;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/*
This class will be continuously ingesting the game_score_details to the postgres DB and maintains all the scores that have happende till now

*/



public class GameScorePostGresIngestionService {

    public static void main(String[] args) throws SQLException {

        File file = new File("/Users/rohitkvrm/IdeaProjects/demo/src/main/resources/GameScore.txt"); // reading from the same score file
        GameScorePostGresIngestionService gameScorePostGresIngestionService =  new GameScorePostGresIngestionService();
        gameScorePostGresIngestionService.ingestPostgresData(file);

    }

    public  void ingestPostgresData(File file) {
        String line;
        Gson gsonObject = new Gson();

        PostgresHelper postgresHelper = new PostgresHelper("gamescoredb");  // this helper will provide method to insert in the db

        boolean firstRead = true;  // if this is the first time reading of the file
        Integer currOffset = 0;  // offset in current iteration
        Integer finalOffset = 0; // offset after completing one iteration

        while (true) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                if (firstRead) {
                    while ((line = bufferedReader.readLine()) != null) {

                        GameScoreDetails gameScoreDetails = gsonObject.fromJson(line, GameScoreDetails.class);
                            // Creation of object from the json using GSON library

                        String query = "INSERT INTO  game_score_details(game_id,user_id,user_score,created_at) VALUES('" + gameScoreDetails.getGameId()
                                + "','" + gameScoreDetails.getUserID() + "','" + gameScoreDetails.getUserScore() + "','" + gameScoreDetails.getCreatedAt() + "')";
                         // query to insert into the DB
                        System.out.println(query);

                        postgresHelper.write(query); // helper to write the records in th db
                        currOffset++;
                    }
                    firstRead = false;
                } else {
                    while ((line = bufferedReader.readLine()) != null) {  // same logic is present here, but it will ignore the records
                                    // that have been already read
                        if (currOffset < finalOffset) {
                            currOffset++;
                            continue;
                        }
                        GameScoreDetails gameScoreDetails = gsonObject.fromJson(line, GameScoreDetails.class);

                        String query = "INSERT INTO  game_score_details(game_id,user_id,user_score,created_at) VALUES('" + gameScoreDetails.getGameId()
                                + "','" + gameScoreDetails.getUserID() + "','" + gameScoreDetails.getUserScore() + "','" + gameScoreDetails.getCreatedAt() + "')";
                        System.out.println(query);
                        postgresHelper.write(query);
                        currOffset++;
                    }
                }
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
