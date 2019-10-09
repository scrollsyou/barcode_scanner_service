package com.gugusong.barcode_scanner_service.action;

import java.time.Duration;

import com.gugusong.barcode_scanner_service.vo.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * TestController
 */
//@RestController
public class TestController {

    static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "test", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> test() {
        log.info("请求开始!");
        /* Flux<String> flux = Flux.create(emitter -> {
            emitter.next("你敢好!\n");
            emitter.next("hello word!");
            emitter.error(new Throwable("aaa"));
        }); */
        Flux<String> flux = Flux.interval(Duration.ofSeconds(1)).map(m -> {
            return "aaaaa\n";
        }).take(Duration.ofSeconds(4));
        flux.doOnCancel(() -> {
            log.info("退出连接!");
        });
        flux.doOnComplete(() -> {
            log.info("连接完成!");
        });
        flux.doAfterTerminate(() -> {
            log.info("连接完闭完成后!");
        });
        flux.doOnError(e -> {
            log.info("请求异常:" + e.getMessage());
        });
        flux.doOnTerminate(() -> {
            log.info("连接完闭完成!");
        });
        return flux;
    }
}