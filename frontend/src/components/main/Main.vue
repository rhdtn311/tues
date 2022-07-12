<template>
  <!DOCTYPE html>
  <html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8">
    <title>Title</title>
  </head>
  <body>
  <div>
    <div id="monthly-goal-container">
      <div class="image-and-title">
        <img id="goal-main-title-img" v-bind:src="monthLogoImage" alt="goal_main_image" style="position: relative; bottom: 6px;">
        <span id="monthly-goal-title" class="main-title">{{this.date.month}}월의 목표</span>
      </div>
      <div class="main-top-option">
        <div>
          <img id="goal-list-button" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/goal_list.jpg" th:onclick="|location.href='@{/goal/list}/?year=${year}&month=${month}'|" alt="logout">
        </div>
        <div>
          <img id="logout-button" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/logout-btn.png" @click="logout" alt="goal_list" >
        </div>
      </div>
      <div id="make-goal-button">
        <button id="btn-monthly-modal" class="hvr-back-pulse">월간 목표 생성 ＋</button>
        <button id="btn-daily-modal" class="hvr-back-pulse"> 일일 목표 생성 ＋</button>
      </div>
    </div>
    <div id="monthly-goals-ajax">
      <ul id="monthly-goals" v-for="monthlyGoal in monthlyGoals">
        <div id="one-monthly-goal">
          <img class="goal-checkbox-img" v-bind:src="[monthlyGoal.success == true ? successGoalImage : failGoalImage]">
          <span class="monthly-goal-name" @click="detailMonthlyGoal">{{monthlyGoal.name}}</span>
          <img class="goal-type-img" v-bind:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + monthlyGoal.goalType + '.png'">
            <div style="display: inline-block" v-if="monthlyGoal.achieveType === 'COUNT'">
              <div class="monthly-count" th:attr="class='monthly-goal-' + ${monthlyGoal.id}">
                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="monthlyGoalFail([[${monthlyGoal.id}]],0, [[${monthlyGoal.achieveType}]], [[${monthlyGoal.goalType}]], [[${monthlyGoal.goalCount}]], [[${monthlyGoal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                    <span class="goal-quota" th:text="|${monthlyGoal.goalCount} / ${monthlyGoal.goalCountQuota}|"> {{monthlyGoal.goalCount}} / {{monthlyGoal.goalCountQuota}} </span>
                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="monthlyGoalSuccess([[${monthlyGoal.id}]], 0, [[${monthlyGoal.achieveType}]], [[${monthlyGoal.goalType}]], [[${monthlyGoal.goalCount}]], [[${monthlyGoal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                </div>
            </div>
            <div style="display:inline-block;" v-if="monthlyGoal.achieveType == 'TIME'">
                <div class="monthly-time" th:attr="class='monthly-goal-' + ${monthlyGoal.id}">
                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="monthlyGoalFail([[${monthlyGoal.id}]],0, [[${monthlyGoal.achieveType}]], [[${monthlyGoal.goalType}]], [[${monthlyGoal.goalTime}]], [[${monthlyGoal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                    <span class="goal-quota">{{monthlyGoal.goalTime}} / {{monthlyGoal.goalTimeQuota}}</span>
                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="monthlyGoalSuccess([[${monthlyGoal.id}]], 0, [[${monthlyGoal.achieveType}]], [[${monthlyGoal.goalType}]], [[${monthlyGoal.goalTime}]], [[${monthlyGoal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                </div>
            </div>
            <div style="display:inline-block;" v-if="monthlyGoal.achieveType == 'BASIC'">
                <div class="monthly-basic" th:attr="class='monthly-goal-' + ${monthlyGoal.id}">
                    <button v-if="monthlyGoal.success == false"
                            class="goal-check-button hvr-fade hvr-push"
                            th:onclick="monthlyGoalSuccess([[${monthlyGoal.id}]], 0, [[${monthlyGoal.achieveType}]], [[${monthlyGoal.goalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                    <button v-if="monthlyGoal.success == true"
                            class="goal-check-button hvr-fade hvr-push"
                            th:onclick="monthlyGoalFail([[${monthlyGoal.id}]],0, [[${monthlyGoal.achieveType}]], [[${monthlyGoal.goalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                </div>
            </div>
