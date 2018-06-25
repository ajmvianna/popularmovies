package edu.nanodegreeprojects.popularmovies.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private String id;
    private String title;
    private String originalTitle;
    private String thumbnailPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;
    private String voteCount;
    private String originalLanguage;

    public Movie(String id, String title, String originalTitle, String thumbnailPath, String overview, String voteAverage, String releaseDate, String voteCount, String originalLanguage) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.thumbnailPath = thumbnailPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.originalLanguage = originalLanguage;
    }

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
}
