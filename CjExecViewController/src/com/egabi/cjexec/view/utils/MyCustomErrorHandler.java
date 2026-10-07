package com.egabi.cjexec.view.utils;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCErrorHandlerImpl;

import java.sql.SQLException;

import java.sql.SQLIntegrityConstraintViolationException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCErrorHandlerImpl;

import oracle.jbo.DMLConstraintException;
import oracle.jbo.JboException;
import oracle.jbo.ReadOnlyViewObjectException;



public class MyCustomErrorHandler extends DCErrorHandlerImpl {

    public MyCustomErrorHandler() {

        this(true);

    }


    public MyCustomErrorHandler(boolean setToThrow) {

        super(setToThrow);

    }


    @Override

    public void reportException(DCBindingContainer dCBindingContainer,

        Exception exception) {

        super.reportException(dCBindingContainer, exception);

    }


    public String getDisplayMessage(BindingContext ctx, Exception ex) {

System.out.println("getDisplayMessage--------------------------");
        String message = ""; 

        if (ex instanceof oracle.jbo.ValidationException) {

           
            String msg = ex.getMessage();
          
//            int i = msg.indexOf("JBO-25013"); //When JBO-25013 Too many object match promary key exception occur.

            message ="Duplicate PK ID";
//                getDisplayMessage(ctx, ex);

        }
   
        else if (ex instanceof SQLIntegrityConstraintViolationException
            || ex instanceof DMLConstraintException
        ) {
    // FK Error & Unique Error
//            String msg = ex.getMessage();
//         System.out.println("mssssssssssssssssssg = "+msg);
//         if(msg.contains("ORA-00001"))
//            message ="Unique Field ";
//         else if (msg.contains("ORA-02291"))
            message="Invalid Input";
       
//                getDisplayMessage(ctx, ex);

        }
        
       
        
        else if(ex instanceof java.sql.SQLException || ex instanceof oracle.jbo.DMLException){
         message = "Null not expected"  ; 
        }
        else if (ex instanceof oracle.jbo.JboException || ex instanceof NullPointerException
        ) {
            
//                message ="Attr. Error ";
                message ="\u064a\u0648\u062c\u062f \u062e\u0637\u0627 \u002e\u002e\u002e\u0628\u0631\u062c\u0627\u0621 \u0645\u0631\u0627\u062c\u0639\u0629 \u0645\u062f\u064a\u0631 \u0627\u0644\u0646\u0638\u0627\u0645";
            
            }
        else if(ex instanceof java.sql.SQLRecoverableException){
                message = "Closed Connection";
            }
        
        else

        {
            String msg = ex.getMessage();
            
//            int i = msg.indexOf("ORA-02291");

            message = "Any Exception That I Don't Handle It ";

        }

        return message;

    }

}