<!--        </span>-->
        </div>
      </ul>
    </div>
  </div>



  <div id="week-goals">
    <div style="display: flex; justify-content: space-between">
      <div id="daily-goal-title" style="margin-left: 20px" class="main-title">주간 목표</div>
    </div>

    <div id="daily-goals-container">
      <div id="monday">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'MONDAY')} ? 'today' : ''">MON</div>
        <div class="day-content">
          <div th:if="${dailyGoals.get('MON')}" th:each="goal : ${dailyGoals.get('MON')}">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}" th:src="${goal.success} ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png'" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:text="${goal.name}" th:onclick="detailDailyGoal([[${goal.id}]])"></span>
                    <img class="goal-type-img" th:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + ${goal.getGoalType()} + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                        <span th:switch="${goal.achieveType}">
                            <span th:case="${T(kong.tues.goal.AchieveType).COUNT}">
                                <div class="daily-count-mon" th:attr="class=${goal.id}">
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'mon', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalCount} / ${goal.goalCountQuota}|"></span>
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'mon', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).TIME}">
                                <div class="daily-time-mon" th:attr="class=${goal.id}">
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'mon', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalTime} / ${goal.goalTimeQuota}|"></span>
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'mon', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).BASIC}">
                                <div class="daily-basic-mon" th:attr="class=${goal.id}">
                                    <button th:if="${goal.success == true}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'mon', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                    <button th:if="${goal.success == false}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'mon', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                </div>
                            </span>
                        </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="tuesday">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'TUESDAY')} ? 'today' : ''">TUE</div>
        <div class="day-content">
          <div th:if="${dailyGoals.get('TUE')}" th:each="goal : ${dailyGoals.get('TUE')}">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}" th:src="${goal.success} ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png'" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:text="${goal.name}" th:onclick="detailDailyGoal([[${goal.id}]])"></span>
                    <img class="goal-type-img" th:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + ${goal.getGoalType()} + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                        <span th:switch="${goal.achieveType}">
                            <span th:case="${T(kong.tues.goal.AchieveType).COUNT}">
                                <div class="daily-count-tue" th:attr="class=${goal.id}">
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'tue', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalCount} / ${goal.goalCountQuota}|"></span>
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'tue', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).TIME}">
                                <div class="daily-time-tue" th:attr="class=${goal.id}">
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'tue', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalTime} / ${goal.goalTimeQuota}|"></span>
                                    <button  class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'tue', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).BASIC}">
                                <div class="daily-basic-tue" th:attr="class=${goal.id}">
                                    <button th:if="${goal.success == false}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'tue', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                    <button th:if="${goal.success == true}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'tue', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                </div>
                            </span>
                        </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="wednesday">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'WEDNESDAY')} ? 'today' : ''">WED</div>
        <div class="day-content">
          <div th:if="${dailyGoals.get('WED')}" th:each="goal : ${dailyGoals.get('WED')}">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}" th:src="${goal.success} ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png'" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:text="${goal.name}" th:onclick="detailDailyGoal([[${goal.id}]])"></span>
                    <img class="goal-type-img" th:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + ${goal.getGoalType()} + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                        <span th:switch="${goal.achieveType}">
                            <span th:case="${T(kong.tues.goal.AchieveType).COUNT}">
                                <div class="daily-count-wed" th:attr="class=${goal.id}">
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'wed', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalCount} / ${goal.goalCountQuota}|"></span>
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'wed', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).TIME}">
                                <div class="daily-time-wed" th:attr="class=${goal.id}">
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'wed', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalTime} / ${goal.goalTimeQuota}|"></span>
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'wed', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).BASIC}">
                                <div class="daily-basic-wed" th:attr="class=${goal.id}">
                                    <button th:if="${goal.success == false}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'wed', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                    <button th:if="${goal.success == true}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'wed', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                </div>
                            </span>
                        </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="thursday">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'THURSDAY')} ? 'today' : ''">THU</div>
        <div class="day-content">
          <div th:if="${dailyGoals.get('THU')}" th:each="goal : ${dailyGoals.get('THU')}">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}" th:src="${goal.success} ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png'" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:text="${goal.name}" th:onclick="detailDailyGoal([[${goal.id}]])"></span>
                    <img class="goal-type-img" th:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + ${goal.getGoalType()} + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                        <span th:switch="${goal.achieveType}">
                            <span th:case="${T(kong.tues.goal.AchieveType).COUNT}">
                                <div class="daily-count-thu" th:attr="class=${goal.id}">
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'thu', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalCount} / ${goal.goalCountQuota}|"></span>
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'thu', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).TIME}">
                                <div class="daily-time-thu" th:attr="class=${goal.id}">
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'thu', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalTime} / ${goal.goalTimeQuota}|"></span>
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'thu', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).BASIC}">
                                <div class="daily-basic-thu" th:attr="class=${goal.id}">
                                    <button th:if="${goal.success == false}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'thu', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                    <button th:if="${goal.success == true}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'thu', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                </div>
                            </span>
                        </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="friday">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'FRIDAY')} ? 'today' : ''">FRI</div>
        <div class="day-content">
          <div th:if="${dailyGoals.get('FRI')}" th:each="goal : ${dailyGoals.get('FRI')}">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}" th:src="${goal.success} ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png'" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:text="${goal.name}" th:onclick="detailDailyGoal([[${goal.id}]])"></span>
                    <img class="goal-type-img" th:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + ${goal.getGoalType()} + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                        <span th:switch="${goal.achieveType}">
                            <span th:case="${T(kong.tues.goal.AchieveType).COUNT}">
                                <div class="daily-count-fri" th:attr="class=${goal.id}">
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'fri', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalCount} / ${goal.goalCountQuota}|"></span>
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'fri', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).TIME}">
                                <div class="daily-time-fri" th:attr="class=${goal.id}">
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'fri', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalTime} / ${goal.goalTimeQuota}|"></span>
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'fri', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).BASIC}">
                                <div class="daily-basic-fri" th:attr="class=${goal.id}">
                                    <button th:if="${goal.success == false}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'fri', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                    <button th:if="${goal.success == true}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'fri', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                </div>
                            </span>
                        </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="saturday">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'SATURDAY')} ? 'today' : ''">SAT</div>
        <div class="day-content">
          <div th:if="${dailyGoals.get('SAT')}" th:each="goal : ${dailyGoals.get('SAT')}">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}" th:src="${goal.success} ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png'" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:text="${goal.name}" th:onclick="detailDailyGoal([[${goal.id}]])"></span>
                    <img class="goal-type-img" th:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + ${goal.getGoalType()} + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                        <span th:switch="${goal.achieveType}">
                            <span th:case="${T(kong.tues.goal.AchieveType).COUNT}">
                                <div class="daily-count-sat" th:attr="class=${goal.id}">
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'sat', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalCount} / ${goal.goalCountQuota}|"></span>
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'sat', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).TIME}">
                                <div class="daily-time-sat" th:attr="class=${goal.id}">
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'sat', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalTime} / ${goal.goalTimeQuota}|"></span>
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'sat', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).BASIC}">
                                <div class="daily-basic-sat" th:attr="class=${goal.id}">
                                    <button th:if="${goal.success == false}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'sat', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                    <button th:if="${goal.success == true}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'sat', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                </div>
                            </span>
                        </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="sunday">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'SUNDAY')} ? 'today' : ''">SUN</div>
        <div class="day-content">
          <div th:if="${dailyGoals.get('SUN')}" th:each="goal : ${dailyGoals.get('SUN')}">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}" th:src="${goal.success} ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png'" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:text="${goal.name}" th:onclick="detailDailyGoal([[${goal.id}]])"></span>
                    <img class="goal-type-img" th:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + ${goal.getGoalType()} + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                        <span th:switch="${goal.achieveType}">
                            <span th:case="${T(kong.tues.goal.AchieveType).COUNT}">
                                <div class="daily-count-sun" th:attr="class=${goal.id}">
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'sun', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalCount} / ${goal.goalCountQuota}|"></span>
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'sun', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalCount}]], [[${goal.goalCountQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).TIME}">
                                <div class="daily-time-sun" th:attr="class=${goal.id}">
                                    <button class="goal-plus-button hvr-fade hvr-push" th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'sun', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                                    <span class="goal-quota" th:text="|${goal.goalTime} / ${goal.goalTimeQuota}|"></span>
                                    <button class="goal-minus-button hvr-fade hvr-push" th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'sun', [[${monthlyGoal.id}]], [[${goal.getGoalType}]], [[${goal.goalTime}]], [[${goal.goalTimeQuota}]])"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                                </div>
                            </span>
                            <span th:case="${T(kong.tues.goal.AchieveType).BASIC}">
                                <div class="daily-basic-sun" th:attr="class=${goal.id}">
                                    <button th:if="${goal.success == false}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalSuccess([[${goal.id}]], [[${goal.achieveType}]], 'sun', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                    <button th:if="${goal.success == true}"
                                            class="goal-check-button hvr-fade hvr-push"
                                            th:onclick="dailyGoalFail([[${goal.id}]], [[${goal.achieveType}]], 'sun', [[${monthlyGoal.id}]], [[${goal.getGoalType}]])"><span class="goal-check-text hvr-fade hvr-push">check</span></button>
                                </div>
                            </span>
                        </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="monthly-modal" class="modal-overlay">
      <div class="modal-window">
        <div class="title">
          <h2>DATE</h2>
        </div>
        <div class="close-area">ⓧ</div>
        <div class="content">
          <div>YEAR</div>
          <label for="year"></label>
          <input style="width: 194px" type="number" th:min="${year}" th:value="${year}" max="3000"
                 id="create-monthly-goal-year" name="year"
                 placeholder="년">
          <div>MONTH</div>
          <label for="month"></label>
          <input style="width: 194px" type="number" min="1" max="12" th:value="${month}" id="create-monthly-goal-month"
                 name="month" placeholder="월">
          <br>
          <div style="padding-top: 15px; text-align: center;">
            <button id="create-button" class="hvr-fade-create" onclick="createMonthlyGoal()">생성</button>
          </div>
        </div>
      </div>
    </div>

    <div id="daily-modal" class="modal-overlay">
      <div class="modal-window">
        <div class="title">
          <h2>DATE</h2>
        </div>
        <div class="close-area">ⓧ</div>
        <div class="content">
          <div>YEAR</div>
          <label for="year"></label>
          <input style="width: 194px" type="number" th:min="${year}" th:value="${year}" max="3000" id="create-daily-goal-year"
                 name="year"
                 placeholder="년">
          <div>MONTH</div>
          <label for="month"></label>
          <input style="width: 194px" type="number" min="1" max="12" th:value="${month}" id="create-daily-goal-month"
                 name="month" placeholder="월">
          <div>DAY</div>
          <label for="day"></label>
          <input style="width: 194px" type="number" min="1" max="31" th:value="${day}" id="create-daily-goal-day" name="day"
                 placeholder="일">
          <br>
          <div style="padding-top: 15px; text-align: center;">
            <button th:onclick="createDailyGoal()" id="create-button">생성</button>
          </div>
        </div>
      </div>
      <div id="hidden" hidden></div>
      <div id="error" hidden></div>
      <div id="monthly-goal-success" hidden></div>
    </div>

    <!--    월간 목표 생성 모달-->
    <div id="create-monthly-goal-modal" class="content"></div>

    <!--    일간 목표 생성 모달-->
    <div id="create-daily-goal-modal" class="content"></div>

    <!--    월간 목표 수정 모달-->
    <div id="update-monthly-goal-modal"> </div>

    <!--    일간 목표 수정 모달-->
    <div id="update-daily-goal-modal"> </div>


    <!--    월간 목표 세부-->
    <div id="monthly-goal-detail"></div>

    <!--    일간 목표 세부-->
    <div id="daily-goal-detail"></div>

  </div>

  </body>


  </html>
