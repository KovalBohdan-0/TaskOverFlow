import { Component } from '@angular/core';
import {Priority} from "../Priority";

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.css']
})
export class TaskUpdateComponent {

  protected readonly Priority = Priority;
}
