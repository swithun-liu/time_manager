package com.swithun.backend.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plan", schema = "time_manger", catalog = "")
public class PlanEntity {

    private Integer id;
    private String planName;
    private Integer repeatType;
    // 0 不重复 ymd
    // 1 周重复 1-7
    // 2 月重复 d
    // 3 年重复 md
    private String expectedStartDate;
    private String expectedEndDate;
    private String expectedStartTimeBegin;
    private String expectedStartTimeEnd;
    private String expectedEndTimeBegin;
    private String expectedEndTimeEnd;
    private String practiceStartDateTime;
    private String practiceEndDateTime;
    private Integer x;
    private Integer y;
    private Integer devotion;
    private Integer satisfaction;
    private Integer priority;
    private Integer expectedTime;
    private Integer time;
    private String note;
    private ArrayList<Integer> typeList;
    @JsonIgnore
    private Collection<FinishedTaskRecordEntity> finishedTaskRecordsById;

    @JsonIgnore
    private Collection<TaskTemplateEntity> taskTemplatesById;
    @JsonIgnore
    private Collection<UnfinishedPlanEntity> unfinishedPlansById;
    @JsonIgnore
    private Collection<FinishedPlanEntity> finishedPlansById;
    @JsonProperty("taskType")
    private PlanTypeEntity planTypeByPlanType;
    @JsonProperty("subTasks")
    private Collection<SubTaskEntity> subTasksById;
    // @JsonIgnore
    private Collection<TrackEntity> tracksById;

    @JsonIgnore
    private Collection<RelationEntity> relationsById;// 当前plan
    @JsonIgnore
    private Collection<RelationEntity> relationsById_0; // plan前驱

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Transient
    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(ArrayList<Integer> typeList) {
        this.typeList = typeList;
    }

    @Basic
    @Column(name = "priority")
    // 0 no
    // 1 low
    // 2 medium
    // 3 high
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public PlanEntity() {
    }

