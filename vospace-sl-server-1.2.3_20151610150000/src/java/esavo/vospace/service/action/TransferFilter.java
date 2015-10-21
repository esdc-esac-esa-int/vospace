package esavo.vospace.service.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.service.utils.access.UserUtils;

/**
 * This filter checks the authentication before accessing 
 * servlet '/transfers'
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class TransferFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        
        UserTO user = null;
        try{
            user = UserUtils.getCurrentContextUser();
        } catch(Exception e) { /*e.printStackTrace();*/ }
        if (user == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 404
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
