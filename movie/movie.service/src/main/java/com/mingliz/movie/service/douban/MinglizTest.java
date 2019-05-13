package com.mingliz.movie.service.douban;

import com.mingliz.movie.model.Movie;

public class MinglizTest {

   public static void main(String[] args) {
      var movieService = new DoubanMovieService();
      for (Movie movie : movieService.search("me")) {
         System.out.println(movie.getName());
      }
   }

}
