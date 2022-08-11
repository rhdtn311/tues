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
          <img @click="goToGoalList" id="goal-list-button" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/goal_list.jpg" alt="goal-list">
        </div>
        <div>
          <img id="logout-button" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/logout-btn.png" @click="logout" alt="logout" >
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
      <div id="tuesday">
        <div class="day-title">TUE</div>
        <div class="day-content">
          <div v-if="this.dailyGoals.TUE != []" v-for="dailyGoal in this.dailyGoals.TUE">
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
      <div id="wednesday">
        <div class="day-title">WED</div>
        <div class="day-content">
          <div v-if="this.dailyGoals.WED != []" v-for="dailyGoal in this.dailyGoals.WED">
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
      <div id="thursday">
        <div class="day-title">THU</div>
        <div class="day-content">
          <div v-if="this.dailyGoals.THU != []" v-for="dailyGoal in this.dailyGoals.THU">
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
      <div id="friday">
        <div class="day-title">FRI</div>
        <div class="day-content">
          <div v-if="this.dailyGoals.FRI != []" v-for="dailyGoal in this.dailyGoals.FRI">
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
      <div id="saturday">
        <div class="day-title">SAT</div>
        <div class="day-content">
          <div v-if="this.dailyGoals.SAT != []" v-for="dailyGoal in this.dailyGoals.SAT">
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
      <div id="sunday">
        <div class="day-title">SUN</div>
        <div class="day-content">
          <div v-if="this.dailyGoals.SUN != []" v-for="dailyGoal in this.dailyGoals.SUN">
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
    </div>

    <!-- 컴포넌트 MyModal -->
    <MonthlyGoalDetailModal @close="closeMonthlyGoalDetailModal"
                            @delete="deleteMonthlyGoal"
                            @update="modifyMonthlyGoalView"
                            v-if="this.isMonthlyGoalDetailModal"
                            v-bind:detailGoal="detailMonthlyGoal">
    </MonthlyGoalDetailModal>
    <MonthlyGoalUpdateModal v-if="this.isMonthlyGoalUpdateModal"
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
                          @close="closeDailyGoalUpdateModal"
                          v-bind:updateDailyGoal="updateDailyGoal"
                          v-bind:createdMonthlyGoals="createdMonthlyGoals">
    </DailyGoalUpdateModal>
    <MonthlyGoalCreateModal v-if="this.isMonthlyGoalCreateModal"
                            @close="closeMonthlyGoalCreateModal">
    </MonthlyGoalCreateModal>
    <DailyGoalCreateModal v-if="this.isDailyGoalCreateModal"
                          @close="closeDailyGoalCreateModal">
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
          .then((response) => {console.log(response)})
          .catch((error) => {
            console.log(JSON.stringify(error, null, 2))
          })
    },
    closeMonthlyGoalCreateModal : function() {
      this.isMonthlyGoalCreateModal = false;
    },
    openDailyGoalCreate: function() {
      this.isDailyGoalCreateModal = true;
    },
    closeDailyGoalCreateModal: function () {
      this.isDailyGoalCreateModal = false;
    },
    goToGoalList: function() {
      this.$router.push("/goalList");
    }
  }
}
</script>

<style scoped>
@import "../../assets/Main-css.css";
</style>