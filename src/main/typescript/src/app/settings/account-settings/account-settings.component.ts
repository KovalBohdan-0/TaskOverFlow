import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../../service/customer.service";

@Component({
  selector: 'app-account-settings',
  templateUrl: './account-settings.component.html',
  styleUrls: ['./account-settings.component.css']
})
export class AccountSettingsComponent implements OnInit {
  confirmPassword = "";
  email = "";

  constructor(private customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.customerService.getCurrentCustomer().subscribe(customer => {
      this.email = (customer.body as any).email;
    });
  }
}
