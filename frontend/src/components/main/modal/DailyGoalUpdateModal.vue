<template>
  <body>
  <div class="container" id="update-daily-goal-modal">
    <div id="head">
      <h1 id="title">일간 목표 수정</h1>
      <button @click="openCreatedMonthlyGoals" id="check-monthly-goals-btn"> 월간 목표 확인 ▶</button>
    </div>
    <div v-if="this.isCreatedMonthlyGoals" id="check-monthly-goals" style="background-color: #ffcf93">
      <div id="all-monthly-goals">
        <ul v-for="monthlyGoal in monthlyGoals" style="padding-left: 27px; padding-right: 40px;">
          <div style="display: flex">
            <img style="width: 20px" :src="goalTypeImage(monthlyGoal.goalType)" >
            <li>{{monthlyGoal.name}}</li>
          </div>
        </ul>
      </div>
    </div>
    <form id="create-monthly-goal-form">
      <input id="monthly-goal-id" hidden>
      <div class="buttons">
        <ul class="goal-types" style = "margin-left: 18px; padding-bottom: 10px">
          <li><img class="goal-type-img" :src="goalTypeImage('A')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('A')" type="radio" name="goalType" id="goalTypeA" value="A"><label for="goalTypeA"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('B')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('B')" type="radio" name="goalType" id="goalTypeB" value="B"><label for="goalTypeB"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('C')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('C')" type="radio" name="goalType" id="goalTypeC" value="C"><label for="goalTypeC"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('D')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('D')" type="radio" name="goalType" id="goalTypeD" value="D"><label for="goalTypeD"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('E')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('E')" type="radio" name="goalType" id="goalTypeE" value="E"><label for="goalTypeE"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('F')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('F')" type="radio" name="goalType" id="goalTypeF" value="F"><label for="goalTypeF"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('G')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('G')" type="radio" name="goalType" id="goalTypeG" value="G"><label for="goalTypeG"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('H')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('H')" type="radio" name="goalType" id="goalTypeH" value="H"><label for="goalTypeH"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('I')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('I')" type="radio" name="goalType" id="goalTypeI" value="I"><label for="goalTypeI"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('J')"><input class="goal-type" v-model="dailyGoal.goalType" :checked="isChecked('J')" type="radio" name="goalType" id="goalTypeJ" value="J"><label for="goalTypeJ"></label></li>
        </ul>
      </div>
      <br>
      <div id="date">
        <div style="font-size: 18px; font-weight: bold;" >날짜</div>
        <label for="create-goal-year"></label>
        <input v-model="dailyGoal.year" type="text" id="create-goal-year" name="year" placeholder="YEAR">
        <label for="create-goal-month"></label>
        <input v-model="dailyGoal.month" type="text" min="1" max="12" id="create-goal-month" name="month" th:value="${month}" placeholder="MONTH">
      </div>
      <div id="input-name">
        <div style="font-size: 18px; font-weight: bold;">이름</div>
        <label for="create-goal-name"></label>
        <input v-model="dailyGoal.name" type="text" id="create-goal-name" name="name" th:value="${updateMonthlyGoal.name}">
      </div>
      <div id="input-content">
        <div style="font-size: 18px; font-weight: bold;">내용</div>
        <label for="create-goal-content"></label>
        <input v-model="dailyGoal.content" type="text" name="content" id="create-goal-content" th:value="${updateMonthlyGoal.content}">
      </div>
      <div id="input-type">
        <div style="font-size: 18px; font-weight: bold;">타입</div>
        <div>
          <div class="goal-types">
            <button type="button" @click="achieveTypeBASIC" class="goal-type-select btn-red" >기본</button>
            <button type="button" @click="achieveTypeCOUNT" class="goal-type-select btn-green">개수</button>
            <button type="button" @click="achieveTypeTIME" class="goal-type-select btn-yellow">시간</button>
          </div>
          <div v-if="this.dailyGoal.achieveType === 'COUNT'">
            <input class="count-input" type="number" v-model="dailyGoal.goalCountQuota"><span>개</span>
          </div>
          <div v-if="this.dailyGoal.achieveType === 'TIME'">
            <input class="count-input" type="number" v-model="dailyGoal.goalTimeQuota"><span>시간</span>
          </div>
        </div>
        <p id="input_out"></p>
      </div>
      <div id="create-buttons">
        <button @click="update" type="submit" class="create-button hvr-fade-create" style="margin-right: 100px;">확인</button>
        <button @click="close" type="button" id="create-monthly-goal-cancel" class="create-button hvr-fade-create">취소</button>
      </div>
    </form>
  </div>

  <div></div>
  </body>
</template>

<script>
export default {
  name: "DailyGoalUpdateModal",
  data() {
    return {
      dailyGoal : {},
      monthlyGoals : {},
      isCreatedMonthlyGoals : false
    }
  },
  mounted() {
    this.init()
  },
  props: ["updateDailyGoal", "createdMonthlyGoals"],
  methods:{
    goalTypeImage(goalType) {return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-" + goalType + ".png"},
    isChecked(goalType) {return this.dailyGoal.goalType === goalType},
    achieveTypeBASIC() {this.dailyGoal.achieveType = "BASIC"},
    achieveTypeCOUNT() {this.dailyGoal.achieveType = "COUNT"},
    achieveTypeTIME() {this.dailyGoal.achieveType = "TIME"},
    openCreatedMonthlyGoals() {
      this.isCreatedMonthlyGoals = !this.isCreatedMonthlyGoals},
    update: function() {
      this.$emit("update", this.dailyGoal)
    },
    close : function() {
      this.$emit("close")
    },
    init() {
      this.monthlyGoals = this.createdMonthlyGoals
      this.dailyGoal = this.updateDailyGoal
    }
  }
}
</script>

<style scoped>
.goal-type-select:hover {
  cursor: pointer;
}

#update-monthly-goal-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  background-color: #F5F5F5;
  height: fit-content;
  width: 650px;
  border-radius: 20px;
  border: solid 4px #325288;
}