</template>

<script>
import axios from "axios";
export default {
  name: "Main",

  data() {
    return {
      date : {
        year : "",
        month : "",
        day : "",
        dayOfWeek : ""
      },
      monthlyGoals: [
      ],
      dailyGoals: [],
    }
  },

  computed: {
    monthLogoImage() {return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/month-" + this.date.month + ".png"},
    successGoalImage() {return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png"},
    failGoalImage() {return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png"},
    goalTypeImage(goalType) {
      return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-" + goalType + ".png"
    }
  },

  async mounted() {
      await this.isLogin();
      this.init()
  },

  methods: {
    init: function () {
      axios.get(this.server + "/api/main")
          .then((response) => {
            console.log("response = " + JSON.stringify(response, null, 2));

            // 날짜 초기화
            this.date.year = response.data.data.dateResponse.year;
            this.date.month = response.data.data.dateResponse.month;
            this.date.day = response.data.data.dateResponse.day;
            this.date.dayOfWeek = response.data.data.dateResponse.dayOfWeek;

            // monthlyGoals 초기화
            for (let monthlyGoal of response.data.data.monthlyGoals) {
              this.monthlyGoals.push(monthlyGoal);
            }

          }).catch((error) => {
        console.log("error = " + error);
      })
    },
    logout: function () {
      const res = axios.get(this.server + "/api/home/logout")
          .then((response) => {
            this.$router.replace("/login")
          })
    },
    isLogin: function () {
      axios.post(this.server + "/api/validate/member", null)
          .then((response) => {
            console.log(response)
          }).catch((error) => {
        this.$router.push("/login")
      })
    },
  }
}
</script>

<style scoped>
@font-face {
  font-family: 'MinSans-Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/MinSans-Bold.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'TmonMonsori';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/TmonMonsori.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'Pretendard-Regular';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
  font-weight: 400;
  font-style: normal;
}


/* 제목 */
.main-title {
  font-family: 'TmonMonsori';
}

/* MON ~ SUN*/
.day-title {

}

body {
  font-family: 'MinSans-Bold';
}

button {
  font-family: 'Pretendard-Regular';
}

.main-top-option {
  display: flex;
}

/*일간 목표 레이아웃*/
#daily-goals-container {
  display: flex;
  justify-content: space-between;
  border: #475762 solid 2px;
  border-top-left-radius: 1rem;
  border-top-right-radius: 1rem;
  border-bottom-left-radius: 1rem;
  border-bottom-right-radius: 1rem;
  margin-top: 5px;
  height: 530px;
}

#daily-goal-title {
  margin-top: 20px;
  margin-left: 20px;
  font-style: italic;
  font-weight: bold;
  font-size: 30px;
}

#btn-daily-modal {
  height: fit-content;
  width: fit-content;
  background: #FFF8F3;
  border: solid 3px #161853;
  margin-left: 20px;
  border-radius: 15px;
  font-size: 13px;
  font-weight: bolder;
  font-style: italic;
}

