/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-12 16:42:46
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-31 08:49:08
 */
package com.swithun.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.swithun.backend.DTO.addTaskDTO;
import com.swithun.backend.dao.FinishedPlanRepository;
import com.swithun.backend.dao.FinishedTaskRecordRepository;
import com.swithun.backend.dao.PlanRepository;
import com.swithun.backend.dao.PlanTypeRepository;
import com.swithun.backend.dao.RelationRepository;
import com.swithun.backend.dao.TrackRepository;
import com.swithun.backend.dao.UnfinishedPlanRepository;
import com.swithun.backend.dao.subTaskRepository;
import com.swithun.backend.entity.FinishedPlanEntity;
import com.swithun.backend.entity.FinishedTaskRecordEntity;
import com.swithun.backend.entity.PlanEntity;
import com.swithun.backend.entity.PlanTypeEntity;
import com.swithun.backend.entity.RelationEntity;
import com.swithun.backend.entity.SubTaskEntity;
import com.swithun.backend.entity.TrackEntity;
import com.swithun.backend.entity.UnfinishedPlanEntity;
import com.swithun.backend.utils.ClassConvert;
import com.swithun.backend.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PlanService {
  @Autowired
  PlanRepository planR;
  @Autowired
  private UnfinishedPlanRepository ufPlanR;
  @Autowired
  private FinishedTaskRecordRepository fTaskRecordR;
  @Autowired
  private PlanTypeRepository planTypeR;
  @Autowired
  private FinishedPlanRepository fPlanR;
  @Autowired
  private subTaskRepository subTaskR;
  @Autowired
  private TrackRepository trackR;
  @Autowired
  private RelationRepository relationR;
  @Autowired
  private ClassConvert conveter;

  Logger logger = LoggerFactory.getLogger(PlanService.class);

  /**
   * @description: ??????????????????
   * @param {String date} date = "" ??????????????????
   * @return {List<UnfinishedPlanEntity>}
   */
  public List<PlanEntity> getDatePlan(String date) {

    List<PlanEntity> ans = new ArrayList<>();

    LocalDate now = (date == "" ? LocalDate.now() : DateUtil.TimeStamp2LocalDate(date));

    String strs[] = DateUtil.parsePreNext(now);

    List<UnfinishedPlanEntity> unfinishedPlanEntities = ufPlanR.findAll(new Specification<UnfinishedPlanEntity>() {
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<UnfinishedPlanEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        Join<Object, Object> join = root.join("planByPlanId");
        Path<Object> repeatTypePath = join.get("repeatType");
        Path<Object> expectedStartDatePath = join.get("expectedStartDate");
        Path<Object> expectedEndDatePath = join.get("expectedEndDate");

        Path<Object> expectedStartTimeBegin = join.get("expectedStartTimeBegin");
        Path<Object> expectedStartTimeEnd = join.get("expectedStartTimeEnd");
        Path<Object> expectedEndTimeBegin = join.get("expectedEndTimeBegin");
        Path<Object> expectedEndTimeEnd = join.get("expectedEndTimeEnd");

        // ???????????????
        Predicate predicate0 = criteriaBuilder.equal(repeatTypePath, 0);
        Predicate predicate0Date = criteriaBuilder.or(
            criteriaBuilder.between(expectedStartDatePath.as(String.class), strs[0], strs[1]),
            criteriaBuilder.between(expectedEndDatePath.as(String.class), strs[0], strs[1]));
        Predicate type0 = criteriaBuilder.and(predicate0, predicate0Date);

        // ???????????????
        Predicate type1 = criteriaBuilder.equal(repeatTypePath, 1);

        // ???????????????
        Predicate predicate2 = criteriaBuilder.equal(repeatTypePath, 2);
        Predicate predicate2Date = criteriaBuilder.or(
            criteriaBuilder.between(expectedStartDatePath.as(String.class), strs[4], strs[5]),
            criteriaBuilder.between(expectedEndDatePath.as(String.class), strs[4], strs[5]));
        Predicate type2 = criteriaBuilder.and(predicate2, predicate2Date);

        Predicate predicate3 = criteriaBuilder.equal(repeatTypePath, 3);
        Predicate predicate3Date = criteriaBuilder.or(
            criteriaBuilder.between(expectedStartDatePath.as(String.class), strs[2], strs[3]),
            criteriaBuilder.between(expectedEndDatePath.as(String.class), strs[2], strs[3]));
        Predicate type3 = criteriaBuilder.and(predicate3, predicate3Date);

        Predicate tipSB = criteriaBuilder.isNull(expectedStartTimeBegin);
        Predicate tipSE = criteriaBuilder.isNull(expectedStartTimeEnd);
        Predicate tipEB = criteriaBuilder.isNull(expectedEndTimeBegin);
        Predicate tipEE = criteriaBuilder.isNull(expectedEndTimeEnd);

        Predicate tip = criteriaBuilder.and(tipSB, tipSE, tipEB, tipEE);

        Predicate type = criteriaBuilder.or(type0, type1, type2, type3);
        return criteriaBuilder.and(type, tip);
      }
    });

    // List<UnfinishedPlanEntity>  unfinishedPlanEntities = ufPlanR.findAll();


    for (UnfinishedPlanEntity ufPlan : unfinishedPlanEntities) {
      PlanEntity plan = ufPlan.getPlanByPlanId();
      plan.setTypeList(conveter.getTypeList(plan));
      ans.add(plan);
    }
    return ans;
  }

  /**
   * @description: ???????????????task
   * @param {*}
   * @return {List<PlanEntity>}
   */

  public List<UnfinishedPlanEntity> gettaskbydate(String date) {
    // List<UnfinishedPlanEntity> findAll = ufPlanR.findAll();
    // List<UnfinishedPlanEntity> res = new ArrayList<>();
    // for (UnfinishedPlanEntity ufPlan : findAll) {
    //   ufPlan.getPlanByPlanId().getExpectedStartDate()hj
    //   if ()
    // }

    LocalDate localDate = date == "" ? LocalDate.now() : LocalDate.parse(date);
    String[] strs = DateUtil.parse(localDate);

    logger.info("strs[0]: " + strs[0]);

    List<UnfinishedPlanEntity> unfinishedPlanEntities = ufPlanR.findAll(new Specification<UnfinishedPlanEntity>() {

      @Override
      public Predicate toPredicate(Root<UnfinishedPlanEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        Join<Object, Object> join = root.join("planByPlanId");
        Path<Object> repeat_type = join.get("repeatType");
        Path<Object> expected_start_date_path = join.get("expectedStartDate");
        Path<Object> expected_end_date_path = join.get("expectedEndDate");

        Predicate p0 = criteriaBuilder.and(criteriaBuilder.equal(repeat_type, 0),
            criteriaBuilder.lessThanOrEqualTo(expected_start_date_path.as(String.class), strs[0]),
            criteriaBuilder.greaterThanOrEqualTo(expected_end_date_path.as(String.class), strs[0]));

        Predicate p1 = criteriaBuilder.and(criteriaBuilder.equal(repeat_type, 1),
            criteriaBuilder.lessThanOrEqualTo(expected_start_date_path.as(String.class), strs[3]),
            criteriaBuilder.greaterThanOrEqualTo(expected_end_date_path.as(String.class), strs[3]));

        Predicate p2 = criteriaBuilder.and(criteriaBuilder.equal(repeat_type, 2),
            criteriaBuilder.lessThanOrEqualTo(expected_start_date_path.as(String.class), strs[2]),
            criteriaBuilder.greaterThanOrEqualTo(expected_end_date_path.as(String.class), strs[2]));

        Predicate p3 = criteriaBuilder.and(criteriaBuilder.equal(repeat_type, 3),
            criteriaBuilder.lessThanOrEqualTo(expected_start_date_path.as(String.class), strs[1]),
            criteriaBuilder.greaterThanOrEqualTo(expected_end_date_path.as(String.class), strs[1]));
        return criteriaBuilder.or(p0, p1, p2, p3);
      }

    });
    return unfinishedPlanEntities;

  }

  public Map<String, Object> dealWithGetAllLimitedPlan(String date) {
    // 0 ??????????????????????????????(??????????????? ??????)
    List<UnfinishedPlanEntity> ufPlans = gettaskbydate(date);

    Map<String, Object> mp = new ConcurrentHashMap<>();
    List<PlanEntity> oPlans = new ArrayList<>();
    List<PlanEntity> tPlans = new ArrayList<>();

    Set<Integer> checkPlanIds = new HashSet<>();
    // 1. ?????? ???????????? ??????
    logger.warn("ufPlans??????" + ufPlans.size());
    for (UnfinishedPlanEntity ufPlan : ufPlans) {
      PlanEntity plan = ufPlan.getPlanByPlanId();
      if (getPlanType(plan) == 11) { // ?????????????????????
        plan.setTypeList(conveter.getTypeList(plan));
        tPlans.add(plan);
        checkPlanIds.add(plan.getId());
      }
    }
    logger.warn("tPlan??????" + tPlans.size());
    mp.put("timeLimitedPlanSet", tPlans);
    // 2. ?????????????????? ?????????
    List<RelationEntity> allRelation = relationR.findAll();// ???????????? relation
    Map<String, Set<String>> piles = new ConcurrentHashMap<>();
    Set<Integer> checkedRelation = new HashSet<>();
    boolean notFinish = false;
    do {
      notFinish = false;
      for (RelationEntity relation : allRelation) {
        if (!checkedRelation.contains(relation.getId())) {

          Integer pre = relation.getPlanByPrePlanId().getId();
          Integer back = relation.getPlanByPlanId().getId();

          if (checkPlanIds.contains(pre) || checkPlanIds.contains(back)) {

            String pre_str = (getPlanType(relation.getPlanByPrePlanId()) == 21 ? "o" : "t") + String.valueOf(pre);
            String back_str = (getPlanType(relation.getPlanByPlanId()) == 21 ? "o" : "t") + String.valueOf(back);

            notFinish = true;

            checkedRelation.add(relation.getId());

            if (!checkPlanIds.contains(pre)) {
              checkPlanIds.add(pre);
              if (getPlanType(relation.getPlanByPrePlanId()) == 21) {
                PlanEntity plan = relation.getPlanByPrePlanId();
                plan.setTypeList(conveter.getTypeList(plan));
                oPlans.add(plan); // ?????? ???????????? Plan
              }
            }
            if (!checkPlanIds.contains(back)) {
              checkPlanIds.add(back);
              if (getPlanType(relation.getPlanByPlanId()) == 21) {
                PlanEntity plan = relation.getPlanByPlanId();
                plan.setTypeList(conveter.getTypeList(plan));
                oPlans.add(plan); // ?????? ???????????? Plan
              }
            }
            if (!piles.containsKey(pre_str)) {
              piles.put(pre_str, new HashSet<>()); // ?????? ???
            }
            piles.get(pre_str).add(back_str); // ??? ??? ????????????
          }
        }
      }
    } while (notFinish);

    mp.put("planRelation", piles);

    // 3. ?????????????????? plan

    mp.put("orderLimitedPlanSet", oPlans);

    return mp;
  }

  public Map<String, Object> getAllUnlimitedPlan() {
    Map<String, Object> mp = new HashMap<>();

    List<UnfinishedPlanEntity> ufPlans = ufPlanR.findAll();
    List<PlanEntity> noPlans = new ArrayList<>();
    List<PlanEntity> lowPlans = new ArrayList<>();
    List<PlanEntity> mediumPlans = new ArrayList<>();
    List<PlanEntity> hightPlans = new ArrayList<>();
    for (UnfinishedPlanEntity ufPlan : ufPlans) {
      PlanEntity plan = ufPlan.getPlanByPlanId();
      plan.setTypeList(conveter.getTypeList(plan));
      if (getPlanType(plan) == 22) {
        Integer prot = plan.getPriority();
        if (prot == null) {
          noPlans.add(plan);
        } else if (prot == 1) {
          lowPlans.add(plan);
        } else if (prot == 2) {
          mediumPlans.add(plan);
        } else if (prot == 3) {
          hightPlans.add(plan);
        }
      }
    }
    mp.put("no", noPlans);
    mp.put("low", lowPlans);
    mp.put("medium", mediumPlans);
    mp.put("high", hightPlans);
    return mp;
  }

  public Integer getPlanType(PlanEntity plan) {

    if (plan.getExpectedStartDate() != null || plan.getExpectedEndDate() != null) {
      if (plan.getExpectedStartTimeBegin() != null
          || plan.getExpectedStartTimeEnd() != null || plan.getExpectedEndTimeBegin() != null
          || plan.getExpectedEndTimeEnd() != null) {
        logger.info("?????????????????????");
        return 11; // date ?????? time ?????? -> ??????????????????
      } else {
        logger.info("?????? ???????????????");
        return 12; // date ?????? time ????????? -> ??? ??? ??????
      }
    } else {
      if (plan.getX() != null) {
        logger.info("?????????????????????");
        return 21; // date ????????? time ????????? pos ?????? -> ??????????????????
      } else {
        logger.info("??????????????????");
        return 22; // date ????????? time ????????? pos ????????? -> ???????????????
      }
    }
    // if (plan.getExpectedStartTimeBegin() == null &&
    // plan.getExpectedStartTimeEnd() == null
    // && plan.getExpectedEndTimeBegin() == null && plan.getExpectedEndTimeEnd() ==
    // null) {
    // if (plan.getX() != null) {
    // return 1;// ??????????????????
    // } else {
    // return 2; // ???????????????
    // }
    // } else {
    // return 0;// ??????????????????
    // }
  }

  /**
   * @description: ???????????????????????????
   * @param {*}
   * @return {List<PlanEntity>}
   */
  public List<UnfinishedPlanEntity> getAllPlan() {
    return ufPlanR.findAll();
  }

  /**
   * @description: ??????????????????(???????????????)
   * @param {*}
   * @return {*}
   */
  public Integer addPlan(PlanEntity plan, Boolean isTime) {

    // 0. ??????plan
    conveter.completeAddPlan(plan);

    if (!isTime) {
      plan.setExpectedStartTimeBegin(null);
      plan.setExpectedStartTimeEnd(null);
      plan.setExpectedEndTimeBegin(null);
      plan.setExpectedEndTimeEnd(null);
    }

    logger.info("addPlan" + plan.getId());

    // 1 ???subPlan ???plan????????? ??????plan
    for (SubTaskEntity subTask : plan.getSubTasksById()) {
      subTask.setPlanByParentPlan(plan);
    }
    // 2. ?????? plan
    PlanEntity savedPlan = planR.save(plan);
    // 3. ??? ????????????????????????
    UnfinishedPlanEntity ufPlan = ufPlanR.findByplanByPlanId(plan);

    // 3.1 ??????????????? (?????????????????????????????????) ?????? ??????????????? ufPlan
    // 3.2 ???????????????
    if (ufPlan == null) {
      UnfinishedPlanEntity unfinishedPlanEntity = new UnfinishedPlanEntity();
      unfinishedPlanEntity.setPlanByPlanId(plan);
      ufPlanR.save(unfinishedPlanEntity);
    }

    return savedPlan.getId();
  }

  public void finishPlanByOnce(FinishedTaskRecordEntity finishedTaskRecordEntity) {
    fTaskRecordR.save(finishedTaskRecordEntity);
  }

  public void addPlanType(PlanTypeEntity planTypeEntity) {
    System.out.println(planTypeEntity.getId());
    System.out.println(planTypeEntity.getName());
    planTypeR.save(planTypeEntity);
  }

  public List<PlanTypeEntity> getAllPlanType() {
    return planTypeR.findAllByPlanTypeByParentId(null);
  }

  public void updatePlanTypeName(PlanTypeEntity planTypeEntity) {
    PlanTypeEntity old = planTypeR.findById(planTypeEntity.getId()).get();
    old.setName(planTypeEntity.getName());
    planTypeR.save(old);
  }

  public void deletePlanType(PlanTypeEntity planTypeEntity) {
    PlanTypeEntity originPlanTypeEntity = planTypeR.findById(planTypeEntity.getId()).get();
    planTypeR.deleteAll(originPlanTypeEntity.getPlanTypesById());
    planTypeR.delete(originPlanTypeEntity);
  }

  public PlanTypeEntity getPlanTypeEntityByName(String name) {
    return planTypeR.findOneByName(name);
  }

  public void addtask(addTaskDTO dto) {
    PlanEntity plan = new PlanEntity();
    plan.setPlanName(dto.getName());
    plan.setRepeatType(0);
    plan.setPracticeStartDateTime(dto.getStartTime());
    plan.setPracticeEndDateTime(dto.getEndTime());
    plan.setDevotion(dto.getDevotion());
    plan.setSatisfaction(dto.getSatisfaction());
    plan.setTime(dto.getTime());
    logger.info("??????" + dto.getTime());
    logger.info("??????" + plan.getTime());
    plan.setNote(dto.getNote());
    plan.setPlanTypeByPlanType(new PlanTypeEntity(dto.getType()));
    plan.setExpectedTime(dto.getExpectedTime());
    List<SubTaskEntity> subTasks = dto.getSubTasks();
    for (SubTaskEntity task : subTasks) {
      task.setPlanByParentPlan(plan);
    }
    plan.setSubTasksById(subTasks);
    List<TrackEntity> tracks = dto.getTracks();
    for (TrackEntity track : tracks) {
      track.setPlanByParentPlan(plan);
    }
    plan.setTracksById(tracks);

    PlanEntity rtPlan = planR.save(plan);
    logger.info("????????? id " + rtPlan.getId());
    // trackR.saveAll(tracks);
    FinishedTaskRecordEntity finishedTaskRecord = new FinishedTaskRecordEntity();
    finishedTaskRecord.setStartTime(dto.getStartTime());
    finishedTaskRecord.setEndTime(dto.getEndTime());
    finishedTaskRecord.setPlanByPlanId(rtPlan);
    fTaskRecordR.save(finishedTaskRecord);
    FinishedPlanEntity finishedPlanEntity = new FinishedPlanEntity();
    finishedPlanEntity.setPlanByPlanId(rtPlan);
    fPlanR.save(finishedPlanEntity);
  }

  public void addRelation(Integer plan_id, Integer pre_plan_id) {
    PlanEntity plan = new PlanEntity(plan_id);
    PlanEntity prePlan = new PlanEntity(pre_plan_id);
    RelationEntity relation = new RelationEntity();
    relation.setPlanByPlanId(plan);
    relation.setPlanByPrePlanId(prePlan);
    relationR.save(relation);
  }

  public void updateRelation(Map<String, Object> mp) {

    String date = DateUtil.TimeStamp2LocalDateStr((String) mp.get("date"));

    // 2.2 ?????????????????? ???????????? ??????j
    List<PlanEntity> oldOplans = (List<PlanEntity>) dealWithGetAllLimitedPlan(date).get("orderLimitedPlanSet");

    Integer updateType = (Integer) mp.get("type");
    List<Integer> relation = (List<Integer>) mp.get("relation");
    if (updateType == 0) {
      System.out.println("?????????");
      relationR.deleteAllByPlanByPlanIdAndPlanByPrePlanId(new PlanEntity(relation.get(1)),
          new PlanEntity(relation.get(0)));
      System.out.println("?????????");
      RelationEntity newRelation = new RelationEntity();
      newRelation.setPlanByPlanId(new PlanEntity(relation.get(2)));
      newRelation.setPlanByPrePlanId(new PlanEntity(relation.get(0)));
      relationR.save(newRelation);
    } else {
      relationR.deleteAllByPlanByPlanIdAndPlanByPrePlanId(new PlanEntity(relation.get(0)),
          new PlanEntity(relation.get(1)));
      RelationEntity newRelation = new RelationEntity();
      newRelation.setPlanByPlanId(new PlanEntity(relation.get(0)));
      newRelation.setPlanByPrePlanId(new PlanEntity(relation.get(2)));
      relationR.save(newRelation);
    }

    dealOPlan2NoPlan(oldOplans, date);
  }

  public void deleteRelation(Map<String, Object> mp) {

    String date = DateUtil.TimeStamp2LocalDateStr((String) mp.get("date"));
    logger.info(date);

    // 2.2 ?????????????????? ???????????? ??????j
    List<PlanEntity> oldOplans = (List<PlanEntity>) dealWithGetAllLimitedPlan(date).get("orderLimitedPlanSet");
    logger.info("????????????relation");
    for (PlanEntity planEntity : oldOplans) {
      logger.info("old" + planEntity.getId() + getPlanType(planEntity));
    }

    // 1 ????????????
    List<Integer> relation = (List<Integer>) mp.get("relation");
    PlanEntity plan = new PlanEntity();
    plan.setId(relation.get(0));
    PlanEntity prePlan = new PlanEntity();
    prePlan.setId(relation.get(1));
    relationR.deleteAllByPlanByPlanIdAndPlanByPrePlanId(plan, prePlan);

    // 2 ???????????? ?????? ????????? ?????????????????? -> ??????????????? ??????

    dealOPlan2NoPlan(oldOplans, date);
    logger.info("????????????relation");

  }

  public void dealOPlan2NoPlan(List<PlanEntity> oldOplans, String date) {

    Set<Integer> checkSet = new HashSet<>();

    List<PlanEntity> newOplans = (List<PlanEntity>) dealWithGetAllLimitedPlan(date).get("orderLimitedPlanSet");

    for (PlanEntity planEntity : newOplans) {
      logger.info("new" + planEntity.getId() + getPlanType(planEntity));
    }

    // 2.1 ???????????? ??????????????????
    for (PlanEntity p : newOplans) {
      checkSet.add(p.getId());
    }

    // 2.3 ????????? ????????? ?????????????????? ?????? ???????????????
    for (PlanEntity p : oldOplans) {
      if (!checkSet.contains(p.getId())) {
        p.setX(null);
        p.setY(null);
        planR.save(p);
      }
    }
  }

  public void deleteWeekMonthPlan(Map<String, Object> mp) {
    planR.deleteById((Integer) mp.get("id"));
  }

  public Map<String, Set<String>> deleteDayPlan(Integer id, String date) {

    PlanEntity plan = planR.findOneById(id);
    logger.info("???????????? id " + id);

    // 2.2 ?????????????????? ???????????? ??????
    List<PlanEntity> oldOplans = (List<PlanEntity>) dealWithGetAllLimitedPlan(date).get("orderLimitedPlanSet");
    for (PlanEntity p : oldOplans) {
      logger.info("????????? id " + p.getId());
    }

    // 1 ????????????

    Map<String, Set<String>> mp = new HashMap<>();

    Set<String> rs = new HashSet<>();
    Set<String> oPlans = new HashSet<>();

    // 1.1 ????????????????????? relation
    List<RelationEntity> delRelations = relationR.findAllByPlanByPlanIdOrPlanByPrePlanId(plan, plan);

    for (RelationEntity relation : delRelations) {
      PlanEntity backPlan = relation.getPlanByPlanId();
      PlanEntity prePlan = relation.getPlanByPrePlanId();

      logger.info(backPlan.getId().toString());
      logger.info(prePlan.getId().toString());

      String backPlan_str = null;
      String prePlan_str = null;

      // 1.2 ?????? relation ???????????? ??? ?????????????????? -> ????????? oPlans
      if (getPlanType(backPlan) == 21) {
        logger.info("backPlan ??? ????????????");
        backPlan_str = "o" + String.valueOf(backPlan.getId());
        if (backPlan.getId() != plan.getId())
          oPlans.add(backPlan_str);
      } else if (getPlanType(backPlan) == 11) {
        logger.info("backPlan ??? ????????????");
        backPlan_str = "t" + String.valueOf(backPlan.getId());
      }
      if (getPlanType(prePlan) == 21) {
        logger.info("prePlan ??? ????????????");
        prePlan_str = "o" + String.valueOf(prePlan.getId());
        if (prePlan.getId() != plan.getId())
          oPlans.add(prePlan_str);
      } else if (getPlanType(prePlan) == 11) {
        logger.info("prePlan ??? ????????????");
        prePlan_str = "t" + String.valueOf(prePlan.getId());
      }
      // 1.3 ????????? ???????????? relation
      rs.add("r" + prePlan_str + "-" + backPlan_str);

    }

    // 1.4 ?????? mp
    mp.put("relations", rs);
    mp.put("plans", oPlans);

    plan = planR.findById(plan.getId()).get();

    System.out.println("plan ID " + plan.getId());

    List<UnfinishedPlanEntity> ufPlans = new ArrayList<>(plan.getUnfinishedPlansById());

    plan.getUnfinishedPlansById().removeAll(ufPlans);

    ufPlanR.deleteAll(ufPlans);
    planR.delete(plan);

    // 2 ???????????? ?????? ????????? ?????????????????? -> ??????????????? ??????

    oldOplans.remove(plan);

    dealOPlan2NoPlan(oldOplans, date);

    return mp;

  }

  public void updatePlanPos(Map<String, Object> mp) {
    PlanEntity plan = planR.findOneById((Integer) mp.get("id"));
    plan.setX((Integer) mp.get("x"));
    plan.setY((Integer) mp.get("y"));
    planR.save(plan);
  }

  public void updatePlan(PlanEntity plan) {
    planR.save(plan);
  }

}
