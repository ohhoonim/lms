package dev.ohhoonim.user.infra;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.user.application.UserReq;
import dev.ohhoonim.user.model.User;
import dev.ohhoonim.user.model.UserAttribute;

@Mapper
public interface UserMapper {

	String findByUsernamePassword(
			@Param("name") String name,
			@Param("password") String password);

	User verifyLoginUser(
			@Param("username") String username,
			@Param("encodedPassword") String encodedPassword);

	void changeActivate(
			@Param("username") String username,
			@Param("isEnabled") boolean isEnabled);

	void changeLock(
			@Param("username") String username,
			@Param("isLock") boolean isLock);

	User getUserId(
			@Param("username") String username);

	void resetPassword(
			@Param("username") String username,
			@Param("newEncodedPassword") String newEncodedPassword);

	void saveUser(
			User user);

	void saveUserAttribute(
			UserAttribute attribute);

	void modifiyInfo(
			User userInfo);

	String findAttributeBy(
			@Param("userId") User userId,
			@Param("name") String name);

	void modifyAttribute(
			UserAttribute userAttribute);

	void increaseFailedAttemptCount(
			@Param("username") String username,
			@Param("newCount") Integer newCount);

	Integer failedCount(
			@Param("username") String username);

	User findByUsername(
			@Param("username") String username);

	List<User> findUsers(@Param("req") UserReq req,
			@Param("page") Page page);

    Integer findUsersTotal(@Param("req") UserReq req);


}
