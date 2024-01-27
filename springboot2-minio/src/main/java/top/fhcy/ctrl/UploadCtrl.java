package top.fhcy.ctrl;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import top.fhcy.utils.MinIOUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

@RestController
public class UploadCtrl {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            return "sdfjadja";
        }

        try {
            MinIOUtils.upload(file, file.getOriginalFilename());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return "sdfjadja";
    }

    @GetMapping("/download/{fileName}")
    public DeferredResult<ResponseEntity<InputStreamResource>> download(@PathVariable String fileName) {
        return this.downloadFile(fileName);
    }

    @GetMapping("/{fileName}")
    public DeferredResult<ResponseEntity<InputStreamResource>> get(@PathVariable String fileName) {
        return this.downloadFile(fileName);
    }

    private DeferredResult<ResponseEntity<InputStreamResource>> downloadFile(String fileName) {
        DeferredResult<ResponseEntity<InputStreamResource>> deferredResult = new DeferredResult<>();
        // 异步下载文件
        CompletableFuture.supplyAsync(() -> MinIOUtils.download(fileName))
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        deferredResult.setErrorResult(ResponseEntity.status(500).body(null));
                    } else {
                        deferredResult.setResult(result);
                    }
                });
        return deferredResult;
    }

}
