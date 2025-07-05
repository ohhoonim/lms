package dev.ohhoonim.component.attachFile;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 파일 데이터 처리 Mapper
 */
// @Repository
public interface AttachFileRepository {

    /**
     * 파일 저장
     */
    void insertAttachFile(AttachFile attachFile);

    /**
     * 파일 ID에 해당하는 정보 조회
     */
    AttachFile selectAttachFile(String fileId);

    /**
     * 파일 삭제
     */
    void deleteAttachFile(String fileId);

    /**
     * 파일 ID로 파일 그룹 삭제
     */
    void deleteAttachFileGroupByFileId(String fileId);

    /**
     * 개체 ID로 파일 목록 조회
     */
    List<AttachFile> selectAttachFiles(String entityId);

    /**
     * 파일 그룹 저장
     */
    void insertAttachFileGroup(AttachFileGroup attachFileGroup);

    /**
     * 개체 ID로 파일 그룹 삭제
     */
    void deleteAttachFileGroupByEntityId(String entityId);
}
