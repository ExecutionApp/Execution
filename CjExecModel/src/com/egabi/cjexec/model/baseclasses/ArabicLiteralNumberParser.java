package com.egabi.cjexec.model.baseclasses;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ArabicLiteralNumberParser
{

  private static final String ZERO = "\u0635\u0641\u0631";
  private static final String ONE = "\u0627\u0644\u0627\u0648\u0644";
  private static final String TWO = "\u0627\u0644\u062B\u0627\u0646\u0649";
  private static final String THREE = "\u0627\u0644\u062B\u0627\u0644\u062B";
  private static final String FOUR = "\u0627\u0644\u0631\u0627\u0628\u0639";
  private static final String FIVE = "\u0627\u0644\u062E\u0627\u0645\u0633";
  private static final String SIX = "\u0627\u0644\u0633\u0627\u062F\u0633";
  private static final String SEVEN = "\u0627\u0644\u0633\u0627\u0628\u0639";
  private static final String EIGHT = "\u0627\u0644\u062B\u0627\u0645\u0646";
  private static final String NINE = "\u0627\u0644\u062A\u0627\u0633\u0639";
  private static final String TEN = "\u0627\u0644\u0639\u0627\u0634\u0631";
  private static final String TWENTY = "\u0627\u0644\u0639\u0634\u0631\u0648\u0646";
  private static final String THIRTY = "\u0627\u0644\u062B\u0644\u0627\u062B\u0648\u0646";
  private static final String FOURTY = "\u0627\u0644\u0623\u0631\u0628\u0639\u0648\u0646";
  private static final String FIFTY = "\u0627\u0644\u062E\u0645\u0633\u0648\u0646";
  private static final String SIXTY = "\u0627\u0644\u0633\u062A\u0648\u0646";
  private static final String SEVENTY = "\u0627\u0644\u0633\u0628\u0639\u0648\u0646";
  private static final String EIGHTY = "\u0627\u0644\u062B\u0645\u0627\u0646\u0648\u0646";
  private static final String NINETY = "\u0627\u0644\u062A\u0633\u0639\u0648\u0646";
  private static final String HUNDRED = "\u0645\u0627\u0626\u0629";
  private static final String THOUSAND = "\u0627\u0644\u0641";
  private static final String MILLION = "\u0645\u0644\u064A\u0648\u0646";
  private static final String MILLIARD = "\u0645\u0644\u064A\u0627\u0631";
  private static final String BILLION = "\u0628\u0644\u064A\u0648\u0646";
  private static final String BILLIARD = "\u0628\u0644\u064A\u0627\u0631";
  private static final String TRILLION = "\u062A\u0631\u064A\u0644\u064A\u0648\u0646";
  private static final Map<Long, String> namesMap;


  static
  {

    namesMap = new HashMap<Long, String>();
    namesMap.put(0l, ZERO);
    namesMap.put(1l, ONE);
    namesMap.put(2l, TWO);
    namesMap.put(3l, THREE);
    namesMap.put(4l, FOUR);
    namesMap.put(5l, FIVE);
    namesMap.put(6l, SIX);
    namesMap.put(7l, SEVEN);
    namesMap.put(8l, EIGHT);
    namesMap.put(9l, NINE);
    namesMap.put(10l, TEN);
    namesMap.put(20l, TWENTY);
    namesMap.put(30l, THIRTY);
    namesMap.put(40l, FOURTY);
    namesMap.put(50l, FIFTY);
    namesMap.put(60l, SIXTY);
    namesMap.put(70l, SEVENTY);
    namesMap.put(80l, EIGHTY);
    namesMap.put(90l, NINETY);
    namesMap.put(100l, HUNDRED);
    namesMap.put(1000l, THOUSAND);
    namesMap.put(1000000l, MILLION);
    namesMap.put(1000000000l, MILLIARD);
    namesMap.put(1000000000000l, BILLION);
    namesMap.put(1000000000000000l, BILLIARD);
    namesMap.put(1000000000000000000l, TRILLION);
  }

  private static String parse(long a)
  {
    StringBuffer buf = new StringBuffer(Long.valueOf(a).toString());
    buf.reverse();
    int index = 0;
    boolean negative;
    int len = (negative = buf.charAt(buf.length() - 1) == '-')? buf.length() - 1: buf.length();

    String[] name = new String[len];
    long unitValue = 0;
    while(index < len)
      {
	Long n = Long.valueOf(String.valueOf(buf.charAt(index)));
	int decimalPos = index % 3;
	if(decimalPos == 0)
	  {
	    unitValue = (Double.valueOf(Math.pow(10, index)).longValue());
	  }
	int decimalPlace = (Double.valueOf(Math.pow(10, decimalPos)).intValue());
	switch(decimalPlace)
	  {
	  case 1:
	    if(unitValue > 1l && index + 1 == len)
	      {
		switch(n.intValue())
		  {
		  case 1:
		    name[index] = namesMap.get(unitValue);
		    break;
		  case 2:
		    name[index] = namesMap.get(unitValue).concat("\u0627\u0646");
		    break;
		  default:
		    if(unitValue == 1000l)
		      {
			name[index] = namesMap.get(n) + " " + "\u0627\u0644\u0627\u0641";
		      } else if(unitValue == 1000000l)
		      {
			name[index] = namesMap.get(n) + " " + "\u0645\u0644\u0627\u064A\u064A\u0646";
		      } else
		      {
			name[index] = namesMap.get(n) + " " + namesMap.get(unitValue) + "\u0627\u062A";
		      }
		  }
	      } else
	      {
		name[index] = namesMap.get(n);
	      }
	    break;
	  case 10:
	    String tmp = name[index - 1];
	    if(n == 1l)
	      {
		if(tmp.equals(ONE))
		  {
		    tmp = tmp.substring(1, tmp.length());
		  } else if(tmp.equals(TWO))
		  {
		    tmp = tmp.substring(0, tmp.length() - 1);
		  }
	      }

	    if(unitValue > 1l && index + 1 == len)
	      {
		if(n == 1 && tmp.equals(ZERO))
		  {
		    if(unitValue == 1000l)
		      {
			name[index] = TEN + "\u0627\u0644\u0627\u0641 ";
		      } else if(unitValue == 1000000l)
		      {
			name[index] = TEN + "\u0645\u0644\u0627\u064A\u064A\u0646 ";
		      } else
		      {

			name[index] = TEN + " " + namesMap.get(unitValue) + "\u0627\u062A";
		      }
		  } else
		  {
		    name[index] = namesMap.get(n * 10l) + " " + namesMap.get(unitValue);
		  }
	      } else
	      {
		name[index] = namesMap.get(n * 10l);
	      }
	    if(n != 0l)
	      {
		name[index - 1] = name[index];
		name[index] = tmp;
	      }

	    break;
	  case 100:

	    String s;
	    if(n > 2)
	      {
		s = namesMap.get(n);
		s = s.substring(0, s.length() - (n == 8? 2: 1)) + HUNDRED;
	      } else
	      {
		s = n == 2? "\u0645\u0627\u0626\u062A\u0627\u0646": namesMap.get(n * 100);
	      }
	    if(unitValue > 1l && !name[index - 2].equals(ZERO))
	      {
		Iterator i = namesMap.keySet().iterator();
		while(i.hasNext())
		  {
		    Long val = (Long) i.next();
		    if(namesMap.get(val).equals(name[index - 2]))
		      {
			if(val > 2l && val <= 10l)
			  {
			    if(unitValue == 1000l)
			      {
				name[index - 2] = name[index - 2] + " " + "\u0627\u0644\u0627\u0641";
			      } else if(unitValue == 1000000l)
			      {
				name[index - 2] = name[index - 2] + " " + "\u0645\u0644\u0627\u064A\u064A\u0646";
			      } else
			      {
				name[index - 2] = name[index - 2] + " " + namesMap.get(unitValue) + "\u0627\u062A";
			      }

			  } else
			  {
			    name[index - 2] = name[index - 2] + " " + namesMap.get(unitValue);
			  }
			break;
		      }
		  }

	      } else if(unitValue > 1l && n != 0)
	      {
		s = s + " " + namesMap.get(unitValue);
	      }

	    name[index] = s;

	  }
	index++;
      }


    String s = "";
    for(int i = 0; i < len; i++)
      {

	if(name[i].equals(ZERO))
	  {
	    continue;
	  }
	name[i] = name[i].trim();
	s = name[i] + (!s.isEmpty() && !(s.startsWith(TEN) && !name[i - 1].equals(ZERO))? " \u0648": " ") + s;

      }
    return s.isEmpty()? ZERO: (negative? "\u0633\u0627\u0644\u0628 " + s: s).trim();
  }

  public static String literalValueOf(Number a)
  {
    if(a.doubleValue() > Long.MAX_VALUE)
      {
	return " \u0627\u0643\u0628\u0631 \u0645\u0646 " + parse(Long.MAX_VALUE);
      }
    a = String.valueOf(a).toLowerCase().contains("e")? a.longValue(): a;

    String[] array = String.valueOf(a).split("\\.");

    long i = Long.valueOf(array[0]);
    long f = array.length == 2? Long.valueOf(array[1]): 0;

    int fractSize = f > 0? array[1].length(): 0;

    String integralPart = (i != 0 || f == 0)? parse(i): "";

    String fractionalPart =
      f > 0? parse(f) + " \u0645\u0646 " + parse(Double.valueOf(Math.pow(10, fractSize)).longValue()): "";

    return integralPart + (f * i != 0? " \u0648 ": "") + fractionalPart;
  }
  ///For testing, please remove!
  public static void main(String[] args)
  {
    System.out.println("\u0627\u0644\u0645\u062A\u0647\u0645 " + literalValueOf(24));
  }
}


