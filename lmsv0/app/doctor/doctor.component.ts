/**
 * Created by chinm_000 on 9/9/2016.
 */
import {Component, Input, OnInit, EventEmitter, Output} from '@angular/core';
import { DoctorService } from "./doctor.service";
import { Doctor } from "./doctor";
import {Router} from "@angular/router";

@Component({
    selector: "doctor",
    template: `
        <div *ngIf="doctors != null">
            <div *ngFor="let doctor of doctors">
                <button (click)="onClick(doctor)">{{doctor.name}}</button>                    
            </div>
            <span *ngIf="debug && (selectedDoctor != null) ">{{ selectedDoctor.name }}</span>
        </div>        
    `
})

export class DoctorComponent implements OnInit {
    doctors: Doctor[];
    selectedDoctor: Doctor;

    @Output() doctorSelected = new EventEmitter();

    debug: boolean = true;

    constructor(private doctorService: DoctorService, private router : Router) { }

    onClick(doctor: Doctor) : void {
        this.selectedDoctor = doctor;
        this.doctorSelected.emit({value: this.selectedDoctor});
        let link = ['question', this.selectedDoctor.id];
        this.router.navigate(link);
    }

    ngOnInit() : void {
        this.doctorService.getDoctors()
            .then(result => {
                this.doctors = result;
                console.log("Doctor Retrieved: " + JSON.stringify(this.doctors))
            });
    }
}
