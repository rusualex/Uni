package model;

import java.util.Objects;

/**
 *      Pojo Class for User Type
 */
public class User {
    private String userName;
    private String passWord;
    private int userId;

    public User(String userName, String passWord,int userId) {
        this.userName = userName;
        this.passWord = passWord;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() &&
                getUserName().equals(user.getUserName()) &&
                getPassWord().equals(user.getPassWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPassWord(), getUserId());
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userId=" + userId +
                '}';
    }
}
