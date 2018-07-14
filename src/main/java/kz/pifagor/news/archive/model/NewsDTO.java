package kz.pifagor.news.archive.model;

import javax.validation.constraints.NotNull;

public class NewsDTO {

    /**
     * City id
     */
    @NotNull
    private Long cityId;

    /**
     * The news author
     */
    @NotNull
    private Long author;

    /**
     *  Title of news
     */
    @NotNull
    private String title;

    /**
     * The content of news
     */
    @NotNull
    private String content;

    /**
     * News is deleted
     */
    @NotNull
    private int deleted;

    /**
     * The teacher who received the news
     */
    @NotNull
    private Long forTeacher;

    /**
     * Photo of the news
     */
    @NotNull
    private String photo;

    public static final NewsDTO constructNewsDTO(News news){
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setCityId(news.getCityId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setContent(news.getContent());
        newsDTO.setAuthor(news.getAuthor());
        newsDTO.setForTeacher(news.getForTeacher());
        newsDTO.setDeleted(news.getDeleted());
        newsDTO.setPhoto(news.getPhoto());
        return newsDTO;
    }

    public NewsDTO() {
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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
