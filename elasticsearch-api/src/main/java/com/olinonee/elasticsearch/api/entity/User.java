package com.olinonee.elasticsearch.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户实体
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2023-04-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private String name;
    private String sex;
    private String tel;
    private Integer age;
}
