<template>
  <router-view />
</template>

<script>
export default {
  name: "Home",
}
</script>

<style>
html {
  height: 90%;
}

body {
  height: 100%;
  background-color: #FFF8F3;
}

/*버튼*/
button {
  cursor: pointer;
}

/*버튼 hover 효과*/
.hvr-fade {
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
.hvr-fade:hover, .hvr-fade:focus, .hvr-fade:active {
  background-color: #516BEB;
  color: white;
}

/*버튼 눌렀을 때 효과*/
@-webkit-keyframes hvr-push {
  50% {
    -webkit-transform: scale(0.8);
    transform: scale(0.8);
  }
  100% {
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}

@keyframes hvr-push {
  50% {
    -webkit-transform: scale(0.8);
    transform: scale(0.8);
  }
  100% {
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}
.hvr-push {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
}
.hvr-push:focus, .hvr-push:active {
  -webkit-animation-name: hvr-push;
  animation-name: hvr-push;
  -webkit-animation-duration: 0.3s;
  animation-duration: 0.3s;
  -webkit-animation-timing-function: linear;
  animation-timing-function: linear;
  -webkit-animation-iteration-count: 1;
  animation-iteration-count: 1;
}

/*예외*/
.field-error {
  border-color: #ffab8a;
  font-style : italic;
  font-size: 14px;
}

.field-error-color {
  color: #FF6666;
}

</style>