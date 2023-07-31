package cn.iocoder.yudao.framework.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "yudao.rocketmq")
@Data
public class RocketProperties {

    private boolean enabledIsolation;

    private String environment = "local";

}
