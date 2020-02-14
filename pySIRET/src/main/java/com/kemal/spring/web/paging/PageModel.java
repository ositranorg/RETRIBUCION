package com.kemal.spring.web.paging;

import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class PageModel {
    private  int PAGE = 0;
    private  int SIZE = 5;

    private HttpServletRequest request;

    public PageModel(HttpServletRequest request) {
        this.request = request;
    }

    public void initPageAndSize(){
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
        	this.PAGE = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
        	this.PAGE = Integer.parseInt(request.getParameter("size"));
        }
    }

    public  void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public  int getPAGE() {
        return this.PAGE;
    }

    public  int getSIZE() {
        return this.SIZE;
    }
}
