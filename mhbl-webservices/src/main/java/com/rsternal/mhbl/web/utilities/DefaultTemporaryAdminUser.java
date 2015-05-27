package com.rsternal.mhbl.web.utilities;

import dao.model.builders.security.UserBuilder;
import dao.model.security.User;

import java.util.Date;

public class DefaultTemporaryAdminUser {

    public User getDefaultuser() {
        User user = new UserBuilder()
                .withFirstName("artificial")
                .withLastName("user")
                .withLogin("default_mhbl@mhbldefaultuser.pl")
                .withEmail("default_mhbl@mhbldefaultuser.pl")
                .withCreatedDate(new Date())
                .withActive(true)
                .withPassword("opsakl22")
                .build();
        return user;
    }
}
