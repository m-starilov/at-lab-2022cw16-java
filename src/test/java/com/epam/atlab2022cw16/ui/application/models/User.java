package com.epam.atlab2022cw16.ui.application.models;

import java.util.Objects;

public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String postalCode;
    private String mobilePhone;
    private String birthDay;
    private String birthYear;
    private String birthMonth;

    public static class Builder {

        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String address;
        private String city;
        private String postalCode;
        private String mobilePhone;
        private String birthDay;
        private String birthYear;
        private String birthMonth;

        private Builder() {
        }

        private Builder(User user) {
            this.email = user.email;
            this.password = user.password;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.address = user.address;
            this.city = user.city;
            this.postalCode = user.postalCode;
            this.mobilePhone = user.mobilePhone;
            this.birthDay = user.birthDay;
            this.birthYear = user.birthYear;
            this.birthMonth = user.birthMonth;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
            return this;
        }

        public Builder setBirthDay(String birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Builder setBirthYear(String birthYear) {
            this.birthYear = birthYear;
            return this;
        }

        public Builder setBirthMonth(String birthMonth) {
            this.birthMonth = birthMonth;
            return this;
        }

        public User build() {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(address);
            user.setCity(city);
            user.setPostalCode(postalCode);
            user.setMobilePhone(mobilePhone);
            user.setBirthDay(birthDay);
            user.setBirthMonth(birthMonth);
            user.setBirthYear(birthYear);
            return user;
        }

    }

    public Builder edit() {
        return new Builder(this);
    }

    public static Builder create() {
        return new Builder();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    void setAddress(String address) {
        this.address = address;
    }

    void setCity(String city) {
        this.city = city;
    }

    void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

}
