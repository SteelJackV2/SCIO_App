package com.example.scio;

public class Challenge {

    String name;
    String Author;
    String Description;
    Long Completitions;
    Long Donations;

    public Challenge() {
    }

    public Challenge(String name, String Author, String Description, Long Completitions, Long Donations) {
        this.name = name;
        this.Author = Author;
        this.Description = Description;
        this.Completitions = Completitions;
        this.Donations = Donations;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Long getCompletitions() {
        return Completitions;
    }

    public void setCompletitions(Long completitions) {
        Completitions = completitions;
    }

    public Long getDonations() {
        return Donations;
    }

    public void setDonations(Long donations) {
        Donations = donations;
    }
}
