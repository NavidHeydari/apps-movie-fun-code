package org.superbiz.moviefun;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomeController {


    MoviesBean repository;

    public HomeController( MoviesBean movieBean){
            this.repository = movieBean;
    }


    //make an end point in this controller for index.jsp part.
    @RequestMapping("/")
    public String getHome(){
        //removing jsp from it
//        return ModelAndView(new HashMap(), "index");
        return "index";
    }

//    <%
//    InitialContext initialContext = new InitialContext();
//    MoviesBean repository = (MoviesBean) initialContext.lookup("java:comp/env/org.superbiz.moviefun.ActionServlet/repository");
//
//  repository.addMovie(new Movie("Wedding Crashers", "David Dobkin", "Comedy", 7, 2005));
//  repository.addMovie(new Movie("Starsky & Hutch", "Todd Phillips", "Action", 6, 2004));
//  repository.addMovie(new Movie("Shanghai Knights", "David Dobkin", "Action", 6, 2003));
//  repository.addMovie(new Movie("I-Spy", "Betty Thomas", "Adventure", 5, 2002));
//  repository.addMovie(new Movie("The Royal Tenenbaums", "Wes Anderson", "Comedy", 8, 2001));
//  repository.addMovie(new Movie("Zoolander", "Ben Stiller", "Comedy", 6, 2001));
//  repository.addMovie(new Movie("Shanghai Noon", "Tom Dey", "Comedy", 7, 2000));
//%>

//    @RequestMapping("/setup")
    @GetMapping("/setup")
    public String getSetupJSP(Map<String, Object> model){

        repository.addMovie(new Movie("Wedding Crashers", "David Dobkin", "Comedy", 7, 2005));
        repository.addMovie(new Movie("Starsky & Hutch", "Todd Phillips", "Action", 6, 2004));
        repository.addMovie(new Movie("Shanghai Knights", "David Dobkin", "Action", 6, 2003));
        repository.addMovie(new Movie("I-Spy", "Betty Thomas", "Adventure", 5, 2002));
        repository.addMovie(new Movie("The Royal Tenenbaums", "Wes Anderson", "Comedy", 8, 2001));
        repository.addMovie(new Movie("Zoolander", "Ben Stiller", "Comedy", 6, 2001));
        repository.addMovie(new Movie("Shanghai Noon", "Tom Dey", "Comedy", 7, 2000));


        model.put("movies",repository.getMovies());

        return "setup";
    }


}
