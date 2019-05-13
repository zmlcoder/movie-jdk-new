import com.mingliz.movie.model.service.IMovieService;

/**
 * @author zml
 *
 */
module movie.model {

   exports com.mingliz.movie.model;
   exports com.mingliz.movie.model.service;

   uses IMovieService;
}