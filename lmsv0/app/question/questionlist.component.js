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
var question_service_1 = require("./question.service");
var router_1 = require("@angular/router");
var QuestionListComponent = (function () {
    function QuestionListComponent(route, questionService) {
        this.route = route;
        this.questionService = questionService;
        this.questionIndex = 0;
        this.debug = true;
    }
    QuestionListComponent.prototype.nextQuestion = function (event) {
        this.questionIndex++;
        if (this.questions.length == this.questionIndex) {
            this.questionService.questionsAnswered();
        }
    };
    QuestionListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            console.log("In route for each in question list");
            var categoryId = params['id'];
            _this.resetQuestionsForCategory(categoryId);
            console.log("Question Retrieved: " + JSON.stringify(_this.questions));
        });
    };
    QuestionListComponent.prototype.resetQuestionsForCategory = function (categoryId) {
        var _this = this;
        this.questions = null;
        this.questionService.resetQuestionsForCategory(categoryId)
            .then(function (questions) { return _this.questions = questions; });
    };
    QuestionListComponent = __decorate([
        core_1.Component({
            selector: "question-list",
            template: "\n        <div *ngIf=\"questions != null\">\n            <div *ngFor=\"let question of questions; let i = index;\">\n                <question [question]=\"question\" \n                    *ngIf=\"i === questionIndex\" (answerProvided)=\"nextQuestion($event)\"></question>            \n            </div> \n        </div>\n    "
        }), 
        __metadata('design:paramtypes', [router_1.ActivatedRoute, question_service_1.QuestionService])
    ], QuestionListComponent);
    return QuestionListComponent;
}());
exports.QuestionListComponent = QuestionListComponent;
//# sourceMappingURL=questionlist.component.js.map