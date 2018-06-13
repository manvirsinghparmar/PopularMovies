package com.manvirsingh.popularmovies.MovieReviews;

public class MovieReview  {



    private String author;
    private String content;

    public MovieReview(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public MovieReview() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MovieReview{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }



}
