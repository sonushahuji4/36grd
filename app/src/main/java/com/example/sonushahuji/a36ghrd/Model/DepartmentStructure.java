package com.example.sonushahuji.a36ghrd.Model;

public class DepartmentStructure
{
    private String department_id;
    private String department_name;
    private String getDepartment_description;
    private String getDepartment_photo;
    private String subscribe;

    public DepartmentStructure()
    {

    }


    public DepartmentStructure(String department_name, String getDepartment_description,String department_id,String getDepartment_photo,String subscribe)
    {
        this.department_name = department_name;
        this.getDepartment_description = getDepartment_description;
        this.department_id=department_id;
        this.getDepartment_photo = getDepartment_photo;
        this.subscribe=subscribe;
    }


    //getter


    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getGetDepartment_photo() {
        return getDepartment_photo;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getGetDepartment_description() {
        return getDepartment_description;
    }

/*    public int getGetDepartment_photo() {
        return getDepartment_photo;
    }*/

    //setter

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public void setGetDepartment_description(String getDepartment_description) {
        this.getDepartment_description = getDepartment_description;
    }

    public void setGetDepartment_photo(String getDepartment_photo) {
        this.getDepartment_photo = getDepartment_photo;
    }
}
