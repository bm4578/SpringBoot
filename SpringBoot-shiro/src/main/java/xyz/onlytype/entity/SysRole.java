package xyz.onlytype.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表(SysRole)表实体类
 *
 * @author 白也
 * @since 2023-01-22 16:10:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole extends Model<SysRole> implements Serializable {
    private static final long serialVersionUID = 1L;
    //角色id
    private Long roleId;
    //角色名称
    private String roleName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createTime = new Date();
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date updateTime = new Date();
}

