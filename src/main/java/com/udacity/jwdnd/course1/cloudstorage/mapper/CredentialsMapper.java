package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> getAllListCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} and credentialid = #{credentialId}")
    Credentials getCreById(Integer credentialId, Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    int addCredentials(Credentials credential);

    @Delete("DELETE FROM CREDENTIALS WHERE userid = #{userId} and credentialid = #{credentialId}")
    int deleteCreById(Integer credentialId, Integer userId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, username = #{username}, password = #{password} WHERE credentialid = #{credentialId} and userid = #{userId}")
    int editCreById(Credentials credential);
}
