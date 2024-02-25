package net.ecbank.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * MessageSource를 전역에서 사용하기 위한 처리
 *
 * @author ECBANK-ecbank1
 * @since 2023-06-07
 */
@Component
public class EcMessageUtils {

    @Resource
    private MessageSource source;

    static MessageSource messageSource;

    @PostConstruct
    public void initialize() {
        messageSource = source;
    }

    /**
     * 메세지 코드 사용 시.
     * @param messageCd
     * @return
     */
    public static String getMessage(String messageCd) {
        return messageSource.getMessage(messageCd, null, LocaleContextHolder.getLocale());
    }

    /**
     * 메세지 코드와 치환 값을 사용 시.
     * @param messageCd
     * @param messageArgs
     * @return
     */
    public static String getMessage(String messageCd, Object[] messageArgs) {
        return messageSource.getMessage(messageCd, messageArgs, LocaleContextHolder.getLocale());
    }
}
