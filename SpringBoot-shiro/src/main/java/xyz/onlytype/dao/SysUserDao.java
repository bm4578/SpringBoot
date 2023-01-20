package xyz.onlytype.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.onlytype.entity.SysUser;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author 白也
 * @since 2023-01-20 15:52:46
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {

}

