package kz.pifagor.news.archive.controller;

import kz.pifagor.news.archive.model.News;
import kz.pifagor.news.archive.beans.NewsRepository;
import kz.pifagor.news.archive.exceptions.NewsNotFoundException;
import kz.pifagor.news.archive.model.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

@RestController
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    /**
     * Search news by key or cityId
     * @param keyword It must like title or content
     * @param cityId Id of the city
     * @return List of news
     * @throws IOException
     */
    @GetMapping(value = "news/search", produces = {"application/json"})
    public @ResponseBody Page<News> searchNews(@RequestParam(value="keyword", required=false) String keyword,
                                               @RequestParam(value="cityId", required=false) Long cityId, Pageable pageable) {
        Page<News> news = null;

        if(cityId == null)
            news = newsRepository.findByTitleContent(keyword, pageable);
        else if( keyword == null )
            news = newsRepository.findByCityId(cityId, pageable);
        else
            news = newsRepository.findByTitleContentCity(keyword, cityId, pageable);

        return news;
    }

    /**
     * Create new news
     * @param newsDTO Created news
     * @return  Current news
     */
    @PostMapping(value = "news", produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody News addNews(@Valid @RequestBody NewsDTO newsDTO) throws MethodArgumentNotValidException {
        News news = News.constructNews(newsDTO);
        news.setCreated(new Date());
        return newsRepository.save(news);
    }

    /**
     * Displays current news
     * @param id Id of News
     * @return Type News
     */
    @GetMapping(value = "news/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody News getNews(@PathVariable("id") Long id) throws NewsNotFoundException {
        return newsRepository.findById(id).orElseThrow( () -> new NewsNotFoundException("Unable to find News with id " + id ));
    }

    /**
     * Update current news
     * @param id Id of News
     * @param newsDTO News that will be updated
     * @return News
     */
    @PutMapping(value = "news/{id}", produces = {"application/json"} )
    public @ResponseBody News updateNews(@PathVariable Long id, @Valid @RequestBody NewsDTO newsDTO) throws NewsNotFoundException {
        News currentNews = newsRepository.findById(id).orElseThrow( () -> new NewsNotFoundException("Unable to find News with id " + id ));
        News updatedNews = News.constructNews(currentNews, newsDTO);
        return newsRepository.save(updatedNews);
    }

}
