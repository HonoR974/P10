package com.batch.config;

import com.batch.service.ReservationService;
import com.batch.service.ReservationServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class ReservConfig {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Bean
    public Job reservBatch()
    {
        return jobBuilders.get("Reservation")
                .start(getFirstReserv())
                .next(sendList())
                .next(checkDelai())
                .next(checkListReservByBook())
                .build();
    }


    @Bean
    public Step getFirstReserv (){
        return stepBuilders.get("getFirstReservationNoSendMail")
                .tasklet(getFirstReservation())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter getFirstReservation()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(reservationService());
        adapter.setTargetMethod("getFirstReserv");
        return adapter;
    }


    @Bean
    public Step sendList() {
        return stepBuilders.get("sendList")
                .tasklet(sendListMethod())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter sendListMethod()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(reservationService());
        adapter.setTargetMethod("sendListReservation");

        return adapter;
    }


    //cherche les livres qui n'ont pas de reserv statut first
    //pour un elire une par sa date de demande
    //la plus vieille sera choisi
    @Bean
    public Step checkListReservByBook()
    {
        return stepBuilders.get("checkListeReservationByBook")
                .tasklet(checkList())
                .build();
    }

    //
    @Bean
    public MethodInvokingTaskletAdapter checkList()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(reservationService());
        adapter.setTargetMethod("checkListReservForStatut");
        return adapter;
    }

    //verifie le delai de 48 h
    @Bean
    public Step checkDelai()
    {
        return stepBuilders.get("checkDelail")
                .tasklet(checkDelaiMethod())
                .build();
    }


    @Bean
    public MethodInvokingTaskletAdapter checkDelaiMethod()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(reservationService());
        adapter.setTargetMethod("checkDelai");
        return adapter;
    }


    @Bean
    public ReservationService reservationService() { return  new ReservationServiceImpl();

    }


}
