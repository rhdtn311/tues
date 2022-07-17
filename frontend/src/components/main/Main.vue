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
        <button @click="openMonthlyGoalCreate" id="btn-monthly-modal" class="hvr-back-pulse">월간 목표 생성 ＋</button>
        <button @click="openDailyGoalCreate" id="btn-daily-modal" class="hvr-back-pulse"> 일일 목표 생성 ＋</button>
      </div>
    </div>
    <div id="monthly-goals-ajax">
      <ul>
        <li id="monthly-goals" v-for="monthlyGoal in monthlyGoals">
          <div id="one-monthly-goal">
            <img class="goal-checkbox-img" v-bind:src="[monthlyGoal.success == true ? successGoalImage : failGoalImage]">
            <span class="monthly-goal-name" @click="monthlyGoalDetail(monthlyGoal.id)">{{monthlyGoal.name}}</span>
            <img class="goal-type-img" v-bind:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + monthlyGoal.goalType + '.png'">
              <div style="display: inline-block" v-if="monthlyGoal.achieveType === 'COUNT'">
                <div class="monthly-count" th:attr="class='monthly-goal-' + ${monthlyGoal.id}">
                      <button @click="monthlyGoalMinus(monthlyGoal.id)" class="goal-plus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                      <span class="goal-quota"> {{monthlyGoal.goalCount}} / {{monthlyGoal.goalCountQuota}} </span>
                      <button @click="monthlyGoalPlus(monthlyGoal.id)" class="goal-minus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                  </div>
              </div>
              <div style="display:inline-block;" v-if="monthlyGoal.achieveType == 'TIME'">
                  <div class="monthly-time" th:attr="class='monthly-goal-' + ${monthlyGoal.id}">
                    <button @click="monthlyGoalMinus(monthlyGoal.id)" class="goal-plus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                      <span class="goal-quota">{{monthlyGoal.goalTime}} / {{monthlyGoal.goalTimeQuota}}</span>
                    <button @click="monthlyGoalPlus(monthlyGoal.id)" class="goal-minus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                  </div>
              </div>
              <div style="display:inline-block;" v-if="monthlyGoal.achieveType == 'BASIC'">
                  <div class="monthly-basic" th:attr="class='monthly-goal-' + ${monthlyGoal.id}">
                      <button v-if="monthlyGoal.success == false"
                              @click="monthlyGoalMinus(monthlyGoal.id)"
                              class="goal-check-button hvr-fade hvr-push">
                        <span class="goal-check-text hvr-fade hvr-push">check</span></button>
                      <button v-if="monthlyGoal.success == true"
                              @click="monthlyGoalMinus(monthlyGoal.id)"
                              class="goal-check-button hvr-fade hvr-push">
                        <span class="goal-check-text hvr-fade hvr-push">check</span></button>
                  </div>
              </div>
          </div>
        </li>
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
          <div v-if="this.dailyGoals.MON != []" v-for="dailyGoal in this.dailyGoals.MON">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" v-bind:src="[dailyGoal.success === true ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png']" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span @click="dailyGoalDetail(dailyGoal.id)" class="daily-goal-name">{{dailyGoal.name}}</span>
                    <img class="goal-type-img" v-bind:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + dailyGoal.goalType + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                    <span v-if="dailyGoal.achieveType === 'COUNT'">
                        <div class="daily-count-mon" th:attr="class=${goal.id}">
                            <button @click="dailyGoalMinus(dailyGoal.id)" class="goal-minus-button hvr-fade hvr-push" ><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                            <span class="goal-quota">{{ dailyGoal.goalCount }} / {{ dailyGoal.goalCountQuota }}</span>
                            <button @click="dailyGoalPlus(dailyGoal.id)" class="goal-plus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                        </div>
                    </span>
                    <span v-if="dailyGoal.achieveType === 'TIME'">
                        <div class="daily-time-mon" th:attr="class=${goal.id}">
                            <button @click="dailyGoalMinus(dailyGoal.id)" class="goal-plus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                            <span class="goal-quota">{{ dailyGoal.goalTime }} / {{ dailyGoal.goalTimeQuota }}</span>
                            <button @click="dailyGoalPlus(dailyGoal.id)" class="goal-minus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                        </div>
                    </span>
                    <span v-if="dailyGoal.achieveType === 'BASIC'">
                        <div class="daily-basic-mon" th:attr="class=${goal.id}">
                            <button v-if="dailyGoal.success === true"
                                    @click="dailyGoalMinus(dailyGoal.id)"
                                    class="goal-check-button hvr-fade hvr-push">
                              <span class="goal-check-text hvr-fade hvr-push">check</span></button>
                            <button v-if="dailyGoal.success === false"
                                    @click="dailyGoalPlus(dailyGoal.id)"
                                    class="goal-check-button hvr-fade hvr-push">
                              <span class="goal-check-text hvr-fade hvr-push">check</span></button>
                        </div>
                    </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="tues">
        <div class="day-title" th:classappend="${#strings.equals(dayOfWeek,'TUESDAY')} ? 'today' : ''">TUE</div>
        <div class="day-content">
          <div v-if="this.dailyGoals.TUE != []" v-for="dailyGoal in this.dailyGoals.TUE">
            <div id="one-daily-goal" class="one-daily-goal">
              <div class="daily-goal-without-success-box-image">
                <div class="image-and-name-goaltype">
                  <div class="daily-goal-check-image">
                    <img style="display: inline-block" th:attr="id='check-'+${goal.id}"  v-bind:src="[dailyGoal.success === true ? 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-success.png' : 'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/check-fail.png']" class="daily-goal-check">
                  </div>
                  <div class="daily-goal-name-and-goalType">
                    <span class="daily-goal-name" th:onclick="detailDailyGoal([[${goal.id}]])">{{dailyGoal.name}}</span>
                    <img class="goal-type-img" v-bind:src="'https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-' + dailyGoal.goalType + '.png'">
                  </div>
                </div>
                <div class="daily-goal-count-and-button">
                    <span v-if="dailyGoal.achieveType === 'COUNT'">
                        <div class="daily-count-mon" th:attr="class=${goal.id}">
                            <button @click="dailyGoalMinus(dailyGoal.id)" class="goal-minus-button hvr-fade hvr-push" ><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                            <span class="goal-quota">{{ dailyGoal.goalCount }} / {{ dailyGoal.goalCountQuota }}</span>
                            <button @click="dailyGoalPlus(dailyGoal.id)" class="goal-plus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                        </div>
                    </span>
                  <span v-if="dailyGoal.achieveType === 'TIME'">
                        <div class="daily-time-mon" th:attr="class=${goal.id}">
                            <button @click="dailyGoalMinus(dailyGoal.id)" class="goal-plus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▼</span></button>
                            <span class="goal-quota">{{ dailyGoal.goalTime }} / {{ dailyGoal.goalTimeQuota }}</span>
                            <button @click="dailyGoalPlus(dailyGoal.id)" class="goal-minus-button hvr-fade hvr-push"><span class="goal-button-text hvr-fade hvr-push">▲</span></button>
                        </div>
                    </span>
                  <span v-if="dailyGoal.achieveType === 'BASIC'">
                        <div class="daily-basic-mon" th:attr="class=${goal.id}">
                            <button v-if="dailyGoal.success === true"
                                    @click="dailyGoalMinus(dailyGoal.id)"
                                    class="goal-check-button hvr-fade hvr-push">
                              <span class="goal-check-text hvr-fade hvr-push">check</span></button>
                            <button v-if="dailyGoal.success === false"
                                    @click="dailyGoalPlus(dailyGoal.id)"
                                    class="goal-check-button hvr-fade hvr-push">
                              <span class="goal-check-text hvr-fade hvr-push">check</span></button>
                        </div>
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

    <!-- 컴포넌트 MyModal -->
    <MonthlyGoalDetailModal @close="closeMonthlyGoalDetailModal"
                            @delete="deleteMonthlyGoal"
                            @update="modifyMonthlyGoalView"
                            v-if="this.isMonthlyGoalDetailModal"
                            v-bind:detailGoal="detailMonthlyGoal">
    </MonthlyGoalDetailModal>
    <MonthlyGoalUpdateModal v-if="this.isMonthlyGoalUpdateModal"
                            @update="modifyMonthlyGoal"
                            @close="closeMonthlyGoalUpdateModal"
                            v-bind:createdGoalTypes="createdGoalTypes"
                            v-bind:updateMonthlyGoal="updateMonthlyGoal">
    </MonthlyGoalUpdateModal>
    <DailyGoalDetailModal v-if="this.isDailyGoalDetailModal"
                          @update="modifyDailyGoalView"
                          @delete="deleteDailyGoal"
                          @close="closeDailyGoalDetailModal"
                          v-bind:detailGoal="detailDailyGoal"
    >
    </DailyGoalDetailModal>
    <DailyGoalUpdateModal v-if="this.isDailyGoalUpdateModal"
                          @update="modifyDailyGoal"
                          @close="closeDailyGoalUpdateModal"
                          v-bind:updateDailyGoal="updateDailyGoal"
                          v-bind:createdMonthlyGoals="createdMonthlyGoals">
    </DailyGoalUpdateModal>
    <MonthlyGoalCreateModal v-if="this.isMonthlyGoalCreateModal"
                            @create="createMonthlyGoal"
                            @close="closeMonthlyGoalCreateModal"
    >
    </MonthlyGoalCreateModal>
    <DailyGoalCreateModal v-if="this.isDailyGoalCreateModal"
                          @create="createDailyGoal"
    >

    </DailyGoalCreateModal>

    </div>

    <!--    일간 목표 세부-->

  </body>


  </html>
</template>

<script>
import axios from "axios";
import MonthlyGoalDetailModal from "./modal/MonthlyGoalDetailModal.vue";
import MonthlyGoalUpdateModal from "./modal/MonthlyGoalUpdateModal.vue";
import DailyGoalDetailModal from "./modal/DailyGoalDetailModal.vue";
import DailyGoalUpdateModal from "./modal/DailyGoalUpdateModal.vue";
import MonthlyGoalCreateModal from "./modal/MonthlyGoalCreateModal.vue";
import DailyGoalCreateModal from "./modal/DailyGoalCreateModal.vue";

export default {
  name: "Main",
  components: {
    MonthlyGoalDetailModal,
    MonthlyGoalUpdateModal,
    DailyGoalDetailModal,
    DailyGoalUpdateModal,
    MonthlyGoalCreateModal,
    DailyGoalCreateModal,
  },
  data() {
    return {
      date: {
        year: "",
        month: "",
        day: "",
        dayOfWeek: ""
      },
      loginId:"",
      monthlyGoals: [],
      dailyGoals: [],
      viewMonthlyGoals: [],
      detailMonthlyGoal: {},
      createdGoalTypes: [],
      updateMonthlyGoal: {},
      detailDailyGoal: {},
      updateDailyGoal: {},
      createdMonthlyGoals: {},

      // Modal
      isMonthlyGoalDetailModal: false,
      isMonthlyGoalUpdateModal: false,
      isDailyGoalDetailModal: false,
      isDailyGoalUpdateModal: false,
      isMonthlyGoalCreateModal: false,
      isDailyGoalCreateModal : false,
    };
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

            // 날짜 초기화
            this.date.year = response.data.data.dateResponse.year;
            this.date.month = response.data.data.dateResponse.month;
            this.date.day = response.data.data.dateResponse.day;
            this.date.dayOfWeek = response.data.data.dateResponse.dayOfWeek;

            // monthlyGoals 초기화
            for (let monthlyGoal of response.data.data.monthlyGoals) {
              this.monthlyGoals.push(monthlyGoal);
            }

            // dailyGoals 초기화
            this.dailyGoals = response.data.data.dailyGoals;

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
    monthlyGoalMinus : function (monthlyGoalId) {
      axios.post(this.server + "/api/main/minus/monthly/" + monthlyGoalId)
          .then((response) => {
            this.monthlyGoals = response.data.data;
          }).catch((error) => {
            alert(error.response.data.message)
      })
    },
    monthlyGoalPlus : function(monthlyGoalId) {
      axios.post(this.server + "/api/main/plus/monthly/" + monthlyGoalId)
          .then((response) => {
            this.monthlyGoals = response.data.data;
          }).catch((error) => {
            alert(error.response.data.message)
      })
    },
    dailyGoalMinus : function (dailyGoalId) {
      axios.post(this.server + "/api/main/minus/daily/" + dailyGoalId)
          .then((response) => {
            console.log("response = " + JSON.stringify(response,null,2))
            if (response.data.data.monthlyGoals != null) {
              this.monthlyGoals = response.data.data.monthlyGoals;
            }
            this.dailyGoals[response.data.data.dailyGoals[0].dayOfWeek] = response.data.data.dailyGoals;
          }).catch((error) => {
            alert(error.response.data.message)
      })
    },
    dailyGoalPlus : function (dailyGoalId) {
      axios.post(this.server + "/api/main/plus/daily/" + dailyGoalId)
          .then((response) => {
          if (response.data.data.monthlyGoals != null) {
              this.monthlyGoals = response.data.data.monthlyGoals;
          }
          this.dailyGoals[response.data.data.dailyGoals[0].dayOfWeek] = response.data.data.dailyGoals;
          }).catch((error) => {
            alert(error.response.data.message);
      })
    },
    monthlyGoalDetail: function (monthlyGoalId) {
      axios.get(this.server + "/api/main/detail/monthly/" + monthlyGoalId)
          .then((response) => {
            this.detailMonthlyGoal = response.data.data;
            if (!this.isMonthlyGoalDetailModal) {
              this.openMonthlyGoalDetailModal();
            } else {
              this.closeMonthlyGoalDetailModal();
            }
          })
    },
    openMonthlyGoalDetailModal() {
      this.isMonthlyGoalDetailModal = true;
    },
    closeMonthlyGoalDetailModal() {
      this.isMonthlyGoalDetailModal = false;
    },
    deleteMonthlyGoal : function (monthlyGoalId) {
      axios.post(this.server + "/api/main/monthly/delete/" + monthlyGoalId)
          .then((response) => {
            this.isMonthlyGoalDetailModal = false;
            this.$router.go();
          }).catch((error) => {
            alert(error)
      })
    },
    modifyMonthlyGoalView: function (monthlyGoalId) {
      axios.get(this.server + "/api/main/monthly/update/" + monthlyGoalId)
          .then((response) => {
            console.log("response = " + JSON.stringify(response, null, 2))
            this.createdGoalTypes = response.data.data.createdGoalTypes
            this.updateMonthlyGoal = response.data.data.updateMonthlyGoal
            this.closeMonthlyGoalDetailModal();
            this.openMonthlyGoalUpdateModal();
          }).catch((error) => {
        alert(error)
      })
    },
    openMonthlyGoalUpdateModal() {
      this.isMonthlyGoalUpdateModal = true;
    },
    closeMonthlyGoalUpdateModal() {
      this.isMonthlyGoalUpdateModal = false;
    },
    modifyMonthlyGoal : function (monthlyGoal) {
      axios.post(this.server + "/api/main/monthly/update", monthlyGoal)
          .then((response) => {
          }).catch((error) => {
            alert(error)
      })
    },
    dailyGoalDetail : function (dailyGoalId) {
      axios.get(this.server + "/api/main/daily/detail/" + dailyGoalId)
          .then((response => {
            this.isDailyGoalDetailModal = true;
            this.detailDailyGoal = response.data.data
          }))
    },
    closeDailyGoalDetailModal : function () {
      this.isDailyGoalDetailModal = false;
    },
    openDailyGoalUpdateModal : function() {
      this.isDailyGoalUpdateModal = true;
    },
    closeDailyGoalUpdateModal: function() {
      this.isDailyGoalUpdateModal = false;
    },
    modifyDailyGoalView: function (dailyGoalId) {
      axios.get(this.server + "/api/main/daily/update/" + dailyGoalId)
          .then((response) => {
            this.isDailyGoalDetailModal = false;
            this.updateDailyGoal = response.data.data.dailyGoal;
            this.createdMonthlyGoals = response.data.data.monthlyGoals;
            this.openDailyGoalUpdateModal();
          })
    },
    modifyDailyGoal: function (dailyGoal) {
      axios.post(this.server + "/api/main/daily/update", dailyGoal)
          .then((response) => {
          }).catch((error) => {
      })
    },
    deleteDailyGoal: function (dailyGoalId) {
      axios.delete(this.server + "/api/main/daily/" + dailyGoalId)
          .then((response) => {
            this.$router.go();
          })
          .catch((error) => {})
    },
    openMonthlyGoalCreate: function() {
      this.isMonthlyGoalCreateModal = true;
    },
    createMonthlyGoal: function(monthlyGoal) {
      axios.post(this.server + "/api/main/monthly", monthlyGoal)
          .then((response) => {})
          .catch((error) => {alert(error)})
    },
    closeMonthlyGoalCreateModal : function() {
      this.isMonthlyGoalCreateModal = false;
    },
    openDailyGoalCreate: function() {
      this.isDailyGoalCreateModal = true;
    },
    createDailyGoal: function(dailyGoal) {
      axios.post(this.server + "/api/main/daily", dailyGoal)
          .then((response) => {})
          .chtch((error) => {alert(error)})
    }
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
  margin-bottom: 10px;
}

/*이미지 사진 크기*/
.goal-type-img {
  width: 17px;
  height: 22px;
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