#one-daily-goal {
  display: flex;
  flex-direction: row;
  align-items: center;
}

#monday {
  border-right: #475762 solid 2px;
  border-top-left-radius: 1rem;
  border-bottom-left-radius: 1rem;
  width: 14%;
}
#tuesday {
  border-right:#475762 solid 2px;
  width: 14%;
}
#wednesday {
  border-right: #475762 solid 2px;
  width: 14%;
}
#thursday {
  border-right: #475762 solid 2px;
  width: 14%;
}
#friday {
  border-right:#475762 solid 2px;
  width: 14%;
}
#saturday {
  border-right: #475762 solid 2px;
  width: 14%;
}
#sunday {
  width: 14%;
}

.day-title {
  text-align: center;
  margin-top: 5px;
  height: 30px;
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: bold;
  font-style: italic;
}

.today {
  color: #6B76FF;
}

.day-content {
  margin-left: 10px;
  overflow: auto;
  height: 470px;
}

.goal-plus-button, .goal-minus-button {
  border: solid 1px #313552;
  background-color: #FAEEE7;
  border-radius: 6px;
  width: 20px;
}

.goal-button-text {
  color: #142F43;
  position: relative;
  right: 3px;
}

.goal-check-button {
  border: solid 1px #313552;
  background-color: #FAEEE7;
  border-radius: 6px;
}

