import { Component } from '@angular/core';
import {BoardService} from "../../../service/board.service";
import {SharedService} from "../../../service/shared.service";
import {MatDialog} from "@angular/material/dialog";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-sidebar-add-member',
  templateUrl: './sidebar-add-member.component.html',
  styleUrls: ['./sidebar-add-member.component.css']
})
export class SidebarAddMemberComponent {
  email;

  constructor(private boardService: BoardService, private sharedService: SharedService, private dialog: MatDialog, private snackBar: MatSnackBar) {
  }

  addBoardMember() {
    this.boardService.addBoardMember(this.sharedService.boardId, this.email).subscribe({
      next: (res: any) => {
        if (res.status === 200) {
          this.dialog.closeAll();
          this.snackBar.open("Member added successfully", "Close");
        }
      }
    });
  }
}
