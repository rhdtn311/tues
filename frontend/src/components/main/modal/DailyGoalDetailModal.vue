<template>
  <body>
  <div id="daily-goal-detail">
    <button @click="close" class="close-button">X</button>
    <div class="daily-goal-detail-title">이름</div>
    <div id="detail-goal-name" class="daily-goal-detail-content">{{detailGoal.name}}</div>
    <div class="daily-goal-detail-title">내용</div>
    <div id="detail-goal-content" class="daily-goal-detail-content">{{detailGoal.content}}</div>
    <div class="daily-goal-detail-title">목표 종류</div>
    <img id="detail-goal-type" class="daily-goal-detail-content" :src="goalTypeImg(detailGoal.goalType)" style="width: 68px">
    <div class="daily-goal-detail-title">타입</div>
    <div id="detail-achieve-type" class="daily-goal-detail-content">{{detailGoal.achieveType}}</div>
    <div class="daily-goal-detail-title" v-if="this.detailGoal.achieveType === 'COUNT' || this.detailGoal.achieveType === 'TIME'">할달량</div>
    <div id="detail-quota">
      <div v-if="this.detailGoal.achieveType === 'COUNT'" style="margin-bottom: 20px">
        <span>{{this.detailGoal.goalCount}} / {{this.detailGoal.goalCountQuota}}</span><span style="font-size: 13px">개</span>
      </div>
      <div v-if="this.detailGoal.achieveType === 'TIME'" style="margin-bottom: 20px">
        <span>{{this.detailGoal.goalTime}} / {{this.detailGoal.goalTimeQuota}}</span><span style="font-size: 13px">시간</span>
      </div>
    </div>
    <div class="daily-goal-detail-title">성공 여부</div>
    <div id="detail-success" class="daily-goal-detail-content"> {{this.detailGoal.success === true ? '성공' : '진행 중'}}</div>
    <div id="detail-goal-buttons" style="margin-bottom: 12px; text-align: center;">
      <button @click="updateGoal(detailGoal.dailyGoalId)" id="daily-goal-update-button" style="margin-right: 25px;" >수정</button>
      <button @click="deleteGoal(detailGoal.dailyGoalId)" id="daily-goal-delete-button">삭제</button>
    </div>
  </div>

  </body>
</template>

<script>
export default {
  name: "DailyGoalDetailModal",
  props: ["detailGoal"],
  methods: {
    goalTypeImg(goalType) {return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-" + goalType + ".png"},
    close: function () {
      this.$emit("close");
    },
    updateGoal(dailyGoalId) {
      this.$emit("update", dailyGoalId);
    },
    deleteGoal(dailyGoalId) {
      this.$emit("delete", dailyGoalId);
    }
  }
}
</script>

<style scoped>
@import "../../../assets/DailyGoalDetailModal-css.css";
</style>