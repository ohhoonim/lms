package dev.ohhoonim.component.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// @Component
@Slf4j
public class SchedulerConfig {
	private final BatchHistoryService batchHistoryService;

	public SchedulerConfig(BatchHistoryService batchHistoryService) {
		this.batchHistoryService = batchHistoryService;
	}

	private final String STATUS_OK = "COMPLETED";
	private final String STATUS_FAIL = "FAILED";

	/**
	 * 배치 처리(흐름만)
	 * 
	 */
	@Scheduled //(cron = "")
	//	@Scheduled(fixedDelay=10000)
	public void runModHistoryLaws() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 배치 시작 처리
			map = batchHistoryService.startBatchHistory(
				null/*jobName */,null /*jobKey */);

			// 여기에 batch 처리 로직 	

			// 배치 종료 처리
			batchHistoryService.endBatchHistory(map, STATUS_OK, "");
		} catch (Exception e) {
			batchHistoryService.endBatchHistory(map, STATUS_FAIL, e.toString());
			log.error("배치 처리중 오류발생 {}", e);
		}
	}

}