    public PlanEntity(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "expected_time")
    public Integer getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Integer expectedTime) {
        this.expectedTime = expectedTime;
    }

    @Basic
    @Column(name = "expected_start_time_begin")
    public String getExpectedStartTimeBegin() {
        return expectedStartTimeBegin;
    }

    public void setExpectedStartTimeBegin(String expectedStartTimeBegin) {
        this.expectedStartTimeBegin = expectedStartTimeBegin;
    }

    @Basic
    @Column(name = "expected_start_time_end")
    public String getExpectedStartTimeEnd() {
        return expectedStartTimeEnd;
    }

    public void setExpectedStartTimeEnd(String expectedStartTimeEnd) {
        this.expectedStartTimeEnd = expectedStartTimeEnd;
    }

    @Basic
    @Column(name = "expected_end_time_begin")
    public String getExpectedEndTimeBegin() {
        return expectedEndTimeBegin;
    }

    public void setExpectedEndTimeBegin(String expectedEndTimeBegin) {
        this.expectedEndTimeBegin = expectedEndTimeBegin;
    }

    @Basic
    @Column(name = "expected_end_time_end")
    public String getExpectedEndTimeEnd() {
        return expectedEndTimeEnd;
    }

    public void setExpectedEndTimeEnd(String expectedEndTimeEnd) {
        this.expectedEndTimeEnd = expectedEndTimeEnd;
    }

    @Basic
    @Column(name = "x")
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Basic
    @Column(name = "plan_name")
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Basic
    @Column(name = "repeat_type")
    public Integer getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(Integer repeatType) {
        this.repeatType = repeatType;
    }

    @Basic
    @Column(name = "expected_start_date")
    public String getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(String expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    @Basic
    @Column(name = "expected_end_date")
    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    @Basic
    @Column(name = "practice_start_date_time")
    public String getPracticeStartDateTime() {
        return practiceStartDateTime;
    }

    public void setPracticeStartDateTime(String practiceStartDateTime) {
        this.practiceStartDateTime = practiceStartDateTime;
    }

    @Basic
    @Column(name = "practice_end_date_time")
    public String getPracticeEndDateTime() {
        return practiceEndDateTime;
    }

    public void setPracticeEndDateTime(String practiceEndDateTime) {
        this.practiceEndDateTime = practiceEndDateTime;
    }

    @Basic
    @Column(name = "devotion")
    public Integer getDevotion() {
        return devotion;
    }

    public void setDevotion(Integer devotion) {
        this.devotion = devotion;
    }

    @Basic
    @Column(name = "satisfaction")
    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    @Basic
    @Column(name = "time")
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlanEntity that = (PlanEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(planName, that.planName)
                && Objects.equals(repeatType, that.repeatType)
                && Objects.equals(expectedStartDate, that.expectedStartDate)
                && Objects.equals(expectedEndDate, that.expectedEndDate)
                && Objects.equals(practiceStartDateTime, that.practiceStartDateTime)
                && Objects.equals(practiceEndDateTime, that.practiceEndDateTime)
                && Objects.equals(devotion, that.devotion) && Objects.equals(satisfaction, that.satisfaction)
                && Objects.equals(time, that.time) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, planName, repeatType, expectedStartDate, expectedEndDate, practiceStartDateTime,
                practiceEndDateTime, devotion, satisfaction, time, note);
    }

    @OneToMany(mappedBy = "planByPlanId", cascade = CascadeType.ALL)
    public Collection<FinishedTaskRecordEntity> getFinishedTaskRecordsById() {
        return finishedTaskRecordsById;
    }

    public void setFinishedTaskRecordsById(Collection<FinishedTaskRecordEntity> finishedTaskRecordsById) {
        this.finishedTaskRecordsById = finishedTaskRecordsById;
    }

    @OneToMany(mappedBy = "planByPlanId", cascade = CascadeType.ALL)
    public Collection<UnfinishedPlanEntity> getUnfinishedPlansById() {
        return unfinishedPlansById;
    }

    public void setUnfinishedPlansById(Collection<UnfinishedPlanEntity> unfinishedPlansById) {
        this.unfinishedPlansById = unfinishedPlansById;
    }

    // 已完成计划
    @OneToMany(mappedBy = "planByPlanId", cascade = CascadeType.ALL)
    public Collection<FinishedPlanEntity> getFinishedPlansById() {
        return finishedPlansById;
    }

    public void setFinishedPlansById(Collection<FinishedPlanEntity> finishedPlansById) {
        this.finishedPlansById = finishedPlansById;
    }

    // 任务模板
    @OneToMany(mappedBy = "planByPlanId", cascade = CascadeType.ALL)
    public Collection<TaskTemplateEntity> getTaskTemplatesById() {
        return taskTemplatesById;
    }

    public void setTaskTemplatesById(Collection<TaskTemplateEntity> taskTemplatesById) {
        this.taskTemplatesById = taskTemplatesById;
    }
    //

    @ManyToOne
    @JoinColumn(name = "plan_type", referencedColumnName = "id")
    public PlanTypeEntity getPlanTypeByPlanType() {
        return planTypeByPlanType;
    }

    public void setPlanTypeByPlanType(PlanTypeEntity planTypeByPlanType) {
        this.planTypeByPlanType = planTypeByPlanType;
    }

    @OneToMany(mappedBy = "planByParentPlan", cascade = CascadeType.ALL)
    public Collection<SubTaskEntity> getSubTasksById() {
        return subTasksById;
    }

    public void setSubTasksById(Collection<SubTaskEntity> subTasksById) {
        this.subTasksById = subTasksById;
    }

    @OneToMany(mappedBy = "planByParentPlan", cascade = CascadeType.ALL)
    public Collection<TrackEntity> getTracksById() {
        return tracksById;
    }

    public void setTracksById(Collection<TrackEntity> tracksById) {
        this.tracksById = tracksById;
    }

    @OneToMany(mappedBy = "planByPlanId", cascade = CascadeType.ALL)
    public Collection<RelationEntity> getRelationsById() {
        return relationsById;
    }

    public void setRelationsById(Collection<RelationEntity> relationsById) {
        this.relationsById = relationsById;
    }

    @OneToMany(mappedBy = "planByPrePlanId", cascade = CascadeType.ALL)
    public Collection<RelationEntity> getRelationsById_0() {
        return relationsById_0;
    }

    public void setRelationsById_0(Collection<RelationEntity> relationsById_0) {
        this.relationsById_0 = relationsById_0;
    }

}
