import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { routing }        from './app.routing';

import { AppComponent }         from './app.component';
import { HeroDetailComponent }  from './hero-detail.component';
import { HeroesComponent }      from './heroes.component';
import { HeroService }          from './hero.service';
import { DashboardComponent }   from './dashboard.component';
import { QuestionModule }      from "./question/question.module";
import { DoctorModule }         from "./doctor/doctor.module";
import {ThankYouComponent} from "./std/thankyou.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    routing,
    QuestionModule,
    DoctorModule
  ],
  declarations: [
    AppComponent,
    HeroesComponent,
    DashboardComponent,
    HeroDetailComponent,
    ThankYouComponent
  ],
  bootstrap: [
    AppComponent
  ],
  providers: [
    HeroService, // Add provider here since it's used in several components declared here.
  ]
})
export class AppModule { }