#create-monthly-goal-title {
  text-align: center;
  display: inline-block;
  position: relative;
}

#date, #input-name, #input-content, #input-type {
  margin-left: 30px;
  margin-top: 20px;
}

#head {
  text-align: center;
}

#create-goal-year, #create-goal-month, #create-goal-day {
  height: 30px;
  background-color: #F5F5F5;
  border: none;
  border-bottom: solid 2px #DDDDDD;
  width: 32px;
}

input:focus {
  outline: none;
}

#create-goal-month {
  width: 15px;
  text-align: center;
}

#create-goal-year:focus {
  color: #325288;
}

#create-goal-name, #create-goal-content {
  height: 30px;
  background-color: #F5F5F5;
  border: none;
  border-bottom: solid 2px #DDDDDD;
  width: 570px;
}

#create-goal-name:focus {
  border-bottom: solid 2px #325288;
}

#create-goal-content:focus {
  border-bottom: solid 2px #325288;
}

#input_out {
  margin-left: 50px;
}

#create-daily-goal-select option {
  background: #325288;
  color: #FFF8F3;
}

#create-monthly-goal-select option {
  background: #325288;
  color: #FFF8F3;
}

.create-button {
  border-radius: 5px;
  border: solid 0px #292C6D;
  background-color: #292C6D;
  width: 62px;
  height: 32px;
  font-weight: bold;
  color: #FFF8F3;
  margin-bottom: 15px;
}

#create-buttons {
  margin-top: 38px;
  text-align: center;
}

.hvr-fade-create {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  overflow: hidden;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: color, background-color;
  transition-property: color, background-color;
}
.hvr-fade-create:hover {
  border: solid 2px #292C6D;
  background-color: #FFF8F3;
  color: #292C6D;
}

/*==========================================*/
/*월간 목표 생성창 년도, 월 보내기 모달창*/

#monthly-modal  {
  background: #FFF8F3;
  box-shadow: 0 8px 32px 0 rgb(31 38 135 / 37%);
  backdrop-filter: blur( 13.5px );
  -webkit-backdrop-filter: blur( 13.5px );
  border-radius: 10px;
  border: solid 4px #325288;
  width: 220px;
  height: fit-content;
  position: relative;
  top: -100px;
  padding: 10px;
}
#monthly-modal .title {
  padding-left: 10px;
  display: inline;
  color: black;
}
#monthly-modal .title h2 {
  display: inline;
}
#monthly-modal {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px grey;
  color: black;
  font-size: 20px;
  font-weight: bold;
}

#monthly-modal .content {
  margin-top: 20px;
  padding: 0px 10px;
  text-shadow: 1px 1px 2px grey;
  color: black;
}

/*월간 목표 생성 모달창*/
ul li {
  list-style-type: none;
  float: left;
  margin-left: 10px;
}

.goal-type-img {
  width : 20px;
}

.buttons {
  box-sizing: border-box;
  padding: 4px 12px;
  font-size: 12px;
  height: 5px;

}

.goal-type-select {
  position: relative;
  border: 0;
  display: inline-block;
  text-align: center;
  color: white;
  margin-top: 12px;
  margin-right: 15px;
  margin-bottom: 15px;
}

.goal-type-select:active {
  top: 4px;
}

.btn-green {
  background: #82c8a0;
  box-shadow: 0px 4px 0px #87a86f;
}

.btn-green:active {
  box-shadow: 0 0 #87a86f;
  background-color: #87a86f;
}

.btn-yellow {
  background: #f0d264;
  box-shadow:0px 4px 0px #D1B757;
}

.btn-yellow:active {
  box-shadow: 0 0 #ff4c4b;
  background-color: #D6BB59;
}

.btn-red {
  background: #fa5a5a;
  box-shadow:0px 4px 0px #E04342;
}

.btn-red:active {
  box-shadow: 0 0 #ff4c4b;
  background-color: #ff4c4b;
}

.count-input {
  width: 128px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* 일간 목표 생성- 년도, 월, 일 보내기 모달창 */
#daily-modal.modal-overlay {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
  display: none;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.25);
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  backdrop-filter: blur(1.5px);
  -webkit-backdrop-filter: blur(1.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
}
#daily-modal .modal-window {
  background: #FFF8F3;
  box-shadow: 0 8px 32px 0 rgb(31 38 135 / 37%);
  backdrop-filter: blur( 13.5px );
  -webkit-backdrop-filter: blur( 13.5px );
  border-radius: 10px;
  border: solid 4px #325288;
  width: 220px;
  height: fit-content;
  position: relative;
  top: -100px;
  padding: 10px;
}
#daily-modal .title {
  padding-left: 10px;
  display: inline;
  color: black;

}
#daily-modal .title h2 {
  display: inline;
}
#daily-modal .close-area {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px grey;
  color: black;
  font-size: 20px;
  font-weight: bold;
}

#daily-modal .content {
  margin-top: 20px;
  padding: 0px 10px;
  text-shadow: 1px 1px 2px grey;
  color: black;
}

#create-daily-goal-year, #create-daily-goal-month, #create-daily-goal-day {
  width: 194px;
  height: 21px;
  border: none;
  border-bottom: solid 2px #325288;
  background: none;
  margin-bottom: 15px;
}

#create-button {
  border-radius: 5px;
  border: solid 0px #292C6D;
  background-color: #292C6D;
  width: 62px;
  height: 32px;
  font-weight: bold;
  color: #FFF8F3;
}
</style>