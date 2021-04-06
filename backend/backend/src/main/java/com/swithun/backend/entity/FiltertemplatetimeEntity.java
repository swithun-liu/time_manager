package com.swithun.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "filtertemplatetime", schema = "time_management", catalog = "")
public class FiltertemplatetimeEntity {
    private int id;
    private int filtertTemplateId;
    private byte type;
    private int time;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "filtertTemplateId")
    public int getFiltertTemplateId() {
        return filtertTemplateId;
    }

    public void setFiltertTemplateId(int filtertTemplateId) {
        this.filtertTemplateId = filtertTemplateId;
    }

    @Basic
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "time")
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FiltertemplatetimeEntity that = (FiltertemplatetimeEntity) o;

        if (id != that.id) return false;
        if (filtertTemplateId != that.filtertTemplateId) return false;
        if (type != that.type) return false;
        if (time != that.time) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + filtertTemplateId;
        result = 31 * result + (int) type;
        result = 31 * result + time;
        return result;
    }
}