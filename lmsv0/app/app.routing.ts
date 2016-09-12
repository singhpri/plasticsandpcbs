import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { QuestionListComponent } from "./question/questionlist.component";
import { DoctorComponent } from "./doctor/doctor.component";
import {ThankYouComponent} from "./std/thankyou.component";
import {Globals} from "./Globals";

const appRoutes: Routes = [
    /*
    {
        path: 'heroes',
        component: HeroesComponent
    },
    {
        path: 'dashboard',
        component: DashboardComponent
    },
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: 'detail/:id',
        component: HeroDetailComponent
    }
    */
    {
        path: [Globals.QUESTION_LIST_PAGE, '/:id'].join(''),
        component: QuestionListComponent
    },
    {
        path: '',
        component: DoctorComponent,
    },
    {
        path: Globals.THANK_YOU_PAGE,
        component: ThankYouComponent
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);