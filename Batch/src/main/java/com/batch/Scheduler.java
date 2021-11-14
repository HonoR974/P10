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
    //@Scheduled(cron = "* * 1 * * ?")
    public void perform() throws Exception {

        System.out.println("\n ----------- Perform Pret -------- ");
        batchLauncher.run();

    }



    //Batch Reserv toutes 20 sec 
    @Scheduled(cron = "5 * * * * ?")
    public void performReserve() throws Exception {

        System.out.println("\n ----------- Perform Test -------- ");
        batchLauncher.runReserve();

    }

}
