/**
 * Created by chinm_000 on 9/9/2016.
 */
import { Injectable } from '@angular/core';
import { DOCTORS } from "./mock-doctors";
import { Doctor } from "./doctor";


@Injectable()
export class DoctorService {
    getDoctors(): Promise<Doctor[]> {
        return Promise.resolve(DOCTORS);
    }
}