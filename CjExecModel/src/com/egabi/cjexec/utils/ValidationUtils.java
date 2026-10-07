package com.egabi.cjexec.utils;

import java.util.Date;

/** Added by M.Salah **/
public class ValidationUtils {
    public ValidationUtils() {
        super();
    }

    public static boolean validateNationalId(String nationalId) {
        if (nationalId == null || nationalId.length() < 1 || nationalId.isEmpty()) {
            return true;
        } else if (nationalId != null) {
            nationalId = nationalId.trim();
            if (isValidNationalId(nationalId)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    
    public static boolean validatePassport(String Passport) {
        if(Passport != null && Passport.length() >= 1)
        if (Passport.length() <= 14 && Passport.length() > 8 ) //  && isValidPassportNo(Passport)
            return true;
        return false;
        
//        if (Passport == null || Passport.length() < 9 || Passport.isEmpty()) {
//            return true;
//        } else if (Passport != null) {
//            Passport = Passport.trim();
//            if (isValidPassportNo(Passport)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
    }
    
    

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 1 || phoneNumber.isEmpty()) {
            return true;
        } else if (phoneNumber != null) {
            phoneNumber = phoneNumber.trim();
            if (isValidPhoneNumber(phoneNumber)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.length() < 1 || email.isEmpty()) {
            return true;
        } else if (email != null) {
            email = email.trim();
            if (isValidEmail(email)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean validateYear(String year) {
        if (year == null || year.length() < 1 || year.isEmpty()) {
            return true;
        } else if (year != null) {
            year = year.trim();
            Date d = new Date();
            int currentYear = d.getYear() + 1900;
            if ((year.length() == 4 && (year.startsWith("19", 0) || year.startsWith("20", 0)) &&
                 Integer.parseInt(year) <= currentYear)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean validateMonth(String month) {
        if (month == null || month.length() < 1 || month.isEmpty()) {
            return true;
        } else if (month != null) {
            month = month.trim();
            if (Integer.parseInt(month) < 12 && Integer.parseInt(month) >= 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean validateDay(String day) {
        if (day == null || day.length() < 1 || day.isEmpty()) {
            return true;
        } else if (day != null) {
            day = day.trim();
            if (Integer.parseInt(day) <= 30 && Integer.parseInt(day) >= 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean validateArabicChar(String text) {
        if (text == null || text.length() < 1 || text.isEmpty()) {
            return true;
        } else if (text != null) {
            text = text.trim();
            if (isArabicChar(text)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean validatePositiveWithoutZero(String number) {
        if (number == null || number.length() < 1 || number.isEmpty()) {
            return true;
        } else if (number != null) {
            number = number.trim();
            if (isPositiveWithoutZero(number)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean validatePositiveWithZero(String number) {
        if (number == null || number.length() < 1 || number.isEmpty()) {
            return true;
        } else if (number != null) {
            number = number.trim();
            if (isPositiveWithZero(number)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /** 
     * 
     * (2|3) - century [1900-1999 : 2 | 2000 - 2099 : 3]
     * [0-9][0-9] - year
     * [0-1][0-9] - month
     * [0-3][0-9] - day
     * (01|02|03|04|11|12|13|14|15|16|17|18|19|21|22|23|24|25|26|27|28|29|31|32|33|34|35|88) - government code
     * next 4 digits - to make id number is unique for citizens with the same day and the same government
     * \d
     * \d
     * \d
     * \d - male: odd / female: even
     * \d - check digit
     * 
     * **/
    public static boolean isValidNationalId(String nationalId) {
        String regex =
            "(2|3)[0-9][0-9][0-1][0-9][0-3][0-9](01|02|03|04|11|12|13|14|15|16|17|18|19|21|22|23|24|25|26|27|28|29|31|32|33|34|35|88)\\d\\d\\d\\d\\d";
        return nationalId.matches(regex);
    }

    public static boolean isValidPhoneNumber(String phone) {
        String regex = "^(\\+201|01|00201)[0-2,5]{1}[0-9]{8}" ;
            //"^\\+(?:[0-9] ?){6,14}[0-9]$";
        
        // String regex = "(201)[0-9]{9}";
        return phone.matches(regex);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    
    public static boolean isValidPassportNo(String PassportNo) {
        String regex = "^[A-Z][A-Z0-9]{1}\\d{7,10}$"; // "^[A-Z][1-9]\\d\\s?\\d{4}[1-9]$";
        return PassportNo.matches(regex);
    }

    public static boolean isArabicChar(String text) {
        // String regex = "^[\u0621-\u064A\u0660-\u0669]+$";
        String regex = "^[\u0621-\u064A \\s]+$";
        // String regex = "/^[\u0600-\u06FF]*$/";
        return text.matches(regex);
    }

    public static boolean isPositiveWithoutZero(String number) {
        String regex = "^[1-9]\\d*$";
        return number.matches(regex);
    }

    public static boolean isPositiveWithZero(String number) {
        String regex = "^[0-9]\\d*$";
        return number.matches(regex);
    }

    public static void main(String[] args) {

        //        System.err.println(isValidDay("31"));
        //        System.err.println(isValidMonth("1"));

        //        "(2|3)[0-9][1-9][0-1][1-9][0-3][1-9](01|02|03|04|11|12|13|14|15|16|17|18|19|21|22|23|24|25|26|27|28|29|31|32|33|34|35|88)\\d\\d\\d\\d\\d"

        //        String nationalId = "27610151301791";
        //        System.out.println("The National ID is: " + nationalId);
        //        System.out.println("Is the above National ID valid? " + isValidNationalId(nationalId));

        //        String email = "";
        //        System.out.println("The E-mail ID is: " + email);
        //        System.out.println("Is the above E-mail ID valid? " + isValidEmail(email));
        //
        //        String phone = "";
        //        System.out.println("The phone is: " + phone);
        //        System.out.println("Is the above phone valid? " + isValidPhoneNumber(phone));
        //
        //        String text = "\u0645\u062d\u0645\u0648\u062f"; // \u0645\u062d\u0645\u0648\u062f
        //        System.out.println("The text is: " + text);
        //        System.out.println("Is the above phone valid? " + isArabicChar(text));

        //        Date d = new Date();
        //        System.out.println("***To get current year add 1900 to the value of year obtained from this date object***");
        //        int currentYear = d.getYear() + 1900;
        //        System.out.println("Current year is : " + currentYear);

        //        System.err.println(validatePositiveWithoutZero("-1"));
        //        System.err.println(validatePositiveWithZero("-1"));

    }

}
