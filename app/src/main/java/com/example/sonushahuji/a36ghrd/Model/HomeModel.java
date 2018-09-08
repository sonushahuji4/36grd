package com.example.sonushahuji.a36ghrd.Model;

public class HomeModel
{
    private String department_name;
    private String title;
    private String message;
    private String date;
    private String link;
    private String type;
    //private int getDepartment_photo;

    public HomeModel()
    {

    }

    public HomeModel(String type,String department_name, String title, String message, String date,String link) {
        this.type=type;
        this.department_name = department_name;
        this.title = title;
        this.message = message;
        this.date = date;
        this.link= link;
        //this.getDepartment_photo = getDepartment_photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //getter


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

   /* public int getGetDepartment_photo() {
        return getDepartment_photo;
    }
*/
    //setter


    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

  /*  public void setGetDepartment_photo(int getDepartment_photo) {
        this.getDepartment_photo = getDepartment_photo;
    }*/
}
