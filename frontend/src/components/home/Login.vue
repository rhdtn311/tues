<template>
  <!DOCTYPE html>
  <html>
  <head>
    <meta charset="UTF-8">
    <title>Title</title>
  </head>
  <body>
  <div class="container">
    <div><img id="main-logo" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-logo.png" alt="tues-logo.png"></div>
    <div class="hvr-wobble-top field-error-color" v-if="isError"> {{errorData}} </div>
    <div class="inputs-and-button">
      <form>
        <label for="loginId"></label>
        <input v-model= "requestData.loginId" placeholder="ID" class="login-input" type="text" id="loginId" name="loginId">
        <br>
        <label for="password"></label>
        <input v-model="requestData.password" placeholder="PASSWORD" class="login-input" type="password" id="password" name="password">
        <span>
            <button id="loginButton" type="submit" @click="login">LOGIN</button>
        </span>
      </form>
    </div>

    <div class="bottom-buttons">
      <button class="join-find-button hvr-float" @click="goToJoin"> JOIN </button>
      <button class="join-find-button hvr-float" @click="goToFind"> FIND </button>
    </div>
  </div>
  </body>
  </html>
</template>

<script>
import axios from "axios"
export default {
  data() {
    return {
      isError: false,
      errorData : "",
      requestData : {loginId: "", password: ""},
    }
  },
  methods: {
    login : async function(e) {
      e.preventDefault();
      const res = await axios.post(this.server + "/api/home/login", this.requestData)
          .then((response) => {
            this.goToMain();
          }).catch((error) => {
            this.isError = true;
            this.errorData = error.response.data.message;
            console.log(error)
          })
    },

    goToFind() {
      this.$router.push("/find")
    },

    goToJoin() {
      this.$router.push("/join")
    },

    goToMain() {
      this.$router.replace("/main");
    }
  },

  mounted() {
    isLogin : axios.post(this.server + "/api/validate/member", null, {withCredentials: true})
        .then((response) => {
          this.$router.replace("/main")
        })
  }
}

</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Contrail+One&family=Gowun+Dodum&family=Poppins:wght@300&display=swap');
@font-face {
  font-family: 'SBAggroB';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2108@1.1/SBAggroB.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

html {
  height: 90%;
}

body {
  height: 100%;
  background-color: #FFF8F3;
}

.title {
  font-size: 80px;
  margin-bottom: 10px;
}

.field-error-color {
  color: #FF6666;
}

.container {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.inputs-and-button {
  display: flex;
  align-items: center;
  position: relative;
}

.join-find-button {
  width: 109px;
  height: 40px;
  margin-top: 18px;
  border: none;
  color: #FFF8F3;
  background-color: #325288;
  font-size: 15px;
  font-weight: 900;
}

.bottom-buttons {
  width: fit-content;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-content: space-between;
  width: 234px;
}

.login-input {
  width: 220px;
  height: 40px;
  margin-top: 15px;
  border: solid 1px #cfcfcf; border-radius : 5px;
  padding-left: 10px;
}

input::placeholder{
  color: #cfcfcf;
}

input:focus {
  outline-color: #548CFF;
}

#loginButton {
  margin-left: 17px;
  width: 109px;
  height: 60px;
  margin-top: 18px;
  border: none;
  color: #FFF8F3;
  background-color: #BBCFFF;
  font-size: 15px;
  font-weight: 900;
  position: absolute;
  bottom: 17%;
  border-radius: 20px;
}

button:hover {
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

#loginId, #password {
  font-family: 'Poppins', sans-serif;
}

button {
  font-family: 'SBAggroB';
}
</style>