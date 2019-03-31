package com.example.asus.workercrowdsource;

public class EnrolledJobs {

    private String Address;
    private String EndDate;
    private String EstNoOfWorker;
    private String Job;
    private String Salary;
    private String StartDate;


    public EnrolledJobs() {
    }

    public EnrolledJobs(String address, String endDate, String estNoOfWorker, String job, String salary, String startDate) {
        Address = address;
        EndDate = endDate;
        EstNoOfWorker = estNoOfWorker;
        Job = job;
        Salary = salary;
        StartDate = startDate;
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

    public String getEstNoOfWorker() {
        return EstNoOfWorker;
    }

    public void setEstNoOfWorker(String estNoOfWorker) {
        EstNoOfWorker = estNoOfWorker;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

}
