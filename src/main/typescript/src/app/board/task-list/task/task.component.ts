import {Component, Input, OnInit} from '@angular/core';
import {Task} from "./Task";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  @Input() task: Task;
  color: string = "black";

  ngOnInit(): void {
    this.color = this.getColor();
  }

  getColor(): string {
    if (this.task.priority == "HIGH") {
      return "red";
    } else if (this.task.priority == "MEDIUM") {
      return "orange";
    } else {
      return "green";
    }
  }
}
