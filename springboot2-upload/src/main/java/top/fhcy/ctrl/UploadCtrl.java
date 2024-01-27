package top.fhcy.ctrl;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UploadCtrl {
    private static final String UPLOAD_DIR = "./uploads/";

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "sdfjadja";
        }

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                boolean mkdir = dir.mkdir();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);
            model.addAttribute("message", "File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "File upload failed");
        }

        return "sdfjadja";
    }

}
