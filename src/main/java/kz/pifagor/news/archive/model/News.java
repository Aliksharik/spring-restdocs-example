package kz.pifagor.news.archive.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="news")
public class News implements Serializable{

    /**
     * Id of the news
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" ,nullable = false)
    private Long id;

    /**
     * City id
     */
    @Column(name = "city_id")
    private Long cityId;

    /**
     * Created day
     */
    @Column(name = "created")
    private Date created;

    /**
     * The news author
     */
    @Column(name = "author")
    private Long author;

    /**
     *  Title of news
     */
    @Column(name = "title")
    private String title;

    /**
     * The content of news
     */
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    /**
     * News is deleted
     */
    @Column(name = "deleted")
    private int deleted;

    /**
     * The teacher who received the news
     */
    @Column(name = "for_teacher")
    private Long forTeacher;

    /**
     * Photo of the news
     */
    @Column(name = "photo")
    private String photo;

    public static final News constructNews(News news, NewsDTO newsDTO){
        news.setCityId(newsDTO.getCityId());
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setAuthor(newsDTO.getAuthor());
        news.setForTeacher(newsDTO.getForTeacher());
        news.setDeleted(newsDTO.getDeleted());
        news.setPhoto(newsDTO.getPhoto());
        return news;
    };

    public static final News constructNews(NewsDTO newsDTO){
        return constructNews(new News(), newsDTO);
    };

    public News(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Long getForTeacher() {
        return forTeacher;
    }

    public void setForTeacher(Long forTeacher) {
        this.forTeacher = forTeacher;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
