package dev.ohhoonim.component.batch;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 배치 이력 조회 Repository 
 * 스키마는 spring batch의 것을 가져옴
 */
// @Repository
public interface BatchHistoryRepository {

    /**
     * 배치 이력 목록 조회
     */
    List<BatchHistory> select(BatchHistorySearch search);
    
    /**
     * 배치 이력 상세 조회
     */
    List<BatchHistory> findHistory(String jobExecutionId);
    
    /**
     * 배치 잡 인스턴스Id 등록 확인
     */
    String findBatchJobInstanceId(BatchHistory batchHistory);
    
    /**
     * 배치 잡 인스턴스Id max값 가져오기 
     */
    String selecBatchJobInstanceId();
    
    /**
     * 배치 잡 실행Id max값 가져오기 
     */
    String selectBatchJobExecutionId();
    
    /**
     * 배치 잡 인스턴스 등록
     */
    void insertBatchJobInstance(BatchHistory batchHistory);
    
    /**
     * 배치 잡 실행 등록
     */
    void insertBatchJobExecution(BatchHistory batchHistory);
    
    /**
     * 배치 잡 실행 수정
     */
    void updateBatchJobExecution(BatchHistory batchHistory);
}
