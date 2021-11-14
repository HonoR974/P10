package com.batch.config;


import com.batch.service.PretService;
import com.batch.service.PretServiceImpl;
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

/**
 * ConfigurationBatch BatchConfig
 */

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {

  

    private static final String JOBNAME = "emailRetardJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    /**
     * Execute les steps pour l'envoie de mail selon la date de fin du pret
     * @return Execution de mail pour les prets proche de la date de fin
     */
    @Bean
    public Job rappel() {
        return jobBuilderFactory.get(JOBNAME)
                .start(verification())
                .next(changeStatutByDate())
                .next(getPret())
                .next(senMail())
                .build();
    }


    /**
     * Verifie les pret a valider / Call verif()
     * @return verif()
     */
    @Bean
    public Step verification()
    {
        return stepBuilderFactory.get("verification")
                .tasklet(verif())
                .build();
    }


    /**
     * Execute getPretEnCours
     * @return PretService.getPretEnCours()
     */
    @Bean
    public MethodInvokingTaskletAdapter verif()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("getPretEnCours");

        return adapter;
    }

    /**
     * Change le statut / Call changeStatut()
     * @return changeStatut()
     */
    @Bean
    public Step changeStatutByDate()
    {
        return stepBuilderFactory.get("ChangeStatut")
                .tasklet(changeStatut())
                .build();
    }

    /**
     * Execute sendPret
     * @return PretService.sendPret()
     */
    @Bean
    public MethodInvokingTaskletAdapter changeStatut()
    {

        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("sendPret");


        return adapter;
    }

    /**
     * Recupere les pret en retard / Call getPretRetard()
     * @return getPretRetard()
     */
    @Bean
    public Step getPret()
    {
        return this.stepBuilderFactory.get("getPretRetard")
                .tasklet(getPretRetard())
                .build();

    }

    /**
     * Executre getPretRetard
     * @return PretService.getPretRetard()
     */
    @Bean
    public MethodInvokingTaskletAdapter getPretRetard() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("getPretRetard");

        return adapter;
    }

    /**
     * Envoie les mails / Call sendMailService()
     * @return sendMailService()
     */
    @Bean
    public Step senMail()
    {
        return this.stepBuilderFactory.get("sendMail")
                .tasklet(sendMailService())
                .build();
    }

    /**
     * Execute sendMailRetard()
     * @return PretService.sendMailService()
     */
    @Bean
    public MethodInvokingTaskletAdapter sendMailService()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("sendMailRetard");

        return adapter;

    }


    /**
     * Bean PretService
     * @return PretServiceImpl
     */
    @Bean
    public PretService pretService()
    {
        return new PretServiceImpl();
    }



}
