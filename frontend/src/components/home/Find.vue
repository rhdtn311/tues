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
      <div v-if="this.findPasswordObj.isWaiting">잠시만 기다려주세요.</div>
      <div v-if="this.findPasswordObj.isFindPassword"> {{this.findPasswordObj.mail}}로 임시 비밀번호가 전송되었습니다. </div>
      <div v-if="this.findPasswordObj.isError" class = "field-error hvr-wobble-top field-error-color">{{this.findPasswordObj.findPasswordErrorMessage}}</div>
    </div>
    <form>
      <label for="loginId"></label>
      <input v-model="this.findPasswordObj.loginId" type="text" id="loginId" name="loginId" placeholder="ID">
      <label for="mail"></label>
      <input v-model="this.findPasswordObj.mail" type="text" id="mail" name="mail" placeholder="EMAIL">
      <button @click="findPassword" type="submit" class="hvr-float submit-button">OK</button>
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
      findPasswordObj: {
        loginId: "",
        mail: "",
        isFindPassword: false,
        isError: false,
        findPasswordErrorMessage : "",
        isWaiting : false
      }
    }
  },
  methods: {
    findId : async function(e) {
      e.preventDefault();
      const res = await axios.get(this.server + "/api/home/find/id",{params: {mail: this.findIdObj.findIdRequest}})
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
    findPassword : async function(e) {
      e.preventDefault();
      this.findPasswordObj.isWaiting = true;
      const res = await axios.get(this.server + "/api/home/find/password", {
        params : {loginId: this.findPasswordObj.loginId, mail : this.findPasswordObj.mail}})
          .then((response) => {
            this.findPasswordObj.isFindPassword = true;
            this.findPasswordObj.isError = false;
            this.findPasswordObj.isWaiting = false;
          }).catch((error) => {
            this.findPasswordObj.isError = true;
            this.findPasswordObj.findPasswordErrorMessage = error.response.data.message;
            this.findPasswordObj.isWaiting = false;
            this.findPasswordObj.isFindPassword = false;
          })
    },
    goBack() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
@import "../../assets/Find-css.css";
</style>