.goal-check-text {
  font-style: italic;
  font-weight: bold;
  color: #313552;
}

.daily-goal-without-success-box-image {
  display: flex;
  flex-direction: column;
}

.daily-goal-count-and-button {
  margin-left: 25px;
}

.daily-goal-check {
  margin-right: 5px;
  position: relative;
  top: 5px;
  width : 17px;
  height : 17px;
}

.image-and-name-goaltype {
  display: flex;
  align-items: start;
}

.one-daily-goal {
  margin-bottom: 15px;
}

/*일간 목표 hover*/
.daily-goal-name:hover {
  cursor:pointer;
  border-radius: 10px;
  background-color: #F8A488;
}


#monthly-goals-ajax {
  height: 230px;
  overflow: auto;
}

#monthly-goal-title {
  font-size: 40px;
  font-weight: bold;
  margin-bottom: 5px;
}

#goal-main-title-img {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

#btn-monthly-modal {
  height: fit-content;
  width: fit-content;
  background: #FFF8F3;
  border: solid 3px #161853;
  margin-left: 20px;
  border-radius: 15px;
  font-size: 13px;
  font-weight: bolder;
  font-style: italic;
}

@-webkit-keyframes hvr-back-pulse {
  50% {
    background-color: #8158FC;
  }
}
@keyframes hvr-back-pulse {
  50% {
    background-color: #8158FC;
  }
}

