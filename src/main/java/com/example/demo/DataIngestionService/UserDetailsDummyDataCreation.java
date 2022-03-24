package com.example.demo.DataIngestionService;

import com.example.demo.Core.PostgresHelper;
import com.example.demo.Models.UserDetails;

import java.io.*;
import java.sql.*;
import java.util.StringTokenizer;

public class UserDetailsDummyDataCreation {

    public static void main(String[] args) throws IOException, SQLException {
        File file = new File("/Users/rohitkvrm/IdeaProjects/demo/src/main/resources/UserDetails.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line;

        PostgresHelper postgresHelper = new PostgresHelper("userdb");

        while ((line = bufferedReader.readLine()) != null) {

            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

            while (stringTokenizer.hasMoreTokens()) {

                Integer id = Integer.valueOf(stringTokenizer.nextToken());
                String userName = stringTokenizer.nextToken();

                UserDetails userDetails = new UserDetails(id, userName);

                String query = "INSERT INTO user_details(user_id,user_name) VALUES('" + userDetails.getId() + "','" + userDetails.getUserName() + "')";

                postgresHelper.write(query);
            }
        }

    }

}
