package com.mingliz.movie.model.service;

import java.util.List;
import java.util.ServiceLoader;

import com.mingliz.movie.model.Movie;

public interface IMovieService {
   List<Movie> search(String searchText);

   public static IMovieService createInstance() {
      var loader = ServiceLoader.load(IMovieService.class);
      return loader.findFirst().get();
   }
}
