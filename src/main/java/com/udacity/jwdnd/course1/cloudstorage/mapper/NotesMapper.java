package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Notes> getAllListNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} and userid = #{userId}")
    Notes getNoteById(Integer noteId, Integer userId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteid = #{noteId} and userid = #{userId}")
    int updateNoteById(Notes note);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    int addNote(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} and userid = #{userId}")
    int delNoteById(Integer noteId, Integer userId);
}
