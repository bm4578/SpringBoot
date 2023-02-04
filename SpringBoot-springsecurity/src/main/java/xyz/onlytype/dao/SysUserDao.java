package xyz.onlytype.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.vo.UserRoleVO;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author 白也
 * @since 2023-01-28 11:23:37
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {

    //根据用户Id查询角色
    List<UserRoleVO> findRolesByUserId(String userId);

    //角色查询
    List<String> findRoleByUserId(String userId);


}

