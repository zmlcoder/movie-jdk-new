import com.mingliz.movie.model.service.IMovieService;
import com.mingliz.movie.service.douban.DoubanMovieService;

/**
 * @author zml
 *
 */
module movie.service.impl {
   requires java.net.http;
   requires org.json;

   requires transitive movie.model;

   provides IMovieService with DoubanMovieService;
}