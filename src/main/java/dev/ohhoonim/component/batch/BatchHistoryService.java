package dev.ohhoonim.component.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * 배치 이력 조회 관련 서비스
 *
 */
@Slf4j
// @Service
@Transactional
public class BatchHistoryService {

	private final BatchHistoryRepository batchHistoryRepository;

	public BatchHistoryService(BatchHistoryRepository batchHistoryRepository) {
		this.batchHistoryRepository = batchHistoryRepository;
	}

	/**
	 * 배치 이력 목록 조회
	 */
	@Transactional(readOnly = true)
	public List<BatchHistory> findBatchHistories(BatchHistorySearch search) {
		return batchHistoryRepository.select(search);
	}

	/**
	 * 배치 이력 싱세 조회
	 */
	@Transactional(readOnly = true)
	public List<BatchHistory> findHistory(String jobExecutionId) {
		return batchHistoryRepository.findHistory(jobExecutionId);
	}

	/**
	 * 배치 잡 인스턴스 ID값 확인
	 */
	public String findBatchJobInstanceId(String jobName, String jobKey) {
		BatchHistory jobNameKey = new BatchHistory(jobName, jobKey);
		String findJobInstanceId = batchHistoryRepository.findBatchJobInstanceId(jobNameKey);
		log.info("배치 잡 인스턴스 ID값 : {}", findJobInstanceId);

		String jobInstanceId = "";
		if (findJobInstanceId == null) { //없으면
			jobInstanceId = batchHistoryRepository.selecBatchJobInstanceId();

			BatchHistory batchHistory = new BatchHistory(
					null,
					Long.parseLong(jobInstanceId),
					jobName, null, null, null,
					jobKey);
			batchHistoryRepository.insertBatchJobInstance(batchHistory); //인스턴스 새로 추가
		}

		jobInstanceId = findJobInstanceId;
		return jobInstanceId;
	}

	/**
	 * 배치 잡 실행 : 시작
	 */
	public Map<String, String> startBatchHistory(String jobName, String jobKey) {

		Map<String, String> map = new HashMap<String, String>();

		String jobInstanceId = "", jobExecutionId = "";

		jobInstanceId = findBatchJobInstanceId(jobName, jobKey);
		jobExecutionId = batchHistoryRepository.selectBatchJobExecutionId();
		log.info("배치 잡 실행 ID값 : {}", jobExecutionId);

		BatchHistory batchHistory = new BatchHistory(
				Long.parseLong(jobInstanceId),
				Long.parseLong(jobExecutionId));

		batchHistoryRepository.insertBatchJobExecution(batchHistory);

		map.put("jobInstanceId", jobInstanceId);
		map.put("jobExecutionId", jobExecutionId);

		return map;
	}

	/**
	 * 배치 잡 실행 : 종료
	 */
	public void endBatchHistory(Map<String, String> map, String status, String exitMessage) {
		BatchHistory batchHistory = new BatchHistory(
				Long.parseLong(map.get("jobExecutionId").toString()),
				Long.parseLong(map.get("jobInstanceId").toString()),
				status,
				exitMessage);

		batchHistoryRepository.updateBatchJobExecution(batchHistory);
	}
}
