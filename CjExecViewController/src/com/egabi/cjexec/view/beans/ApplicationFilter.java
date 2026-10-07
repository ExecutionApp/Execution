package com.egabi.cjexec.view.beans;

import com.egabi.cjexec.view.beans.security.UserInfo;

import java.io.IOException;

import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApplicationFilter
  implements Filter
{
  private FilterConfig _filterConfig = null;

  public void init(final FilterConfig filterConfig) throws ServletException
  {
    _filterConfig = filterConfig;
  }

  public void destroy()
  {
    _filterConfig = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException
  {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    HttpSession session = req.getSession(true);
    String uri = req.getRequestURI();
//    System.err.println(uri);
    final boolean isWelcomePage = uri.contains("faces") && (uri.contains("Welcome"));

    res.setBufferSize(516166);

    String loginId = req.getParameter("loginid");
    String sessionLoginId = (String) session.getAttribute("loginId");
    Boolean isSignedIn = (Boolean) session.getAttribute("isUserSignedIn");
    //    System.err.println("xloginId              :: " + loginId);
    //    System.err.println("xsession loginId      :: " + sessionLoginId);
    //    System.err.println("xsession isSignedIn   :: " + isSignedIn);
    //    System.err.println("xURI                  :: " + uri);
    //    System.err.println("xSessionId            :: " + session.getId());

    //////
    // Go error page
    if(uri.contains("AuthenticationError"))
      {
	chain.doFilter(request, response);
	return;
      } else if(uri.contains("Logout"))
      {
	chain.doFilter(request, response);
	return;
      }

    //////
    // Error
    // No url parametars and session parametars
    else if(loginId == null && sessionLoginId == null)
      {
	session.setAttribute("loginErrorMsg", "Url is wrong");
	res.sendRedirect(session.getServletContext().getContextPath() + "/faces/AuthenticationError");
      }

    //////
    // First request for login from outside
    // Session's attributes not initialized/created yet
    // Check URL parametars (LoginId) and store in serssion
    else if(loginId != null)
      {
	// Detect Welcome page URL correctly
	if(isWelcomePage)
	  {
	    // Another user login request in the same session
	    if(sessionLoginId != null && !sessionLoginId.equals(loginId))
	      {
		session.setAttribute("loginId", loginId);
		chain.doFilter(request, response);
	      } else
	      {
		// New user login request

		if(sessionLoginId == null || isSignedIn == null)
		  {
		    session.setAttribute("loginId", loginId);
		    chain.doFilter(request, response);
		  } else if(sessionLoginId.equals(loginId))
		  {
		    chain.doFilter(request, response);
		  }
	      }
	  }
      }

    //////
    // Go same page with same loginId
    // user is already logined
    else if(isSignedIn != null && sessionLoginId != null && isSignedIn)
      {
	// Go now	
	HashMap<String, Integer> pageKeys = pagesHmap(session);
	if(pageKeys.containsKey(getPageNameFromUri(uri)))
	  chain.doFilter(request, response);
	else
	  res.sendRedirect(session.getServletContext().getContextPath() + "/faces/Welcome?loginid=" + sessionLoginId);
      }
  }

  private String getPageNameFromUri(String uri)
  {
    String page = null;
    page = uri.substring(uri.indexOf("/faces"), uri.length());
    return page;
  }

  //  private boolean hasPrivlages(HttpSession session, int functionId)
  //  {
  //    return ((UserInfo) session.getAttribute("userInfo")).getUserPrevMap().get(functionId);
  //  }

  private HashMap<String, Integer> pagesHmap(HttpSession session)
  {
    HashMap<String, Integer> pagesPrivsFunc;
    pagesPrivsFunc = ((UserInfo) session.getAttribute("userInfo")).getPagesHmap();
    pagesPrivsFunc.put("/faces/Welcome", 1);
    pagesPrivsFunc.put("/faces/adf.dialog-request", -1);
    pagesPrivsFunc.put("/faces/WebCamTF/WebCamFrag", -2);
    pagesPrivsFunc.put("/faces/nIdServiceTF/nIdServiceFrag", -3);
    return pagesPrivsFunc;
  }

}
