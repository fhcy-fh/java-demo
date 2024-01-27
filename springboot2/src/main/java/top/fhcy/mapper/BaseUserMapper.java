package top.fhcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.fhcy.entity.BaseUser;

@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUser> {
}
