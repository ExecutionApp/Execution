package com.egabi.jobs;

import java.io.Serializable;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class SendToSmsListener
  implements ServletContextListener, Serializable
{
  Scheduler scheduler = null;

  @Override
  public void contextInitialized(ServletContextEvent ctx)
  {
    System.out.println(new Date() + " : inside contextInitialized method");
//    NtraDBOperation operation = new NtraDBOperation();
    try
    {
      JobDetail job = new JobDetail();
      job.setName("SendToSmsJob");
      job.setJobClass(SendToSmsJob.class);

      //configure the scheduler time
      SimpleTrigger trigger = new SimpleTrigger();
      trigger.setName("trigger");
      trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
      trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
      
      //16/01/2020
      //trigger.setRepeatInterval(60000);//1 MINUTE
//      int repeatInterval = operation.getNtraRepeatInterval();
      trigger.setRepeatInterval(1 * 60 * 1000 );//15 MINUTES 900000

      //schedule it
      scheduler = new StdSchedulerFactory().getScheduler();

      scheduler.start();
      scheduler.scheduleJob(job, trigger);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent event)
  {
    try
    {
      System.out.println(new Date() + " : inside contextDestroyed method");
      scheduler.shutdown();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }


  public static void main(String[] args)
  {
    try
    {
      JobDetail job = new JobDetail();
      job.setName("SendToNtraJob");
      job.setJobClass(SendToSmsJob.class);

      //configure the scheduler time
      SimpleTrigger trigger = new SimpleTrigger();
      trigger.setName("trigger");
      trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
      trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
      trigger.setRepeatInterval(10000);

      //schedule it
      Scheduler sched = new StdSchedulerFactory().getScheduler();

      sched.start();
      sched.scheduleJob(job, trigger);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}