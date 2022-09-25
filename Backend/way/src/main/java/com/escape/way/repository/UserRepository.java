package com.escape.way.repository;

import com.escape.way.model.User;
import com.escape.way.vo.UserPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

    @Query("SELECT new com.escape.way.vo.UserPlace(u.userId, u.latitude, u.longitude) FROM User u WHERE u.userNo = :user_no")
    Optional<UserPlace> findPlaceById(@Param("user_no") Long userNo);

    @Query("SELECT u.updateTime FROM User u WHERE u.userNo = :user_no")
    Optional<ZonedDateTime> getUpdateTime(@Param("user_no") Long userNo);

    @Modifying
    @Query("update User u set u.updateTime = :time where u.userNo = :user_no ")
    int setUpdateTime(@Param("user_no") Long no, @Param("time") ZonedDateTime time);


}
