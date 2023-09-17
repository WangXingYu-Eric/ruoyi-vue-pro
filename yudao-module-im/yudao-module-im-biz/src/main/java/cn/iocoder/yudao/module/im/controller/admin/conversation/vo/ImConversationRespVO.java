package cn.iocoder.yudao.module.im.controller.admin.conversation.vo;

import cn.iocoder.yudao.module.im.controller.admin.message.vo.ImMessageRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 会话 Response VO")
@Data
public class ImConversationRespVO {

    @Schema(description = "会话编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "会话类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type; // 对应 ImConversationTypeEnum 枚举

    @Schema(description = "聊天对象，用户编号或群编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long toId; // 根据 type 区分

    @Schema(description = "会话的最后一条消息", requiredMode = Schema.RequiredMode.REQUIRED)
    private ImMessageRespVO lastMessage;

    @Schema(description = "未读消息数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer unreadCount;

    // TODO 芋艿：还有如下字段
//    updateTime: 会话更新的时间
//    msgReceiptTime: 消息已读回执时间戳, 如果有此字段, 说明此时间戳之前的所有消息对方均已读

}



