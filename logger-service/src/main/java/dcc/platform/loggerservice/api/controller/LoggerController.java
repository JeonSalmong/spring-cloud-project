package dcc.platform.loggerservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class LoggerController {
    @PostMapping("/saveActionLog")
    public void saveActionLog(@RequestBody Map<String, Object> paramMap) {
        log.info((String) paramMap.get("CD_SYS"));
    }
}
