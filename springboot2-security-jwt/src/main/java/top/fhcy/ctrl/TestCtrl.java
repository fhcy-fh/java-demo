package top.fhcy.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fhcy.result.Result;

/**
 * @author fh
 * @date 2024/1/20
 */
@RestController
public class TestCtrl {

    @GetMapping("/test")
    public Result<String> test() {

        return Result.success("请求成功123");
    }
}
