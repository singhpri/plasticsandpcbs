import { NgModule }         from '@angular/core';
import { QuestionAnswerComponent }        from "./question.answer.component";
import { QuestionService }  from "./question.service";
import { CommonModule }     from "@angular/common";
import { FormsModule }      from "@angular/forms";
import {QuestionListComponent} from "./questionlist.component";

@NgModule({
    declarations: [
        QuestionAnswerComponent,
        QuestionListComponent,
    ],
    imports: [
      FormsModule,
      CommonModule,
    ],
    exports: [
        QuestionAnswerComponent,
        QuestionListComponent,
    ],
    bootstrap: [
    ],
    providers: [
        QuestionService
    ]
})
export class QuestionModule { }
