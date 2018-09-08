package com.example.sonushahuji.a36ghrd.Model;

public class Eligibility
{
    private String elg_id;
    private String elg_title;
    private String elg_description;
    private String elg_instance_view;
    private String elg_link;
    private String elg_date;
    private String link;

    public Eligibility()
    {

    }

    public Eligibility(String elg_id, String elg_title, String elg_description, String elg_link,String link, String elg_date) {
        this.elg_id = elg_id;
        this.elg_title = elg_title;
        this.elg_description = elg_description;
        this.link=link;
       // this.elg_instance_view = elg_instance_view;
        this.elg_link = elg_link;
        this.elg_date = elg_date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getElg_id() {
        return elg_id;
    }

    public void setElg_id(String elg_id) {
        this.elg_id = elg_id;
    }

    public String getElg_title() {
        return elg_title;
    }

    public void setElg_title(String elg_title) {
        this.elg_title = elg_title;
    }

    public String getElg_description() {
        return elg_description;
    }

    public void setElg_description(String elg_description) {
        this.elg_description = elg_description;
    }

    public String getElg_instance_view() {
        return elg_instance_view;
    }

    public void setElg_instance_view(String elg_instance_view) {
        this.elg_instance_view = elg_instance_view;
    }

    public String getElg_link() {
        return elg_link;
    }

    public void setElg_link(String elg_link) {
        this.elg_link = elg_link;
    }

    public String getElg_date() {
        return elg_date;
    }

    public void setElg_date(String elg_date) {
        this.elg_date = elg_date;
    }
}
