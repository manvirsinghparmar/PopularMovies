package com.manvirsingh.popularmovies.MovieTrailers;

public class MovieTrailer {

    private String id;
    private String key;
    private String name;
    private String type;
    private int size;
    private String url="https://www.youtube.com/watch?v="+key;

    public MovieTrailer(String id, String key, String name, String type, int size, String url) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.type = type;
        this.size = size;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MovieTrailer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "MovieTrailer{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                '}';
    }
}
