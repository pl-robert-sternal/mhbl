package dao.model.builders.security;

import dao.model.security.User;

import java.util.Date;

public class UserBuilder {

    private String firstName;

    private String lastName;

    private String login;

    private String email;

    private Date createdDate;

    private Date closedDate;

    private boolean active;

    private String password;

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public UserBuilder withClosedDate(Date closedDate) {
        this.closedDate = closedDate;
        return this;
    }

    public UserBuilder withActive(boolean active) {
        this.active = active;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setLogin(this.login);
        user.setEmail(this.email);
        user.setCreatedDate(this.createdDate);
        user.setClosedDate(this.closedDate);
        user.setActive(this.active);
        user.setPassword(this.password);

        return user;
    }
}
