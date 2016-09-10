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
var doctor_service_1 = require("./doctor.service");
var DoctorComponent = (function () {
    function DoctorComponent(doctorService) {
        this.doctorService = doctorService;
        this.doctorSelected = new core_1.EventEmitter();
        this.debug = true;
    }
    DoctorComponent.prototype.onClick = function (doctor) {
        this.selectedDoctor = doctor;
        this.doctorSelected.emit({ value: this.selectedDoctor });
    };
    DoctorComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.doctorService.getDoctors()
            .then(function (result) {
            _this.doctors = result;
            console.log("Doctor Retrieved: " + JSON.stringify(_this.doctors));
        });
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], DoctorComponent.prototype, "doctorSelected", void 0);
    DoctorComponent = __decorate([
        core_1.Component({
            selector: "doctor",
            template: "\n        <div *ngIf=\"doctors != null\">\n            <div *ngFor=\"let doctor of doctors\">\n                <button (click)=\"onClick(doctor)\">{{doctor.name}}</button>                    \n            </div>\n            <span *ngIf=\"debug && (selectedDoctor != null) \">{{ selectedDoctor.name }}</span>\n        </div>        \n    "
        }), 
        __metadata('design:paramtypes', [doctor_service_1.DoctorService])
    ], DoctorComponent);
    return DoctorComponent;
}());
exports.DoctorComponent = DoctorComponent;
//# sourceMappingURL=doctor.component.js.map