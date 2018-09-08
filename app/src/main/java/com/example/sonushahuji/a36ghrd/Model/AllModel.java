package com.example.sonushahuji.a36ghrd.Model;

public class AllModel
{
    private String scId;
    private String all_title;
    private String all_description;
    private String all_link;
    //private String all_instanceview;
    private String all_date;
    private String all_date2;

    public AllModel()
    {

    }

    public AllModel(String all_title, String all_description, String all_link, /*String all_instanceview,*/String all_date,String all_date2,String scId ) {
        this.all_title = all_title;
        this.all_description = all_description;
        this.all_link = all_link;
        this.all_date=all_date;
        this.all_date2 = all_date2;
        this.scId=scId;
        //this.all_instanceview = all_instanceview;
    }

    //getter


    public String getAll_date2() {
        return all_date2;
    }

    public void setAll_date2(String all_date2) {
        this.all_date2 = all_date2;
    }

    public String getAll_date() {
        return all_date;
    }

    public void setAll_date(String all_date) {
        this.all_date = all_date;
    }

    public String getAll_title() {
        return all_title;
    }

    public void setAll_title(String all_title) {
        this.all_title = all_title;
    }

    public String getAll_description() {
        return all_description;
    }

    public void setAll_description(String all_description) {
        this.all_description = all_description;
    }

    public String getAll_link() {
        return all_link;
    }

    public void setAll_link(String all_link) {
        this.all_link = all_link;
    }

   /* public String getAll_instanceview() {
        return all_instanceview;
    }*/

   /* public void setAll_instanceview(String all_instanceview) {
        this.all_instanceview = all_instanceview;
    }*/

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }
}
