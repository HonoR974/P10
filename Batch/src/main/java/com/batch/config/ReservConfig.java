package com.batch.config;


import com.batch.service.ReservationService;
import com.batch.service.ReservationServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservConfig {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Bean
    public Job reservBatch()
    {
        return jobBuilders.get("Reservation")
                .start(verifFirstUser())
                .build();
    }


    @Bean
    public Step verifFirstUser() {
        return stepBuilders.get("verifUser")
                .tasklet(verificationUser())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter verificationUser()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(reservationService());
        adapter.setTargetMethod("getFirstReserv");

        return adapter;
    }

    @Bean
    public ReservationService reservationService() { return  new ReservationServiceImpl();

    }


}
