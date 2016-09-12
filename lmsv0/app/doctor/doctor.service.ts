/**
 * Created by chinm_000 on 9/9/2016.
 */
import {Injectable, Output, EventEmitter} from '@angular/core';
import { DOCTORS } from "./mock-doctors";
import { Doctor } from "./doctor";


@Injectable()
export class DoctorService {
    @Output() doctorSelected = new EventEmitter();

    getDoctors(): Promise<Doctor[]> {
        return Promise.resolve(DOCTORS);
    }

    setActiveDoctor(selectedDoctor: Doctor) {
        this.doctorSelected.emit({value: selectedDoctor});
    }
}