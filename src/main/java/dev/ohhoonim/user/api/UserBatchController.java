package dev.ohhoonim.user.api;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import dev.ohhoonim.user.model.User;
import dev.ohhoonim.user.model.UserBatchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-batch")
public class UserBatchController {

    private final UserBatchService userBatchService;

    @PostMapping("/batchUpdate")
    public int batchUpdate(@RequestBody List<User> users) {
        return userBatchService.batchUpdate(users);
    }

    @GetMapping("/applyPendingChangesToUser")
    public int applyPendingChangesToUser(
            @RequestParam("effectiveDate") LocalDateTime effectiveDate) {
        return userBatchService.applyPendingChangesToUser(effectiveDate);
    }

    @PostMapping("/batchRegister")
    public int batchRegister(@RequestBody List<User> users) {
        return userBatchService.batchRegister(users);
    }

    @PostMapping("/translateCsvToUsers")
    public List<User> translateCsvToUsers(@RequestPart("file") MultipartFile uploadedFile) {
        String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!"csv".equals(extension)) {
            throw new RuntimeException("csv 파일만 업로드해주세요");
        }

        try (var csv = uploadedFile.getInputStream()) {
            return userBatchService.translateCsvToUsers(csv);
        } catch (Exception e) {
            throw new RuntimeException("파일 처리중 에러가 발생하였습니다");
        }
    }

    @PostMapping("/translateExcelToUsers")
    public List<User> translateExcelToUsers(@RequestPart("file") MultipartFile uploadedFile) {
        String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!("xls".equals(extension) || "xlsx".equals(extension))) {
            throw new RuntimeException("excel 파일만 업로드해주세요");
        }

        try (var excel = uploadedFile.getInputStream()) {
            return userBatchService.translateExcelToUsers(excel);
        } catch (Exception e) {
            throw new RuntimeException("파일 처리중 에러가 발생하였습니다");
        }
    }

    @GetMapping("/fetchHrSystemToPendingChange")
    public int fetchHrSystemToPendingChange() {
        return userBatchService.fetchHrSystemToPendingChange();
    }
}
