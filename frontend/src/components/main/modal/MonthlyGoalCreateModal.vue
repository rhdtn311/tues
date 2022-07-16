<template>
  <body>
  <div v-if="isInputDate" id="create-monthly-goal-modal">
    <div id="head">
      <h1 id="create-monthly-goal-title">월간 목표 생성</h1>
    </div>
    <form id="create-monthly-goal-form">
      <div class="buttons">
        <ul style = "margin-left: 18px; padding-bottom: 10px">
          <li><img style="width: 20px" :src="goalTypeImage('A')"><input v-model="this.monthlyGoal.goalType" value="A"  class="goalType" :disabled="isDisabled(0,'A')" type="radio" name="goalType" id="goalTypeA"><label for="goalTypeA"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('B')"><input v-model="this.monthlyGoal.goalType"  value="B" class="goalType" :disabled="isDisabled(1,'B')" type="radio" name="goalType" id="goalTypeB"><label for="goalTypeB"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('C')"><input v-model="this.monthlyGoal.goalType" value="C" class="goalType" :disabled="isDisabled(2,'C')" type="radio" name="goalType" id="goalTypeC"><label for="goalTypeC"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('D')"><input v-model="this.monthlyGoal.goalType" value="D" class="goalType" :disabled="isDisabled(3,'D')" type="radio" name="goalType" id="goalTypeD"><label for="goalTypeD"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('E')"><input v-model="this.monthlyGoal.goalType" value="E" class="goalType" :disabled="isDisabled(4,'E')" type="radio" name="goalType" id="goalTypeE"><label for="goalTypeE"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('F')"><input v-model="this.monthlyGoal.goalType" value="F" class="goalType" :disabled="isDisabled(5,'F')" type="radio" name="goalType" id="goalTypeF"><label for="goalTypeF"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('G')"><input v-model="this.monthlyGoal.goalType" value="G" class="goalType" :disabled="isDisabled(6,'G')" type="radio" name="goalType" id="goalTypeG"><label for="goalTypeG"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('H')"><input v-model="this.monthlyGoal.goalType" value="H" class="goalType" :disabled="isDisabled(7,'H')" type="radio" name="goalType" id="goalTypeH"><label for="goalTypeH"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('I')"><input v-model="this.monthlyGoal.goalType" value="I" class="goalType" :disabled="isDisabled(8,'I')" type="radio" name="goalType" id="goalTypeI"><label for="goalTypeI"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('J')"><input v-model="this.monthlyGoal.goalType" value="J" class="goalType" :disabled="isDisabled(9,'J')" type="radio" name="goalType" id="goalTypeJ"><label for="goalTypeJ"></label></li>
        </ul>
      </div>
      <br>
      <div id="date">
        <div style="font-size: 18px; font-weight: bold;" >날짜</div>
        <label for="create-goal-year"></label>
        <input v-model="this.monthlyGoal.year" type="text" id="create-goal-year" name="year" readonly placeholder="YEAR">
        <label for="create-goal-month"></label>
        <input v-model="this.monthlyGoal.month" type="text" min="1" max="12" id="create-goal-month" name="month" readonly th:value="${month}" placeholder="MONTH">
      </div>
      <div id="input-name">
        <div style="font-size: 18px; font-weight: bold;">이름</div>
        <label for="create-goal-name"></label>
        <input v-model="this.monthlyGoal.name" type="text" name="name" id="create-goal-name">
      </div>
      <div id="input-content">
        <div style="font-size: 18px; font-weight: bold;">내용</div>
        <label for="create-goal-content"></label>
        <input v-model="this.monthlyGoal.content" type="text" name="content" id="create-goal-content">
      </div>
      <div id="input-type">
        <div style="font-size: 18px; font-weight: bold;">타입</div>
        <div>
          <div class="goal-types">
            <button type="button" @click="achieveTypeBASIC" class="goal-type-select btn-red" >기본</button>
            <button type="button" @click="achieveTypeCOUNT" class="goal-type-select btn-green">개수</button>
            <button type="button" @click="achieveTypeTIME" class="goal-type-select btn-yellow">시간</button>
          </div>
          <div v-if="this.monthlyGoal.achieveType === 'COUNT'">
            <input class="count-input" type="number" v-model="monthlyGoal.goalCountQuota"><span>개</span>
          </div>
          <div v-if="this.monthlyGoal.achieveType === 'TIME'">
            <input class="count-input" type="number" v-model="monthlyGoal.goalTimeQuota"><span>시간</span>
          </div>
        </div>
        <p id="input_out"></p>
      </div>
      <div id="create-buttons">
        <button @click="createMonthlyGoal" type="submit" class="create-button hvr-fade-create" style="margin-right: 100px;">확인</button>
        <button type="button" id="create-monthly-goal-cancel" class="create-button hvr-fade-create">취소</button>
      </div>
    </form>
  </div>
  </body>

  <div v-if="isDateModal" id="date-modal">
    <form>
      <input type="number" v-model="this.monthlyGoal.year">
      <input type="number" v-model="this.monthlyGoal.month">
      <button @click="getCreatedMonthlyGoals">확인</button>
    </form>
  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "MonthlyGoalCreateModal",
  data() {
    return {
      date: {
        year: new Date().getFullYear(),
        month: new Date().getMonth() + 1
      },
      createdMonthlyGoals: [],
      isInputDate: false,
      isDateModal: true,
      monthlyGoal : {
        name:"",
        content:"",
        goalType:"",
        achieveType:"BASIC",
        goalCountQuota:"",
        goalCount:"",
        goalTimeQuota:"",
        goalTime:"",
        year: new Date().getFullYear(),
        month: new Date().getMonth() + 1
      }
    }
  },
  props: ["createdGoalTypes"],
  methods: {
    getCreatedMonthlyGoals : function(e) {
      e.preventDefault();
      axios.get(this.server + "/api/main/monthly/view/" + this.monthlyGoal.year + "/" + this.monthlyGoal.month)
          .then((response) => {
            this.isInputDate = true;
            this.isDateModal = false;
            this.createdMonthlyGoals=response.data.data;
          })
    },
    isDisabled: function(index, goalType) {
      return this.createdMonthlyGoals[index] === goalType
    },
    goalTypeImage: function (goalType) {
      return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-"+ goalType + ".png"
    },
    achieveTypeBASIC() {this.monthlyGoal.achieveType = "BASIC"},
    achieveTypeCOUNT() {this.monthlyGoal.achieveType = "COUNT"},
    achieveTypeTIME() {this.monthlyGoal.achieveType = "TIME"},
    createMonthlyGoal() {
      this.$emit("create", this.monthlyGoal)
    }
  },
}
</script>

<style scoped>
.goal-type-select:hover {
  cursor: pointer;
}

#create-monthly-goal-modal {
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

</style>