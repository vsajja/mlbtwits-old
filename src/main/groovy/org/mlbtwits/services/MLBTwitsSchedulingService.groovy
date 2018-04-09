package org.mlbtwits.services

import com.google.inject.Inject
import com.google.inject.Injector
import org.mlbtwits.jobs.MLBPlayerNewsFeed
import org.mlbtwits.jobs.RotoworldFeed
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.service.StopEvent

import static org.quartz.SimpleScheduleBuilder.simpleSchedule

class MLBTwitsSchedulingService implements Service {
    final Logger log = LoggerFactory.getLogger(this.class)
    Scheduler scheduler

    @Inject
    Injector injector

    public void onStart(StartEvent event) {
        log.info('STARTED')

        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.setJobFactory(injector.getInstance(GuiceJobFactory.class));
        scheduler.start();

        JobDetail rotoworldJob = JobBuilder.newJob(RotoworldFeed.class)
                .withIdentity('RotoworldFeed', 'group1')
                .build();

        JobDetail mlbPlayerNewsJob = JobBuilder.newJob(MLBPlayerNewsFeed.class)
                .withIdentity('MLBPlayerNewsFeed', 'group1')
                .build();

        Trigger rotoworldJobTrigger = TriggerBuilder.newTrigger()
                .withIdentity('RotoworldFeedTrigger', 'group1')
                .startNow()
                .withSchedule(simpleSchedule()
                .withIntervalInMinutes(5)
                .repeatForever())
                .build();

        Trigger mlbPlayerNewsJobTrigger = TriggerBuilder.newTrigger()
                .withIdentity('MLBPlayerNewsFeedTrigger', 'group1')
                .startNow()
                .withSchedule(simpleSchedule()
                .withIntervalInMinutes(5)
                .repeatForever())
                .build();

        scheduler.scheduleJob(rotoworldJob, rotoworldJobTrigger);
//        scheduler.scheduleJob(mlbPlayerNewsJob, mlbPlayerNewsJobTrigger);
    }

    public void onStop(StopEvent event) {
        scheduler.clear()
        log.info('STOPPED')
    }
}
