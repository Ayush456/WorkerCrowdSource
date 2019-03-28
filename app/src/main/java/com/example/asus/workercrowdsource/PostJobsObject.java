package com.example.asus.workercrowdsource;

public class PostJobsObject {
    private String id;
    private String Address;
    private String EndDate;
    private String StartDate;
    private String Salary;
    private String Job;
    private String EstNoOfWorker;
    public PostJobsObject() {
    }
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getEstNoOfWorker() {
        return EstNoOfWorker;
    }

    public void setEstNoOfWorker(String estNoOfWorker) {
        EstNoOfWorker = estNoOfWorker;
    }


}
