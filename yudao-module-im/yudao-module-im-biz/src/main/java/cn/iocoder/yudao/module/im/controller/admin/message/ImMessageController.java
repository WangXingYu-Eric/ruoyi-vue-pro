package cn.iocoder.yudao.module.im.controller.admin.message;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理后台 - IM 聊天")
@RestController
@RequestMapping("/im/message")
@Validated
@Slf4j
public class ImMessageController {
}
