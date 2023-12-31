package cn.iocoder.yudao.module.bpm.dal.mysql.task;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.bpm.dal.dataobject.task.BpmActivityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// TODO @ke：可以使用 mybatis plus 来写查询么？
@Mapper
public interface BpmActivityMapper extends BaseMapperX<BpmActivityDO> {

    /**
     * 获取所有历史任务
     *
     * @return 返回历史任务
     */
    List<BpmActivityDO> listAll();

    /**
     * 获取指定流程的历史任务
     *
     * @param procInstId 流程id
     *
     * @return 返回历史任务
     */
    @TenantIgnore
    List<BpmActivityDO> listAllByProcInstId(@Param("procInstId") String procInstId);

    /**
     * 逻辑删除hiActInst表任务
     *
     * @param taskIdList 任务列表
     *
     * @return 返回是否成功
     */
    Boolean delHiActInstByTaskId(@Param("taskIdList") List<String> taskIdList);

    /**
     * 逻辑删除hiTaskInst任务
     *
     * @param taskIdList 任务列表
     *
     * @return 返回是否成功
     */
    Boolean delHiTaskInstByTaskId(@Param("taskIdList") List<String> taskIdList);
}
