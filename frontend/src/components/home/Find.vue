<template>
  <!DOCTYPE html>
  <html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8">
    <title>아이디/비밀번호 찾기 페이지</title>

    <link th:href="@{/styles/css/find.css}" rel="stylesheet" />
  </head>
  <body>
  <img @click="goBack" id="back-button" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/back-btn.png">
  <div class="container">
    <h1 id="title" style="font-family: 'LeferiPoint-BlackObliqueA'">FIND</h1>

    <div class="subject">아이디 찾기</div>
    <div v-if="this.findIdObj.isFindId">아이디는 『{{this.findIdObj.loginId}}』 입니다.</div>
    <div v-if="this.findIdObj.isError" class = "field-error hvr-wobble-top field-error-color">{{this.findIdObj.findIdErrorMessage}}</div>
    <form style="width: 234px; margin-bottom: 50px">
      <label for="mail"></label>
      <input type="email" v-model="this.findIdObj.findIdRequest" name="mail" placeholder="EMAIL">
      <button @click="findId" type="submit" class="hvr-float submit-button">OK</button>
    </form>
    <div class="subject">비밀번호 찾기</div>
    <div>
<!--      <div v-if="this.isFindId"> {{this.findIdRequest}}로 임시 비밀번호가 전송되었습니다. </div>-->
<!--      <div v-if="this.isError">잠시 후 다시 시도해주세요.</div>-->
    </div>
    <form th:action="@{/member/find/password}" method="get">
      <label for="loginId"></label>
      <input type="text" id="loginId" name="loginId" placeholder="ID">
      <label for="mail"></label>
      <input type="text" id="mail" name="mail" placeholder="EMAIL">
      <button type="submit" class="hvr-float submit-button">OK</button>
    </form>

  </div>
  </body>
  </html>
</template>

<script>
import axios from "axios"
export default {
  name: "Find",
  data() {
    return {
      findIdObj : {
        findIdRequest: "",
        loginId: "",
        isFindId: false,
        isError: false,
        findIdErrorMessage: ""
      },
      findPasswordObj : {

      }
    }
  },
  methods: {
    findId : async function(e) {
      e.preventDefault();
      const res = await axios.get(this.server + "/api/home/find/id",          {params: {mail: this.findIdObj.findIdRequest}})
          .then((response) => {
            this.findIdObj.isFindId = true
            this.findIdObj.loginId = response.data.data;
            this.findIdObj.isError = false;
          }).catch((error) => {
            this.findIdObj.isError = true
            this.findIdObj.findIdErrorMessage = error.response.data.message;
            this.findIdObj.isFindId = false;
          })
      },
    goBack() {
      this.$router.go(-1);
    }
  }
}
</script>

<style>

:root {
  --shadow: #325288;
}

body {
  width: 100%;
  height: 100%;
  background: #FFF8F3;
  margin-top: 157px;
}

#back-button {
  width: 30px;
  height: 30px;
  position: absolute;
  top: 30px;
  left: 30px;
}

#back-button:hover {
  cursor:pointer;
}

.container {
  text-align: center;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.field-error {
  border-color: #ffab8a;
}

#error {
  color: #ffab8a;
}

.field-error-color {
  color: #FF6666;
}

input::placeholder{
  color: #cfcfcf;
}

input:focus {
  outline-color: #548CFF;
}

input {
  width: 220px;
  height: 40px;
  margin-top: 15px;
  border: solid 1px #cfcfcf; border-radius : 5px;
  padding-left: 10px;
}

button {
  width: 100%;
  height: 40px;
  margin-top: 18px;
  border: none; border-radius : 5px;
  color: #ffffff;
  background-color: #325288;
  font-weight: 900;
  font-size: 15px;
}

#title {
  font-size: 55px;
  letter-spacing: 3px;
  margin-bottom: 30px;
  -webkit-text-stroke: 3px #325288;
  color: #FFF8F3;
  text-shadow: 6px 6px var(--shadow),
  5.75px 5.75px  var(--shadow),
  5.5px 5.5px    var(--shadow),
  5.25px 5.25px  var(--shadow),
  5px 5px        var(--shadow),
  4.75px 4.75px  var(--shadow),
  4.5px 4.5px    var(--shadow),
  4.25px 4.25px  var(--shadow),
  4px 4px        var(--shadow),
  3.75px 3.75px  var(--shadow),
  3.5px 3.5px    var(--shadow),
  3.25px 3.25px  var(--shadow),
  3px 3px        var(--shadow),
  2.75px 2.75px  var(--shadow),
  2.5px 2.5px    var(--shadow),
  2.25px 2.25px  var(--shadow),
  2px 2px        var(--shadow),
  1.75px 1.75px  var(--shadow),
  1.5px 1.5px    var(--shadow),
  1.25px 1.25px  var(--shadow),
  1px 1px        var(--shadow),
  0.75px 0.75px  var(--shadow),
  0.5px 0.5px    var(--shadow),
  0.25px 0.25px  var(--shadow);
}

#mail {
  display: grid;
}

.hvr-float {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
  -webkit-transition-timing-function: ease-out;
  transition-timing-function: ease-out;
}

.hvr-float:hover {
  -webkit-transform: translateY(-8px);
  transform: translateY(-8px);
}

.submit-button:hover{
  cursor:pointer;
}

@keyframes hvr-wobble-top {
  16.65% {
    -webkit-transform: skew(-12deg);
    transform: skew(-12deg);
  }
  33.3% {
    -webkit-transform: skew(10deg);
    transform: skew(10deg);
  }
  49.95% {
    -webkit-transform: skew(-6deg);
    transform: skew(-6deg);
  }
  66.6% {
    -webkit-transform: skew(4deg);
    transform: skew(4deg);
  }
  83.25% {
    -webkit-transform: skew(-2deg);
    transform: skew(-2deg);
  }
  100% {
    -webkit-transform: skew(0);
    transform: skew(0);
  }
}

.hvr-wobble-top {
  horiz-align: center;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transform-origin: 0 100%;
  transform-origin: 0 100%;
}

.hvr-wobble-top {
  -webkit-animation-name: hvr-wobble-top;
  animation-name: hvr-wobble-top;
  -webkit-animation-duration: 1s;
  animation-duration: 1s;
  -webkit-animation-timing-function: ease-in-out;
  animation-timing-function: ease-in-out;
  -webkit-animation-iteration-count: 1;
  animation-iteration-count: 1;
}

#mail, #loginId {
  font-family: 'Poppins', sans-serif;
}

.subject {
  font-size: 20px;
  font-family: 'GmarketSansMedium';
}

button {
  font-family: 'SBAggroB';
}

@import url('https://fonts.googleapis.com/css2?family=Contrail+One&family=Gowun+Dodum&family=Poppins:wght@300&display=swap');

@font-face {
  font-family: 'LeferiPoint-BlackObliqueA';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-BlackObliqueA.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'SBAggroB';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2108@1.1/SBAggroB.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}


</style>