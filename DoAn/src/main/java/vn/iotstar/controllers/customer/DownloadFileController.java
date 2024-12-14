package vn.iotstar.controllers.customer;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.iotstar.utils.Constain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class DownloadFileController {

    @GetMapping("/image")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fname") String fileName) throws IOException {

    	 String filePath = fileName.replace("\\", "/");
    	

        System.out.println("File path: " + filePath);
        
        File file = new File(filePath);
        
        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // Trả về lỗi 404 nếu tệp không tồn tại
        }

        // Xác định loại MIME cho các tệp hình ảnh khác nhau
        MediaType mediaType = determineMediaType(fileName);

        // Tạo InputStreamResource để truyền tệp ảnh
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // Trả về ResponseEntity với tệp hình ảnh và header phù hợp
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"") // "inline" để hiển thị ảnh trong trình duyệt
                .body(resource);
    }

    // Phương thức xác định loại MIME dựa trên phần mở rộng của tệp
    private MediaType determineMediaType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        
        } else if (fileName.endsWith(".webp")) {
            return MediaType.valueOf("image/webp");
        } else {
            return MediaType.APPLICATION_OCTET_STREAM; 
        }
    }
}
