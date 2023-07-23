import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import { RegistrationComponent } from './registration/registration.component';
import {RouterLink, RouterModule, Routes} from "@angular/router";
import { BoardComponent } from './board/board.component';
import { TaskListComponent } from './board/task-list/task-list.component';
import { TaskComponent } from './board/task-list/task/task.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {CdkDrag, CdkDragPlaceholder, CdkDropList, CdkDropListGroup} from "@angular/cdk/drag-drop";
import {RxStompService} from "./service/rx-stomp.service";
import {rxStompServiceFactory} from "./service/rx-stomp-service-factory";

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'board', component: BoardComponent },
  { path: 'board/:id', component: BoardComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    BoardComponent,
    TaskListComponent,
    TaskComponent,
  ],
    imports: [
        BrowserModule,
        NgbModule,
        FormsModule,
        HttpClientModule,
        RouterLink,
        RouterModule.forRoot(routes),
        BrowserAnimationsModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatInputModule,
        MatButtonModule,
        CdkDropList,
        CdkDrag,
        CdkDragPlaceholder,
        CdkDropListGroup
    ],
  providers: [
    HttpClient,
    AuthService,
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
    },
  ],
  bootstrap: [AppComponent],
  exports: [RouterModule]
})
export class AppModule { }
