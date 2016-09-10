import { NgModule }       from '@angular/core';
import { DoctorComponent} from "./doctor.component";
import { DoctorService } from "./doctor.service";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { Doctor } from "./doctor";


@NgModule({
    declarations: [
        DoctorComponent,
    ],
    imports: [
      FormsModule,
      CommonModule,
    ],
    exports: [
        DoctorComponent,
    ],
    bootstrap: [
    ],
    providers: [
        DoctorService
    ]
})
export class DoctorModule { }
