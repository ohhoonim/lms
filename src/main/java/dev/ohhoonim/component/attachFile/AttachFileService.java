package dev.ohhoonim.component.attachFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.auditing.model.Modified;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일 처리 기능 서비스 클래스
 */
// @Service
@Slf4j
@Service
public class AttachFileService {
    private final AttachFileMapper attachFileRepository;

    public AttachFileService(AttachFileMapper attachFileRepository) {
        this.attachFileRepository = attachFileRepository;
    }

    @Value("${attachFile.upload-path}")
    private String uploadPath;

    @Value("${attachFile.max-attach-files}")
    private int maxNuberOfFiles;

    /**
     * 파일 업로드 
     * @param multipart 파일 목록 
     * @return 업로드된 파일 id 목록
     */
    @Transactional
    public List<Id> uploadFiles(List<MultipartFile> files) {
        // 저장경로 확보
        securePath();

        if (files.size() > maxNuberOfFiles) {
            throw new IllegalArgumentException("The number of files that can be uploaded has been exceeded");
        }
        // 파일 업로드 
        var attachedFiles = files.stream()
                .map(f -> {
                    var filename = f.getOriginalFilename();
                    var extension = FilenameUtils.getExtension(filename);
                    var capacity = f.getSize();
                    var fileId = new Id();

                    var targetPath = uploadPath + File.separator + fileId;
                    try {
                        Files.copy(f.getInputStream(), Paths.get(targetPath));
                    } catch (IOException e) {
                        return Optional.empty();
                    }
                    return Optional.of(AttachFile.builder()
                            .id(fileId)
                            .name(filename)
                            .path(targetPath)
                            .capacity(capacity)
                            .extension(extension)
                            .build());
                })
                .toList();
        // 업로드된 파일들의 id 정보를 프론트에서 이용할 수 있게 
        List<Id> uploadedFileIds = attachedFiles.stream()
                .filter(Optional::isPresent).map(o -> (AttachFile) o.get())
                .map(f -> {
                    var attach = AttachFile.builder()
                            .id(f.getId())
                            .name(f.getName())
                            .path(f.getPath())
                            .capacity(f.getCapacity())
                            .extension(f.getExtension())
                            .creator(new Created("app"))
                            .modifier(new Modified("app")) // 파일 그룹 저장시 업데이트 해주기 
                            .build();
                    attachFileRepository.insertAttachFile(attach);

                    return f.getId();
                })
                .toList();
        return uploadedFileIds;
    }

    private void securePath() {
        if (!StringUtils.hasText(uploadPath)) {
            throw new NullPointerException("Upload path does not exist");
        }

        var targetDir = Paths.get(uploadPath);
        if (!Files.exists(targetDir)) {
            try {
                Files.createDirectories(targetDir);
            } catch (IOException e) {
                throw new RuntimeException("fail to make upload directory");
            }
        }
    }

    /**
     * 파일 목록 조회(by entityId)
     * @param entityId
     * @return
     */
    public List<AttachFile> findAttachFiles(String entityId) {
        return attachFileRepository.selectAttachFiles(Id.valueOf(entityId));
    }

    /**
     * 파일 삭제
     * @param fileIds
     */
    @Transactional
    public void removeFiles(Set<String> fileIds) {
        fileIds.stream().forEach(fileId -> {
            try {
                String filePath = uploadPath + File.separator + fileId;
                Files.deleteIfExists(Paths.get(filePath));

                attachFileRepository.deleteAttachFileGroupByFileId(Id.valueOf(fileId));
                attachFileRepository.deleteAttachFile(Id.valueOf(fileId));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    /**
     * 첨부파일 정보 조회
     * @param fileId
     * @return
     */
    public AttachFile getAttachFile(String fileId) {
        return attachFileRepository.selectAttachFile(Id.valueOf(fileId));
    }

    public Resource getAttachFileResource(AttachFile attachFile) {
        try {
            return new InputStreamResource(Files.newInputStream(getAttachFilePath(attachFile)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getAttachFilePath(AttachFile attachFile) {
        return Paths.get(uploadPath + File.separator + attachFile.getId());
    }

}
