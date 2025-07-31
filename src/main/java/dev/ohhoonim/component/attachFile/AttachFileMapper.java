package dev.ohhoonim.component.attachFile;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dev.ohhoonim.component.id.Id;

@Mapper
public interface AttachFileMapper {

    void insertAttachFile(@Param("file") AttachFile attachFile);

    AttachFile selectAttachFile(@Param("id") Id fileId);

    void deleteAttachFile(@Param("id") Id fileId);

    void deleteAttachFileGroupByFileId(@Param("id") Id fileId);

    List<AttachFile> selectAttachFiles(@Param("entityId") Id entityId);

    void insertAttachFileGroup(@Param("group") AttachFileGroup attachFileGroup);

    void deleteAttachFileGroupByEntityId(@Param("entityId") Id entityId);
}
