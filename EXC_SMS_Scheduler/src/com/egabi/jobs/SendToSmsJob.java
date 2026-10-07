package com.egabi.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendToSmsJob implements Job{
    
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext)
//      throws JobExecutionException
//    {
//      System.out.println(new Date() + " :starting Add New Case Job ");
//    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // TODO Implement this method
        System.out.println(new Date() + " :Starting Sending SMS Job ");
        CallSendSMSOTPServiceClient client = new CallSendSMSOTPServiceClient();
        client.callSms();
 
    }
}
