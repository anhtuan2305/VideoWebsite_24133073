package vn.trananhtuan.video.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import vn.trananhtuan.video.dao.IUserDAO_24133073;
import vn.trananhtuan.video.dao.UserDAOImpl_24133073;
import vn.trananhtuan.video.model.User_24133073;

public class UserServiceImpl_24133073
        implements IUserService_24133073 {

    private final IUserDAO_24133073 userDAO;

    public UserServiceImpl_24133073() {
        this.userDAO = new UserDAOImpl_24133073();
    }

    public UserServiceImpl_24133073(
            IUserDAO_24133073 userDAO) {

        this.userDAO = userDAO;
    }

    @Override
    public User_24133073 login(
            String username,
            String password) {

        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()) {

            return null;
        }

        User_24133073 user =
                userDAO.findByUsername(username.trim());

        if (user == null || !user.isActive()) {
            return null;
        }

        String storedPassword = user.getPassword();

        if (storedPassword == null) {
            return null;
        }

        boolean passwordMatches;

        if (isBCryptPassword(storedPassword)) {
            passwordMatches =
                    BCrypt.checkpw(password, storedPassword);
        } else {
            // Hỗ trợ dữ liệu mẫu đang lưu mật khẩu thường
            passwordMatches =
                    storedPassword.equals(password);
        }

        return passwordMatches ? user : null;
    }

    @Override
    public boolean register(User_24133073 user) {

        if (!isValidUser(user)) {
            return false;
        }

        String username = user.getUsername().trim();
        String email = user.getEmail().trim();

        if (userDAO.existsByUsername(username)
                || userDAO.existsByEmail(email)) {

            return false;
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setFullname(user.getFullname().trim());

        if (user.getPhone() != null) {
            user.setPhone(user.getPhone().trim());
        }

        user.setPassword(
                BCrypt.hashpw(
                        user.getPassword(),
                        BCrypt.gensalt()
                )
        );

        user.setAdmin(false);
        user.setActive(true);

        return userDAO.insert(user);
    }

    @Override
    public User_24133073 findByUsername(
            String username) {

        if (username == null || username.isBlank()) {
            return null;
        }

        return userDAO.findByUsername(username.trim());
    }

    @Override
    public User_24133073 findByEmail(String email) {

        if (email == null || email.isBlank()) {
            return null;
        }

        return userDAO.findByEmail(email.trim());
    }

    @Override
    public List<User_24133073> findAll(
            int page,
            int pageSize) {

        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 6;
        }

        return userDAO.findAll(page, pageSize);
    }

    @Override
    public int count() {
        return userDAO.count();
    }

    @Override
    public boolean update(User_24133073 user) {

        if (user == null
                || user.getUsername() == null
                || user.getUsername().isBlank()) {

            return false;
        }

        User_24133073 oldUser =
                userDAO.findByUsername(
                        user.getUsername().trim()
                );

        if (oldUser == null) {
            return false;
        }

        String newPassword = user.getPassword();

        if (newPassword == null
                || newPassword.isBlank()) {

            user.setPassword(oldUser.getPassword());

        } else if (!newPassword.equals(
                oldUser.getPassword())
                && !isBCryptPassword(newPassword)) {

            user.setPassword(
                    BCrypt.hashpw(
                            newPassword,
                            BCrypt.gensalt()
                    )
            );
        }

        if (user.getFullname() != null) {
            user.setFullname(
                    user.getFullname().trim()
            );
        }

        if (user.getEmail() != null) {
            user.setEmail(
                    user.getEmail().trim()
            );
        }

        if (user.getPhone() != null) {
            user.setPhone(
                    user.getPhone().trim()
            );
        }

        return userDAO.update(user);
    }

    @Override
    public boolean delete(String username) {

        if (username == null || username.isBlank()) {
            return false;
        }

        return userDAO.delete(username.trim());
    }

    @Override
    public boolean existsByUsername(String username) {

        return username != null
                && !username.isBlank()
                && userDAO.existsByUsername(
                        username.trim()
                );
    }

    @Override
    public boolean existsByEmail(String email) {

        return email != null
                && !email.isBlank()
                && userDAO.existsByEmail(
                        email.trim()
                );
    }

    private boolean isValidUser(User_24133073 user) {

        return user != null
                && user.getUsername() != null
                && !user.getUsername().isBlank()
                && user.getPassword() != null
                && !user.getPassword().isBlank()
                && user.getFullname() != null
                && !user.getFullname().isBlank()
                && user.getEmail() != null
                && !user.getEmail().isBlank();
    }

    private boolean isBCryptPassword(String password) {

        return password.startsWith("$2a$")
                || password.startsWith("$2b$")
                || password.startsWith("$2y$");
    }
}