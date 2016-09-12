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
var question_1 = require("./question");
var QuestionAnswerComponent = (function () {
    function QuestionAnswerComponent() {
        this.debug = true;
        this.answerProvided = new core_1.EventEmitter();
    }
    QuestionAnswerComponent.prototype.answerSelected = function (event) {
        this.answerProvided.emit({ value: event.value });
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', question_1.Question)
    ], QuestionAnswerComponent.prototype, "question", void 0);
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], QuestionAnswerComponent.prototype, "answerProvided", void 0);
    QuestionAnswerComponent = __decorate([
        core_1.Component({
            selector: "question",
            template: "\n        <div *ngIf=\"question != null\">\n            <h1>{{ question.title }}</h1>\n                <li *ngFor=\"let item of question.answers\">\n                    <input [(ngModel)]=\"answer\"\n                        name=\"{{'question:' + question.id}}\"\n                        (click)=\"answerSelected($event)\"\n                        value=\"{{item}}\"\n                        type=\"radio\">{{item}}                    \n                </li>\n                <span *ngIf=\"debug\">{{ answer }}</span>\n        </div>        \n    "
        }), 
        __metadata('design:paramtypes', [])
    ], QuestionAnswerComponent);
    return QuestionAnswerComponent;
}());
exports.QuestionAnswerComponent = QuestionAnswerComponent;
//# sourceMappingURL=question.answer.component.js.map