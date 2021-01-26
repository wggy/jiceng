package com.linewell.jiceng.gateway.entity;

import com.google.common.base.MoreObjects;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/***
 *
 * @author wangping created on 2020/11/4 18:59
 */
@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gmt_create", nullable = false)
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @PrePersist
    protected void prePersist() {
        if (this.createTime == null) {
            createTime = new Date();
        }
        if (this.updateTime == null) {
            updateTime = new Date();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        this.updateTime = new Date();
    }

    @PreRemove
    protected void preRemove() {
        this.updateTime = new Date();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("createTime", createTime)
                .add("updateTime", updateTime);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
