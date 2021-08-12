package com.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * BatchLauncher Component
 */

@Component
public class BatchLauncher {

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("rappel")
    @Autowired
    private Job job;

    @Qualifier("reservBatch")
    @Autowired
    private Job jobReserv;
    /**
     * Execute Job.rappel()
     * @return Job - L'envoie de mail pour les prets en retard
     * @throws JobParametersInvalidException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     */
    public BatchStatus run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        System.out.println("\n ----------- batchLauncher ------------- ");
        JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        return jobExecution.getStatus();
    }

    /**
     * Execute Job.rappel()
     * @return Job - L'envoie de mail pour les prets en retard
     * @throws JobParametersInvalidException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     */
    public BatchStatus runReserve() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        System.out.println("\n ----------- batchLauncher Test  ------------- ");
        JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution = jobLauncher.run(jobReserv, parameters);
        return jobExecution.getStatus();
    }
}

