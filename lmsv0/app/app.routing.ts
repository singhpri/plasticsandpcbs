import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HeroesComponent } from './heroes.component';
import { HeroDetailComponent } from './hero-detail.component';
import { DashboardComponent } from './dashboard.component';
import { QuestionAnswerComponent } from "./question/question.answer.component";
import { QuestionListComponent } from "./question/questionlist.component";
import {DoctorComponent} from "./doctor/doctor.component";

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
        path: 'question/:id',
        component: QuestionListComponent
    },
    {
        path: '',
        component: DoctorComponent
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);