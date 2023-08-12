import {Component} from '@angular/core';
import {CustomerService} from "../../service/customer.service";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";
import {AuthService} from "../../service/auth.service";

@Component({
    selector: 'app-password-settings',
    templateUrl: './password-settings.component.html',
    styleUrls: ['./password-settings.component.css']
})
export class PasswordSettingsComponent {
    oldPasswordForm = new FormControl('', [Validators.required]);
    newPasswordForm = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(60)]);
    confirmPasswordForm = new FormControl('', [Validators.required]);
    matcher = new ErrorStateMatcher();
    passwordForm = new FormGroup({
        password: this.newPasswordForm,
        confirmPassword: this.confirmPasswordForm,
    }, {validators: this.checkPasswords});

    constructor(private customerService: CustomerService, private authService: AuthService) {
    }

    confirmErrorMatcher = {
        isErrorState: (control: FormControl, form: FormGroupDirective): boolean => {
            return control.touched && control.invalid || control.touched && this.passwordForm.get('password').touched && this.passwordForm.invalid;
        }
    }

    checkPasswords(group: FormGroup) {
        return group.get('password').value === group.get('confirmPassword').value ? null : {passwordMismatch: true};
    }

    updatePassword() {
        this.customerService.updatePassword({oldPassword: this.oldPasswordForm.value, newPassword: this.newPasswordForm.value}).subscribe({
                next: (response) => {
                    this.authService.storeToken((response.body as any).jwt);
                }
            }
        );
    }
}
