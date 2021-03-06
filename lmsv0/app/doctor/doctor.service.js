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
var mock_doctors_1 = require("./mock-doctors");
var DoctorService = (function () {
    function DoctorService() {
        this.doctorSelected = new core_1.EventEmitter();
    }
    DoctorService.prototype.getDoctors = function () {
        return Promise.resolve(mock_doctors_1.DOCTORS);
    };
    DoctorService.prototype.setActiveDoctor = function (selectedDoctor) {
        this.doctorSelected.emit({ value: selectedDoctor });
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], DoctorService.prototype, "doctorSelected", void 0);
    DoctorService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [])
    ], DoctorService);
    return DoctorService;
}());
exports.DoctorService = DoctorService;
//# sourceMappingURL=doctor.service.js.map