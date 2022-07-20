<template>
  <body>
  <div id="monthly-goal-detail">
    <button @click="close" class="close-button">X</button>
    <div class="monthly-goal-detail-title">이름</div>
    <div id="detail-goal-name" class="monthly-goal-detail-content"> {{this.detailGoal.name}} </div>
    <div class="monthly-goal-detail-title">내용</div>
    <div id="detail-goal-content" class="monthly-goal-detail-content">{{this.detailGoal.content}}</div>
    <div class="monthly-goal-detail-title">목표 종류</div>
    <img id="detail-goal-type" class="monthly-goal-detail-content" v-bind:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + this.detailGoal.goalType + '.png'" style="width: 68px">
    <div class="monthly-goal-detail-title">타입</div>
    <div id="detail-achieve-type" class="monthly-goal-detail-content">{{this.detailGoal.achieveType}}</div>
    <div class="monthly-goal-detail-title" v-if="this.detailGoal ==='COUNT' || this.detailGoal === 'TIME'">할달량</div>
    <div id="detail-quota" v-if="this.detailGoal ==='COUNT' || this.detailGoal === 'TIME'">
      <div v-if="this.detailGoal === 'COUNT'" style="margin-bottom: 20px">
        <span>{{this.detailGoal.goalCount}} / {{this.detailGoal.goalCountQuota}}</span><span style="font-size: 13px">개</span>
      </div>
      <div th:case="${T(kong.tues.goal.AchieveType).TIME}" style="margin-bottom: 20px">
        <span th:text="${detailGoal.goalTime} + '/' + ${detailGoal.goalTimeQuota}"></span><span style="font-size: 13px">시간</span>
      </div>
    </div>
    <div class="monthly-goal-detail-title">성공 여부</div>
    <div id="detail-success" class="monthly-goal-detail-content"> {{this.detailGoal.success ? "성공" : "진행 중"}}</div>
    <div id="detail-goal-buttons" style="margin-bottom: 12px; text-align: center;">
      <button @click="updateGoal" id="monthly-goal-update-button" style="margin-right: 25px;">수정</button>
      <button @click="deleteGoal" id="monthly-goal-delete-button">삭제</button>
    </div>
  </div>
  </body>
</template>

<script>
export default {
  name: "MonthlyGoalDetailModal",
  props: ["detailGoal"],
  methods: {
    close: function () {
      this.$emit("close");
    },
    deleteGoal : function() {
      this.$emit("delete", this.detailGoal.monthlyGoalId)
    },
    updateGoal : function() {
      this.$emit("update", this.detailGoal.monthlyGoalId)
    }
  }
}
</script>

<style scoped>
@import "../../../assets/MonthlyGoalDetailModal-css.css";
</style>