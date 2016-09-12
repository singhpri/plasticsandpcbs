import {Component, ViewChild} from '@angular/core';
import {Router, RouterOutlet, RouterLink, RouterModule, NavigationStart, Event} from "@angular/router";
import {DoctorComponent} from "./doctor/doctor.component";
import {DoctorService} from "./doctor/doctor.service";
import {QuestionService} from "./question/question.service";
import {Globals as G} from "./Globals";

@Component({
    selector:'lms',
    template: `
    <router-outlet></router-outlet>
    `,
    styleUrls: ['app/app.component.css'],
})

export class AppComponent {


    constructor(private router: Router, private doctorService: DoctorService, private questionService: QuestionService) {
        doctorService.doctorSelected.subscribe((event:any) => this.doctorChanged(event));
        questionService.questionsAnsweredEvent.subscribe((event:any) => this.showThankYouPage());
    }

    private doctorChanged(event: any): void {
        var link = [G.QUESTION_LIST_PAGE, event.value.id];
        this.router.navigate(link);
    }

    private showThankYouPage() {
        this.router.navigate([G.THANK_YOU_PAGE]);
        setTimeout(() => {this.router.navigate([''])}, 6000);
    }
}