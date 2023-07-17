import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {AuthService} from "./auth.service";
import { RegistrationComponent } from './registration/registration.component';
import {RouterLink, RouterModule, Routes} from "@angular/router";
import { BoardComponent } from './board/board.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'board', component: BoardComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    BoardComponent,
  ],
  imports: [
    BrowserModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    RouterLink,
    RouterModule.forRoot(routes)
  ],
  providers: [HttpClient, AuthService],
  bootstrap: [AppComponent],
  exports: [RouterModule]
})
export class AppModule { }
