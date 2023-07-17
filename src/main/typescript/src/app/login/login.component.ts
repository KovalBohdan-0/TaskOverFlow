import {Component} from '@angular/core';
import {AuthService} from "../auth.service";
import {NgForm} from "@angular/forms";
import {UserInfo} from "./UserInfo";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  userInfo: UserInfo = {
    email: "",
    password: "",
  };

  constructor(private authService: AuthService, private router: Router) {
  }

  logIn(formUser: NgForm): void {
    this.userInfo = formUser.value;
    this.authService.logIn(this.userInfo).subscribe({
      next: (response: any) => {
        this.authService.storeToken(response.body.jwt);
        this.router.navigate(['/board']);
      },
      error: (error: any) => {
        if (error.status == 404) {
          //TODO add error message
        } else {
        }
      }
    });
  }

  googleLogin() {
    this.authService.googleLogin();
  }

  redirectToRegistration() {
    console.log("here");
    this.router.navigate(['registration']);
  }
}
