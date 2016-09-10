import { NgModule }       from '@angular/core';
import { DQuestion } from "./dquestion.component";
import { QuestionService } from "./question.service";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        DQuestion,
    ],
    imports: [
      FormsModule,
      CommonModule,
    ],
    exports: [
        DQuestion,
    ],
    bootstrap: [
    ],
    providers: [
        QuestionService
    ]
})
export class DQuestionModule { }
