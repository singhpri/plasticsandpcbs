"use strict";
var router_1 = require('@angular/router');
var questionlist_component_1 = require("./question/questionlist.component");
var doctor_component_1 = require("./doctor/doctor.component");
var thankyou_component_1 = require("./std/thankyou.component");
var Globals_1 = require("./Globals");
var appRoutes = [
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
        path: [Globals_1.Globals.QUESTION_LIST_PAGE, '/:id'].join(''),
        component: questionlist_component_1.QuestionListComponent
    },
    {
        path: '',
        component: doctor_component_1.DoctorComponent,
    },
    {
        path: Globals_1.Globals.THANK_YOU_PAGE,
        component: thankyou_component_1.ThankYouComponent
    }
];
exports.routing = router_1.RouterModule.forRoot(appRoutes);
//# sourceMappingURL=app.routing.js.map