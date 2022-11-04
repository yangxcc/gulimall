package com.yangxcc.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 品牌
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-28 10:27:54
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 * @NotBlank、@NotNull、@NotEmpty的区别
	 * - @NotNull不能为null，但可以为empty，一般用在 Integer 类型的基本数据类型的非空校验上
	 * - @NotEmpty不能为null，且长度必须大于0，一般用在集合类或者数组类上
	 * - @NotBlank 只能用在string类型的字段上，且调用 trim() 后，长度必须大于0，即不能只有空格，必须有实际字符
	 * */
	@NotBlank(message = "名字不能为空")
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "log地址必须是url")
	private String logo;
	/**
	 * 介绍
	 */
	@NotBlank(message = "描述不能为空")
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@Range(min = 0, max = 1)
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotNull
	@Pattern(regexp = "/^[a-zA-Z]$", message = "检索首字母必须是一个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0, message = "排序数字必须大于0")
	private Integer sort;

}
