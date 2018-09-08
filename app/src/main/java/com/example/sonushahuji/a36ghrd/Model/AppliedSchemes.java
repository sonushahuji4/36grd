package com.example.sonushahuji.a36ghrd.Model;

public class AppliedSchemes
{
    private String appleid_id;
    private String appleid_scheme_name;
    private String appleid_status;
    private String appleid_others;
    private String applied_date;
    private String appicationstatus;
    private String deptname;

    public AppliedSchemes()
    {

    }

    public AppliedSchemes(String appleid_id,String deptname, String appleid_scheme_name,String appicationstatus, String appleid_status,String applied_date, String appleid_others)
    {
        this.appleid_id = appleid_id;
        this.deptname=deptname;
        this.appleid_scheme_name = appleid_scheme_name;
        this.appicationstatus=appicationstatus;
        this.appleid_status = appleid_status;
        this.applied_date = applied_date;
        this.appleid_others = appleid_others;
    }

    public String getAppicationstatus() {
        return appicationstatus;
    }

    public void setAppicationstatus(String appicationstatus) {
        this.appicationstatus = appicationstatus;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getAppleid_id() {
        return appleid_id;
    }

    public void setAppleid_id(String appleid_id) {
        this.appleid_id = appleid_id;
    }

    public String getAppleid_scheme_name() {
        return appleid_scheme_name;
    }

    public void setAppleid_scheme_name(String appleid_scheme_name) {
        this.appleid_scheme_name = appleid_scheme_name;
    }

    public String getAppleid_status() {
        return appleid_status;
    }

    public void setAppleid_status(String appleid_status) {
        this.appleid_status = appleid_status;
    }

    public String getAppleid_others() {
        return appleid_others;
    }

    public void setAppleid_others(String appleid_others) {
        this.appleid_others = appleid_others;
    }

    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_date) {
        this.applied_date = applied_date;
    }
}
