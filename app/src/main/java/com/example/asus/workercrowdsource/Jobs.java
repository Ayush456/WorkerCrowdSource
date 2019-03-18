package com.example.asus.workercrowdsource;

public class Jobs {

    public String Job,salary,sdate,dura,nw,status;

    public Jobs(){

    }

public Jobs(String job, String salary, String startd, String dur, String work,String status){

        this.Job = job;
        this.salary = salary;
        this.sdate = startd;
        this.dura = dur;
        this.nw = work;
        this.status = status;
  }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getDura() {
        return dura;
    }

    public void setDura(String dura) {
        this.dura = dura;
    }

    public String getNw() {
        return nw;
    }

    public void setNw(String nw) {
        this.nw = nw;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
