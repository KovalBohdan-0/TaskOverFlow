import {Component, OnInit} from '@angular/core';
import {AuthService} from "../service/auth.service";
import {NgForm} from "@angular/forms";
import {UserInfo} from "./UserInfo";
import {Router} from "@angular/router";
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userInfo: UserInfo = {
    email: "",
    password: "",
  };

  constructor(private authService: AuthService, private router: Router, private matSnackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.authService.healthCheck().subscribe();
    this.authService.loginWithOAuthJwt();
    this.authService.ifLoggedIn();
  }

  logIn(formUser: NgForm): void {
    this.userInfo = formUser.value;
    this.authService.logIn(this.userInfo).subscribe({
      next: (response: any) => {
        this.authService.storeToken(response.body.jwt);
        this.router.navigate(['/board']);
      },
      error: () => {
        this.matSnackBar.open("Invalid email or password", "Close", {duration: 5000});
      }
    });
  }

  googleLogin() {
    this.authService.googleLogin();
  }

  redirectToRegistration() {
    this.router.navigate(['registration']);
  }
}
