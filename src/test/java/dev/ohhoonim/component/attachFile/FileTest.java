package dev.ohhoonim.component.attachFile;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import dev.ohhoonim.component.auditing.model.Id;
import jakarta.servlet.http.Part;

/**
 * {@link java.net.URI} 
 * {@link java.nio.file.Files}
 * {@link java.nio.file.Paths} 
 */
public class FileTest {
    @Test
    void uriTest() throws URISyntaxException {
        /*
         * [scheme:][//authority][path][?query][#fragment]
         * 
         * [user-info@]host[:port]
         * ssh matthew@localhost:23
         * 
         * mailto:ohhoonim@demo.ohhoonim.com
         * 
         * file://~/calendar
         * ../../demo/b/index.html
         */
        String url = "http://localhost:8080/search?k=kor#region";

        var uri = new URI(url);

        assertThat(uri.getScheme()).isEqualTo("http");
        assertThat(uri.getSchemeSpecificPart()).isEqualTo("//localhost:8080/search?k=kor");
        assertThat(uri.getAuthority()).isEqualTo("localhost:8080");
        assertThat(uri.getUserInfo()).isNull();
        assertThat(uri.getHost()).isEqualTo("localhost");
        assertThat(uri.getPort()).isEqualTo(8080);
        assertThat(uri.getPath()).isEqualTo("/search");
        assertThat(uri.getQuery()).isEqualTo("k=kor");
        assertThat(uri.getFragment()).isEqualTo("region");

        /*
         * URI : uniform resource identifier
         * URL : uniform resource locator
         * URN : uniform resource names (mailto, news, isbn 등)
         * 
         * URI = URL + URN
         */

        var urn = new URI("mailto:ohhoonim@demo.ohhoonim.com");

        assertThat(urn.getScheme()).isEqualTo("mailto");
        assertThat(urn.getSchemeSpecificPart()).isEqualTo("ohhoonim@demo.ohhoonim.com");
        assertThat(urn.getAuthority()).isNull();
        assertThat(urn.getUserInfo()).isNull();
        assertThat(urn.getHost()).isNull();
        assertThat(urn.getPort()).isEqualTo(-1);
        assertThat(urn.getPath()).isNull();
        assertThat(urn.getQuery()).isNull();
        assertThat(urn.getFragment()).isNull();

    }

    @Test
    void hangulEncodedTest() throws URISyntaxException {
        URI url = new URI(
                "https://ohhoohim.net/series/Spring-Boot-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-%EA%B8%B0%EC%B4%88");
        assertThat(url.getPath()).isEqualTo("/series/Spring-Boot-스프링-부트-기초");

        URI url2 = new URI("http://www.ohhoonim.net/series/Spring-Boot-스프링-부트-기초");
        assertThat(url2.getPath()).isEqualTo("/series/Spring-Boot-스프링-부트-기초");
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////

    private final String ROOT_PATH = "/Users/hyoungdonju/Repositories/temp";
    private final String LOCAL_PATH = ROOT_PATH + "/local";
    private final String SERVER_PATH = ROOT_PATH + "/server2";

    @Test
    void simualtedFileUploadTest() throws IOException {
        // 업로드할 파일이라고 가정 (LOCAL_PATH+"/readme.md" 파일 하나 만들어둬야함)
        var file = Paths.get(LOCAL_PATH + "/readme.md").toFile();
        assertThat(file.exists()).isTrue();

        // multipartFile 구현체를 임시로 만듦
        // 파일명은 http header에 담겨 들어옴
        MultipartFile multipart = new StandardMultipartFile(new TestPart(file), "readme.md");

        var filename = multipart.getOriginalFilename();
        assertThat(FilenameUtils.getExtension(filename)).isEqualTo("md");

        var targetDir = Paths.get(SERVER_PATH);
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        var id = new Id();
        var uploadedPath = SERVER_PATH + File.separator + id;
        Path targetPath = Paths.get(uploadedPath);

        Files.copy(multipart.getInputStream(), targetPath);

        assertThat(targetPath.toFile().exists()).isTrue();
        assertThat(targetPath.toFile().length()).isEqualTo(file.length());
    }

    @Test
    @DisplayName("org.apache.commons.io.FilenameUtils")
    void filenameUtilsTest() {
        String filename = "readme.md.pdf";

        assertThat(FilenameUtils.getExtension(filename).isEmpty()).isFalse();
        assertThat(FilenameUtils.getExtension(filename)).isEqualTo("pdf");

    }

    @Test
    void streamThrowTest() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        List<Optional<?>> results = numbers.stream()
                .map(num -> {
                    try {
                        if (num == 2) {
                            throw new IOException("number 2 is thrown");
                        }
                    } catch (IOException e) {
                        return Optional.empty();
                    }
                    return Optional.of(num);
                }).toList();
        assertThat(results).hasSize(5);

        List<Integer> finalResults = results.stream()
                .filter(r -> r.isPresent())
                .map(o -> (Integer) o.get())
                .toList();
        assertThat(finalResults).hasSize(4);
    }
}

class StandardMultipartFile implements MultipartFile, Serializable {

    private final Part part;

    private final String filename;

    public StandardMultipartFile(Part part, String filename) {
        this.part = part;
        this.filename = filename;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return this.filename;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return (this.part.getSize() == 0);
    }

    @Override
    public long getSize() {
        return this.part.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.part.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {

    }
}

class TestPart implements Part {
    private File localFile;

    public TestPart(File localFile) {
        this.localFile = localFile;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(localFile.toPath());
    }

    @Override
    public long getSize() {
        return localFile.exists() ? localFile.length() : 0L;
    }

    // 여기 아래로는 구현 안함.

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getSubmittedFileName() {
        return null;
    }

    @Override
    public void write(String fileName) throws IOException {

    }

    @Override
    public void delete() throws IOException {
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

}