package com.rsternal.mhbl.dao.model.builders;

import com.rsternal.mhbl.dao.model.UserEntity;

import java.util.Date;

public class UserEntityBuilder {

        private String firstName;

        private String lastName;

        private String login;

        private String email;

        private String password;

        private Date createdDate;

        private Date closedDate;

        private boolean active;

        public UserEntityBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserEntityBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserEntityBuilder withLogin(String login) {
            this.login = login;
            return this;
        }

        public UserEntityBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserEntityBuilder withCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public UserEntityBuilder withClosedDate(Date closedDate) {
            this.closedDate = closedDate;
            return this;
        }

        public UserEntityBuilder withActive(boolean active) {
            this.active = active;
            return this;
        }

        public UserEntityBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntity build() {
            UserEntity user = new UserEntity();
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
