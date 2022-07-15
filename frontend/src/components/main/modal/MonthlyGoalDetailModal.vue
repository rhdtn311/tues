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
/*  목표 상세 */
#monthly-goal-detail, #daily-goal-detail {
  position: absolute;
  top: 5%;
  left: 37%;
  background-color: #F5F5F5;
  height: fit-content;
  width: 300px;
  padding-top: 30px;
  padding-left: 30px;
  padding-right: 30px;
  border: solid 5px #325288;
  border-radius: 7px;
}

.monthly-goal-detail-title, .daily-goal-detail-title {
  font-weight: bold;
  font-size: 20px;
}

.monthly-goal-detail-content, .daily-goal-detail-content {
  word-break: break-all;
  margin-bottom: 15px;
}

.close-button {
  width: 25px;
  position: absolute;
  right: 16px;
  height: 25px;
  top: 14px
}

button {
  border-radius: 5px;
  border: solid 0px #292C6D;
  background-color: #292C6D;
  width: 62px;
  height: 32px;
  font-weight: bold;
  color: #FFF8F3;
  margin-bottom: 15px;
}
</style>