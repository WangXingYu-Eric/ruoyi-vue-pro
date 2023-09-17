package cn.iocoder.yudao.module.im.controller.admin.message.vo;

import cn.iocoder.yudao.module.im.dal.dataobject.message.body.ImMessageBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 消息 Response VO")
@Data
public class ImMessageRespVO {

    @Schema(description = "消息编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "发送人的用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long fromId;

    @Schema(description = "消息类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type; // 参见 ImMessageTypeEnum 枚举

    @Schema(description = "消息内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private ImMessageBody body;

    @Schema(description = "发送时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // TODO 芋艿：网易有这 2 字段？看着存储需要，vo 不需要 https://doc.yunxin.163.com/messaging/docs/DE0MTk0OTY?platform=server
//    "fromclienttype": 2, //1：android、2:iOS、4：PC、16:WEB、32:REST、64:MAC
//    "msgidclient": "3bfd9660665a14bce4ec95e1b1d3afed",

}
