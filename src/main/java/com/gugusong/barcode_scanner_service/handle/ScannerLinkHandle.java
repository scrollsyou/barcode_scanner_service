package com.gugusong.barcode_scanner_service.handle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

/**
 * ScannerLinkHandle
 * @author you
 */
@Component
public class ScannerLinkHandle implements WebSocketHandler {

    static final Logger log = LoggerFactory.getLogger(ScannerLinkHandle.class);

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Mono<Void> mono = session.send(session.receive()
        .map(msg -> "你是谁呀!" + msg.getPayloadAsText())
        .map(session::textMessage)
        ).doOnTerminate(() -> {
            log.info(session.getId() + "当前连接关闭了!");
        }).doOnSuccessOrError((a,e) -> {
            log.info("连接异常");
        });
        log.info(session.getId() + "当前连接建立了!");
        String queryString = session.getHandshakeInfo().getUri().getQuery();
        if (queryString != null) {
            log.info("请求建立连接参数为:{}", queryString);
            String[] keyValues = queryString.split("\\&|\\=");
            for (String value : keyValues) {
                log.info(value + "...");
            }
            log.info("token 为：" + Pattern.compile("(\\&|\\?|^)token\\=.*(\\&|$)").matcher(queryString).group());
        }else {
            session.close();
        }
        return mono;
    }

    public static void main(String[] args) {
        Matcher matcher = Pattern.compile("(\\&|\\?|^)token\\=([^&]*)(\\&|$)").matcher("token=1233&a=19&encoding=text");
        matcher.find();
        System.out.println(matcher.group());
    }

}
