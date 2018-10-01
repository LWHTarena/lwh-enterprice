package com.lwhtarena.company.ip.entities;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:12 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LocationInfo {
    public String country;
    public String state;
    public String city;
    public String isp;

    public LocationInfo() {
    }

    public LocationInfo(String country, String state, String city, String isp) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.isp = isp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
