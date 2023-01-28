package xyz.onlytype.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(SysUser)表实体类
 *
 * @author 白也
 * @since 2023-01-28 11:23:37
 */
@SuppressWarnings("serial")
@Data
public class SysUser extends Model<SysUser> implements Serializable{
    //用户 ID
    private Long userId;
    //与博客关联
    private String isArticle;
    //系统登录名
    private String username;
    //登录密码，加密存储, admin/1234
    private String password;
    //加密盐
    private String isSalt;
    //邮箱
    private String isEmail;
    //头像
    private String isImg;
    //权限字段0/管理员，1/普通用户
    private Integer isPermission;
    //帐户是否可用(1 可用，0 不可用)
    private Integer isEnabled;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createTime = new Date();
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime = new Date();


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIsArticle() {
        return isArticle;
    }

    public void setIsArticle(String isArticle) {
        this.isArticle = isArticle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsSalt() {
        return isSalt;
    }

    public void setIsSalt(String isSalt) {
        this.isSalt = isSalt;
    }

    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    public String getIsImg() {
        return isImg;
    }

    public void setIsImg(String isImg) {
        this.isImg = isImg;
    }

    public Integer getIsPermission() {
        return isPermission;
    }

    public void setIsPermission(Integer isPermission) {
        this.isPermission = isPermission;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
    }

