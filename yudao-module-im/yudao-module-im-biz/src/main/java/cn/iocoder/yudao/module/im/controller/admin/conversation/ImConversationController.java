package cn.iocoder.yudao.module.im.controller.admin.conversation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理后台 - IM 会话")
@RestController
@RequestMapping("/im/conversation")
@Validated
@Slf4j
public class ImConversationController {
}
