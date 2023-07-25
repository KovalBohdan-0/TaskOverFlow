import {Component, Input, OnInit} from '@angular/core';
import {ShortTask} from "./ShortTask";
import {MatDialog} from "@angular/material/dialog";
import {TaskUpdateComponent} from "./task-update/task-update.component";
import {TaskService} from "../../../service/task.service";
import {SharedService} from "../../../service/shared.service";
import {Task} from "./Task";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  @Input() task: ShortTask;
  color: string = "black";

  constructor(public dialog: MatDialog,private taskService: TaskService, private sharedService: SharedService) {
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

  updateTaskDone() {
    this.taskService.getTask(this.task.id).subscribe({
      next: (response: any) => {
        let updatedTask: Task = response.body;
        updatedTask.done = !updatedTask.done;
        this.taskService.updateTask(updatedTask , this.sharedService.boardId);
      }});
  }

  openTaskModal(event) {
    const clickedId = event.target.attributes.id;

    if (clickedId == undefined || clickedId.value != "done" && clickedId.value != "done-checkbox") {
      this.dialog.open(TaskUpdateComponent, {
        data: this.task.id,
      });
    }
  }
}
