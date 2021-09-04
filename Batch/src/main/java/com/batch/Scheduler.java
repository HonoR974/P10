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

    //toutes les 8 sec
   // @Scheduled(fixedDelay = 8000)

    //tout les min a 19 h le 27/05

//    @Scheduled(cron = "0 */1 19 27 05 ? ")
/*
    public void perform() throws Exception {

        System.out.println("\n ----------- Perform -------- ");
        batchLauncher.run();

    }
*/


    @Scheduled(fixedRate = 5000)
    public void performReserve() throws Exception {

        System.out.println("\n ----------- Perform Test -------- ");
        batchLauncher.runReserve();

    }


}
