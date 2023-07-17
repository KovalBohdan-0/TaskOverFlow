import {Component} from '@angular/core';
import {UserInfo} from "../login/UserInfo";
import {RegistrationInfo} from "./RegistrationInfo";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registrationInfo: RegistrationInfo = {
    email: '',
    password: '',
    confirmPassword: '',
  }

  constructor(private authService: AuthService, private router: Router) {}

  register(registrationInfo: RegistrationInfo) {
    this.authService.register(registrationInfo).subscribe(
      (userInfo: UserInfo) => {
        this.router.navigate(['/login']);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }

  googleLogin() {
    this.authService.googleLogin();
  }
}