.hvr-back-pulse {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  overflow: hidden;
  -webkit-transition-duration: 0.5s;
  transition-duration: 0.5s;
  -webkit-transition-property: color, background-color;
  transition-property: color, background-color;
}
.hvr-back-pulse:hover {
  -webkit-animation-name: hvr-back-pulse;
  animation-name: hvr-back-pulse;
  -webkit-animation-duration: 1s;
  animation-duration: 1s;
  -webkit-animation-delay: 0s;
  animation-delay: 0s;
  -webkit-animation-timing-function: linear;
  animation-timing-function: linear;
  -webkit-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
  background-color: #FF414D;
  color: #000000;
}

#monthly-goal-container {
  display: flex;
  flex-direction: row;
  align-items: center;
}

#monthly-goals {
  margin-bottom: -10px;
}

/*이미지 사진 크기*/
.goal-type-img {
  width: 15px;
  height: 20px;
  margin-left: 4px;
  margin-right: 15px;
}

.monthly-goal-name {
  font-size: 16px;
  margin-left:5px;
}

.goal-check {
  width : 17px;
  height : 17px;
  display: inline-block;
}

#one-monthly-goal {
  display: flex;
  flex-direction: row;
  align-items: center;
}

#make-goal-button {
  margin-top: 30px;
  margin-left: 45px;
  position: relative;
  left: 70%;
}

/*  월간 목표 hover*/
.monthly-goal-name:hover{
  cursor:pointer;
  border-radius: 10px;
  background-color: #F8A488;
}

.image-and-title {
  display: flex;
  flex-direction: row;
  justify-items: center;
}

.goal-quota {
  position: relative;
  top: 3px;
}

.main-top-option {
  display: flex;
  position: absolute;
  right: 45px;
  top: 23px;
}

#logout-button, #goal-list-button {
  width: 30px;
  height: 30px;
  margin-left: 10px;
}

#logout-button:hover {
  cursor:pointer;
}

#goal-list-button:hover {
  cursor:pointer;
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

.goal-checkbox-img {
  width: 15px;
}

html {
  height: 90%;
}
body {
  background-color: #FFF8F3;
  height: 100%;
  margin: 40px;
}

/*스크롤바*/
/* width */
::-webkit-scrollbar {
  width: 5px;
}
/* Handle */
::-webkit-scrollbar-thumb {
  background: #CDDEFF;
  border-radius: 10px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #2C2891;
}
</style>