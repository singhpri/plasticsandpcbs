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
var core_1 = require('@angular/core');
var router_1 = require("@angular/router");
var doctor_service_1 = require("./doctor/doctor.service");
var question_service_1 = require("./question/question.service");
var Globals_1 = require("./Globals");
var AppComponent = (function () {
    function AppComponent(router, doctorService, questionService) {
        var _this = this;
        this.router = router;
        this.doctorService = doctorService;
        this.questionService = questionService;
        doctorService.doctorSelected.subscribe(function (event) { return _this.doctorChanged(event); });
        questionService.questionsAnsweredEvent.subscribe(function (event) { return _this.showThankYouPage(); });
    }
    AppComponent.prototype.doctorChanged = function (event) {
        var link = [Globals_1.Globals.QUESTION_LIST_PAGE, event.value.id];
        this.router.navigate(link);
    };
    AppComponent.prototype.showThankYouPage = function () {
        var _this = this;
        this.router.navigate([Globals_1.Globals.THANK_YOU_PAGE]);
        setTimeout(function () { _this.router.navigate(['']); }, 6000);
    };
    AppComponent = __decorate([
        core_1.Component({
            selector: 'lms',
            template: "\n    <router-outlet></router-outlet>\n    ",
            styleUrls: ['app/app.component.css'],
        }), 
        __metadata('design:paramtypes', [router_1.Router, doctor_service_1.DoctorService, question_service_1.QuestionService])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map