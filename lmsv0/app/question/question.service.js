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
        this.questionsAnsweredEvent = new core_1.EventEmitter();
    }
    QuestionService.prototype.resetQuestionsForCategory = function (categoryId) {
        return Promise.resolve(mock_questions_1.QUESTIONS.filter(function (element, index, array) {
            var question = element;
            return question.categoryId === categoryId;
        }));
    };
    QuestionService.prototype.questionsAnswered = function () {
        this.questionsAnsweredEvent.emit({ value: true });
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], QuestionService.prototype, "questionsAnsweredEvent", void 0);
    QuestionService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [])
    ], QuestionService);
    return QuestionService;
}());
exports.QuestionService = QuestionService;
//# sourceMappingURL=question.service.js.map