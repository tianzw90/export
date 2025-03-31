package com.example.gzs.export.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ExportDemo {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @TableField("ID")
    private String id;

    @TableField("BUSINESS_CODE")
    private String businessCode;

    @TableField("BUSINESS_NAME")
    private String businessName;

    @TableField("BUSINESS_TITLE")
    private String businessTitle;

    @TableField("BUSINESS_CONTENT")
    private String businessContent;

    @TableField("PARENT_ID")
    private String parentId;

    @TableField("BUSINESS_TYPE")
    private String businessType;

    @TableField("CREATE_BY")
    private String createBy;

    @TableField("CREATE_NAME")
    private String createName;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("UPDATE_BY")
    private String updateBy;

    @TableField("UPDATE_NAME")
    private String updateName;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("DELETE_BY")
    private String deleteBy;

    @TableField("DELETE_NAME")
    private String deleteName;

    @TableField("DELETE_TIME")
    private String deleteTime;

}
