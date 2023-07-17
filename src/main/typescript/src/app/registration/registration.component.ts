import {Component} from '@angular/core';
import {UserInfo} from "../login/UserInfo";
import {RegistrationInfo} from "./RegistrationInfo";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";

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

  constructor(private authService: AuthService, private router: Router) {
  }

  register(registrationInfo: NgForm) {
    this.registrationInfo = registrationInfo.value;
    const userInfo: UserInfo = {
      email: this.registrationInfo.email,
      password: this.registrationInfo.password,
    }

    this.authService.register(userInfo).subscribe({
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

  redirectToLogin() {
    this.router.navigate(['/login']);
  }

  googleLogin() {
    this.authService.googleLogin();
  }
}
