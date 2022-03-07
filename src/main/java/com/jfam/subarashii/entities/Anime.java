package com.jfam.subarashii.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jfam.subarashii.utils.Constantes;
import com.jfam.subarashii.utils.Helpers;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "animes")
@JsonIgnoreProperties(value = { "episodes"},allowSetters = true)
public class Anime {

    @Id
    @GeneratedValue
    private Long id;

    private Long idApi;

    private String nom;

    private Long nbSaison;

    private Long nbEpisodes;

    private String image;

    @Column(columnDefinition = "text")
    private String description;

    private Float note;

    @OneToMany
    @JoinColumn( name = "anime")
    private List<Comment> comments;


    @OneToMany(mappedBy = "anime")
    private List<Episode> episodes;


    @ManyToMany(mappedBy = "animes")
    @JsonBackReference
    private List<UserList> userLists;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "anime_genres",
            joinColumns = @JoinColumn(name = "animeId"),
            inverseJoinColumns = {@JoinColumn(name = "genreId")}
    )
    private List<Genre> genres;

    private String backgroundPath;

    public Anime(){}

    public Anime(JsonObject jsonObject) {
        this.idApi = jsonObject.get("id").getAsLong();
        this.nom = jsonObject.get("name").getAsString();
        this.nbSaison = jsonObject.get("number_of_seasons").getAsLong();
        this.nbEpisodes = jsonObject.get("number_of_episodes").getAsLong();
        this.description = jsonObject.get("overview").getAsString();
        this.note = jsonObject.get("vote_average").getAsFloat();

        JsonElement jsonPosterPath = jsonObject.get("poster_path");
        this.image =  jsonPosterPath == null ? Constantes.URL_IMAGE_NOT_FOUND : Constantes.ApiMovie.URL_IMAGE +  jsonObject.get("poster_path").getAsString();

        JsonElement jsonBackgroundPath= jsonObject.get("backdrop_path");
        this.backgroundPath =  jsonBackgroundPath == null ? Constantes.URL_IMAGE_NOT_FOUND : Constantes.ApiMovie.URL_IMAGE_ORIGNE +  jsonObject.get("backdrop_path").getAsString();
    }

    //region  === getter-setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdApi() {
        return idApi;
    }

    public void setIdApi(Long idApi) {
        this.idApi = idApi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getNbSaison() {
        return nbSaison;
    }

    public void setNbSaison(Long nbSaison) {
        this.nbSaison = nbSaison;
    }

    public Long getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(Long nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getNote() {
        return note;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<UserList> getUserLists() {
        return userLists;
    }

    public void setUserLists(List<UserList> userLists) {
        this.userLists = userLists;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    //endregion
}
