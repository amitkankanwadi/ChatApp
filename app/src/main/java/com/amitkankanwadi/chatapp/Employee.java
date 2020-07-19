package com.amitkankanwadi.chatapp;

class Employee {

    String EmployeeId, Abbreviation, FirstName, LastName, Designation, Birthday, Address;

    public Employee(){

    }

    public Employee(String employeeId, String abbreviation, String firstName, String lastName, String designation, String birthday, String address) {
        EmployeeId = employeeId;
        Abbreviation = abbreviation;
        FirstName = firstName;
        LastName = lastName;
        Designation = designation;
        Birthday = birthday;
        Address = address;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getDesignation() {
        return Designation;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getAddress() {
        return Address;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public void setAbbreviation(String abbreviation) {
        Abbreviation = abbreviation;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
