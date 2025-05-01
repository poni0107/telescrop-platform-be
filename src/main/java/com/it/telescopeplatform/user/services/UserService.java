package com.it.telescopeplatform.user.services;

import com.it.telescopeplatform.user.dtos.UpdateUserDto;
import com.it.telescopeplatform.user.models.User;

public interface UserService {

    User getCurrentUser();

    User updateUser(UpdateUserDto userDto);
}