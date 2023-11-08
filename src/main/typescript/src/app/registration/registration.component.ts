import {Component, OnInit} from '@angular/core';
import {UserInfo} from "../login/UserInfo";
import {RegistrationInfo} from "./RegistrationInfo";
import {AuthService} from "../service/auth.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  registrationInfo: RegistrationInfo = {
    email: '',
    password: '',
    confirmPassword: '',
  }

  constructor(private authService: AuthService, private router: Router, private  matSnackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.authService.healthCheck().subscribe();
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
        if (error.status == 409) {
          this.matSnackBar.open("Email already exists", "Close", {duration: 5000});
        } else {
          this.matSnackBar.open("Error while registering", "Close", {duration: 5000});
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
