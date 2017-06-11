package com.spring3.demo.scheduler;

import com.spring3.demo.config.SpringWebConfig;
import com.spring3.demo.scheduler.jobs.JobA;
import com.spring3.demo.scheduler.jobs.JobB;
import com.spring3.demo.scheduler.jobs.JobC;
import com.spring3.demo.utils.SpringProvider;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author ajay.kg created on 11/06/17.
 */
@Slf4j
public class JobScheduler {

    private String jobACronExpression;

    private String jobBCronExpression;

    private String jobCCronExpression;

    private SchedulerFactory schedulerFactory;

    private Scheduler scheduler;

    private LeaderSelectionService leaderSelectionService;

    public JobScheduler(String jobACronExpression, String jobBCronExpression, String jobCCronExpression, SchedulerFactory factory,
            LeaderSelectionService leaderSelectionService) {

        this.jobACronExpression = jobACronExpression;
        this.jobBCronExpression = jobBCronExpression;
        this.jobCCronExpression = jobCCronExpression;
        this.schedulerFactory = factory;
        this.leaderSelectionService = leaderSelectionService;
    }

    @PostConstruct
    public void init() throws SchedulerException {
        System.out.println("Job A cron expression " + jobACronExpression);
        System.out.println("Job B cron expression " + jobBCronExpression);
        System.out.println("Job C cron expression " + jobCCronExpression);

//        leaderSelectionService.registerRunnableOnLeaderSelection("/abcd", testRunnable());


//        JobDetail sampleJob = JobBuilder.newJob(SampleCronJob.class).build();
        Trigger triggerA = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(jobACronExpression)).build();
        scheduler = schedulerFactory.getScheduler();
//        scheduler.scheduleJob(sampleJob, triggerA);

        scheduler.scheduleJob(JobBuilder.newJob(JobA.class).build(), TriggerBuilder.newTrigger().startNow().withSchedule(CronScheduleBuilder.cronSchedule(jobACronExpression)).build());
        scheduler.scheduleJob(JobBuilder.newJob(JobB.class).build(), TriggerBuilder.newTrigger().startNow().withSchedule(CronScheduleBuilder.cronSchedule(jobBCronExpression)).build());
        scheduler.scheduleJob(JobBuilder.newJob(JobC.class).build(), TriggerBuilder.newTrigger().startNow().withSchedule(CronScheduleBuilder.cronSchedule(jobCCronExpression)).build());

        scheduler.startDelayed(1);

    }



    @PreDestroy
    public void cleanUp() throws SchedulerException {
        scheduler.shutdown();
    }

    private Runnable testRunnable () {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("I am testRunnable");
            }
        };
    }


    public static class SampleCronJob implements Job {

        @Autowired
        LeaderSelectionService leaderSelectionService;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("execute job");
            leaderSelectionService.selectLeader("/abcd");
        }
    }

    public void executingLeaderSelection(String path){
        System.out.println("Executing leader selection cron - " + path);
        log.info("Executing leader selection cron - {}", path);
        leaderSelectionService.selectLeader(path);
    }

}
