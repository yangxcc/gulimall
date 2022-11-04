package com.yangxcc.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangxcc.common.validgroup.addGroup;
import com.yangxcc.common.validgroup.updateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
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
	// 只有注册了@Validated(value = addGroup.class)，下面的Null注解才会生效，
	// 而NotNull只有在注册了@Validated(value = updateGroup.class)的时候才会生效
	// 对于没有指定分组的注解，只有在@Validated/无@Validated的情况下才会生效
	@Null(groups = addGroup.class)
	@NotNull(groups = updateGroup.class)
	private Long brandId;
	/**
	 * 品牌名
	 * @NotBlank、@NotNull、@NotEmpty的区别
	 * - @NotNull不能为null，但可以为empty，一般用在 Integer 类型的基本数据类型的非空校验上
	 * - @NotEmpty不能为null，且长度必须大于0，一般用在集合类或者数组类上
	 * - @NotBlank 只能用在string类型的字段上，且调用 trim() 后，长度必须大于0，即不能只有空格，必须有实际字符
	 * */
	@NotBlank(message = "名字不能为空", groups = {addGroup.class, updateGroup.class})
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
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0, message = "排序数字必须大于0")
	private Integer sort;

}
