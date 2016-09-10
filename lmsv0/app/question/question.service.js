"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
/**
 * Created by chinm_000 on 9/9/2016.
 */
var core_1 = require('@angular/core');
var mock_questions_1 = require('./mock-questions');
var QuestionService = (function () {
    function QuestionService() {
    }
    /*
    getNumberOfQuestions(): number {
        this.checkNullOrEmptyQuestions();
        return this.questions.length;
    }

    getQuestion(index: number): Question {
        //var randomIndex = Math.floor(Math.random() * (QUESTIONS.length - 1));
        //console.log('randomIndex:' + randomIndex);
        this.checkNullOrEmptyQuestions();
        return QUESTIONS[index];
    }
    */
    QuestionService.prototype.resetQuestionsForCategory = function (categoryId) {
        return Promise.resolve(mock_questions_1.QUESTIONS);
    };
    QuestionService.prototype.checkNullOrEmptyQuestions = function () {
        if (this.questions === null || this.questions.length === 0) {
            throw new Error("No Questions Found");
        }
    };
    QuestionService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [])
    ], QuestionService);
    return QuestionService;
}());
exports.QuestionService = QuestionService;
//# sourceMappingURL=question.service.js.map