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


    
    // Batch Pret   
    @Scheduled(fixedDelay = 8000)
    public void perform() throws Exception {

        System.out.println("\n ----------- Perform Pret -------- ");
        batchLauncher.run();

    }


    //Batch Reserv 
   // @Scheduled(fixedRate = 5000)
    //@Scheduled(cron = "0 */1 10 04 11 ? ")
   /*
   
    public void performReserve() throws Exception {

        System.out.println("\n ----------- Perform Test -------- ");
        batchLauncher.runReserve();

    }
*/
}
