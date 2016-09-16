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
var Person = (function () {
    function Person() {
    }
    return Person;
}());
exports.Person = Person;
var AppComponent = (function () {
    function AppComponent() {
        this.name = 'Your Name';
        this.mobile = 'Mobile Phone';
        this.person = {
            name: '',
            mobile: 0
        };
    }
    AppComponent = __decorate([
        core_1.Component({
            selector: 'my-app',
            template: '<div>' +
                '<label> Your name: </label>' +
                '<div><input [(ngModel)] ="person.name" placeholder="name"></div>' +
                '</div>' +
                '<div>' +
                '<label> Mobile Phone: </label>' +
                '<div><input [(ngModel)] ="person.mobile" placeholder="mobile"></div>' +
                '</div>',
            styles: ["\n    label {\n      text-align: center;\n      color:#A9A9A9;\n      font-size: 40px;\n      font-family: \"arial\", sans-serif;\n    }\n    input {\n    width: 400px;\n    height: 50px;\n    padding: 12px, 20px;\n    margin: 8px 0;\n    box-sizing: border-box;\n    border: 1px solid gray;\n    background-color: light gray;\n    \n    }\n    "]
        }), 
        __metadata('design:paramtypes', [])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map