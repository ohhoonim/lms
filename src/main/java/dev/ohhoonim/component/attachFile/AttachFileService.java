package dev.ohhoonim.component.attachFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import dev.ohhoonim.component.id.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일 처리 기능 서비스 클래스
 */
// @Service
@Slf4j
public class AttachFileService {
    private final AttachFileRepository attachFileRepository;

    public AttachFileService(AttachFileRepository attachFileRepository) {
        this.attachFileRepository = attachFileRepository;
    }

    @Value("${attachFile.upload-path}")
    private String uploadPath;

    @Value("${attachFile.max-attach-files}")
    private int maxAttacheFiles;

    /**
     * 파일 업로드 
     */
    @Transactional
    public List<AttachFile> uploadFiles(List<MultipartFile> files, String uploadFilePath) {
        if (!StringUtils.hasText(uploadFilePath)) {
            throw new NullPointerException(
                    "empty upload path. check property in application.yml [constants.file.upload-path]");
        }

        getDirPath(uploadFilePath);
        checkMaxFiles(files);

        List<AttachFile> attachFiles = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            Id id = new Id();
            String name = multipartFile.getOriginalFilename();
            String extension = Optional.ofNullable(FilenameUtils.getExtension(name))
                    .map(String::toLowerCase)
                    .orElseThrow(NullPointerException::new);

            File uploadedFile = new File(uploadFilePath + File.separator + id);

            try (InputStream uploadFile = multipartFile.getInputStream();
                    BufferedOutputStream outputStream = new BufferedOutputStream(
                            Files.newOutputStream(uploadedFile.toPath()))) {

                saveFile(uploadFile, outputStream);
                saveFileInfo(uploadFilePath, attachFiles, multipartFile, id, name, extension);

            } catch (IOException e) {
                log.error("uploadFiles method IOException", e);
                throw new ServerErrorException(e.getMessage(), e);
            }
        }
        return attachFiles;
    }

    private void saveFileInfo(String uploadFilePath, List<AttachFile> attachFiles, MultipartFile multipartFile, Id id,
            String name, String extension) {
        AttachFile file = new AttachFile(
                id,
                FilenameUtils.getBaseName(name),
                uploadFilePath,
                multipartFile.getSize(),
                extension);

        attachFiles.add(file);
        attachFileRepository.insertAttachFile(file);
    }

    private void saveFile(InputStream uploadFile, BufferedOutputStream outputStream) throws IOException {
        int chunkSize = 1024 * 1024;
        byte[] buffer = new byte[chunkSize];
        int bytesRead;

        while ((bytesRead = uploadFile.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }

    private void checkMaxFiles(List<MultipartFile> files) {
        if (files.size() > maxAttacheFiles) {
            log.error("Maximum number of file uploads exceeded");
            throw new IllegalArgumentException("Maximum number of file uploads exceeded");
        }
    }

    private void getDirPath(String uploadFilePath) {
        Path dirPath = Paths.get(uploadFilePath);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
                log.info("create directory");
            } catch (IOException e) {
                log.error("uploadFiles method IOException", e);
                throw new ServerErrorException(e.getMessage(), e);
            }
        }
    }

    /**
     * 파일 삭제 
     */
    @Transactional
    public void deleteFiles(Set<String> fileIds) {
        for (String fileId : fileIds) {
            try {
                String filePath = uploadPath + File.separator + fileId;
                // 물리적 파일 삭제
                Path copyOfLocation = Paths.get(filePath);
                Files.deleteIfExists(copyOfLocation);
                // 외래키 참조 파일 그룹 삭제 후 파일 삭제
                attachFileRepository.deleteAttachFileGroupByFileId(fileId);
                attachFileRepository.deleteAttachFile(fileId);
            } catch (IOException e) {
                log.error("deleteFiles method IOException", e);
                throw new ServerErrorException(e.getMessage(), e);
            }
        }
    }

    public AttachFile getAttachFile(String fileId) {
        return attachFileRepository.selectAttachFile(fileId);
    }

    public Resource getAttachFileResource(AttachFile attachFile) {
        try {
            return new InputStreamResource(Files.newInputStream(getAttachFilePath(attachFile)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getAttachFilePath(AttachFile attachFile) {
        return Paths.get(uploadPath + File.separator + attachFile.id());
    }

    /**
     * 파일 목록 조회(by Id)
     */
    public List<AttachFile> findAttachFiles(String entityId) {
        return attachFileRepository.selectAttachFiles(entityId);
    }

    /**
     * 파일 업로드 대상 객체 ID와 파일 ID를 저장
     */
    public void inputAttachFileGroup(AttachFileGroup attachFileGroup) {
        attachFileRepository.insertAttachFileGroup(attachFileGroup);
    }

    /**
     * 레코드 ID와 연관된 파일 그룹 삭제
     */
    public void deleteAttachFileGroup(String entityId) {
        attachFileRepository.deleteAttachFileGroupByEntityId(entityId);
    }

}
