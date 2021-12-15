package com.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Scheduler Component
 */
@Component
public class Scheduler {

    @Autowired
    private BatchLauncher batchLauncher;

    //cron expression = second, minute, hour, day of month, month, day(s) of week
    
    // Batch Pret  toutes les heures 
    @Scheduled(cron = "30 * * * * ?")
    public void perform() throws Exception {

        System.out.println("\n ----------- scheduler Pret -------- ");
        batchLauncher.run();

    }



    //Batch Reserv toutes 5 sec 
  //  @Scheduled(cron = "5 * * * * ?")
    public void performReserve() throws Exception {

        System.out.println("\n ----------- scheduler Reservation -------- ");
        batchLauncher.runReserve();

    }

}
