package com.egabi.cjexec.view.utils;

import com.egabi.cjexec.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.trinidad.model.UploadedFile;

/** Added by M.Salah **/
public class CJValidator extends ValidationUtils {

    public CJValidator() {
        super();
    }

    public void nationalIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validateNationalId(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "NATIONAL_ID_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }
    
    
    public void PassportValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if(object != null )
        if (  !ValidationUtils.validatePassport(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "PASSPORT_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void phoneNumberValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validatePhoneNumber(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "PHONE_NUMBER_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validateEmail(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "EMAIL_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void fileFormatValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;

        List<String> formatsList = new ArrayList<String>();
        formatsList = (List<String>) JSFUtils.getFromSession("acceptedFormats");
        System.err.println("validate bean --> formatsList : " + formatsList);
        //formats.add("pdf");
        UploadedFile file = null;
        if (object != null) {
            file = (UploadedFile) object;
            if (!formatsList.contains(getFileExtension(file))) {
                fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "FILE_FORMAT_VALIDATOR"));
                throw new ValidatorException(fm);
            }
        }
    }

    public String getFileExtension(UploadedFile file) {
        if (file != null) {
            String fileName = file.getFilename() == null ? "" : file.getFilename();
            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                return fileName.substring(fileName.lastIndexOf(".") + 1);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public void yearValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validateYear(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "YEAR_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void monthValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validateMonth(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "MONTH_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void dayValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validateDay(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "DAY_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void arabicCharValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validateArabicChar(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "ARABIC_CHAR_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void positiveWithoutZeroValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validatePositiveWithoutZero(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "POSITIVE_WITHOUT_ZERO_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public void positiveWithZeroValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // TODO Implement this method
        FacesMessage fm = null;
        if (object != null && !ValidationUtils.validatePositiveWithZero(object.toString())) {
            fm = new FacesMessage(JSFUtils.getFromBundle("bundle", "POSITIVE_WITH_ZERO_VALIDATOR"));
            throw new ValidatorException(fm);
        }
    }

    public static void main(String[] args) {
        //        File file = new File("‪C://Users//Mahmoud//Desktop//Query.pdf");
        //        System.out.println("File extension is: " + getFileExtension(file));
        //        List<String> formats = new ArrayList<String>();
        //        formats.add("pdf");
        //        System.err.println(formats.contains(getFileExtension(file)));
        //        //file name without extension
        //        file = new File("/Users/pankaj/temp");
        //        System.out.println("File extension is: " + getFileExtension(file));
        //        //file name with dot
        //        file = new File("/Users/pankaj/java.util.txt");
        //        System.out.println("File extension is: " + getFileExtension(file));
        //        //hidden files without extension
        //        file = new File("/Users/pankaj/.htaccess");
        //        System.out.println("File extension is: " + getFileExtension(file));
    }

    public void yearVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        JSFUtils.addToSession("pYear", valueChangeEvent.getNewValue()+"");
    }

    public void origEntityVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.addToSession("pPolice", valueChangeEvent.getNewValue()+"");
    }
}
