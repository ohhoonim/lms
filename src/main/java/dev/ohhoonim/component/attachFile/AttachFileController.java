package dev.ohhoonim.component.attachFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 처리 API 
 */
// @RestController
@RequestMapping("/api/attachFile")
public class AttachFileController {
    private final AttachFileService attachFileService;

    public AttachFileController(AttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    @Value("${attachFile.upload-path}")
    private String uploadPath;

    /**
     * 파일 업로드
     */
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AttachFile>> upload(@RequestPart(value = "files") List<MultipartFile> files) {
        return ResponseEntity.ok(attachFileService.uploadFiles(files, uploadPath));
    }

    /**
     * 파일 다운로드
     */
    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable(value = "fileId") String fileId) {
        AttachFile attachFile = attachFileService.getAttachFile(fileId);
        Path path = attachFileService.getAttachFilePath(attachFile);
        Resource resource = attachFileService.getAttachFileResource(attachFile);

        String filename = attachFile.name() + "." + attachFile.extension();
        Long contentLength;
        try {
            contentLength = Files.size(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    httpHeaders.setContentDisposition(ContentDisposition.builder("attachment")
                            .filename(filename.replace("+", "%20"), StandardCharsets.UTF_8)
                            .build());
                })
                .contentLength(contentLength)
                .body(resource);
    }

    /**
     * 멀티 파일 업로더 zip 다운로드
     */
    @GetMapping("/download-zip")
    public ResponseEntity<Resource> zipDownload(@RequestParam(name = "fileIds") List<String> fileIds) throws IOException {
        String zipFileName = "files.zip";
        Path zipFilePath = null;
        long contentLength = 0;
        Resource zipResource = null;
        try {
            zipFilePath = Files.createTempFile(null, zipFileName); // 임시 파일 생성
            try (OutputStream outputStream = Files.newOutputStream(zipFilePath);
                 ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {

                for (String fileId : fileIds) {
                    AttachFile attachFile = attachFileService.getAttachFile(fileId);
                    Path filePath = attachFileService.getAttachFilePath(attachFile);

                    // 파일을 ZipOutputStream에 추가
                    try (InputStream inputStream = Files.newInputStream(filePath)) {
                        ZipEntry zipEntry = new ZipEntry(attachFile.name() + "." + attachFile.extension());
                        zipOutputStream.putNextEntry(zipEntry);

                        byte[] buffer = new byte[4096];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, length);
                        }
                        zipOutputStream.closeEntry();
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to add file to ZIP: " + filePath, e);
                    }
                }
            }

            zipResource = new InputStreamResource(Files.newInputStream(zipFilePath));
            contentLength = Files.size(zipFilePath);

        } catch (IOException e) {
            // 모종의 이유로 파일 생성 과정에서에러 발생시 임시 파일 resource 해제
            if (zipFilePath != null) {
                Files.deleteIfExists(zipFilePath);
            }
            throw new RuntimeException("Failed to create ZIP file: " + zipFilePath, e);
        } finally {
            if (zipFilePath != null) {
                Files.deleteIfExists(zipFilePath);
            }
        }

        return ResponseEntity.ok()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    httpHeaders.setContentDisposition(ContentDisposition.builder("attachment")
                            .filename(zipFileName, StandardCharsets.UTF_8)
                            .build());
                })
                .contentLength(contentLength)
                .body(zipResource);
    }

    /**
     * 파일 목록 조회
     */
    @GetMapping("/{entityId}")
    public ResponseEntity<List<AttachFile>> searchFiles(@PathVariable String entityId) {
        return ResponseEntity.ok(attachFileService.findAttachFiles(entityId));
    }

    /**
     * 파일 삭제
     */
    @PostMapping("/remove/{fileIds}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "fileIds") Set<String> fileIds) {
        attachFileService.deleteFiles(fileIds);
    }
}
