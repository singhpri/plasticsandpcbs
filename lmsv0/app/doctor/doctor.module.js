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
var doctor_component_1 = require("./doctor.component");
var doctor_service_1 = require("./doctor.service");
var common_1 = require("@angular/common");
var forms_1 = require("@angular/forms");
var DoctorModule = (function () {
    function DoctorModule() {
    }
    DoctorModule = __decorate([
        core_1.NgModule({
            declarations: [
                doctor_component_1.DoctorComponent,
            ],
            imports: [
                forms_1.FormsModule,
                common_1.CommonModule,
            ],
            exports: [
                doctor_component_1.DoctorComponent,
            ],
            bootstrap: [],
            providers: [
                doctor_service_1.DoctorService
            ]
        }), 
        __metadata('design:paramtypes', [])
    ], DoctorModule);
    return DoctorModule;
}());
exports.DoctorModule = DoctorModule;
//# sourceMappingURL=doctor.module.js.map