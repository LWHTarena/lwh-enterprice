package com.lwhtarena.company.rank.entities;

import com.lwhtarena.company.rank.obj.RankUnitPlace;

import javax.persistence.*;

/**
 * @Author：liwh
 * @Description:
 * @Date 14:42 2018/8/4
 * @Modified By:
 * <h1>
 * <ol></ol>
 * </h1>
 * 继承映射在 Annotation 中使用 @Inheritance 注解，并且需要使
 * 用 strategy 属性指定继承策略，继承策略有 SINGLE_TABLE、
 * TABLE_PER_CLASS 和 JOINED 三种。
 *
 * @Inheritance 的 strategy 属性是指定继承关系的生成策略
 * @DiscriminatorColumn 注解作用是指定生成的新的判断对象类型的字段的名称和类型
 * @DiscriminatorValue 注解是确定此类（Person）的标示，即 DiscriminatorColumn 的值。
 *
 */

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RankUnit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "footLeft")
    private Integer footLeft;

    @Column(name = "footRight")
    private Integer footRight;

    @Column(name = "orderStr")
    private String orderStr;

    private String entitiesName;


    private RankUnit parent;

    private RankUnitPlace rup;

    public RankUnit() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getFootLeft() {
        return this.footLeft;
    }

    public void setFootLeft(Integer footLeft) {
        this.footLeft = footLeft;
    }

    public Integer getFootRight() {
        return this.footRight;
    }

    public void setFootRight(Integer footRight) {
        this.footRight = footRight;
    }

    public String getOrderStr() {
        return this.orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getEntitiesName() {
        return this.entitiesName;
    }

    public void setEntitiesName(String entitiesName) {
        this.entitiesName = entitiesName;
    }

    public RankUnit getParent() {
        return this.parent;
    }

    public void setParent(RankUnit parent) {
        this.parent = parent;
    }

    public RankUnitPlace getRup() {
        return this.rup;
    }

    public void setRup(RankUnitPlace rup) {
        this.rup = rup;
    }
}
