package vn.trananhtuan.video.service;

import java.util.List;

import vn.trananhtuan.video.model.User_24133073;

public interface IUserService_24133073 {

    User_24133073 login(
            String username,
            String password
    );

    boolean register(User_24133073 user);

    User_24133073 findByUsername(String username);

    User_24133073 findByEmail(String email);

    List<User_24133073> findAll(
            int page,
            int pageSize
    );

    int count();

    boolean update(User_24133073 user);

    boolean delete(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}