package vn.trananhtuan.video.dao;

import java.util.List;

import vn.trananhtuan.video.model.User_24133073;

public interface IUserDAO_24133073 {

    User_24133073 findByUsername(String username);

    User_24133073 findByEmail(String email);

    List<User_24133073> findAll(int page, int pageSize);

    int count();

    boolean insert(User_24133073 user);

    boolean update(User_24133073 user);

    boolean delete(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}