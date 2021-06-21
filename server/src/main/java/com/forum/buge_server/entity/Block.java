package com.forum.buge_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author
 * @since 2020-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Block对象", description = "")
public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "b_id", type = IdType.AUTO)
    private Integer bId;

    @ApiModelProperty(value = "版块名称")
    private String bName;

    @ApiModelProperty(value = "版块介绍")
    private String bIntroduce;

    @ApiModelProperty(value = "分值")
    private Integer bScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    private String head;

    @TableField(exist = false)
    private Integer attention;

    @TableField(exist = false)
    private Integer number;

}
