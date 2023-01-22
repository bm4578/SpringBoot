package xyz.onlytype.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表
(SysMenu)表实体类
 *
 * @author 白也
 * @since 2023-01-22 16:10:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu extends Model<SysMenu> implements Serializable {
    private static final long serialVersionUID = 1L;
    //菜单id
    private Long menuId;
    //菜单名称
    private String menuName;
    //菜单URL
    private String menuUrl;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createTime = new Date();
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date updateTime = new Date();
}
