import {Component} from '@angular/core';
import {AuthService} from "../service/auth.service";

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent {
    constructor(private authService: AuthService) {
    }

    logOut() {
        this.authService.logOut();
    }
}
