package com.mingliz.movie.service.douban;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mingliz.movie.model.Movie;
import com.mingliz.movie.model.service.IMovieService;

public class DoubanMovieService implements IMovieService {

   private HttpClient httpClient;

   public DoubanMovieService() {
      init();
   }

   private void init() {
      httpClient = HttpClient.newHttpClient();
   }

   @Override
   public List<Movie> search(String searchText) {

      var resultList = new ArrayList<Movie>();

      var url = "https://movie.douban.com/j/subject_suggest?q=" + searchText;
      var request = HttpRequest.newBuilder().uri(URI.create(url)).build();

      httpClient.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(jsonStr -> {
         var jsonArray = new JSONArray(jsonStr);

         jsonArray.forEach(json -> {
            var jsonObject = (JSONObject) json;
            var movie = new Movie();
            movie.setId(jsonObject.getString("id"));
            movie.setName(jsonObject.getString("title"));
            movie.setImage(jsonObject.getString("img"));
            movie.setDesc(jsonObject.getString("sub_title"));
            resultList.add(movie);
         });

      }).join();

      return resultList;
   }

}
