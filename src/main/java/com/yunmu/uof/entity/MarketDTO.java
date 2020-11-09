package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MarketDto
 *
 * @author aleng
 * @version 1.0.0
 * @since 2020年7月29日 下午8:48:57
 */
@Data
@ToString(callSuper = true)
public class MarketDTO implements Serializable {

  /**
   * 盘口id
   */
  public String id;

  /**
   * marketId
   */
  public String marketId;
  /**
   * 盘口名称
   */
  public String marketName;
  /**
   * 修饰符
   */
  public List<SpecifierDTO> specifiers = new ArrayList<>();
  /**
   * 盘口的状态(0-活跃/1-暂停/2-失活/3-结算/4-已关闭/5-盘口转移)
   */
  private Integer status;
}
