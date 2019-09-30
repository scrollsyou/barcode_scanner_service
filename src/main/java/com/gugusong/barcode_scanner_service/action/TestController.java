package com.gugusong.barcode_scanner_service.action;

import java.time.Duration;

import com.gugusong.barcode_scanner_service.vo.Test;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * TestController
 */
@RestController
public class TestController {


    @RequestMapping(value = "test", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Test> test() {
        Flux<Test> flux = Flux.interval(Duration.ofSeconds(10)).map(l -> {return new Test();});
        flux.doOnCancel(() -> {
            System.out.println("退出连接!");
        });
        flux.doOnComplete(() -> {
            System.out.println("连接完成!");
        });
        return flux;
    }
}