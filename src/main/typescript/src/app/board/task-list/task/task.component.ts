import {Component, Input, OnInit} from '@angular/core';
import {Task} from "./Task";
import {MatDialog} from "@angular/material/dialog";
import {TaskUpdateComponent} from "./task-update/task-update.component";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  @Input() task: Task;
  color: string = "black";

  constructor(public dialog: MatDialog) {
  }


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

  openTaskModal() {
    const dialogRef = this.dialog.open(TaskUpdateComponent, {
      data: this.task,
    });

    dialogRef.afterClosed().subscribe( () => {
      console.log('The dialog was closed');
    });
  }
}
