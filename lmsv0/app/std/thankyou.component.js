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
var ThankYouComponent = (function () {
    function ThankYouComponent(router) {
        this.router = router;
    }
    ;
    ThankYouComponent.prototype.ngOnInit = function () {
    };
    ThankYouComponent.prototype.ngAfterContentInit = function () {
        //setTimeout(() => {this.router.navigate([''])}, 6000);
    };
    ThankYouComponent = __decorate([
        core_1.Component({
            selector: 'thankyou',
            template: '<h1>Thank you!</h1>',
        }), 
        __metadata('design:paramtypes', [router_1.Router])
    ], ThankYouComponent);
    return ThankYouComponent;
}());
exports.ThankYouComponent = ThankYouComponent;
//# sourceMappingURL=thankyou.component.js.map