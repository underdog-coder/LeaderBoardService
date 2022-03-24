package com.example.demo.Controller;

import com.example.demo.ApiService.LeaderBoardServiceHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class LeaderBoardServiceController {


    private LeaderBoardServiceHelper leaderBoardService;

    @RequestMapping(method = RequestMethod.GET, path = "/api/leaderboard")  // this will be a rest endpoint by using which the
                                                                        // the top five scorers will be fetched
    public ResponseEntity<String> getLeaderboard()  {

        leaderBoardService = new LeaderBoardServiceHelper();   // the helper service will calculate the result and send that to the api
        String result  = leaderBoardService.getLeaderBoard();   // which will resturn the data to the end user

        return ResponseEntity.ok(result);
    }

}