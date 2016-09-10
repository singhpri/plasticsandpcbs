import { Component } from '@angular/core';
import { Router } from "@angular/router";

@Component({
    selector:'lms',
    template: `    
    <router-outlet>        
    </router-outlet>
    `,
    styleUrls: ['app/app.component.css'],
})

export class AppComponent {
    title = "MDVal";
    private currentDoctorId : string;

    constructor(private router : Router) { }

    routeToQuestions(event: any) {
        let doctor = event.value;
        this.currentDoctorId = doctor.id;
        let link = ['question', this.currentDoctorId];
        this.router.navigate(link);
    }
}